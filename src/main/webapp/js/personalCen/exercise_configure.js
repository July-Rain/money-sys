var vm = new Vue({
    el: '#app',
    data: {
        idArr:[],// 部门Tree默认展开数据
        tableData: [],
        title: '新增配置',
        dialogConfig: false,
        exerciseConfigure: {
            name: '',
            prefix: '',
            list: [],
            rangeType: 0,
            deptIds: [],
            userIds: []
        },
        rangeType: [
            {key:0, value:'个人'},
            {key:1, value:'公开'},
            {key:2, value:'部门'}
        ],
        config: {
            topicId: '',
            topicName: '',
            primaryNum: 0,
            middleNum: 0,
            seniorNum: 0,
            topic: null,
            total: 0
        },
        addConfigFlag: false,
        topicList: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0,
            taskName: '',//筛选条件
            startTime: '',
            endTime: ''
        },
        deptData: [],
        userData: [],
        dialogDept: false,
        defaultDeptProps: {
            children: 'child',
            label: 'orgName'
        },//部门树的默认格式
        defaultUserProps: {
            children: 'child',
            label: 'orgName'
        },//部门人员的默认格式
        paperDialog: false,
        questionList: [],
        configRules: {
            topic: [{required: true, message: '请选择主题类型', trigger: 'change'}],
            primaryNum: [
                {required: true, message: '请设置初级题量', trigger: 'blur'}
            ],
            middleNum: [
                {required: true, message: '请设置中级题量', trigger: 'blur'}
            ],
            seniorNum: [
                {required: true, message: '请设置高级题量', trigger: 'blur'}
            ]
        },
        rules: {
            prefix: [
                {required: true, message: '请输入试卷名称前缀并生成试卷名称', trigger: 'blur'},
                {max: 25, message: '最大长度25', trigger: 'blur'}
            ],
            name: [
                {required: true, message: '请生成试卷名称', trigger: 'blur'}
            ]
        },
        dialogUser: false,
        userForm: {
            userCode: "",
            userName: "",
            orgCode: "",
            currPage: 1,
            pageSize: 10,
            totalCount: 0,
            identify: '0',// 表明是用户
            userStatus:'2000'//查询有效的用户

        },//人员查询
        userTableData: [],//人员表格信息
    },
    methods: {
        //序列号计算
        indexUserMethod: function (index) {
            return index + 1 + (vm.userForm.currPage - 1) * vm.userForm.pageSize;
        },
        addConfig: function () {
            vm.title =  '新增练习配置';
            vm.dialogConfig = true;
        },
        createName: function () {
            if(vm.exerciseConfigure.prefix == null || vm.exerciseConfigure.prefix == ''){
                vm.$alert('请输入试卷名称前缀', '提示', {
                    confirmButtonText: '确定',
                    callback: function () {
                    }
                });
            } else {
                $.ajax({
                    type: "GET",
                    url: baseURL + "exercise/configure/createName",
                    contentType: "application/json",
                    data:{
                        prefix: vm.exerciseConfigure.prefix
                    },
                    success: function (result) {
                        if (result.code === 0) {
                            vm.exerciseConfigure.name = result.name;
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            }

        },
        add: function () {
            vm.addConfigFlag = true;
        },
        sure: function(formName){
            this.$refs[formName].validate(function (valid) {
                if(valid){
                    var tot = vm.config.primaryNum
                        + vm.config.middleNum
                        + vm.config.seniorNum;
                    if(tot < 1){
                        vm.$alert('请至少设置一种类型题目数量', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                            }
                        });
                        valid = false;
                    }
                }
                if (valid) {
                    var obj = vm.config.topic;
                    if(obj != null && obj != ''){
                        vm.config.topicId = obj.key;
                        vm.config.topicName = obj.value;
                    }
                    vm.exerciseConfigure.list.push(vm.config);
                    if(vm.exerciseConfigure.list.length > 1){
                        vm.exerciseConfigure.type = '综合类型';
                    } else if(vm.exerciseConfigure.list.length == 1){
                        vm.exerciseConfigure.type = obj.value;
                    } else {
                        vm.exerciseConfigure.type = '';
                    }
                    vm.config = {
                        topicId: '',
                        topicName: '',
                        primaryNum: 0,
                        middleNum: 0,
                        seniorNum: 0,
                        topic: null,
                        total: 0
                    };
                    vm.addConfigFlag = false;
                }
            });
        },
        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    if(vm.exerciseConfigure.list.length == 0){
                        vm.$alert('请添加配置', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                            }
                        });
                    } else {

                        $.ajax({
                            type: "POST",
                            url: baseURL + "exercise/configure/save",
                            data: JSON.stringify(vm.exerciseConfigure),
                            contentType: "application/json",
                            success: function (result) {
                                if (result.code === 0) {
                                    vm.dialogConfig = false;
                                    vm.exerciseConfigure = {
                                        name: '',
                                        prefix: '',
                                        list: [],
                                        rangeType: 0
                                    };
                                    vm.refresh();
                                } else {
                                    alert(result.msg);
                                }
                            }
                        });
                    }

                }
            });

        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/configure/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        deptShow:function(){
            vm.dialogDept=true;
        },
        handleCheckChange: function (data, checked, node) {

        },
        confimDept: function(){
            this.multipleDeptSelection = this.$refs.deptTree.getCheckedNodes();
            this.exerciseConfigure.deptIds = [];
            var deptNames = [];
            for (var i = 0; i < this.multipleDeptSelection.length; i++) {
                this.exerciseConfigure.deptIds.push(this.multipleDeptSelection[i].id);
                deptNames.push(this.multipleDeptSelection[i].orgName);
            }
            this.exerciseConfigure.deptNames = deptNames.join();
            vm.dialogDept = false;
        },
        cancelDept: function(){
            vm.dialogDept = false;
        },
        showPaper: function (id) {
            vm.paperDialog = true;
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/configure/show/"+id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionList = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        deleteOne: function(index){
            vm.exerciseConfigure.list.splice(index, 1);
        },
        dele: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/configure/delete/"+id,
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.refresh();
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        toHome:function () {
            parent.location.reload()
        },
        closeConfig: function () {
            vm.config = {
                topicId: '',
                topicName: '',
                primaryNum: 0,
                middleNum: 0,
                seniorNum: 0,
                topic: null,
                total: 0
            };
            vm.addConfigFlag = false;
        },
        chooseUser: function () {
            //选择人员
            this.dialogUser = true;
        },
        handleDeptNodeClick: function (data) {
            this.exerciseConfigure.orgCode = data.orgCode;
            this.reloadUser();
        },
        cancelDept: function () {
            this.dialogDept = false;
        },
        searchUser: function () {
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
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode = data.orgCode;
            this.reloadUser();
        },
        confimUser: function () {
            //  userNames
            this.dialogUser = false;
        },
        cancelUser: function () {
            this.dialogUser = false;
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage = val;
            this.reloadUser();
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "law/zTree", function (r) {
                ztree = $.fn.zTree.init($("#classTree"), setting, r.classifyList);
            })
        },
        userHandleSizeChange: function (val) {
            this.userForm.pageSize = val;
            this.reloadUser();
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage = val;
            this.reloadUser();
        },
        handleSelectionChange(val) {
            this.exerciseConfigure.userIds = [];
            var userNames = [];
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            for (var i = 0; i < val.length; i++) {
                this.exerciseConfigure.userIds.push(val[i].id);
                userNames.push(val[i].userName)
            }
            this.exerciseConfigure.userNames = userNames.join();

        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        // 查询
        onSubmit: function () {

        },
    },
    created: function () {
        this.$nextTick(function () {
        this.refresh();
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.topicList = result.topicList;
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.deptData = result.orgList;
                        vm.userData = result.orgList;
                        // 默认展开第一级
                        vm.userData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });

            this.reloadUser();
     });
    }
});