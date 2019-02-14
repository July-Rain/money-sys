/**
 * Author:
 * Date: 2018
 * Description:
 */
var Dict = {
    id: "dictTable",
    table: "null",
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dict.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '字典ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '字典名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '字典类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '字典码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '70px'},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};

var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            parentId: '0',
            type: '0',
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        tableData: [],//表格数据
        visible: false,

        sysDict: {
            id: "",
            name: "",
            type: "",
            code: "",
            orderNum: "",
            remark: "",
            parentCode: ""
        },
        /*sysMenu: {
            parentName:null,
            parentId:'',

            orderNum:0,
            visible: false,
            type:'',

        },*/
        dictList: [],
        //menuList:[],
        dictListTreeProps: {
            children: 'list',
            label: 'name'
        },

        rules: {//表单验证规则
            name: [
                {required: true, message: '请输入目录名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
            // orderNum: [
            //     {required: true, message: '请输入排序号', trigger: 'blur'},
            //     {max: 50, message: '最大长度50', trigger: 'blur'}
            // ]
        },
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            this.dict();
            // this.reload();
        })
    },
    methods: {
        dict:function () {
            var colunms = Dict.initColumn();
            var table = new TreeTable(Dict.id, baseURL + "dict/dictList", colunms);
            table.setExpandColumn(2);
            table.setIdField("id");
            table.setCodeField("id");
            table.setParentCodeField("parentCode");
            table.setExpandAll(false);
            table.init();
            Dict.table = table;
        },
        // 菜单树选中
        dictListTreeCurrentChangeHandle: function (data) {

            vm.sysDict.parentName = data.name;//选中后把name给页面显示
            vm.sysDict.parentCode = data.code;//选中后把父节点id拿到

            if (data.parentcode == "-1") {
                vm.sysDict.type = "0";//选中后把父节点id拿到
            }
            else {
                vm.sysDict.type = "1";//选中后把父节点id拿到
            }
            vm.sysDict.visible = false

        },
        // 保存和修改
        saveOrUpdate: function (formName) {

            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysDict.id ? "dict/update" : "dict/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysDict),
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
        // resetForm: function (formName) {
        //     this.$refs[formName].resetFields();
        // },
        addConfig: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "/dict/listAllDictTree",
                dataType: "json",
                success: function (result) {
                    vm.dictList = result.listAllDictTree;
                }
            });
            vm.sysDict = {
                id: '',
                type: "0",
            };
            this.title = "新增";
            this.dialogConfig = true;
        },
        handleEdit: function () {
            var id = getDictId();
            if (id == null) {
                return;
            }
            this.title = "修改";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'dict/info?id=' + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sysDict = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function () {
            var id = getDictId();
            if (id == null) {
                return;
            }

            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'dict/delete?id=' + id,
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
                /*$.ajax({
                    type: "POST",
                    url: baseURL + 'menu/delete?id='+id,
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
                });*/
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
            Dict.table.refresh();
            /*$.ajax({
                type:"GET",
                url:baseURL+"/dict/dictList",
                contentType:"application/json",
                success:function(result){
                    result.dictList;
                }
            });*/
        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
/*var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};*/

function getDictId() {
    var selected = $('#dictTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}