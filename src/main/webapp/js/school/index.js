/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
        form:{
            desc: ""
        },
        menuIndex: 0,
        navList: ["全部","试题","试题报错","学习","案例","建议","常见问题解答","我的参与"]
    },
    created: function (){

    },
    methods:{
        refreshTab: function (index) {
            this.menuIndex = index;
            // $ajax 更新数据
        }
    }
});
