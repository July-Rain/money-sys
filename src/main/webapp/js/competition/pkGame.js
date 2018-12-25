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
        nowQscore:"",//当前题目分值
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
            // vm.oryesorno();//答完题目入库保存记录
            //触发发送事件
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

        },


        reload: function () {


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
var code=null;
$.ajax({
    type: "POST",
    url: baseURL + "websocket/pkAloneByRandom",
    dataType: "json",
    async:false,
    success: function (result) {
        vm.u=result.user;
        uid=result.user.id;
//发送人编号
        from=result.user.id;
        fromName=result.user.fullName;
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
    websocket = new WebSocket("ws://" + path+ "ws");
    console.log("=============WebSocket");
    //火狐
} else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket("ws://" + path + "ws");
    console.log("=============MozWebSocket");
} else {
    websocket = new SockJS("http://" + path + "ws/sockjs");
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

        console.info("系统发的消息");
        console.info("系统发的消息是"+data);
        //===系统消息
        $("#contentUl").append("<li><b>"+data.date+"</b><em>系统消息：</em><span>"+data.text+"</span></li>");
        //刷新在线用户列表
        $("#chatOnline").html("在线用户("+data.userList.length+")人");

        // 当收到系统消息的时候  然且当是在线2人的时候 这时候 默认给第一题
        if(data.userList.length=="2")
        {
            vm.dialogQuestion=true,
            vm.radio_disabled=false;
            vm.allnum=data.tqList.length;
            vm.nownum=Number(nowtimu)+1;
            vm.nowQscore=data.competitionOnline.battleTopicSettingList[Number(nowtimu)].score;
            vm.Question=data.tqList[Number(nowtimu)];
        }

        $("#chatUserList").empty();

        $(data.userList).each(function(){
            console.info(this);
            $("#chatUserList").append("<li>"+this.fullName+"</li>");
        });

        //当收到消息的时候 给人赋值

        console.info(" 收到系统消息，是给"+data.to);
        to=data.to;

    }else{
        
        console.info("人发的消息");
        console.info(data);
        answerpeople.push(data.from);
        console.info("收到消息后"+answerpeople);
        //当受到普通消息是时候  判断发送人
        if(answerpeople.length=="2")
        {
            //如果2人都回答过了
            // 题目要变
            //收到消息时候来变化题目，前提是2人回答过
            if(data.tqList.length <= Number(nowtimu))
            {
                alert("全部题目答完");
            }else
            {

               vm.dialogQuestion=true,
               vm.radio_disabled=false;
               vm.allnum=data.tqList.length;
               vm.nownum=Number(nowtimu)+1;
                vm.nowQscore=data.competitionOnline.battleTopicSettingList[Number(nowtimu)].score;
                vm.Question=data.tqList[Number(nowtimu)];
            }

            answerpeople=[];//再将这个回答过的人制空

        }
        //===普通消息
        //处理一下个人信息的显示：
        // if(data.fromName==fromName){
        //     data.fromName="我";
        //     $("#contentUl").append("<li><span  style='display:block; float:right;'><em>"+data.fromName+"</em><span>"+data.text+data.nowtimu+"</span><b>"+data.date+"</b></span></li><br/>");
        // }else{
        //     $("#contentUl").append("<li><b>"+data.date+"</b><em>"+data.fromName+"</em><span>"+data.text+data.nowtimu+"</span></li><br/>");
        // }

    }

    scrollToBottom();
};

// 监听WebSocket的关闭
websocket.onclose = function(event) {
    $("#contentUl").append("<li><b>"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</b><em>系统消息：</em><span>连接已断开！</span></li>");
    scrollToBottom();
    console.log("WebSocket:已关闭：Client notified socket has closed",event);
};

//监听异常
websocket.onerror = function(event) {
    $("#contentUl").append("<li><b>"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</b><em>系统消息：</em><span>连接异常，建议重新登录</span></li>");
    scrollToBottom();
    console.log("WebSocket:发生错误 ",event);
};


//onload初始化
$(function(){
    //发送消息
    // $("#sendBtn").on("click",function(){
    //     sendMsg();
    // });

    //给退出聊天绑定事件
    $("#exitBtn").on("click",function(){
        closeWebsocket();
        // location.href="login.jsp";
    });

    //给输入框绑定事件
    // $("#msg").on("keydown",function(event){
    //     keySend(event);
    // });

    // 初始化时如果有消息，则滚动条到最下面：
    scrollToBottom();

});
//使用ctrl+回车快捷键发送消息
// function keySend(e) {
//     var theEvent = window.event || e;
//     var code = theEvent.keyCode || theEvent.which;
//     if (theEvent.ctrlKey && code == 13) {
//         var msg=$("#msg");
//         if (msg.innerHTML == "") {
//             msg.focus();
//             return false;
//         }
//         sendMsg();
//     }
// }

//发送消息
function sendMsg(){
    console.info("我发消息了,是给"+to);
    //对象为空了
    if(websocket==undefined||websocket==null){
        //alert('WebSocket connection not established, please connect.');
        alert('您的连接已经丢失，请退出聊天重新进入');
        return;
    }
    //获取用户要发送的消息内容
    // var msg=$("#msg").val();
    var msg="我答提了";
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
        data["competitionOnline"]=datamag.competitionOnline;
        //发送消息
        websocket.send(JSON.stringify(data));
        //发送完消息，清空输入框
        // $("#msg").val("");
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


