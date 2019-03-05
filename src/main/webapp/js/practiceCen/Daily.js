var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data: {
        questionForm: {},
        answers:[],//我的答案
        radio_disabled: false,
        daliyDate: null,
        dateList: [],// 左侧时间选项卡
        isShowAnswer: '0',
        showAnswer: false,
        starIcon: 'el-icon-star-off',
        collectMsg: '收藏',
        rightAnswerStr: '',
        userAnswerStr: '',
        indexs: ['A', 'B', 'C', 'D', 'E', 'F'],
        todayDate: null
    },
    methods: {
        showTips: function(){
            this.$notify.info({
                title: '提示',
                message: vm.questionForm.answerDescrible,
                duration: 0,
                position: 'bottom-right',
                offset: 100
            });
        },
        doDate: function(){
            if(vm.daliyDate != '' && vm.daliyDate != null){
                vm.getQuestion(vm.daliyDate);
                for(var i = 0;i<vm.dateList.length;i++){
                    if(vm.dateList[i].date == vm.daliyDate){
                        vm.dateList[i].active = true;
                    } else {
                        vm.dateList[i].active = false;
                    }
                }
            } else {
                vm.getQuestion(vm.todayDate);
                if(vm.dateList[i].date == vm.todayDate){
                    vm.dateList[i].active = true;
                } else {
                    vm.dateList[i].active = false;
                }
            }

        },
        doCollect: function(){
            // 收藏题目
            var obj = {
                key: vm.questionForm.id,
                value: vm.questionForm.recordId
            };
            var type;
            if(vm.questionForm.isCollect == 1){
                type = 0;
                vm.questionForm.isCollect = 0;
                vm.starIcon = 'el-icon-star-off';
            } else {
                type = 1;
                vm.questionForm.isCollect = 1;
                vm.starIcon = 'el-icon-star-on';
            }

            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/doCollect/"+type,
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
        toHome: function () {
            parent.location.reload()
        },
        dateChange: function (index) {
            for(var i = 0;i<vm.dateList.length;i++){
                vm.dateList[i].active = false;
            }
            vm.dateList[index].active = true;
            vm.getQuestion(vm.dateList[index].date);
        },
        getQuestion: function (date) {
            // 根据日期请求题目
            $.ajax({
                type: "GET",
                url: baseURL + "dailyQuestion/getQuestion",
                data:{
                    date: date
                },
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionForm = result.question;
                        if(result.question != null){
                            vm.isShowAnswer = result.isShowAnswer;

                            var userAnswer = vm.questionForm.userAnswer;
                            var type = vm.questionForm.questionType;
                            if(vm.questionForm.isCollect == '1'){
                                vm.starIcon = 'el-icon-star-on';
                            } else {
                                vm.starIcon = 'el-icon-star-off';
                            }
                            if(type == '10005'){
                                // 多选题
                                vm.answers = userAnswer.split(',');
                            } else {
                                vm.answers = userAnswer;
                            }
                            vm.getTsxx(userAnswer, vm.questionForm.answerId);
                            vm.showAnswer = true;
                        } else {

                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        commit: function () {
            // 提交
            if(vm.answers.length == 0){
                vm.$alert('请选择您的答案！', '提示', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return;
                    }
                });
            }
            var lx = typeof vm.answers;
            var str = '';
            if(lx == 'string'){
                str = vm.answers;
            } else {
                str = vm.answers.join(',');
            }

            vm.getTsxx(str,vm.questionForm.answerId);

            var obj = {
                qId: vm.questionForm.id,
                answer: str,
                right: vm.questionForm.right,
                themeName: vm.questionForm.themeName
            };

            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/saveAnswer",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionForm.recordId = result.recordId;
                        vm.questionForm.isCollect = 0;
                    } else {
                        alert(result.msg);
                    }
                }
            });


        },
        getTsxx: function(userAnswer, rightAnswer){
            // userAnswer、rightAnswer可能多选
            var userArr = new Array();
            var rightArr = new Array();
            userArr = userAnswer.split(',');
            rightArr = rightAnswer.split(',');
            vm.rightAnswerStr = '';
            vm.userAnswerStr = '';

            for(var i=0; i<vm.questionForm.answer.length; i++){
                if(rightArr.indexOf(vm.questionForm.answer[i].id) > -1){
                    vm.rightAnswerStr += vm.indexs[i];
                }
                if(userArr.indexOf(vm.questionForm.answer[i].id) > -1){
                    vm.userAnswerStr += vm.indexs[i];
                }
            }

            if(vm.rightAnswerStr == vm.userAnswerStr){
                vm.questionForm.right = 1;
            }else {
                vm.questionForm.right = 0;
            }
            vm.showAnswer = true;
        },
    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "dailyQuestion/dateList",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dateList = result.dateList;
                        vm.todayDate = result.today;
                        vm.getQuestion(vm.todayDate);
                    } else {
                        alert(result.msg);
                    }
                }
            });


        })
    }

});
