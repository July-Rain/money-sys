/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */


var vm = new Vue({
    el: '#app',
    data: {
        bigcheckNum: [],//大关数量数据
        tableData: [],//表格数据
        daguanArray: [],
        daguannum: '',
        formInline: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        rules1: [
            {required: true, message: '请输入参数名11', trigger: 'blur'},
            {pattern: regularExp("number"), message: '正则不匹配55', trigger: 'blur'}
        ],
        rules: {//表单验证规则
            // smallNum: [
            //     // {required: true, message: '请输入参数名11', trigger: 'blur'},
            //     { required: true,pattern: regularExp("number"), message: '正则不匹配55', trigger: 'blur'}
            // ],
            // markReward: [
            //     {required: true, message: '不能为空', trigger: 'blur'},
            //     { pattern: regularExp("number"), message: '正则不匹配' }
            // ],
            // xiala: [
            //     {required: true, message: '不能为空', trigger: 'blur'}
            // ],
            // itemDifficulty: [
            //     {required: true, message: '不能为空', trigger: 'blur'}
            // ]
        },
        dialogConfig: false,//table弹出框可见性
        dialog2: false,//查看小关详情弹出框
        title: "",//弹窗的名称

        //小关集合
        xiaoguanList: [],
//专项知识
        zhuanxiangzhishiList: [],
//试题类型
        itemtype: [],
//试题难度
        itemjibie: [],
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {
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
                    //保存前默认先删除一波
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'recruitConfiguration/delete',

                        async: false,
                        dataType: "json",
                        success: function (result) {
                            var url = "recruitConfiguration/save";
                            $.ajax({
                                type: "POST",
                                url: baseURL + url,
                                contentType: "application/json",
                                async: false,
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
                        }
                    });
                    console.log(vm.ruleForm)//多的表单
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });


        },
        add: function () {

            $.ajax({
                type: "POST",
                url: baseURL + 'recruitConfiguration/findAll',
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.data.length != "0") {

                        vm.$alert('请先删除原有配置,在添加新的配置');
                        vm.dialogConfig = false;
                    } else {
                        vm.title = "新增闯关配置";
                        vm.dialogConfig = true;
                    }

                }
            });

            vm.daguanArray = [];
            vm.bigcheckNum = [];
            //每次打开添加按钮时候 取后台获取 字典表中大关和小关数量的配置
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "BIGCHECKNUM", Parentcode: "99997"},
                success: function (result) {
                    if (result.code == 0) {
                        //区间也就2个值 也排序过了
                        var bigchecknum1 = result.dictlist[0].value;
                        var bigchecknum2 = result.dictlist[1].value;
                        for (var i = bigchecknum1; i <= bigchecknum2; i++) {
                            vm.bigcheckNum.push({
                                value: i,
                                label: i + '大关'
                            })
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });

            //专项知识
            $.ajax({
                type: "POST",
                url: baseURL + "recruitConfiguration/findAllTopic",
                dataType: "json",
                async: false,
                success: function (result) {

                    vm.zhuanxiangzhishiList = result.data;
                }
            });

            // 试题类型
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "QUESTION_TYPE", Parentcode: "0"},
                success: function (result) {


                    // vm.itemtype=result.dictlist;
                    for (var tt = 0; tt < result.dictlist.length; tt++) {
                        if ((result.dictlist[tt].value == "单选题") || (result.dictlist[tt].value == "判断题") || (result.dictlist[tt].value == "多选题")) {
                            vm.itemtype.push(result.dictlist[tt]);
                        }
                    }
                }
            });

            //试题难度
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "QUESTION_DIFF", Parentcode: "0"},
                success: function (result) {
                    vm.itemjibie = result.dictlist;
                }
            });


        },
        onselect: function (num) {//点完选择大关触发事件
            // alert(vId);
            vm.daguanArray = [];
            for (var k = 0; k < num; k++) {
                vm.daguanArray.push(
                    {
                        id: '',
                        markNumOrder: k + 1,
                        smallNum: '',
                        rewardScore: '0',
                        recruitCheckpointConfigurationList: [{id: '', unifyConfiguration: '1'}]
                    }
                )
            }
        },
        onselectunifyConfiguration: function (num) {//点完是否统一配置触发事件

            // var xiaoguannum=   Number(num.markNumOrder);

            var unifyConfiguration = num.unifyConfiguration
            var smallNum = Number(num.smallNum);
            if (!smallNum) {
                vm.$message({
                    type: 'info',
                    message: '请先设置小关数量!'
                });
                return;
            }
            //不管选的是是还是否  都先清除一遍  在加
            num.recruitCheckpointConfigurationList = [];
            if (unifyConfiguration == "0")//0不是统一配置
            {
                for (var p = 0; p < smallNum; p++) {
                    num.recruitCheckpointConfigurationList.push
                    (
                        {id: '', howManySmall: p + 1, unifyConfiguration: '0'}
                    )
                }

            } else if (unifyConfiguration == "1")//1是  是统一
            {
                num.recruitCheckpointConfigurationList.push
                (
                    {id: '', unifyConfiguration: '1'}
                )
            }
            console.info(num);
        },
        look: function (index, row) {
            vm.title = "查看关卡配置";
            vm.xiaoguanList = [];//每次打开前 都删一边
            $.ajax({
                type: "POST",
                url: baseURL + 'recruitConfiguration/getSonList',
                dataType: "json",
                async: false,
                data: {"id": row.id},
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
        del: function () {

            this.$confirm('此操作将永久删除竞赛配置, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'recruitConfiguration/delete',
                    async: false,
                    dataType: "json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        update: function () {
            vm.daguanArray = [];
            vm.bigcheckNum = [];
            //每次打开添加按钮时候 取后台获取 字典表中大关和小关数量的配置
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "BIGCHECKNUM", Parentcode: "99997"},
                success: function (result) {
                    if (result.code == 0) {
                        //区间也就2个值 也排序过了
                        var bigchecknum1 = result.dictlist[0].value;
                        var bigchecknum2 = result.dictlist[1].value;
                        for (var i = bigchecknum1; i <= bigchecknum2; i++) {
                            vm.bigcheckNum.push({
                                value: i,
                                label: i + '大关'
                            })
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
            //专项知识
            $.ajax({
                type: "POST",
                url: baseURL + "recruitConfiguration/findAllTopic",
                dataType: "json",
                async: false,
                success: function (result) {

                    vm.zhuanxiangzhishiList = result.data;
                }
            });

            // 试题类型
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "QUESTION_TYPE", Parentcode: "0"},
                success: function (result) {


                    // vm.itemtype=result.dictlist;
                    for (var tt = 0; tt < result.dictlist.length; tt++) {
                        if ((result.dictlist[tt].value == "单选题") || (result.dictlist[tt].value == "判断题") || (result.dictlist[tt].value == "多选题")) {
                            vm.itemtype.push(result.dictlist[tt]);
                        }
                    }
                }
            });

            //试题难度
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "QUESTION_DIFF", Parentcode: "0"},
                success: function (result) {
                    vm.itemjibie = result.dictlist;
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + 'recruitConfiguration/findAll',
                dataType: "json",
                async: false,
                success: function (result) {
                    console.info(result);
                    console.info(result.data.length);
                    if (result.code === 0) {
                        if (result.data.length == "0") {
                            vm.$alert('暂无数据');

                        } else {
                            vm.title = "编辑";
                            vm.dialogConfig = true;
                            vm.daguanArray = result.data;
                            vm.daguannum = result.data.length;
                        }

                    } else {
                        alert(result.msg);
                    }
                }
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
                async: false,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        },
        toMain: function () {
            window.location.href = baseURL + 'modules/manageCen/compSetting.html'
        }
    }
});
