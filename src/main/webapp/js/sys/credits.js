var menuId = getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        source: [], // result数据
        fractionList: {
            audioStudy: {
                fractionType: '1',
                source: 'AUDIOSTUDY'

            }, // 音频
            videoStudy: {
                fractionType: '1',
                source: 'VIDEOSTUDY'
            }, // 视频
            picStudy: {
                fractionType: '1',
                source: 'PICSTUDY'
            }, // 图文
            stuTask: {
                fractionType: '1',
                source: 'STUTASK'
            }, // 任务
            dailyQue: {
                fractionType: '1',
                source: 'DAILYQUE'
            }, // 每日一题
            groupPrac: {
                fractionType: '1',
                source: 'GROUPPRAC'
            }, // 组卷练习
            otherPrac: {
                fractionType: '1',
                source: 'OTHERPRAC'
            }, // 非练习卷
            examIntegral: {
                fractionType: '1',
                source: 'EXAM',
                fractionRulesList:[{
                    rightRateMin:'',
                    intervalScore:''
                }]
            }, // 获得积分
            examCredits: {
                fractionType: '0',
                source: 'EXAM',

            }, // 获得学分
            recruit: {
                fractionType: '1',
                source: 'RECRUIT'
            }, // 闯关竞赛
            competitionOnline: {
                fractionType: '1',
                source: 'COMPEITIONONLINE'
            }, // 在线比武
            match: {
                fractionType: '1',
                source: 'MATCH'
            }, // 擂台赛
        },
       sum: 1,
       handleType:'add'
    },
    created: function () {
        this.reload();
    },
    methods: {
        addLimitations: function () {
            vm.fractionList.examIntegral.fractionRulesList.push({
                rightRateMin:'',
                intervalScore:''
            })
            vm.sum++;

        },
        removeLimitations: function (index) {
            if (vm.sum===1) {
                return;
            }
            vm.fractionList.examIntegral.fractionRulesList.splice(index-1,1)
            console.log(vm.fractionList.examCredits.fractionRulesList)
            vm.sum--;
        },
        toHome: function () {
            parent.location.reload()
        },
        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "fraction/save",
                contentType: "application/json",
                data: JSON.stringify(vm.fractionList),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.handleType = 'view';
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        reload:function () {
            $.ajax({
                type: "GET",
                url: baseURL + "fraction/list",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        if (JSON.stringify(result.fractionMap)!='{}') {
                            vm.fractionList = result.fractionMap;
                            if (vm.fractionList.examIntegral.fractionRulesList.length > 0) {
                                vm.sum = vm.fractionList.examIntegral.fractionRulesList.length;
                            }
                            if (JSON.stringify(vm.fractionList)!='{}') {
                                vm.handleType = 'view';
                            }
                        }
                    }else {
                        alert(result.msg);
                    }
                }
            })

        },
        edit : function () {
            vm.handleType = 'add';
        }
    }
});
