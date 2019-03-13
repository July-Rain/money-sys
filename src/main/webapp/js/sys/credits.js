var menuId = getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        source: [], // result数据
        audioStudy: {
            fractionType : '1',
            source : 'audioStudy'

        }, // 音频
        videoStudy: {
            fractionType : '1',
            source : 'videoStudy'
        }, // 视频
        picStudy: {
            fractionType : '1',
            source : 'picStudy'
        }, // 图文
        stuTask: {
            fractionType : '1',
            source : 'stuTask'
        }, // 任务
        dailyQue: {
            fractionType : '1',
            source : 'dailyQue'
        }, // 每日一题
        groupPrac: {
            fractionType : '1',
            source : 'groupPrac'
        }, // 组卷练习
        otherPrac: {
            fractionType : '1',
            source : 'otherPrac'
        }, // 非练习卷
        examIntegral: {
            fractionType : '1',
            source : 'integral'
        }, // 获得积分
        examCredits: {
            fractionType : '0',
            source : 'credits'
        }, // 获得学分
        recruit: {
            fractionType : '1',
            source : 'recruit'
        }, // 闯关竞赛
        competitionOnline: {
            fractionType : '1',
            source : 'competitionOnline'
        }, // 在线比武
        match: {
            fractionType : '1',
            source : 'match'
        }, // 擂台赛
        sum: 1,
        demo: []
    },
    created: function () {
        /*this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.treeData = result.orgList;
                        // 默认展开第一级
                        vm.treeData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });
        })
        this.$nextTick(function () {
            this.reload();
        })*/
    },
    methods: {
        /*reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getUorT?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },*/
        addLimitations: function () {
            vm.sum++;
        },
        removeLimitations: function () {
            if (vm.sum===1) {
                return;
            }
            vm.sum--;
        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
