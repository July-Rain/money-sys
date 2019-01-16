/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        caseData: [],//视频列表
        navData: [],//导航
        formInline: { // 搜索表单
            contentType:"1",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
            caseLawName:"",
            caseLawid:"",
            startTime:"",
            endTime:"",
            lawLevel:"",
            caseType:""
        },
        visible: false,
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        dialogLaw:false,//法律分类的弹窗
        multipleSelection:[],//法律分类弹窗
        playTime:0,//播放时间
        oldTime:0,//原播放时间
        caseTypeOption:[],
        lawLevelOption:[],
        caseProcessOption:[]

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
            // 案件类型
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"CASETYPE",Parentcode:"0"},
                success: function (result) {
                    vm.caseTypeOption=result.dictlist;
                }
            });
            // 法院层级
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"LAWLEVEL",Parentcode:"0"},
                success: function (result) {
                    vm.lawLevelOption=result.dictlist;
                }
            });
            // 审判程序
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"CASEPROCESS",Parentcode:"0"},
                success: function (result) {
                    vm.caseProcessOption=result.dictlist;
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
            this.formInline.caseLawid="";
            this.$refs[formName].resetFields();
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
                        for(var i=0;i<vm.caseData.length;i++){
                            vm.caseData[i].caseContentUrl=baseURL+"sys/download?accessoryId="+vm.caseData[i].comContent;
                            if(vm.caseData[i].videoPicAcc){
                                vm.caseData[i].videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.caseData[i].videoPicAcc;
                            }else{
                                vm.caseData[i].videoPicAccUrl="http://temp.im/640x260";
                            }
                        }
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                        console.info("caseData",vm.caseData)
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
            this.formInline.caseLawid="";
            this.formInline.caseLawName="";
            this.multipleSelection=this.$refs.lawtree.getCheckedNodes();
            for(var i=0;i<this.multipleSelection.length;i++){
                if (!this.formInline.caseLawid) {
                    this.formInline.caseLawid=this.multipleSelection[i].id;
                    this.formInline.caseLawName=this.multipleSelection[i].classifyName;
                }else{
                    this.formInline.caseLawid+=","+this.multipleSelection[i].id;
                    this.formInline.caseLawName+=","+this.multipleSelection[i].classifyName;
                }
            }
            this.dialogLaw=false;
        },
        cancelLaw: function () {
            this.dialogLaw=false;
        },
        changeType: function () {
            this.reload();
        },
        onPlay:function (id,flag) {
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "caseana/updateCount?stuId="+id+"&stuType="+flag+"&stuFrom=caseana",
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
            debugger
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
                url: baseURL + "caseana/countTime?stuId="+id+"&stuFrom=caseana&playTime="+temp+"&finishFlag="+finishFlag,
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
