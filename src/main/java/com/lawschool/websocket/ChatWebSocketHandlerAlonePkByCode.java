package com.lawschool.websocket;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.service.UserService;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.util.RedisUtil;
import net.sf.json.JSONObject;
import com.lawschool.beans.Message;
import com.lawschool.beans.User;
import com.lawschool.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.util.HtmlUtils;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * 说明：WebSocket处理器
 * @author
 * @version 1.0
 * @date
 */
@Component("chatWebSocketHandlerAlonePkByCode")
public class ChatWebSocketHandlerAlonePkByCode implements WebSocketHandler {
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private BattlePlatformService battlePlatformService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompetitionOnlineService competitionOnlineService;

	//在线用户的SOCKETsession(存储了所有的通信通道)
	public static final Map<String, WebSocketSession> USER_SOCKETSESSION_MAP;

	public static final Map<String, List> timuMap;
	public static final Map<String, CompetitionOnline> timussettingMap;

	public static final Map<String, BattlePlatform> battlePlatformMap;


	//存储所有的在线用户
	static {
		USER_SOCKETSESSION_MAP = new HashMap<String, WebSocketSession>();

		timuMap=new HashMap<String,List>();

		timussettingMap=new HashMap<String,CompetitionOnline>();

		battlePlatformMap=new HashMap<String,BattlePlatform>();
	}

	/**
	 * webscoket建立好链接之后的处理函数--连接建立后的准备工作
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		//将当前的连接的用户会话放入MAP,key是用户编号
		User loginUser=(User) webSocketSession.getAttributes().get("loginUser");
		String type=(String) webSocketSession.getAttributes().get("joinType");
		String code=(String) webSocketSession.getAttributes().get("joinCode");

		USER_SOCKETSESSION_MAP.put(loginUser.getId(), webSocketSession);
		/**
		 *去redis中取redisFromBattlePlatList  对战平台的集合   先去看看里面有没有人在创建频台
		 */
		new Thread(new Runnable() {
			public  void run() {
				xxx(loginUser,webSocketSession,type,code);
			}

		}).start();

	}

	@Override
	/**
	 * 客户端发送服务器的消息时的处理函数，在这里收到消息之后可以分发消息
	 */
	//处理消息：当一个新的WebSocket到达的时候，会被调用（在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理）
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> message) throws Exception {
		//如果消息没有任何内容，则直接返回
		if(message.getPayloadLength()==0)return;
		//反序列化服务端收到的json消息
		Message msg = GsonUtils.fromJson(message.getPayload().toString(), Message.class);



		if(msg.getMyanswer()!=null && msg.getTq()!=null)
		{
			competitionOnlineService.saveQuestion(msg.getTq(),msg.getMyanswer(),msg.getFrom());
		}



		msg.setDate(new Date());
		//处理html的字符，转义：
		String text = msg.getText();
		//转换为HTML转义字符表示
		String htmlEscapeText = HtmlUtils.htmlEscape(text);
		msg.setText(htmlEscapeText);
		System.out.println("消息（可存数据库作为历史记录）:"+message.getPayload().toString());
		//判断是群发还是单发
		if(msg.getTo()==null||msg.getTo().equals("-1")){
			//群发
			sendMessageToAll(new TextMessage(GsonUtils.toJson(msg)));
		}
		else if((msg.getTo().contains(",")))
		{
			sendMessageToUsers(msg.getTo(), new TextMessage(GsonUtils.toJson(msg)));
		}
		else{
			//单发
			sendMessageToUser(msg.getTo(), new TextMessage(GsonUtils.toJson(msg)));
		}
	}


	@Override
	/**
	 * 消息传输过程中出现的异常处理函数
	 * 处理传输错误：处理由底层WebSocket消息传输过程中发生的异常
	 */
	public void handleTransportError(WebSocketSession webSocketSession, Throwable exception) throws Exception {
		// 记录日志，准备关闭连接
		System.out.println("Websocket异常断开:" + webSocketSession.getId() + "已经关闭");
		//一旦发生异常，强制用户下线，关闭session
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}

//		//群发消息告知大家
//		Message msg = new Message();
//		msg.setDate(new Date());
//
//		//获取异常的用户的会话中的用户编号
//		User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
//		//获取所有的用户的会话
//		Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
//		//并查找出在线用户的WebSocketSession（会话），将其移除（不再对其发消息了。。）
//		for (Entry<String, WebSocketSession> entry : entrySet) {
//			if(entry.getKey().equals(loginUser.getId())){
//				msg.setText("万众瞩目的【"+loginUser.getFullName()+"】已经退出。。。！");
//				//清除在线会话
//				USER_SOCKETSESSION_MAP.remove(entry.getKey());
//				//记录日志：
//				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
//				break;
//			}
//		}
//
//		//并查找出在线用户的WebSocketSession（会话），将其移除（不再对其发消息了。。）
//		for (Entry<String, WebSocketSession> entry : entrySet) {
//			msg.getUserList().add(
//					(User)entry.getValue().getAttributes().get("loginUser")
//			);
//		}
//
//		TextMessage message = new TextMessage(GsonUtils.toJson(msg));
//		sendMessageToAll(message);

	}

	@Override
	/**
	 * websocket链接关闭的回调
	 * 连接关闭后：一般是回收资源等
	 */
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		// 记录日志，准备关闭连接
		System.out.println("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");

		//群发消息告知大家
		Message msg = new Message();
		msg.setDate(new Date());

		//获取异常的用户的会话中的用户编号
		User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
//		new Thread(new Runnable() {
//			public synchronized void run() {
//				try {
		//先移除 ，在发消息告诉对方
		//先得到要处理的id  看看有没有匹配成啊  删哪个啊
		String playids=	(String)webSocketSession.getAttributes().get("playids");
		msg.setTo(playids);//为了传到前端页面
		if((playids.contains(",")))
		{
			//说明配成功了  要处理2个用户
			//将他冲USER_SOCKETSESSION_MAP里删掉
			playids.split(",");
			//主动去断这2个用户    但是要先断自己  在给另一个人发消息说我走了   在断另一个人
			msg.setDate(new Date());
			msg.setText(loginUser.getFullName()+"已经离开了,直接获得获胜者奖励"+timussettingMap.get("onlinePksetting"+playids.split(",")[0]).getWinReward());
			msg.setMycore(timussettingMap.get("onlinePksetting"+playids.split(",")[0]).getWinReward());

			if(loginUser.getId().equals(playids.split(",")[0]))
			{
				msg.getUserList().add((User)USER_SOCKETSESSION_MAP.get(playids.split(",")[1]).getAttributes().get("loginUser"));//现在在线人员只有 没掉线的哪一个了
				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
				sendMessageToUser(playids.split(",")[1],message);//给另一个人发消息
				USER_SOCKETSESSION_MAP.get(playids.split(",")[0]).close();
				USER_SOCKETSESSION_MAP.get(playids.split(",")[1]).close();
			}
			else
			{

				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
				msg.getUserList().add((User)USER_SOCKETSESSION_MAP.get(playids.split(",")[0]).getAttributes().get("loginUser"));//现在在线人员只有 没掉线的哪一个了
				sendMessageToUser(playids.split(",")[0],message);//给另一个人发消息不发消息
				USER_SOCKETSESSION_MAP.get(playids.split(",")[1]).close();
				USER_SOCKETSESSION_MAP.get(playids.split(",")[0]).close();
			}
			//将这2个从里面删掉
			USER_SOCKETSESSION_MAP.remove(playids.split(",")[0]);
			USER_SOCKETSESSION_MAP.remove(playids.split(",")[1]);

			//记录日志：
			System.out.println("Socket会话已经移除:用户ID" + playids.split(",")[1]);
			//记录日志：
			System.out.println("Socket会话已经移除:用户ID" + playids.split(",")[0]);
		}
		else
		{
			//还没有配对成功，只需要处理当前的用户
			//将他冲USER_SOCKETSESSION_MAP里删掉

//				msg.setText("万众瞩目的【"+loginUser.getFullName()+"】已经有事先走了，大家继续聊...");
			//删除redis 里的数据

//			Map<String,BattlePlatform> battlePlatformMap=(Map) JSONObject.fromObject(redisUtil.get("redisFrombattlePlatformMap"));
			battlePlatformMap.remove(webSocketSession.getAttributes().get("battlePlatcode"));
//			redisUtil.set("redisFrombattlePlatformMap",battlePlatformMap);
			//清除在线会话
			USER_SOCKETSESSION_MAP.remove(loginUser.getId());
			webSocketSession.close();
			//记录日志：
			System.out.println("Socket会话已经移除:用户ID" + loginUser.getId());
//				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
//				sendMessageTouser(loginUser.getId(),message);//就一个人 都走了  不发消息
		}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//
//		}).start();
	}
	@Override
	/**
	 * 是否支持处理拆分消息，返回true返回拆分消息
	 */
	//是否支持部分消息：如果设置为true，那么一个大的或未知尺寸的消息将会被分割，并会收到多次消息（会通过多次调用方法handleMessage(WebSocketSession, WebSocketMessage). ）
	//如果分为多条消息，那么可以通过一个api：org.springframework.web.socket.WebSocketMessage.isLast() 是否是某条消息的最后一部分。
	//默认一般为false，消息不分割
	public boolean supportsPartialMessages() {
		return false;
	}
	/**
	 *
	 * 说明：给某个人发信息
	 * @param id
	 * @param message
	 * @author 传智.BoBo老师
	 * @throws IOException
	 * @time：2016年10月27日 下午10:40:52
	 */
	private void sendMessageToUser(String id, TextMessage message) throws IOException{
		//获取到要接收消息的用户的session
		WebSocketSession webSocketSession = USER_SOCKETSESSION_MAP.get(id);
		if (webSocketSession != null && webSocketSession.isOpen()) {
			//发送消息
			webSocketSession.sendMessage(message);
		}
	}

	/**
	 *
	 * 说明：给自定义人数发信息
	 * @param ids
	 * @param message
	 * @author
	 * @throws IOException
	 * @time：2016年10月27日 下午10:40:52
	 */

	private void sendMessageToUsers(String ids, TextMessage message) throws IOException{
		//获取到要接收消息的用户的session
		String[] strs=ids.split(",");
		for(String id:strs)
		{
			WebSocketSession webSocketSession = USER_SOCKETSESSION_MAP.get(id);
			//开启多线程发送消息（效率高）
			new Thread(new Runnable() {
				public void run() {
					try {
						if (webSocketSession != null && webSocketSession.isOpen()) {
							webSocketSession.sendMessage(message);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}).start();
		}

	}
	/**
	 *
	 * 说明：群发信息：给所有在线用户发送消息
	 * @author 传智.BoBo老师
	 * @time：2016年10月27日 下午10:40:07
	 */
	private void sendMessageToAll(final TextMessage message){
		//对用户发送的消息内容进行转义

		//获取到所有在线用户的SocketSession对象
		Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
		for (Entry<String, WebSocketSession> entry : entrySet) {
			//某用户的WebSocketSession
			final WebSocketSession webSocketSession = entry.getValue();
			//判断连接是否仍然打开的
			if(webSocketSession.isOpen()){
				//开启多线程发送消息（效率高）
				new Thread(new Runnable() {
					public void run() {
						try {
							if (webSocketSession.isOpen()) {
								webSocketSession.sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}).start();

			}
		}
	}

	public synchronized void xxx(User loginUser,WebSocketSession webSocketSession,String type,String code)
	{
		try {
		    	Message msg = new Message();
				//是房间的创建者
				if(type.equals("add"))
				{
					//先根据这个人 新建一个 对战平台出来   这个人是play1
					BattlePlatform battlePlatform=battlePlatformService.save(loginUser.getId(),"PKOnline");
					battlePlatformMap.put(battlePlatform.getBattleCode(),battlePlatform);//把当前新建的平台加到map中
					//下面这步是为了 一个用户断开   给另一个用户发消息  而不是给 所有人
					webSocketSession.getAttributes().put("playids",loginUser.getId());

					webSocketSession.getAttributes().put("battlePlatcode",battlePlatform.getBattleCode());
					//第一个玩家进来 就去找题目   这样第2个玩家进来就可以直接开始。没关系 有锁
					//得到题目的list(对象里面有答案)
					List<TestQuestions> qList=competitionOnlineService.getQuest();

					CompetitionOnline competitionOnline=competitionOnlineService.findAll2();
//						现在我要把它放onlinePk+id的形式存入   第一个user的id为准
//						redisUtil.set("onlinePk"+loginUser.getId(),qList);
					timuMap.put("onlinePk"+loginUser.getId(),qList);
					timussettingMap.put("onlinePksetting"+loginUser.getId(),competitionOnline);

					msg.setText("请等待 玩家加入");
					msg.setDate(new Date());
					msg.setBattlePlatform(battlePlatform);
					msg.setTo(loginUser.getId());
					msg.setNowtimu("0");
					msg.setBattleCode(battlePlatform.getBattleCode());
					msg.setCompetitionOnline(competitionOnline);
					//把题目塞到信息里面去往页面打
					msg.setTqList(qList);

					msg.getUserList().add(loginUser);
					TextMessage message = new TextMessage(GsonUtils.toJson(msg));
					//群发消息
//						sendMessageToAll(message);
					sendMessageToUser(loginUser.getId(),message);
				}

				//凭借邀请码加入房间的
				else if (type.equals("join"))
				{
					//找到这个比武台 然后把自己加到play2里面
					BattlePlatform battlePlatform=battlePlatformMap.get(code);
					//如果code码错误  提示 然后退出
					if(battlePlatform==null)
					{
						msg.setText("未找到对应房间");
						msg.setDate(new Date());
						TextMessage message = new TextMessage(GsonUtils.toJson(msg));
						sendMessageToUser(loginUser.getId(),message);
						webSocketSession.close();
					}
					//通过code找到了房间
					else
					{
						//找到这个比武台 然后把自己加到play2里面
						battlePlatformService.updata(battlePlatform,loginUser.getId());
						//添加完后要把这个房间在除去  防止其他人加进来
           				battlePlatformMap.remove(code);
						//下面这liang两步是为了 一个用户断开   给另一个用户发消息  而不是给 所有人
						webSocketSession.getAttributes().put("playids",battlePlatform.getPlay1()+","+loginUser.getId());//
						USER_SOCKETSESSION_MAP.get(battlePlatform.getPlay1()).getAttributes().put("playids",battlePlatform.getPlay1()+","+loginUser.getId());
			 		    msg.setText("玩家"+loginUser.getFullName()+"加入,欢迎。。。。。。。。。。");
         				msg.setDate(new Date());

		 				//把题目塞到信息里面去往页面打
		 				msg.setTqList(timuMap.get("onlinePk"+battlePlatform.getPlay1()));
		 				msg.setCompetitionOnline(timussettingMap.get("onlinePksetting"+battlePlatform.getPlay1()));
		  				msg.setNowtimu("0");
						msg.setBattleCode(code);
						msg.setBattlePlatform(battlePlatform);
		 				msg.setTo(battlePlatform.getPlay1()+","+loginUser.getId());//为了传到前端页面
		 				msg.getUserList().add(
		 						(User)USER_SOCKETSESSION_MAP.get(battlePlatform.getPlay1()).getAttributes().get("loginUser")
		 				);
		 				msg.getUserList().add(loginUser);
						TextMessage message = new TextMessage(GsonUtils.toJson(msg));
		 				//群发消息
		 				sendMessageToUsers(battlePlatform.getPlay1()+","+loginUser.getId(),message);//拼接接收人的id

					}
				}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
