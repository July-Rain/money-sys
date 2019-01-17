var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data: {
        textmag:"",
        myAnswer:"",//我的答案ID
        questionForm: {
            id: "",
            answerId:"",
            legalBasis:"",
            answerDescrible:"",
            answer:[],
            questionType:""
        },
        answers:[],//我的答案
        //接收每日一题
        currentConfig:{
            id:"",
        },//当前每日一题配置
        singleAnswer:{
          id:""
        },
        qtOption: [],//题目类型
        showDetail:false,//展示细节
        answerShow: false,//是否显示答案
        legalBasisShow:false,//显示法律依据
        radioData: ""
    },
    methods: {
        onSubmit:function(answerId) {
            console.info(answerId);
            console.info("我的答案",vm.answers);//我的答案
            var myAnswer = vm.answers.split(',');
            var answer = answerId.split(',');

            //先判断个数一不一样
            if(answer.length!=vm.answers.length){
                alert("错误答案！")
                return;
            }
            else if(answer.length==vm.answers.length)
            {
                for (var i = 0; i < answer.length; i++){
                    alert(777777777)
                    console.info(answer[i]);
                    console.info(vm.myAnswer);
                    if($.inArray(answer[i], vm.myAnswer)=="-1"){
                        alert("答案不匹配")
                    }else{
                        alert("答对了")
                    }
                }
            }
            this.showDetail=true;
        },
        questionError:function(type)
        {
            // type有error和over  error是答错 走这个方法  over是主动放弃不答了 走这个方法   因为主动不答还要涉及到积分获取
            vm.oryesorno();//答完题目入库保存记录
            vm.textmag="很遗憾！答错了,闯关结束！是否继续";

            vm.dialogQuestion=false;
            vm.dialogerror=true;
        },
        questionYes:function(){
            // questionForm
        },
        //不管答对答错 都要入库方法
        oryesorno:function () {
            alert("保存入库")
            /*$.ajax({
                type: "POST",
                url: baseURL + 'recruitConfiguration/saveQuestion?myanswer='+vm.answers,
                contentType: "application/json",
                async:false,
                data: JSON.stringify(vm.Question),
                success: function (result) {

                }
            });*/

        },
        toHome: function () {
            parent.location.reload()
        }
        /*onSubmit:function () {
            console.log(vm.questionForm.answer);
            this.showDetail=true;
        }*/
    },
    created: function () {

        // this.tableList.push(this.practiceConfiguration);
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/showDailyTest",
                dataType: "json",
                success: function (result) {
                    console.info(result);
                    if (result.code === 0) {
                        console.info(result);
                         vm.questionForm = result.question;//返回一道试题
                         vm.questionForm.answer = result.question.answer;//答案
                         vm.curretConfig = result.currentConfig;//配置
                        if(result.currentConfig.isShowLegal=="1"){
                            vm.legalBasisShow=true;
                        }else{
                            vm.legalBasisShow=false;
                        }
                        if(result.currentConfig.isShowAnswer=="1"){
                            vm.answerShow=true;
                        }else{
                            vm.answerShow=false;
                        }
                        if(questionForm.questionDifficulty=="10001"){
                            questionForm.questionDifficulty = "初级";
                        }
                        if(questionForm.questionDifficulty=="10002"){
                            questionForm.questionDifficulty = "中级";
                        }
                        if(questionForm.questionDifficulty=="10003"){
                            questionForm.questionDifficulty = "高级";
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});