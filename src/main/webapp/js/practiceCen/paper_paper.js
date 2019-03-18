// 配置ID
var configureId = getUrlParam('id');
// 练习任务Id
var id = getUrlParam('taskId');
var review = getUrlParam('review');

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
                id: id,
                isSubmit: vm.isSubmit
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

                        vm.creator = result.userName;
                        vm.type = result.typeName;
                        vm.createdTime = result.createTime;
                        vm.paperName = result.paperName;
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
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },
        comback: function(){
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_index.html';
        },
        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/paper/save/" + id,
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(vm.questionList),
                success: function (result) {
                    if (result.code == 0) {
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/exerciseCenter/paper_index.html';
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        submit: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/paper/commit/" + id,
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(vm.questionList),
                success: function (result) {
                    if (result.code == 0) {
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/exerciseCenter/paper_index.html';
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
        // 保存后显示做题状况
        isChecked: function (id, answer) {
            var answerId = [];
            answer.forEach(function (val) {
                answerId.push(val.id);
            })
            return answerId.indexOf(id);
        }
    },
    created: function () {
        this.$nextTick(function () {
            if(review != null && review != ''){
                vm.isSubmit = true;
            } else {
                vm.isSubmit = false;
            }
            vm.refresh();
        });
    },
});