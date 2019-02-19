var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],
        childData: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0,
            parentCode: null
        },
        childInline: {
            limit: 10,
            page: 1,
            count: 0,
            parentCode: null
        },
        rules: {
            name: [
                {max: 20, message: '最大长度20', trigger: 'blur'},
                {required: true, message: '请输入字典类型名称', trigger: 'blur'}
            ],
            code: [
                {max: 20, message: '最大长度20', trigger: 'blur'},
                {required: true, message: '请输入字典类型编码', trigger: 'blur'}
            ],
            remark: [
                {max: 200, message: '最大长度200', trigger: 'blur'}
            ]
        },
        diction: {
            name: '',
            code: '',
            sort: 1,
            remark: ''
        },
        dictConfig: false,
        parentId: null
    },
    methods: {
        toHome: function () {
            parent.location.reload()
        },
        change: function (row, expandedRows) {
            if(expandedRows.length > 1){
                expandedRows.shift();
            }
            vm.childList(row.id);
        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "dict/list",
                data: vm.formInline,
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        childList: function(parentCode){
            vm.childInline.parentCode = parentCode;
            $.ajax({
                type: "GET",
                url: baseURL + "dict/list",
                data: vm.childInline,
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.childData.splice(result.page.list.length);
                        vm.childData = result.page.list;
                        vm.childInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        add: function () {
            vm.dictConfig = true;
            vm.parentId = null;
        },
        closeDia: function () {
            // 重置表单信息
            vm.resetForm();
            vm.dictConfig = false;
            vm.parentId = null;
        },
        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    vm.diction.parentCode = vm.parentId;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "dict/save",
                        data: JSON.stringify(vm.diction),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                vm.resetForm();
                                vm.dictConfig = false;
                                vm.refresh();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        cancel: function () {
            vm.closeDia();
        },
        resetForm: function () {
            vm.diction = {
                name: '',
                code: '',
                sort: 1,
                remark: ''
            };
            this.$refs['diction'].resetFields();
        },
        edit: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "dict/info",
                data: {
                    id: id
                },
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.diction = result.info;
                        vm.dictConfig = true;
                        vm.parentId = result.info.parentCode;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        remove: function (id, isParent) {
            this.$confirm('是否确定删除字典类型及字典信息', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "dict/delete?id="+id+"&isParent="+isParent,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.code === 0) {
                            vm.refresh();
                        } else {
                            alert(result.msg);
                        }
                    }
                });

            }).catch(function () {
            });
        },
        manage: function (id) {
            vm.parentId = id;
            vm.dictConfig = true;
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.formInline.parentCode = null;
            vm.refresh();
        })
    }
});