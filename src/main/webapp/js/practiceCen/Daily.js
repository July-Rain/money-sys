var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data: {
        questionForm: {
            "id": null,
            "comContent": "test0001",
            "questionDifficulty": "10001",
            "questionType": "2",
            "answerId": "2",
            "legalBasis": null,
            "answerDescrible": "答案",
            "answer": [
                {
                    "id": "24",
                    "questionContent": "这是选项A",
                    "questionId": "1001"
                },
                {
                    "id": "25",
                    "questionContent": "这是选项B",
                    "questionId": "1001"
                },
                {
                    "id": "26",
                    "questionContent": "这是选项C",
                    "questionId": "1001"
                }, {
                    "id": "27",
                    "questionContent": "这是选项D",
                    "questionId": "1001"
                }],
            "answerChoiceNumber": null
        },//接收每日一题
        qtOption: [],//题目类型
        answerShow: false,//是否显示答案
        radioData: ""
    },
    methods: {
        aaa(){
        }
    },
    created: function () {

        // this.tableList.push(this.practiceConfiguration);
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/showDailyTest",
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        // console.info("result",result);
                        // alert("415456");
                        // vm.questionForm = result.question;//返回一道试题
                        // vm.questionForm.answer = result.question.answer;
                        // if(result.questionForm.length == 0){
                        //     alert('每日一题已结束，请提交');
                        // }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});