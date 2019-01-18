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

            var _html = document.getElementById('qiaojie').innerHTML;
            vm.getTempName(_html);
        })

    },
    methods: {
        handleClick : function(tab, event){
            console.log(tab, event);
        },
        getTempName: function (_html) {




            let a = ["中文姓名","证件类型"];
            let b = [{"name":"证件类型","value":"IDTYPE"},{"name":"中文姓名","value":"CHINESE_NAME"},{"name":"证件号码","value":"IDNO"}];
            let result = []
            a.map((i)=>{
                b.map((j)=>{
                    if(i === j.name){
                        result.push(j.value)
                    }
                })
            });
            console.log(result);



            console.info("_html",_html)
            console.log(typeof _html)
            //
            // for(var i = 0 ; i< list.length ; i ++ ){
            //     var s = $(list[i]).text();//获取里面的博客摘要内容
            //     var dd=s.replace(/<[^>]+>/g,"");//截取html标签
            //     var dds=dd.replace(/&nbsp;/ig,"");//截取空格等特殊标签
            //
            //     $(list[i]).text("");//清空里面内容
            //     $(list[i]).text(dds.substring(0,200) + "...")//截取200个字符
            // }







        }


    }

});


function toChuangGuan()
{
    parent.location.href =baseURL+"modules/competition/chuangguanGame.html";
}