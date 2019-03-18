/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        videoData: [],//视频列表
        navData: [],//导航
        formInline: { // 搜索表单
            stuType:"pic",
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
        playTime:0,//播放时间
        oldTime:0,//原播放时间
        dialogPic:false,
        caseContent:"",//图文资料数据
        title:"",
        startTime:"",//开始时间
        endTime:""//结束时间
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

            //获取信息
            /*$.ajax({
                type: "POST",
                url: baseURL + "stumedia/list",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.videoData = result.page.list;
                    }else{
                        alert(result.msg);
                    }
                }
            });*/
        })
        this.$nextTick(function () {
            this.reload();
        })

    },
    methods: {
        //序列号计算
        indexMethod:function (index) {
            return index + 1 + (vm.formInline.currPage-1) * vm.formInline.pageSize;
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
                        // for(var i=0;i<vm.videoData.length;i++){
                        //     vm.videoData[i].contentUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                        // }
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
        onPlay:function (id) {
            //播放时暂停别的正在播放的音频
            for(var item in vm.videoData){
                if(vm.videoData[item].id!=id){
                    var audio = document.getElementById(vm.videoData[item].id);
                    if(audio!==null){
                        //检测播放是否已暂停.audio.paused 在播放器播放时返回false.在播放器暂停时返回true

                        if(!audio.paused)
                        {
                            audio.pause();// 这个就是暂停//audio.play();// 这个就是播放
                        }
                    }
                }

            }


            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/updateCount?stuId="+id+"&stuType=stu_audio&stuFrom=audiocen",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        onPause: function (id,event) {
            var el = event.currentTarget;
            var temp =0;
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
                url: baseURL + "stumedia/countTime?stuId="+id+"&stuFrom=audiocen&type=audio&playTime="+temp+"&finishFlag="+finishFlag,
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
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
                        vm.reload();
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        cancelCollect:function (id) {
            var coll={
                comStucode:id,
                type:'10'
            };
            //取消收藏课程
            $.ajax({
                type: "POST",
                url: baseURL + "coll/delColl?comStucode="+id+"&type=10",
                contentType: "application/json",
                data: JSON.stringify(coll),
                success: function(result){
                    if(result.code === 0){
                        alert("取消收藏成功！");
                        vm.reload();
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        handleDetail :function (id,row) {
            this.title='查看';

            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
                        vm.startTime = new Date();
                        vm.stuMedia=result.data;
                        vm.caseContent=result.data.comContent;
                        vm.dialogPic=true;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL +  "stumedia/updateCount?stuId="+row.id+"&stuType=pic&stuFrom=piccen",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        closeDia:function () {
            this.dialogPic=false;
            //结束时间
            vm.endTime = new Date();
            var time = (vm.endTime - vm.startTime )/1000;
            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/countTime?stuId=' + vm.stuMedia.id+'&stuFrom=pic'+'&playTime='+time+'&type=pic',
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
                    }else{
                        alert(result.msg);
                    }
                }
            });
            this.reload();
        },
        collect:function (id,row) {
            var coll={
                sourceId: row.id,
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
                        vm.reload();
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
        },
        cancelCollect:function (id,row) {
            var coll={
                comStucode:row.id,
                type:'10'
            };
            // 取消收藏课程
            $.ajax({
                type: "POST",
                url: baseURL + "coll/delColl?comStucode="+row.id+"&type=10",
                contentType: "application/json",
                data: JSON.stringify(coll),
                success: function(result){
                    if(result.code === 0){
                        vm.$message('取消收藏成功');
                        vm.reload();
                    }else{
                        vm.$message(result.msg);
                    }
                }
            });
        },
    }
});
