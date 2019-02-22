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
                var num;
                if(obj.answerNum != null && obj.answerNum != ''){
                    if(obj.answerNum == obj.total){
                        num = obj.answerNum - 1;
                    } else {
                        num = obj.answerNum;
                    }
                } else {
                    num = 0;
                }
                var parentWin = window.parent;
                parentWin.document.getElementById("container").src
                    = 'modules/exerciseCenter/theme_answer.html?id='+id
                    +'&indexs='+num;
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

                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/exerciseCenter/theme_answer.html?id='+id
                            +'&indexs='+0;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        reStart: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/restart/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        var nid = result.id;
                        var parentWin = window.parent;
                        parentWin.document.getElementById("container").src
                            = 'modules/exerciseCenter/theme_answer.html?id='+nid
                            +'&indexs='+0;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
           parent.location.reload();
        },
        review: function (id) {
            // 错题回顾
            // 跳转页面
            var parentWin = window.parent;
            parentWin.document.getElementById("container").src
                = 'modules/exerciseCenter/theme_answer.html?id='+id+'&isReview=1';
        },

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