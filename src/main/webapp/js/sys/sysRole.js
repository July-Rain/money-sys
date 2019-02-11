var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        pageNo: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            id : '',
            roleName: '',
            remarks: '',
            menuList : [],
            orgList : []
        },
        formLabelWidth: '120px',
        diffList: [],
        typeList: [],
        qtList: [],
        treeData1: [],
        treeData2: [],
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        defaultProps2: {
            children: 'child',
            label: 'fullName'
        },
        inline: {
            limit: 10,
            page: 1,
            count: 0
        },
        title: '新增',
        isEdit: false,
        rules: {//表单验证规则
            roleName: [
                {required: true, message: '请输入角色名称', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
    },
    mounted: function () {
    },
    methods: {
        layFn() {
            $(".el-dialog").css("height", "auto")
        },
        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        addRole: function () {
            this.form = {
                id : '',
                roleName: '',
                remarks: '',
                menuList : [],
                orgList : []
            };
            vm.dialogFormVisible = true;
        },
        handleWatch: function (index, row) {
            vm.isEdit = true;
            vm.title = '详情';
            var that = this;
            $.ajax({
                type: "GET",
                url: baseURL + "role/info/" + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        that.form = result.data;
                        that.dialogFormVisible = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleEdit: function (index, row) {
            vm.title = '编辑';
            var that = this;
            $.ajax({
                type: "GET",
                url: baseURL + "role/info/" + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        that.form = result.data;
                        that.dialogFormVisible = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        handleDel: function (index, row) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                var arr = new Array();
                arr.push(row.id);
                $.ajax({
                    type: "POST",
                    url: baseURL + "role/delete",
                    contentType: "application/json",
                    data: JSON.stringify(arr),
                    success: function (result) {
                        if (result.code === 0) {
                            vm.reload();
                        } else {
                            alert(result.msg);
                        }
                    }
                });

            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {

                    let that = vm;

                    that.dialogFormVisible = false;
                    //ajax
                    let _data = {}
                    _data.menuList = [];
                    _data.orgList = [];
                    that.$refs.tree1.getCheckedNodes().map((info) => {
                        _data.menuList.push(info.id)
                    })
                    that.$refs.tree2.getCheckedNodes().map((info) => {
                        _data.orgList.push(info.id)
                    })
                    _data.id = vm.form.id;
                    _data.roleName = that.form.roleName;
                    _data.remark = that.form.remark;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "role/save",
                        contentType: "application/json",
                        data: JSON.stringify(_data),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }else {
                    return false;
                }
            });
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "role/list",
                dataType: "json",
                data: vm.inline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.inline.page = result.page.pageNo;
                        vm.inline.limit = result.page.pageSize;
                        vm.inline.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome:function () {
            parent.location.reload()
        },
        indexMethod: function (index) {
            return index + 1 + (vm.inline.page-1) * vm.inline.limit;
        },
        closeDia: function () {
            vm.title = '新增';
            vm.dialogFormVisible = false;
            vm.isEdit = false;
            vm.$refs.tree1.setCheckedKeys([]);
            vm.$refs.tree2.setCheckedKeys([]);
        }
    }
    ,
    created: function () {
        this.$nextTick(function () {
            vm.reload();
            var that = this;
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/elTree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData1 = result.list;

                }
            })
            $.ajax({
                type: "GET",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData2 = result.orgList;
                }
            })
        })
    }

})