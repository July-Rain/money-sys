var vm = new Vue({
    el: '#app',
    data:{
        navData : [
            {
                icon: 'el-icon-tickets',
                name: ' 主题练习'
            }, {
                icon: 'el-icon-tickets',
                name: ' 随机练习'
            }
        ],
        tableData: []
    },
    methods: {
        start: function(id, status, typeId, typeName){
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/theme/start",
                dataType: "json",
                data: {
                    id: id,
                    status: status,
                    typeId: typeId,
                    typeName: typeName
                },
                success: function (result) {
                    if (result.code == 0) {
                        var vid = result.id;
                        window.location.href = baseURL + "exercise/theme/answer?id=" + vid;
                    }
                }
            });
        },
        continues: function(id, typeId) {
            window.location.href = baseURL + "exercise/theme/answer?id=" + id;
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