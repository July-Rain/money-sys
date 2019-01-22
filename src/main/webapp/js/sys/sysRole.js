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
            console.table({
                "row": row
            })
        },
        handleEdit: function (index, row) {
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
                _data.id = vm.form.id;
                _data.roleName = that.form.roleName;
                _data.remarks = that.form.remarks;
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
        }
    }
    ,
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "role/list",
                data: vm.inline,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.inline.pageNo = result.page.pageNo;
                        vm.inline.pageSize = result.page.pageSize;
                        vm.inline.count = parseInt(result.page.count);
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