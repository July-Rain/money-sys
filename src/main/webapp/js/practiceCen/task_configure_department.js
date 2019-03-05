var ztree = null;
var vm = new Vue({
    el: '#app',
    data: {
        idArr:[],// 部门Tree默认展开数据
        tableData: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0,
            source: 1,
            jssj:"",
            kssj:"",
            name: ''
        },
        dialogConfig: false,
        configureEntity: {
            name: '',
            difficultys: [],
            classifys: [],
            types: [],
            topics: [],
            themeName: '',
            users: [],
            depts: [],
            deptNames: '',
            userNames: ''
        },
        topicList: [],
        diffList: [],
        typeList: [],
        qtList: [],
        rules: {
            name: [
                {required: true, message: '请输入任务名称', trigger: 'blur'},
                {max: 25, message: '最大长度25', trigger: 'blur'}
            ]
        },
        isEdit: false,
        title: '',
        deptData: [],//部门树数据
        userData: [],//人员树数据
        defaultDeptProps: {
            children: 'child',
            label: 'orgName'
        },//部门树的默认格式
        defaultUserProps: {
            children: 'child',
            label: 'orgName'
        },//部门人员的默认格式
        userForm: {
            userCode: "",
            userName: "",
            orgCode: "",
            currPage: 1,
            pageSize: 10,
            totalCount: 0,
            identify: '',// 表明是用户
            userStatus:'2000'//查询有效的用户

        },//人员查询
        userTableData: [],//人员表格信息
        dialogDept: false,
        dialogUser: false,

    },
    methods: {
        onSubmit: function () {
            this.refresh();
        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
            this.refresh();
        },
        //序列号计算
        indexUserMethod: function (index) {
            return index + 1 + (vm.userForm.currPage - 1) * vm.userForm.pageSize;
        },
        confimUser: function () {
            //  userNames
            this.dialogUser = false;
        },
        cancelUser: function () {
            this.dialogUser = false;
        },
        handleSelectionChange(val) {
            this.configureEntity.users = [];
            var userNames = [];
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            for (var i = 0; i < val.length; i++) {
                this.configureEntity.users.push(val[i].id);
                userNames.push(val[i].userName)
            }
            this.configureEntity.userNames = userNames.join();

        },
        userHandleSizeChange: function (val) {
            this.userForm.pageSize = val;
            this.reloadUser();
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
        cancelDept: function () {
            this.dialogDept = false;
        },
        confimDept: function () {
            this.multipleDeptSelection = this.$refs.deptTree.getCheckedNodes();
            this.configureEntity.depts = [];
            var deptNames = [];
            for (var i = 0; i < this.multipleDeptSelection.length; i++) {
                this.configureEntity.depts.push(this.multipleDeptSelection[i].id);
                deptNames.push(this.multipleDeptSelection[i].orgName);
            }
            this.configureEntity.deptNames = deptNames.join();
            this.dialogDept = false;
        },
        handleCheckChange: function (data, checked, indeterminate) {

        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "taskConfigure/list",
                data: vm.formInline,
                contentType: "application/json",
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
        addConfigure: function () {
            vm.dialogConfig = true;
            vm.title = "新增配置信息";
        },
        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    if (vm.configureEntity.topics.length == 1) {
                        var tid = vm.configureEntity.topics[0];
                        for (var i = 0; i < vm.topicList.length; i++) {
                            if (vm.topicList[i].key == tid) {
                                vm.configureEntity.themeName = vm.topicList[i].value;
                                break;
                            }
                        }
                    } else {
                        vm.configureEntity.themeName = '综合类';
                    }
                    $.ajax({
                        type: "POST",
                        url: baseURL + "taskConfigure/save/1",
                        contentType: "application/json",
                        data: JSON.stringify(vm.configureEntity),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.dialogConfig = false;
                                vm.configureEntity = {
                                    name: '',
                                    difficultys: [],
                                    classifys: [],
                                    types: [],
                                    topics: [],
                                    themeName: '',
                                    users: [],
                                    depts: []
                                };
                                vm.refresh();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            });

        },
        closeDia: function () {
            vm.configureEntity = {
                name: '',
                difficultys: [],
                classifys: [],
                types: [],
                topics: [],
                themeName: '',
                users: [],
                depts: []
            };
            vm.dialogConfig = false;
        },
        cancle: function () {
            vm.closeDia();
        },
        deletes: function (id) {
            this.$confirm('确定删除此任务设置吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "taskConfigure/delete/" + id,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.code === 0) {
                            vm.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: function () {
                                }
                            });
                            vm.refresh();
                        } else {
                            alert(result.msg);
                        }
                    }
                });

            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        info: function (id) {
            vm.isEdit = true;
            vm.title = "配置信息";
            $.ajax({
                type: "GET",
                url: baseURL + "taskConfigure/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                        vm.configureEntity = result.info;
                        if (result.info.difficulty != null) {
                            vm.configureEntity.difficultys = result.info.difficulty.split(',');
                        }
                        if (result.info.classify != null) {
                            vm.configureEntity.classifys = result.info.classify.split(',');
                        }
                        if (result.info.type != null) {
                            vm.configureEntity.types = result.info.type.split(',');
                        }
                        if (result.info.themeId != null) {
                            vm.configureEntity.topics = result.info.themeId.split(',');
                        }
                        vm.dialogConfig = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        },
        edit: function (id) {
            vm.title = "编辑任务配置";
            vm.isEdit = false;
            $.ajax({
                type: "GET",
                url: baseURL + "taskConfigure/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.configureEntity = result.info;
                        if (result.info.difficulty != null) {
                            vm.configureEntity.difficultys = result.info.difficulty.split(',');
                        }
                        if (result.info.classify != null) {
                            vm.configureEntity.classifys = result.info.classify.split(',');
                        }
                        if (result.info.type != null) {
                            vm.configureEntity.types = result.info.type.split(',');
                        }
                        if (result.info.themeId != null) {
                            vm.configureEntity.topics = result.info.themeId.split(',');
                        }
                        if (result.info.depts != null) {
                            vm.configureEntity.depts = result.info.depts.split(',');
                        }
                        if (result.info.users != null) {
                            vm.configureEntity.users = result.info.users.split(',');
                        }
                        if (result.info.deptNames != null) {
                            vm.configureEntity.deptNames = result.info.deptNames;
                        }
                        if (result.info.userNames != null) {
                            vm.configureEntity.userNames = result.info.userNames;
                        }
                        vm.dialogConfig = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getDiff: function (row, column) {
            var msg = '';
            var str = row.difficulty;
            if (str != null && str != '') {
                var arr = new Array();
                arr = str.split(',');
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] == '10001') {
                        msg += "初级，";
                    } else if (arr[i] == '10002') {
                        msg += "中级，";
                    } else {
                        msg += "高级，";
                    }
                }
                msg = msg.substr(0, msg.length - 1);
            } else {
                msg = "不限";
            }

            return msg;
        },
        getClass: function (row, column) {
            var msg = "";
            var classify = row.classify;
            if (classify != null && classify != '') {
                var arr = new Array();
                arr = classify.split(',');
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] == 0) {
                        msg += "文字，";
                    } else {
                        msg += "视频，";
                    }
                }
                msg = msg.substr(0, msg.length - 1);
            } else {
                msg = "不限";
            }

            return msg;
        },
        getType: function (row, column) {
            var msg = "";
            var type = row.type;
            if (type != null && type != '') {
                var arr = new Array();
                arr = type.split(',');
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] == 10004) {
                        msg += "单选，";
                    } else if (arr[i] == 10005) {
                        msg += "多选，";
                    } else {
                        msg += "判断，";
                    }
                }
                msg = msg.substr(0, msg.length - 1);
            } else {
                msg = "不限";
            }

            return msg;
        },
        chooseDept: function () {
            //选择部门
            this.dialogDept = true;

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser = true;
        },
        indexMethod: function (index) {

            return index + 1 + (vm.formInline.page - 1) * vm.formInline.limit;
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.refresh();
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.topicList = result.topicList;
                        vm.diffList = result.diffList;
                        vm.typeList = result.typeList;
                        vm.qtList = result.qtList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            //加载部门数据
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
        })
    }
});