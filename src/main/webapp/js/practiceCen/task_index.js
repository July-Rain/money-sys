var vm = new Vue({
    el: "#app",
    data: {
        tableData: []
    },
    methods: {

    },
    created: function(){
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/task/list",
                data:{
                    pageNo: 1,
                    pageSize: 10
                },
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        alert(111);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }
});