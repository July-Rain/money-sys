/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */


var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航

        bigcheckNum:[],//大关数量数据

        formInline: { // 搜索表单
            value: '',
            name: '',
            status: "",
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        tableData: [],//表格数据
        visible: false,
        daguannum:'',
        sysConfig: {
            id: '',
            code: '',
            value: '',
            remark: '',
            status: "1"
        },
        daguanArray: [
        ],
        rules: {//表单验证规则
            value: [
                {required: true, message: '请输入参数名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入参数值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        dialog2:false,//查看小关详情弹出框
        title: "",//弹窗的名称
        delIdArr: [],//删除数据

        xiaoguanList:[],
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            this.reload();
        })
    },
    methods: {
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
            console.info(vm.daguanArray);
            // this.$refs[formName].validate(function (valid) {
            //     if (valid) {
                    var url ="recruitConfiguration/save";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.daguanArray),
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
                // } else {
                //     console.log('error submit!!');
                //     return false;
                // }
            // });
        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        add: function () {
            vm.daguanArray=[];
            vm.bigcheckNum=[];
            //每次打开添加按钮时候 取后台获取 字典表中大关和小关数量的配置
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                data: {type:"BIGCHECKNUM",Parentcode:"99997"},
                success: function (result) {
                    if (result.code == 0) {
                        //区间也就2个值 也排序过了
                        var bigchecknum1 =  result.dictlist[0].value;
                        var bigchecknum2 =  result.dictlist[1].value;
                        for(var i=bigchecknum1;i<=bigchecknum2;i++)
                        {
                           vm.bigcheckNum.push({
                               value: i,
                               label: i+'大关'
                           })
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });

            this.title = "新增闯关配置";
            this.dialogConfig = true;

        },
        onselect:function (num) {//点完选择大关触发事件
            // alert(vId);
            vm.daguanArray=[];
            for(var k=0;k<num;k++)
            {
                vm.daguanArray.push(
                    {
                        id:'',
                        markNumOrder:k+1,
                        smallNum:'',
                        recruitCheckpointConfigurationList:[{id:'',}]
                    }
                )
            }
        },
        look: function (index, row) {
            vm.title = "查看关卡配置";
            vm.xiaoguanList =[];//每次打开前 都删一边
            $.ajax({
                type: "POST",
                url: baseURL + 'recruitConfiguration/getSonList',
                dataType: "json",
                data:{"id": row.id},
                success: function (result) {

                    console.info(result)
                    if (result.code === 0) {
                        // 返回的是一个集合   不想做成在翻页   直接做成循环table
                        vm.xiaoguanList = result.data;

                        vm.dialog2 = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        del: function (index, row) {
            vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'sysconfig/delete',
                    async: true,
                    data: JSON.stringify(vm.delIdArr),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
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
        closedialog2: function () {
            vm.dialog2 = false;
            // vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "recruitConfiguration/list",
                dataType: "json",
                // data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        console.info(result)
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
