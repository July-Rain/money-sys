/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        caseData: [],//视频列表
        videoDataId: [],//视频ID列表
        navData: [],//导航
        formInline: { // 搜索表单
            contentType: "pic",
            currPage: 1,
            pageSize: 10,
            totalCount: 0,
            caseLawName: "",
            caseLawid: "",
            startTime: "",
            endTime: "",
            lawLevel: "",
            caseType: "",
            caseProcess: ""
        },
        visible: false,
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        dialogLaw: false,//法律分类的弹窗
        multipleSelection: [],//法律分类弹窗
        playTime: 0,//播放时间
        oldTime: 0,//原播放时间
        caseTypeOption: [],
        lawLevelOption: [],
        caseProcessOption: [],
        contentTypeOption: [{
            code: 'pic',
            value: '图文'
        }, {
            code: 'audio',
            value: '音频'
        }, {
            code: 'video',
            value: '视频'
        }],
        dialogCaseAna: false,
        caseContent: "",
        title: "查看",
        startTime: "",//开始时间
        endTime: "",//结束时间
        caseAna: {},//实体
    },
    created: function () {

        this.$nextTick(function () {

            //法律分类树数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/tree",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.treeData = result.classifyList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            // 案例类型
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "CASETYPE", Parentcode: "0"},
                success: function (result) {
                    vm.caseTypeOption = result.dictlist;
                }
            });
            // 法院层级
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "LAWLEVEL", Parentcode: "0"},
                success: function (result) {
                    vm.lawLevelOption = result.dictlist;
                }
            });
            // 审判程序
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "CASEPROCESS", Parentcode: "0"},
                success: function (result) {
                    vm.caseProcessOption = result.dictlist;
                }
            });
        })
        this.$nextTick(function () {
            this.reload();
        })

    },
    methods: {
        //实例化视频
        initPlayer: function () {
            var that = this;
            setTimeout(function () {
                var options = {
                    controls: true,
                    bigPlayButton: true,
                    controlBar: {
                        //设置是否显示该组件
                        playToggle: false,
                        remainingTimeDisplay: true,
                        fullscreenToggle: false,
                        volumePanel: false
                    },
                };
                that.videoDataId.forEach(function (val, index) {
                    var myPlayer = videojs(val, options);
                    var bigButton = document.getElementsByClassName('vjs-big-play-button')[index];
                    bigButton.style.outline = 'none';
                    myPlayer.on('play', function () {
                        bigButton.style.display = 'none';
                    });
                    myPlayer.on('pause', function () {
                        bigButton.style.display = 'block';
                    });
                })
            }, 400)
        },
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.currPage - 1) * vm.formInline.pageSize;
        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        // 表单重置
        resetForm: function (formName) {
            this.formInline.caseLawid = "";
            this.$refs[formName].resetFields();
            this.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "caseana/list?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.caseData = result.page.list;
                        for (var i = 0; i < vm.caseData.length; i++) {
                            vm.caseData[i].caseContentUrl = baseURL + "sys/download?accessoryId=" + vm.caseData[i].caseContent;
                            if (vm.caseData[i].videoPicAcc) {
                                vm.caseData[i].videoPicAccUrl = baseURL + "sys/download?accessoryId=" + vm.caseData[i].videoPicAcc;
                            } else {
                                vm.caseData[i].videoPicAccUrl = "http://temp.im/640x260";
                            }
                        }
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                        console.info("caseData", vm.caseData)
                    } else {
                        alert(result.msg);
                    }
                    if (vm.formInline.contentType=='video') {
                        vm.caseData.forEach(function (val) {
                            vm.videoDataId.push(val.id)
                        })
                        console.info(vm.videoDataId)
                        vm.initPlayer();
                    }
                }
            })
        },
        // el-tree节点点击事件
        handleNodeClick: function (data) {
            vm.formInline.stuLawid = data.id;
            this.reload();
        },
        handleCheckChange: function () {

        },
        chooseLaw: function () {
            this.dialogLaw = true;

            this.multipleSelection = [];
        },
        confimLaw: function () {
            this.formInline.caseLawid = "";
            this.formInline.caseLawName = "";
            this.multipleSelection = this.$refs.lawtree.getCheckedNodes();
            for (var i = 0; i < this.multipleSelection.length; i++) {
                if (!this.formInline.caseLawid) {
                    this.formInline.caseLawid = this.multipleSelection[i].id;
                    this.formInline.caseLawName = this.multipleSelection[i].classifyName;
                } else {
                    this.formInline.caseLawid += "," + this.multipleSelection[i].id;
                    this.formInline.caseLawName += "," + this.multipleSelection[i].classifyName;
                }
            }
            this.dialogLaw = false;
        },
        cancelLaw: function () {
            this.dialogLaw = false;
        },
        changeType: function () {
            this.reload();
        },
        onPlay: function (id, flag) {
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "caseana/updateCount?id=" + id + "&stuType=" + flag + "&stuFrom=caseana",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        //vm.treeData = result.classifyList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        onPause: function (id, event) {
            var el = event.currentTarget;
            var temp = 0;
            vm.oldTime = vm.playTime;

            vm.playTime = el.currentTime;

            temp = vm.playTime - vm.oldTime;

            var finishFlag = false;
            if (el.currentTime == el.duration) {
                finishFlag = true;
            }
            //获取当前选择对象

            //媒介因素暂停事件
            //请求后台记录观看时长
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/countTime?stuId=" + id + "&stuFrom=caseana&type=" + vm.formInline.contentType + "&playTime=" + temp + "&finishFlag=" + finishFlag,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        //vm.treeData = result.classifyList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        closeDia: function () {
            //结束时间
            vm.endTime = new Date();
            var time = (vm.endTime - vm.startTime) / 1000;
            vm.dialogCaseAna = false;
            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/countTime?stuId=' + vm.caseAna.id + '&stuFrom=pic' + '&playTime=' + time + '&type=pic',
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDetail: function (index, row) {

            vm.startTime = new Date();
            $.ajax({
                type: "POST",
                url: baseURL + 'caseana/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.caseAna = result.data;
                        vm.caseContent = result.data.caseContent;
                        vm.dialogCaseAna = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            //请求后台修改播放量 记录学习记录 --案例分析模块
            $.ajax({
                type: "POST",
                url: baseURL + "caseana/updateCount?id=" + row.id + "&stuType=" + vm.infoFlag + "&stuFrom=caseana",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        //vm.treeData = result.classifyList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
