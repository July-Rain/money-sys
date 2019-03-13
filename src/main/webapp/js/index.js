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
        showThis: false,
        isMouseDown: false,
        individual: null
    },
    created: function () {
        this.$nextTick(function () {
            // vm.individual = document.getElementById('individual');
            vm.loadNav();
            // vm.eventTools(vm.individual, vm.mDown, vm.mMove, vm.mUp);
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
                        console.log("indexjs 51",vm.navData)
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
            if(item.url === null){
                return
            }
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
                        if(vm.childUrl === item.url + "?id=" + item.id){
                            document.getElementById('container').src = vm.childUrl;
                        }else{
                            vm.childUrl = item.url + "?id=" + item.id;
                        }


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

        },

        // 悬浮头像移动
        // 定义公共方法
        eventTools: function (el, mDown, mMove, mUp) {
            var that = this;
            el.addEventListener('mousedown', function (e) {
                console.log(e)
                // e.points = that.getPoint(e, el);
                mDown && mDown(e);
            })
            el.addEventListener('mousemove', function (e) {
                // e.points = that.getPoint(e, el);
                mDown && mMove(e);
            })
            el.addEventListener('mouseup', function (e) {
                console.log(e)
                // e.points = that.getPoint(e, el);
                mDown && mUp(e);
            })
        },
        getPoint: function (e, el) {
            var x = e.pageX - el.offsetLeft;
            var y = e.pageY - el.offsetTop;
            return {
                dx: x,
                dy: y
            }
        },
        mDown: function (e) {
            vm.isMouseDown = true;
            e.preventDefault();
        },
        mMove: function (e) {
            e.preventDefault();
            if (!vm.isMouseDown) {
                return
            }
            var x = e.pageX,
                y = e.pageY;
            console.log(x, y)
            vm.individual.style.left = x + 'px';
            vm.individual.style.top = y + 'px';
        },
        mUp: function (e) {
            e.preventDefault();
            vm.isMouseDown = false;
        }
    }
});
