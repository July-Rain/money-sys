/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        formInline: { // 搜索表单
            classifyId: '',
            currPage: 1,
            pageSize: 10,
            totalCount:0,
            status:"",
            issueOrg:"",
            lawTitle:"",
            libName:"",
            libId:""
        },
        tableData: [],//表格数据
        visible: false,
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        dialogLib: false,//法律法规库的弹窗
        libData:[],//部门树数据
        defaultLibProps:{
            children: 'child',
            label: 'libName'
        },//部门树的默认格式
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

            //加载法律知识库
            $.ajax({
                type: "POST",
                url: baseURL + "classlib/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.libData = result.data;
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
            this.$refs[formName].resetFields();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "classdesic/list?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
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
            vm.formInline.classifyId=data.id;
            this.reload();
            //console.log(data);
        },
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode=data.orgCode;
            this.reloadUser();
        },

        chooseLib: function () {
            //选择部门
            console.log(vm.libData);
            this.dialogLib=true;

        },
        confimLib: function () {
            var node=this.$refs.libTree.getCurrentNode();
            vm.formInline.libId=node.libId;
            vm.formInline.libName=node.libName;
            this.dialogLib=false;
        },
        cancelLib: function () {
            this.dialogLib=false;
        },
        handleCheckChange:function () {
            
        },
        clearLib:function () {
            vm.formInline.libId="";
            vm.formInline.libName="";
        },
        handleDetail: function (index,row) {
            //查看详情
        }
    }
});
