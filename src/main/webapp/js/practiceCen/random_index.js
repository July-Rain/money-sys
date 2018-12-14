var vm = new Vue({
    el: '#app',
    data:{
        tableData: [],
        dialogConfig: false,
        title: '',
        exercise: {

        },
        diffList: [],
        typeList: [],
        qtList: [],
        topicList: [],

    },
    methods: {
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        start: function(){
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/random/start",
                contentType: "application/json",
                data: JSON.stringify(vm.exercise),
                success: function (result) {
                    if (result.code === 0) {
                        var id = result.id;
                        window.location.href = baseURL + "exercise/random/answer?id=" + id;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        addExercise: function(){
            vm.title = '随机练习设置';
            this.dialogConfig = true;
        },
        reload: function () {

        }
    },
    created: function(){
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/list",
                contentType: "application/json",
                data:{
                    pageNo: 1,
                    pageSize: 10
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.diffList = result.diffList;
                        vm.typeList = result.typeList;
                        vm.qtList = result.qtList;
                        vm.topicList = result.topicList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});