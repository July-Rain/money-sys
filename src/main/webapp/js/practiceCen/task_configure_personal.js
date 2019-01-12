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
        qtList: []
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
        },
        save: function () {
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
        },
        deletes: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "taskConfigure/delete/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        alert('删除成功');
                        vm.refresh();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        info: function (id) {
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
        })
    }
});