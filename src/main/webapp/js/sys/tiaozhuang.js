/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */
var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        navData: [],//导航
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    },
    methods: {
        toChild: function (item) {

                 parent.location.href =baseURL+item.url+"?id="+item.id;

        }
    }
});
