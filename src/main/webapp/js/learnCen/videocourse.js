/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        videoData: [],//视频列表
        videoDataId: [],
        dialogPlayer: null,
        navData: [],//导航
        formInline: { // 搜索表单
            stuType:"video",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
            stuLawName:"",
            stuIssuer:"",
            startTime:"",
            endTime:""
        },
        visible: false,
        stuMedia: {
            id:"",
            stuType: "video",
            stuTitle: "",
            comContent: "",
            deptIds: "",
            userIds: "",
            stuDescribe:"",
            userName:"",//适用人员姓名
            deptName:"",//适用部门姓名
        },
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        dialogLaw:false,//法律分类的弹窗
        dialogVideo:false, // 视频播放弹窗
        thisVideoId:null, // 视频播放 - id
        thisVideoContentUrl:'', // 视频播放 - 视频图片url
        thisVideoPicAccUrl:'', // 视频播放 - 视频图片url
        multipleSelection:[],//法律分类弹窗
        playTime:0,//播放时间
        oldTime:0,//原来播放时间
    },
    created: function () {
        this.$nextTick(function () {
            //法律分类树数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.treeData = result.classifyList;
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
            vm.reload();
        });
    },
    methods: {
        initPlayer: function () {
            // debugger
            var that = this;

            window.onload = function () {
                setTimeout(function () {
                    var options = {
                        controls: true,
                        bigPlayButton: true,
                        controlBar:{
                            //设置是否显示该组件
                            playToggle: false,
                            remainingTimeDisplay: true,
                            fullscreenToggle: false,
                            volumePanel: false
                        },
                    };
                    that.videoDataId.forEach(function (val, index) {
                        videojs(val, options);
                    })
                }, 400)
            }
        },
        // 初始化对话框视频
        initDialogPlayer: function () {
            var dialogOptions = {
                controls: true,
                autoplay: true,
                bigPlayButton: true,
                controlBar:{
                    //设置是否显示该组件
                    playToggle: false,
                    remainingTimeDisplay: true,
                    fullscreenToggle: false,
                    volumePanel: false
                },
            };
            this.dialogPlayer = videojs('dialog-player', dialogOptions);
            if (!vm.videoDataId) {
                return
            }
            var bigDialogButton = document.getElementsByClassName('vjs-big-play-button')[this.videoDataId.length];
            bigDialogButton.style.outline = 'none';
            this.dialogPlayer.on('play', function () {
                bigDialogButton.style.display = 'none';
            });
            this.dialogPlayer.on('pause', function () {
                bigDialogButton.style.display = 'block';
            });
        },
        dialogOpen: function () {
                if (!this.dialogPlayer) {
                    this.initDialogPlayer();
                } else {
                    console.log(this.dialogPlayer);
                    this.dialogPlayer.dispose();
                    this.initDialogPlayer();
                }
        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize=val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage=val;
            this.reload();
        },
        // 表单重置
        resetForm: function (formName) {
            this.formInline.stuLawid="";
            this.$refs[formName].resetFields();
            this.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/list?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.videoData = result.page.list;
                        console.log(vm.videoData);
                        for(var i=0;i<vm.videoData.length;i++){
                            vm.videoDataId.push(vm.videoData[i].id);
                            vm.videoData[i].contentUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                            if(vm.videoData[i].videoPicAcc){
                                vm.videoData[i].videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].videoPicAcc;
                            }else{
                                vm.videoData[i].videoPicAccUrl=baseURL+'/statics/img/video_bg.png';
                            }
                            if(vm.videoData[i].stuType=='pic'){
                                vm.videoData[i].stuType="图文";
                            }else if(vm.videoData[i].stuType=='audio'){
                                vm.videoData[i].stuType="音频";
                            }else if(vm.videoData[i].stuType=='video'){
                                vm.videoData[i].stuType="视频";
                            }
                        }
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        vm.$message(result.msg);
                    }
                }
            }).then(
                vm.initPlayer()
        );
        },
        // el-tree节点点击事件
        handleNodeClick: function (data) {
            vm.formInline.stuLawid=data.id;
            this.reload();
        },
        handleCheckChange:function(){

        },
        chooseLaw: function(){
            this.dialogLaw=true;

            this.multipleSelection=[];
        },
        confimLaw: function () {
            this.formInline.stuLawid="";
            this.formInline.stuLawName="";
            this.multipleSelection=this.$refs.lawtree.getCheckedNodes();
            for(var i=0;i<this.multipleSelection.length;i++){
                if (this.formInline.stuLawid == "") {
                    this.formInline.stuLawid=this.multipleSelection[i].id;
                    this.formInline.stuLawName=this.multipleSelection[i].classifyName;
                }else{
                    this.formInline.stuLawid+=","+this.multipleSelection[i].id;
                    this.formInline.stuLawName+=","+this.multipleSelection[i].classifyName;
                }
            }
            this.dialogLaw=false;
        },
        cancelLaw: function () {
            this.dialogLaw=false;
        },
        onVideoDialog: function (id,contentUrl,videoPicAccUrl){
            this.dialogVideo = true;
            // this.$nextTick(function () {
                this.onPlay(id);
                this.thisVideoId = id;
                this.thisVideoContentUrl = contentUrl;
                this.thisVideoPicAccUrl = videoPicAccUrl;
            // })
        },
        onPlay:function (id, event) {
            //获取当前选择对象
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/updateCount?stuId="+id+"&stuType=stu_video&stuFrom=videocen",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
            /*$(el).on(
                "timeupdate",
                function(event){
                    vm.playTime=this.currentTime;
                    vm.durationTime=this.duration;
                    console.log(this.currentTime, this.duration);
                });*/
        },
        onPause: function (id,event) {
            var el = event.currentTarget;
            var temp =0;
            //var playTime= $(el).currentTime;
            vm.oldTime=vm.playTime;

            vm.playTime= el.currentTime;

            temp=vm.playTime-vm.oldTime;

            var finishFlag =false;
            if(el.currentTime == el.duration ){
                finishFlag=true;
            }
            //获取当前选择对象

            //媒介因素暂停事件
            //请求后台记录观看时长
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/countTime?stuId="+id+"&stuFrom=videocen&type=video&playTime="+temp+"&finishFlag="+finishFlag,
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        },
        collect:function (id) {
            var coll={
                sourceId: id,
                type: 1,
                isCollect: 1
            };
            //收藏课程
            $.ajax({
                type: "POST",
                url: baseURL + "collection/doCollect",
                contentType: "application/json",
                data: JSON.stringify(coll),
                success: function(result){
                    if(result.code === 0){
                        vm.$message('收藏成功');
                        vm.reload();
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
        },
        cancelCollect:function (id) {
            var coll={
                sourceId: id,
                type: 1,
                isCollect: 0
            };
            // 取消收藏课程
            $.ajax({
                type: "POST",
                url: baseURL + "collection/doCollect",
                contentType: "application/json",
                data: JSON.stringify(coll),
                success: function(result){
                    if(result.code === 0){
                        vm.reload();
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
        },
    },
    watch: {
       /* 'videoDataId': function () {

        }*/
    }
});

