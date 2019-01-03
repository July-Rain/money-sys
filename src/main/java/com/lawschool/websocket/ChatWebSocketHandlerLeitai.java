package com.lawschool.websocket;


import com.lawschool.beans.Message;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.service.UserService;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.service.competition.MatchSettingService;
import com.lawschool.util.GsonUtils;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * 说明：WebSocket处理器
 * @author
 * @version 1.0
 * @date
 */
@Component("chatWebSocketHandlerLeitai")
public class ChatWebSocketHandlerLeitai implements WebSocketHandler {
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private BattlePlatformService battlePlatformService;

	@Autowired
	private UserService userService;

	@Autowired
	private MatchSettingService matchSettingService;

	//在线用户的SOCKETsession(存储了所有的通信通道)
	public static final Map<String, WebSocketSession> USER_SOCKETSESSION_MAP;

	public static final Map<String, List> timuMap;

	public static final Map<String, MatchSetting> timussettingMap;

	public static final Map<String, BattlePlatform> battlePlatformMap;


	//存储所有的在线用户
	static {
		USER_SOCKETSESSION_MAP = new HashMap<String, WebSocketSession>();

		timuMap=new HashMap<String,List>();

		timussettingMap=new HashMap<String,MatchSetting>();

		battlePlatformMap=new HashMap<String,BattlePlatform>();
	}

	/**
	 * webscoket建立好链接之后的处理函数--连接建立后的准备工作
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		//将当前的连接的用户会话放入MAP,key是用户编号
		User loginUser=(User) webSocketSession.getAttributes().get("loginUser");
//		MatchSetting matchSetting=(MatchSetting) webSocketSession.getAttributes().get("matchSetting");


		USER_SOCKETSESSION_MAP.put(loginUser.getId(), webSocketSession);
		/**
		 *去redis中取redisFromBattlePlatList  对战平台的集合   先去看看里面有没有人在创建频台
		 */
		new Thread(new Runnable() {
			public  void run() {
				xxx(loginUser,webSocketSession);
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
			matchSettingService.saveQuestion(msg.getTq(),msg.getMyanswer(),msg.getFrom());
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
			msg.setText(loginUser.getFullName()+"已经离开了,直接获得获胜者奖励"+timussettingMap.get("leitaisetting"+playids.split(",")[0]).getWinReward());
			msg.setMycore(timussettingMap.get("leitaisetting"+playids.split(",")[0]).getWinReward());

			if(loginUser.getId().equals(playids.split(",")[0]))
			{
				msg.getUserList().add((User)USER_SOCKETSESSION_MAP.get(playids.split(",")[1]).getAttributes().get("loginUser"));//现在在线人员只有 没掉线的哪一个了
				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
				sendMessageToUser(playids.split(",")[1],message);//给另一个人发消息
				USER_SOCKETSESSION_MAP.get(playids.split(",")[0]).close();

				//启动换人
				matchSettingService.updateWin(timussettingMap.get("leitaisetting"+playids.split(",")[0]),loginUser.getId());
				USER_SOCKETSESSION_MAP.get(playids.split(",")[1]).close();
			}
			else
			{

				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
				msg.getUserList().add((User)USER_SOCKETSESSION_MAP.get(playids.split(",")[0]).getAttributes().get("loginUser"));//现在在线人员只有 没掉线的哪一个了
				sendMessageToUser(playids.split(",")[0],message);//给另一个人发消息不发消息
				USER_SOCKETSESSION_MAP.get(playids.split(",")[1]).close();
				//启动换人
				matchSettingService.updateWin(timussettingMap.get("leitaisetting"+playids.split(",")[0]),loginUser.getId());
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
			battlePlatformMap.remove(webSocketSession.getAttributes().get("battlePlatId"));
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

	public synchronized void xxx(User loginUser,WebSocketSession webSocketSession)
	{
		try {
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
		    Message msg = new Message();
			MatchSetting matchSetting=matchSettingService.findAll2();

			//假如现在是有擂主的
		    	if(matchSetting.getWinId()!=null)
		    	{
		    		//看当前登陆人是不是擂主      如果当前登陆人是擂主
					if(loginUser.getId().equals(matchSetting.getWinId()))
					{
						//开房，等人来
						//先根据这个人 新建一个 对战平台出来   这个人是play1
						BattlePlatform battlePlatform=battlePlatformService.save(loginUser,"leitai");
						battlePlatformMap.put(battlePlatform.getId(),battlePlatform);//把当前新建的平台加到map中   id为主键
						//下面这步是为了 一个用户断开   给另一个用户发消息  而不是给 所有人
						webSocketSession.getAttributes().put("playids",loginUser.getId());
						webSocketSession.getAttributes().put("battlePlatId",battlePlatform.getId());


						//第一个玩家进来 就去找题目   这样第2个玩家进来就可以直接开始。没关系 有锁
						//得到题目的list(对象里面有答案)
						List<TestQuestions> qList=matchSettingService.getQuest();

//						现在我要把它放onlinePk+id的形式存入   第一个user的id为准
//						redisUtil.set("onlinePk"+loginUser.getId(),qList);
						timuMap.put("leitai"+loginUser.getId(),qList);
						timussettingMap.put("leitaisetting"+loginUser.getId(),matchSetting);
						msg.setText("请擂主等待 挑战者加入");
						msg.setDate(new Date());
						msg.setBattlePlatform(battlePlatform);
						msg.setTo(loginUser.getId());
						msg.setNowtimu("0");
						msg.setMatchSetting(matchSetting);
						//把题目塞到信息里面去往页面打
						msg.setTqList(qList);
						msg.getUserList().add(loginUser);
						TextMessage message = new TextMessage(GsonUtils.toJson(msg));
						sendMessageToUser(loginUser.getId(),message);
					}
					//如果登陆人不是擂主  首先 看擂主在不在线咯
					else
					{
						//得到擂主的id
					    String leizhuId=matchSetting.getWinId();
					    //判断在不在咯
						final WebSocketSession webSocket =  USER_SOCKETSESSION_MAP.get(leizhuId);
						if(webSocket==null)//如果不在线
						{
							System.out.println("擂主不在线");

							//判断擂主是否过期

							Calendar c = Calendar.getInstance();
							c.setTime(matchSetting.getLastTime());
							c.add(Calendar.DAY_OF_MONTH, 1);
							Date invalidTiem = c.getTime();//得到擂主最后一次登陆 加1天的时间
							String strTime = sdf.format(invalidTiem);//格式化时间
							if (invalidTiem.before(new Date()))
							{
								//过期时间大于当前时间，换人
								msg.setText("擂主当前不再线，期限为"+strTime+",现在你是擂主了，请重新进入");
								msg.setDate(new Date());
								TextMessage message = new TextMessage(GsonUtils.toJson(msg));
								sendMessageToUser(loginUser.getId(),message);

								//启动换人
								matchSettingService.updateWin(matchSetting,loginUser.getId());

								webSocketSession.close();

							}
							else
							{
								//表示过期时间小于当前时间
								msg.setText("擂主当前不再线，擂主最后期限为"+strTime);
								msg.setDate(new Date());
								TextMessage message = new TextMessage(GsonUtils.toJson(msg));
								sendMessageToUser(loginUser.getId(),message);
								webSocketSession.close();

							}
					    }
						else
						{
							System.out.println("擂主在线");
							//我要加入擂主的房间了
							//找到这个比武台 然后把自己加到play2里面
							BattlePlatform battlePlatform=battlePlatformMap.get(webSocket.getAttributes().get("battlePlatId"));
							if(battlePlatform==null)
							{
								msg.setText("擂主正在战斗，请等待");
								msg.setDate(new Date());
								TextMessage message = new TextMessage(GsonUtils.toJson(msg));
								sendMessageToUser(loginUser.getId(),message);
								webSocketSession.close();
							}
							else
							{
								//找到这个比武台 然后把自己加到play2里面
								battlePlatformService.updata(battlePlatform,loginUser.getId());
								//添加完后要把这个房间在除去  防止其他人加进来
								battlePlatformMap.remove(battlePlatform.getId());
								//下面这liang两步是为了 一个用户断开   给另一个用户发消息  而不是给 所有人
								webSocketSession.getAttributes().put("playids",battlePlatform.getPlay1()+","+loginUser.getId());//
								USER_SOCKETSESSION_MAP.get(battlePlatform.getPlay1()).getAttributes().put("playids",battlePlatform.getPlay1()+","+loginUser.getId());
								msg.setText("玩家"+loginUser.getFullName()+"加入,欢迎。。。。。。。。。。");
								msg.setDate(new Date());
								msg.setBattlePlatform(battlePlatform);
								//把题目塞到信息里面去往页面打
								msg.setTqList(timuMap.get("leitai"+battlePlatform.getPlay1()));
								msg.setMatchSetting(timussettingMap.get("leitaisetting"+battlePlatform.getPlay1()));
								msg.setNowtimu("0");

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
					}

				}
				//现在是没有擂主的
				else
				{

					msg.setText("当前占无擂主，你已成为擂主，请先退出");
					msg.setDate(new Date());
					TextMessage message = new TextMessage(GsonUtils.toJson(msg));
					sendMessageToUser(loginUser.getId(),message);

					//启动换人
					matchSettingService.updateWin(matchSetting,loginUser.getId());

					webSocketSession.close();
				}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
