var vm = new Vue({
    el: '#app',
    data:{
        tableData: []
    },
    methods: {
        start: function(index){
            var obj = vm.tableData[index];

            var id = obj.id;
            if(id == null || id == ''){
                vm.found(obj);
            } else {
                vm.jump(id);
            }

        },
        found: function (obj) {
            // 创建任务
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/save",
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        var id = result.id;
                        vm.jump(id);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        jump: function (id) {
            // 跳转页面
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/theme_answer.html?id='+id;
        },
        reStart: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/restart/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        var nid = result.id;
                        vm.jump(nid);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    created: function(){
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/theme/list",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});