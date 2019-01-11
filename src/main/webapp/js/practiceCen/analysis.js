var vm = new Vue({
    el: '#app',
    data: {
        overall: [],
        total: 0,
        right: 0,
        themeAnswerNum: [],
        themeRightNum: []
    },
    methods: {

    },
    created: function () {
        $.ajax({
            type: "GET",
            url: baseURL + "/analysis/exercise/count",
            contentType: "application/json",
            success: function (result) {
                if (result.code === 0) {
                    vm.overall = result.map.overall;
                    vm.total = result.map.total;
                    vm.right = result.map.right;
                    vm.themeAnswerNum = result.map.themeAnswerNum;
                    vm.themeRightNum = result.map.themeRightNum;
                } else {
                    alert(result.msg);
                }
            }
        });
    }
});