var vm = new Vue({
    el: "#app",
    data: {
        tableData: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0
        },
    },
    methods: {
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/paper/list",
                data: vm.formInline,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        start: function (id, taskId) {
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_paper.html?id=' + id + '&taskId=' + taskId;
        },
        goon: function (id, taskId) {
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_paper.html?id=' + id + '&taskId=' + taskId;
        },
        review: function(id, taskId){
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/paper_paper.html?id=' + id + '&taskId=' + taskId + '&review=review';
        },
        toHome: function () {
            parent.location.reload()
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.refresh();
        })
    }
});