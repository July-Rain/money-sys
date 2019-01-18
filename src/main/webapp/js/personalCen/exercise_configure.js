var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],
        title: '',
        dialogConfig: false,
        exerciseConfigure: {
            name: '',
            prefix: '',
            list: [],
            rangeType: 0
        },
        rangeType: [
            {key:0, value:'个人'},
            {key:1, value:'公开'},
            {key:2, value:'部门'}
        ],
        config: {
            topicId: '',
            topicName: '',
            primaryNum: 0,
            middleNum: 0,
            seniorNum: 0,
            topic: {},
            total: 0
        },
        addConfigFlag: false,
        topicList: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0
        },
        treeData: [],
        dialogDept: false,
        defaultProps: {
            children: 'child',
            label: 'localOrgName'
        },
        paperDialog: false,
        questionList: []
    },
    methods: {
        addConfig: function () {
            vm.dialogConfig = true;
        },
        createName: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/configure/createName",
                contentType: "application/json",
                data:{
                    prefix: vm.exerciseConfigure.prefix
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm.exerciseConfigure.name = result.name;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        add: function () {
            vm.addConfigFlag = true;
        },
        sure: function(){
            var obj = vm.config.topic;
            if(obj != null && obj != ''){
                vm.config.topicId = obj.key;
                vm.config.topicName = obj.value;
            }
            vm.exerciseConfigure.list.push(vm.config);
            if(vm.exerciseConfigure.list.length > 1){
                vm.exerciseConfigure.type = '综合类型';
            } else if(vm.exerciseConfigure.list.length == 1){
                vm.exerciseConfigure.type = obj.value;
            } else {
                vm.exerciseConfigure.type = '';
            }
            vm.config = {};
            vm.addConfigFlag = false;
        },
        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/configure/save",
                data: JSON.stringify(vm.exerciseConfigure),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dialogConfig = false;
                        vm.exerciseConfigure = {
                            name: '',
                            prefix: '',
                            list: [],
                            rangeType: 0
                        };
                        vm.refresh();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/configure/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        deptShow:function(){
            vm.dialogDept=true;
            if(vm.treeData.length==0){
                $.ajax({
                    type: "POST",
                    url: baseURL + "org/tree",
                    contentType: "application/json",
                    success: function(result){

                        if(result.code === 0){
                            vm.treeData = result.orgList;
                        }else{
                            alert(result.msg);
                        }
                    }
                });
            }
        },
        handleCheckChange: function (data, checked, node) {
            if(checked == true){
                this.checkedId = data.id;
                this.$refs.treeForm.setCheckedNodes([data]);
                vm.exerciseConfigure.depts= data.localOrgCode;
            }
        },
        confimDept: function(){
            vm.dialogDept = false;
        },
        cancelDept: function(){
            vm.dialogDept = false;
        },
        showPaper: function (id) {
            vm.paperDialog = true;
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/configure/show/"+id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionList = result.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        deleteOne: function(index){
            vm.exerciseConfigure.list.splice(index, 1);
        },
        dele: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "exercise/configure/delete/"+id,
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.refresh();
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        toHome:function () {
            parent.location.reload()
        }
    },
    created: function () {
        this.$nextTick(function () {
            this.refresh();
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.topicList = result.topicList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }
});