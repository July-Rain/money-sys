var storage=window.sessionStorage;
var userStatus = storage.getItem("userStatus");
var checkExamUser = storage.getItem("checkExamUser");
var examConfig = storage.getItem("examConfig");
console.info(checkExamUser);
var vm = new Vue({
    el:'#app',
    data:{
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
        },
        checkExamUser :{
            examUserName :'',
            examUserSex : '0'
        },
        checkExamUserRule:{
            examUserName: [
                {required: true, message: '请输入您的姓名', trigger: 'blur'}
            ]
        }
    },
    created: function () {
        this.$nextTick(function () {
            if (userStatus == 0) {
                this.checkExamUser = JSON.parse(checkExamUser);
                this.checkExamUser.examUserName = '';
                this.checkExamUser.examUserSex = '0';
                this.openDia();
            }else{
                this.checkExamUser = JSON.parse(checkExamUser);
                this.closeDia();
                this.reload();
            }
        })
    },
    methods:{
        onSubmit: function () {
            this.reload();
        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page - 1) * vm.formInline.limit;
        },
        handleCurrentChange: function (val) {
            this.formInline.page = val;
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.limit = val;
            this.reload();
        },
        openDia:function(){
            this.checkExamUserDia = true;
            console.log(vm.checkExamUser)
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
                            vm.checkExamUser = result.checkExamUser;
                            storage.setItem("checkExamUser",JSON.stringify(vm.checkExamUser));
                            storage.setItem("userStatus","1");
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
                        vm.formInline.page = result.page.currPage;
                        vm.formInline.limit = result.page.pageSize;
                        vm.formInline.count = parseInt(result.page.count);
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
        },
        toLogout:function () {
            window.location.href = baseURL + 'logout'
        }
        
    }
});