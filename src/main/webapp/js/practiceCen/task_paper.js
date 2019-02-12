// 练习任务Id
var id = getUrlParam('id');
// 任务配置ID
var taskId = getUrlParam('taskId');

var vm = new Vue({
    el: "#app",
    data: {
        questionList: [],
        answers: [],
        page: 1,
        limit: 5,
        count: 0,
        preserved: []
    },
    methods: {
        sure: function (index) {// 多选
            var obj = vm.questionList[index];
            var answerId = obj.answerId;
            var checkList = obj.checkList;
            if(checkList.length == 0){
                alert('请选择选项后再提交');
                return;
            }

            var arr = new Array();
            arr = answerId.split(',');

            if(arr.length != checkList.length){
                obj.right = 0;
                return;
            }

            obj.right = 1;
            for(var i=0; i<arr.length; i++){
                if(checkList.indexOf(arr[i]) == -1){
                    obj.right = 0;
                    break;
                }
            }

            var form = {
                taskId: id,
                qId: obj.id,
                answer: checkList.join(','),
                right: obj.right,
                themeName: obj.themeName
            };

            vm.preserved.push(form);
        },
        affirm: function(index){
            var obj = vm.questionList[index];
            var answerId = obj.answerId;
            var userAnswer = obj.userAnswer;

            if(userAnswer == answerId){
                obj.right = 1;
            } else {
                obj.right = 0;
            }

            var form = {
                taskId: id,
                qId: obj.id,
                answer: userAnswer,
                right: obj.right,
                themeName: obj.themeName
            };

            vm.preserved.push(form);
        },
        save: function(){
            if(vm.preserved.length > 0){
                vm.preserve(0);
            }
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/task_index.html';
        },
        commit: function(){
            if(vm.preserved.length > 0){
                vm.preserve(1);
            } else {
                vm.tj();
            }
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/task_index.html';
        },
        tj: function(){
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/task/commit/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (event) {
            vm.limit = event;
            if(vm.preserved.length > 0){
                vm.preserve(0);
            }
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.page = event;
            if(vm.preserved.length > 0){
                vm.preserve(0);
            }
            vm.refresh();
        },
        refresh: function () {
            var obj = {
                taskId: taskId,
                id: id,
                limit: vm.limit,
                page: vm.page
            };
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/task/paper",
                data: obj,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionList = result.result.list;
                        vm.count = result.result.count;
                        id = result.result.id;
                        vm.preserved = [];
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        preserve: function (type) {
            // 保存答题情况
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/task/preserve/" + type,
                data: JSON.stringify(vm.preserved),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

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
        })
    }
    
});