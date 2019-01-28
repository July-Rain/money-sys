var menuId = $("#menuId").val();

var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        formInline: {
            parentId: '0'
        },
        childData: [],
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
        nodeName: ''
    },
    created: function () {
        this.$nextTick(function () {
            vm.refresh();
            vm.initParentData();
        })
    },
    methods: {
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
        save: function () {
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
        },
        edit: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysMenu = result.info;
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
        }
    }
});
