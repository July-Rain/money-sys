var vm = new Vue({
    el: '#app',
    data: {
        coinNum: 123
    },
    created: function () {
    },
    methods: {
        toUrl:function (url) {
            window.location.href = baseURL+"modules/competition/" + url + ".html"
        }
    }
});