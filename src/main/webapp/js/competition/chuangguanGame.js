/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */


var vm = new Vue({
    el: '#app',
    data: {

        textmag:"",
        //一共几大关
        allBignum:"1",
        //现在第几大关
        nowBignum:"1",
        // 当前大关多少小关
        allLitnum:"1",
        //当前第几小关
        nowLitnum:"1",

        // tableData: [],//表格数据
        dialogBegin: false,//准备好了吗 提示弹出框
        dialogQuestion:false,//开始答题  弹出
        closedialog:false, //隐藏右上角X
        dialogerror:false,//答错提示
        afterAnswer:false,//答题正确后
        title: "",//弹窗的名称
        answers:[],
        radio_disabled: false,
       //题目集合
        QuestionList:[
            // Question={id:""},
        ],
        //题目
        Question:{},
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {

        //返回首页跳转
        tomain:function()
        {
            parent.location.href =baseURL+"main.html";
        },

        //接受挑战
        begin:function()
        {
            //先随便写写  去获取题目
            vm.dialogerror=false;//关闭错误提示框
            vm.dialogBegin = false;//关闭消息框
            vm.dialogQuestion=true;//打开答题框
            vm.radio_disabled = false;//让答题框可选
            vm.answers=[],//答案集合制空
            //题目集合
            vm.QuestionList=[],
            //题目
            vm.Question={},

            $.ajax({
                type: "POST",
                url: baseURL + 'recruitCheckpointConfiguration/getQuestByids',
                dataType: "json",
                async:false,
                // data:{"id": row.id},
                success: function (result) {

                    if (result.code === 0) {
                        //将查到的所有题目交给了题目数组
                        vm.QuestionList= result.Questlist;
                    } else {
                        alert(result.msg);
                    }
                }
            });


           //接下来我要给4个属性赋值
            //一共几大关
            vm.allBignum="1";
            //现在第几大关
            vm.nowBignum="1";
            // 当前大关多少小关
            vm.allLitnum="2";
            //当前第几小关
            vm.nowLitnum="1";

            vm.Question=vm.QuestionList[Number(vm.nowLitnum)-1];//重题目集合中把题目取出来

            console.info(vm.Question);


        },
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            var answer = vm.answers[0];

            //如果答对了
            if(answer==answerId)
            {
                vm.questionYes();
            }
            else
            {
               vm.questionError('error');
            }

        },

        //答错事件
        questionError:function(type)
        {
            // type有error和over  error是答错 走这个方法  over是主动放弃不答了 走这个方法   因为主动不答还要涉及到积分获取

            if(type=="error")
            {
                vm.textmag="很遗憾！答错了,闯关结束！是否继续";
            }
            else if(type=="over")
            {
                vm.textmag="您已主动放弃！闯关结束，成绩为第"+vm.nowBignum+"大关的第"+vm.nowBignum+"小关，是否开启新一轮闯关";
            }
            vm.dialogQuestion=false;
            vm.dialogerror=true;
        },
        //答对事件
        questionYes:function()
        {
            vm.afterAnswer=true;//答题正确后

        },
        //继续答题事件
        goon:function()
        {
            vm.afterAnswer=false;//继续答题
            vm.radio_disabled = false;//让答题框可选

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
            if((Number(vm.nowLitnum)+1)<=Number(vm.allLitnum))
            {
                //首先给nowLitnum赋值
                vm.nowLitnum=Number(vm.nowLitnum)+1;
                vm.Question=vm.QuestionList[Number(vm.nowLitnum)-1];//重题目集合中把题目取出来
            }
            //如果超过了 当前大关的 题目数
            else if((Number(vm.nowLitnum)+1)>Number(vm.allLitnum))
            {
                // 那就判断下大关的关系
                //如果大关没有超过没有超过
                if((Number(vm.nowBignum)+1)<=Number(vm.allBignum))
                {
                    // 开启新的一轮去查题目，并从新给4个属性赋值
                    //还要提示积分什么的。。啦啦啦啦的  先不写了
                }
                else if((Number(vm.nowBignum)+1)>Number(vm.allBignum))
                {
                    alert("闯关结束，你赢了");
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
        closeBegin: function () {
            vm.dialogBegin = false;
            // vm.reload();
        },
        closeQuestion: function () {
            vm.dialogQuestion = false;
            // vm.reload();
        },

        reload: function () {
            vm.dialogBegin=true;
        }
    }
});

