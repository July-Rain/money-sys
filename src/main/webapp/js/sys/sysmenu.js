/**
 * Author:
 * Date: 2018
 * Description:
 */
var menuId = $("#menuId").val();

var ztree;
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            parentId:'0',
            type:'0',
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        tableData: [],//表格数据
        visible: false,

        sysMenu: {
            parentName:null,
            parentId:0,
            type:1,
            orderNum:0
        },
        data: [
            {// el-tree数据
                label: '一级 1',
                children: [{
                    label: '二级 1-1',
                    children: [{
                        label: '三级 1-1-1'
                    }]
                }]
            }, {
                label: '一级 2',
                children: [{
                    label: '二级 2-1',
                    children: [{
                        label: '三级 2-1-1'
                    }]
                }, {
                    label: '二级 2-2',
                    children: [{
                        label: '三级 2-2-1'
                    }]
                }]
            }, {
                label: '一级 3',
                children: [{
                    label: '二级 3-1',
                    children: [{
                        label: '三级 3-1-1'
                    }]
                }, {
                    label: '二级 3-2',
                    children: [{
                        label: '三级 3-2-1'
                    }]
                }]
            }],
        rules: {//表单验证规则
            name: [
                {required: true, message: '请输入目录名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            orderNum: [
                {required: true, message: '请输入排序号', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "menu/listAllMenuTree",
                dataType: "json",
                success: function (result) {
                    console.info(result);
                }
            });
            this.reload();
        })
    },
    methods: {
        menuTree: function () {



        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        // 保存和修改
        saveOrUpdate: function (formName) {

            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysMenu.id ? "menu/update" : "menu/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysMenu),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.dialogConfig = false;
                                        vm.reload();
                                    }
                                });
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        addConfig: function () {
            vm.sysMenu = {
                id:'',
                parentId:"0",

                type:"0",

            };
            this.title = "新增";

            this.dialogConfig = true;
        },
        handleEdit: function (index, row) {
            this.title = "修改目录";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'menu/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysMenu = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        showSon: function (index, row) {
            parent.location.href =baseURL+"modules/sys/sysmenuSon.html?id="+row.id;
        },
        handleDel: function (index, row) {
            // vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'menu/delete?id='+row.id,
                    async: true,
                    // data: JSON.stringify(row.id),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();

                        vm.$message({
                            type: 'success',
                            message: result.str
                        });

                    }
                });
            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {
            // $.ajax({
            //     type: "POST",
            //     url: baseURL + "menu/list",
            //     dataType: "json",
            //     data: vm.formInline,
            //     success: function (result) {
            //         if (result.code === 0) {
            //             vm.tableData=result.page.list;
            //
            //             vm.formInline.currPage = result.page.currPage;
            //             vm.formInline.pageSize = result.page.pageSize;
            //             vm.formInline.totalCount = parseInt(result.page.totalCount);
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });
        }
    }
});
var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '菜单ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '菜单名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级菜单', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '60px', formatter: function(item, index){
            return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
        }},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){


            if(item.type == 0){
                return '<span class="label label-primary">目录</span>';
            }
            if(item.type == 1){
                return '<span class="label label-success">菜单</span>';
            }
            if(item.type == 2){
                return '<span class="label label-warning">按钮</span>';
            }
        }},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '70px'},
        {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: '授权标识', field: 'perms', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};

$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "menu/list2", colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;
});