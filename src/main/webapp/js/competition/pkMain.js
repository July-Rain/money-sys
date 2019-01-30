var vm = new Vue({
    el: '#app',
    data: {
        coinNum: 123,
        rollAreaShow: false,
        nameList: ["乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇"],
        rollStop: false
    },
    created: function () {
    },
    methods: {
        toUrl:function (url) {

            var resultName = "旗鼓相当的对手";

            switch (url) {
                case 'pkGame':
                    vm.rollAreaShow = true;
                    // $ajax
                    setTimeout(function () {
                        vm.nameList = ["乔杰","李坤宇",resultName,"李坤宇","乔杰","李坤宇","乔杰","李坤宇","旗鼓相当的对手","李坤宇","乔杰","李坤宇"];
                        vm.rollStop = true;
                        setTimeout(function () {
                            vm.toUrlCallBack(url);
                        },2000)
                    },5000);
                    break;
            }

        },
        toUrlCallBack: function (url) {
            window.location.href = baseURL+"modules/competition/" + url + ".html"
        }
    }
});