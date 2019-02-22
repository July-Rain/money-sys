var menuId = getUrlParam('id');
var identify = getUrlParam('identify');
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        idArr:[],// 部门Tree默认展开数据
        formInline: { // 搜索表单
            id:'',
            userName: '',
            userCode: '',
            orgCode:'',
            identify:identify,//表明是用户
            currPage: 1,
            pageSize:10,
            totalCount: 0

        },//检索条件
        tableData: [],//表格数据

        title:"",//弹窗的名称
        visible: false,
        dialogtch: false,//用户可见性
        dialogDept:false,//部门可见性


        treeData: [],//部门数据
        treeForm:'',
        defaultProps: { // el-tree
            children: 'child',
            label: 'localOrgName'
        },
        importFileUrl:baseURL+"sys/upload",
        teacher:{
            id:"",
            userName:"",
            userCode:"",
            password:"",
            photo:"",
            orgCode:"",
            identify:identify,//添加为用户
            orgName:"",
            userPoliceId:"",
            roles:"",//角色
            tmroles:[],
            policeclass:""
        },
        rules: {//表单验证规则
            userName: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
            ],
            userCode: [
                {required: true, message: '请输入登陆账号', trigger: 'blur'},
            ],
            userPoliceId: [
                {required: true, message: '请输入警号', trigger: 'blur'},
            ],

            tmroles: [
                { required: true, message: '请选择角色', trigger: 'change'}
            ],
            orgName: [
                {required: true, message: '请选择部门', trigger: 'blur'},
            ],
        },
        roles:[],
        policeclassOption:[],//所属警种
        breadArr:[],//面包屑数据
        src: baseURL+"/statics/img/police_head.png",
    },
    created: function () {
        this.$nextTick(function () {
            //加载部门树
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.treeData = result.orgList;
                        // 默认展开第一级
                        vm.treeData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });
            // 所属警种
            $.ajax({
                type: "GET",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"POLICACLASS",Parentcode:"0"},
                success: function (result) {
                    vm.policeclassOption=result.dictlist;
                }
            });
            this.breadArr=getBreadcrumb(menuId);
            this.reload();
        });
    },


    methods: {
        //序列号计算
        indexMethod:function (index) {
            return index + 1 + (vm.formInline.currPage-1) * vm.formInline.pageSize;
        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        //用户添加
        handleAdd:function(){
            vm.teacher={
                    id:"",
                    userName:"",
                    userCode:"",
                    password:"",
                    photo:"",
                    orgCode:"",
                    identify:identify,//添加为用户
                    orgName:"",
                    userPoliceId:"",
                    roles:"",//角色
                    tmroles:[],
                    policeclass:""
            },
                vm.teacher.tmroles=[];
            vm.dialogtch=true;
            vm.handleRoles();
        },
        //用户修改vm.
        handleUpt:function(index,row){
            vm.dialogtch=true;
            vm.teacher=row;
            vm.handleRoles();
            if(vm.teacher.roles){
                vm.teacher.tmroles=vm.teacher.roles.split(",");
            }else{
                vm.teacher.tmroles=[];
            }

        },

        //获取角色列表
        handleRoles:function(){
            $.ajax({
                type : "POST",
                url: baseURL + "role/getAllRoles",
                contentType: "application/json",
                success:function (result) {
                    if(result.code==0){
                        vm.roles=result.roles;
                    }else{
                        alert(result.msg);
                    }
                }
            })
        },

        //密码重置
        handleEdit : function(index,row){
            $.ajax({
                type : "POST",
                url: baseURL + "sys/resetPassword?id="+row.id,
                contentType: "application/json",
                success:function (result) {
                    if(result.code==0){
                        alert("密码重置成功");
                    }else{
                        alert(result.msg);
                    }
                }
            })
        },

        //用户禁用
        handleDel : function(index,row){
            $.ajax({
                type : "POST",
                url: baseURL + "sys/deleteUser?id="+row.id,
                contentType: "application/json",
                success:function (result) {
                    if(result.code==0){
                        alert("禁用用户成功");
                        vm.reload();
                    }else{
                        alert(result.msg);
                    }
                }
            })
        },
//用户删除
        handleDel2: function (index, row) {

            //vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'sys/deleteuser?id='+row.id,
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
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        //用户恢复
        handleRecovery : function(index,row){
            $.ajax({
                type : "POST",
                url: baseURL + "sys/recoveryUser?id="+row.id,
                contentType: "application/json",
                success:function (result) {
                    if(result.code==0){
                        alert("恢复用户成功");
                        vm.reload();
                    }else{
                        alert(result.msg);
                    }
                }
            })
        },

        // 部门点击事件
        // handleNodeClick:function(data){
        //     vm.formInline.orgCode=data.localOrgCode;
        //     this.reload();
        // },

        //展示部门
        deptShow:function(){
            vm.dialogDept=true;
            if(vm.treeData.length==0){
                $.ajax({
                    type: "POST",
                    url: baseURL + "org/tree",
                    contentType: "application/json",
                    success: function(result){

                        if(result.code === 0){
                            vm.treeData = result.orgList;
                        }else{
                            alert(result.msg);
                        }
                    }
                });
            }
        },


        //分页
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },

        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },

        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
            vm.formInline.orgCode='';
            vm.reload();
        },

        //关闭弹出框
        closeTchDia : function(){
            this.dialogtch=false;
            vm.reload();
        },

        //刷新页面
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getUorT?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },


        // 部门选择事件
        handleNodeClick: function (data) {
            vm.formInline.orgCode= data.localOrgCode;
            vm.formInline.orgName= data.localOrgName;
            vm.formInline.orgId= data.orgId;
            this.reload();
        },

        //确定部门
        confimDept:function(){

            vm.dialogDept=false;
        },
        //取消部门
        cancelDept:function(){
            vm.dialogDept=false;
        },


        //上传
        uploadSuccess: function(response, file, fileList) {
            vm.teacher.photo=response.accessoryId;
        },

        beforeUpload: function () {
            //to do
        },

        uploadProcess: function () {

        },

        //保存
        saveOrUpdate:function (formName) {
            this.$refs[formName].validate(function (valid) {

                    if (valid) {

                        var myuserPoliceId="";
                        var myuserCode="";
                        var rs="";
                        vm.teacher.tmroles.map(function (item,index) {
                            if(!(index==vm.roles.length-1  || index==0)){
                                rs=rs+",";
                            }
                            rs=rs+item;
                        });
                        vm.teacher.roles=rs;
                        var url=baseURL + "sys/add";
                        var mytype="1"
                        console.log(vm.teacher.id);
                        if (vm.teacher.id != null && vm.teacher.id != '') {
                            url=baseURL+"sys/updata";
                            mytype="2"
                        }


                        //保存前先判断 身份证号 与  警号 有没有重复 冲突  在js做  就不再java里判了
                        //先判断警员号
                        $.ajax({
                            type : "POST",
                            url: baseURL + "sys/userPoliceId?userPoliceId="+vm.teacher.userPoliceId+"&mytype="+mytype+"&id="+vm.teacher.id,
                            dataType: "json",
                            async:false,
                            success:function (result) {
                                if(result.code==0){

                                    if(result.type=="1")//说明找到了
                                    {
                                        // alert("存在重复的警员号，添加失败");
                                        myuserPoliceId="1";
                                        return;
                                    }
                                }else{
                                    alert(result.msg);
                                }
                            }
                        });

                        //在判断身份证号
                        $.ajax({
                            type : "POST",
                            url: baseURL + "sys/userCode?userCode="+vm.teacher.userCode+"&mytype="+mytype+"&id="+vm.teacher.id,
                            dataType: "json",
                            async:false,
                            success:function (result) {
                                if(result.code==0){

                                    if(result.type=="1")//说明找到了
                                    {
                                        // alert("存在重复的登陆账号，添加失败");
                                        myuserCode="1";
                                        return;
                                    }
                                }else{
                                    alert(result.msg);
                                }
                            }
                        });
                        if(myuserPoliceId=="1")
                        {
                            alert("存在重复的警员号，操作失败");
                            return;
                        }
                        if(myuserCode=="1")
                        {
                            alert("存在重复的登陆账号，操作失败");
                            return;
                        }

                        $.ajax({
                            type : "POST",
                            url: url,
                            contentType: "application/json",
                            data:JSON.stringify(vm.teacher),
                            async:false,
                            success:function (result) {
                                if(result.code==0){
                                    alert("成功");
                                    vm.closeTchDia();
                                }else{
                                    alert(result.msg);
                                }
                            }
                        })
                    }
                    else {
                        console.log('error submit!!');
                        return false;
                    }
            });

        },
        toHome:function () {
            parent.location.reload()
        }
    }
});
