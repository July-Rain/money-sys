var vm = new Vue({
    el: '#app',
    data: {
        title:'新增头衔',
        tableData: [],//表格数据
        page: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            titleName: '',
            integral: '',
            credit: '',
            badge:'',
            limit: 10,
            page: 1,
            count: 0,
        },
        formLabelWidth: '200px',
        formLabelWidthS: '143px',
        fileList: []
    },
    mounted: function () {

    },
    methods:{
        indexMethod(index) {
            return index+1;
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
            return this.$confirm(`确定移除 ${ file.name }？`);
        },

        handleSizeChange: function (event) {
            vm.form.limit = event;
            vm.reload();
        },
        handleCurChange: function (event) {
            vm.form.page = event;
            vm.reload();
        },
        addMedal: function () {
            this.form = {
                titleName: '',
                integral: '',
                credit: '',
                badge:''
            };
            this.title = '新增头衔';
            vm.dialogFormVisible = true
        },

        handleEdit: function (index, row) {
            var that = this;
            $.ajax({
                type: "GET",
                url: baseURL + "medal/info/" + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        that.form = result.data;
                        that.title = '编辑头衔';
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
                url: baseURL + "medal/delete",
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
                url: baseURL + "medal/save",
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
                url: baseURL + "medal/list",
                dataType: "json",
                data: vm.form,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.form.page = result.page.pageNo;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        }
    },
    created: function(){
        this.$nextTick(function () {
            // $.ajax({
            //     type: "GET",
            //     url: baseURL + "medal/list",
            //     contentType: "application/json",
            //     data:vm.form,
            //     success: function (result) {
            //         if (result.code === 0) {
            //             vm.tableData = result.page.list;
            //             vm.form.page = result.page.pageNo;
            //             vm.form.pageSize = result.page.pageSize;
            //             vm.form.count = parseInt(result.page.count);
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });
            vm.reload();
        })
    }

})