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
        userExam:[],
        mulChoicCheck: [],
        sinChoicCheck: [],
        judge:[],
        subject: [],
        sinStarIcon:[],
        mulStarIcon:[],
        judStarIcon:[],
        subStarIcon:[],
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
                        vm.userExam = result.userExam;
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
                                if (_mul[i].isCollect===1){
                                    vm.mulStarIcon.push('el-icon-star-on');
                                } else{
                                    vm.mulStarIcon.push('el-icon-star-off');
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
                                if (vm.sinChoicList[i].isCollect===1){
                                   vm.sinStarIcon.push('el-icon-star-on');
                                } else{
                                   vm.sinStarIcon.push('el-icon-star-off');
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
                                if (vm.judgeList[i].isCollect===1){
                                    vm.judStarIcon.push('el-icon-star-on');
                                } else{
                                    vm.judStarIcon.push('el-icon-star-off');
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
                                if (vm.subjectList[i].isCollect===1){
                                    vm.subStarIcon.push('el-icon-star-on');
                                } else{
                                    vm.subStarIcon.push('el-icon-star-off');
                                }
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
        doCollect :function(index,type){
            var obj ={};
            var type;
            if(type==='10004'){
                obj={
                    key: vm.sinChoicList[index].id,
                    value : vm.sinChoicList[index].questionId
                }
                if(vm.sinChoicList[index].isCollect == 1){
                    type = 0;
                    vm.sinChoicList[index].isCollect = 0;
                    vm.sinStarIcon[index] = 'el-icon-star-off';
                } else {
                    type = 1;
                    vm.sinChoicList[index].isCollect = 1;
                    vm.sinStarIcon [index]= 'el-icon-star-on';
                }
            }else if (type ==='10005'){
                obj={
                    key: vm.mulChoicList[index].id,
                    value : vm.mulChoicList[index].questionId
                }
                if(vm.mulChoicList[index].isCollect == 1){
                    type = 0;
                    vm.mulChoicList[index].isCollect = 0;
                    vm.mulStarIcon[index] = 'el-icon-star-off';
                } else {
                    type = 1;
                    vm.mulChoicList[index].isCollect = 1;
                    vm.mulStarIcon[index] = 'el-icon-star-on';
                }
            }else if(type==='10006'){
                obj={
                    key: vm.judgeList[index].id,
                    value : vm.judgeList[index].questionId
                }
                if(vm.judgeList[index].isCollect == 1){
                    type = 0;
                    vm.judgeList[index].isCollect = 0;
                    vm.judStarIcon[index] = 'el-icon-star-off';
                } else {
                    type = 1;
                    vm.judgeList[index].isCollect = 1;
                    vm.judStarIcon[index] = 'el-icon-star-on';
                }
            }else if(type==='10007'){
                obj={
                    key: vm.subjectList[index].id,
                    value : vm.subjectList[index].questionId
                }
                if(vm.subjectList[index].isCollect == 1){
                    type = 0;
                    vm.subjectList[index].isCollect = 0;
                    vm.subStarIcon[index] = 'el-icon-star-off';
                } else {
                    type = 1;
                    vm.subjectList[index].isCollect = 1;
                    vm.subStarIcon[index] = 'el-icon-star-on';
                }
            }

            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/doCollect/"+type,
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
