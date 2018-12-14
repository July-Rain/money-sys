/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data() {
        return {
            childUrl: 'container.html',
            navData: [],//导航
        }
    },
    created: function () {
        console.log("进入页面触发一次")

        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav",
                contentType: "application/json",
                success: function (result) {

                    if (result.code === 0) {
                        vm.navData = result.menuList;
                        console.info("infoinfo", result)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })

    },

    methods: {

        // 加载菜单
        loadNav: function (menuId) {
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                        console.info("info", result)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 导航栏
        handleSelect: function (key, keyPath) {
            console.log(key, keyPath);
        },


        toChild: function (item) {
            console.info("item!!!", item)

            // parent.location.href = baseURL + item.list[0].url + "?id=" + item.id;
            if (item.url) {
                vm.childUrl = item.url + "?id=" + item.id;
                // this.loadNav(item.id)
                // 并不需要更新菜单
            } else {
                if (item.list.length == 0) {
                    alert("暂无链接")
                }

            }

        }
    }
});
