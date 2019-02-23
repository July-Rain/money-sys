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
        },
        rules: {//表单验证规则
            value: [
                {required: true, message: '请输入参数名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入参数值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
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
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
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
        // formatterEnd: function (row, column) {
        //     var _time = new Date(row.endTime);
        //     return _time.getFullYear() + "/" + (_time.getMonth() + 1) + "/" + _time.getDate() + " " + _time.getHours() + ":" + _time.getMinutes() + ":" + _time.getSeconds();
        // },
        // formatterStart: function (row, column) {
        //     var _time = new Date(row.startTime);
        //     return _time.getFullYear() + "/" + (_time.getMonth() + 1) + "/" + _time.getDate() + " " + _time.getHours() + ":" + _time.getMinutes() + ":" + _time.getSeconds();
        // },
        toHome: function () {
            parent.location.reload()
        }
    }
});