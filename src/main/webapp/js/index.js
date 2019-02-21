/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */

var vm = new Vue({
    el: '#app',
    data: {
        childUrl: 'container.html',
        navData: [],//导航
        iframeSrc: '',
        backShow: true,
        loginType: 0,// 登陆方式
        headerHide: false,
        showThis: false
    },
    created: function () {
        this.$nextTick(function () {
            vm.loadNav();
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
            if(menuId == '' || menuId == null){
                menuId = '0';
            }
            $.ajax({
                type: "GET",
                url: baseURL + "sysmenu/nav" + _menuId,
                contentType: "application/json",
                data: {
                    parentId: menuId
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                        console.log(vm.navData)
                        if(url&&url!='container.html'){
                            vm.navData.push({
                                icon: "icon-fanhui",
                                name: "返回首页",
                                url: 'container.html',
                                type: '0',
                                parentId: '0'
                            });
                        }

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
        toChild: function (item,event) {

            if(item.url === 'container.html'){
                vm.navData.splice(-1,1);
                vm.showThis = false;
            }else {
                vm.showThis = true;
            }
            if(item.name == "退出"){
                window.location.href = baseURL + 'logout'
            }
            if (item.url) {
                $(".header-right ul li").removeClass("this");

                if(item.url.indexOf("?") == -1){

                    if(item.url == 'menu'){
                        vm.childUrl = item.list[0].url + "?id=" + item.id;
                    }else{
                        vm.childUrl = item.url + "?id=" + item.id;

                    }


                }else{
                    vm.childUrl = item.url + "&id=" + item.id;
                }

                if(item.type == '0'){
                    this.loadNav(item.id,item.url)
                }else {
                    var el = event.target;
                    $(".header-right ul li").removeClass("this");
                    if(typeof($(el).attr("data-url"))=="undefined"){
                        $(el).parents('li').addClass("this");
                    }else {
                        $(el).addClass("this");
                    }
                }

            } else {
                if (item.list.length == 0) {
                    alert("暂无链接")
                }
            }
            if(item.url.indexOf("competition")>=0) {
                this.headerHide = true
            }else {
                this.headerHide = false
            }

        }
    }
});
