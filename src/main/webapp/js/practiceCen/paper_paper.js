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
        oneOptionList: [],
        multiOptionsList: [],
        fillingList: [],
        checkingList: [],
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
        creator: '凡凡',
        type: '综合类型',
        createdTime: '2019-02-12 13:25:10',

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

        // 路径方法
        toHome: function () {
            parent.location.reload()
        },
        goBack: function () {
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },

        // 提交试卷
        submit: function () {
            this.isSubmit = true;
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