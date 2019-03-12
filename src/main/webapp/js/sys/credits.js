var menuId = getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        source: [], // result数据
        detail: {
            audioStudy: {}, // 音频
            videoStudy: {}, // 视频
            picStudy: {}, // 图文
            stuTask: {}, // 任务
            dailyQue: {}, // 每日一题
            groupPrac: {}, // 组卷练习
            otherPrac: {}, // 非练习卷
            integral: {}, // 获得积分
            credits: {}, // 获得学分
            recruit: {}, // 闯关竞赛
            competitionOnline: {}, // 在线比武
            match: {}, // 擂台赛
        }
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
    mounted: function () {
        /*this.$nextTick(function () {
            var limitButtons = document.getElementsByClassName('limit');
            for (var i=0;i<limitButtons.length;i++) {
                limitButtons[i].onclick = vm.addIfs;
            }
        })*/
    },
    methods: {
        addIfs: function (e) {
            var parent = e.target.parentNode;
            var dropdownGro = parent.firstChild.cloneNode(true);
            parent.insertBefore(dropdownGro, e.target);
            parent.getElementsByClassName('icon-tianjia')[0].onclick = vm.addLimitations;
            e.target.remove();
        },
        addLimitations: function (e) {
            var parent = e.target.parentNode.parentNode.parentNode;
            var examData = e.target.parentNode.parentNode.cloneNode(true);
            examData.getElementsByClassName('icon-tianjia')[0].onclick = vm.addLimitations;
            if (examData.getElementsByClassName('limit')) {
                console.log(examData.getElementsByClassName('limit'))
                examData.getElementsByClassName('limit').onclick = vm.addIfs;
            }

            parent.appendChild(examData);
        },

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

        toHome: function () {
            parent.location.reload()
        }
    }
});
