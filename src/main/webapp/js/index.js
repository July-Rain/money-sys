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
        iconString:'icon-xunzhang',
        individual: null,
        srcphoto: baseURL+"/statics/img/police_head.png",

    },
    created: function () {
        this.$nextTick(function () {
            vm.loadNav();


            vm.getsrcphoto();


            //关于勋章
            vm.BymyMedal();

            // vm.eventTools(vm.individual, vm.mDown, vm.mMove, vm.mUp);
        })
    },

    methods: {
        getsrcphoto: function () {
            if(jsgetUser().photo){

                vm.srcphoto=baseURL+'/sys/download?accessoryId='+jsgetUser().photo;

            }
        },
        BymyMedal: function () {

            //进来 先看 这个人身上有没有 背着勋章的id  要根据勋章id反查勋章的字符串   (怕管理员修改了勋章的条件，每次进来在判断下 还符不符合勋章的条件)

            var u=jsgetUser();//user
            if(u.myMedal==null||u.myMedal==""){
                //身上没有勋章，已经设置了没勋章的样式  什么都不做
            }else {
                // 根据勋章id去反查
                $.ajax({
                    type: "GET",
                    url: baseURL + "medal/info/" + u.myMedal,
                    contentType: "application/json",
                    async: false,
                    success: function (result) {

                        //没找到 对应的 勋章 信息  说明被删了  我要把 勋章这一栏 制空
                        if(result.data==null){
                            //修改这个人身上背的勋章
                            $.ajax({
                                type: "GET",
                                // url: baseURL + "sys/updateBymyMedal?myMedal="+row.badge,
                                url: baseURL + "sys/updateBymyMedal?myMedal="+"",
                                dataType: "json",
                                async:false,
                                success: function (result1) {

                                }
                            });
                        }
                        //找到了 我们来看下他的 获取条件  还和自己的 符合不
                        else {
                            //查询这个人的 积分 学分情况
                            $.ajax({
                                type: "POST",
                                url: baseURL + "userIntegral/info",
                                dataType: "json",
                                async:false,
                                success: function (result2) {

                                     var integral= result2.info.integralPoint;//积分
                                     var credit= result2.info.creditPoint;//学分

                                        if(integral>=result.data.integral   && credit>=result.data.credit){
                                           vm.iconString=result.data.badge;// 将徽章给这个人
                                        }
                                        else {
                                            //不满足， 说明勋章条件 被改了

                                            $.ajax({
                                                type: "GET",
                                                // url: baseURL + "sys/updateBymyMedal?myMedal="+row.badge,
                                                url: baseURL + "sys/updateBymyMedal?myMedal="+"",
                                                dataType: "json",
                                                async:false,
                                                success: function (result1) {

                                                }
                                            });
                                        }


                                }
                            });
                        }


                    }
                });
            }


        },
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
                    console.log(result)
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
            console.info(item, baseURL);
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
                    vm.$message("暂无链接")
                }
            }
            if(item.url.indexOf("competition")>=0) {
                this.headerHide = true
            }else {
                this.headerHide = false
            }

        },
        logOut: function () {
            window.location.href = baseURL + 'logout';
        },
        toPersonCenter: function () {
            document.getElementById('container').src = baseURL + "modules/personalCen/messageRemind.html?id=1072374227602251777";
        },
    }
});
