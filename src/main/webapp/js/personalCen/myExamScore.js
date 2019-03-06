var vm = new Vue({
    el:'#app',
    data:{
        formInline: { // 搜索表单
            examName:"",
            limit: 10,
            page: 1,
            count: 0,
            status:"2", //考试状态已完成
            source:'0'  //成绩报告
        },
        tableData: [],//表格数据
        visible: false,
        examConfig: {
            id: '',
            examName:'',
            code: '',
            value: '',
            remark: '',
            status: "1"
        }
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods:{
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            this.reload();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            this.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/exam/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page - 1) * vm.formInline.limit;
        },
        viewExam : function (index,row) {
            var parentWin = window.parent;
            var id = row.id;
            var examStatus = row.examStatus;
            var userExamId = row.userExamId==null?'':row.userExamId;
            parentWin.document.getElementById("container").src
                = 'modules/personalCen/myScoreReport.html?id='+id+'&examStatus='+examStatus+'&userExamId='+userExamId;
        },
        toHome: function () {
            parent.location.reload()
        }
    }
});