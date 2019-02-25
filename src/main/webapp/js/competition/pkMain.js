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
        joinTeamShow:false,//加入队伍弹框！！！！
        alonePkGameNum: ''
    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/winLeiTaiCountByUserId?winId="+jsgetUser().id,
                dataType: "json",
                async: false,
                success: function (result) {
                    vm.coinNum=result.jifen;
                }
            });
        })
        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==13){ // enter 键
                if(vm.joinTeamShow){
                    vm.joinTeamShow = false;
                    window.location.href = baseURL + "modules/competition/alonePkGameByCode.html?warType=join&type=join&code=" + vm.alonePkGameNum;
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
            window.location.href = baseURL + "modules/competition/alonePkGameByCode.html?warType=formation&type=add&code=0";
        },
        joinWar: function () {
            // 加入战区
            this.warAreaShow = false;

            this.joinTeamShow=true;
            // window.location.href = baseURL + "modules/competition/alonePkGameByCode.html?warType=join&type=join";
        },

    }
});