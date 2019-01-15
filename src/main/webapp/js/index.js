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
            iframeSrc: '',
            backShow: true
        }
    },
    created: function () {
        console.log("created")
        this.$nextTick(function () {
            vm.loadNav()
        })
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
        loadNav: function (menuId,url) {
            var _menuId = menuId?"?id="+menuId:"";
            $.ajax({
                type: "POST",
                url: baseURL + "menu/menuNav" + _menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                        if(url&&url!='container.html'){
                            vm.navData.push({
                                icon: "icon-zaixianxuexi",
                                name: "返回首页",
                                url: 'container.html'
                            });
                        }

                        console.info("nav with menuId", result)
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
            console.info("item!!!", item);
            if(item.url === 'container.html'){
                vm.navData.splice(-1,1);
            }
            if (item.url) {
                if(item.url.indexOf("?") == -1){
                    vm.childUrl = item.url + "?id=" + item.id;
                }else{
                    vm.childUrl = item.url + "&id=" + item.id;
                }

                if(
                    item.url === 'container.html'
                    || item.parentId == "0"
                    || item.list.length != 0
                    // || item.name === "系统管理"
                    // || item.name === "管理中心"
                    // || item.name === "评估中心"
                    // || item.name === "学情统计"
                    // || item.name === "考情统计"
                    // || item.name === "学分统计"
                    // || item.name === "积分统计"
                    // || item.name === "任务统计"
                ){
                    this.loadNav(item.id,item.url)
                }

            } else {
                if (item.list.length == 0) {
                    alert("暂无链接")
                }
            }

        }
    }
});
