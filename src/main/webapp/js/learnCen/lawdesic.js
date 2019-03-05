/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var vm = new Vue({
    el: '#app',
    data: {
        libIds:[],//lib第一层tree的id
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
        dialogLaw:false,//查看详情页面
        lawDesic:{},//法律法规实体信息
        title:"",//法律法规查看详情标题
    },
    created: function () {

        this.$nextTick(function () {
            //法律分类树数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/alltree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        console.log(result.classifyList)
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
                        vm.libData.map(function (val) {
                            vm.libIds.push(val.libId)
                        })
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
            vm.formInline.libId="";
            this.$refs[formName].resetFields();
            vm.reload();
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
            vm.formInline.libId=node.libCode;
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
            vm.title=row.lawTitle;
            //查看详情
            $.ajax({
                type: "POST",
                url: baseURL +  "synlaw/lawDetail?lawid="+row.id+"&rid="+row.libId,
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.dialogLaw=true;
                        if(result.info.list[0]){
                            vm.lawDesic=result.info.list[0];
                        }
                        console.log(result);
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            //记录学习记录
            this.insertRecord(row.id);
        },
        closeDia:function(){
            vm.dialogLaw=false;
        },
        insertRecord: function (id) {
            //插入学习记录
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL +  "sturecord/insertRecord?stuId="+id+"&stuType=law"+"&stuFrom=law",
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
        }
    }
});
