var storage=window.sessionStorage;
var userStatus = storage.getItem("userStatus");
var checkExamUser = storage.getItem("checkExamUser");
var examConfig = storage.getItem("examConfig");
console.info(checkExamUser);
var vm = new Vue({
    el:'#app',
    data:{
        checkExamUser: {},
        userStatus:{},
        headerHide: false,
        checkExamUserDia:false,
        tableData: [],//表格数据
        formInline: {
            checkStatus:'',
            limit: 10,
            page: 1,
            count: 0,
            checkExamUserId:''
        }
    },
    created: function () {
        this.$nextTick(function () {
            if (userStatus == 0) {
                vm.checkExamUser = JSON.parse(checkExamUser);
                vm.openDia();
            }else{
                vm.checkExamUser = JSON.parse(checkExamUser);
                vm.closeDia();
                this.reload();
            }
        })
    },
    methods:{
        onSubmit: function () {
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        openDia:function(){
            this.checkExamUserDia = true;
        },
        closeDia:function () {
            this.checkExamUserDia = false;
        },
        saveCheckExamUser:function () {
                $.ajax({
                    type: "POST",
                    url: baseURL+'check/exam/saveCheckExamUser',
                    data: JSON.stringify(vm.checkExamUser),
                    contentType: "application/json; charset=utf-8",
                    success: function(result){
                        if(result.code == 0){//登录成功
                            vm.reload();
                        }else{
                            alert(result.msg);
                        }
                    }
                })
        },
        reload:function () {
            vm.closeDia();
            vm.formInline.checkExamUserId = vm.checkExamUser.id;
            console.info(vm.formInline);
            $.ajax({
                type: "POST",
                url: baseURL + "check/exam/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.count = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        startCheckExam:function (index,row) {
            // var parentWin = window.parent;
            // var id = row.id;
            // var checkStatus = row.checkStatus;
            // var userExamId = row.userExamId==null?'':row.userExamId;
            storage.setItem("examConfigId",row.id);
            storage.setItem("checkExamId",row.checkExamId);
            storage.setItem("checkStatus",row.checkStatus);
            storage.setItem("userExamId",row.userExamId==null?'':row.userExamId);
            storage.setItem("checkExamUserId",vm.checkExamUser.id);
            parent.location.href
                =baseURL+'checkExamCen/checkSubjectExam.html';
        }
        
    }
});