var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        pageNo: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            roleName: '',
            remarks: ''
        },
        formLabelWidth: '120px',
        diffList: [],
        typeList: [],
        qtList: [],
        treeData1: [],
        treeData2: [],
        defaultProps: {
            children: 'list',
            label: 'name'
        },
        defaultProps2: {
            children: 'child',
            label: 'fullName'
        }

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
            vm.dialogFormVisible = true
        },
        handleWatch: function (index, row) {
            console.table({
                "row": row
            })
        },
        handleEdit: function () {

        },

        handleDel: function (index, row) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "role/delete",
                    dataType: "json",
                    data: {
                        idList: [row.id]
                    },
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

        save: function () {
            let that = this;
            this.$confirm('是否确定提交？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                that.dialogFormVisible = false;
                //ajax
                let _data = {}
                _data.menuList = [];
                _data.orgList = [];
                that.$refs.tree1.getCheckedNodes().map((info)=>{
                    console.info("11",info)
                    _data.menuList.push(info.id)
                })
                that.$refs.tree2.getCheckedNodes().map((info)=>{
                    console.info("22",info)
                    _data.orgList.push(info.id)
                })
                _data.roleName = that.form.roleName;
                _data.remarks = that.form.remarks;
                $.ajax({
                    type: "POST",
                    url: baseURL + "role/save",
                    contentType: "application/json",
                    data: JSON.stringify(_data),
                    success: function (result) {
                        console.info("result",result)
                        if (result.code === 0) {
                            vm.reload();
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            });


        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "role/list",
                dataType: "json",
                data: {
                    pageNo: 1,
                    limit: 10
                },
                success: function (result) {
                    console.info("result",result);
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.form.pageNo = result.page.pageNo;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
    ,
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "role/list",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.form.pageNo = result.page.pageNo;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });

        })
        this.$nextTick(function () {
            var that = this;
            $.ajax({
                type: "GET",
                url: baseURL + "menu/elTree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData1 = result.menuList;
                    console.info("功能权限", result);

                }
            })
            $.ajax({
                type: "GET",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData2 = result.orgList;
                    console.info("数据权限", result);

                }
            })
        })
    }

})