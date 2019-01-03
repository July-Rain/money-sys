/**
 * Author: MengyuWu
 * Date: 2018/12/18
 * Description:学习任务管理
 */
var menu=getUrlParam("menu");//获取页面标识
var ztree;
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "infoId",
            pIdKey: "infoParentId",
            rootPId: -1,

        },
        key: {
            url:"nourl",
            name:"infoName"
        }

    },
    check:{
        enable:true,
        nocheckInherit:true
    }
};
var vm = new Vue({
    el: '#app',
    data: {
        navData: [],//导航
        formInline: { // 搜索表单
            taskContent: '',
            taskName: '',
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        tableData: [],//表格数据
        visible: false,
        learnTasks: {
            id: '',
            deptIds: "",
            userIds: "",
            userName:"",//适用人员姓名
            deptName:"",//适用部门姓名
            userArr:[],//适用人员姓名
            deptArr:[],//适用部门姓名
            taskName:"",
            taskContent:"",
            startTime:"",
            endTime:"",
            taskContentList:[]

        },
        rules: {//表单验证规则
            /*value: [
                {required: true, message: '请输入学习任务名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入学习任务值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]*/
        },
        dialogLearnTask: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: [],//删除数据
        dialogDept: false,//部门的弹窗
        dialogUser: false,//人员的弹窗
        deptData:[],//部门树数据
        userData:[],//人员树数据
        defaultDeptProps:{
            children: 'child',
            label: 'orgName'
        },//部门树的默认格式
        defaultUserProps:{
            children: 'child',
            label: 'orgName'
        },//部门人员的默认格式
        userForm:{
            userCode:"",
            userName:"",
            orgCode:"",
            currPage: 1,
            pageSize: 10,
            totalCount:0

        },//人员查询
        userTableData:[],//人员表格信息
        multipleSelection:[],//选中人员信息
        multipleDeptSelection:[],//选中部门信息
        //dialogClass:false,//法律法规
        classData:[],//法律法规数据
        defaultClassProps:{
            children: 'child',
            label: 'infoName'
        },//法律法规树默认数据
       // multipleClassSelection:[]//法律法规数据选择框
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
            //加载法律法规数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/classTree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.classData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            this.reload();
            this.reloadUser();
            vm.menuForm=menu;
        })
    },
    methods: {
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
                    var url = vm.learnTasks.id ? "learntasks/update?menuFrom="+vm.menuForm : "learntasks/insert?menuFrom="+vm.menuForm;
                    var deptArr = vm.learnTasks.deptIds?vm.learnTasks.deptIds.split(","):[];
                    var userArr = vm.learnTasks.userIds?vm.learnTasks.userIds.split(","):[];
                    vm.learnTasks.deptArr=deptArr;
                    vm.learnTasks.userArr=userArr;
                    var nodes = ztree.getCheckedNodes(true);
                    vm.learnTasks.taskContentList=nodes;
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.learnTasks),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.dialogLearnTask = false;
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
        addLearnTask: function () {
            this.learnTasks = {
                id: '',
                deptIds: "",
                userIds: "",
                userName:"",//适用人员姓名
                deptName:"",//适用部门姓名
                userArr:[],//适用人员姓名
                deptArr:[],//适用部门姓名
                taskName:"",
                taskContent:"",
                startTime:"",
                endTime:"",
                taskContentList:[]
            };
            this.title = "新增学习任务";
            this.dialogLearnTask = true;
            this.getDept();
        },
        handleEdit: function (index, row) {
            this.title = "修改学习任务";
            this.dialogLearnTask = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'learntasks/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.learnTasks = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.getDept();
        },
        handleDel: function (index, row) {
            vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'learntasks/delete',
                    async: true,
                    data: JSON.stringify(vm.delIdArr),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });s
            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia: function () {
            this.dialogLearnTask = false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "learntasks/list?isMp=true",
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
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode=data.orgCode;
            this.reloadUser();
        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);

        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept=true;

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser=true;

        },
        confimDept: function () {
            this.multipleDeptSelection=this.$refs.deptTree.getCheckedNodes();
            for(var i=0;i<this.multipleDeptSelection.length;i++){
                if (this.learnTasks.deptIds == "") {
                    this.learnTasks.deptIds=this.multipleDeptSelection[i].id;
                    this.learnTasks.deptName=this.multipleDeptSelection[i].orgName;
                }else{
                    this.learnTasks.deptIds+=","+this.multipleDeptSelection[i].id;
                    this.learnTasks.deptName+=","+this.multipleDeptSelection[i].orgName;
                }
            }
            this.dialogDept=false;
        },
        cancelDept: function () {
            this.dialogDept=false;
        },
        confimUser: function () {
            this.dialogUser=false;
        },
        cancelUser: function () {
            this.dialogUser=false;
        },
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
        userHandleSizeChange: function (val) {
            this.userForm.pageSize=val;
            this.reloadUser();
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage=val;
            this.reloadUser();
        },
        handleSelectionChange(val) {
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            for (var i=0;i<val.length;i++){
                if (this.learnTasks.userIds == "") {
                    this.learnTasks.userIds=val[i].id;
                    this.learnTasks.userName=val[i].userName;
                }else{
                    this.learnTasks.userIds+=","+val[i].id;
                    this.learnTasks.userName+=","+val[i].userName;
                }
            }

        },
        handleClassCheckChange: function () {
            this.multipleClassSelection=this.$refs.classTree.getCheckedNodes();
            vm.learnTasks.taskContentList=this.multipleClassSelection;
            console.log(vm.learnTasks.taskContentList);
        },
        getDept: function(){
            //加载部门树
            $.get(baseURL + "law/zTree", function(r){
                ztree = $.fn.zTree.init($("#classTree"), setting, r.classifyList);
            })
        },
    }
});