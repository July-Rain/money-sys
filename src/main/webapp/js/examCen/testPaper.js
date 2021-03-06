// 配置ID
document.oncontextmenu = function()
{
    return false;
}
var storage = window.sessionStorage;
var examConfigId = storage.getItem("id");
var examStatus = storage.getItem("examStatus");
var userExamId = storage.getItem("userExamId");
var vm = new Vue({
    el: '#app',
    data: {
        // 修改后的数据
        // boolean
        isSubmit: false,
        isFavored: false,
        // 题目数据
        sinChoicList: [],
        mulChoicList: [],
        judgeList: [],
        subjectList: [],
        fillingList: [],

        // 答题数据
        mulChoicCheck: [],
        sinChoicCheck: [],
        judge:[],
        subject: [],
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
        // bar
        barData: [
            {
                href: "#oneOption",
                questionType: '单选题',
                currentFinishedNum: 0,
                totalNum: 0
            },
            {
                href: "#multiOptions",
                questionType: '多选题',
                currentFinishedNum: 0,
                totalNum: 0
            },
            {
                href: "#checking",
                questionType: '判断题',
                currentFinishedNum: 0,
                totalNum: 0
            },
            {
                href: "#expressing",
                questionType: '主观题',
                currentFinishedNum: 0,
                totalNum: 0
            }
        ],
        // 时间变量
        timer: null, //定时器
        displayTime: 0,
        lefttime: 0,
        leftHours: '00',
        leftMinutes: '00',
        leftSeconds: '00',
        consumedHours: '00',
        consumedMinutes: '00',
        consumedSeconds: '00',
        consumed: '',
        // 总分
        totalScore: 0,

        favoriteText: '收藏此题',
        paperName: '',
        username: '',
        answers: [],
        page: 1,
        limit: 5,
        count: 0,
        preserved: [],
        examConfig: [],
        goHomeButton: null,
        floatIcon: null,
        floatIcon: null,
        optionIndex:['A','B','C','D','E','F'],
        subjectScore: 0
        // 修改前的数据
        /*,
        otherList: [],
        user:[],
        page: 1,
        limit: 5,
        count: 0,*/
    },

    methods: {
        // 倒计时
        computeTime: function () {
            // 用计时器动态显示:间隔时间1s
            if (this.lefttime > 0 ) {
                this.lefttime -= 1000;
                var results = this.figureTime(this.lefttime);
                this.leftHours = results[0];
                this.leftMinutes = results[1];
                this.leftSeconds = results[2];            }
            // 3个小时用完,强制提交
           if (this.lefttime == 0) {
               this.timer = null;
               vm.submit();
               vm.$alert('时间到，考试结束！', '提示', {
                   confirmButtonText: '确定',
                   callback: function () {
                       var parentWin = window.parent;
                       parentWin.document.getElementById("container").src
                           = 'modules/examCen/userExam.html';
                   }
               });
           }
            // 考试时间还剩五分钟的提示
            if (this.lefttime == 5 * 60000) {
                vm.$confirm('距离考试结束时间还有五分钟！', '提示', {
                    confirmButtonText: '确定',
                    type: 'warning'
                }).catch(() => {
                });
            };
        },
        // 使用时间
        consumedTime: function () {
            var consumed =  this.displayTime*60000 - this.lefttime;
            var results = this.figureTime(consumed);
            this.consumedHours = results[0];
            this.consumedMinutes = results[1];
            this.consumedSeconds = results[2];
            this.consumedMinutes = Number(this.consumedHours)*60 + Number(this.consumedMinutes);
        },
        // 计算时间
        figureTime: function (time) {
            var hours = Math.floor(time/3600000); // 时
            var minutes = Math.floor((time-hours*3600000)/60000); // 分
            var seconds = Math.floor((time-hours*3600000-minutes*60000)/1000); // 秒
            hours<10?hours='0'+hours:hours;
            minutes<10?minutes='0'+minutes:minutes;
            seconds<10?seconds='0'+seconds:seconds;
            return [hours, minutes, seconds];
        },

        // 路径方法
        goBack: function () {
            vm.$confirm('请先保存试卷，直接退出将不会保存答题内容，点击确定按钮继续退出!','提示', {
                confirmButtonText: '确定',
                type: 'warning'
            }).then(function () {
                vm.updateRemainTime();
                var parentWin = window.parent;
                vm.goHomeButton.style.display = 'block';
                vm.floatIcon.style.display = 'flex';
                parentWin.document.getElementById("container").src
                    = 'modules/examCen/userExam.html';
            },() => {
                // 点击取消 ----- 加入这部分就可以了
            });
        },
        updateRemainTime : function(){
          <!--更新剩余时间-->
            vm.userAnswerForm.remainingExamTime = vm.lefttime/60000;
            $.ajax({
                type: "POST",
                async: false,
                url: baseURL + "user/exam/updateRemainTime",
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            vm.$confirm('请先保存试卷，直接返回首页将不会保存答题内容，点击确定按钮继续返回首页!','提示', {
                confirmButtonText: '确定',
                type: 'warning'
            }).then(function () {
                vm.updateRemainTime();
                parent.location.reload()
            }).catch(() => {
            });

        },
        // 改变字体大小
        changeFontSize: function (e) {
            var fontSpans = document.getElementsByClassName('font-size-span');
            var html = document.getElementById('html');

            if (e.target.innerHTML === '小') {
                html.style.fontSize = '10px';
            } else if (e.target.innerHTML === '中') {
                html.style.fontSize = '12px';
            } else if (e.target.innerHTML === '大') {
                html.style.fontSize = '13px';
            } else {
                return;
            }
            for (var i = 0; i < fontSpans.length; i++) {
                fontSpans[i].style.background = 'white';
            }
            e.target.style.background = '#eff8ff';
        },
        // 改变 bar 中元素被选择时的字体颜色 & 定位字体图标
        pickArea: function (e) {
            var des = null;
            if (e) {
                des = e.target;
            } else {
                des = document.getElementsByClassName('type')[0];
            }
            console.info(des)
            var aTags = document.getElementsByClassName('type');
            var icons = document.getElementsByClassName('iconfont icon-biaodiandidian');
            var icon = des.getElementsByClassName('iconfont')[0];
            for (var i = 0; i < aTags.length; i++) {
                aTags[i].style.color = 'black';
            }
            for (var i = 0; i < icons.length; i++) {
                icons[i].style.display = 'none';
            }
            des.style.color = '#1381e3';
            icon.style.display = 'inline-block';
        },
        // 收藏
        favor: function () {
            this.isFavored = !this.isFavored;
            if (this.isFavored) {
                this.favoriteText = '已收藏';
            } else {
                this.favoriteText = '收藏此题';
            }
        },

        // 提交试卷
        submit: function () {
            // 解除定时器
            this.timer = null;
            // this.isSubmit = true;
            // 展示使用时间
            this.consumedTime();
            vm.userAnswerForm.answerForm = [];
            vm.userAnswerForm.remainingExamTime = vm.lefttime/60000;
            for (var i = 0; i < vm.sinChoicList.length; i++) {
                var obj = {
                    qId: vm.sinChoicList[i].id,
                    answer: vm.sinChoicCheck[i] == '' ? '' : vm.sinChoicCheck[i]
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.mulChoicList.length; i++) {
                var obj = {
                    qId: vm.mulChoicList[i].id,
                    answer: vm.mulChoicCheck[i] == '' ? '' : vm.mulChoicCheck[i].join(','),
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.judgeList.length; i++) {
                var obj = {
                    qId: vm.judgeList[i].id,
                    answer: vm.judge[i] == '' ? '' : vm.judge[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.subjectList.length; i++) {
                var obj = {
                    qId: vm.subjectList[i].id,
                    answer: vm.subject[i] == '' ? '' : vm.subject[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            $.ajax({
                type: "POST",
                async: false,
                url: baseURL + "user/exam/commitExam",
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('您已交卷！', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                var parentWin = window.parent;
                                vm.goHomeButton.style.display = 'block';
                                vm.floatIcon.style.display = 'flex';
                                parentWin.document.getElementById("container").src
                                    = 'modules/examCen/userExam.html';
                            }
                        });


                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 保存
        save: function () {
            // 展示使用时间
            this.consumedTime();
            vm.userAnswerForm.answerForm = [];
            vm.userAnswerForm.remainingExamTime = vm.lefttime/60000;
            for (var i = 0; i < vm.sinChoicList.length; i++) {
                var obj = {
                    qId: vm.sinChoicList[i].id,
                    answer: vm.sinChoicCheck[i] == '' ? '' : vm.sinChoicCheck[i]
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.mulChoicList.length; i++) {
                var obj = {
                    qId: vm.mulChoicList[i].id,
                    answer: vm.mulChoicCheck[i] == '' ? '' : vm.mulChoicCheck[i].join(','),
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.judgeList.length; i++) {
                var obj = {
                    qId: vm.judgeList[i].id,
                    answer: vm.judge[i] == '' ? '' : vm.judge[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            for (var i = 0; i < vm.subjectList.length; i++) {
                var obj = {
                    qId: vm.subjectList[i].id,
                    answer: vm.subject[i] == '' ? '' : vm.subject[i],
                };
                vm.userAnswerForm.answerForm.push(obj)
            }
            console.info(vm.userAnswerForm);
            $.ajax({
                type: "POST",
                async: false,
                url: baseURL + "user/exam/saveExam",
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$confirm('保存成功!','提示', {
                            confirmButtonText: '确定',
                            type: 'success'
                        }).then(function () {
                            var parentWin = window.parent;
                            vm.goHomeButton.style.display = 'block';
                            vm.floatIcon.style.display = 'flex';
                            parentWin.document.getElementById("container").src
                                = 'modules/examCen/userExam.html';
                        }).catch(() => {
                        });

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        startExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/startExam",
                data: {
                    examConfigId: examConfigId
                },
                success: function (result) {
                    if (result.code === 0) {
                        console.log(result);
                        vm.examConfig = result.examConfig;
                        var endTime = new Date(Date.parse(vm.examConfig.endTime.replace(/-/g,  "/")))
                        var nowTime = new Date($.ajax({async: false}).getResponseHeader("Date"));
                        var timeDiff = endTime - nowTime;

                        vm.user = result.user;
                        vm.userAnswerForm.userExamId = result.userExam.id;
                        vm.paperName = result.examConfig.examName;
                        // 考试时间
                        vm.lefttime = result.examConfig.examTime*60000;
                        if (vm.lefttime>timeDiff){
                            vm.lefttime = timeDiff;
                        }
                        vm.displayTime = result.examConfig.examTime;

                        var _mul = result.mulChoicList;
                        _mul.forEach(function (val) {
                            vm.testForm.mulChoic.push([]);
                            vm.mulChoicCheck.push([]);
                        });
                        //单选
                        vm.sinChoicList = result.sinChoicList;
                        vm.barData[0].totalNum = vm.sinChoicList.length;
                        //多选
                        vm.mulChoicList = result.mulChoicList;
                        vm.barData[1].totalNum = vm.mulChoicList.length;
                        //判断
                        vm.judgeList = result.judgeList;
                        vm.barData[2].totalNum = vm.judgeList.length;
                        //主观
                        vm.subjectList = result.subjectList;
                        vm.subjectList.forEach(function (val) {
                            vm.subjectScore += val.score;
                        })
                        vm.barData[3].totalNum = vm.subjectList.length;

                        // 考试人员
                        vm.username = result.user.userName;
                        vm.computeTime();
                        vm.timer = setInterval(vm.computeTime,1000);
                        storage.setItem("examStatus",'1');
                        storage.setItem("userExamId", result.userExam.id);
                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        },
        continueExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/continueExam",
                data: {
                    userExamId: userExamId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.user = result.user;
                        vm.examConfig = result.examConfig;
                        var endTime = new Date(Date.parse(vm.examConfig.endTime.replace(/-/g,  "/")))
                        var nowTime = new Date($.ajax({async: false}).getResponseHeader("Date"));
                        var timeDiff = endTime - nowTime;

                        // 考试人员
                        vm.username = result.user.userName;
                        vm.displayTime = result.examConfig.examTime;
                        vm.lefttime = result.userExam.remainingExamTime * 60000;
                        if (vm.lefttime>timeDiff){
                            vm.lefttime = timeDiff;
                        }
                        vm.paperName = result.examConfig.examName;

                        vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        if(_mul){
                            for (var i = 0; i < _mul.length; i++) {
                                vm.testForm.mulChoic.push([]);
                                if(_mul[i].userAnswer){
                                    var _arr = _mul[i].userAnswer.split(",");
                                    vm.mulChoicCheck.push(_arr)
                                    vm.barData[1].currentFinishedNum ++;
                                }else {
                                    vm.mulChoicCheck.push([])
                                }
                            }
                        }
                        //单选
                        vm.sinChoicList = result.sinChoicList;
                        vm.barData[0].totalNum = vm.sinChoicList.length;
                        if(vm.sinChoicList){
                            for(var i=0;i<vm.sinChoicList.length;i++){
                                if(vm.sinChoicList[i].userAnswer){
                                    vm.sinChoicCheck.push(vm.sinChoicList[i].userAnswer);
                                    vm.barData[0].currentFinishedNum ++;
                                }else{
                                    vm.sinChoicCheck.push('');
                                }
                            }
                        }
                        //多选
                        vm.mulChoicList = result.mulChoicList;
                        vm.barData[1].totalNum = vm.mulChoicList.length;
                        //判断
                        vm.judgeList = result.judgeList;
                        vm.barData[2].totalNum = vm.judgeList.length;
                        if(vm.judgeList){
                            for(var i=0;i<vm.judgeList.length;i++){
                                if(vm.judgeList[i].userAnswer){
                                    vm.judge.push(vm.judgeList[i].userAnswer);
                                    vm.barData[2].currentFinishedNum ++;
                                }else{
                                    vm.judge.push('');
                                }
                            }
                        }
                        //主观
                        vm.subjectList = result.subjectList;
                        vm.barData[3].totalNum = vm.subjectList.length;
                        if(vm.subjectList){
                            for(var i=0;i<vm.subjectList.length;i++){
                                vm.subjectScore += vm.subjectList[i].score;
                                if(vm.subjectList[i].userAnswer){
                                    vm.subject.push(vm.subjectList[i].userAnswer);
                                    vm.barData[3].currentFinishedNum ++;
                                }else{
                                    vm.subject.push('');
                                }
                            }
                        }
                        vm.computeTime();
                        vm.timer = setInterval(vm.computeTime,1000);
                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        },

        // bar栏完成题数实时更新
        updateCommon: function (index, arr) {
            vm.barData[index].currentFinishedNum = 0;
            if (index!=1) {
                arr.forEach(function (val) {
                    if (val) {
                        vm.barData[index].currentFinishedNum ++;
                    }
                })
            } else {
                arr.forEach(function (val) {
                    if (val.length) {
                        vm.barData[index].currentFinishedNum ++;
                    }
                })
            }
        },
        update: function () {
            this.updateCommon(0, vm.sinChoicCheck);
            this.updateCommon(1, vm.mulChoicCheck);
            this.updateCommon(2, vm.judge);
            this.updateCommon(3, vm.subject);
        }
    },
    created: function () {
        this.$nextTick(function () {
            // 隐藏 返回首页 按钮
            vm.goHomeButton = window.parent.document.getElementsByClassName('header-right')[0];
            vm.goHomeButton.style.display = 'none';
            // 隐藏悬浮图标 (没有数据，尚未自测)
            vm.floatIcon = window.parent.document.getElementById('individual');
            vm.floatIcon.style.display = 'none';
            console.log(vm.floatIcon)
            if (examStatus == '0') {
                //开始考试
                vm.startExam();
            } else {
                //继续考试
                vm.continueExam();
            }
            setTimeout(function () {
                vm.pickArea()
            }, 400)
        });
    }
});
