var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        dailyConfig:{

        },
        specialKnowledgeIds:[],

        //试题难度
        itemjibie:[],
        navData: [],//导航
        formInline: { // 搜索表单
            value: '',
            name: '',
            status: "",
            pageNo: 1,
            pageSize: 1,
            limit : 10,
            count: 0
        },
        tableData: [],//表格数据
        visible: false,
        dailyQuestion:[],
        /*examConfig: {
            id: '',
            examName:'',
            code: '',
            value: '',
            remark: '',
            status: "1"
        },*/
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
                const dateMat= new Date(parseInt(daterc.replace("/Date(", "").replace(")/", ""), 10));
                const year = dateMat.getFullYear();
                const month = dateMat.getMonth() + 1;
                const day = dateMat.getDate();
                const hh = dateMat.getHours();
                const mm = dateMat.getMinutes();
                const ss = dateMat.getSeconds();
                const timeFormat= year + "/" + month + "/" + day;
                return timeFormat;
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
                    console.info("asfasf");
                    console.info(result);
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
                    console.info(result);
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
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },

        // 保存和修改
        saveOrUpdate: function (formName) {


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
                vm.dailyConfig={};//新增的时候制空
                vm.sss();//获取专项知识点
                this.title = "新增";
                this.dialogConfig = true;
            //parent.location.href =baseURL+"modules/examCen/examConfig.html";
        },
        handleEdit: function (index, row) {
            this.title = "修改规则";
            this.dialogConfig = true;
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dailyConfig = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            alert(row.id)
            //vm.delIdArr.push(row.id);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
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
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "dailyQuestion/list",
                dataType: "json",

                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.pageNo;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.count)+1;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },


        toChild: function (item) {

            parent.location.href =baseURL+item.url+"?id="+item.id;

        }
    }
});
