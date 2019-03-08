/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var ztree;
var taskId=getUrlParam("id");
// var setting = {
//     data: {
//         simpleData: {
//             enable: true,
//             idKey: "infoId",
//             pIdKey: "infoParentId",
//             rootPId: -1,
//
//         },
//         key: {
//             url:"nourl",
//             name:"infoName"
//         }
//
//     },
//     callback: {
//         onClick: zTreeOnClick
//     }
// };
var vm = new Vue({
    el: '#app',
    data: {
        infoData: [],//列表
        navData: [],//导航
        formInline: { // 搜索表单
            stuType:"law",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
            stuLawName:"",
            stuIssuer:"",
            startTime:"",
            endTime:""
        },
        queryCond:{
            infoType:"law",
            taskId:"",
            infoId:"",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
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
        infoFlag:"law",//页面展示标记
        playTime:0,//播放时间
        oldTime:0,//原播放时间
        dialogPic:false,
        caseContent:"",
        title:"查看",
        defaultLearnTaskProps:{
            children: 'list',
            label: 'classifyName'
        },//学习内容默认数据
        classData:[],//树形数据
    },
    created: function () {

        this.$nextTick(function () {

            //法律分类树数据
            // $.ajax({
            //     type: "POST",
            //     url: baseURL + "law/tree",
            //     contentType: "application/json",
            //     success: function(result){
            //         if(result.code === 0){
            //             vm.treeData = result.classifyList;
            //         }else{
            //             alert(result.msg);
            //         }
            //     }
            // });
            //加载法律法规数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/alltree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        console.log(result.classifyList);
                        vm.classData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });

        })
        this.$nextTick(function () {
            this.reload();
            //this.getZtree();
        })

    },
    methods: {
        //序列号计算
        indexMethod:function (index) {
            return index + 1 + (vm.queryCond.currPage-1) * vm.queryCond.pageSize;
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
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "learntasks/allInfo",
                dataType: "json",
                data: vm.queryCond,
                success: function (result) {
                    if (result.code == 0) {
                        debugger
                        vm.infoData=result.page.list;
                        vm.infoFlag=result.page.remarks;
                        if(vm.infoFlag=='stu_video'||vm.infoFlag=='stu_audio'){
                            for(var i=0;i<vm.infoData.length;i++){
                                vm.infoData[i].contentUrl=baseURL+"sys/download?accessoryId="+vm.infoData[i].comContent;
                                if(vm.infoData[i].videoPicAcc){
                                    vm.infoData[i].videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.infoData[i].videoPicAcc;
                                }else{
                                    vm.infoData[i].videoPicAccUrl="http://temp.im/640x260";
                                }
                            }
                        }
                        else if(vm.infoFlag=='stu_video'||vm.infoFlag=='stu_audio') {
                            for (var i = 0; i < vm.caseData.length; i++) {
                                vm.caseData[i].caseContentUrl = baseURL + "sys/download?accessoryId=" + vm.caseData[i].comContent;
                                if (vm.caseData[i].videoPicAcc) {
                                    vm.caseData[i].videoPicAccUrl = baseURL + "sys/download?accessoryId=" + vm.caseData[i].videoPicAcc;
                                } else {
                                    vm.caseData[i].videoPicAccUrl = "http://temp.im/640x260";
                                }
                            }
                        }
                        vm.queryCond.currPage = result.page.currPage;
                        vm.queryCond.pageSize = result.page.pageSize;
                        vm.queryCond.totalCount = parseInt(result.page.totalCount);
                        console.info("infoData",vm.infoData)
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
        getZtree: function(){
            //加载部门树
            $.get(baseURL + "learntasks/zTree?id="+taskId, function(r){
                ztree = $.fn.zTree.init($("#classTree"), setting, r.data);
            })
        },
        closeDia:function(){
            vm.dialogPic=false;
        },
        handleDetail: function (index , row) {
            //查看详情信息
            //1.记录学习记录
            if(vm.infoFlag=='law'){
                this.insertStuRecord(row.id);
            }else if(vm.infoFlag=='stu_pic'){

                this.countStu(row.id);
            }else if(vm.infoFlag=='case_pic'){

                this.countCase(row.id);
            }
        },
        insertStuRecord: function (id) {
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL +  "sturecord/insertRecord?stuId="+id+"&stuType="+vm.infoFlag+"&stuFrom=learntask&taskId="+vm.queryCond.taskId,
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
        countStu:function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/info?id=' + id,
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
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
                url: baseURL +  "stumedia/updateCount?stuId="+id+"&stuType="+vm.infoFlag+"&stuFrom=learntask&taskId="+vm.queryCond.taskId,
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
        countCase:function (id) {
            //请求后台修改播放量 记录学习记录 --案例分析模块
            $.ajax({
                type: "POST",
                url: baseURL + 'caseana/info?id=' + id,
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
                        vm.caseContent=result.data.caseContent;
                        vm.dialogPic=true;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            $.ajax({
                type: "POST",
                url: baseURL +  "caseana/updateCount?id="+id+"&stuType="+vm.infoFlag+"&stuFrom=learntask&taskId="+vm.queryCond.taskId,
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
        onPlay:function (id,flag) {
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "caseana/updateCount?stuId="+id+"&stuType="+flag+"&stuFrom=learntask",
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
                url: baseURL + "caseana/countTime?stuId="+id+"&stuFrom=learntask&playTime="+temp+"&finishFlag="+finishFlag,
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
        handleTaskNodeClick:function(data){
            vm.queryCond.taskId= data.taskId;
            vm.queryCond.infoId= data.infoId;
            vm.reload();

        },//点击事件
    }
});

