package com.lawschool.websocket;

import com.lawschool.beans.MessageByTeam;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionTeam;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.service.competition.CompetitionTeamService;
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
 * @author 传智.BoBo老师
 * @version 1.0
 * @date 2016年10月27日
 */
@Component("chatWebSocketHandlerteam")
public class ChatWebSocketHandlerTeam implements WebSocketHandler {

	@Autowired
	private CompetitionTeamService competitionTeamService;
	@Autowired
	private BattlePlatformService battlePlatformService;
	//在线用户的SOCKETsession(存储了所有的通信通道)
	public static final Map<String, WebSocketSession> USER_SOCKETSESSION_MAP;

    //在线的队伍  根据队伍的code
	public static final Map<String, CompetitionTeam> USER_TEAM;

	//在线的队伍  根据队伍的code 存队伍人员的id
	public static final Map<String, List> USER_ids;
	//在线的队伍  根据队伍的code 存队伍人员的user
	public static final Map<String, List> USER_us;
	//在线的比武台  根据比武台的code
	public static final Map<String, BattlePlatform> battlePlatformMap;

	//存储所有的在线用户
	static {
		USER_SOCKETSESSION_MAP = new HashMap<String, WebSocketSession>();
		USER_TEAM= new HashMap<String, CompetitionTeam>();
		USER_ids=new HashMap<String, List>();
		USER_us=new HashMap<String, List>();
		battlePlatformMap=new HashMap<String,BattlePlatform>();
	}
	
	/**
	 * webscoket建立好链接之后的处理函数--连接建立后的准备工作
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		//将当前的连接的用户会话放入MAP,key是用户编号
		User loginUser=(User) webSocketSession.getAttributes().get("loginUser");
		String peopleNum=(String) webSocketSession.getAttributes().get("peopleNum");
		String type=(String) webSocketSession.getAttributes().get("joinType");
		String code=(String) webSocketSession.getAttributes().get("joinCode");
		USER_SOCKETSESSION_MAP.put(loginUser.getId(), webSocketSession);
		new Thread(new Runnable() {
			public  void run() {
				xxx(webSocketSession,loginUser,peopleNum,type,code);
			}
		}).start();
	}
	public synchronized void xxx(WebSocketSession webSocketSession,User loginUser,String peopleNum,String type,String code)
	{
		try {

			MessageByTeam msg = new MessageByTeam();
			//如果是新增
			if(type.equals("add")) {
				//一上来是要建立队伍，而不是建房间  这个是时候要根据这个人建个team出来
				CompetitionTeam competitionTeam = competitionTeamService.insertNewTeam(loginUser, peopleNum);
				//把队伍共享下  根据队伍code
				USER_TEAM.put(competitionTeam.getTeamCode(), competitionTeam);
				//把队伍人员的id存下
				List<String> uid=new ArrayList<String>();
				uid.add(loginUser.getId());
				USER_ids.put(competitionTeam.getTeamCode(),uid);
				//把队伍人员的实体存下
				List<User> u=new ArrayList<User>();
				u.add(loginUser);
				USER_us.put(competitionTeam.getTeamCode(),u);
				//将战队的code码放到通道中
				webSocketSession.getAttributes().put("TeamCode",competitionTeam.getTeamCode());
				msg.setText("请等待 队友加入");
				msg.setDate(new Date());
				msg.getTo().add(loginUser.getId());
				msg.getUserList().add(loginUser);
				msg.setCompetitionTeam(competitionTeam);
				msg.setTeamOrGame("0");
				//将消息转换为json
				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
				//群发消息
				sendMessageToUserByList(msg.getTo(),message);
			}
			//如果是加入
			else if(type.equals("join"))
			{
				//首先找队伍
				CompetitionTeam competitionTeam=USER_TEAM.get(code);
				if(competitionTeam==null)
				{
					//没找到
					msg.setText("未找到队伍,请退出重试");
					msg.setDate(new Date());
					//将消息转换为json
					TextMessage message = new TextMessage(GsonUtils.toJson(msg));
					//发消息给自己
					sendMessageToUser(loginUser.getId(),message);
					//然后断开
					webSocketSession.close();
				}
				else
				{
					//找到队伍了
					//判断队伍人员有没有满
					if(Integer.parseInt(competitionTeam.getNowScale())>=Integer.parseInt(competitionTeam.getScale()))
					{
						//如果当前人员大于等于实际人员，人满 不能进
						msg.setText("队伍已满");
						msg.setDate(new Date());
						//将消息转换为json
						TextMessage message = new TextMessage(GsonUtils.toJson(msg));
						//发消息给自己
						sendMessageToUser(loginUser.getId(),message);
						//然后断开
						webSocketSession.close();
					}
					else if(Integer.parseInt(competitionTeam.getNowScale())<Integer.parseInt(competitionTeam.getScale()))
					{
						//队伍人没满 加入队伍
						//先把队伍的 当前人员数+1
						competitionTeam.setNowScale((Integer.parseInt(competitionTeam.getNowScale())+1)+"");
						competitionTeamService.updateById(competitionTeam);
						//去找这个队伍有哪些人id
						List<String> uids= USER_ids.get(competitionTeam.getTeamCode());
						uids.add(loginUser.getId());
						//再将这个list放回静态变量里
						USER_ids.put(competitionTeam.getTeamCode(),uids);
						//去找这个队伍有哪些人
						List<User> us=USER_us.get(competitionTeam.getTeamCode());
						us.add(loginUser);
						//再将这个list放回静态变量里
						USER_us.put(competitionTeam.getTeamCode(),us);
						//将战队的code码放到通道中
						webSocketSession.getAttributes().put("TeamCode",competitionTeam.getTeamCode());
						msg.setText("玩家"+loginUser.getFullName()+"加入,欢迎。。。。。。。。。。");
						msg.setDate(new Date());
						msg.getTo().addAll(uids);
						msg.setTeamOrGame("0");
						msg.getUserList().addAll(us);
						msg.setCompetitionTeam(competitionTeam);
						//将消息转换为json
						TextMessage message = new TextMessage(GsonUtils.toJson(msg));
						//群发消息
						sendMessageToUserByList(msg.getTo(),message);

					}
				}
			}

		}
		catch (Exception e) {
			System.out.println(e);
		}
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
		MessageByTeam msg = GsonUtils.fromJson(message.getPayload().toString(), MessageByTeam.class);
		if(msg.getText().equals("addroom") && msg.getTeamOrGame().equals("1")) //当触发新建房间事件
		{
			//这时候要建房间了。有这个按钮的权限必定是房主，用这个人的id就好他要不要线程呢。 要把
			new Thread(new Runnable() {
				public  void run() {
					addroom(msg);
				}
			}).start();
		}
		else//正常的发送消息等
		{
			msg.setDate(new Date());
			//处理html的字符，转义：
			String text = msg.getText();
			//转换为HTML转义字符表示
			String htmlEscapeText = HtmlUtils.htmlEscape(text);
			msg.setText(htmlEscapeText);
			System.out.println("消息（可存数据库作为历史记录）:"+message.getPayload().toString());
			//判断是群发还是单发
	//		if(msg.getTo()==null||msg.getTo().equals("-1")){
	//			//群发
	//			sendMessageToAll(new TextMessage(GsonUtils.toJson(msg)));
	//		}else{
	//			//单发
	//			sendMessageToUser(msg.getTo().get(0), new TextMessage(GsonUtils.toJson(msg)));
	//		}
			sendMessageToUserByList(msg.getTo(),new TextMessage(GsonUtils.toJson(msg)));
		}
	}

	public synchronized void addroom(MessageByTeam msg)
	{
		try
		{
			//先创建房间
			//先根据这个人找到战队
			CompetitionTeam competitionTeam=msg.getCompetitionTeam();
			//先根据这战队 新建一个 对战平台出来   这play1 就是战队的id
			BattlePlatform battlePlatform=battlePlatformService.save(competitionTeam.getId(),"teamOnline");
			//把当前新建的平台加到map中，用平台的cosd码
			battlePlatformMap.put(battlePlatform.getBattleCode(),battlePlatform);
			//找到这个队长的sockersession
			WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(msg.getFrom());
			//将平台code给队长
			SocketSession.getAttributes().put("battlePlatcode",battlePlatform.getBattleCode());

			//先不找题目  后面再说

			//发消息
			msg.setFrom(null);//设置为系统发消息
			msg.setText("请等待 其他队伍加入.。。。。。。。。");
			msg.setDate(new Date());
			msg.setBattlePlatform(battlePlatform);
			msg.setBattleCode(battlePlatform.getBattleCode());
//			msg.setUserList2(null);
			TextMessage message = new TextMessage(GsonUtils.toJson(msg));
			sendMessageToUserByList(msg.getTo(),new TextMessage(GsonUtils.toJson(msg)));

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public synchronized void joinroom()
	{
		try
		{

		}
		catch (Exception e) {
			System.out.println(e);
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
//		MessageByTeam msg = new MessageByTeam();
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
//			msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
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
		//获取异常的用户的会话中的用户编号
		User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
		//群发消息告知大家
		MessageByTeam msg = new MessageByTeam();
		msg.setDate(new Date());
		//成员离开------队长离开  队伍解散
		//得到队伍code码
		 String teamCode=(String)webSocketSession.getAttributes().get("TeamCode");
		//找队伍信息
		CompetitionTeam competitionTeam = USER_TEAM.get(teamCode);
		//判断当前离开人是不是队长
		if(loginUser.getId().equals(competitionTeam.getUserId()))
		{
			//如果是队长，整个战队成员退出
			//先移除队长
			USER_SOCKETSESSION_MAP.remove(loginUser.getId());
			//得到整个战队人员id 去遍历发消息与退出
		    List<String> ids=	USER_ids.get(teamCode);
			for(String id:ids)
			{
				if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
				{
					WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
					msg.setText("队长"+loginUser.getFullName()+"离开,队伍解散。");
					TextMessage message = new TextMessage(GsonUtils.toJson(msg));
					sendMessageToUser(id,message);
					SocketSession.close();
				}
			}

			competitionTeam.setStatus("0");
			competitionTeamService.updateById(competitionTeam);//改变状态位
			//清除静态变量
			 USER_TEAM.remove(teamCode);
 			 USER_ids.remove(teamCode);
			 USER_us.remove(teamCode);
		}

		else
		{
			//不是队长离开 是成员离开   只他一人走
		   //需要将USER_ids，和USER_us 中的他删除
			List<String> ids=	USER_ids.get(teamCode);
			List<User> us=	USER_us.get(teamCode);
			Iterator<String> it=ids.iterator();
			while (it.hasNext())
			{
				if(it.next().equals(loginUser.getId()))
				{
					it.remove();
				}
			}
			Iterator<User> it2=us.iterator();
			while (it2.hasNext())
			{
				if(it2.next().equals(loginUser))
				{
					it2.remove();
				}
			}
			USER_ids.put(teamCode,ids);
			USER_us.put(teamCode,us);

			competitionTeam.setNowScale((Integer.parseInt(competitionTeam.getNowScale())-1)+"");
			competitionTeamService.updateById(competitionTeam);//改变状态位

			msg.setText(loginUser.getFullName()+"离开");
			msg.setDate(new Date());
			msg.getTo().addAll(ids);
			msg.getUserList().addAll(us);
			msg.setCompetitionTeam(competitionTeam);
			//将消息转换为json
			TextMessage message = new TextMessage(GsonUtils.toJson(msg));
			//群发消息
			sendMessageToUserByList(msg.getTo(),message);

		}

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
	 * 说明：给指定的一堆人发消息

	 * @param message
	 * @author 传智.BoBo老师
	 * @throws IOException
	 * @time：2016年10月27日 下午10:40:52
	 */
	private void sendMessageToUserByList(List<String> ids, TextMessage message) throws IOException{
		//获取到要接收消息的用户的session
		for(String id:ids)
		{
			WebSocketSession webSocketSession = USER_SOCKETSESSION_MAP.get(id);
			if (webSocketSession != null && webSocketSession.isOpen()) {
				//发送消息
				webSocketSession.sendMessage(message);
			}
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
	
}
