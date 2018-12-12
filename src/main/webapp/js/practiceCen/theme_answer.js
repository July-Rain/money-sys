var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data:{
        dataList:[],
        answers:[],
        radio_disabled: false,
        obj: null
    },
    methods: {
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            var answer = vm.answers[0];
            var right = 0;

            if(answer == answerId){
                right = 1;
            }

            vm.obj = {
                id: params.substring(4),
                status: 1,
                list: [{
                    qId: id,
                    answer: answer,
                    right: right,
                    typeName: typeName
                }]
            };

        },
        next: function(){
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/questions",
                data: JSON.stringify(vm.obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        if(result.list.length == 0){
                            alert('答题结束，请提交本次练习');
                        } else {
                            vm.dataList = result.list;
                            vm.answers = [];
                            vm.radio_disabled = false;
                            vm.obj.list = [];
                        }

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        commit: function(){
            if(vm.obj == null){
                vm.obj = {
                    id: params.substring(4),
                    status: 2
                };
            }
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/commit",
                data: JSON.stringify(vm.obj),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        var str = "总题数：" + result.form.total;
                        str += "     做对题数：" + result.form.rightNum;
                        str += " list中为各类型题目答题信息，不一一表述";
                        alert(str);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    created: function(){
        this.$nextTick(function () {
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
                        if(result.list.length == 0){
                            alert('本次练习已全部结束，请直接提交本次练习');
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});