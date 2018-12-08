<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>在线聊天系统</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/sun-jsp/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/sun-jsp/js/lbt.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sun-jsp/css/style.css" type="text/css"/>
    <script type="text/javascript">

    </script>

</head>
<body>
<!--顶部开始-->
<div class="top">
    <div class="inTop">
        <p>传智播客旗下品牌：<a href="http://www.itheima.com/" target="_blank">黑马程序员</a>|<a href="http://v.itcast.cn/" target="_blank">博学谷</a></p>
        <span>改变中国IT教育，我们正在行动<strong>全国咨询热线：400-618-4000</strong></span>
    </div>
</div>
<!--顶部end-->
<!--头部开始-->
<div class="header">
    <h1>聊天室系统</h1>
</div>
<!--头部end-->
<!--登陆区域开始-->
<div class="loginMain">
    <div class="con">
        <div class="inCon">
            <ul class="imgList">
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/1.png" width="680" height="494" /></li>
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/2.png" width="680" height="494" /></li>
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/3.png" width="680" height="494" /></li>
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/4.png" width="680" height="494" /></li>
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/5.png" width="680" height="494" /></li>
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/6.png" width="680" height="494" /></li>
                <li><img src="${pageContext.request.contextPath}/sun-jsp/images/7.png" width="680" height="494" /></li>
            </ul>
        </div>
        <ol class="btnList">
            <li class="current"></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ol>
        <a href="javascript:;" class="left"></a>
        <a href="javascript:;" class="right"></a>   </div>


    <div class="loginArea">
        <h2>欢迎登陆</h2>
        <p>欢迎您来到聊天空间！</p>

        <form action="${pageContext.request.contextPath }/socket/login"  method="post">
            <input type="text" value="请输入您想显示的昵称" name="fullName" id="myText" />
            <button>进入聊天室</button>
        </form>
    </div>

<!--登陆区域结束-->


</body>
<script type="text/javascript">
    var myText=document.getElementById('myText');
    myText.onfocus=function(){
        if(myText.value=='请输入您想显示的昵称'){
            myText.value='';
            myText.style.color='#333';
        }
    }
    myText.onblur=function(){
        if(myText.value==''){
            myText.value='请输入您想显示的昵称';
            myText.style.color='#ccc';
        }
    }

</script>
</html>