var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        dailyConfig:{
            id:"",
            createRule:"",//生成规则
            createUser:"",
            beginTime:"",//开始时间
            endTime:"",//结束时间
            questionType:"",//题目类型
            questionWay:"",//出题方式
            isShowAnswer:"",
            specialKnowledgeId:"",
            isShowLegal:"",//是否显示法律依据
            questionDifficulty:""
        },
        specialKnowledgeIds:[],
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
            value: [
                {required: true, message: '请输入参数名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入参数值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.reload();
        })
    },
    methods: {
        loadTopic: function(){
            $.ajax({
                type: "GET",
                url: baseURL + "topic/list",
                dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == 0) {
                        vm.specialKnowledgeIds = result.page.list;
                    } else {
                        alert(result.page);
                    }
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
            console.info(vm.dailyConfig.id);

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
                vm.dailyConfig={
                    id:'',
                    createRule : '',
                    createUser:"",
                    beginTime:'',
                    endTime:'',
                    questionType:'',
                    questionWay:'',
                    isShowAnswer:'',
                    specialKnowledgeId:'',
                    isShowLegal:'',
                    questionDifficulty:''
                };
                vm.loadTopic();
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
                //data: vm.formInline,
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
