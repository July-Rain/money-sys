var menuId = $("#menuId").val();

var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        formInline: {
            parentId: '0'
        },
        childData: [],
        childDataTwo: [],
        dialogConfig: false,
        sysMenu: {
            id: '',
            type: 0,
            name: '',
            url: '',
            icon: '',
            perms: '',
            parentId: '0'
        },
        parentData: [],
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        nodeName: '',
        rules: {//表单验证规则
            name: [
                {required: true, message: '请输入目录/菜单名称', trigger: 'blur'},
                {max: 25, message: '最大长度25', trigger: 'blur'}
            ],
            url: [
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ]
        },
    },
    created: function () {
        this.$nextTick(function () {
            vm.refresh();
            vm.initParentData();
        })
    },
    methods: {
        typeChange: function(){
            var type = vm.sysMenu.type;
            if(type == 0){// 目录
                vm.rules.url = [
                    {max: 100, message: '最大长度100', trigger: 'blur'}
                ];
            } else {
                vm.rules.url = [
                    {max: 100, message: '最大长度100', trigger: 'blur'},
                    {required: true, message: '请输入路由信息', trigger: 'blur'},
                ];
            }
        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        change: function (row, expandedRows) {
            if(expandedRows.length > 1){
                expandedRows.shift();
            }
            vm.formInline.parentId = row.id;
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code === 0) {
                        vm.childData.splice(result.list.length);
                        vm.childData = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        changeTwo: function(row, expandedRows){
            if(expandedRows.length > 1){
                expandedRows.shift();
            }
            vm.formInline.parentId = row.id;
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code === 0) {
                        vm.childDataTwo.splice(result.list.length);
                        vm.childDataTwo = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getRowClass: function (row, rowIndex) {
            if(row.row.type != 0){
                return 'row-expand-cover';
            }
        },
        add: function () {
            vm.dialogConfig = true;
        },
        closeDia: function () {
            vm.cancel();
        },
        initParentData: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/catalog",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.parentData = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        nodeCheck: function (obj) {
            vm.nodeName = obj.name;
            vm.sysMenu.parentId = obj.id;

        },
        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sysmenu/save",
                        data: JSON.stringify(vm.sysMenu),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                vm.dialogConfig = false;
                                vm.sysMenu = {
                                    id: '',
                                    type: 0,
                                    name: '',
                                    url: '',
                                    icon: '',
                                    perms: '',
                                    parentId: '0'
                                };
                                vm.formInline.parentId = '0';
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
        edit: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysMenu = result.info;
                        if(vm.sysMenu.type == 0){
                            vm.rules.url = [
                                {max: 100, message: '最大长度100', trigger: 'blur'}
                            ];
                        } else {
                            vm.rules.url = [
                                {max: 100, message: '最大长度100', trigger: 'blur'},
                                {required: true, message: '请输入路由信息', trigger: 'blur'}
                            ];
                        }
                        vm.nodeName = result.parentName;
                        vm.dialogConfig = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        cancel: function () {
            vm.dialogConfig = false;
            vm.sysMenu = {
                id: '',
                type: 0,
                name: '',
                url: '',
                icon: '',
                perms: '',
                parentId: '0'
            };
        },
        toHome: function () {
            parent.location.reload()
        },
        remove: function (id, type) {
            var msg = "";
            if(type == 0){
                msg = "是否确认删除当前目录及下级目录和菜单";
            } else {
                msg = "是否确认删除当前菜单";
            }
            this.$confirm(msg, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                var arr = new Array();
                arr.push(id);
                $.ajax({
                    type: "POST",
                    url: baseURL + "sysmenu/delete",
                    contentType: "application/json",
                    data: JSON.stringify(arr),
                    success: function (result) {
                        if (result.code === 0) {
                            vm.formInline.parentId = '0';
                            vm.refresh();
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
        }
    }
});
