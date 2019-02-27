var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        dailyConfig:{

        },
        specialKnowledgeIds:[],
        xxx:true,
        //试题难度
        itemjibie:[],
        navData: [],//导航
        formInline: {
            limit: 10,
            page: 1,
            count: 0
        },
        tableData: [],//表格数据
        visible: false,
        dailyQuestion:[],

        rules: {//表单验证规则
            // value: [
            //     {required: true, message: '请输入参数名', trigger: 'blur'},
            //     {max: 50, message: '最大长度50', trigger: 'blur'}
            // ],
            // code: [
            //     {required: true, message: '请输入参数值', trigger: 'blur'},
            //     {max: 50, message: '最大长度50', trigger: 'blur'}
            // ]
        },
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            vm.reload();
        })
    },
    methods: {
        dateFormat : function(row, column, cellValue, index){
            var daterc = row[column.property]+"";
            if(daterc!=null){
                var dateMat= new Date(parseInt(daterc.replace("/Date(", "").replace(")/", ""), 10));
                var year = dateMat.getFullYear();
                var month = dateMat.getMonth() + 1;
                var day = dateMat.getDate();
                var hh = dateMat.getHours();
                var mm = dateMat.getMinutes();
                var ss = dateMat.getSeconds();
                var timeFormat= year + "-" + month + "-" + day;
                return timeFormat;
            }

        },
        dateFormat2: function (row, column) {
            var daterc = arguments.length === 1? row + "":row[column.property] + "";
            if (daterc != null) {
                var dateMat = new Date(parseInt(daterc.replace("/Date(", "").replace(")/", ""), 10));
                return dateMat.getFullYear() + "-" + (dateMat.getMonth() + 1) + "-" + dateMat.getDate();
            }

        },
        sss: function(){

            //专项知识
            $.ajax({
                type: "POST",
                url: baseURL + "recruitConfiguration/findAllTopic",
                dataType: "json",
                async:false,
                success: function (result) {
                    vm.specialKnowledgeIds=result.data;
                }
            });
            //试题难度
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"QUESTION_DIFF",Parentcode:"0"},
                success: function (result) {
                    vm.itemjibie=result.dictlist;
                }
            });
        },
        handleChange:function(){

        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (event) {
            vm.formInline.limit = event;
            vm.refresh();
        },
        handleCurChange: function (event) {
            vm.formInline.page = event;
            vm.refresh();
        },
        refresh: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "dailyQuestion/list",
                contentType: "application/json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.count = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 保存和修改
        saveOrUpdate: function (formName) {
            console.info(vm.dailyConfig);
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.dailyConfig.id ? "dailyQuestion/update" : "dailyQuestion/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.dailyConfig),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.dialogConfig = false;
                                        vm.reload();
                                    }
                                });
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        addConfig: function () {
            vm.xxx=true;
                vm.dailyConfig={};//新增的时候制空
                vm.sss();//获取专项知识点
                this.title = "新增";
                this.dialogConfig = true;
            //parent.location.href =baseURL+"modules/examCen/examConfig.html";
        },
        handlelook: function (index, row) {
            vm.xxx=false;
            this.title = "查看";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        console.info(result);
                        vm.sss();
                        vm.dailyConfig = result.data;
                        vm.dailyConfig.beginTime= vm.dateFormat2(vm.dailyConfig.beginTime);
                        vm.dailyConfig.endTime= vm.dateFormat2(vm.dailyConfig.endTime);
                        console.info(vm.dailyConfig);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleEdit: function (index, row) {
            vm.xxx=true;
            this.title = "修改";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.sss();
                        vm.dailyConfig = result.data;
                        vm.dailyConfig.beginTime= vm.dateFormat2(vm.dailyConfig.beginTime);
                        vm.dailyConfig.endTime= vm.dateFormat2(vm.dailyConfig.endTime);
                        console.info(vm.dailyConfig);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            alert(row.id)
            //vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'dailyQuestion/delete?id='+row.id,
                    //async: true,
                    //data: JSON.stringify(row.id),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {
            vm.refresh();
        },


        toChild: function (item) {

            parent.location.href =baseURL+item.url+"?id="+item.id;

        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
