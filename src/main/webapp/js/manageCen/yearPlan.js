var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        page: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            planName: '',
            planContent: '',
            startDate: '',
            endDate: '',
            credit: '',
            integral : '',
            participantUser : '',
            participantDept : '',
            remarks: '',
            page: 1,
            limit: 10,
            count: 0
        },
        formLabelWidth: '120px',
        fileList: [],
        diffList: []
    },
    mounted: function () {

    },
    methods: {
        layFn() {
            $(".el-dialog").css("height", "auto")
        },
        // 文件上传
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },

        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        addPlan: function () {
            console.log(22)
            vm.dialogFormVisible = true
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
                url: baseURL + "schoolYearPlan/info/" + row.id,
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
            var arr = new Array();
            arr.push(row.id);
            $.ajax({
                type: "POST",
                url: baseURL + "schoolYearPlan/delete",
                contentType: "application/json",
                data: JSON.stringify(arr),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.dialogFormVisible = false;
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "schoolYearPlan/save",
                contentType: "application/json",
                data: JSON.stringify(vm.form),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.dialogFormVisible = false;
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "schoolYearPlan/list",
                contentType: "application/json",
                data: {
                    page: 1,
                    limit: 10
                },
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.form.page = result.page.page;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "schoolYearPlan/list",
                contentType: "application/json",
                data: vm.form,
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.form.page = result.page.page;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });


        })
    }

})