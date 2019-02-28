// 配置ID
var configureId = getUrlParam('id');
// 练习任务Id
var id = getUrlParam('taskId');

var vm = new Vue({
    el: "#app",
    data: {
        oneOptionScore: 1,
        multiOptionScore: 2,
        checkingScore: 2,
        oneOptiontTotalScore: 50,
        multiOptiontsTotalScore: 10,
        checkingTotalScore: 10,
        oneOptionList: [],
        multiOptionsList: [],
        checkingList: [],
        paperName: '卷子名称：第四季度执法资格考试',
        username:'王小明',
        time: 3,
        hours: 2,
        minutes: 20,
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
            var themeName = obj.themeName;

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
                themeName: themeName
            };

            vm.preserved.push(form);
        },
        save: function(){
            if(vm.preserved.length > 0){
                vm.preserve(0);
            }
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },
        commit: function(){
            if(vm.preserved.length > 0){
                vm.preserve(1);
            } else {
                vm.tj();
            }
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },
        tj: function(){
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/paper/commit/" + id,
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
                configureId: configureId,
                id: id,
                limit: vm.limit,
                page: vm.page
            };
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/paper/paper",
                data: obj,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionList = result.page.list;
                        console.log(vm.questionList)
                        vm.count = result.page.count;
                        id = result.page.id;
                        vm.preserved = [];
                    } else {
                        alert(result.msg);
                    }
                    vm.questionList.forEach(function (val) {
                        switch (val.questionType) {
                            case '10004':
                                vm.oneOptionList.push(val);
                                break;
                            case '10005':
                                vm.multiOptionsList.push(val);
                                break;
                            default:
                                return;
                        }
                    })
                }
            });
        },
        preserve: function (type) {
            // 保存答题情况
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/paper/preserve/" + type,
                data: JSON.stringify(vm.preserved),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        },
        //  样式转换方法
        changeFontSize: function (e) {
            var fontSpans = document.getElementsByClassName('font-size-span');
            for (var i=0;i<fontSpans.length;i++) {
                fontSpans[i].style.fontWeight = '300';
            }
            e.target.style.fontWeight = '600';
            // 改变整体页面字体大小，待
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.refresh();
        })
    }
    
});