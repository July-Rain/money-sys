// 配置ID
var examConfigId = getUrlParam('id');
var examStatus = getUrlParam("examStatus");
var userExamId = getUrlParam("userExamId");
var vm = new Vue({
    el: '#app',
    data: {

        testForm: {
            sinChoic: [],
            mulChoic: [],
            judge: [],
            subject: []
        },
        userAnswerForm: {
            answerForm: [],
            userExamId: [],
            remainingExamTime: []
        },
        sinChoicList: [],
        mulChoicList: [],
        judgeList: [],
        subjectList: [],
        otherList: [],
        questionList: [],
        examConfig: [],
        user:[],
        page: 1,
        limit: 5,
        count: 0,
        maxtime: 60 * 60,//总时长
        minutes: 0,//显示分
        seconds: 0,//显示秒
        timer: null,//定时器
        mulChoicCheck: [],
        sinChoicCheck: [],
        judge:[],
        subject: []

    },
    created: function () {
        this.$nextTick(function () {
            if (examStatus == '0') {
                //开始考试
                vm.startExam();
            } else {
                //继续考试
                vm.continueExam();
            }
            vm.timer = setInterval("vm.CountDown()", 1000);
        })

    },
    methods: {
        startExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/startExam",
                data: {
                    examConfigId: examConfigId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.examConfig = result.examConfig;
                        vm.user = result.user;
                        vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        for (var i = 0; i < _mul.length; i++) {
                            vm.testForm.mulChoic.push([]);
                        }

                        vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        for (var i = 0; i < _mul.length; i++) {
                            vm.mulChoicCheck.push([]);
                        }
                        //单选
                        vm.sinChoicList = result.sinChoicList;

                        //多选
                        vm.mulChoicList = result.mulChoicList;
                        //判断
                        vm.judgeList = result.judgeList;
                        //主观
                        vm.subjectList = result.subjectList;
                        vm.maxtime = result.examConfig.examTime * 60;

                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        },
        continueExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/continueExam",
                data: {
                    userExamId: userExamId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.user = result.user;
                        vm.examConfig = result.examConfig;

                        vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        if(_mul){
                            for (var i = 0; i < _mul.length; i++) {
                                vm.testForm.mulChoic.push([]);

                                if(_mul[i].userAnswer){
                                    var _arr = _mul[i].userAnswer.split(",");
                                    vm.mulChoicCheck.push(_arr)
                                }else {
                                    vm.mulChoicCheck.push([])
                                }
                            }
                        }
                        //单选
                        vm.sinChoicList = result.sinChoicList;
                        console.info("vm.sinChoicList: "+vm.sinChoicList )
                        if(vm.sinChoicList){
                            for(var i=0;i<vm.sinChoicList.length;i++){
                                if(vm.sinChoicList[i].userAnswer){
                                    vm.sinChoicCheck.push(vm.sinChoicList[i].userAnswer);
                                }else{
                                    vm.sinChoicCheck.push('');
                                }
                            }
                        }
                        console.info("sinChoicList",vm.sinChoicList)
                        //多选
                        vm.mulChoicList = result.mulChoicList;
                        //判断
                        vm.judgeList = result.judgeList;
                        if(vm.judgeList){
                            for(var i=0;i<vm.judgeList.length;i++){
                                if(vm.judgeList[i].userAnswer){
                                    vm.judge.push(vm.judgeList[i].userAnswer);
                                }else{
                                    vm.judge.push('');
                                }
                            }
                        }
                        //主观
                        vm.subjectList = result.subjectList;
                        if(vm.subjectList){
                            for(var i=0;i<vm.subjectList.length;i++){
                                if(vm.subjectList[i].userAnswer){
                                    vm.subject.push(vm.subjectList[i].userAnswer);
                                }else{
                                    vm.subject.push('');
                                }
                                console.info("subject="+vm.subject);
                            }
                        }
                        vm.maxtime = result.userExam.remainingExamTime * 60;
                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        },
        fontS: function () {
            console.log(2)
            $("p,span").css("font-size", "16px");
            $(".text_s").css({"font-size": "16px", "font-weight": "bolder"});
            $(".text_m").css({"font-size": "18px", "font-weight": "normal"});
            $(".text_l").css({"font-size": "24px", "font-weight": "normal"})
        },
        fontM: function () {
            console.log(3)
            $("p,span").css("font-size", "18px");
            $(".text_s").css({"font-size": "16px", "font-weight": "normal"});
            $(".text_m").css({"font-size": "18px", "font-weight": "bolder"});
            $(".text_l").css({"font-size": "24px", "font-weight": "normal"})
        },
        fontL: function () {
            console.log(4)
            $("p,span").css("font-size", "24px");
            $(".text_s").css({"font-size": "16px", "font-weight": "normal"});
            $(".text_m").css({"font-size": "18px", "font-weight": "normal"});
            $(".text_l").css({"font-size": "24px", "font-weight": "bolder"})
        },
        submit: function () {
            vm.userAnswerForm.answerForm = [];
            vm.userAnswerForm.remainingExamTime = vm.maxtime/60;
            for (var i = 0; i < vm.sinChoicList.length; i++) {
                var obj = {
                    qId: vm.sinChoicList[i].id,
                    answer: vm.sinChoicCheck[i] == '' ? '' : vm.sinChoicCheck[i]
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.mulChoicList.length; i++) {
                var obj = {
                    qId: vm.mulChoicList[i].id,
                    answer: vm.mulChoicCheck[i] == '' ? '' : vm.mulChoicCheck[i].join(','),
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.judgeList.length; i++) {
                var obj = {
                    qId: vm.judgeList[i].id,
                    answer: vm.judge[i] == '' ? '' : vm.judge[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.subjectList.length; i++) {
                var obj = {
                    qId: vm.subjectList[i].id,
                    answer: vm.subject[i] == '' ? '' : vm.subject[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            console.info(vm.userAnswerForm);
            $.ajax({
                type: "POST",
                async: false,
                url: baseURL + "user/exam/commitExam",
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                        alert("提交成功");
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        save: function () {
            vm.userAnswerForm.answerForm = [];
            vm.userAnswerForm.remainingExamTime = vm.maxtime/60;
            console.info(vm.sinChoicCheck);

            for (var i = 0; i < vm.sinChoicList.length; i++) {
                var obj = {
                    qId: vm.sinChoicList[i].id,
                    answer: vm.sinChoicCheck[i] == '' ? '' : vm.sinChoicCheck[i]
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.mulChoicList.length; i++) {
                var obj = {
                    qId: vm.mulChoicList[i].id,
                    answer: vm.mulChoicCheck[i] == '' ? '' : vm.mulChoicCheck[i].join(','),
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.judgeList.length; i++) {
                var obj = {
                    qId: vm.judgeList[i].id,
                    answer: vm.judge[i] == '' ? '' : vm.judge[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.subjectList.length; i++) {
                var obj = {
                    qId: vm.subjectList[i].id,
                    answer: vm.subject[i] == '' ? '' : vm.subject[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            console.info(vm.userAnswerForm);
            $.ajax({
                type: "POST",
                async: false,
                url: baseURL + "user/exam/saveExam",
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                        alert("保存成功");
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        CountDown: function () {
            if (vm.maxtime >= 0) {
                vm.minutes = Math.floor(vm.maxtime / 60);
                vm.seconds = Math.floor(vm.maxtime % 60);
                if (vm.maxtime == 5 * 60) {
                    this.$notify.info({
                        title: '提示',
                        message: '距离考试结束时间还有五分钟！'
                    });
                };
                --vm.maxtime;
            } else {
                clearInterval(vm.timer);
                vm.$alert('时间到，结束!', '提示', {
                    confirmButtonText: '确定',
                    callback: function () {
                        vm.submit();
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                });

            }
        },
        shuffle: function (a) {
            var j, x, i;
            for (i = a.length; i; i--) {
                j = Math.floor(Math.random() * i);
                x = a[i - 1];
                a[i - 1] = a[j];
                a[j] = x;
            }
            return a;
        }
    }
});
