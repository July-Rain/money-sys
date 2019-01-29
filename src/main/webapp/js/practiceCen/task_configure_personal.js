var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0,
            source: 0
        },
        dialogConfig: false,
        configureEntity: {
            name: '',
            difficultys: [],
            classifys: [],
            types: [],
            topics: [],
            themeName: ''
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
        title: ''
    },
    methods: {
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
            vm.title = "配置任务信息";
        },
        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    if(vm.configureEntity.topics.length == 1){

                        var tid = vm.configureEntity.topics[0];
                        for(var i=0; i<vm.topicList.length; i++){
                            if(vm.topicList[i].key == tid){
                                vm.configureEntity.themeName = vm.topicList[i].value;
                                break;
                            }
                        }
                    } else {
                        vm.configureEntity.themeName = '综合类';
                    }
                    $.ajax({
                        type: "POST",
                        url: baseURL + "taskConfigure/save/0",
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
                                    themeName: ''
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
                themeName: ''
            };
            vm.dialogConfig = false;
            vm.isEdit = false;
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
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
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
                        if(result.info.difficulty != null){
                            vm.configureEntity.difficultys = result.info.difficulty.split(',');
                        }
                        if(result.info.classify != null){
                            vm.configureEntity.classifys = result.info.classify.split(',');
                        }
                        if(result.info.type != null){
                            vm.configureEntity.types = result.info.type.split(',');
                        }
                        if(result.info.themeId != null){
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
        getDiff: function (row, column) {
            var msg = '';
            var str = row.difficulty;
            if(str != null && str != ''){
                var arr = new Array();
                arr = str.split(',');
                for(var i=0; i<arr.length; i++){
                    if(arr[i] == '10001'){
                        msg += "初级，";
                    } else if(arr[i] == '10002'){
                        msg += "中级，";
                    } else {
                        msg += "高级，";
                    }
                }
                msg = msg.substr(0, msg.length -1);
            } else {
                msg = "不限";
            }

            return msg;
        },
        getClass: function (row, column) {
            var msg = "";
            var classify = row.classify;
            if(classify != null && classify != ''){
                var arr = new Array();
                arr = classify.split(',');
                for(var i=0; i<arr.length; i++){
                    if(arr[i] == 0){
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
            if(type != null && type != ''){
                var arr = new Array();
                arr = type.split(',');
                for(var i=0; i<arr.length; i++){
                    if(arr[i] == 10004){
                        msg += "单选，";
                    } else if(arr[i] == 10005){
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
        cancle: function(){
            vm.closeDia();
        },
        edit: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "taskConfigure/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.configureEntity = result.info;
                        if(result.info.difficulty != null){
                            vm.configureEntity.difficultys = result.info.difficulty.split(',');
                        }
                        if(result.info.classify != null){
                            vm.configureEntity.classifys = result.info.classify.split(',');
                        }
                        if(result.info.type != null){
                            vm.configureEntity.types = result.info.type.split(',');
                        }
                        if(result.info.themeId != null){
                            vm.configureEntity.topics = result.info.themeId.split(',');
                        }
                        vm.dialogConfig = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
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
        })
    }
});