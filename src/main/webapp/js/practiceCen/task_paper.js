// 练习任务Id
var id = getUrlParam('id');
// 任务配置ID
var taskId = getUrlParam('taskId');
var isReview = getUrlParam('isReview');
var indexs = getUrlParam('indexs');
var total = getUrlParam('total');

var vm = new Vue({
    el: "#app",
    data: {
        question: {},
        answers: [],
        index: 1,
        isAnswer: false,
        indexs: ['A', 'B', 'C', 'D', 'E', 'F'],
        rightAnswerStr: '',
        userAnswerStr: '',
        isLast: false,
        isNew: true,
        isNext: false,
        title: '',
        starIcon: 'el-icon-star-off',
        isReview: false
    },
    methods: {
        sure: function (rightAnswer) {// 多选

            var userAnswer = vm.answers.join(',');
            vm.getTsxx(userAnswer, rightAnswer);
            if(vm.rightAnswerStr == vm.userAnswerStr){
                vm.question.right = 1;
            }else {
                vm.question.right = 0;
            }

            vm.isAnswer = true;

            if(vm.isNew && vm.answers.length > 0){
                vm.saveAnswer();
            }
        },
        affirm: function(index){

            // 单选和判断才会调用此方法
            var userAnswer = vm.answers;
            var rightAnswer = vm.question.answerId;
            if(userAnswer == rightAnswer){
                vm.question.right = 1;
            } else {
                vm.question.right = 0;
            }
            vm.isAnswer = true;

            vm.getTsxx(userAnswer, rightAnswer);

            if(vm.isNew && vm.answers.length > 0){
                vm.saveAnswer();
            }
        },
        getTsxx: function(userAnswer, rightAnswer){
            // userAnswer、rightAnswer可能多选
            var userArr = new Array();
            var rightArr = new Array();
            userArr = userAnswer.split(',');
            rightArr = rightAnswer.split(',');
            vm.rightAnswerStr = '';
            vm.userAnswerStr = '';

            for(var i=0; i<vm.question.answer.length; i++){
                if(rightArr.indexOf(vm.question.answer[i].id) > -1){
                    vm.rightAnswerStr += vm.indexs[i];
                }
                if(userArr.indexOf(vm.question.answer[i].id) > -1){
                    vm.userAnswerStr += vm.indexs[i];
                }
            }
        },
        save: function(){
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/task_index.html';
        },
        commit: function(){
            if(this.title !== '提 交'){
                var parentWin = window.parent;
                parentWin.document.getElementById("container").src = 'modules/exerciseCenter/task_index.html';
                return
            }
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/task/commit/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src = 'modules/exerciseCenter/task_index.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        refresh: function () {
            var obj = {
                taskId: taskId,
                id: id,
                index: vm.index,
                isReview: isReview
            };
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/task/paper",
                data: obj,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        if(result.question == null){
                            vm.isNext = true;
                            if(vm.index != 1){
                                vm.index--;
                            }
                            if(isReview != null && isReview != ''){
                                vm.$alert('当前为最后一题，是否结束本次回顾！', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        var parentWin = window.parent;
                                        parentWin.document.getElementById("container").src = 'modules/exerciseCenter/task_index.html';
                                    }
                                });

                            } else {

                                vm.$alert('您已完成本次练习，请结束本次练习！', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                    }
                                });
                            }

                        } else {
                            vm.question = result.question;
                            id = result.id;
                            // 判断此题目是否已经回答过
                            var userAnswer = vm.question.userAnswer;
                            var rightAnswer = vm.question.answerId;
                            if(vm.question.isCollect == 1){
                                vm.starIcon = 'el-icon-star-on';
                            } else {
                                vm.starIcon = 'el-icon-star-off';
                            }
                            if(userAnswer == null || userAnswer == ''){
                                vm.isAnswer = false;
                                vm.answers = [];
                                vm.rightAnswerStr = '';
                                vm.userAnswerStr = '';
                                vm.isNew = true;
                            } else {
                                vm.isAnswer = true;
                                if(vm.question.questionType == '10005'){
                                    vm.answers = userAnswer.split(',');
                                } else {
                                    vm.answers = userAnswer;
                                }
                                vm.getTsxx(userAnswer, rightAnswer);
                                vm.isNew = false;
                            }
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        saveAnswer: function(){
            var lx = typeof vm.answers;
            var str = '';
            if(lx == 'string'){
                str = vm.answers;
            } else {
                str = vm.answers.join(',');
            }
            // 保存答题信息
            var obj = {
                taskId: id,
                qId: vm.question.id,
                answer: str,
                right: vm.question.right,
                themeName: vm.question.themeName
            };

            $.ajax({
                type: "POST",
                url: baseURL + "exercise/task/preserve",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function (result) {
                    if (result.code === 0) {
                        vm.question.recordId = result.recordId;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        doCollect: function () {
            // 收藏题目
            var obj = {
                key: vm.question.id,
                value: vm.question.recordId
            };
            var type;
            if(vm.question.isCollect == 1){
                type = 0;
                vm.question.isCollect = 0;
                vm.starIcon = 'el-icon-star-off';
            } else {
                type = 1;
                vm.question.isCollect = 1;
                vm.starIcon = 'el-icon-star-on';
            }

            $.ajax({
                type: "POST",
                url: baseURL + "exercise/task/doCollect/"+type,
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        last: function () {
            if(vm.question.questionType == '10005' && vm.answers.length > 0 && !vm.isAnswer) {
                this.$confirm('请确认是否放弃本次答题？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    // 上一页
                    if(vm.index == 1){
                        vm.isLast = true;
                    } else {
                        vm.index--;
                        vm.refresh();
                        vm.isNext = false;
                    }
                });
            } else {
                // 上一页
                if(vm.index == 1){
                    vm.isLast = true;
                } else {
                    vm.index--;
                    vm.refresh();
                    vm.isNext = false;
                }
            }

        },
        next: function () {

            // 多选题，已选未提交
            if(vm.question.questionType == '10005' && vm.answers.length > 0 && !vm.isAnswer){
                this.$confirm('请确认是否放弃本次答题？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    // 下一页
                    if(total == vm.index){
                        vm.isNext = true;

                        var msg = "";
                        if(isReview != '' && isReview != null){
                            msg = '当前为最后一题，请结束本次错题回顾！';
                        } else {
                            msg = '当前为最后一题，请结束本次练习！';
                        }
                        vm.$alert(msg, '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                            }
                        });
                    } else {

                        vm.index++;
                        vm.refresh();
                        vm.isLast = false;
                    }
                })
            } else {
                // 下一页
                if(total == vm.index){
                    vm.isNext = true;

                    var msg = "";
                    if(isReview != '' && isReview != null){
                        msg = '当前为最后一题，请结束本次错题回顾！';
                    } else {
                        msg = '当前为最后一题，请结束本次练习！';
                    }
                    vm.$alert(msg, '提示', {
                        confirmButtonText: '确定',
                        callback: function () {
                        }
                    });
                } else {

                    vm.index++;
                    vm.refresh();
                    vm.isLast = false;
                }
            }

        },

    },
    created: function () {
        this.$nextTick(function () {
            if(isReview != null && isReview != ''){
                vm.title = '结束回顾';
                vm.isReview = true;
            } else {
                vm.title = '提 交';
                vm.isReview = false;
            }
            if(indexs != null && indexs != ''){
                vm.index = Number(indexs) + 1;
            }
            vm.refresh();
        })
    }
    
});