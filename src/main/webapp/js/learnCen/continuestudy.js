/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var ztree;
var taskId=getUrlParam("id");
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "infoId",
            pIdKey: "infoParentId",
            rootPId: -1,

        },
        key: {
            url:"nourl",
            name:"infoName"
        }

    },
    callback: {
        onClick: zTreeOnClick
    }
};
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
        queryCond:{
            infoType:"",
            taskId:"",
            infoId:"",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
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
        multipleSelection:[]//法律分类弹窗
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
            this.getZtree();
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
        getZtree: function(){
            //加载部门树
            $.get(baseURL + "learntasks/zTree?id="+taskId, function(r){
                ztree = $.fn.zTree.init($("#classTree"), setting, r.data);
            })
        },
    }
});

//ztree节点点击事件
function zTreeOnClick(event, treeId, treeNode) {
    console.log(treeNode);
    var type = treeNode.infoType;
    vm.queryCond.infoType= treeNode.infoType;
    vm.queryCond.taskId= treeNode.taskId;
    vm.queryCond.infoId= treeNode.infoId;

    //法律分类树数据
    $.ajax({
        type: "POST",
        url: baseURL + "learntasks/allInfo",
        dataType: "json",
        data: vm.queryCond,
        success: function(result){
            console.log(result)
        }
    });
}
