
var vm = new Vue({
    el: '#app',
    data: {
        formInline: {

        },
        tableData: [],
        title: '新增',
        dialogConfig: false,
        topic: {},

    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "topic/list",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    },
    methods: {
        addTopic: function(){
            this.dialogConfig = true;
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        save: function(formName){
            var url = vm.topic.id ? "topic/update" : "topic/save";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.topic),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.dialogConfig = false;
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        deletes: function(id){

            $.ajax({
                type: "POST",
                url: baseURL + "/topic/delete",
                contentType: "application/json",
                data: id,
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "topic/list",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
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
