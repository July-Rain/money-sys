// 配置ID
var examConfigId = getUrlParam('id');
var examStatus = getUrlParam("examStatus");
var userExamId = getUrlParam("userExamId");
var vm = new Vue({
    el: '#app',
    data: {
        // 修改后的数据
        // boolean
        isSubmit: false,
        // 题目数据
        oneOptionList: [
            {
                id: "6",
                comContent: "我国的根本政治制度是__________。",
                questionDifficulty: "10001",
                questionType: "10004",
                answerId: "17",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 3,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "宪法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "16",
                        questionContent: "人民民主专政制",
                        questionId: "6"
                    },
                    {
                        id: "17",
                        questionContent: "人民代表大会制度",
                        questionId: "6"
                    },
                    {
                        id: "18",
                        questionContent: "社会主义制度",
                        questionId: "6"
                    }
                ],
                checkList: [],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "6",
                comContent: "我国的根本政治制度是__________。",
                questionDifficulty: "10001",
                questionType: "10004",
                answerId: "17",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 3,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "宪法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "16",
                        questionContent: "人民民主专政制",
                        questionId: "6"
                    },
                    {
                        id: "17",
                        questionContent: "人民代表大会制度",
                        questionId: "6"
                    },
                    {
                        id: "18",
                        questionContent: "社会主义制度",
                        questionId: "6"
                    }
                ],
                checkList: [],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "6",
                comContent: "我国的根本政治制度是__________。",
                questionDifficulty: "10001",
                questionType: "10004",
                answerId: "17",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 3,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "宪法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "16",
                        questionContent: "人民民主专政制",
                        questionId: "6"
                    },
                    {
                        id: "17",
                        questionContent: "人民代表大会制度",
                        questionId: "6"
                    },
                    {
                        id: "18",
                        questionContent: "社会主义制度",
                        questionId: "6"
                    }
                ],
                checkList: [],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
        ],
        multiOptionsList: [
            {
                id: "13",
                comContent: "人民警察办理治安案件，有下列哪种行为应依法给予行政处分；构成犯罪的，依法追究刑事责任（ ）",
                questionDifficulty: "10003",
                questionType: "10005",
                answerId: "38,39,40,41",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 4,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "人民警察法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "38",
                        questionContent: "刑讯逼供、体罚、虐待、侮辱他人的",
                        questionId: "13"
                    },
                    {
                        id: "39",
                        questionContent: "超过询问查证的时间限制人身自由的",
                        questionId: "13"
                    },
                    {
                        id: "40",
                        questionContent: "违反规定不及时退还保证金的",
                        questionId: "13"
                    },
                    {
                        id: "41",
                        questionContent: "接到要求制止违反治安管理行为的报警后，不及时出警的",
                        questionId: "13"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "13",
                comContent: "人民警察办理治安案件，有下列哪种行为应依法给予行政处分；构成犯罪的，依法追究刑事责任（ ）",
                questionDifficulty: "10003",
                questionType: "10005",
                answerId: "38,39,40,41",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 4,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "人民警察法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "38",
                        questionContent: "刑讯逼供、体罚、虐待、侮辱他人的",
                        questionId: "13"
                    },
                    {
                        id: "39",
                        questionContent: "超过询问查证的时间限制人身自由的",
                        questionId: "13"
                    },
                    {
                        id: "40",
                        questionContent: "违反规定不及时退还保证金的",
                        questionId: "13"
                    },
                    {
                        id: "41",
                        questionContent: "接到要求制止违反治安管理行为的报警后，不及时出警的",
                        questionId: "13"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "13",
                comContent: "人民警察办理治安案件，有下列哪种行为应依法给予行政处分；构成犯罪的，依法追究刑事责任（ ）",
                questionDifficulty: "10003",
                questionType: "10005",
                answerId: "38,39,40,41",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 4,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "人民警察法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "38",
                        questionContent: "刑讯逼供、体罚、虐待、侮辱他人的",
                        questionId: "13"
                    },
                    {
                        id: "39",
                        questionContent: "超过询问查证的时间限制人身自由的",
                        questionId: "13"
                    },
                    {
                        id: "40",
                        questionContent: "违反规定不及时退还保证金的",
                        questionId: "13"
                    },
                    {
                        id: "41",
                        questionContent: "接到要求制止违反治安管理行为的报警后，不及时出警的",
                        questionId: "13"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "13",
                comContent: "人民警察办理治安案件，有下列哪种行为应依法给予行政处分；构成犯罪的，依法追究刑事责任（ ）",
                questionDifficulty: "10003",
                questionType: "10005",
                answerId: "38,39,40,41",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 4,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "人民警察法",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "38",
                        questionContent: "刑讯逼供、体罚、虐待、侮辱他人的",
                        questionId: "13"
                    },
                    {
                        id: "39",
                        questionContent: "超过询问查证的时间限制人身自由的",
                        questionId: "13"
                    },
                    {
                        id: "40",
                        questionContent: "违反规定不及时退还保证金的",
                        questionId: "13"
                    },
                    {
                        id: "41",
                        questionContent: "接到要求制止违反治安管理行为的报警后，不及时出警的",
                        questionId: "13"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            }
        ],
        fillingList: [],
        checkingList: [
            {
                id: "8",
                comContent: "根据《反恐怖主义法》的规定，恐怖主义必须具有政治、意识形态等目的（ ）",
                questionDifficulty: "10001",
                questionType: "10006",
                answerId: "22",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 2,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "国家安全和公共安全法律",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "22",
                        questionContent: "正确",
                        questionId: "8"
                    },
                    {
                        id: "23",
                        questionContent: "错误",
                        questionId: "8"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "8",
                comContent: "根据《反恐怖主义法》的规定，恐怖主义必须具有政治、意识形态等目的（ ）",
                questionDifficulty: "10001",
                questionType: "10006",
                answerId: "22",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 2,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "国家安全和公共安全法律",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "22",
                        questionContent: "正确",
                        questionId: "8"
                    },
                    {
                        id: "23",
                        questionContent: "错误",
                        questionId: "8"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "8",
                comContent: "根据《反恐怖主义法》的规定，恐怖主义必须具有政治、意识形态等目的（ ）",
                questionDifficulty: "10001",
                questionType: "10006",
                answerId: "22",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 2,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "国家安全和公共安全法律",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "22",
                        questionContent: "正确",
                        questionId: "8"
                    },
                    {
                        id: "23",
                        questionContent: "错误",
                        questionId: "8"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            },
            {
                id: "8",
                comContent: "根据《反恐怖主义法》的规定，恐怖主义必须具有政治、意识形态等目的（ ）",
                questionDifficulty: "10001",
                questionType: "10006",
                answerId: "22",
                legalBasis: null,
                answerDescrible: null,
                answerChoiceNumber: 2,
                questionId: null,
                userAnswer: null,
                right: null,
                themeName: "国家安全和公共安全法律",
                score: 0,
                rightAnsCon: null,
                isCollect: null,
                recordId: null,
                answer: [
                    {
                        id: "22",
                        questionContent: "正确",
                        questionId: "8"
                    },
                    {
                        id: "23",
                        questionContent: "错误",
                        questionId: "8"
                    }
                ],
                checkList:[],
                firScore: 0,
                secScore: 0,
                userScore: 0,
                rightAnswer: null
            }
        ],
        expressingList: [],
        // bar
        barData: [
            {
                href: "#oneOption",
                questionType: '单选题',
                currentFinishedNum: 1,
                totalNum: 50
            },
            {
                href: "#multiOptions",
                questionType: '多选题',
                currentFinishedNum: 0,
                totalNum: 10
            },
            {
                href: "#filling",
                questionType: '填空题',
                currentFinishedNum: 0,
                totalNum: 5
            },
            {
                href: "#checking",
                questionType: '判断题',
                currentFinishedNum: 0,
                totalNum: 5
            },
            {
                href: "#expressing",
                questionType: '论述题',
                currentFinishedNum: 0,
                totalNum: 5
            }
        ],
        // 时间变量
        startTime: 0,
        time: 0,
        lefttime: 0,
        leftHours: '00',
        leftMinutes: '00',
        leftSeconds: '00',
        consumedHours: '00',
        consumedMinutes: '00',
        consumedSeconds: '00',
        consumed: '',
        // 总分
        totalScore: 87,
        // 主观题答案
        answer: '',

        paperName: '',
        username: '',
        answers: [],
        answersList: [],
        page: 1,
        limit: 5,
        count: 0,
        preserved: [],
        // 修改前的数据
        /*testForm: {
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
        user:[],
        page: 1,
        limit: 5,
        count: 0,
        maxtime: 60 * 60,//总时长
        minutes: 0,//显示分
        seconds: 0,//显示秒
        timer: null,//定时器
        mulChoicCheck: [],
        sinChoicCheck: [],
        judge:[],
        subject: []*/
    },
    /*created: function () {
        this.$nextTick(function () {
            if (examStatus == '0') {
                //开始考试
                vm.startExam();
            } else {
                //继续考试
                vm.continueExam();
            }
            vm.timer = setInterval("vm.CountDown()", 1000);
        })
    },*/
    methods: {
        // created中执行以获取数据
        refresh: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/startExam",
                data: {
                    examConfigId: examConfigId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.paperName = result.examConfig.examName;
                        console.log(result);
                        // 单选题
                        vm.oneOptionList = result.sinChoicList;
                        // 多选题
                        vm.mulChoicList = result.mulChoicList;
                        // 判断题
                        vm.checkingList = result.judgeList;
                        // 主观题
                        vm.expressingList = result.subjectList;
                        // 考试时间
                        vm.lefttime = result.examConfig.examTime*60000;
                        vm.time = (result.examConfig.examTime)/60;
                        // 考试人员
                        vm.username = result.user.fullName;
                        // vm.examConfig = result.examConfig;
                        // vm.userAnswerForm.userExamId = result.userExam.id;
                        /*var _mul = result.mulChoicList;
                        for (var i = 0; i < _mul.length; i++) {
                            vm.testForm.mulChoic.push([]);
                        }*/

                        /*vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        for (var i = 0; i < _mul.length; i++) {
                            vm.mulChoicCheck.push([]);
                        }*/
                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
            /*var obj = {
                configureId: configureId,
                id: id,
                limit: vm.limit,
                page: vm.page
            };
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/paper/paper",
                data: obj,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionList = result.page.list;
                        console.log(vm.questionList);
                        vm.count = result.page.count;
                        id = result.page.id;
                        vm.preserved = [];
                    } else {
                        alert(result.msg);
                    }
                    vm.questionList.forEach(function (val) {
                        switch (val.questionType) {
                            // 单选题
                            case '10004':
                                vm.oneOptionList.push(val);
                                break;
                            // 多选题
                            case '10005':
                                vm.multiOptionsList.push(val);
                                break;
                            // 判断题
                            case '10006':
                                vm.checkingList.push(val);
                            // 主观题
                            case '10007':
                                vm.expressingList.push(val);
                            default:
                                return;
                        }
                    })
                }
            });*/
        },
        // 倒计时
        computeTime: function () {
            // 用计时器动态显示:间隔时间1s
            this.lefttime -= 1000;
            var results = this.figureTime(this.lefttime);
            [this.leftHours, this.leftMinutes, this.leftSeconds] = [...results];
            // 3个小时用完,强制提交
            if (this.lefttime <= 0) {
                this.submit();
            }
        },
        // 使用时间
        consumedTime: function () {
            var consumed =  3*3600000 - this.lefttime;
            var results = this.figureTime(consumed);
            [this.consumedHours, this.consumedMinutes, this.consumedSeconds] = [...results];
            this.consumedMinutes = Number(this.consumedMinutes)*60 + Number(this.consumedMinutes);
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
        toHome: function () {
            parent.location.reload()
        },
        // 改变字体大小
        changeFontSize: function (e) {
            var fontSpans = document.getElementsByClassName('font-size-span');
            var html = document.getElementById('html');

            if (e.target.innerHTML === '小') {
                html.style.fontSize = '9px';
            } else if (e.target.innerHTML === '中') {
                html.style.fontSize = '12px';
            } else if (e.target.innerHTML === '大') {
                html.style.fontSize = '13px';
            } else {
                return;
            }
            for (var i = 0; i < fontSpans.length; i++) {
                fontSpans[i].style.fontWeight = '300';
                fontSpans[i].style.background = 'white';
            }
            e.target.style.fontWeight = '600';
            e.target.style.background = '#eff8ff';
        },
        // 改变 bar 中元素被选择时的字体颜色 & 定位字体图标
        pickArea: function (e) {
            var aTags = document.getElementsByClassName('type');
            var icons = document.getElementsByClassName('icon-biaodiandidian');
            var icon = e.target.getElementsByClassName('iconfont')[0];
            for (var i = 0; i < aTags.length; i++) {
                aTags[i].style.color = 'black';
            }
            for (var i = 0; i < icons.length; i++) {
                icons[i].style.display = 'none';
            }
            e.target.style.color = '#1381e3';
            icon.style.display = 'inline-block';
        },

        // 提交试卷
        submit: function () {
            this.isSubmit = true;
            // 展示使用时间
            this.consumedTime();
            // 路径转换
            /*var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';*/
        },
        submitSubject: function () {
            
        }
        

        // 修改前的方法
        /*startExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/startExam",
                data: {
                    examConfigId: examConfigId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.examConfig = result.examConfig;
                        vm.user = result.user;
                        vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        for (var i = 0; i < _mul.length; i++) {
                            vm.testForm.mulChoic.push([]);
                        }

                        vm.userAnswerForm.userExamId = result.userExam.id;
                        var _mul = result.mulChoicList;
                        for (var i = 0; i < _mul.length; i++) {
                            vm.mulChoicCheck.push([]);
                        }
                        //单选
                        vm.sinChoicList = result.sinChoicList;

                        //多选
                        vm.mulChoicList = result.mulChoicList;
                        //判断
                        vm.judgeList = result.judgeList;
                        //主观
                        vm.subjectList = result.subjectList;
                        vm.maxtime = result.examConfig.examTime * 60;

                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        },*/
        /*continueExam: function () {
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
                            }
                        }
                        console.info("sinChoicList",vm.sinChoicList)
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
                                console.info("subject="+vm.subject);
                            }
                        }
                        vm.maxtime = result.userExam.remainingExamTime * 60;
                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                }
            });
        },*/
        /*fontS: function () {
            console.log(2)
            $("p,span").css("font-size", "16px");
            $(".text_s").css({"font-size": "16px", "font-weight": "bolder"});
            $(".text_m").css({"font-size": "18px", "font-weight": "normal"});
            $(".text_l").css({"font-size": "24px", "font-weight": "normal"})
        },*/
        /*fontM: function () {
            console.log(3)
            $("p,span").css("font-size", "18px");
            $(".text_s").css({"font-size": "16px", "font-weight": "normal"});
            $(".text_m").css({"font-size": "18px", "font-weight": "bolder"});
            $(".text_l").css({"font-size": "24px", "font-weight": "normal"})
        },*/
        /*fontL: function () {
            console.log(4)
            $("p,span").css("font-size", "24px");
            $(".text_s").css({"font-size": "16px", "font-weight": "normal"});
            $(".text_m").css({"font-size": "18px", "font-weight": "normal"});
            $(".text_l").css({"font-size": "24px", "font-weight": "bolder"})
        },*/
        /*submit: function () {
            vm.userAnswerForm.answerForm = [];
            vm.userAnswerForm.remainingExamTime = vm.maxtime/60;
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
                url: baseURL + "user/exam/commitExam",
                data: JSON.stringify(
                    vm.userAnswerForm
                ),
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    if (result.code === 0) {
                        alert("提交成功");
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },*/
        /*save: function () {
            vm.userAnswerForm.answerForm = [];
            vm.userAnswerForm.remainingExamTime = vm.maxtime/60;
            console.info(vm.sinChoicCheck);

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
                        alert("保存成功");
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },*/
        /*CountDown: function () {
            if (vm.maxtime >= 0) {
                vm.minutes = Math.floor(vm.maxtime / 60);
                vm.seconds = Math.floor(vm.maxtime % 60);
                if (vm.maxtime == 5 * 60) {
                    this.$notify.info({
                        title: '提示',
                        message: '距离考试结束时间还有五分钟！'
                    });
                };
                --vm.maxtime;
            } else {
                clearInterval(vm.timer);
                vm.$alert('时间到，结束!', '提示', {
                    confirmButtonText: '确定',
                    callback: function () {
                        vm.submit();
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/examCen/userExam.html';
                    }
                });

            }
        },*/
        /*shuffle: function (a) {
            var j, x, i;
            for (i = a.length; i; i--) {
                j = Math.floor(Math.random() * i);
                x = a[i - 1];
                a[i - 1] = a[j];
                a[j] = x;
            }
            return a;
        }*/
    },
    created: function () {
        setInterval(this.computeTime,1000);
        this.$nextTick(function () {
            vm.refresh();
        });
    }
});
