/**
 * Author: crain
 * Date: 2018/12/3
 * Description:公共方法
 */
var baseURL = contextPath + "/";
var colorList = ['#52c9e7','#3e98e8','#81bdd8','#5ebd5c','#feae24','#f97a1f','#f26443','#b97deb','#7e72f2','#4f7ee9'];
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
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
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
function formatTime(scope, cellvalue) {
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
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;           //获得窗口的水平位置;
    myWindow = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}


function jsgetUser() {
    var u = null;
    $.ajax({
        type: "POST",
        url: baseURL + "sys/jsGetUser",
        dataType: "json",
        async: false,
        success: function (result) {
            u = result.u;
        }
    });
    return u;
}

function getBreadcrumb(menuId) {
    var breadArr = [];
    $.ajax({
        type: "POST",
        url: baseURL + "sysmenu/getParent?id=" + menuId,
        dataType: "json",
        async: false,
        success: function (result) {
            breadArr = result.parentList;
        }
    });
    return breadArr;
}
function getUser() {
    //获取当前登陆人信息
    var user ={userName:"",userId:""};
    $.ajax({
        type: "POST",
        url: baseURL + "sys/getuser",
        dataType: "json",
        async: false,
        success: function (result) {
            user = result.user;
        }
    });
    return user;
}

//- 小写数字转换成大写
function ArabiSimplified(Num) {
    for (var i = Num.length - 1; i >= 0; i--) {
        Num = Num.replace(",", "")//替换Num中的“,”
        Num = Num.replace(" ", "")//替换Num中的空格
    }
    if (isNaN(Num)) { //验证输入的字符是否为数字
        //alert("请检查小写金额是否正确");
        return;
    }
    //字符处理完毕后开始转换，采用前后两部分分别转换
    var part = String(Num).split(".");
    var newchar = "";
    //小数点前进行转化
    for (var i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) {
            //alert("位数过大，无法计算");
            return "";
        }//若数量超过拾亿单位，提示
        tmpnewchar = ""
        perchar = part[0].charAt(i);
        switch (perchar) {
            case "0":  tmpnewchar = "零" + tmpnewchar;break;
            case "1": tmpnewchar = "一" + tmpnewchar; break;
            case "2": tmpnewchar = "二" + tmpnewchar; break;
            case "3": tmpnewchar = "三" + tmpnewchar; break;
            case "4": tmpnewchar = "四" + tmpnewchar; break;
            case "5": tmpnewchar = "五" + tmpnewchar; break;
            case "6": tmpnewchar = "六" + tmpnewchar; break;
            case "7": tmpnewchar = "七" + tmpnewchar; break;
            case "8": tmpnewchar = "八" + tmpnewchar; break;
            case "9": tmpnewchar = "九" + tmpnewchar; break;
        }
        switch (part[0].length - i - 1) {
            case 0: tmpnewchar = tmpnewchar; break;
            case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
            case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
            case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
            case 4: tmpnewchar = tmpnewchar + "万"; break;
            case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
            case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
            case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
            case 8: tmpnewchar = tmpnewchar + "亿"; break;
            case 9: tmpnewchar = tmpnewchar + "十"; break;
        }
        newchar = tmpnewchar + newchar;
    }
    //替换所有无用汉字，直到没有此类无用的数字为止
    while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
        newchar = newchar.replace("零亿", "亿");
        newchar = newchar.replace("亿万", "亿");
        newchar = newchar.replace("零万", "万");
        newchar = newchar.replace("零零", "零");
    }
    //替换以“一十”开头的，为“十”
    if (newchar.indexOf("一十") == 0) {
        newchar = newchar.substr(1);
    }
    //替换以“零”结尾的，为“”
    if (newchar.lastIndexOf("零") == newchar.length - 1) {
        newchar = newchar.substr(0, newchar.length - 1);
    }
    return newchar;
}
// 兼容ie的动画
function animation(_function,fps){
    var now;
    var then = Date.now();
    var interval = 1000/fps;
    var delta;
    window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;

    function tick() {
        console.log("tick")
        if(window.requestAnimationFrame)
        {
            requestAnimationFrame(tick);
            now = Date.now();
            delta = now - then;
            if (delta > interval) {
                // 这里不能简单then=now，否则还会出现上边简单做法的细微时间差问题。例如fps=10，每帧100ms，而现在每16ms（60fps）执行一次draw。16*7=112>100，需要7次才实际绘制一次。这个情况下，实际10帧需要112*10=1120ms>1000ms才绘制完成。
                then = now - (delta % interval);
                _function(); // ... Code for Drawing the Frame ...
            }
        }
        else
        {
            setTimeout(tick, interval);
            _function();
        }
    }
    tick()
}

/**
 * 获取指定正则
 * 参数：type 正则类型
 *      phone:手机号码
 *      password:密码
 *      id:身份证号
 *      number:数字
 *      chinese:中文
 *      character:字母或数字或下划线
 *      menuUrl:菜单管理正则（modules/开头 html结尾
 * 调用方法:regularExp("phone")
 * 返回值:正则表达式
 */
function regularExp(type) {
    switch(type)
    {
        case "phone":
            return "^1[345789]\\d{9}$";
            break;
        case "password":
            return "^[0-9A-Za-z]{6,20}$";
            break;
        case "id":
            return "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
            break;
        case "number":
            return "^[0-9]*$";
            break;
        case "chinese":
            return "^[\u4e00-\u9fa5]{0,}$";
            break;
        case "character":
            return "^[\u4E00-\u9FA5A-Za-z0-9_]+$";
            break;
        case "menuUrl":
            return "^modules\\/.*?html$";
            break;
        default:
    }
}
// ECharts - 绘制图表
function echartsOption(myChart, option) {
    // this[chartName].clear()
    myChart.setOption(option)
    window.addEventListener('resize', function () {
        myChart.resize()
    })
    // this[chartName].hideLoading() // Chart提示关闭
}
// 到首页
function toHome() {
    parent.location.reload()
}
// 数组去重
function unique(arr){

    var res = [arr[0]];

    for(var i=1;i<arr.length;i++){

        var repeat = false;

        for(var j=0;j<res.length;j++){

            if(arr[i] == res[j]){

                repeat = true;

                break;

            }

        }

        if(!repeat){

            res.push(arr[i]);

        }

    }

    return res;

}
// 冒泡排序
function arrSort(arr) {
    var t;
    for(var i=0;i<arr.length;i++){
        for(var j=i+1;j<arr.length;j++){
            if(arr[i]>arr[j]){
                t=arr[i];
                arr[i]=arr[j];
                arr[j]=t;
            }
        }
    }
    return arr
}