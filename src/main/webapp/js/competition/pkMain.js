var vm = new Vue({
    el: '#app',
    data: {
        coinNum: 123,
        rollAreaShow: false,
        nameList: ["乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇","乔杰","李坤宇"],
        rollStop: false,
        ruleAreaShow: false,
        teamAreaShow: false,
        teamPerNum: 3,
        formationTeamShow: false,
        joinTeamShow:false
    },
    created: function () {
        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==13){ // enter 键
                if(vm.joinTeamShow){
                    vm.joinTeamShow = false;
                    vm.formationTeamShow = true;
                }

            }
        }
    },
    methods: {
        toUrl:function (url) {
            // 匹配到对手的昵称
            var resultName = "卜楠";

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
            window.location.href = baseURL + "modules/competition/" + url + ".html"
        },
        goBack: function () {
            window.location.href = baseURL + "modules/competition/competeCenter.html"
        },
        formationTeam: function () {
            console.log("组建"+ vm.teamPerNum +"人组队");
            this.teamAreaShow = false;
            this.formationTeamShow = true;
        },
        joinTeam: function () {
            console.log("加入"+ vm.teamPerNum +"人组队");
            this.teamAreaShow = false;
            this.joinTeamShow = true
        },
        formationTeamExit: function () {
            this.formationTeamShow = false;
            // 退出组队
        }
    }
});