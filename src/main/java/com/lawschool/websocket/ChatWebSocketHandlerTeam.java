package com.lawschool.websocket;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.MessageByTeam;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.CompetitionTeam;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.service.competition.CompetitionTeamService;
import com.lawschool.service.competition.TeamUserService;
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
	@Autowired
	private CompetitionOnlineService competitionOnlineService;
	@Autowired
	private TeamUserService teamUserService;

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

	public static final Map<String, List> timuMap;
	public static final Map<String, CompetitionOnline> timussettingMap;

	//根据战队code纯共用的计数器
    public static final Map<String, Integer> count;
	//回答次数计数器 最后与题目数量对比 判断是不是全部答完题目后退出的‘
	public static final Map<String, Integer> answerCount;//计数器
	//存储所有的在线用户
	static {
		USER_SOCKETSESSION_MAP = new HashMap<String, WebSocketSession>();
		USER_TEAM= new HashMap<String, CompetitionTeam>();
		USER_ids=new HashMap<String, List>();
		USER_us=new HashMap<String, List>();
		battlePlatformMap=new HashMap<String,BattlePlatform>();
		timuMap=new HashMap<String,List>();
		timussettingMap=new HashMap<String,CompetitionOnline>();
        count=new HashMap<String,Integer>();
		answerCount=new HashMap<String,Integer>();
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
                //设置共用count
                count.put(competitionTeam.getTeamCode(),0);

				answerCount.put(loginUser.getId(),0);//一进来设置0

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

						//teamUserService  将人加入到战队人员表中
						teamUserService.save(competitionTeam.getId(),loginUser.getId());


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

						answerCount.put(loginUser.getId(),0);//一进来设置0
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

        if( (msg.getMyanswer()!=null && !"".equals(msg.getMyanswer())) && (msg.getTq()!=null))
        {
            competitionOnlineService.saveQuestion(msg.getTq(),msg.getMyanswer(),msg.getFrom(),"teamOnline");
        }

		if(!"addroom".equals(msg.getText()) && !"joinroom".equals(msg.getText()) && msg.getTeamOrGame().equals("1")) //当触发新建房间事件
		{
			answerCount.put(msg.getFrom(),answerCount.get(msg.getFrom())+1);
		}

		if(msg.getText().equals("addroom") && msg.getTeamOrGame().equals("1")) //当触发新建房间事件
		{
			//这时候要建房间了。有这个按钮的权限必定是房主，用这个人的id就好他要不要线程呢。 要把
			new Thread(new Runnable() {
				public  void run() {
					addroom(msg);
				}
			}).start();
		}
		else if(msg.getText().equals("joinroom") && msg.getTeamOrGame().equals("1")) //当触发新建房间事件
		{
			//这时候要建房间了。有这个按钮的权限必定是房主，用这个人的id就好他要不要线程呢。 要把
			new Thread(new Runnable() {
				public  void run() {
					joinroom(msg);
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

			//题目
			//第一个玩家进来 就去找题目   这样第2个玩家进来就可以直接开始。没关系 有锁
			//得到题目的list(对象里面有答案)
			List<TestQuestions> qList=competitionOnlineService.getQuest();

			CompetitionOnline competitionOnline=competitionOnlineService.findAll2();
//			现在我要把它存入 因为是双方都需要去找到的  所以 用双方都能取到的属性 对战平台的code码为key
			timuMap.put(battlePlatform.getBattleCode(),qList);
			timussettingMap.put(battlePlatform.getBattleCode(),competitionOnline);


			USER_TEAM.remove(competitionTeam.getTeamCode());//进入匹配了  将退伍信息去掉了
			//发消息
			msg.setFrom(null);//设置为系统发消息
			msg.setText("");
			msg.setDate(new Date());
			msg.setBattlePlatform(battlePlatform);
			msg.setBattleCode(battlePlatform.getBattleCode());
			msg.setNowtimu("0");
			msg.setBattleCode(battlePlatform.getBattleCode());
			msg.setCompetitionOnline(competitionOnline);
			msg.setUserList(USER_us.get(competitionTeam.getTeamCode()));
			//把题目塞到信息里面去往页面打
			msg.setTqList(qList);
			TextMessage message = new TextMessage(GsonUtils.toJson(msg));
			sendMessageToUserByList(msg.getTo(),new TextMessage(GsonUtils.toJson(msg)));

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public synchronized void joinroom(MessageByTeam msg)
	{
		try
		{
			//先根据这个人找到战队
			CompetitionTeam competitionTeam=msg.getCompetitionTeam();
			//要加入了哦
			//首先肯定是 通过code 去找平台
			String battleCode=msg.getBattleCode();
			//找平台
			BattlePlatform battlePlatform=battlePlatformMap.get(battleCode);
			if(battlePlatform==null)//根据code码没找到
			{
				msg.setFrom(null);//设置为系统发消息
				msg.setText("未找到房间,重新输入");
				msg.setDate(new Date());
				msg.setTeamOrGame("0");
				TextMessage message = new TextMessage(GsonUtils.toJson(msg));
				sendMessageToUserByList(msg.getTo(),new TextMessage(GsonUtils.toJson(msg)));
			}
			else
			{
				//找到了
				CompetitionTeam Team1 = competitionTeamService.selectById(battlePlatform.getPlay1());

//				Team1.getScale();//得到人家的是几人队
//				msg.getCompetitionTeam().getScale();//自己是几人队
				if(msg.getCompetitionTeam().getScale().equals(Team1.getScale()))//是同一种队伍
				{
					//找到这个比武台 然后把自己加到play2里面
					battlePlatformService.updata(battlePlatform,competitionTeam.getId());
					//添加完后要把这个房间在除去  防止其他人加进来
					battlePlatformMap.remove(battleCode);

					USER_TEAM.remove(competitionTeam.getTeamCode());//进入匹配了  将退伍信息去掉了
					//发消息
					msg.setFrom(null);//设置为系统发消息
					msg.setText("");
					msg.setDate(new Date());
					msg.setBattlePlatform(battlePlatform);
					msg.setBattleCode(battlePlatform.getBattleCode());
					//把题目塞到信息里面去往页面打
					msg.setTqList(timuMap.get(battlePlatform.getBattleCode()));
					msg.setCompetitionOnline(timussettingMap.get(battlePlatform.getBattleCode()));
					msg.setNowtimu("0");

					msg.setUserList2(USER_us.get(msg.getCompetitionTeam().getTeamCode()));
					msg.setUserList(USER_us.get(Team1.getTeamCode()));

					msg.getTo().addAll(USER_ids.get(Team1.getTeamCode()));
					TextMessage message = new TextMessage(GsonUtils.toJson(msg));
					sendMessageToUserByList(msg.getTo(),new TextMessage(GsonUtils.toJson(msg)));
				}
				else
				{
					msg.setFrom(null);//设置为系统发消息
					msg.setText("不是同一种类型队伍，请重新加入");
					msg.setDate(new Date());
					msg.setTeamOrGame("0");
					TextMessage message = new TextMessage(GsonUtils.toJson(msg));
					sendMessageToUserByList(msg.getTo(),new TextMessage(GsonUtils.toJson(msg)));
				}


			}


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

		
	}

	@Override
	/**
     * websocket链接关闭的回调
     * 连接关闭后：一般是回收资源等
     */
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

        //得到队伍code码
        String teamCode=(String)webSocketSession.getAttributes().get("TeamCode");

        if(count.get(teamCode)==0)
        {
            count.put(teamCode,count.get(teamCode)+1);//+1

                // 记录日志，准备关闭连接
                System.out.println("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");
                //获取异常的用户的会话中的用户编号
                User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
                //群发消息告知大家
                MessageByTeam msg = new MessageByTeam();
                msg.setDate(new Date());
                //成员离开------队长离开  队伍解散

                //找队伍信息
                CompetitionTeam competitionTeam = USER_TEAM.get(teamCode);



                // 判断是比赛前还是比赛中途包括比赛后退出是事件

                if(competitionTeam==null)//说明是在游戏环节退出的
                {

					//不管什么队长不队长的  只要退出了就发消息给所有人  并让其他人都 下线

					//根据teamcode  找这个人的队伍信息   code码页数唯一的  可以取到
					CompetitionTeam Team=competitionTeamService.selectOne(new EntityWrapper<CompetitionTeam>().eq("TEAM_CODE",teamCode));
					//这个id可能是play1也可能是play2 但是不会有重复的数据 队伍是每次生成的 id是不重复

					BattlePlatform battlePlatform=	battlePlatformService.selectOne(new EntityWrapper<BattlePlatform>().eq("PLAY1",Team.getId()).or().eq("PLAY2",Team.getId()));

					//1。teamcode   2.team 有了
					//如果battlePlatform 的play2 没有值   只是单纯的给队友发消息 说XXX走了 队伍解散
					//如果battlePlatform 的play2 有值  判断大家是不是都回答过题目   根据这个条件 来判断是不是偷跑
					if(battlePlatform.getPlay2()==null)
					{
						//给队友发消息 说xxx走了
						List<String> ids=USER_ids.get(teamCode);
						for(String id:ids)
						{
							if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
							{
								WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
								msg.setText(loginUser.getFullName()+"离开,游戏结束");
								TextMessage message = new TextMessage(GsonUtils.toJson(msg));
								sendMessageToUser(id,message);
								SocketSession.close();
							}
							USER_SOCKETSESSION_MAP.remove(id);//移除这些人
							answerCount.remove(id);
						}
						//清除静态变量
						USER_TEAM.remove(teamCode);
						USER_ids.remove(teamCode);
						USER_us.remove(teamCode);
						count.remove(teamCode);
						battlePlatformMap.remove(battlePlatform.getBattleCode());
						timuMap.remove(battlePlatform.getBattleCode());
						timussettingMap.remove(battlePlatform.getBattleCode());

					}
					else
					{
						//在成功匹配上了 其他队伍
						//定义个状态位
						 int i=0;
						String team1=  battlePlatform.getPlay1();
						String team2=  battlePlatform.getPlay2();

						List<String> ids1=USER_ids.get((competitionTeamService.selectById(team1)).getTeamCode());
						for(String id:ids1)
						{
							if(answerCount.get(id)!=timuMap.get(battlePlatform.getBattleCode()).size())
							{
								i=i+1;
							}
						}
						List<String> ids2=USER_ids.get((competitionTeamService.selectById(team2)).getTeamCode());
						for(String id:ids2)
						{
							if(answerCount.get(id)!=timuMap.get(battlePlatform.getBattleCode()).size())
							{
								i=i+1;
							}
						}
						if(i==0)
						{
							count.put((competitionTeamService.selectById(team1)).getTeamCode(),count.get((competitionTeamService.selectById(team1)).getTeamCode())+1);
							count.put((competitionTeamService.selectById(team2)).getTeamCode(),count.get((competitionTeamService.selectById(team2)).getTeamCode())+1);
							//大家一起答完题目 一起愉快的结束   所有人发消息说
							List<String> idsA=USER_ids.get((competitionTeamService.selectById(team1)).getTeamCode());
							for(String id:idsA)
							{
								if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
								{
									WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
									msg.setText("游戏结束");
									TextMessage message = new TextMessage(GsonUtils.toJson(msg));
									sendMessageToUser(id,message);
									SocketSession.close();
								}
								USER_SOCKETSESSION_MAP.remove(id);//移除这些人
								answerCount.remove(id);
							}
							List<String> idsB=USER_ids.get((competitionTeamService.selectById(team2)).getTeamCode());
							for(String id:idsB)
							{
								if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
								{
									WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
									msg.setText("游戏结束");
									TextMessage message = new TextMessage(GsonUtils.toJson(msg));
									sendMessageToUser(id,message);
									SocketSession.close();
								}
								USER_SOCKETSESSION_MAP.remove(id);//移除这些人
								answerCount.remove(id);
							}
							//清除静态变量
							USER_TEAM.remove(teamCode);
							USER_ids.remove(teamCode);
							USER_us.remove(teamCode);
							count.remove(teamCode);
							battlePlatformMap.remove(battlePlatform.getBattleCode());
							timuMap.remove(battlePlatform.getBattleCode());
							timussettingMap.remove(battlePlatform.getBattleCode());
						}
						else if(i!=0)//说明偷跑
						{


							competitionOnlineService.recordScore(battlePlatform.getId(),"0","0","teamOnline",loginUser.getId());
							count.put((competitionTeamService.selectById(team1)).getTeamCode(),count.get((competitionTeamService.selectById(team1)).getTeamCode())+1);
							count.put((competitionTeamService.selectById(team2)).getTeamCode(),count.get((competitionTeamService.selectById(team2)).getTeamCode())+1);

							List<String> idsA=USER_ids.get((competitionTeamService.selectById(team1)).getTeamCode());

							for(String id:idsA)
							{

								if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
								{
									WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
									if(idsA.contains(loginUser.getId())==true)
									{
										competitionOnlineService.recordScore(battlePlatform.getId(),"0","0","teamOnline",id);
										msg.setText(loginUser.getFullName()+"离开,很遗憾，游戏结束");
										msg.setJifen(timussettingMap.get(battlePlatform.getBattleCode()).getLoserReward());
										msg.setStrus("0");//队友跑了
									}
									else
									{
										competitionOnlineService.recordScore(battlePlatform.getId(),"1",timussettingMap.get(battlePlatform.getBattleCode()).getWinReward(),"teamOnline",id);
										msg.setText(loginUser.getFullName()+"离开,恭喜你，游戏结束");
										msg.setJifen(timussettingMap.get(battlePlatform.getBattleCode()).getWinReward());
										msg.setStrus("1");//对方跑了
									}

									msg.setBattlePlatform(battlePlatform);
									TextMessage message = new TextMessage(GsonUtils.toJson(msg));
									sendMessageToUser(id,message);
									SocketSession.close();
								}
								USER_SOCKETSESSION_MAP.remove(id);//移除这些人
								answerCount.remove(id);
							}
							List<String> idsB=USER_ids.get((competitionTeamService.selectById(team2)).getTeamCode());
							for(String id:idsB)
							{
								if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
								{
									WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
									if(idsB.contains(loginUser.getId())==true)
									{
										competitionOnlineService.recordScore(battlePlatform.getId(),"0","0","teamOnline",id);

										msg.setText(loginUser.getFullName()+"离开,很遗憾，游戏结束");
										msg.setJifen(timussettingMap.get(battlePlatform.getBattleCode()).getLoserReward());
										msg.setStrus("0");//队友跑了
									}
									else
									{
										competitionOnlineService.recordScore(battlePlatform.getId(),"1",timussettingMap.get(battlePlatform.getBattleCode()).getWinReward(),"teamOnline",id);

										msg.setText(loginUser.getFullName()+"离开,恭喜你，游戏结束");
										msg.setJifen(timussettingMap.get(battlePlatform.getBattleCode()).getWinReward());
										msg.setStrus("1");//对方跑了
									}

									msg.setBattlePlatform(battlePlatform);
									TextMessage message = new TextMessage(GsonUtils.toJson(msg));
									sendMessageToUser(id,message);
									SocketSession.close();
								}
								USER_SOCKETSESSION_MAP.remove(id);//移除这些人
								answerCount.remove(id);
							}
							//清除静态变量
							USER_TEAM.remove(teamCode);
							USER_ids.remove(teamCode);
							USER_us.remove(teamCode);
							count.remove(teamCode);
							battlePlatformMap.remove(battlePlatform.getBattleCode());
							timuMap.remove(battlePlatform.getBattleCode());
							timussettingMap.remove(battlePlatform.getBattleCode());
						}
						i=0;
					}

//
//
//
//
//
//                    //什么不想  先去找这个队的id   用id来反查比武平台实体
//
//                    //play1必定有值；play2不一定有值   因为 在play1等待阶段也会有人退出，这时候play2没加进来  ，要记得把好多东西删掉
//                    CompetitionTeam TeamPlay1=competitionTeamService.selectById(battlePlatform.getPlay1());
//                    USER_SOCKETSESSION_MAP.remove(loginUser.getId());//先移除这个人
//                    //得到整个战队人员id 去遍历发消息与退出
//                    List<String> ids=USER_ids.get(TeamPlay1.getTeamCode());
//                    for(String id:ids)
//                    {
//                        if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
//                        {
//                            WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
//                            msg.setText(loginUser.getFullName()+"离开,游戏结束");
//                            TextMessage message = new TextMessage(GsonUtils.toJson(msg));
//                            sendMessageToUser(id,message);
//                            SocketSession.close();
//                        }
//                    }
//                    //清除静态变量
//                    USER_TEAM.remove(TeamPlay1.getTeamCode());
//                    USER_ids.remove(TeamPlay1.getTeamCode());
//                    USER_us.remove(TeamPlay1.getTeamCode());
//                    count.remove(TeamPlay1.getTeamCode());
//                    battlePlatformMap.remove(battlePlatform.getBattleCode());
//                    timuMap.remove(battlePlatform.getBattleCode());
//                    timussettingMap.remove(battlePlatform.getBattleCode());
//                    if(battlePlatform.getPlay2()==null)//如果没有玩家2  结束
//                    {
//
//                    }
//                    else
//                    {
//
//                        CompetitionTeam TeamPlay2=competitionTeamService.selectById(battlePlatform.getPlay2());
//						count.put(TeamPlay2.getTeamCode(),count.get(TeamPlay2.getTeamCode())+1);
//                        //得到整个战队人员id 去遍历发消息与退出
//                        List<String> ids2=USER_ids.get(TeamPlay2.getTeamCode());
//                        for(String id:ids2)
//                        {
//                            if(!id.equals(loginUser.getId()))//要不等于当前退出人，已经退出了
//                            {
//                                WebSocketSession SocketSession=USER_SOCKETSESSION_MAP.get(id);
//                                msg.setText(loginUser.getFullName()+"离开,游戏结束");
//                                TextMessage message = new TextMessage(GsonUtils.toJson(msg));
//                                sendMessageToUser(id,message);
//                                SocketSession.close();
//                            }
//                        }
//
//                        count.remove(TeamPlay2.getTeamCode());
//                        //清除静态变量
//                        USER_TEAM.remove(TeamPlay2.getTeamCode());
//                        USER_ids.remove(TeamPlay2.getTeamCode());
//                        USER_us.remove(TeamPlay2.getTeamCode());
//                    }

                    //remove掉一切关系东西


                }
                else//没进入游戏  还在吹逼阶段
                {
                    //判断当前离开人是不是队长
                    if(loginUser.getId().equals(competitionTeam.getUserId()))
                    {
                        //如果是队长，整个战队成员退出
                        //先移除队长
                        USER_SOCKETSESSION_MAP.remove(loginUser.getId());
                        //得到整个战队人员id 去遍历发消息与退出
                        List<String> ids=USER_ids.get(teamCode);
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

                        count.remove(teamCode);
                        competitionTeam.setStatus("0");
                        competitionTeamService.updateById(competitionTeam);//改变状态位
                        //清除静态变量
                        USER_TEAM.remove(teamCode);
                        USER_ids.remove(teamCode);
                        USER_us.remove(teamCode);
						answerCount.remove(loginUser.getId());
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
                        //将人重战队中删去

                        teamUserService.deleteTeamUser(competitionTeam.getId(),loginUser.getId());
                        msg.setText(loginUser.getFullName()+"离开");
                        msg.setDate(new Date());
                        msg.getTo().addAll(ids);
                        msg.getUserList().addAll(us);
                        msg.setCompetitionTeam(competitionTeam);
                        //将消息转换为json
                        TextMessage message = new TextMessage(GsonUtils.toJson(msg));
                        //群发消息
                        sendMessageToUserByList(msg.getTo(),message);
						answerCount.remove(loginUser.getId());
                        count.put(teamCode,0);//+1
                    }
                }




        }
        else
        {

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
