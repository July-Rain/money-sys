/**
 * Author:
 * Date: 2018
 * Description:
 */

var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        formInline: { // 搜索表单

            parentId:menuId,
            type:1,
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        tableData: [],//表格数据
        visible: false,
        sysConfig: {
            id: '',
            code: '',
            value: '',
            remark: '',
            status: "1"
        },
        sysMenu: {

        },
        rules: {//表单验证规则
            name: [
                {required: true, message: '请输入目录名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            orderNum: [
                {required: true, message: '请输入排序号', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
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
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        // 保存和修改
        saveOrUpdate: function (formName) {

            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysMenu.id ? "menu/update" : "menu/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysMenu),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.dialogConfig = false;
                                        vm.reload();
                                    }
                                });
                            } else {
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
        addMenu: function () {
            vm.sysMenu = {
                id:'',
                parentId:menuId,
                type:"1",

            };
            this.title = "新增";
            this.dialogConfig = true;
        },
        handleEdit: function (index, row) {
            this.title = "修改目录";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'menu/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysMenu = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        showSon: function (index, row) {
            parent.location.href =baseURL+"modules/sys/sysmenuSon.html?id="+row.id;
        },
        handleDel: function (index, row) {
            // vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'menu/delete?id='+row.id,
                    async: true,
                    // data: JSON.stringify(row.id),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();

                        vm.$message({
                            type: 'success',
                            message: result.str
                        });

                    }
                });
            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "menu/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {

                    if (result.code === 0) {
                        vm.tableData=result.page.list;

                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
