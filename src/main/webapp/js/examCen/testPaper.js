var vm = new Vue({
    el: '#app',
    data: {
        form:{
            radio1:'',
            radio2:'',
            checkbox1: [],
            judgement1: ''
        }
    },
    created: function () {
    },
    methods: {
        fontS:function(){
            console.log(2)
            $("p,span").css("font-size","16px");
            $(".text_s").css({"font-size":"16px","font-weight":"bolder"});
            $(".text_m").css({"font-size":"18px","font-weight":"normal"});
            $(".text_l").css({"font-size":"24px","font-weight":"normal"})
        },
        fontM:function(){
            console.log(3)
            $("p,span").css("font-size","18px");
            $(".text_s").css({"font-size":"16px","font-weight":"normal"});
            $(".text_m").css({"font-size":"18px","font-weight":"bolder"});
            $(".text_l").css({"font-size":"24px","font-weight":"normal"})
        },
        fontL:function(){
            console.log(4)
            $("p,span").css("font-size","24px");
            $(".text_s").css({"font-size":"16px","font-weight":"normal"});
            $(".text_m").css({"font-size":"18px","font-weight":"normal"});
            $(".text_l").css({"font-size":"24px","font-weight":"bolder"})
        },
        submit: function () {
            console.info("交卷",vm.form)
        }
    }
});
