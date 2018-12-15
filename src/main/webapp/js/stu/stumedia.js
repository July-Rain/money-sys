/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
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
            id:"",
            stuType: "1",
            stuTitle: "",
            comContent: "",
            deptIds: "",
            userIds: "",
            stuDescribe:""
        },
        rules: {//表单验证规则
            stuType: [
                {required: true, message: '请选择资料类型', trigger: 'blur'}
            ],
            stuTitle: [
                {required: true, message: '请输入标题', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            deptIds: [
                {required: true, message: '请选择适用部门', trigger: 'blur'}
            ],
            userIds: [
                {required: true, message: '请选择适用人员', trigger: 'blur'}
            ]
        },
        dialogStuMedia: false,//table弹出框可见性
        title:"",//弹窗的名称
        delIdArr: [],//删除数据
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        fileList:[],//文件列表
        importFileUrl:baseURL+"sys/upload",//文件上传url
        dialogDept: false,//部门的弹窗
        dialogUser: false,//人员的弹窗
        deptData:[],//部门树数据
        userData:[],//人员树数据
        defaultDeptProps:{
            children: 'child',
            label: 'orgName'
        },//部门树的默认格式
    },
    created: function () {

        this.$nextTick(function () {
            console.log(vm.deptData);
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

            //加载部门数据
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        debugger
                        vm.deptData = result.orgList;
                        console.log(vm.deptData);
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
                    var url = vm.stuMedia.id?"stumedia/updateStuMedia": "stumedia/insertStuMedia";
                    var deptArr = vm.stuMedia.deptIds.split(",");
                    var userArr = vm.stuMedia.userIds.split(",");
                    vm.stuMedia.deptArr=deptArr;
                    vm.stuMedia.userArr=userArr;
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.stuMedia),
                        success: function(result){
                            if(result.code === 0){
                                alert('操作成功', function(index){
                                    vm.stuMedia.id=result.id;
                                    vm.dialogStuMedia=false;
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
        addStuMedia: function () {
            this.stuMedia= {
                id:"",
                stuType: "1",
                stuTitle: "",
                comContent: "",
                deptIds: "",
                userIds: "",
                stuDescribe:""
            },
            this.title="新增";
            this.dialogStuMedia=true;
        },
        handleEdit: function (index, row){
            this.title="修改";
            this.dialogStuMedia=true;
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
            this.dialogStuMedia=false;
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
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);
        },
        uploadSuccess: function (response, file, fileList) {
            vm.stuMedia.comContent=response.accessoryId;
        },
        uploadError: function () {

        },
        beforeAvatarUpload: function (file) {
            /*if(!checkFile(file)) return false;*/
        },
        changeStuType: function () {
            //修改资料类型
            console.log(vm.stuMedia.stuType);
            vm.stuMedia.comContent="";
        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept=true;

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser=true;

        },
        confimDept: function () {
            this.dialogDept=false;
        },
        cancelDept: function () {
            this.dialogDept=false;
        }
    }
});
