var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data:{
        /*dailyConfig:{
            id:"",
            specialKnowledgeId:"",
            createRule:"",
            obtainPoint:"",
            isShowLegal:"",
            createWay:"",//出题方式：随机，自定义
            isShowAnswer:"",//显示答案
            questionDifficulty:"",
            joinPeople:"",
            joinDept:"",
            questionId:"",
            beginTime:"",
            endTime:"",
            questionType:""
        },//每日一题属性*/
        questionForm:{
            id:"",
            comContent:"",
            questionDifficulty:"",
            questionType:"",
            answerId:"",
            legalBasis:"",
            answerDescrible:"",
            answer:[],
            answerChoiceNumber:"",//选项数量
            questionContent:""
        }//接收每日一题

    },
    methods: {

    },
    created: function(){
        // this.tableList.push(this.practiceConfiguration);
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/showDailyTest",
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionForm = result.question;//返回一道试题
                        if(result.questionForm.length == 0){
                            alert('每日一题已结束，请提交');
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});