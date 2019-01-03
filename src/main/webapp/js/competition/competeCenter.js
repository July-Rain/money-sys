/**
 * Author: qj
 * Date: 2018/12/28
 * Description:
 */
var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        navData: [],//导航
        rankTop5: 'first',
        firstList: [
            {
                headImg: 'photo1.png',
                name: '飞翔的企鹅',
                score: 1824
            },
            {
                headImg: 'photo1.png',
                name: '飞翔的鸭子',
                score: 1324
            },
            {
                headImg: 'photo1.png',
                name: '飞翔的小鸡',
                score: 1214
            },
            {
                headImg: 'photo1.png',
                name: '飞翔的老鹰',
                score: 1114
            },
            {
                headImg: 'photo1.png',
                name: '飞翔的兔子',
                score: 1018
            }
        ]
    },
    created: function () {
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event);
        }
    }
});
