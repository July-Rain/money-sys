var vm = new Vue({
    el: '#app',
    data: {
        choice:["A","B","C","D","E","F","G","H","I"],
        formInline: { // 搜索表单
            specialKnowledgeId: '',
            questionDifficulty: '',
            questionType: "",
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        sks:[],//所属专项
        qds:[],//难度
        qts:[],//试题类型
        tableData: [],//表格数据
        visible: false,
        testInfo: false,//table弹出框可见性
        random:false,
        title: "",//弹窗的名称
        title1: "",//弹窗的名称
        questObj: {
            id: "",
            comContent:"",
            answerValue:"",
            answerId:""
        },//试题
        answers:[],
        exercise:[],
        params:{
            pname:"",
            num:"",
            sourceFrom:"2"
        },
        taskName:'',
        taskId:'',
        genTaskByQue:false
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();

            //加载选项数据
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "QUESTION_DIFF", Parentcode: "0"},
                success: function (result) {
                    if (result.code === 0) {
                        vm.qds = result.dictlist;
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });

            $.ajax({
                type: "GET",
                url: baseURL + "exam/config/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sks = result.skOption;
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "QUESTION_TYPE", Parentcode: "0"},
                success: function (result) {
                    if (result.code === 0) {
                        vm.qts = result.dictlist;
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });

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
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        onCom:function(){
            if (vm.params.num >vm.formInline.totalCount){
                vm.$message({
                    message: "出题数量不能大于收藏试题总数量",
                    type: 'warning'
                });
                return false;
            }else if (vm.params.num<0|| vm.params.num % 1 != 0){
                vm.$message({
                    message: "出题数量必须为正整数",
                    type: 'warning'
                });
                return false;
            }
            $.ajax({
                type: "POST",
                url: baseURL + 'coll/randomQuestColl',
                dataType: "json",
                async:false,
                data: vm.params,
                success: function (result) {
                    if (result.code === 0) {
                        vm.taskName = result.name;
                        vm.taskId = result.id;
                        vm.genTaskByQue = true;
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });
        },
        closeGenDia : function(){
            vm.genTaskByQue = false;
        },
        jumpTask : function(){
            vm.$message({
                message: vm.taskId,
                type: 'success'
            });
        },
        handleInfo: function (index, row) {
            this.title = "试题详情";
            this.testInfo = true;
            vm.questObj=row;
            $.ajax({
                type: "POST",
                url: baseURL + 'coll/getTestQuestion',
                contentType: "application/json",
                async:false,
                data: JSON.stringify(vm.questObj),
                success: function (result) {
                    if (result.code === 0) {
                        vm.answers=result.answer;
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });
            console.log(vm.answers)
        },
        handleDel: function (index, row) {
            questObj=row;
            vm.questObj.id=row.collectionId;
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'coll/delColl',
                    async: true,
                    data: JSON.stringify(vm.questObj),
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
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        closeDia: function () {
            this.testInfo = false;
            vm.reload();
        },
        closeDia1: function () {
            this.random = false;
            //vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "coll/testQuestion",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.result.list;
                        vm.formInline.currPage = result.result.currPage;
                        vm.formInline.pageSize = result.result.pageSize;
                        vm.formInline.totalCount = parseInt(result.result.totalCount);
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });
        },
        toHome:function () {
            parent.location.reload()
        }
    }
});

