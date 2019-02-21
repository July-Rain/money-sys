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
        warAreaShow:false,
        formationWarShow:false,//建立战区
        joinWarShow:false//加入战区
    },
    created: function () {

    },
    methods: {
        toUrl:function (url) {
            // 匹配到对手的昵称
            var resultName = "卜楠";

            switch (url) {
                case 'pkGame':
                    vm.toUrlCallBack(url);
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
            //coding 跳转组队页面
            window.location.href = baseURL + "modules/competition/teamGame.html?teamType=formation";
        },
        joinTeam: function () {
            console.log("加入"+ vm.teamPerNum +"人组队");
            this.teamAreaShow = false;
            //coding 跳转组队页面
            window.location.href = baseURL + "modules/competition/teamGame.html?teamType=join";
        },

        formationWar: function () {
            // 建立战区
            this.warAreaShow = false;
            this.formationWarShow = true;
        },
        joinWar: function () {
            // 加入战区
            this.warAreaShow = false;
            this.joinWarShow = true
        },
        joinFormationWar: function () {
            // 加入战区到建立
            this.formationWarShow = true;
            this.joinWarShow = false
        }
    }
});