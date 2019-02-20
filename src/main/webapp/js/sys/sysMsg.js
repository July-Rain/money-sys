var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        idArr:[],// 部门Tree默认展开数据
        user:{},
        teamtype2:true,
        sysMsg:{
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
            deptArr:[],//接收单位数组
            userArr:[],//接收人数组
            recieveDate:"",
            releaseState:"",//发布状态
            releaseDate:""
        },
        userTableData:[],//人员表格信息
        userForm:{
            userCode:"",
            userName:"",
            orgCode:"",
            currPage: 1,
            pageSize: 10,
            totalCount:0

        },//人员查询
        formInline:{
            currPage:1,
            pageSize:10,
            totalCount:0,
            orgCode:'',
        },
        defaultUserProps:{
            children: 'child',
            label: 'orgName'
        },//部门人员的默认格式
        userData:[],//人员树数据
        tableData: [],//表格数据
        deptData:[],//接收树形数据
        dialogDept:false,//部门的弹窗
        dialogUser:false,//人员的弹窗
        defaultProps: { // el-tree
            children: 'child',
            label: 'localOrgName'
        },
        visible: false,
        rules: {//表单验证规则

            title:[
                {required: true, message: '请输入消息标题', trigger: 'blur'},
            ],
            noticeType:[
                {required: true, message: '请选择消息类型', trigger: 'blur'},
            ],
            content:[
                {required: true, message: '请输入消息内容', trigger: 'blur'},
            ],
            userName:[
                {required: true, message: '请输入接收人', trigger: 'blur'},
            ],

        },
        dialogConfig: false,//table弹出框可见性
        title02: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            vm.user=jsgetUser();//获得人
            //加载部门数据
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.deptData = result.orgList;
                        vm.userData = result.orgList;
                        // 默认展开第一级
                        vm.deptData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });

            this.reload();
        })
    },
    methods: {
        searchUser: function () {
            //查询人员信息
            vm.reloadUser();
        },
        reloadUser: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getAllUsers",
                dataType: "json",
                data: vm.userForm,
                success: function (result) {
                    if (result.code == 0) {
                        vm.userTableData = result.page.list;
                        vm.userForm.currPage = result.page.currPage;
                        vm.userForm.pageSize = result.page.pageSize;
                        vm.userForm.totalCount = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage=val;
            this.reloadUser();
        },
        userHandleSizeChange: function (val) {
            this.userForm.pageSize=val;
            this.reloadUser();
        },
        handleSelectionChange(val) {

            //选择人员信息
            this.multipleSelection = val;
            this.sysMsg.recievePeople="";
            // 取到msg 的id 看有没
                if(this.sysMsg.id!="")
                {

                    if(val.length>1)
                    {
                        alert("请选择一位接收人");
                        return;
                    }
                }

                    //遍历最终的人员信息

                    for (var i=0;i<val.length;i++){
                        if(i==0)
                        {
                            this.sysMsg.recievePeople=val[i].id;
                            this.sysMsg.userName=val[i].fullName;
                        }
                        else
                        {
                            this.sysMsg.recievePeople=this.sysMsg.recievePeople+','+val[i].id;
                            this.sysMsg.userName=this.sysMsg.fullName+','+val[i].fullName;
                        }
                    }


        },
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode=data.orgCode;
            this.reloadUser();
        },
        chooseUser: function () {
            //选择人员
            this.dialogUser=true;
            // vm.sysMsg.recievePeople="";
        },
        confimUser: function () {
            this.dialogUser=false;
        },
        cancelUser: function () {
            this.dialogUser=false;
        },
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
            //idalert("000000000000")
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
            //alert("9999999999")
            console.log(data);tableData

        },
        confimDept: function () {
            this.multipleDeptSelection=this.$refs.deptTree.getCheckedNodes();
            for(var i=0;i<this.multipleDeptSelection.length;i++){
                if (this.sysMsg.recievePeople == "") {
                    this.sysMsg.recieveDept=this.multipleDeptSelection[i].id;
                    this.sysMsg.deptName=this.multipleDeptSelection[i].orgName;
                }else{
                    this.sysMsg.recieveDept+=","+this.multipleDeptSelection[i].id;
                    this.sysMsg.deptName+=","+this.multipleDeptSelection[i].orgName;
                }
            }
            this.dialogDept=false;
        },
        cancelDept: function () {
            this.dialogDept=false;
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
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysMsg.id ? "msg/update" : "msg/insert";
                    var deptArr = vm.sysMsg.recieveDept?vm.sysMsg.recieveDept.split(","):[];
                    var userArr = vm.sysMsg.recievePeople?vm.sysMsg.recievePeople.split(","):[];
                    vm.sysMsg.deptArr=deptArr;
                    vm.sysMsg.userArr=userArr;
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
        // 保存和修改
        /*saveOrUpdate: function (formName) {
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

        },*/
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        addConfig: function () {
            vm.sysMsg.releasePeople=vm.user.id;
            vm.sysMsg.releasePeopleName=vm.user.fullName;
            this.title02 = "新增";
            this.dialogConfig = true;
            //parent.location.href =baseURL+"modules/examCen/examConfig.html";
        },
        handleNodeClick: function (data) {
            vm.formInline.orgCode= data.localOrgCode;
            this.reload();
        },
        handleEdit: function (index, row) {
            this.title02 = "修改";
            this.dialogConfig = true;
            this.teamtype2=true;
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

        lookMsg:function(index, row)
        {
            this.title02 = "查看";
            this.dialogConfig = true;
            this.teamtype2=false;
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
                url: baseURL + "msg/listAll?isMp=true",
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


        toChild: function (item) {

            parent.location.href =baseURL+item.url+"?id="+item.id;

        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
