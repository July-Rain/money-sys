

var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            id:'',
            userName: '',
            userPoliceId: '',
            orgCode:'',
            identify:'1',//表明是教官
            currPage: 1,
            pageSize:10,
            totalCount: 0,
            userStatus:'2000'
        },
        tchData:[1],//教官数据
        newsData:[],//最新课程数据

        visible: false,
        dialogConfig: false,//table弹出框可见性
        title:"",//弹窗的名称
        tchId:"",//教官的id
        dialogVideo:false,//视频页面
        thisVideoContentUrl:"",//当前播放视频的url
        thisVideoPicAccUrl:"",//当前播放视频的默认封面
        dialogPlayer: null,
        videoDataId: [],
    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumediatch/list",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.newsData = result.page.list;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        })
        this.$nextTick(function () {
            this.reload();
        })

    },
    methods: {
        toTchvedio:function(id){
            vm.tchId=id;
            window.location="./tchcourmanage.html?createUser="+id+"&menu=tchman";
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
            // this.$refs[formName].resetFields();
            vm.formInline= { // 搜索表单
                id:'',
                    userName: '',
                    userPoliceId: '',
                    orgCode:'',
                    identify:'1',//表明是教官
                    currPage: 1,
                    pageSize:10,
                    totalCount: 0,
                    userStatus:'2000'
            },
            vm.reload();

        },
        closeDia : function(){
            this.dialogConfig=false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getUorT?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tchData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // el-tree节点点击事件
        handleNodeClick: function (data) {
            vm.formInline.orgCode= data.localOrgCode;
            this.reload();
        },
        uploadSuccess: function (response, file, fileList) {
            vm.stuMedia.comContent=response.filePath;
        },
        uploadError: function () {

        },
        beforeAvatarUpload: function () {
            if(!checkFile(file)) return false;
        },
        getStuContent:function(item){

        },//获取信息
        initPlayer: function () {
            var that = this;
            window.onload = function () {
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
                    var myPlayer = videojs(val, options);
                })
            }
        },
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
            var bigDialogButton = document.getElementsByClassName('vjs-big-play-button')[this.videoDataId.length];
            console.log(bigDialogButton);
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
        toUrl: function (url) {
            window.location.href = baseURL + url
        }
    },
    watch:  {
        // 暂时记录：深度监控
        'videoDataId' : function (newVal) {
            vm.initPlayer();
        }
    }
}).$mount('#app');
