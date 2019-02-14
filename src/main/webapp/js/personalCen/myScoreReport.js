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
        mulChoicCheck: [],
        sinChoicCheck: [],
        judge:[],
        subject: []

    },
    created: function () {
        this.$nextTick(function () {
            vm.viewExam();
        })

    },
    methods: {
        viewExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/viewExam",
                data: {
                    userExamId: userExamId
                },
                success: function (result) {
                    if (result.code === 0) {

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
