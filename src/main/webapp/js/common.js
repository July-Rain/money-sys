/**
 * Author: crain
 * Date: 2018/12/3
 * Description:公共方法
 */
var baseURL = contextPath + "/";

//上传图片和PDF格式、大小验证
function checkFile(file) {
    var index1 = file.name.lastIndexOf(".");
    var index2 = file.name.length;
    var nameinfo = file.name.substring(index1 + 1, index2);//后缀名
    var extension1 = nameinfo.toLowerCase() === 'jpg';
    var extension2 = nameinfo.toLowerCase() === 'png';
    var extension3 = nameinfo.toLowerCase() === 'pdf';
    var extension4 = nameinfo.toLowerCase() === 'jpeg';
    var extension5 = nameinfo.toLowerCase() === 'gif';
    var extension6 = nameinfo.toLowerCase() === 'bmp';
    var isLt2M = file.size / 1024 / 1024 < 500;
    if (!extension1 && !extension2 && !extension3 && !extension4 && !extension5 && !extension6) {
        alert('上传模板只能是图片和pdf格式!')
    }
    if (!isLt2M) {
        alert('上传模板大小不能超过 500MB!')
    }
    return extension1 || extension2 || extension3 || extension4 || extension5 || extension6 && isLt2M
}

// 获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
/**
 * 获取指定的URL参数值
 * URL:http://www.quwan.com/index?name=tyler
 * 参数：paramName URL参数
 * 调用方法:getParam("name")
 * 返回值:tyler
 */
function getParam(paramName) {
    paramValue = "", isFound = !1;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
    }
    return paramValue == "" && (paramValue = null), paramValue
}
//格式化时间为年月日
function formatTime(scope,cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        //cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
        var date = new Date(cellvalue);
        console.log(date);
        console.log(date.getMonth())
        var month = date.getMonth() + 1;

        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }

        currentdate = date.getFullYear() + "年" + month + "月" + strDate + "日";
    }

    return currentdate;
}


function openWindow(url, title, w, h) {
    var iWidth = w;
    var iHeight = h;
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
    myWindow  = window.open( url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}
