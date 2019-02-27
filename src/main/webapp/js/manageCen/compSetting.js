var vm = new Vue({
    el: '#app',
    data: {},
    created:function () {

    },
    methods:{
        toHome: function () {
            parent.location.reload()
        },
        toUrl: function (url) {
            window.location.href = baseURL + "modules/competition/"+url+".html";
        }
    }
})