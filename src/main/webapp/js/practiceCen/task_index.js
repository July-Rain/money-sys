var menuId = getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],
        formInline: {
            taskContent: '',
            name: '',
            limit: 10,
            page: 1,
            count: 0,
            jssj:"",
            kssj:"",
            breadArr:[]
        },
    },
    methods: {
        getRate: function (row, column) {
            var msg = '';

            var answerNum = row.answerNum == null? 0 : row.answerNum;
            if(answerNum == 0){
                return msg;

            }else {
                var rightNum = row.rightNum==null? 0 : row.rightNum;
                msg = rightNum/answerNum;
                msg = (msg * 100).toFixed(2);
                return msg + "%";
            }
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        refresh: function () {
            console.info(vm.formInline)
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/task/list",
                data: vm.formInline,
                contentType: "application/json",
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
        start: function (obj) {
            var answerNum = obj.answerNum == null? 0 : obj.answerNum;
            var total = obj.total;
            var indexs = 0;

            if(answerNum == total){
                indexs = answerNum - 1;
            } else {
                indexs = answerNum;
            }

            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/task_paper.html?id='+obj.id+'&taskId='
                +obj.taskId+'&indexs='+indexs+'&total='+total;
        },
        toHome: function () {
            parent.location.reload();
        },
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page-1) * vm.formInline.limit;
        },
        review: function (obj) {
            // 错题回顾
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/task_paper.html?id='+obj.id+'&taskId='
                +obj.taskId+'&indexs='+0+'&total='+(Number(obj.answerNum) - Number(obj.rightNum))+'&isReview=1';
        },
        // 查询
        onSubmit: function () {
            this.refresh();
        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
            this.refresh();
        },
    },
    created: function () {
        this.$nextTick(function () {
            this.breadArr=getBreadcrumb(menuId);
            vm.refresh();
        })
    }
});