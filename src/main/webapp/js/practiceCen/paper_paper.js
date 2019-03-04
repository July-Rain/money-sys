// 配置ID
var configureId = getUrlParam('id');
// 练习任务Id
var id = getUrlParam('taskId');

var vm = new Vue({
    el: "#app",
    data: {
        // boolean
        isSubmit: false,
        // 题目数据
        questionList: [],
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
            }
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
        countdown: '00:00:00',
        time: 3,
        lefttime: 10800000,
        consumed: '00:00:00',
        // 总分
        totalScore: 87,

        paperName: '卷子名称：第四季度执法资格考试',
        username: '王小明',
        answers: [],
        answersList: [],
        page: 1,
        limit: 5,
        count: 0,
        preserved: [],
    },
    methods: {
        // created中执行以获取数据
        refresh: function () {
            var obj = {
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
            });
        },
        // 倒计时
        computeTime: function () {
            // 用计时器动态显示:间隔时间1s
            this.lefttime -= 1000;
            var results = this.figureTime(this.lefttime);
            let a, b, c;
            [a, b, c] = [...results];
            this.countdown = a + ':' + b + ':' + c;
            // 3个小时用完,强制提交
            if (this.lefttime <= 0) {
                this.submit();
            }
        },
        // 使用时间
        consumedTime: function () {
            var consumed =  3*3600000 - this.lefttime;
            var results = this.figureTime(consumed);
            let a, b, c;
            [a, b, c] = [...results];
            this.consumed = a + ':' + b + ':' + c;
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

        toHome: function () {
            parent.location.reload()
        },

        // 改变字体大小
        changeFontSize: function (e) {
            var fontSpans = document.getElementsByClassName('font-size-span');
            var html = document.getElementById('html');
            for (var i = 0; i < fontSpans.length; i++) {
                fontSpans[i].style.fontWeight = '300';
            }
            //  待
            if (e.target.innerHTML === '小') {
                html.style.fontSize = '10px';
            } else if (e.target.innerHTML === '中') {
                html.style.fontSize = '12px';
            } else if (e.target.innerHTML === '大') {
                html.style.fontSize = '14px';
            } else {
                return;
            }
            e.target.style.fontWeight = '600';
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

        // 修改后未用到的方法
        sure: function (index) {// 多选
            var obj = vm.questionList[index];
            var answerId = obj.answerId;
            var checkList = obj.checkList;
            if (checkList.length == 0) {
                alert('请选择选项后再提交');
                return;
            }

            var arr = new Array();
            arr = answerId.split(',');

            if (arr.length != checkList.length) {
                obj.right = 0;
                return;
            }

            obj.right = 1;
            for (var i = 0; i < arr.length; i++) {
                if (checkList.indexOf(arr[i]) == -1) {
                    obj.right = 0;
                    break;
                }
            }

            var form = {
                taskId: id,
                qId: obj.id,
                answer: checkList.join(','),
                right: obj.right,
                themeName: obj.themeName
            };

            vm.preserved.push(form);
        },
        affirm: function (index) {
            var obj = vm.questionList[index];
            var answerId = obj.answerId;
            var userAnswer = obj.userAnswer;
            var themeName = obj.themeName;

            if (userAnswer == answerId) {
                obj.right = 1;
            } else {
                obj.right = 0;
            }

            var form = {
                taskId: id,
                qId: obj.id,
                answer: userAnswer,
                right: obj.right,
                themeName: themeName
            };

            vm.preserved.push(form);
        },
        save: function () {
            if (vm.preserved.length > 0) {
                vm.preserve(0);
            }
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },
        commit: function () {
            if (vm.preserved.length > 0) {
                vm.preserve(1);
            } else {
                vm.tj();
            }
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },
        tj: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/paper/commit/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (event) {
            vm.limit = event;
            if (vm.preserved.length > 0) {
                vm.preserve(0);
            }
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.page = event;
            if (vm.preserved.length > 0) {
                vm.preserve(0);
            }
            vm.refresh();
        },
        preserve: function (type) {
            // 保存答题情况
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/paper/preserve/" + type,
                data: JSON.stringify(vm.preserved),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
    },
    created: function () {
        setInterval(this.computeTime,1000);
        this.$nextTick(function () {
            vm.refresh();
        });
    },
});