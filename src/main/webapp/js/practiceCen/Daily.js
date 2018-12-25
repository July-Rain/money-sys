var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data:{
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
        },//接收每日一题
        qtOption:[],//题目类型
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
                        console.info(result);
                        alert("415456");
                        vm.questionForm = result.question;//返回一道试题
                        vm.questionForm.answer = result.question.answer;
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