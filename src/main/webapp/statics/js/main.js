var dbsx = false,auditId = 0;
var navtab;
layui.config({
    base:'statics/js/'
}).use(['navtab'],function(){
    window.jQuery = window.$ = layui.jquery;
    window.layer = layui.layer;
    var element = layui.element(),
        navtab = layui.navtab({
            elem: '.larry-tab-box'
        });

    //iframe自适应
    $(window).on('resize', function() {
        var $content = $('#larry-tab .layui-tab-content');
        $content.height($(this).height() - 122);
        $content.find('iframe').each(function(index) {
            if(index!=0){
                $(this).height($content.height()-44);
            }else {
                $(this).height($content.height());
            }
        });
    }).resize();
});
function navTabFn(data) {
    layui.navtab({
        elem: '.larry-tab-box'
    }).tabAdd(data);
}
function openWindow(url, title, w, h) {
    var iWidth = w;
    var iHeight = h;
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
    myWindow  = window.open( url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}
// larry-side-menu向左折叠
function isShrinkSideFn (isShrink) {
    var isShrink=isShrink|false;//true隐藏、false显示
    if(isShrink) {
        $('#larry-body').animate({
            left: '0'
        });
        $('#larry-footer').animate({
            left: '0'
        });
        $('#larry-side').animate({
            width: '0'
        });
    } else {
        $('#larry-body').animate({
            left: '200px'
        });
        $('#larry-footer').animate({
            left: '200px'
        });
        $('#larry-side').animate({
            width: '200px'
        });
    }
};


layui.use(['jquery','layer','element'],function(){
    window.jQuery = window.$ = layui.jquery;
    window.layer = layui.layer;
    var element = layui.element();

    // larry-side-menu向左折叠
    $('.larry-side-menu').click(function () {
        var sideWidth = $('#larry-side').width();
        if(sideWidth === 200) {
            $('#larry-body').animate({
                left: '0'
            });
            $('#larry-footer').animate({
                left: '0'
            });
            $('#larry-side').animate({
                width: '0'
            });
        } else {
            $('#larry-body').animate({
                left: '200px'
            });
            $('#larry-footer').animate({
                left: '200px'
            });
            $('#larry-side').animate({
                width: '200px'
            });
        }
    });
});

//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li class="layui-nav-item" v-if="item.isShow==1" dc-select="false" >',
        '<a  @click="navClick" v-if="item.type === 0" href="javascript:;">',
        '<span class="iconbox"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="layui-icon">&#xe60a;</i></span>',
        '<span>{{item.name}}</span>',
        '<em class="layui-nav-more"></em>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-nav-child">',
        '<dd v-for="item in item.list" >',
        '<a @click="navUrl" class="navUrl" v-if="item.type === 1 &&  item.isShow==1 " href="javascript:;" :data-id="item.menuId" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',

        '<a @click="navThreeClick" v-if="item.type === 0" href="javascript:;" class="three_level">',
        '<span class="iconbox"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="layui-icon">&#xe60a;</i></span>',
        '<span >{{item.name}}</span>',
        '<em class="layui-nav-more"></em>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-three-child">',
        '<dd style="padding-left: 5px;" v-for="item2 in item.list">',
        '<a @click="navUrl" class="navUrl" v-if="item2.url != null" href="javascript:;" :data-id="item2.menuId" :data-url="item2.url"> <span>{{item2.name}}</span></a>',
        '</dd>',
        '</dl>',
        '</dd>',
        '</dl>',
        '<a v-if="item.type === 1 &&  item.isShow==1 " href="javascript:;" :data-id="item.menuId" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</li>'
    ].join(''),
    methods:{
        navClick:function (event){
            var _this=$(event.target).parents("li");
            if( _this.attr("dc-select")=="false" || _this.attr("dc-select")==undefined){
                $(_this).siblings().removeClass("layui-nav-itemed").attr("dc-select","false");
                _this.attr("dc-select","true").addClass("layui-nav-itemed");
            }else {
                $(_this).siblings().removeClass("layui-nav-itemed").attr("dc-select","false");
                _this.attr("dc-select","false").removeClass("layui-nav-itemed");
            }
            $(_this).children().unbind();
        },
        navUrl:function (event) {
            var $a;
            if ( $(event.target).hasClass("navUrl")){
                $a = $(event.target);
            }else {
                $a = $(event.target).parents("a");
            }
            var href = $a.data('url');
            var id = $a.data('id');
            var icon = $a.children('i:first').data('icon');
            var title = $a.children('span').text();
            var data = {
                href: href,
                icon: icon,
                title: title,
                id:id
            };
            this.navThis($a);
            navTabFn(data);

            vm.navTitle=title;//面包屑
        },
        navThreeClick:function (event) {
            var _this=$(event.target).parents("dd");
            var _thisP=_this.parents("li");
            if( _this.attr("dc-select")=="true" ){
                _this.removeClass("layui-three-itemed").attr("dc-select","false");
            }else {
                _this.attr("dc-select","true").addClass("layui-three-itemed").siblings().removeClass("layui-three-itemed").attr("dc-select","false");
                _this.find("dd").removeClass("dc-nav-this");
            }
        },
        navThis:function (event) {
            $(".dc-nav-this").removeClass("dc-nav-this");
            $(".three_level_op1").removeClass("three_level_op1");

            $(event).parent().addClass("dc-nav-this");
            if($(event).parents("dl").hasClass("layui-three-child")){
                $(event).parents(".layui-three-itemed").children('a').addClass("three_level_op1")
            }
        }
    }
});

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
    el:'#layui_layout',
    data:{
        user:{},
        menuList:{},
        password:'',
        newPassword:'',
        navTitle:"工作台",
        num:"",
        messageNum: 0,
        show:false,
        messageList: [],
        dialogVisible:false,//修改密码弹出框
    },
    methods: {
        cback:function(){
            $(window.parent.document).find(".layui-tab-item").eq(0).addClass("layui-show").siblings().removeClass("layui-show");
            $(window.parent.document).find("#admin-home").addClass("layui-this").siblings().removeClass("layui-this");
        },
        getMenuList: function () {
            $.getJSON("menu/nav?_"+$.now(), function(r){
                vm.menuList = r.menuList;
            });
        },
        isBlank:function (value) {
            //判断是否为空
            return !value || !/\S/.test(value)
        },
    },
    created: function(){
        this.getMenuList();
        //this.getUser();
    },
    mounted: function(){
        // 定时刷新获取消息提醒信息
        //setInterval(function() {getMessageNum() }, 1000*30);
    },
    destroyed: function(){
        //离开该页面
    }
});
