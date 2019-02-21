var vm = new Vue({
    el: '#app',
    data: {
        tree1Arrs:[],
        tree2Arrs:[],
        tableData: [],//表格数据
        pageNo: 1,//分页：当前页
        loadPower:0,//加载功能权限与数据权限
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
        treeData1D: [],
        treeData2: [],
        treeData2D: [],
        tree2Disb:true,
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        defaultProps3: {
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
        },
        title: '新增',
        isEdit: false,
        rules: {//表单验证规则
            roleName: [
                {required: true, message: '请输入角色名称', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
    },
    mounted: function () {
    },
    methods: {
        layFn() {
            $(".el-dialog").css("height", "auto")
        },
        handleSizeChange: function (event) {
            vm.inline.limit = event;
            vm.reload();
        },
        handleCurChange: function (event) {
            vm.inline.page = event;
            vm.reload();
        },
        addRole: function () {
            this.form = {
                id : '',
                roleName: '',
                remarks: '',
                menuList : [],
                orgList : []
            };
            vm.tree2Disb = true;
            vm.title =  '新增角色';
            vm.dialogFormVisible = true;
            vm.isEdit = false;
        },
        handleWatch: function (index, row) {
            vm.tree2Disb = false;

            var that = this;
            vm.isEdit = true;
            vm.title = '详情';

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
        addOrremDisabled: function (ar,child,bool){
            // 笨方法 - 更改位置层级下的children的disabled 到6层
            var arr = [];
            ar.map(function (val) {
                arr.push(val)
            });
            if((arr instanceof Array)){
                arr.map(function (val,index) {
                    arr[index]["disabled"] = bool;
                    if(arr[index][child] instanceof Array){
                        arr[index][child].map(function (val2,index2) {
                            arr[index][child][index2]["disabled"] = bool;
                            if(arr[index][child][index2][child] instanceof Array){
                                arr[index][child][index2][child].map(function (val3,index3) {
                                    arr[index][child][index2][child][index3]["disabled"] = bool;
                                    if(arr[index][child][index2][child][index3][child] instanceof Array){
                                        arr[index][child][index2][child][index3][child].map(function (val4,index4) {
                                            arr[index][child][index2][child][index3][child][index4]["disabled"] = bool;
                                            if(arr[index][child][index2][child][index3][child][index4][child] instanceof Array){
                                                arr[index][child][index2][child][index3][child][index4][child].map(function (val5,index5) {
                                                    arr[index][child][index2][child][index3][child][index4][child][index5]["disabled"] = bool;
                                                    if(arr[index][child][index2][child][index3][child][index4][child][index5][child] instanceof Array){
                                                        arr[index][child][index2][child][index3][child][index4][child][index5][child].map(function (val6,index6) {
                                                            arr[index][child][index2][child][index3][child][index4][child][index5][child][index6]["disabled"] = bool;
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
            return arr
        },
        handleEdit: function (index, row) {
            vm.title = '编辑';
            vm.isEdit = false;
            vm.tree2Disb = true;
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

            });
        },

        save: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {

                    var that = vm;

                    that.dialogFormVisible = false;
                    //ajax
                    var _data = {};
                    _data.menuList = [];
                    _data.orgList = [];
                    that.$refs.tree1.getCheckedNodes().map(function (info) {
                        _data.menuList.push(info.id)
                    });
                    that.$refs.tree2.getCheckedNodes().map(function (info) {
                        _data.orgList.push(info.id)
                    });
                    _data.id = vm.form.id;
                    _data.roleName = that.form.roleName;
                    _data.remark = that.form.remark;
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
                }else {
                    return false;
                }
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
        },
        toHome:function () {
            parent.location.reload()
        },
        indexMethod: function (index) {
            return index + 1 + (vm.inline.page-1) * vm.inline.limit;
        },
        closeDia: function () {
            vm.title = '新增';
            vm.$refs.tree1.setCheckedKeys([]);
            vm.$refs.tree2.setCheckedKeys([]);
            vm.dialogFormVisible = false;
        },
        beforeClose: function (){
            vm.dialogFormVisible = false;
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.reload();
            var that = this;
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/elTree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData1 = result.list;
                    that.treeData1.map(function (m) {
                        vm.tree1Arrs.push(m.id)
                    });
                    that.loadPower++;
                }
            });
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/elTree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData1D = result.list;
                    vm.treeData1D = vm.addOrremDisabled(vm.treeData1D,"children",true);
                    that.loadPower++;
                }
            });
            $.ajax({
                type: "GET",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData2 = result.orgList;
                    // 默认展开二级
                    that.treeData2.map(function (m) {
                        vm.tree2Arrs.push(m.id)
                    });
                    that.loadPower++;
                }
            });
            $.ajax({
                type: "GET",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function (result) {
                    that.treeData2D = result.orgList;
                    that.addOrremDisabled(result.orgList,"child",true);
                    that.loadPower++;
                }
            })
        })
    }

})