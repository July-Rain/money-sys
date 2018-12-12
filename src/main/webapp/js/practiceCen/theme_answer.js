var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data:{
        dataList:[],

    },
    methods: {

    },
    created: function(){
        this.$nextTick(function () {
            var id = params.substring(4);

            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/questions",
                data: JSON.stringify({
                    id: id
                }),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dataList = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});