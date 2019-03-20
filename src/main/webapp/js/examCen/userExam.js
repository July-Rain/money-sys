var vm = new Vue({
    el:'#app',
    data:{
        formInline: { // 搜索表单
            examName:"",
            status: "",
            limit: 10,
            page: 1,
            count: 0
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
            vm.reload();
        })
    },
    methods:{
        verifyTime :function(startTime,endTime){
            var flag = new Date(Date.parse(startTime.replace(/-/g,  "/"))).getTime() <= new Date().getTime()&&new Date(Date.parse(endTime.replace(/-/g,  "/"))).getTime() >= new Date().getTime();
            return flag;
        },
        verifyStartTime :function(startTime){
            var flag = new Date(Date.parse(startTime.replace(/-/g,  "/"))).getTime() >= new Date().getTime();
            return flag;
        },
        verifyEndTime :function(endTime){
            var flag = new Date(Date.parse(endTime.replace(/-/g,  "/"))).getTime() <= new Date().getTime();
            return flag;
        },
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page - 1) * vm.formInline.limit;
        },
        onSubmit: function () {
            this.reload();
        },
        viewExam : function (index,row) {
            var parentWin = window.parent;
            var id = row.id;
            var examStatus = row.examStatus;
            var userExamId = row.userExamId==null?'':row.userExamId;
            parentWin.document.getElementById("container").src
                = 'modules/personalCen/myScoreReport.html?id='+id+'&examStatus='+examStatus+'&userExamId='+userExamId;
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            this.reload();
        },
        resetForm: function (formName) {
            // this.$refs[formName].resetFields();
            vm.formInline= { // 搜索表单

                    limit: 10,
                    page: 1,
                    count: 0
            };
            this.reload();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            this.reload();
        },
        reload: function () {
            console.info(vm.formInline);
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
        startExam : function (index,row) {
            var parentWin = window.parent;
            var id = row.id;
            var examStatus = row.examStatus;
            var userExamId = row.userExamId==null?'':row.userExamId;
            parentWin.document.getElementById("container").src
                = 'modules/examCen/testPaper.html?id='+id+'&examStatus='+examStatus+'&userExamId='+userExamId;
        },
        toHome: function () {
            parent.location.reload()
        }
    },

});