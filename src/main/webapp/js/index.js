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
            iframeSrc: ''
        }
    },
    created: function () {
        console.log("created")
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
    mounted(){
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
    },
    activated(){

    },


    methods: {

        loadFrame: function (obj) {
            var _src = $("#container").attr("src");
            if(_src === vm.iframeSrc){
                // window.location.reload()
            }
            vm.iframeSrc = _src;

        },

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

            if (item.url) {
                if(item.url.indexOf("?") == -1){
                    vm.childUrl = item.url + "?id=" + item.id;
                }else{
                    vm.childUrl = item.url + "&id=" + item.id;
                }

                this.loadNav(item.id)
                // 并不需要更新菜单
            } else {
                if (item.list.length == 0) {
                    alert("暂无链接")
                }
            }

        }
    }
});
