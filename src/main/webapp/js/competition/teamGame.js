/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
        allnum:"",
        nownum:"",
        answers:"",
        //题目集合
        QuestionList:[
            // Question={id:""},
        ],
        //题目
        Question:{},
        battleCode:"",
        radio_disabled: false,
        // dialogQuestion:false,//开始答题  弹出
        BattleTopicSettings:[],//题目配置集合
        nowbattleTopicSetting:"",//当前题目配置
        nowQscore:"",//当前题目分值
        u:"",//人
        competitionTeam:"",//队伍信息
        teamtype:true,//组队和比赛模式下 不同展示的东西  （组队）
        teamtype2:false,//组队和比赛模式下 不同展示的东西  （比赛）
        dialogQuestion:false,//开始答题  弹出
        battlePlatform:{},//对战平台
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },


    methods: {
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            // var answer = vm.answers[0];
            // alert(vm.answers);
            // alert("我选的"+id);
            // alert("正确的"+answerId);
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
            // oryesorno();//答完题目入库保存记录
            //触发发送事件
            $("#msg").val("答题了");
            sendMsg();
        },
        //答错事件
        questionError:function()
        {
            alert("答错了")


        },
        //答对事件
        questionYes:function()
        {
            alert("答对了")

            // if(vm.nowbattleTopicSetting.whetherGetIntegral=="1")//这题能获得积分
            // {
            //     vm.myscore=Number(vm.myscore)+Number(vm.nowQscore);
            //     alert("答对了，获得本题积分");
            // }
            // else
            // {
            //     alert("答对了，本题不能获得积分");
            // }
        },
        reload:function () {

        },

    }
});




var peopleNum = getUrlParam('peopleNum');//几人队伍
var type = getUrlParam('type');//建房还是加入房
var code = getUrlParam('code');//房间code码
//回答过题目的人
var answerpeople=[];
var news=null;
//当前第几题  默认第1  排序0
var nowtimu=0;
var uid=null;
//发送人编号
var from=null;

var fromName;
//接收人编号
//        var to="-1";
var to= null;

$.ajax({
    type: "POST",
    url: baseURL + "websocket/pkTeam",
    dataType: "json",
    data:{"peopleNum":peopleNum,"type":type,"code":code},
    async:false,
    success: function (result) {
         vm.u=result.user;
        uid=result.user.id;
        //发送人编号
        from=result.user.id;
        fromName=result.user.fullName;
    }
});


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
    websocket = new WebSocket("ws://" + path+ "team");
    console.log("=============WebSocket");
    //火狐
} else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket("ws://" + path + "team");
    console.log("=============MozWebSocket");
} else {
    websocket = new SockJS("http://" + path + "team/sockjs");
    console.log("=============SockJS");
}


//打开Socket,
websocket.onopen = function(event) {
    console.log("WebSocket:已连接");
}

// 监听消息
//onmessage事件提供了一个data属性，它可以包含消息的Body部分。消息的Body部分必须是一个字符串，可以进行序列化/反序列化操作，以便传递更多的数据。
websocket.onmessage = function(event) {
    //var data=JSON.parse(event.data);
    var data=$.parseJSON(event.data);
    console.info("WebSocket:收到一条消息");
    console.info(data);
    news=data;
    if(data.teamOrGame=="1")//如果是进入到2队比赛环节了  要隐藏一些东西了  然后 放出一些东西了
    {
        vm.teamtype=false;
        vm.teamtype2=true;
    }
    vm.battlePlatform=data.battlePlatform;//将对战频台给vue
    vm.competitionTeam=data.competitionTeam;//将队伍信息给vue
    vm.battleCode=data.battleCode;
    vm.QuestionList= data.tqList;
    if(vm.u.id==vm.competitionTeam.userId)
    {
        $("#addroom").show();//按钮隐藏
        $("#joinroom").show();//按钮隐藏
    }

    //2种推送的消息
    //1.用户聊天信息：发送消息触发
    //2.系统消息：登录和退出触发

    //判断是否是欢迎消息（没用户编号的就是欢迎消息）
    if(data.from==undefined||data.from==null||data.from==""){

        // 当收到系统消息的时候  看看是不是两个队伍匹配上了  看to的人和队伍配置人的2倍相比
        if(Number(data.to.length)==Number(data.competitionTeam.nowScale)*2)
        {

            nowtimu=0;//因为下面send消息的时候 已经在不停的改变nowtimu的值  我要将他回归最初的状态

            vm.dialogQuestion=true,//有人加进来 才显示 题目
            vm.radio_disabled=false;
            vm.allnum=data.tqList.length;
            vm.nownum=Number(nowtimu)+1;
            vm.nowQscore=data.competitionOnline.battleTopicSettingList[Number(nowtimu)].score;
            vm.nowbattleTopicSetting=data.competitionOnline.battleTopicSettingList[Number(nowtimu)];
            vm.Question=data.tqList[Number(nowtimu)];
        }




        //===系统消息
        $("#contentUl").append("<li><b>"+data.date+"</b><em>系统消息：</em><span>"+data.text+"</span></li>");
        //刷新在线用户列表
        $("#chatOnline").html("队伍人员("+data.userList.length+")人");
        $("#chatOnline2").html("队伍人员("+data.userList2.length+")人");
        $("#chatUserList").empty();

        $(data.userList).each(function(){
            console.info(this);
            $("#chatUserList").append("<li>"+this.fullName+"</li>");
        });
        $("#chatUserList2").empty();

        $(data.userList2).each(function(){
            console.info(this);
            $("#chatUserList2").append("<li>"+this.fullName+"</li>");
        });
        //当收到消息的时候 给人赋值

        console.info(" 收到系统消息，是给"+data.to);
        to=data.to;

    }
    else   //人之间的发消息
    {

        if(data.teamOrGame=="1")//前提是 在游戏比赛环节  不是在比赛前的吹逼时候  是人发消息  与系统发来的消息 带过来的==1不同
        {
            answerpeople.push(data.from);
            if(answerpeople.length==data.to.length)
            {
                //如果2人都回答过了
                // 题目要变
                //收到消息时候来变化题目，前提是2人回答过
                if(data.tqList.length <= Number(nowtimu))
                {
                    alert("全部题目答完");
                    closeWebsocket();
                }
                else
                {
                    vm.dialogQuestion=true,
                    vm.radio_disabled=false;
                    vm.allnum=data.tqList.length;
                    vm.nownum=Number(nowtimu)+1;
                    // vm.nowQscore=data.competitionOnline.battleTopicSettingList[Number(nowtimu)].score;
                    vm.nowbattleTopicSetting=data.competitionOnline.battleTopicSettingList[Number(nowtimu)];
                    vm.Question=data.tqList[Number(nowtimu)];
                    // vm.yesOrNoAnswer="未答题";
                }
                answerpeople=[];//再将这个回答过的人制空
            }
        }


        //===普通消息
        //处理一下个人信息的显示：
        if(data.fromName==fromName){
            data.fromName="我";
            $("#contentUl").append("<li><span  style='display:block; float:right;'><em>"+data.fromName+"</em><span>"+data.text+"</span><b>"+data.date+"</b></span></li><br/>");
        }else{
            $("#contentUl").append("<li><b>"+data.date+"</b><em>"+data.fromName+"</em><span>"+data.text+"</span></li><br/>");
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

     document.getElementById("div1").innerHTML=getUrlParam('peopleNum')+"人队";

    $("#addroom").hide();//按钮隐藏
    $("#joinroom").hide();//按钮隐藏
    $("#joinroomDiv").hide();//按钮隐藏
    //发送消息
    $("#sendBtn").on("click",function(){
        sendMsg();
    });

    //给退出聊天绑定事件
    $("#exitBtn").on("click",function(){
        closeWebsocket();
        location.href="main.html";
    });

    //给输入框绑定事件
    $("#msg").on("keydown",function(event){
        keySend(event);
    });


    //初始化时如果有消息，则滚动条到最下面：
    scrollToBottom();
});




//addroom 创建比赛房间事件
$("#addroom").on("click",function(){
    //先看能不能点  人不齐 不能点
    if(news.competitionTeam.nowScale!=news.competitionTeam.scale)
    {
        //人不齐  不可以点了
        alert("人不齐");
        return;
    }
    else
    {
        //人齐了 ，走send发消息这一步  告诉handler
        $("#msg").val("addroom");
        news.teamOrGame="1";//下面就是要到比赛去了;
        sendMsg();
    }


});
//joinroom 加入比赛房间事件
$("#joinroom").on("click",function(){

    $("#joinroomDiv").show();//按钮隐藏
});

//点击进入code房间事件
$("#joinroomButton").on("click",function(){
    var roomcode=$("#joinroomCode").val();
    if(roomcode=="")
    {
        alert("请输入房间code码");
        return;
    }
    else
    {
        //先看能不能点  人不齐 不能点
        if(news.competitionTeam.nowScale!=news.competitionTeam.scale)
        {
            //人不齐  不可以点了
            alert("人不齐");
            return;
        }
        else
        {
            $("#joinroomDiv").hide();//div隐藏
            //人齐了 ，走send发消息这一步  告诉handler
            $("#msg").val("joinroom");
            news.teamOrGame="1";//下面就是要到比赛去了;
            news.battleCode=roomcode;
            sendMsg();
        }
    }




});
//使用ctrl+回车快捷键发送消息
function keySend(e) {
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (theEvent.ctrlKey && code == 13) {
        var msg=$("#msg");
        if (msg.innerHTML == "") {
            msg.focus();
            return false;
        }
        sendMsg();
    }
}

//发送消息
function sendMsg(){
    //对象为空了
    if(websocket==undefined||websocket==null){
        //alert('WebSocket connection not established, please connect.');
        alert('您的连接已经丢失，请退出聊天重新进入');
        return;
    }
    //获取用户要发送的消息内容
    var msg=$("#msg").val();
    if(msg==""){
        return;
    }else{
        nowtimu=nowtimu+1;
        var data={};
        data["from"]=from;
        data["fromName"]=fromName;
        data["to"]=news.to;
        data["text"]=msg;
        data["competitionTeam"]=news.competitionTeam;
        data["teamOrGame"]=news.teamOrGame;
        data["battlePlatform"]=news.battlePlatform;
        data["battleCode"]=news.battleCode;

        data["nowtimu"]=nowtimu;
        data["tqList"]=news.tqList;
        data["myanswer"]=vm.answers;
        data["tq"]=vm.Question;
        data["competitionOnline"]=news.competitionOnline;
        //发送消息
        websocket.send(JSON.stringify(data));
        //发送完消息，清空输入框
        $("#msg").val("");
    }
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


//不管答对答错 都要入库方法
function oryesorno() {
    //数据格式问题  把这两个时间值为空
    vm.Question.tuIsstim="";
    vm.Question.stuIsstim="";
    console.info(vm.Question)
    $.ajax({
        type: "POST",
        url: baseURL + 'competitionOnline/saveQuestion?myanswer='+vm.answers,
        contentType: "application/json",
        async:false,
        data: JSON.stringify(vm.Question),
        success: function (result) {
        }
    });


}
