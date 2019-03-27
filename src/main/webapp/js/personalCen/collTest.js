var vm = new Vue({
    el: '#app',
    data: {
        choice:["A","B","C","D","E","F","G","H","I"],
        num: 0,
        formInline: { // 搜索表单
            specialKnowledgeId: '',
            questionDifficulty: '',
            questionType: "",
            page: 1,
            limit: 10,
            count: 0,
            type: 0
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
            pname: "",
            num: 0,
            sourceFrom: 3
        },
        taskName: '',
        taskId: '',
        genTaskByQue: false,
        optionIndex: ['A', 'B', 'C', 'D', 'E', 'F']
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();

            $.ajax({
                type: "GET",
                url: baseURL + "common/dicts",
                contentType: "application/json",
                data: {
                    codes: 'QUESTION_DIFF,QUESTION_TYPE'
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.qds = result.results.QUESTION_DIFF;
                        vm.qts = result.results.QUESTION_TYPE;
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
            vm.formInline.page = 1;
            vm.reload();
        },
        onCom:function(){
            if(vm.num == 0){
                vm.$message({
                    message: '尚无【我的错题】',
                    type: 'warning',
                    duration: 1000
                });
                return false;

            } else if (vm.params.pname=='') {
                vm.$message({
                    message: '请输入正确的卷名',
                    type: 'warning',
                    duration: 1000
                });
                return false;
            } else if (vm.params.num > vm.num){
                vm.$message({
                    message: '出题数量请勿超过错题数量【' + vm.num + '】',
                    type: 'warning',
                    duration: 1000
                });
                return false;
            } else if (vm.params.num<=0|| vm.params.num % 1 != 0){
                vm.$message({
                    message: '请输入正确出题数量',
                    type: 'warning',
                    duration: 1000
                });
                return false;
            }

            var obj = {
                key: vm.params.pname,
                value: vm.params.num,
                opinion: vm.params.sourceFrom
            }

            $.ajax({
                type: "POST",
                url: baseURL + "collection/createPaper",
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.taskName = result.name;
                        vm.taskId = result.id;
                        vm.genTaskByQue = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        closeGenDia : function(){
            vm.genTaskByQue = false;
        },
        jumpTask : function(){
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_paper.html?id=' + vm.taskId;
        },
        handleInfo: function (qid) {
            this.title = "试题详情";
            this.testInfo = true;
            $.ajax({
                type: "GET",
                url: baseURL + 'collection/show',
                contentType: "application/json",
                data:{
                    qid: qid,
                    type: 0
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.questObj = result.form;
                        console.info(vm.questObj)
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
            });
        },
        handleDel: function (index, row) {
            $.ajax({
                type: "POST",
                url: baseURL + "collection/delete/"+id,
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.reload();
                    }else{
                        alert(result.msg);
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                    }
                }
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
                type: "GET",
                url: baseURL + "collection/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                        vm.num = result.num;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome:function () {
            parent.location.reload()
        }
    }
});

