var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data:{
        dataList:[],
        answers:[],
        radio_disabled: false,
    },
    methods: {
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            var answer = vm.answers[0];
            var right = 0;

            if(answer == answerId){
                right = 1;
            }

            var obj = {
                id: params.substring(4),
                status: 1,
                list: [{
                    qId: id,
                    answer: answer,
                    right: right,
                    typeName: typeName
                }]
            };
            console.log("2222222")
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/questions",
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        if(result.list.length == 0){
                            alert('答题结束，请提交本次练习');
                        } else {
                            vm.dataList = result.list;
                            vm.answers = [];
                            vm.radio_disabled = false;
                        }

                    } else {
                        alert(result.msg);
                    }
                }
            });

        }
    },
    created: function(){
        this.$nextTick(function () {
            console.log("11111111")
            var id = params.substring(4);

            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/questions",
                data: JSON.stringify({
                    id: id
                }),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dataList = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});