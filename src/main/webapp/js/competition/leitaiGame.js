/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */

var datamag=null;
//回答过题目的人
var answerpeople=[];

var vm = new Vue({
    el: '#app',
    data: {
        coinNum: 123,
        ruleAreaShow: false,
        allnum:"",
        nownum:"",
        u:"",
        answers:"",
        //题目集合
        QuestionList:[
            // Question={id:""},
        ],
        //题目
        Question:{},
        radio_disabled: false,
        dialogQuestion:false,//开始答题  弹出
        BattleTopicSettings:[],//题目配置集合
        nowbattleTopicSetting:"",
        nowQscore:"",//当前题目分值
        //我的得分
         myscore:"0",
        //对手的得分
         youscore:"0",
        //对方是否已答题
        yesOrNoAnswer:"未答题",
        leizhu:"",//擂主
        jifen:"0",//最终录入的成绩积分
        isFighting: true,
        leitaiReady: true,//显示主页或擂台页面
        msg:"",
        play1:"",
        play2:"",
        winLeiTaiByUser:"",
        jifenByUser:"",
        successShow:false,
        failShow:false,
        winCoin:'',//奖励分数（无论成功失败）
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },


    methods: {
        goBack: function () {
            window.location.href = baseURL + "modules/competition/competeCenter.html"
        },
        radioCheck: function (id, answerId, typeName) {

            vm.radio_disabled = true;
            //如果答对了
            if(id==answerId)
            {
                vm.questionYes();
            }
            else
            {
                vm.questionError();
            }
            //不管答对答错  都要走保存题目的方法
            // vm.oryesorno();//答完题目入库保存记录
            //触发发送事件
            sendMsg();
        },
        //答错事件
        questionError:function()
        {
            vm.$message({
                message: '答错了',
                type: 'warning'
            });


        },
        //答对事件
        questionYes:function()
        {

                vm.myscore=Number(vm.myscore)+Number(vm.nowQscore);
            vm.$message({
                message: '答对了',
                type: 'success'
            });

        },


        rePlay: function () {
            window.location.reload()

        }
    }
});






var uid=null;
//发送人编号
var from=null;
//当前第几题  默认1
var nowtimu=0;
var fromName;
//接收人编号
//        var to="-1";
var to= null;
var matchSetting=null;
$.ajax({
    type: "POST",
    url: baseURL + "websocket/leitaiGame",
    dataType: "json",
    async:false,
    success: function (result) {

        vm.u=result.user;
        uid=result.user.id;
//发送人编号
        from=result.user.id;
        fromName=result.user.fullName;


        matchSetting=  result.matchSetting;//当前擂台配置，里面有擂主信息

        if(matchSetting==null)
        {
            vm.msg="占无题目配置，请联系管理员";
            vm.leizhu="";

        }
        else
            {
                vm.leizhu=result.matchSetting.winId;
                if(result.matchSetting.winId==null || result.matchSetting.winId=="")
                {
                    vm.$message({
                        message: '暂时无擂主,你已成为擂主',
                        type: 'success'
                    });
                    vm.leizhu="暂时无擂主";
                }
                else
                {
                    vm.$message({
                        message: "擂主为"+result.matchSetting.winName,
                        type: 'success'
                    });

                    if(result.matchSetting.winId==result.user.id)
                    {
                        vm.$message({
                            message: '欢迎擂主登陆',
                            type: 'success'
                        });
                    }
                    vm.leizhu=result.matchSetting.winName;

                    //通过擂主id去获取这个人的积分 与 擂台 次数
                    $.ajax({
                        type: "POST",
                        url: baseURL + "battleRecord/winLeiTaiCountByUserId?winId="+result.matchSetting.winId,
                        dataType: "json",
                        async: false,
                        success: function (result) {

                            vm.winLeiTaiByUser=result.winLeiTai;
                            vm.jifenByUser=result.jifen;
                        }
                    });

                }
            }



    }
});

//        console.info(to);
// 创建一个Socket实例
//参数为URL，ws表示WebSocket协议。onopen、onclose和onmessage方法把事件连接到Socket实例上。每个方法都提供了一个事件，以表示Socket的状态。
var websocket;
//获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
var curWwwPath = window.document.location.href;
//获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8080
var localhostPath = curWwwPath.substring(0, pos);
//获取带"/"的项目名，如：/ems
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
//获取项目的basePath   http://localhost:8080/ems/
var basePath=localhostPath+projectName+"/";
var path= basePath.substring(7);

//不同浏览器的WebSocket对象类型不同
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://" + path+ "Leitai");
    console.log("=============WebSocket");
    //火狐
} else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket("ws://" + path + "Leitai");
    console.log("=============MozWebSocket");
} else {
    websocket = new SockJS("http://" + path + "Leitai/sockjs");
    console.log("=============SockJS");
}


//打开Socket,
websocket.onopen = function(event) {
    console.log("WebSocket:已连接");
}

// 监听消息
//onmessage事件提供了一个data属性，它可以包含消息的Body部分。消息的Body部分必须是一个字符串，可以进行序列化/反序列化操作，以便传递更多的数据。
websocket.onmessage = function(event) {
    var data=$.parseJSON(event.data);
    console.log("WebSocket:收到一条消息",data);
    datamag=data;

    vm.QuestionList= data.tqList;
    //2种推送的消息
    //1.用户聊天信息：发送消息触发
    //2.系统消息：登录和退出触发
    //判断是否是欢迎消息（没用户编号的就是欢迎消息）
    if(data.from==undefined||data.from==null||data.from==""){

        // console.info("系统发的消息");
        // console.info("系统发的消息是"+data);
        //===系统消息
        $("#contentUl").append("<li><b>"+data.date+"</b><em>系统消息：</em><span>"+data.text+"</span></li>");
        vm.msg=data.text;


        //刷新在线用户列表
        $("#chatOnline").html("在线用户("+data.userList.length+")人");

        if(data.userList.length>1)
        {

            setTimeout(function(){
                vm.leitaiReady=false;

                $(data.userList).each(function(){
                    if(jsgetUser().fullName!=this.fullName)
                    {
                        vm.play2=this.fullName;
                    }
                    else
                    {
                        vm.play1=this.fullName;
                    }
                });


            },2000);

        }
        // 当收到系统消息的时候  然且当是在线2人的时候 这时候 默认给第一题
        if(data.userList.length=="2")
        {
            vm.dialogQuestion=true,
            vm.radio_disabled=false;
            vm.allnum=data.tqList.length;
            vm.nownum=Number(nowtimu)+1;
            vm.nowQscore=data.matchSetting.battleTopicSettingList[Number(nowtimu)].score;
            vm.nowbattleTopicSetting=data.matchSetting.battleTopicSettingList[Number(nowtimu)];
            vm.Question=data.tqList[Number(nowtimu)];
        }
        if(data.mycore!=undefined&&data.mycore!=null&&data.mycore!="")
        {
            // vm.jifen=data.mycore;
            //
            // // recordScoreFromTow(datamag.battlePlatform.id,vm.jifen,'leitai',data.to);
            //
            //
            if(vm.leizhu==uid)
            {
                //擂主不变
                vm.$message({
                    message: '对手弃权,守擂成功',
                    type: 'success'
                });
            }
            else
            {
                // 改变擂主   在Handler里改
                vm.$message({
                    message: '对手弃权，恭喜成为擂主',
                    type: 'success'
                });
            }




        }

        $("#chatUserList").empty();

        $(data.userList).each(function(){
            // console.info(this);
            $("#chatUserList").append("<li>"+this.fullName+"</li>");
        });
        //当收到消息的时候 给人赋值
        // console.info(" 收到系统消息，是给"+data.to);
        to=data.to;
    }else{


        // console.info("人发的消息");
        // console.info(data);
        //===普通消息
        //处理一下个人信息的显示：
        if(data.fromName==fromName){
            // data.fromName="我";   我发送的
            // $("#contentUl").append("<li><span  style='display:block; float:right;'><em>"+data.fromName+"</em><span>"+data.text+data.nowtimu+"</span><b>"+data.date+"</b></span></li><br/>");
        }else{
            //对手发送的
            // $("#contentUl").append("<li><b>"+data.date+"</b><em>"+data.fromName+"</em><span>"+data.text+data.nowtimu+"</span></li><br/>");
            vm.youscore=data.mycore;
            vm.yesOrNoAnswer="已答题";
        }
        answerpeople.push(data.from);
        // console.info("收到消息后"+answerpeople);
        //当受到普通消息是时候  判断发送人
        if(answerpeople.length=="2")
        {
            setTimeout(function(){
             answerpeople=[];//再将这个回答过的人制空
            //如果2人都回答过了
            // 题目要变
            //收到消息时候来变化题目，前提是2人回答过
            if(data.tqList.length <= Number(nowtimu))
            {
                if(Number(vm.myscore)==Number(vm.youscore))
                {
                    vm.$message({
                        message: '双方分数一样，擂主不变，平局，占不计入成绩表中',
                        type: 'success'
                    });
                }
                else if(Number(vm.myscore)<Number(vm.youscore))
                {
                    vm.jifen=Number(data.matchSetting.loserReward);
                    // recordScore(datamag.battlePlatform.id,'0',vm.jifen,'leitai',vm.u.id);
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'competitionOnline/recordScore',
                        dataType: "json",
                        async: false,
                        data: {"battlePlatformId":datamag.battlePlatform.id,"win":'0',"score":vm.jifen,"type":'leitai',"uid":vm.u.id},
                        success: function (result) {

                            vm.$message({
                                message:result.s,
                                type: 'success'
                            });
                            vm.failShow = true;
                            // vm.winCoin = data.matchSetting.loserReward;
                            vm.winCoin = result.s
                        }
                    });
                    // alert("全部题目答完");
                    closeWebsocket();

                }
                else if(Number(vm.myscore)>Number(vm.youscore))
                {
                    vm.jifen=Number(data.matchSetting.winReward);
                    // recordScore(datamag.battlePlatform.id,'1',vm.jifen,'leitai',vm.u.id);

                    $.ajax({
                        type: "POST",
                        url: baseURL + 'competitionOnline/recordScore',
                        dataType: "json",
                        async: false,
                        data: {"battlePlatformId":datamag.battlePlatform.id,"win":'1',"score":vm.jifen,"type":'leitai',"uid":vm.u.id},
                        success: function (result) {

                            vm.$message({
                                message:result.s,
                                type: 'success'
                            });
                            vm.successShow = true;
                            // vm.winCoin = data.matchSetting.winReward;
                            vm.winCoin = result.s;
                        }
                    });
                    if(vm.leizhu==uid)
                    {
                        // alert("全部题目答完");
                        // 擂主  获胜次数  累加
                        $.ajax({
                            type: "POST",
                            url: baseURL + "matchSetting/wincountjia?uid="+uid,
                            contentType: "application/json",
                            data: JSON.stringify(data.matchSetting),
                            async: false,
                            success: function (result) {

                            }
                        });
                        closeWebsocket();
                        //擂主不变

                    }
                    else
                    {
                        // 改变擂主
                        $.ajax({
                            type: "POST",
                            url: baseURL + "matchSetting/chuangLeizhu?uid="+uid,
                            contentType: "application/json",
                            data: JSON.stringify(data.matchSetting),
                            async: false,
                            success: function (result) {

                            }
                        });
                        // alert("全部题目答完");
                        closeWebsocket();
                        //擂主不变
                        // vm.successShow = true;
                        // vm.winCoin = data.matchSetting.winReward;
                    }



                }

            }else
            {
               vm.dialogQuestion=true;
               vm.radio_disabled=false;
               vm.allnum=data.tqList.length;
               vm.nownum=Number(nowtimu)+1;
                vm.nowQscore=data.matchSetting.battleTopicSettingList[Number(nowtimu)].score;

                vm.nowbattleTopicSetting=data.matchSetting.battleTopicSettingList[Number(nowtimu)];
                vm.Question=data.tqList[Number(nowtimu)];
                vm.yesOrNoAnswer="未答题";
            }

            }, 3000);
        }
    }
    scrollToBottom();
};

// 监听WebSocket的关闭
websocket.onclose = function(event) {
    console.info("连接已断开！");

    $("#contentUl").append("<li><b>"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</b><em>系统消息：</em><span>连接已断开！</span></li>");
    scrollToBottom();
    console.log("WebSocket:已关闭：Client notified socket has closed",event);
};

//监听异常
websocket.onerror = function(event) {
    console.info("连接异常，建议重新登录");
    $("#contentUl").append("<li><b>"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</b><em>系统消息：</em><span>连接异常，建议重新登录</span></li>");
    scrollToBottom();
    console.log("WebSocket:发生错误 ",event);
};


//onload初始化
$(function(){
    //给退出聊天绑定事件
    $("#exitBtn").on("click",function(){
        closeWebsocket();
        // location.href="login.jsp";
    });
    // 初始化时如果有消息，则滚动条到最下面：
    scrollToBottom();
});

//发送消息
function sendMsg(){
    //对象为空了
    if(websocket==undefined||websocket==null){
        this.$message.error('您的连接已经丢失，请退出聊天重新进入');
        return;
    }
    //获取用户要发送的消息内容
    // var msg=$("#msg").val();
    var msg="我答题了";
    if(msg==""){
        return;
    }else{
        nowtimu=nowtimu+1;
        console.info(datamag);

        var data={};
        data["from"]=from;
        data["fromName"]=fromName;
        data["to"]=to;
        data["text"]=msg;
        data["nowtimu"]=nowtimu;
        data["tqList"]=datamag.tqList;
        data["myanswer"]=vm.answers;
        data["tq"]=vm.Question;
        data["matchSetting"]=datamag.matchSetting;
        data["mycore"]=vm.myscore;
        data["youcore"]=vm.youscore;
        data["battlePlatform"]=datamag.battlePlatform;

        //发送消息
        websocket.send(JSON.stringify(data));
        //发送完消息，清空输入框
        // $("#msg").val("");
    }
}

function addSorce()
{
 //得分累加
    vm.myscore=vm.myscore+Number(vm.nowQscore)
}

//关闭Websocket连接
function closeWebsocket() {
    if (websocket != null) {
        websocket.close();
        websocket = null;
    }
}
//div滚动条(scrollbar)保持在最底部
function scrollToBottom(){
    //var div = document.getElementById('chatCon');
    var div = document.getElementById('up');
    div.scrollTop = div.scrollHeight;
}
//格式化日期
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}




function recordScore(battlePlatformId,win,score,type,uid)
{
    $.ajax({
        type: "POST",
        url: baseURL + 'competitionOnline/recordScore',
        dataType: "json",

        data: {"battlePlatformId":battlePlatformId,"win":win,"score":score,"type":type,"uid":uid},
        success: function (result) {

            alert(result.s);
        }
    });
}
function recordScoreFromTow(battlePlatformId,score,type,users)
{
    var userArray= users.split(",");

    for ( var i = 0; i <userArray.length; i++){

        if(userArray[i]==vm.u.id)
        {
            recordScore(battlePlatformId,'1',score,type,userArray[i]);
        }
        else
        {
            recordScore(battlePlatformId,'0','0',type,userArray[i]);
        }
    }
}
