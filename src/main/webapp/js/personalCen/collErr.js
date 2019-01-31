/**
 * Author: zhujunwen
 * Date: 2018/12/14
 * Description:重点试题
 */

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
            num:""
        }
    },
    created: function () {
        this.$nextTick(function () {
            //加载选项数据
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getbyType/QUESTION_DIFF",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.qds = result.forms;
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "dict/getbyType/SPECIALKNOWLEDGE",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sks = result.forms;
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "dict/getbyType/QUESTION_TYPE",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.qts = result.forms;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.reload();
        })
       // this.answers.set("1","B")
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
            $.ajax({
                type: "POST",
                url: baseURL + 'coll/randomErrorColl',
                dataType: "json",
                async:false,
                data: vm.params,
                success: function (result) {
                    if (result.code === 0) {
                        vm.random = true;
                        vm.exercise=result.data;
                        console.log(vm.exercise)
                    } else {
                        alert(result.msg);
                    }
                }
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
                        questObj=result.qustion;
                        vm.answers=result.answer;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            console.log(vm.answers)
        },
        closeDia: function () {
            this.testInfo = false;
            vm.reload();
        },
        closeDia1: function () {
            this.random = false;
            //vm.reload();
        },
        toHome: function () {
            parent.location.reload()
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "coll/errorTestQuestion",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.result.list;
                        vm.formInline.currPage = result.result.currPage;
                        vm.formInline.pageSize = result.result.pageSize;
                        vm.formInline.totalCount = parseInt(result.result.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});

