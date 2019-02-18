var menuId = getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        labelPosition: 'left',
        navData: [],//导航
        formInline: { // 搜索表单
            examName :'',
            startTime:'',
            endTime:'',
            pageNo: 1,
            pageSize: 1,
            limit: 10,
            count: 0
        },
        tableData: [],//表格数据
        visible: false,
        examConfig: {
            id: '',
            examName: '',
            code: '',
            value: '',
            remark: '',
            status: "1"
        },
        checkSetting: {},
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
        checkSettingRules: {},
        dialogConfig: false,//table弹出框可见性
        checkSettingDia: false,//阅卷设置弹出框
        title: "",//弹窗的名称
        delIdArr: [],//删除数据
        dialogAdd: false
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
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
        addConfig: function () {
            // parent.location.href = baseURL + "modules/examCen/examConfig.html";
            this.dialogAdd = true
        },
        resetForm: function (formName) {
            alert(formName);
            this.$refs[formName].resetFields();
        },
        handleEdit: function (index, row) {
            this.title = "修改参数";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'sysconfig/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysConfig = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'sysconfig/delete',
                    async: true,
                    data: JSON.stringify(vm.delIdArr),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
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
        handleChange: function () {
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        closeCheckSettingDia: function () {
            this.checkSettingDia = false;
            vm.reload();
        },
        handleChange: function () {

        },
        saveCheckSet: function () {
            console.info(vm.checkSetting);
            $.ajax({
                type: "POST",
                url: baseURL + "exam/config/checkset",
                dataType: "json",
                data: JSON.stringify(vm.checkSetting),
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        alert("保存成功");
                        this.checkSettingDia = false;
                        vm.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exam/config/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.pageNo;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        generateOrViewCheck: function (index, row) {
            var checkPassword = row.checkPassword;
            if (checkPassword) {
                //查看

            } else {
                //生成
                this.checkSettingDia = true;
                vm.checkSetting.id = row.id;
            }
        },
        toChild: function (item) {

            parent.location.href = baseURL + item.url + "?id=" + item.id;

        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
