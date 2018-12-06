//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = contextPath + "/";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false
});
// $.ajaxSetup({
//     dataType: "json",
// 	cache: false,
//     complete:function(XMLHttpRequest,textStatus){
//         if(textStatus=="parsererror"){
//             alert("登陆超时！请重新登陆！",function(){
//                 window.location.href = 'login';
//             });
//         } else if(textStatus=="error"){
//             alert("请求超时！请稍后再试！");
//         }
//     }
// })

// 用正则实现html转码
function htmlEncodeByRegExp(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&/g, "&amp;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    return s;
};

/*用正则实现html解码*/
function htmlDecodeByRegExp(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&amp;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&nbsp;/g, " ");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    return s;
}

// 男女--1、2
function sexFn(sex) {
    return sex == 1 ? "男" : "女";
}

//获取url中"?"符后的字串
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

// 重写alert
window.alert = function (msg, callback) {
    // parent.layer.alert(msg, function(index){
    //     parent.layer.close(index);
    //     if(typeof(callback) === "function"){
    //         callback("ok");
    //     }
    // });

    parent.layer.confirm(msg, {
        title: '提示',
        btn: ['确定'] //按钮
        , cancel: function (index, layero) {
            //取消操作，点击右上角的X
            if (typeof(callback) === "function") {
                callback("ok");
            }
            parent.layer.close(index);
        }
    }, function (index) {
        //确定
        if (typeof(callback) === "function") {
            callback("ok");
        }
        parent.layer.close(index);
    });
};

// 重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {title: '提示', btn: ['确定', '取消']},
        function (index) {//确定事件
            var reg = new RegExp("PageOffice");
            var result = reg.test(this.content);

            if (typeof(callback) === "function") {
                callback("ok");
            } else if (result) {
                window.location.href = baseURL + "/posetup.exe"
            }
            layer.close(index);
        });

}


function openWindow(url, title, w, h) {
    var iWidth = w;
    var iHeight = h;
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;           //获得窗口的水平位置;
    myWindow = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}

//选择多条记录
function getSelectedRows(obj) {
    var grid = $("#jqGrid");
    if (obj) {
        grid = obj;
    }
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}


//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();       //获取当前小时数(0-23)
    var minute = date.getMinutes();     //获取当前分钟数(0-59)
    var second = date.getSeconds();     //获取当前秒数(0-59)
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate + " " + hour + seperator2 + minute + seperator2 + second;

    return currentdate;
}

//获取当前时间
function formatNowFormatDate(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        console.log(cellvalue);
        if(typeof (cellvalue) ==  "string"){
            cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/");
        }
        var date = new Date(cellvalue);        var seperator1 = "-";
        var seperator2 = ":";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hour = date.getHours();       //获取当前小时数(0-23)
        var minute = date.getMinutes();     //获取当前分钟数(0-59)
        var second = date.getSeconds();     //获取当前秒数(0-59)
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if (hour >= 0 && hour <= 9) {
            hour = "0" + hour;
        }
        if (minute >= 0 && minute <= 9) {
            minute = "0" + minute;
        }
        if (second >= 0 && second <= 9) {
            second = "0" + second;
        }
        currentdate = year + seperator1 + month + seperator1 + strDate + " " + hour + seperator2 + minute + seperator2 + second;
    }

    return currentdate;
}

//获取当前时间
function getNFDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();       //获取当前小时数(0-23)
    var minute = date.getMinutes();     //获取当前分钟数(0-59)
    var second = date.getSeconds();     //获取当前秒数(0-59)
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }
    var currentdate = year + "年" + month + "月" + strDate + "日 " + hour + "时" + minute + "分" + second + "秒";

    return currentdate;
}

//获取当前时间
function getNowYmd() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();

    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + "年" + month + "月" + strDate + "日";

    return currentdate;
}

//获取当前时间
function formatNowFormatDateYmd(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/");
        var date = new Date(cellvalue);
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        currentdate = year + seperator1 + month + seperator1 + strDate;
    }

    return currentdate;
}

//打开一个新的窗体
function open_win(url, title, w, h) {

    var iWidth = w;
    var iHeight = h;
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;           //获得窗口的水平位置;
    myWindow = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}


//验证消息弹窗
//格式化时间为年月日
function formatTime(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
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

//格式化时间为年月
function formatTimeMonth(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
        var date = new Date(cellvalue);
        var month = date.getMonth() + 1;

        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }

        currentdate = date.getFullYear() + "年" + month + "月";
    }

    return currentdate;
}

//格式化时间为年月日时分
function formatTimeDetail(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
        var date = new Date(cellvalue);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hour = date.getHours();       //获取当前小时数(0-23)
        var minute = date.getMinutes();     //获取当前分钟数(0-59)
        /*if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if (hour >= 0 && hour <= 9) {
            hour = "0" + hour;
        }
        if (minute >= 0 && minute <= 9) {
            minute = "0" + minute;
        }*/
        currentdate = year + "年" + month + "月" + strDate + "日" + hour + "时" + minute + "分";
    }

    return currentdate;
}
//格式化时间为年月日时分秒
function formatTimeAll(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
        var date = new Date(cellvalue);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hour = date.getHours();       //获取当前小时数(0-23)
        var minute = date.getMinutes();     //获取当前分钟数(0-59)
        var seconds=date.getSeconds();    //获取当前秒数（0-59）
        /*if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if (hour >= 0 && hour <= 9) {
            hour = "0" + hour;
        }
        if (minute >= 0 && minute <= 9) {
            minute = "0" + minute;
        }*/
        currentdate = year + "年" + month + "月" + strDate + "日  " + hour + "时" + minute + "分"+seconds+"秒";
        console.log(currentdate)
    }

    return currentdate;
}

//格式化时间为年月日时
function formatTimeHours(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
        var date = new Date(cellvalue);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hour = date.getHours();       //获取当前小时数(0-23)
        /*if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if (hour >= 0 && hour <= 9) {
            hour = "0" + hour;
        }
        if (minute >= 0 && minute <= 9) {
            minute = "0" + minute;
        }*/
        currentdate = year + "年" + month + "月" + strDate + "日" + hour + "时";
    }

    return currentdate;
}

//格式化时间为日时分
function formatTimeDay(cellvalue) {
    var currentdate = "";
    if (cellvalue) {
        cellvalue=cellvalue.replace(new RegExp(/-/gm) ,"/")
        var date = new Date(cellvalue);
        var strDate = date.getDate();
        var hour = date.getHours();       //获取当前小时数(0-23)
        var minute = date.getMinutes();     //获取当前分钟数(0-59)
        currentdate = strDate + "日" + hour + "时" + minute + "分";
    }
    return currentdate;
}

//设置cookie
function setCookie(name, value, day) {
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires=' + date;
};

//获取cookie
function getCookie(name) {
    var reg = RegExp(name + '=([^;]+)');
    var arr = document.cookie.match(reg);
    if (arr) {
        return arr[1];
    } else {
        return '';
    }
};

//删除cookie
function delCookie(name) {
    setCookie(name, null, -1);
};


function addDate(date, days) {
    if (days == undefined || days == '') {
        days = 1;
    }
    if (date) {
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        var strDate = date.getDate();
        var month = date.getMonth() + 1;
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }

        var currentdate = date.getFullYear() + "年" + month + "月" + strDate + "日";
        return currentdate;
    }
    return "";
}

//钱转大写
function toSuperMoney(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)) return "";
    var unit = "京亿万仟佰拾兆万仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
    n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p + 1, 2);
    unit = unit.substr(unit.length - n.length);
    for (var i = 0; i < n.length; i++) str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(兆|万|亿|元)/g, "$1").replace(/(兆|亿)万/g, "$1").replace(/(京|兆)亿/g, "$1").replace(/(京)兆/g, "$1").replace(/(京|兆|亿|仟|佰|拾)(万?)(.)仟/g, "$1$2零$3仟").replace(/^元零?|零分/g, "").replace(/(元|角)$/g, "$1整");
}



//日期转大写js
function toUperDate(date) {
    var today = new Date(date);
    var chinese = ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
    var y = today.getFullYear().toString();
    var m = (today.getMonth() + 1).toString();
    var d = today.getDate().toString();
    var result = "";
    for (var i = 0; i < y.length; i++) {
        result += chinese[y.charAt(i)];
    }
    result += "年";
    if (m.length == 2) {
        if (m == 10) {
            result += ("十" + "月");
        } else if (m.charAt(0) == "1") {
            result += ("十" + chinese[m.charAt(1)] + "月");
        }
    }
    else {
        result += (chinese[m.charAt(0)] + "月");
    }
    if (d.length == 2) {
        if (d == 10) {
            result += ("十" + "日");
        } else if (d == 20) {
            result += ("二十" + "日");
        } else if (d == 30) {
            result += ("三十" + "日");
        } else {
            result += (chinese[d.charAt(0)] + "十" + chinese[d.charAt(1)] + "日");
        }

    }
    else {
        result += (chinese[d.charAt(0)] + "日");
    }
    return result;
}

//数字转大写
function intToChinese(str) {
    str = str + '';
    var len = str.length - 1;
    var idxs = ['', '十', '百', '千', '万', '十', '百', '千', '亿', '十', '百', '千', '万', '十', '百', '千', '亿'];
    var num = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    return str.replace(/([1-9]|0+)/g, function ($, $1, idx, full) {
        var pos = 0;
        if ($1[0] != '0') {
            pos = len - idx;
            if (idx == 0 && $1[0] == 1 && idxs[len - idx] == '十') {
                return idxs[len - idx];
            }
            return num[$1[0]] + idxs[len - idx];
        } else {
            var left = len - idx;
            var right = len - idx + $1.length;
            if (Math.floor(right / 4) - Math.floor(left / 4) > 0) {
                pos = left - left % 4;
            }
            if (pos) {
                return idxs[pos] + num[$1[0]];
            } else if (idx + $1.length >= len) {
                return '';
            } else {
                return num[$1[0]]
            }
        }
    });
}

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

//上传图片、word和excel格式、大小验证
function checkFile2(file) {
    var index1 = file.name.lastIndexOf(".");
    var index2 = file.name.length;
    var nameinfo = file.name.substring(index1 + 1, index2);//后缀名

    var extension = nameinfo.toLowerCase() === 'xls';
    var extension2 = nameinfo.toLowerCase() === 'xlsx';
    var extension3 = nameinfo.toLowerCase() === 'doc';
    var extension4 = nameinfo.toLowerCase() === 'docx';
    var img1 = nameinfo.toLowerCase() === 'jpg';
    var img2 = nameinfo.toLowerCase() === 'png';
    var img3 = nameinfo.toLowerCase() === 'jpeg';
    var img4 = nameinfo.toLowerCase() === 'gif';
    var img5 = nameinfo.toLowerCase() === 'bmp';
    var isLt2M = file.size / 1024 / 1024 < 500
    if (!extension && !extension2 && !extension3 && !extension4 &&
        !img1 && !img2 && !img3 && !img4 && !img5) {
        alert('上传模板只能是图片、 xls、xlsx、doc、docx格式!');
        console.log('上传模板只能是图片、 xls、xlsx、doc、docx格式!')
    }
    if (!isLt2M) {
        alert('上传模板大小不能超过 500MB!');
        console.log('上传模板大小不能超过 500MB!')
    }
    return extension || extension2 || extension3 || extension4 || img1 || img2 || img3 || img4 || img5 && isLt2M
}
