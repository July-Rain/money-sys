var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data: {
        textmag:"",
        myAnswer:"",//我的答案ID
        questionForm: {},
        answers:[],//我的答案
        //接收每日一题
        currentConfig:{
            id:"",
        },//当前每日一题配置
        singleAnswer:{
          id:""
        },
        qtOption: [],//题目类型
        showDetail:true,//展示细节
        answerShow: false,//是否显示答案
        legalBasisShow:false,//显示法律依据
        radioData: "",
        radio_disabled: false,
        checkboxdisabled:false,//多选情况下 复选框 禁用 选项
        daliyDate: null,
        dateList:[{
            day: '今日',
            date: '2019-01-19',
            active: true
        },{
            day: '周一',
            date: '2019-01-18',
            active:false
        },{
            day: '周日',
            date: '2019-01-17',
            active:false
        }]//左侧时间选项卡
    },
    methods: {
        onSubmit:function(answerId) {
            console.info(vm.answers)
            var answer = answerId.split(',');
            console.info(answer);
            //先判断个数一不一样
            if (answer.length != vm.answers.length) {
                //个数不一样，说明错了
                 vm.questionError();
                console.info("出错")
                return;
            } else if (answer.length == vm.answers.length) {
                for (var i = 0; i < answer.length; i++) {
                    if ($.inArray(answer[i], vm.answers) == "-1") {
                        //如果-1 就是不包含  就错了
                        vm.questionError();
                        console.info("出错")
                        alert("答错了");
                        return;
                    }
                }

                //对的
                 vm.questionYes();
                console.info("对的")
                alert("答对了");
            }
        },
        //单选题的答案结果
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            // var answer = vm.answers[0];
            // alert(vm.answers);
            // alert("我选的"+id);
            // alert("正确的"+answerId);
            //如果答对了
            if (id == answerId) {
                vm.questionYes();
                alert("答对了");
            } else {
                vm.questionError();
                alert("答错了");
            }

        },
        questionError:function() {
            //答错了  0分
            vm.recordScore('0');
            vm.oryesorno();//答完题目入库保存记录
        },
        questionYes:function(){
            //答对了 才会 添加分数啊
            vm.recordScore(vm.curretConfig.obtainPoint);
            vm.oryesorno();//答完题目入库保存记录
        },
        //不管答对答错 都要入库方法
        oryesorno:function () {
            vm.checkboxdisabled=true;
            console.info("!!!!!");
            console.info(vm.answers);
            console.info(vm.questionForm);
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/saveQuestion?myanswer='+vm.answers,
                contentType: "application/json",
                async:false,
                data: JSON.stringify(vm.questionForm),
                success: function (result) {

                }
            });

        },

        recordScore: function (sorce) {
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/recordScore',
                dataType: "json",
                async: false,
                data: {"sorce": sorce},
                success: function (result) {

                }
            });
        },
        toHome: function () {
            parent.location.reload()
        },
        dateChange: function (index) {
            for(var i = 0;i<vm.dateList.length;i++){
                vm.dateList[i].active = false
            }
            vm.dateList[index].active = true
        }
    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/newshowDailyTest",
                dataType: "json",
                success: function (result) {
                    console.info(result);
                    if (result.code === 0) {

                         vm.questionForm = result.question;//返回一道试题
                         // vm.questionForm.answer = result.question.answer;//答案
                         vm.curretConfig = result.currentConfig;//配置
                         console.info(vm.questionForm);
                        console.info(vm.questionForm.questionType);
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
                        if(result.myaswerid==null)  //如果没带我的答案过来  说明没做过
                        {

                        }
                        else//做过了题目
                        {
                            vm.checkboxdisabled=true;//多选情况下 复选框 禁用 选项
                            vm.radio_disabled=true;//单选 情况下  不能选

                            if(result.myaswerid.indexOf(",")>-1){
                                alert('str中包含,字符串');
                                var answer = result.myaswerid.split(',');
                                for (var i = 0; i < answer.length; i++) {
                                    vm.answers.push(answer[i]);
                                }
                            }

                            else
                            {
                                vm.answers = result.myaswerid;
                            }

                        }

                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});
