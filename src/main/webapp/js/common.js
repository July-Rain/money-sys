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