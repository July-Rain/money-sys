var id = getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data:{
        dataList:[],
        answers:[],
        radio_disabled: false,
        limit: 1,
        page: 1,
        count: 0,
        pageCount: 0,
        theme:{
            id: id,
            status: 0,
            typeName: '',
            list: []
        },
    },
    methods: {
        sure: function (index) {// 多选
            var obj = vm.dataList[index];
            var answerId = obj.answerId;
            var checkList = obj.checkList;
            if(checkList.length == 0){
                alert('请选择选项后再提交');
                return;
            }

            var arr = new Array();
            arr = answerId.split(',');

            if(arr.length != checkList.length){
                obj.right = 0;
                return;
            }

            obj.right = 1;
            for(var i=0; i<arr.length; i++){
                if(checkList.indexOf(arr[i]) == -1){
                    obj.right = 0;
                    break;
                }
            }

            var form = {
                taskId: id,
                qId: obj.id,
                answer: checkList.join(','),
                right: obj.right,
                themeName: obj.themeName
            };

            vm.preserved.push(form);
        },
        affirm: function(index){
            var obj = vm.dataList[index];
            var answerId = obj.answerId;
            var userAnswer = obj.userAnswer;

            if(userAnswer == answerId){
                obj.right = 1;
            } else {
                obj.right = 0;
            }

            var form = {
                taskId: id,
                qId: obj.id,
                answer: userAnswer,
                right: obj.right,
                themeName: obj.themeName
            };

            vm.preserved.push(form);
        },
        next: function(){
            var current = vm.page;
            if(current < vm.pageCount){
                vm.page = current + 1;
                vm.theme.status = 1;
                vm.preserve(-1);
                vm.refresh();
                vm.radio_disabled = false;
            }
        },
        last: function(){
            if(vm.page > 1){
                vm.page = vm.page - 1;
                if(vm.preserved.length > 0){
                    this.$confirm('是否保存已答题目?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        vm.theme.status = 1;
                        vm.preserve(-1);
                    }).catch(function () {

                    });

                }
                vm.refresh();
            }
        },
        commit: function(type){
            vm.preserve(type);
            if(type == 1){
                $.ajax({
                    type: "GET",
                    url: baseURL + "exercise/theme/analysis/" + id,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.code === 0) {
                            vm.preserved = [];
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
        preserve: function (type) {
            // 保存答题情况
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/theme/preserve/" + type,
                data: JSON.stringify({
                    list: vm.preserved,
                    id : id
                }),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.preserved = [];
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        refresh: function(){
            var obj = {
                id: id,
                limit: vm.limit,
                page: vm.page
            };
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/theme/paper",
                data: obj,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dataList = result.result.list;
                        vm.count = result.result.count;
                        id = result.id;
                        vm.preserved = [];
                        vm.pageCount = result.result.pageCount;
                        vm.theme.typeName = result.result.typeName;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    created: function(){
        this.$nextTick(function () {
            vm.refresh();
        })
    }

});