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
        fillingList: [],
        expressingList: [],
        // bar
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

        oneOptionCheck:[],
        multiOptionsCheck: [],
        checkingCheck: [],
        pdNum: 0,
        singleNum: 0,
        multiNum: 0
    },
    methods: {
        // created中执行以获取数据
        refresh: function () {
            var obj = {
                configureId: configureId,
                id: id
            };
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/paper/paper",
                data: obj,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionList = result.questionList;
                        vm.singleNum = result.singleNum;
                        vm.multiNum = result.multiNum;
                        vm.pdNum = result.pdNum;
                        id = result.id;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 路径方法
        toHome: function () {
            parent.location.reload()
        },
        goBack: function () {
            console.info('goback')
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },

        select: function (e) {
            var anchors = document.getElementsByClassName('anchor');
            for (var i=0;i<anchors.length;i++) {
                anchors[i].style.background = '#dddddd';
            }
            e.target.style.background = '#1381e3';
        },

        // 提交试卷
        submit: function () {
            this.isSubmit = true;
            // 路径转换
            /*var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';*/
        },

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
        this.$nextTick(function () {
            vm.refresh();
        });
    },
});