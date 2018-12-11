/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
        dataList:[{
            topic:'1',
            cont:'1+1=？'
        },{
            topic:'2',
            cont:'1+2=？'
        },{
            topic:'3',
            cont:'5+5=？'
        }],
        mySwiper:null, // swiper
    },
    created: function () {
        console.log("进入页面触发一次")
        this.$nextTick(function () {
            // //加载菜单
            // $.ajax({
            //     type: "POST",
            //     url: baseURL + "menu/nav",
            //     contentType: "application/json",
            //     success: function(result){
            //         if(result.code === 0){
            //             vm.navData = result.menuList;
            //         }else{
            //             alert(result.msg);
            //         }
            //     }
            // });
            this.mySwiper = new Swiper ('.swiper-container')
        })

    },
    methods: {
        // 上一页
        preClick : function () {
            var activeIndex = this.mySwiper.activeIndex;
            var newIndex = activeIndex - 1
            this.mySwiper.slideTo(newIndex);
        },
        // 下一页
        nextClick : function () {
            var activeIndex = this.mySwiper.activeIndex;
            var newIndex = activeIndex + 1
            console.log(newIndex,this.dataList.length)
            if (newIndex == this.dataList.length){
                alert('最后一题，已经没有题给你做了')
            }
            this.mySwiper.slideTo(newIndex);
        }
    }
});
