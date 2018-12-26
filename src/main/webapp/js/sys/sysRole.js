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
        treeData1: [
            {
                id: 1,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{
                        id: 9,
                        label: '三级 1-1-1'
                    }, {
                        id: 10,
                        label: '三级 1-1-2'
                    }]
                }]
            },
            {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
            },
            {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id: 8,
                    label: '二级 3-2'
                }]
            }
        ],
        treeData2: [
            {
                id: 199,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{
                        id: 9,
                        label: '三级 1-1-1'
                    }, {
                        id: 10,
                        label: '三级 1-1-2'
                    }]
                }]
            },
            {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
            },
            {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id: 8,
                    label: '二级 3-2'
                }]
            }
        ],
        defaultProps: {
            children: 'children',
            label: 'label'
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
                    url: baseURL + "testQuestion/delete",
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
            });


        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "testQuestion/list",
                dataType: "json",
                data: {
                    pageNo: 1,
                    limit: 10
                },
                success: function (result) {
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
                url: baseURL + "testQuestion/list",
                contentType: "application/json",
                data: vm.form,
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
                url: baseURL + "menu/list2",
                contentType: "application/json",
                success: function (result) {
                    that.treeData1 = [];
                    console.info("功能权限", result);
                    result.map((info) => {
                        if (!info.parentName) {
                            that.treeData1.push({
                                id: info.id,
                                label: info.name
                            })
                        }
                    })
                }
            })
            // $.ajax({
            //     type: "GET",
            //     url: baseURL + "org/tree",
            //     contentType: "application/json",
            //     success: function (result) {
            //         console.info("数据权限",result)
            //     }
            // })
        })
    }

})