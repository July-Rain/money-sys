/**
 * Author: Zhujunwen
 * Date: 2018/12/22
 * Description:个人中心-重点课程
 */
var vm = new Vue({
    el: '#app',
    data: {
        videoData: [],//视频列表
        videoDataId: [],
        navData: [],//导航
        formInline: { // 搜索表单
            stuType:"pic",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
            stuLawName:"",
            stuIssuer:"",
            startTime:"",
            endTime:"",
        },
        visible: false,
        stuMedia: {
            id:"",
            stuType: "pic",
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
        multipleSelection:[],//法律分类弹窗
        delIdArr: {
            id:""
        },//删除数据
        dialogPic:false,
        title:"",
        caseContent:"",
        myPlayers: []

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
                        alert(result.msg);
                    }
                }
            });

        })
        this.$nextTick(function () {
            vm.reload();
        })
    },
    methods: {
        //序列号计算
        indexMethod:function (index) {
            return index + 1 + (vm.formInline.currPage-1) * vm.formInline.pageSize;
        },
        initPlayer: function () {
            if (vm.myPlayers.length!=0) {
                vm.myPlayers.forEach(function (val) {
                    val.dispose();
                })
            }
            setTimeout(function () {
                var options = {
                    bigPlayButton: true
                };
                vm.videoDataId.forEach(function (val, index) {
                    var favoritePlayer = videojs(val, options);
                    var bigButton = document.getElementsByClassName('vjs-big-play-button')[index];
                    bigButton.style.outline = 'none';
                    favoritePlayer.on('play', function () {
                        bigButton.style.display = 'none';
                    });
                    favoritePlayer.on('pause', function () {
                        bigButton.style.display = 'block';
                    });
                    vm.myPlayers.push(favoritePlayer);
                })
            }, 500)
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
            // this.formInline.stuLawid="";
            // this.$refs[formName].resetFields();


            vm.formInline= { // 搜索表单
                stuType:"pic",
                    currPage: 1,
                    pageSize: 10,
                    totalCount:0,
                    stuLawName:"",
                    stuIssuer:"",
                    startTime:"",
                    endTime:"",
            };
            this.reload();
        },
        handleDel: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "collection/delete/"+id,
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.reload();
                    }else{
                        alert(result.msg);
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/mycollection",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.videoData = result.result.list;
                        for(var i=0;i<vm.videoData.length;i++){
                            vm.videoData[i].contentUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                            if(vm.videoData[i].videoPicAcc){
                                vm.videoData[i].videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].videoPicAcc;
                            }else{
                                vm.videoData[i].videoPicAccUrl="http://temp.im/640x260";
                            }
                            if(vm.videoData[i].stuType=='pic'){
                                vm.videoData[i].stuType="图文";
                            }else if(vm.videoData[i].stuType=='audio'){
                                vm.videoData[i].stuType="音频";
                            }else if(vm.videoData[i].stuType=='video'){
                                vm.videoData[i].stuType="视频";
                            }
                        }
                        vm.formInline.currPage = result.result.currPage;
                        vm.formInline.pageSize = result.result.pageSize;
                        vm.formInline.totalCount = parseInt(result.result.totalCount);
                        console.info("videoData",vm.videoData)
                        if (vm.formInline.stuType=='video') {
                            vm.videoDataId = [];
                            vm.videoData.forEach(function (val) {
                                vm.videoDataId.push(val.id)
                            })
                            vm.initPlayer();
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
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
        toHome: function () {
            parent.location.reload()
        },
        changeType: function () {
            this.reload();
        },
        handleDetail :function (id,row) {
            this.title='查看';

            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
                        vm.stuMedia=result.data;
                        vm.caseContent=result.data.comContent;
                        vm.dialogPic=true;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        closeDia:function () {
            this.dialogPic=false;
        }
    }
});
