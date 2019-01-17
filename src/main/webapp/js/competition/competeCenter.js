/**
 * Author: qj
 * Date: 2018/12/28
 * Description:
 */
var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        navData: [],//导航
        rankTop5: 'first',
        firstListByChaungGuan: [],
        firstListByPk: [],
        firstListByleitai: [],
    },
    created: function () {
        this.$nextTick(function () {
            //获取闯关排名
            $.ajax({
                type: "POST",
                url: baseURL + "competitionRecord/chuangGuanRanking",//闯关排名
                Type: "json",
                async:false,
                success: function (data) {
                   vm.firstListByChaungGuan=data.competitionRecordList.slice(0,5);
                }
            });
            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/firstListByPk",//闯关排名
                Type: "json",
                async:false,
                success: function (data) {
                    vm.firstListByPk=data.firstListByPk.slice(0,5);
                    console.info(vm.firstListByPk);
                }
            });
            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/firstListByleitai",//闯关排名
                Type: "json",
                async:false,
                success: function (data) {
                    vm.firstListByleitai=data.firstListByleitai.slice(0,5);

                    console.info(vm.firstListByleitai);
                }
            });


        })

    },
    methods: {
        handleClick : function(tab, event){
            console.log(tab, event);
        },



    }
});
