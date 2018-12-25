var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        sysMsg:{
            id:"",
            title:"",//标题
            noticeType:"",//消息类型
            content:"",//内容
            releaseDept:"",//发布单位
            releasePeople:"",//发布人
            recieveDept:"",//接收单位
            recievePeople:"",
            recieveDate:"",
            releaseState:"",//发布状态
            releaseDate:""
        },
        formInline:{
            currPage:"",
            pageSize:"",
            totalCount:"",
            orgCode:'',
        },
        tableData: [],//表格数据
        deptData:[],//接收树形数据
        dialogDept:false,//部门的弹窗
        defaultProps: { // el-tree
            children: 'child',
            label: 'localOrgName'
        },
        visible: false,
        rules: {//表单验证规则
            value: [
                {required: true, message: '请输入参数名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入参数值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        title02: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            //加载部门数据
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.deptData = result.orgList;
                        vm.userData = result.orgList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            this.reload();
        })
    },
    methods: {
        loadTopic: function(){
            /*$.ajax({
                type: "GET",
                url: baseURL + "topic/list",
                dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == 0) {
                        vm.specialKnowledgeIds = result.page.list;
                    } else {
                        alert(result.page);
                    }
                }
            });*/

        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept=true;

        },

        //消息发送
        send:function(index,row){
            $.ajax({
                type: "POST",
                url: baseURL + 'msg/send?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysMsg = result.msg;
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.dialogConfig = false;
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleChange:function(){

        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);

        },
        confimUser: function () {
            this.dialogUser=false;
        },
        cancelUser: function () {
            this.dialogUser=false;
        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },

        // 保存和修改
        saveOrUpdate: function (formName) {
            console.info(vm.sysMsg);
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysMsg.id ? "msg/update" : "msg/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysMsg),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.dialogConfig = false;
                                        vm.reload();
                                    }
                                });
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        addConfig: function () {
            vm.sysMsg={
                id:"",
                title:"",
                noticeType:"",//消息类型
                content:"",//内容
                releaseDept:"",//发布单位
                releasePeople:"",//发布人
                recieveDept:"",//接收单位
                recievePeople:"",
                recieveDate:"",
                releaseState:"",//发布状态
                releaseDate:""
            };
            this.title02 = "新增";
            this.dialogConfig = true;
            //parent.location.href =baseURL+"modules/examCen/examConfig.html";
        },
        handleNodeClick: function (data) {
            vm.formInline.orgCode= data.localOrgCode;
            this.reload();
        },
        handleEdit: function (index, row) {
            this.title02 = "修改规则";
            this.dialogConfig = true;
            $.ajax({
                type: "GET",
                url: baseURL + 'msg/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysMsg = result.msg;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            alert(row.id)
            //vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'msg/delete?id='+row.id,
                    //async: true,
                    //data: JSON.stringify(row.id),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "msg/listAll?isMap=true",
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


        toChild: function (item) {

            parent.location.href =baseURL+item.url+"?id="+item.id;

        }
    }
});
