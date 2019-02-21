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
            currPage:1,
            pageSize:10,
            totalCount:0,
        },
        dialogConfig: false,//table弹出框可见性
        title02: "",//弹窗的名称
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
                url: baseURL + "msg/findMsgList?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = Number(result.page.currPage);
                        vm.formInline.pageSize = Number(result.page.pageSize);
                        vm.formInline.totalCount = Number(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        toUrl: function(url) {
            alert(1)
            window.location.href = baseURL + url
        },
        toHome: function () {
            parent.location.reload()
        },
        lookMsg:function(index, row)
        {
            this.title02 = "查看";
            this.dialogConfig = true;
            $.ajax({
                type: "GET",
                url: baseURL + 'msg/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.msg = result.msg;
                        console.info(vm.msg);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
