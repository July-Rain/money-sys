// 配置ID
var examConfigId = getUrlParam('id');
var examStatus = getUrlParam("examStatus");
var userExamId = getUrlParam("userExamId");
var vm = new Vue({
    el: '#app',
    data: {
        isSubmit: true, // 是否提交
        // 答题数据
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
        mulChoicCheck: [],
        sinChoicCheck: [],
        judge:[],
        subject: [],
        // 题目数据
        sinChoicList: [],
        mulChoicList: [],
        judgeList: [],
        subjectList: [],
        otherList: [],
        questionList: [],
        arrTotal:[],
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
        rightNum:0,
        wrongNum:0,

        displayTime: 0,
        lefttime: 0,
        consumedMinutes: '00', // 所用分钟
        consumedSeconds: '00', // 所用秒
        totalScore: 0,

        favoriteText: '收藏此题',
        paperName: '',
        username: '',

        examConfig: [],
        userExam:[],

        sinStarIcon:[],
        mulStarIcon:[],
        judStarIcon:[],
        subStarIcon:[],
    },
    created: function () {
        this.$nextTick(function () {
            vm.viewExam();
            vm.consumedTime();
        })
    },
    methods: {
        // created钩子中执行获取数据
        viewExam: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/viewExam",
                data: {
                    userExamId: userExamId
                },
                success: function (result) {
                    console.log(result)
                    if (result.code === 0) {
                        // 配置试卷
                        vm.examConfig = result.examConfig;
                        vm.paperName = vm.examConfig.examName;
                        vm.displayTime= vm.examConfig.examTime;
                        // 考试人员
                        vm.userExam = result.userExam;
                        vm.totalScore = vm.userExam.score;
                        vm.userAnswerForm.userExamId = result.userExam.id;
                        vm.username = result.user.userName;
                        vm.lefttime = vm.userExam.remainingExamTime*60000;

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
                                if (_mul[i].isCollect===1){
                                    vm.mulStarIcon.push('el-icon-star-on');
                                } else{
                                    vm.mulStarIcon.push('el-icon-star-off');
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
                                if (vm.sinChoicList[i].isCollect===1){
                                   vm.sinStarIcon.push('el-icon-star-on');
                                } else{
                                   vm.sinStarIcon.push('el-icon-star-off');
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
                                if (vm.judgeList[i].isCollect===1){
                                    vm.judStarIcon.push('el-icon-star-on');
                                } else{
                                    vm.judStarIcon.push('el-icon-star-off');
                                }
                            }
                        }
                        //主观
                        vm.subjectList = result.subjectList;
                        vm.barData[3].totalNum = vm.subjectList.length;
                        if(vm.subjectList){
                            for(var i=0;i<vm.subjectList.length;i++){
                                if(vm.subjectList[i].userAnswer){
                                    vm.subject.push(vm.subjectList[i].userAnswer);
                                    vm.barData[3].currentFinishedNum ++;
                                }else{
                                    vm.subject.push('');
                                }
                                if (vm.subjectList[i].isCollect===1){
                                    vm.subStarIcon.push('el-icon-star-on');
                                } else{
                                    vm.subStarIcon.push('el-icon-star-off');
                                }
                            }
                        }
                        vm.arrTotal = vm.sinChoicList.concat(vm.mulChoicList, vm.judgeList, vm.subjectList);
                        vm.answerNumber();
                    } else {
                        alert(result.msg);
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/personalCen/myExamScore.html';
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
        },
        // 使用时间
        consumedTime: function () {
            var consumed =  this.displayTime*60000 - this.lefttime;
            var results = this.figureTime(consumed);
            [this.consumedHours, this.consumedMinutes, this.consumedSeconds] = [...results];
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
        // 答题对错个数
        answerNumber: function () {
            vm.arrTotal.forEach(function (val) {
                if (val.userScore) {
                    vm.rightNum ++;
                } else {
                    vm.wrongNum ++;
                }
            });
        },
        // 答案区域
        answerDisplay: function (list, id, answer) {
            // 用户答案对应的数字数组
            var checkIndex = [];
            // 正确答案对应的数字数组
            var rightIndex = [];
            // 选项id的数组
            var answerIdList =[];
            // 答案id字符串分解而得的数组
            var rightIdList = id.split(',');
            answer.forEach(val => answerIdList.push(val.id));
            list.forEach(val => checkIndex.push(answerIdList.indexOf(val)+1));
            rightIdList.forEach(val => rightIndex.push(answerIdList.indexOf(val)+1));
            return {
                checkIndex: checkIndex.sort((a,b) => a-b).join(' '),
                rightIndex: rightIndex.sort((a,b) => a-b).join(' ')
            }
        },
        // 收藏
        doCollect :function(index,type){
            var obj ={};
            var type;
            if(type==='10004'){
                obj={
                    key: vm.sinChoicList[index].id,
                    value : vm.sinChoicList[index].questionId
                }
                if(vm.sinChoicList[index].isCollect == 1){
                    type = 0;
                    vm.sinChoicList[index].isCollect = 0;
                } else {
                    type = 1;
                    vm.sinChoicList[index].isCollect = 1;
                }
            }else if (type ==='10005'){
                obj={
                    key: vm.mulChoicList[index].id,
                    value : vm.mulChoicList[index].questionId
                }
                if(vm.mulChoicList[index].isCollect == 1){
                    type = 0;
                    vm.mulChoicList[index].isCollect = 0;
                } else {
                    type = 1;
                    vm.mulChoicList[index].isCollect = 1;
                }
            }else if(type==='10006'){
                obj={
                    key: vm.judgeList[index].id,
                    value : vm.judgeList[index].questionId
                }
                if(vm.judgeList[index].isCollect == 1){
                    type = 0;
                    vm.judgeList[index].isCollect = 0;
                } else {
                    type = 1;
                    vm.judgeList[index].isCollect = 1;
                }
            }else if(type==='10007'){
                obj={
                    key: vm.subjectList[index].id,
                    value : vm.subjectList[index].questionId
                }
                if(vm.subjectList[index].isCollect == 1){
                    type = 0;
                    vm.subjectList[index].isCollect = 0;
                } else {
                    type = 1;
                    vm.subjectList[index].isCollect = 1;
                }
            }

            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/doCollect/"+type,
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 路径方法
        goBack: function () {
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/personalCen/myExamScore.html';
        },
        // 样式方法
        // 改变字体大小
        changeFontSize: function (e) {
            var fontSpans = document.getElementsByClassName('font-size-span');
            var html = document.getElementById('html');

            if (e.target.innerHTML === '小') {
                html.style.fontSize = '9px';
            } else if (e.target.innerHTML === '中') {
                html.style.fontSize = '13px';
            } else if (e.target.innerHTML === '大') {
                html.style.fontSize = '14px';
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
    }
});
