// 配置ID
var storage=window.sessionStorage;
var examConfigId = storage.getItem('examConfigId');
var checkStatus = storage.getItem("checkStatus");
var userExamId = storage.getItem("userExamId");
var checkExamUserId = storage.getItem("checkExamUserId");
var checkExamId = storage.getItem("checkExamId");
console.info("checkStatus="+checkStatus+"examConfigId="
    +examConfigId+"userExamId="+userExamId+"checkExamUserId="+checkExamUserId+"  checkExamId="+checkExamId);

var vm = new Vue({
    el: '#app',
    data: {

        testForm: {
            subject: []
        },
        userAnswerForm: {
        },
        subjectList: [],
        examConfig:{},
        subject: [],
        subScore:[],
        checkStatus:''
    },
    created: function () {
        this.$nextTick(function () {
            vm.checkStatus = checkStatus;
            if (checkStatus == '0') {
                //开始考试
                vm.startCheckExam();
            } else if (checkStatus == '1') {
                //继续考试
                vm.startCheckExam();
            }else if (checkStatus =='3'){
                vm.auditCheckExam();
            }
        })

    },
    methods: {
        startCheckExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "check/exam/startCheckExam",
                data: {
                    userExamId: userExamId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.examConfig = result.examConfig;
                        //主观
                        vm.subjectList = result.subjectList
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
        continueCheckExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "check/exam/continueCheckExam",
                data: {
                    userExamId: userExamId,
                    checkExamUserId:checkExamUserId
                },
                success: function (result) {
                    if (result.code === 0) {

                        vm.examConfig = result.examConfig;
                        //主观
                        vm.subjectList = result.subjectList;
                        if(vm.subjectList){
                            for(var i=0;i<vm.subjectList.length;i++){
                                if(vm.subjectList[i].userAnsId){
                                    vm.subject.push(vm.subjectList[i].userAnsId);
                                }else{
                                    vm.subject.push('');
                                }
                                if(vm.subjectList[i].firCheckUserId = checkExamUserId){
                                    vm.subScore.push(vm.subjectList[i].firCheckScore);
                                }else if (vm.subjectList[i].secCheckUserId = checkExamUserId){
                                    vm.subScore.push(vm.subjectList[i].secCheckScore);
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
        submit: function () {
            vm.userAnswerForm.checkExamForm = [];
            vm.userAnswerForm.checkExamUserId = checkExamUserId;
            vm.userAnswerForm.userExamId = userExamId;
            vm.userAnswerForm.checkExamId = checkExamId;
            for (var i = 0; i < vm.subjectList.length; i++) {
                console.info(vm.subjectList[i]);
                var obj = {
                    queId: vm.subjectList[i].questionId,
                    score: vm.subScore[i] == '' ? '' : vm.subScore[i],
                };
                vm.userAnswerForm.checkExamForm.push(obj)
            }
            console.info(vm.userAnswerForm);
            var url ='';
            if (checkStatus=='3'){
                url = "check/exam/commitAuditExam";
            }else{
                url = "check/exam/commitCheckExam";
            }
            $.ajax({
                type: "POST",
                async: false,
                url: baseURL + url,
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                        alert("提交成功");
                        var parentWin = window.parent;
                        parent.location.href
                            =baseURL+'checkExamCen/checkExamMain.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        auditCheckExam : function () {
            $.ajax({
                type: "POST",
                url: baseURL + "check/exam/auditCheckExam",
                data: {
                    userExamId: userExamId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.examConfig = result.examConfig;
                        //主观
                        vm.subjectList = result.subjectList
                        /*if(vm.subjectList){
                            for(var i=0;i<vm.subjectList.length;i++){
                                if(vm.subjectList[i].userAnswer){
                                    vm.subject.push(vm.subjectList[i].userAnswer);
                                }else{
                                    vm.subject.push('');
                                }
                                console.info("subject="+vm.subject);
                            }
                        }
*/

                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        }
        // save: function () {
        //     vm.userAnswerForm.checkExamForm = [];
        //     vm.userAnswerForm.checkExamUserId = checkExamUserId;
        //     vm.userAnswerForm.userExamId = userExamId;
        //     vm.userAnswerForm.checkExamId = checkExamId;
        //     for (var i = 0; i < vm.subjectList.length; i++) {
        //         var obj = {
        //             queId: vm.subjectList[i].questionId,
        //             score: vm.subScore[i] == '' ? '' : vm.subScore[i],
        //         };
        //         vm.userAnswerForm.checkExamForm.push(obj)
        //     }
        //     console.info(vm.userAnswerForm);
        //     $.ajax({
        //         type: "POST",
        //         async: false,
        //         url: baseURL + "check/exam/saveCheckExam",
        //         data: JSON.stringify(
        //             vm.userAnswerForm
        //         ),
        //         datatype: "json",
        //         contentType: "application/json; charset=utf-8",
        //         success: function (result) {
        //             if (result.code === 0) {
        //                 alert("保存成功");
        //                 parent.location.href
        //                     =baseURL+'checkExamCen/checkExamMain.html';
        //             } else {
        //                 alert(result.msg);
        //             }
        //         }
        //     });
        // },
    }
});
