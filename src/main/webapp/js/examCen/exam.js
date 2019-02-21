var menuId = getUrlParam('id');
var storage = window.sessionStorage;
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        labelPosition: 'left',
        navData: [],//导航
        formInline: { // 搜索表单
            examName: '',
            startTime: '',
            endTime: '',
            pageNo: 1,
            pageSize: 1,
            limit: 10,
            count: 0
        },
        queformInline:{
            pageNo: 1,
            pageSize: 1,
            limit: 10,
            count: 0,
            queContent:'',
            typeId:''
        },
        skOption:[],
        tableData: [],//表格数据
        visible: false,
        examConfig: {

        },
        checkSetting: {},
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
        checkSettingRules: {},
        checkSettingDia: false,//阅卷设置弹出框
        viewCheckSettingDia: false,//查看阅卷信息弹出框
        title: "",//弹窗的名称
        delIdArr: [],//删除数据
        dialogAdd: false,
        setExam: false,//设置考试弹框
        dialogView: false,
        dialogEdit: false,
        setQuestion: false,// 设置题目弹框
        tableData3: [],
        sinMultipleSelection: [],//单选题多选框
        mulMultipleSelection:[],//多选题
        judgeMultipleSelection:[],//判断题
        subMultipleSelection:[],  //主观题
        sinMultScore:1,
        mulMultScore:1,
        judgeMultScore:1,
        subMultScore:1,
        randomQuesModal : false,
        randomQuesData:[],
        dataListSelections: [],//选中行
        examConfigForm:{}
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
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        quehandleSizeChange: function (val) {
            this.queformInline.pageSize = val;
            this.queReload();
        },
        quehandleCurrentChange:function(val){
            this.queformInline.currPage = val;
            this.queReload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        addConfig: function () {
            // parent.location.href = baseURL + "modules/examCen/examConfig.html";
            storage.setItem("operate",0); //新增
            this.dialogAdd = true;
            document.getElementById("dialogAdd").contentWindow.location.reload(true);

        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        handleEdit: function (index, row) {
            storage.setItem("operate",2); //修改
            storage.setItem("examConId",row.id);
            this.dialogEdit = true;
            document.getElementById("dialogEdit").contentWindow.location.reload(true);

        },
        handleDelExam: function (index, row) {
            var id = row.id;
            this.$confirm('此操作将永久删除该考试配置, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'exam/config/delete?id='+id,
                    async: true,
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
        handleChange: function () {
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        closeCheckSettingDia: function () {
            this.checkSettingDia = false;
            vm.reload();
        },
        closeViewCheckSettingDia: function () {
            this.viewCheckSettingDia = false;
            vm.reload();
        },
        handleInfo: function(index, row){
            storage.setItem("operate",1); //查看
            storage.setItem("examConId",row.id);
            this.dialogView = true;
            document.getElementById("dialogView").contentWindow.location.reload(true);

        },
        handleChange: function () {

        },
        saveCheckSet: function () {
            console.info(vm.checkSetting);
            $.ajax({
                type: "POST",
                url: baseURL + "exam/config/checkset",
                dataType: "json",
                data: JSON.stringify(vm.checkSetting),
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        alert("保存成功");
                        this.checkSettingDia = false;
                        vm.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exam/config/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.pageNo;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        generateOrViewCheck: function (index, row) {
            var checkPassword = row.checkPassword;
            if (checkPassword) {
                //查看
                this.getCheckSetting(row.id);
            } else {
                //生成
                this.checkSettingDia = true;
                vm.checkSetting.id = row.id;
            }
        },

        getCheckSetting : function(id){
            this.viewCheckSettingDia = true;
            $.ajax({
                type: "GET",
                url: baseURL + "exam/config/getCheckSetting?id="+id,
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.checkSetting = result.checkSetForm;
                    } else {
                        alert(result.msg);
                    }
                }
            })
        },
        toChild: function (item) {

            parent.location.href = baseURL + item.url + "?id=" + item.id;

        },
        toHome: function () {
            parent.location.reload()
        },
        setExamFn: function (index,row) {
            vm.examConfig = row;
            if(row.questionWay==='10033'){
                this.randomQuesModal = true
                this.getDict();
            }else {
                this.setExam = true
            }
        },
        handleSelectionChange(val) {
            if(val[0].questionType === '10004'){
                this.sinMultipleSelection = val;
            }else if (val[0].questionType === '10005'){
                this.mulMultipleSelection = val;
            }else if(val[0].questionType === '10006'){
                this.judgeMultipleSelection = val;
            }else {
                this.subMultipleSelection = val;
            }
        },
        addQuestion:function (type){
            this.setQuestion = true;
            vm.queformInline.questionType = type;
            this.queReload();
            this.getDict();
        },
        onQueSubmit : function () {
            this.queReload();
        },
        getDict : function(){
            $.ajax({
                type: "GET",
                url: baseURL + "exam/config/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.skOption = result.skOption;
                        vm.qtOption = result.qtOption;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        queReload : function () {
            $.ajax({
                type: "GET",
                url: baseURL + "testQuestion/list",
                dataType: "json",
                data: vm.queformInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData3 = result.page.list;
                        vm.queformInline.page = result.page.pageNo;
                        vm.queformInline.limit = result.page.pageSize;
                        vm.queformInline.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getQue : function () {
            this.setQuestion = false;
            console.info(vm.multipleSelection);
        },
        closeAutoQue:function(){
            this.setExam = false;
        },
        genAutoQue :function(){
            vm.examConfigForm.id = vm.examConfig.id;
            vm.examConfigForm.sinMultScore = vm.sinMultScore;
            vm.examConfigForm.mulMultScore = vm.mulMultScore;
            vm.examConfigForm.judgeMultScore = vm.judgeMultScore;
            vm.examConfigForm.sinMultipleSelection = vm.sinMultipleSelection;
            vm.examConfigForm.mulMultipleSelection = vm.mulMultipleSelection;
            vm.examConfigForm.judgeMultipleSelection = vm.judgeMultipleSelection;
            vm.examConfigForm.subMultipleSelection = vm.subMultipleSelection;
            console.info(vm.subMultipleSelection)
            var subScore;
            for (var i=0;i<vm.subMultipleSelection.length;i++){
                subScore += vm.subMultipleSelection[i].perScore;
            }
            subScore = subScore+vm.sinMultipleSelection.length*vm.sinMultScore
                +vm.mulMultipleSelection.length*vm.mulMultScore+vm.judgeMultipleSelection.length*vm.judgeMultScore;
            if (subScore!=vm.examConfig.examScore){
                vm.$alert( '配置规则总分应与试题总分相等', '操作失败', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return false;
                    }
                });
            }else {
                $.ajax({
                    type:"POST",
                    url :baseURL + "exam/config/examConfig/genAutoQue",
                    data: JSON.stringify(
                        vm.examConfigForm
                    ),
                    contentType: "application/json",
                    success :function (result) {
                        if (result.code === 0){
                            alert('试题配置完成');
                            this.setExam = false;
                            this.reload();
                        }
                    }
                })
            }

        },
        <!-- 随机出题开始-->
        //新增行
        handleAdd: function () {
            var index = this.randomQuesData.length;
            this.randomQuesData.push({
                specialKnowledgeId:null,
                questionType: null,
                questionNumber: null,
                questionScore: null,
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

            this.dataListSelections.map(function (index) {
                arr.push(index)
            })
            var arr2=[];
            vm.randomQuesData.map(function (item,index) {
                if(arr.indexOf(index)==-1){
                    arr2.push(item)
                }
            })
            vm.randomQuesData = arr2;
        },
        // 多选
        selectionChangeHandle: function (val) {
            this.dataListSelections = val;
        },
        handleSave:function(randomQuesData){
            console.info(randomQuesData)
            if (randomQuesData.length<=0||randomQuesData==''){
                vm.$alert('请配置随机出题规则', '操作失败', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return false;
                    }
                });
            }else {
                var isRight = true;
                var msg ='';
                var subScore=0;
                console.info("111",subScore)
                for(var i= 0;i<randomQuesData.length;i++){
                    if(randomQuesData[i].questionNumber==null||randomQuesData[i].questionNumber==''){
                        isRight = false;
                        msg = '题目数量不能为空';
                        break;
                    }else if(randomQuesData[i].questionScore==null||randomQuesData[i].questionScore==''){
                        isRight = false;
                        msg = '题目分值不能为空';
                        break;
                    }else if(randomQuesData[i].questionType==null||randomQuesData[i].questionType==''){
                        isRight = false;
                        msg = '题目类型不能为空';
                        break;
                    }
                    subScore +=parseFloat(randomQuesData[i].questionScore);
                }
                console.info("111",isRight,vm.examConfig.examScore)
                if(!isRight){
                    vm.$alert( msg, '操作失败', {
                        confirmButtonText: '确定',
                        callback: function () {
                            return false;
                        }
                    });
                }else if (subScore!=vm.examConfig.examScore){
                    console.info("111",subScore,vm.examConfig.examScore)
                    vm.$alert( '配置规则总分应与试题总分相等', '操作失败', {
                        confirmButtonText: '确定',
                        callback: function () {
                            return false;
                        }
                    });
                }else{
                    vm.examConfigForm.id = vm.examConfig.id;
                    vm.examConfigForm.examQueConfigList = randomQuesData;
                    $.ajax({
                        type:"POST",
                        url :baseURL + "exam/config/examConfig/genRandomQue",
                        data: JSON.stringify(
                            vm.examConfigForm
                        ),
                        contentType: "application/json",
                        success :function (result) {
                            if (result.code === 0){
                                alert('试题配置完成');
                                vm.randomQuesModal = false;
                                vm.reload();
                            }
                        }
                    })
                }
            }


        },
        closeRanDia : function () {
            vm.randomQuesModal = false;
        },

    },
    filters: {
        sinMultScoreFn: function (_length) {
            if(vm.sinMultScore || vm.sinMultScore ===0){
                return _length * vm.sinMultScore
            }
        },
        mulMultScoreFn: function (_length) {
            if(vm.mulMultScore || vm.mulMultScore ===0){
                return _length * vm.mulMultScore
            }
        },
        judgeMultScoreFn: function (_length) {
            if(vm.judgeMultScore || vm.judgeMultScore ===0){
                return _length * vm.judgeMultScore
            }
        },
        subMultScoreFn: function (_length) {
            if(vm.subMultScore || vm.subMultScore ===0){
                return _length * vm.subMultScore
            }
        }
    }
});
