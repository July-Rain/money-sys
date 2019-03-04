var vm = new Vue({
    el: '#app',
    data: {

        //存放 查出来的 大关信息集合
        BigGuanList: [],
        levelList: [],
        position: [{left: 0, top: 102}, {left: 471, top: 0}, {left: 942, top: 102}, {left: 1342, top: 102}],
        moveClass: false,
        coinNum: ""
    },
    created: function () {
        this.$nextTick(function () {


            vm.coinNum = getUrlParam('coinNum');


            var storage = window.sessionStorage;
            if (getUrlParam('index') == '1') {

                $.ajax({
                    type: "POST",
                    url: baseURL + 'recruitConfiguration/findAll2',
                    dataType: "json",
                    async: false,
                    // data:{"id": row.id},
                    success: function (result) {
                        if (result.code === 0) {
                            //第一次进来 我给你的 一些数据
                            vm.BigGuanList = result.data;
                            vm.getLevel(Number(getUrlParam('index')), result.data.length);
                            storage.setItem("BigList", JSON.stringify(result.data));

                        } else {
                            alert(result.msg);
                        }
                    }
                });

                $.ajax({
                    type: "POST",
                    url: baseURL + 'recruitConfiguration/getQuest',
                    contentType: "application/json",
                    async: false,
                    data: JSON.stringify(vm.BigGuanList[0]),
                    success: function (result) {

                        if (result.code === 0) {
                            //将查到的所有题目交给了题目数组

                            storage.setItem("questionList", JSON.stringify(result.data));
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            } else {
                //不是第一关说明过关，我们都动一下
                vm.moveClass = true;

                var obj = storage.getItem("BigList");


                var BigList = eval('(' + obj + ')');
                vm.getLevel(Number(getUrlParam('index')), BigList.length);
                // 开启新的一轮去查题目，并从新给4个属性赋值
                //还要提示积分什么的。
                $.ajax({
                    type: "POST",
                    url: baseURL + 'recruitConfiguration/getQuest',
                    contentType: "application/json",
                    async: false,
                    data: JSON.stringify(BigList[(Number(getUrlParam('index')) - Number(1))]),
                    success: function (result) {

                        if (result.code === 0) {

                            storage.setItem("questionList2", JSON.stringify(result.data));

                        } else {
                            alert(result.msg);
                        }
                    }
                });

            }

        })
    },

    methods: {
        // index 为当前面对关数
        getLevel: function (index, totalNum) {
            console.log('这是第' + index + '关')
            this.levelList = [];
            for (var i = 0; i < 4; i++) {

                this.levelList.push({
                    indexName: index + i - 1,
                    hide: index + i - 1 > totalNum
                })

            }
            console.info(this.levelList)

            console.info("1", this.levelList)
        },
        jumpGame: function () {

            window.location.href = baseURL + "modules/competition/chuangguanGame.html?index=" + getUrlParam('index') + "&coinNum=" + vm.coinNum;

        },
        backMain: function () {
            window.location.href = baseURL + 'modules/competition/competeCenter.html'
        }
    },
    filters: {
        capitalize: function (value) {
            return ArabiSimplified(value)
        }
    }

});