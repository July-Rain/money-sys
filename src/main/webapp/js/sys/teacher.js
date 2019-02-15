var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        idArr:[],// 部门Tree默认展开数据
        //menuId:"",//菜单id
        navData: [],//导航

        formInline: { // 搜索表单
            id:'',
            userName: '',
            userCode: '',
            orgCode:'',
            identify:'1',//表明是教官
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
            identify:1,//添加为教官
            orgName:"",
            userPoliceId:"",

        },
        rules: {//表单验证规则

            userName:[
                {required: true, message: '请输入用户名', trigger: 'blur'},
            ],
            userCode:[
                {required: true, message: '请输入登陆账号', trigger: 'blur'},
            ],
            userPoliceId:[
                {required: true, message: '请输入警号', trigger: 'blur'},
            ],
            password:[
                {required: true, message: '请输入密码', trigger: 'blur'},
            ],
            userStatus:[
                {required: true, message: '请选择', trigger: 'blur'},
            ],
            orgName:[
                {required: true, message: '请输入部门', trigger: 'blur'},
            ],
        },
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
        })
        this.$nextTick(function () {
            this.reload();
        })
    },


    methods: {
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
                identify:1,//添加为教官
                orgName:"",
                userPoliceId:"",

            };
            vm.dialogtch=true;
        },
        //用户修改vm.
        handleUpt:function(index,row){
            vm.dialogtch=true;
            row.userStatus= row.userStatus.toString()
            vm.teacher=row;
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
        handleNodeClick:function(data){
            vm.formInline.orgCode=data.localOrgCode;
            this.reload();
        },

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
        handleCheckChange: function (data, checked, node) {
            if(checked == true){
                this.checkedId = data.id;
                this.$refs.treeForm.setCheckedNodes([data]);
                console.log(data);
                vm.teacher.orgCode= data.localOrgCode;
                vm.teacher.orgName= data.localOrgName;
            }
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
                        var url=baseURL + "sys/add";
                        console.log(vm.teacher.id);
                        if (vm.teacher.id != null && vm.teacher.id != '') {
                            url=baseURL+"sys/updata";
                        }
                        $.ajax({
                            type : "POST",
                            url: url,
                            contentType: "application/json",
                            data:JSON.stringify(vm.teacher),
                            success:function (result) {
                                if(result.code==0){
                                    alert("成功");
                                    vm.dialogtch = false;
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
