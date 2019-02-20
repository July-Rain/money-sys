var vm = new Vue({
    el: '#app',
    data:{

    },
    methods: {
        //获取url路径中参数
        toHome: function () {
            parent.location.reload()
        }
    },
    created: function(){
    }

});