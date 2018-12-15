var menuId = $("#menuId").val();
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        details: [],
        options: [],
        tableData: [],//表格数据
        visible: false,
        examConfig: {},
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
        randomQuesModal: false,//table弹出框可见性
        autonomyQuesModal: false,
        title: "",//弹窗的名称
        delIdArr: [],//删除数据
        data:[],//题型data
        dataListSelections: [],//选中行
        deleteIds:[],
        etOption:[],
        gfOption:[],
        imtOption:[],
        qwOption:[],
        otOption:[],
        asuOption:[],
        rrtOption:[],
        ctOption:[],
        skOption:[],
        randomQuesData:[],
        qtOption:[]
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
        }),
        this.$nextTick(function () {
            //下拉框选项
            $.ajax({
                type: "GET",
                url: baseURL + "exam/config/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.etOption = result.etOption;
                        vm.gfOption = result.gfOption;
                        vm.imtOption = result.imtOption;
                        vm.qwOption = result.qwOption;
                        vm.otOption = result.otOption;
                        vm.asuOption = result.asuOption;
                        vm.rrtOption = result.rrtOption;
                        vm.ctOption = result.ctOption;
                        vm.skOption = result.skOption;
                        vm.qtOption = result.qtOption;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        });

    },
    methods: {
        // 查询
        onSubmit: function () {
            this.reload();
        },
        /*handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },*/
        openDiffModal : function(){
            if(vm.examConfig.questionWay=='10033'){
                this.randomQues();
            }
        },
        randomQues : function(){
            this.title = "随机出题配置";
            this.randomQuesModal = true;
        },
        handleSave:function(){

        },
        // 保存和修改
        saveOrUpdate: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysConfig.id ? "sysconfig/update" : "sysconfig/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.data11),

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
        closeDia: function () {
            this.randomQuesModal = false;
            vm.reload();
        },
        // 多选
        selectionChangeHandle: function (val) {
            this.dataListSelections = val;
        },
        //新增行
        handleAdd: function () {
            var index = this.randomQuesData.length;
            this.randomQuesData.push({
                knowledge:null,
                type: null,
                num: null,
                score: null,
                index: index
            })
        },
        handleDel : function(index,row){
            row.splice(index,1);
        },
        //批量删除
        handleDelete: function (dataListSelections) {
            this.dataListSelections=dataListSelections;
            var arr = [];
            for(var i=0; i<dataListSelections.length; i++){
                arr.push(dataListSelections[i].index);
            }
            var arr2=[];
            for(var i=0;i<vm.randomQuesData.length;i++){
                if(arr.indexOf(i)==-1){
                    arr2.push(vm.randomQuesData[i]);
                }
            }
            vm.randomQuesData = arr2;
        }
    }
});
