var vm = new Vue({
    el: '#app',
    data:{
        tableData: [],
        dialogConfig: false,
        title: '',
        exercise: {
            difficulty: '',
            classify: '',
            type: '',
            diffName: '',
            className: '',
            typeName: '',
            topicName: ''
        },
        diffList: [],
        typeList: [],
        qtList: [],
        topicList: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0
        },
        diffs: [],
        classs: [],
        types: [],
        topics: []
    },
    methods: {
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        saveTask: function(){
            // 处理参数信息
            if(vm.diffs.length > 0){
                var idStr = "";
                var nameStr = "";
                for(var i=0; i<vm.diffs.length; i++){
                    idStr += "," + vm.diffs[i].key;
                    nameStr += "," + vm.diffs[i].value;
                }
                idStr = idStr.substring(1);
                nameStr = nameStr.substring(1);
                vm.exercise.difficulty = idStr;
                vm.exercise.diffName = nameStr;
            } else {
                vm.exercise.difficulty = '';
                vm.exercise.diffName = '不限';
            }

            if(vm.classs.length > 0){
                var idStr = "";
                var nameStr = "";
                for(var i=0; i<vm.classs.length; i++){
                    idStr += "," + vm.classs[i].key;
                    nameStr += "," + vm.classs[i].value;
                }
                idStr = idStr.substring(1);
                nameStr = nameStr.substring(1);
                vm.exercise.classify = idStr;
                vm.exercise.className = nameStr;
            } else {
                vm.exercise.classify = '';
                vm.exercise.className = '不限';
            }

            if(vm.types.length > 0){
                var idStr = "";
                var nameStr = "";
                for(var i=0; i<vm.types.length; i++){
                    idStr += "," + vm.types[i].key;
                    nameStr += "," + vm.types[i].value;
                }
                idStr = idStr.substring(1);
                nameStr = nameStr.substring(1);
                vm.exercise.type = idStr;
                vm.exercise.typeName = nameStr;
            } else {
                vm.exercise.type = '';
                vm.exercise.typeName = '不限';
            }

            if(vm.topics.length > 0){
                var idStr = "";
                var nameStr = "";
                for(var i=0; i<vm.topics.length; i++){
                    idStr += "," + vm.topics[i].key;
                    nameStr += "," + vm.topics[i].value;
                }
                idStr = idStr.substring(1);
                if(vm.topics.length > 1){
                    nameStr = '综合类';
                } else {
                    nameStr = nameStr.substring(1);
                }
                vm.exercise.topic = idStr;
                vm.exercise.topicName = nameStr;
            } else {
                vm.exercise.topic = '';
                vm.exercise.topicName = '不限';
            }

            // 保存配置，并跳转页面
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/random/start",
                contentType: "application/json",
                data: JSON.stringify(vm.exercise),
                success: function (result) {
                    if (result.code === 0) {
                        var id = result.id;
                        vm.jump(id);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        jump: function(id){
            // 跳转页面
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/random_answer.html?id='+id;
        },
        addExercise: function(){
            vm.title = '随机练习设置';
            this.dialogConfig = true;
            vm.diffs = [];
            vm.classs = [];
            vm.types = [];
            vm.topics = [];
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/list",
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
        toHome: function () {
            parent.location.reload()
        },
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page-1) * vm.formInline.limit;
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.reload();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.reload();
        },
        getRate: function (row, column) {
            var msg = '100%';

            var answerNum = row.answerNum;
            if(answerNum == 0){
                return msg;

            }else {
                var rightNum = row.rightNum;
                msg = rightNum/answerNum;
                msg = (msg * 100).toFixed(2);
                return msg + "%";
            }
        },
    },
    created: function(){
        this.$nextTick(function () {
            vm.reload();

            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.diffList = result.diffList;
                        vm.typeList = result.typeList;
                        vm.qtList = result.qtList;
                        vm.topicList = result.topicList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});