/**
 * Author: zhujunwen
 * Date: 2018/12/21
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        videoData: [],//视频列表
        navData: [],//导航
        formInline: { // 搜索表单
            value: '',
            name: '',
            status: "",
            stuType:"video",
            currPage: 1,
            addsrc:"1",
            createUser:"",
            pageSize: 10,
            totalCount:0,
            createUser:"",
            addsrc:"",
            stuTitle:"",
            stuLawid:""
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
           /* $.ajax({
                type: "POST",
                url: baseURL + "stumedia/list",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.videoData = result.page.list;
                        for(var i=0;i<vm.videoData.length;i++){
                            vm.videoData[i].comContent=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                        }
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
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
            this.$refs[formName].resetFields();
        },
        reload: function () {
            vm.formInline.createUser=getUrlParam("createUser");
            vm.formInline.addsrc=getUrlParam("addsrc");
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
                        console.info("videoData",vm.videoData)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // el-tree节点点击事件
        handleNodeClick: function (data) {
            console.log(data);
            vm.formInline.stuLawid=data.classifyId;
            vm.reload();
        },
    }
});
