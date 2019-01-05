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
            stuType:"3",
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
            stuType: "1",
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
            this.reload();
        })

    },
    methods: {
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
                        for(var i=0;i<vm.videoData.length;i++){
                            vm.videoData[i].contentUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                            if(vm.videoData[i].videoPicAcc){
                                vm.videoData[i].videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].videoPicAcc;
                            }else{
                                vm.videoData[i].videoPicAccUrl="http://temp.im/640x260";
                            }
                            if(vm.videoData[i].stuType=='1'){
                                vm.videoData[i].stuType="文字";
                            }else if(vm.videoData[i].stuType=='2'){
                                vm.videoData[i].stuType="音频";
                            }else if(vm.videoData[i].stuType=='3'){
                                vm.videoData[i].stuType="视频";
                            }
                        }
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                        console.info("videoData",vm.videoData)
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
        onPlay:function (id,accId) {
            vm.playTime=0;
            debugger
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/updateCount?stuId="+id+"&stuType=stu_video&stuFrom=videocen",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            $("#demo-video").on(
                "timeupdate",
                function(event){
                    vm.playTime=this.currentTime;
                    console.log(this.currentTime, this.duration);
                });
        },
        onPause: function (id) {
            debugger
            //媒介因素暂停事件
            //请求后台记录观看时长
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/countTime?stuId="+id+"&stuFrom=videocen&playTime="+vm.playTime,
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
var timeinfo;
/*$(document).ready(function () {
    debugger
    var options = {
    };
    var player = videojs('demo-video', options, function onPlayerReady() {
        debugger
        var time1;
        var t1 = 0;
        function aa() {
            t1 += 0.25;
            timeinfo = t1;
            console.log('aa-' + t1);
        }
        //开始播放视频时，设置一个定时器，每250毫秒调用一次aa(),观看时长加2.5秒
        this.on('play', function () {
            console.log('开始播放');
            time1 = setInterval(function () {
                aa();
            }, 250)
        });
        //结束和暂时时清除定时器，并向后台发送数据
        this.on('ended', function () {
            console.log('结束播放');
            window.clearInterval(time1);
            countTime();   //向后台发数据
        });
        this.on('pause', function () {
            console.log('暂停播放');
            window.clearInterval(time1);
            countTime();  //向后台发数据
        });
    });
    //直接关闭页面，并向后台发送数据
    if(window.addEventListener){
        window.addEventListener("beforeunload",countTime,false);
    }else{
        window.attachEvent("onbeforeunload",countTime);
    }
})*/

function countTime() {
    debugger
    var sTime = timeinfo;
    console.log(timeinfo)
    /*$.ajax({
        url: "{{ route('time') }}",
        type: "POST",
        dataType: 'json',
        data: {'sTime': sTime, '_token': "{{ csrf_token() }}"},
        success: function (data) {
            console.log(data);
        }
    })*/
}
