/*var menuId =getUrlParam('id');*/
var vm = new Vue({
    el: '#app',
    data: {
        msg:{
            id:"",
            title:"",//标题
            noticeType:"",//消息类型
            content:"",//内容
            releaseDept:"",//发布单位
            releasePeople:"",//发布人
            recieveDept:"",//接收单位
            deptName:"",//接收单位名称
            recievePeople:"",//接收人ID
            userName:"",//接收人名称
            recieveDate:"",
            releaseState:"",//发布状态
            releaseDate:""
        },//接收消息
        msgList:[],//接收推送的消息
        tableData:[],//表格数据
        formInline:{
            currPage:0,
            pageSize:0,
            totalCount:0
        }
    },
    created: function () {
        this.$nextTick(function (){
            this.reload();
        })
    },
    methods: {
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "msg/findMsgList?isMap=true",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.pageNo;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.count)+1;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        toUrl: function(url) {
            alert(1)
            window.location.href = baseURL + url
        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
