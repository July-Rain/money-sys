var id = getUrlParam('id');
var isReview = getUrlParam('isReview');
var indexs = getUrlParam('indexs');

var vm = new Vue({
    el: '#app',
    data:{
        question: {
            questionType: '',
        },
        answers: [],
        isAnswer: false,
        rightAnswerStr: '',
        userAnswerStr: '',
        indexs: ['A', 'B', 'C', 'D', 'E', 'F'],
        isLast: false,
        isNew: true,
        isNext: false,
        index: 1,
        title: '',
        starIcon: 'el-icon-star-off'
    },
    methods: {
        getQuestion: function (type) {
            var _index = 1;
            if(type == 0){
                _index = vm.index - 1;
            } else {
                _index = vm.index + 1;
            }
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/getQuestion",
                contentType: "application/json",
                data: {
                    id: id,
                    index: _index,
                    isReview: isReview
                },
                success: function (result) {
                    if (result.code === 0) {
                        if(result.question == null){
                            vm.isNext = true;
                            if(isReview != null && isReview != ''){
                                vm.$alert('当前为最后一题，是否結束本次回顾！', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
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
                            if(type == 0){
                                vm.index--;
                            } else {
                                vm.index++;
                            }
                            vm.question = result.question;
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
        affirm: function(){
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
        last: function () {
            // 上一页
            if(vm.index == 1){
                vm.isLast = true;
            } else {
                vm.getQuestion(0);
                vm.isNext = false;
            }
        },
        next: function () {
            // 下一页
            vm.getQuestion(1);
            vm.isLast = false;
        },
        commit: function () {
            // 结束本次练习
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/random_index.html';
        },
        sure: function (rightAnswer) {
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
        saveAnswer: function () {
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
                url: baseURL + "exercise/random/saveAnswer",
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
                url: baseURL + "exercise/random/doCollect/"+type,
                data: JSON.stringify(obj),
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
    created: function(){
        this.$nextTick(function () {
            if(isReview != null && isReview != ''){
                vm.title = '结束回顾';
            } else {
                vm.title = '结束本次练习';
            }
            if(indexs != null && indexs != ''){
                vm.index = Number(indexs) + 1;
            }
            vm.getQuestion();
        })
    }
});