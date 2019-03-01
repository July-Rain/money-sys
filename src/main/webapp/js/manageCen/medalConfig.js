var vm = new Vue({
    el: '#app',
    data: {
        title:'新增头衔',
        tableData: [],//表格数据
        page: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            integral: '',
            credit: '',
            limit: 10,
            page: 1,
            count: 0,
        },

        medal:{},

        formLabelWidth: '150px',
        formLabelWidthS: '100px',
        fileList: [],
        rules: {
            titleName: [
                {max: 20, message: '最大长度20', trigger: 'blur'},
                {required: true, message: '请输入名称', trigger: 'blur'}
            ],
            badge: [
                {max: 20, message: '最大长度20', trigger: 'blur'},
                {required: true, message: '请输入', trigger: 'blur'}
            ],
            integral: [
                {required: true,max: 200, message: '最大长度200', trigger: 'blur'}
            ],
            credit: [
                {required: true,max: 200, message: '最大长度200', trigger: 'blur'}
            ]
        },
    },
    mounted: function () {

    },
    methods:{ //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.form.page - 1) * vm.form.limit;
        },
        // indexMethod(index) {
        //     return index+1;
        // },

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
            vm.medal={},
            vm.title = '新增头衔';
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
                        that.medal = result.data;
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
        save: function (formName) {
            this.$refs[formName].validate(function (valid) {

                    if (valid) {

                        //判断勋章名称  重复问题
                        var mytype = "1"

                        if (vm.medal.id != null && vm.medal.id != '') {

                            mytype = "2"
                        }

                        $.ajax({
                            type: "POST",
                            url: baseURL + "medal/medalName?medalname=" + vm.medal.titleName + "&mytype=" + mytype + "&id=" + vm.medal.id,
                            dataType: "json",
                            async: false,
                            success: function (result) {
                                if (result.code == 0) {

                                    if (result.type == "1")//说明找到了
                                    {
                                         alert("存在重复的勋章名称，添加失败");
                                        return;
                                    }
                                    else if(result.type == "0")
                                    {
                                        $.ajax({
                                            type: "POST",
                                            url: baseURL + "medal/save",
                                            contentType: "application/json",
                                            data: JSON.stringify(vm.medal),
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
                                    }

                                } else {
                                    alert(result.msg);
                                }
                            }
                        });

                        //////////////////////////////////////////////////////////////////////////////////////////




                    }
                    else {
                        return false;
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