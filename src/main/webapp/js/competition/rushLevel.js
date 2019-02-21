var vm = new Vue({
    el: '#app',
    data: {

        //存放 查出来的 大关信息集合
        BigGuanList: [],
        levelList: [],
        position: [{left: 0, top: 102}, {left: 471, top: 0}, {left: 942, top: 102},{left: 1342, top: 102}],
        moveClass: false,
        coinNum: ""
    },
    created: function () {
        this.$nextTick(function () {


                vm.coinNum = getUrlParam('coinNum');
                vm.getLevel(Number(getUrlParam('index')));

                var storage=window.sessionStorage;
                if(getUrlParam('index')=='1')
                {

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
                                storage.setItem("BigList",JSON.stringify(result.data));

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

                                storage.setItem("questionList",JSON.stringify(result.data));
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
                else
                {

                    var obj = storage.getItem("BigList");
                    var BigList = eval('(' + obj + ')');
                    // 开启新的一轮去查题目，并从新给4个属性赋值
                    //还要提示积分什么的。
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'recruitConfiguration/getQuest',
                        contentType: "application/json",
                        async: false,
                        data: JSON.stringify(BigList[(Number(getUrlParam('index'))-Number(1))]),
                        success: function (result) {

                            if (result.code === 0) {

                                storage.setItem("questionList2",JSON.stringify(result.data));

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
        getLevel: function (index) {
            this.levelList = [];
            for (var i = 0; i < 4; i++) {
                this.levelList.push({
                    indexName: index + i - 1
                })
            }
            console.info("1", this.levelList)
        },
        rushAnimation: function(_index) {
            // if(index!=1){
            //     return
            // }
            this.moveClass = true;
            setTimeout(function () {
                window.location.href =baseURL+"modules/competition/chuangguanGame.html?index="+getUrlParam('index')+"&coinNum="+vm.coinNum;
            },1000)

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