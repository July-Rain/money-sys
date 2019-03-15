var vm = new Vue({
    el: '#app',
    data: function(){
        var data=[]
        var validateDate = function (rule, value, callback) {
            if (vm.planForm.endDate === '') {
                callback(new Error('请输入结束日期'))
            } else {
                if (!(new Date(Date.parse(vm.planForm.startDate.replace(/-/g, "/")))<
                    new Date(Date.parse( vm.planForm.endDate.replace(/-/g, "/"))))) {
                    callback(new Error('结束日期必须大于开始日期'))
                } else {
                    callback()
                }
            }
        };

    return {
        tableData: [],//表格数据
        page: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            page: 1,
            limit: 10,
            count: 0
        },
        planForm : {
            planName: '',
            planContent: '',
            startDate: '',
            endDate: '',
            credit: '',
            integral: '',
            remarks: '',
        },
        formLabelWidth: '120px',
        fileList: [],
        diffList: [],
        title:"",
        formRules: {//表单验证规则
            planName: [
                {required: true, message: '请输入计划名称', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            startDate: [
                {required: true, message: '请输入计划开始日期', trigger: 'change'},
            ],
            endDate: [
                { validator: validateDate,required: true, message: validateDate.callback, trigger: 'change'}
            ],
            // endDate: [
            //     { required: true, message: '请输入计划结束日期', trigger: 'change'}
            // ],
            credit: [
                {message: '请输入计划积分', trigger: 'change', required: true}
            ],
            integral: [
                {required: true, message: '请输入计划学分', trigger: 'change'}
            ]
        },
        };
    },
    mounted: function () {

    },
    methods: {
        layFn() {
            $(".el-dialog").css("height", "auto")
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
            vm.title="新增";
            vm.planForm={

            };
            vm.dialogFormVisible = true
        },

        handleWatch: function (index, row) {
            console.table({
                "row": row
            })
        },
        handleEdit: function (index, row) {
            vm.title="修改";
            $.ajax({
                type: "GET",
                url: baseURL + "schoolYearPlan/info/" + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.planForm = result.data;
                        vm.dialogFormVisible = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        handleDel: function (index, row) {
            var arr = new Array();
            arr.push(row.id);
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
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
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });
        },

        save: function (formName) {

            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "schoolYearPlan/save",
                        contentType: "application/json",
                        data: JSON.stringify(vm.planForm),
                        async:false,
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
            })
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
        },
        toHome: function () {
            parent.location.reload()
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