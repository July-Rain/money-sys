/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:参数配置
 */
var menuId=$("#menuId").val();
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            value: '',
            name: '',
            status: "",
            currPage: 1,
            pageSize: 10,
            totalCount:0
        },
        tableData: [],//表格数据
        visible: false,
        stuMedia: {
            id: '',
            code: '',
            value: '',
            remark: '',
            status: "1"
        },
        rules: {//表单验证规则
            value: [
                {required: true, message: '请输入参数名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入参数值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        title:"",//弹窗的名称
        delIdArr: [],//删除数据
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        fileList:[],//文件列表
        importFileUrl:baseURL+"system/upload",//文件上传url
    },
    created: function () {

        this.$nextTick(function () {
            debugger

            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id="+menuId,
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.navData = result.menuList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "law/tree",
                contentType: "application/json",
                success: function(result){
                    debugger
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
        // 保存和修改
        saveOrUpdate: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.stuMedia.id ? "sysconfig/update" :  "sysconfig/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.stuMedia),
                        success: function(result){
                            if(result.code === 0){
                                alert('操作成功', function(index){
                                    vm.dialogConfig=false;
                                    vm.reload();

                                });
                            }else{
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        addConfig: function () {
            this.stuMedia= {
                id: '',
                code: '',
                value: '',
                remark: '',
                status: "1"
            };
            this.title="新增参数";
            this.dialogConfig=true;
        },
        handleEdit: function (index, row){
            this.title="修改参数";
            this.dialogConfig=true;
            $.ajax({
                type: "POST",
                url: baseURL + 'sysconfig/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
                        vm.stuMedia = result.data;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row){
            vm.delIdArr.push(row.id);
            var that = this
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                debugger
                $.ajax({
                    type: "POST",
                    url: baseURL + 'sysconfig/delete' ,
                    async:true,
                    data: JSON.stringify(vm.delIdArr),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        that.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
                /*that.$message({
                    type: 'success',
                    message: '删除成功!'
                });*/

            }).catch(function () {
                that.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia : function(){
            this.dialogConfig=false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/list",
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
            console.log(data);
        },
        uploadSuccess: function (response, file, fileList) {
            vm.stuMedia.comContent=response.accessoryId;
        },
        uploadError: function () {

        },
        beforeAvatarUpload: function () {
            if(!checkFile(file)) return false;
        },
    }
});
