/**
 * Author: MengyuWu
 * Date: 2018/12/18
 * Description:学习任务管理
 */
var menu=getUrlParam("menu");//获取页面标识
var menuId = getUrlParam('id');
var ztree = null;
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
        idArr:[],// 部门Tree默认展开数据
        menuForm:"",
        navData: [],//导航
        formInline: { // 搜索表单
            taskContent: '',
            taskName: '',
            currPage: 1,
            pageSize: 10,
            totalCount: 0,
            taskClass:"",
            policeclass:"",
            startTime:"",
            endTime:""
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
            taskContentList:[],
            taskClass:"",
            policeclassOption:""

        },
        rules: {//表单验证规则
            taskName: [
                {required: true, message: '请输入学习任务名称', trigger: 'blur'},
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ],

            startTime: [
                {required: true, message: '请输入开始日期', trigger: 'blur'}
            ],
            endTime: [
                {required: true, message: '请输入结束日期', trigger: 'blur'}
            ],

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
            totalCount:0,
            identify:'',// 表明是用户
            userStatus:'2000'//查询有效的用户

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
        taskClassOption:[],//所属分类
        policeclassOption:[],//所属警种
       // multipleClassSelection:[]//法律法规数据选择框
        deptCheckData:[],//部门默认选中节点
        saveUserTableData: [],//用于人员回显表格的对象  --回显需加
        breadArr:[]//面包屑数据
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
                        // 默认展开第一级
                        vm.userData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
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
            // 所属警种
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"POLICACLASS",Parentcode:"0"},
                success: function (result) {
                    vm.policeclassOption=result.dictlist;
                }
            });
            // 所属分类
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"TASKCLASS",Parentcode:"0"},
                success: function (result) {
                    vm.taskClassOption=result.dictlist;
                }
            });

        });
        this.$nextTick(function () {
            this.reload();
            this.reloadUser();
            vm.menuForm=menu;
            this.breadArr=getBreadcrumb(menuId);
        })
    },
    methods: {
        //序列号计算
        indexMethod:function (index) {
            return index + 1 + (vm.formInline.currPage-1) * vm.formInline.pageSize;
        },
        //序列号计算
        indexUserMethod: function (index) {
            return index + 1 + (vm.userForm.currPage - 1) * vm.userForm.pageSize;
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
                taskContentList:[],
                taskClass:"",
                policeclassOption:""
            };
            this.title = "新增学习任务";
            this.dialogLearnTask = true;
            this.getDept();
        },
        handleEdit: function (index, row) {
            //判断是否发布
            if(vm.isUsed(row)){
                vm.$message({
                    type: 'error',
                    message: '已发布的数据无法修改'
                });
                return;
            }
            this.title = "修改学习任务";
            this.dialogLearnTask = true;
            this.deptCheckData=[];
            $.ajax({
                type: "POST",
                url: baseURL + 'learntasks/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.learnTasks = result.data;
                        vm.deptCheckData=result.data.deptArr;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.getDept();
        },
        handleDel: function (index, row) {
            //判断是否发布
            if(vm.isUsed(row)){
                vm.$message({
                    type: 'error',
                    message: '已发布的数据无法删除'
                });
                return;
            }
            vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
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
                });
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        handleStart: function (index, row) {
            this.$confirm('发布后将不能修改, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'learntasks/startTask?id='+row.id,
                    async: true,
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '发布成功!'
                        });

                    }
                });
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消启用'
                // });
            });

        },
        isUsed:function(row){
          //是否启用
            if(row.isUse=='1'){
                return true;
            }
            return false;
        },
        closeDia: function () {
            this.dialogLearnTask = false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "learntasks/listICreate?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        if(vm.tableData){
                            //有值
                            for(var i=0;i< vm.tableData.length;i++){
                                vm.tableData[i].completionDegree= vm.tableData[i].allCount == null ? '0%' :  vm.tableData[i].finishCount=='0' ? '0%' :(Math.round( vm.tableData[i].finishCount/ vm.tableData[i].allCount * 100* 100)/100) + '%';
                                vm.tableData[i].overDegree= vm.tableData[i].overCount == null ? '0%' :  vm.tableData[i].allCount=='0' ? '0%' :(Math.round( vm.tableData[i].overCount/ vm.tableData[i].allCount * 100* 100)/100) + '%';
                            }
                        }
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

            this.huixian(this.learnTasks.userArr) //  --回显需加

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
            //遍历最终的人员信息
            if(val.length==0){
                vm.learnTasks.userIds = "";
                vm.learnTasks.userName = "";
            }
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
                url: baseURL + "sys/getUorT?isMp=true",
                dataType: "json",
                data: vm.userForm,
                success: function (result) {
                    if (result.code == 0) {
                        vm.userTableData = result.page.list;
                        vm.userForm.currPage = result.page.currPage;
                        vm.userForm.pageSize = result.page.pageSize;
                        vm.userForm.totalCount = parseInt(result.page.totalCount);
                        // 点击展示回显内容：   --回显需加
                        vm.huixian(vm.learnTasks.userArr)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        huixian: function (ids) {
            // saveUserTableData    --回显需加
            if(!ids){
                return
            }
            if(ids.length==0){
                return
            }
            var that = this;
            ids.map(function (_id) {
                that.userTableData.map(function (_data) {
                    if (_id == _data.id) {
                        that.saveUserTableData.push(_data)
                    }
                })

            });
            console.info("saveUserTableData", that.saveUserTableData);
            this.$nextTick(function () {
                // vm.$refs.userTable.toggleRowSelection()
                vm.userToggleSelection(that.saveUserTableData)

            })
        },
        userToggleSelection(rows) {
            //  --回显需加
            if (rows) {
                rows.map(function(row){
                    vm.$refs.userTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.userTable.clearSelection();
            }
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
        toHome: function () {
            parent.location.reload()
        }
    }
});
