/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */


var vm = new Vue({
    el: '#app',
    data: {

        textmag: "",
        //一共几大关
        allBignum: "1",
        //现在第几大关
        nowBignum: "1",
        // 当前大关多少小关
        allLitnum: "1",
        //当前第几小关
        nowLitnum: "1",

        //存放 查出来的 大关信息集合
        BigGuanList: [],


        Score: "0",
        // tableData: [],//表格数据
        dialogBegin: false,//准备好了吗 提示弹出框
        dialogQuestion: false,//开始答题  弹出
        closedialog: false, //隐藏右上角X
        dialogerror: false,//答错提示
        dialogyes: false,//答对提示

        title: "",//弹窗的名称
        answers: [],
        radio_disabled: false,
        //题目集合
        QuestionList: [
            // Question={id:""},
        ],
        //题目
        Question: {},
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {

        //返回首页跳转
        tomain: function () {
            window.location.href = baseURL + "modules/competition/competeCenter.html";
        },

        //接受挑战
        begin: function () {
            //先随便写写  去获取题目
            vm.dialogerror = false;//关闭错误提示框
            vm.dialogBegin = false;//关闭消息框
            vm.dialogQuestion = true;//打开答题框
            vm.radio_disabled = false;//让答题框可选
            vm.dialogyes = false;//关闭回答正确提示框
            vm.Score = "0"; //开启新的一轮 将成绩执委0
            vm.answers = [];//答案集合制空
            //题目集合
            vm.QuestionList = [];
            //题目
            vm.Question = {};


            var storage=window.sessionStorage;
            var obj = storage.getItem("BigList");
            var BigList = eval('(' + obj + ')');


            vm.BigGuanList = BigList;
            //接下来我要给4个属性赋值
            //获得一共几大关
            // alert("一共"+result.data.length+"关");
            //一共几大关
            vm.allBignum = BigList.length;
            // alert("当前一进来肯定第一关，不用想");
            //现在第几大关
            vm.nowBignum = "1";
            // alert("当前第一关有"+result.data[0].recruitCheckpointConfigurationList.length+"小关");
            // 当前大关多少小关
            vm.allLitnum = BigList[0].recruitCheckpointConfigurationList.length;
            //当前第几小关
            // alert("当前一进来肯定第一关的第一小关，不用想");
            vm.nowLitnum = "1";

            var obj2 = storage.getItem("questionList");
            var questionList = eval('(' + obj2 + ')');
            vm.QuestionList = questionList;




            // $.ajax({
            //     type: "POST",
            //     url: baseURL + 'recruitConfiguration/findAll2',
            //     dataType: "json",
            //     async: false,
            //     // data:{"id": row.id},
            //     success: function (result) {
            //         if (result.code === 0) {
            //             console.info(result);
            //             vm.BigGuanList = result.data;
            //             //接下来我要给4个属性赋值
            //             //获得一共几大关
            //             // alert("一共"+result.data.length+"关");
            //             //一共几大关
            //             vm.allBignum = result.data.length;
            //             // alert("当前一进来肯定第一关，不用想");
            //             //现在第几大关
            //             vm.nowBignum = "1";
            //             // alert("当前第一关有"+result.data[0].recruitCheckpointConfigurationList.length+"小关");
            //             // 当前大关多少小关
            //             vm.allLitnum = result.data[0].recruitCheckpointConfigurationList.length;
            //             //当前第几小关
            //             // alert("当前一进来肯定第一关的第一小关，不用想");
            //             vm.nowLitnum = "1";
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });

            // $.ajax({
            //     type: "POST",
            //     url: baseURL + 'recruitConfiguration/getQuest',
            //     contentType: "application/json",
            //     async: false,
            //     data: JSON.stringify(vm.BigGuanList[0]),
            //     success: function (result) {
            //
            //         if (result.code === 0) {
            //             //将查到的所有题目交给了题目数组
            //             vm.QuestionList = result.data;
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });

            vm.Question = vm.QuestionList[Number(vm.nowLitnum) - 1];//重题目集合中把题目取出来


        },

        //跳大关 继续答题
        toBiglevel: function () {




            //先随便写写  去获取题目
            vm.dialogerror = false;//关闭错误提示框
            vm.dialogBegin = false;//关闭消息框
            vm.dialogQuestion = true;//打开答题框
            vm.radio_disabled = false;//让答题框可选
            vm.dialogyes = false;//关闭回答正确提示框
            vm.Score = getUrlParam('coinNum');
            vm.answers = [];//答案集合制空
            //题目集合
            vm.QuestionList = [];
            //题目
            vm.Question = {};


            var storage=window.sessionStorage;
            var obj = storage.getItem("BigList");
            var BigList = eval('(' + obj + ')');
            vm.BigGuanList = BigList;
            //接下来我要给4个属性赋值
            //一共几大关
            vm.allBignum = BigList.length;

            // 当前大关多少小关
            vm.allLitnum = BigList[0].recruitCheckpointConfigurationList.length;




            var obj2 = storage.getItem("questionList2");
            var questionList = eval('(' + obj2 + ')');
            vm.QuestionList = questionList;

            //一共几大关   不变
            // vm.allBignum="1";
            // //现在第几大关  进入下一大关了  要加一
            vm.nowBignum = Number(getUrlParam('index'));
            // // 当前大关多少小关  下表在上一行已经加了1了
            vm.allLitnum = vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList.length;
            // //当前第几小关
            vm.nowLitnum = "1";


            vm.Question = vm.QuestionList[Number(vm.nowLitnum) - 1];//重题目集合中把题目取出来
        },
        thisSubmit: function (answerId) {
            var answer = answerId.split(',');
            console.info(answer);
            //先判断个数一不一样
            if (answer.length != vm.answers.length) {
                //个数不一样，说明错了
                vm.questionError('error');
                console.info("出错")
                return;
            } else if (answer.length == vm.answers.length) {
                for (var i = 0; i < answer.length; i++) {
                    if ($.inArray(answer[i], vm.answers) == "-1") {
                        //如果-1 就是不包含  就错了
                        vm.questionError('error');
                        console.info("出错")
                        return;
                    }
                }

                //对的
                vm.questionYes();
                console.info("对的")
            }

        },
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            // var answer = vm.answers[0];
            // alert(vm.answers);
            // alert("我选的"+id);
            // alert("正确的"+answerId);
            //如果答对了
            if (id == answerId) {
                vm.questionYes();
            } else {
                vm.questionError('error');
            }

        },

        //答错事件
        questionError: function (type) {
            // type有error和over  error是答错 走这个方法  over是主动放弃不答了 走这个方法   因为主动不答还要涉及到积分获取

            if (type == "error") {
                vm.oryesorno();//答完题目入库保存记录

                //分数记录下去//答错了 也说明他进来过了  要有记录  分数只是 都是0   所得的关卡要减1才是 他实际答对的 关卡
                vm.recordScore(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowLitnum) - 1].id, vm.nowBignum, vm.nowLitnum, '0');
                vm.textmag = "很遗憾！答错了,闯关结束！";
            } else if (type == "over") {
                console.info(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowLitnum) - 1].id);
                //分数记录下去
                vm.recordScore(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowLitnum) - 1].id, vm.nowBignum, vm.nowLitnum, vm.Score);

                vm.textmag = "您已主动放弃！闯关结束，成绩为第" + vm.nowBignum + "大关的第" + vm.nowLitnum + "小关，获得积分为" + vm.Score ;
            }

            vm.dialogQuestion = false;
            vm.dialogerror = true;
        },

        //答对事件
        questionYes: function () {

            vm.getScore();//每次答完题后会有个分数累加   当然只限于答对
            vm.textmag = "恭喜你，回答正确,当前奖励积分" + vm.Score;
            vm.oryesorno();//答完题目入库保存记录
            vm.dialogyes = true;//答题正确后
        },


        //不管答对答错 都要入库方法
        oryesorno: function () {
            $.ajax({
                type: "POST",
                url: baseURL + 'recruitConfiguration/saveQuestion?myanswer=' + vm.answers,
                contentType: "application/json",
                async: false,
                data: JSON.stringify(vm.Question),
                success: function (result) {

                }
            });

        },
        //每次答完题后会有个分数累加   当然只限于答对
        getScore: function () {
            //首先 要获取当前的大关   然后 取当前的小关  里面的 分值这个属性
            vm.Score = Number(vm.Score) + Number(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowLitnum) - 1].crossingPoints);  //下表原因  要减一
        },

        //继续答题事件
        goon: function () {

            // alert("获得的积分"+vm.Score);
            vm.dialogyes = false;//继续答题
            vm.radio_disabled = false;//让答题框可选
            vm.answers = [];//答案集合制空
            // alert(vm.allBignum);
            // alert(vm.nowBignum);
            // alert(vm.allLitnum);
            // alert(vm.nowLitnum);

            //一共几大关
            // vm.allBignum="1";
            // //现在第几大关
            // vm.nowBignum="1";
            // // 当前大关多少小关
            // vm.allLitnum="2";
            // //当前第几小关
            // vm.nowLitnum="1";

            //先判断大关里面的小题有没有超过小题之和
            //如果没有超过
            if ((Number(vm.nowLitnum) + 1) <= Number(vm.allLitnum)) {
                //首先给nowLitnum赋值+

                vm.nowLitnum = Number(vm.nowLitnum) + 1;

                vm.Question = vm.QuestionList[Number(vm.nowLitnum) - 1];//重题目集合中把题目取出来
            }
            //如果超过了 当前大关的 题目数
            else if ((Number(vm.nowLitnum) + 1) > Number(vm.allLitnum)) {
                // 那就判断下大关的关系
                //如果大关没有超过没有超过
                if ((Number(vm.nowBignum) + 1) <= Number(vm.allBignum)) {
                    //xian提示他这一大关成功闯过
                    //获得多少积分  并且有无大关奖励  是否进入下一个大关
                    // alert("当前大关成功 下一大，你赢了");
                    //如果当前大关 没有大关通关奖励
                    if (vm.BigGuanList[Number(vm.nowBignum) - 1].markReward == "0") {
                        vm.$alert("恭喜你，通过第" + vm.nowBignum + "大关，该大关没有大关通关奖励分值,当前奖励积分" + vm.Score)
                    } else if (vm.BigGuanList[Number(vm.nowBignum) - 1].markReward == "1") {
                        vm.Score = Number(vm.Score) + Number(vm.BigGuanList[Number(vm.nowBignum) - 1].rewardScore);  //下表原因  要减一
                        vm.$alert("恭喜你，通过第" + vm.nowBignum + "大关，并获得大关通关奖励" + vm.BigGuanList[Number(vm.nowBignum) - 1].rewardScore + ",当前奖励积分" + vm.Score);
                    }

                    setTimeout(function () {
                        // 跳转闯关页面
                        window.location.href =baseURL+"modules/competition/rushLevel.html?coinNum="+vm.Score+"&index="+(Number(vm.nowBignum)+Number(1));
                    },3000)
                    //把分数和第几关传过去






                } else if ((Number(vm.nowBignum) + 1) > Number(vm.allBignum)) {
                    // alert("闯关结束，你赢了");
                    if (vm.BigGuanList[Number(vm.nowBignum) - 1].markReward == "0") {
                        console.info(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowBignum) - 1]);
                        vm.recordScore(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowLitnum) - 1].id, vm.nowBignum, vm.nowLitnum, vm.Score);
                        vm.$alert("恭喜你，通过所有大关，当前大关没有大关通关奖励分值,当前奖励积分" + vm.Score)
                    } else if (vm.BigGuanList[Number(vm.nowBignum) - 1].markReward == "1") {

                        vm.Score = Number(vm.Score) + Number(vm.BigGuanList[Number(vm.nowBignum) - 1].rewardScore);  //下表原因  要减一
                        vm.recordScore(vm.BigGuanList[Number(vm.nowBignum) - 1].recruitCheckpointConfigurationList[Number(vm.nowLitnum) - 1].id, vm.nowBignum, vm.nowLitnum, vm.Score);
                        vm.$alert("恭喜你，通过所有大关，并获得当前大关通关奖励" + vm.BigGuanList[Number(vm.nowBignum) - 1].rewardScore + ",当前奖励积分" + vm.Score);
                    }

                    vm.dialogQuestion = false;//关闭答题框


                }
            }


            // vm.Question=vm.QuestionList[Number(vm.nowLitnum)-1];//重题目集合中把题目取出来
        },

        // 保存和修改
        saveOrUpdate: function () {
        },
        add: function () {

        },
        del: function () {
        },
        update: function () {
        },
        recordScore: function (foreignKeyId, nowbig, nowlit, sorce) {
            $.ajax({
                type: "POST",
                url: baseURL + 'competitionRecord/recordScore',
                dataType: "json",
                async: false,
                data: {"foreignKeyId": foreignKeyId, "nowbig": nowbig, "nowlit": nowlit, "sorce": sorce},
                success: function (result) {

                }
            });
        },
        closeBegin: function () {
            vm.dialogBegin = false;
            // vm.reload();
        },
        closeQuestion: function () {
            vm.dialogQuestion = false;
            // vm.reload();
        },

        reload: function () {

           if(getUrlParam('index')=='1')
           {
               vm.begin();
           }
           else
           {
               vm.toBiglevel();
           }

        },
        dateFormat: function (row, column) {
            var daterc = arguments.length === 1 ? row + "" : row[column.property] + "";
            if (daterc != null) {
                var dateMat = new Date(parseInt(daterc.replace("/Date(", "").replace(")/", ""), 10));
                return dateMat.getFullYear() + "/" + (dateMat.getMonth() + 1) + "/" + dateMat.getDate();
            }

        }
    }
});




