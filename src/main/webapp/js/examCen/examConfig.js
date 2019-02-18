var menuId = $("#menuId").val();
var vm = new Vue({
    el: '#app',
    data:function() {

        var data=[]
        var validateDate = function (rule, value, callback) {
            if (vm.examConfig.endTime === '') {
                callback(new Error('请输入结束时间'))
            } else {
                if (!(vm.examConfig.startTime < vm.examConfig.endTime)) {
                    callback(new Error('结束日期必须大于开始日期'))
                } else {
                    callback()
                }
            }
        };
        var validateReachReward = function (rule, value, callback) {

            if (vm.examConfig.reachRewardType!='10038'&&vm.examConfig.reachRewardType!='') {
                if(vm.examConfig.reachReward===''){
                    alert(11);
                    callback(new Error('请输入达标奖励分数'))
                }else {
                    callback();
                }
            }else {
                callback();
            }
        };
        var generateData = function () {
            for (var i = 1; i <= 15; i++) {
                data.push({
                    key: i,
                    label: `备选项 ${ i }`,
                    disabled: i % 4 === 0
                });
            }
            console.info("data格式",data)
            return data

        };
        return {
            idArr:[],// 部门Tree默认展开数据
            data2: generateData(),
            value2: [],
            filterMethod(query, item) {
                return item.pinyin.indexOf(query) > -1;
            },
            titleArr: ['待选题目','题目排序'],
            //menuId:"",//菜单id
            navData: [],//导航
            details: [],
            options: [],
            tableData: [],//表格数据
            visible: false,
            examConfig: {
                deptId: "",
                userId: "",
                reachRewardType:""
            },
            rules: {//表单验证规则
                examName: [
                    {required: true, message: '请输入考试名称', trigger: 'blur'},
                    {max: 50, message: '最大长度50', trigger: 'blur'}
                ],
                examType: [
                    {required: true, message: '请选择考试类型', trigger: 'change'},
                ],
                groupForm:[
                    {required: true, message: '请选择组卷方式', trigger: 'change'},
                ],
                isMustTest:[
                    {required: true, message: '请选择是否必考', trigger: 'change'}
                ],
                questionWay:[
                    {required: true, message: '请选择出题方式', trigger: 'change'}
                ],
                topicOrderType:[
                    {required: true, message: '请选择题目顺序', trigger: 'change'}
                ],
                optionOrderType:[
                    {required: true, message: '请选择选项顺序', trigger: 'change'}
                ],
                startTime:[
                    {required: true, message: '请输入开始时间', trigger: 'blur'}
                ],
                endTime:[
                    {validator: validateDate, trigger: 'blur'}
                ],
                examTime : [
                    {required: true, message: '请输入考试时长', trigger: 'blur'}
                ],
                answerShowRule:[
                    {required: true, message: '请选择答案显示规则', trigger: 'change'}
                ],
                examScore : [
                    {required: true, message: '请输入考试总分', trigger: 'blur'}
                ],
                passPnt : [
                    {required: true, message: '请输入达标线', trigger: 'blur'}
                ],
                reachRewardType : [
                    {required: true, message: '请选择达标奖励', trigger: 'change'}
                ],
                reachReward : [
                    {validator: validateReachReward, trigger: 'blur'}
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
            answers:[],
            previewList:[],
            radio_disabled: false,
            skOption:[],
            randomQuesData:[],
            qtOption:[],
            userTableData:[],//人员表格信息
            multipleSelection:[],//选中人员信息
            multipleDeptSelection:[],//选中部门信息
            deptCheckData:[],//部门默认选中节点
            dialogOrgDept: false,
            dialogDept: false,//部门的弹窗
            dialogUser: false,//人员的弹窗
            dialogCompany: false,//单位弹框
            dialogWatch: false,
            dialogChange: false,
            deptData:[],//部门树数据
            userData:[],//人员树数据
            defaultDeptProps:{
                children: 'child',
                label: 'orgName'
            },//部门树的默认格式
            defaultUserProps:{
                children: 'child',
                label: 'orgName'
            },//部门人员的默认格式
            userForm:{
                userCode:"",
                userName:"",
                orgCode:"",
                currPage: 1,
                pageSize: 10,
                totalCount:0

            },//人员查询
            //更换题目数据
            changeData: [{
                name: '全面依法治国必须坚持从中国实际出发。对此，下列哪一理解是正确的?',
                info: 'A：123；B：123；C：123；D：123'
            },{
                name: '全面依法治国必须坚持从中国实际出发。对此，下列哪一理解是正确的?',
                info: 'A：123；B：123；C：123；D：123'
            },{
                name: '全面依法治国必须坚持从中国实际出发。对此，下列哪一理解是正确的?',
                info: 'A：123；B：123；C：123；D：123'
            },{
                name: '全面依法治国必须坚持从中国实际出发。对此，下列哪一理解是正确的?',
                info: 'A：123；B：123；C：123；D：123'
            },{
                name: '全面依法治国必须坚持从中国实际出发。对此，下列哪一理解是正确的?',
                info: 'A：123；B：123；C：123；D：123'
            }],
            //选中题目
            currentRow: null

        };
    },

    created: function () {
        this.gettrData();
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
            //加载部门数据
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.deptData = result.orgList;
                        vm.userData = result.orgList;
                        // 默认展开第一级
                        vm.userData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });
        });
    },
    methods: {
        openDiffModal : function(e){
            if(e === "10033"){
                this.randomQues();
            }
            if(e === "10034"){
                this.selfQuse();
            }
        },
        getPassPnt : function(){
            vm.examConfig.passPnt = vm.examConfig.examScore*0.6;
        },
        randomQues : function(){
            this.title = "随机出题配置";
            this.randomQuesModal = true;
        },
        selfQuse : function(){
            this.autonomyQuesModal = true;

        },
        handleSave:function(randomQuesData){
            vm.examConfig.examQueConfigList = randomQuesData;
            vm.randomQuesModal = false;
        },
        mineQueSave : function(){
            vm.autonomyQuesModal = false;
        },
        //判断总分是否一致
        validScore : function(){
            if(vm.examConfig.questionWay==='10033'){
                //随机出题
                if(vm.examConfig.examQueConfigList==''){
                    //列表为空
                    vm.$alert('操作失败', '请配置随机出题规则', {
                        confirmButtonText: '确定',
                        callback: function () {
                            return false;
                        }
                    });
                }else {
                    //不为空  判断分数
                    var listScore;
                    for (var i= 0; i < vm.examConfig.examQueConfigList.length; i++) {
                        listScore += vm.examConfig.examQueConfigList.questionScore;
                    }
                    if(listScore!=vm.examConfig.examScore){
                        vm.$alert('操作失败', '随机出题配置总分数与试卷总分数不相等，请核对！', {
                            confirmButtonText: '确定',
                            callback: function () {
                                return false;
                            }
                        });
                    }else{
                        callback();
                    }
                }
            }else {
                //自主出题
                if(vm.examConfig.idList==''){
                    //列表为空
                    vm.$alert('操作失败', '请添加自主出题内容', {
                        confirmButtonText: '确定',
                        callback: function () {
                            return false;
                        }
                    });
                }else{
                    //TODO 判断分数等待前端修改模态框
                    callback();
                }
            }
        },
        preview:function(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.dialogWatch = true;
                    var deptArr = vm.examConfig.deptId?vm.examConfig.deptId.split(","):[];
                    var userArr = vm.examConfig.userId?vm.examConfig.userId.split(","):[];
                    console.log(vm.examConfig.examQueConfigList);
                    this.validScore();
                    vm.examConfig.deptArr=deptArr;
                    vm.examConfig.userArr=userArr;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "exam/config/examConfig/preview",
                        data: JSON.stringify(
                            vm.examConfig
                        ),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                vm.previewList = result.list;
                                console.log(vm.previewList);
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    return false;
                }
            });

        },
        generate : function(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    vm.examConfig.qfList = vm.previewList;
                    var deptArr = vm.examConfig.deptId ? vm.examConfig.deptId.split(",") : [];
                    var userArr = vm.examConfig.userId ? vm.examConfig.userId.split(",") : [];
                    vm.examConfig.deptArr = deptArr;
                    vm.examConfig.userArr = userArr;
                    //
                    $.ajax({
                        type: "POST",
                        url: baseURL + "exam/config/examConfig/generate",
                        data: JSON.stringify(
                            vm.examConfig
                        ),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                alert('生成成功');
                               window.location.reload = baseURL + "examCen/exam.html";
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }else {
                return false;
                }
            })
        },
        confimDept: function () {
            this.multipleDeptSelection=this.$refs.deptTree.getCheckedNodes();
            for(var i=0;i<this.multipleDeptSelection.length;i++){
                if (this.examConfig.deptId == "") {
                    this.examConfig.deptId=this.multipleDeptSelection[i].id;
                    this.examConfig.deptName=this.multipleDeptSelection[i].orgName;
                }else{
                    this.examConfig.deptId+=","+this.multipleDeptSelection[i].id;
                    this.examConfig.deptName+=","+this.multipleDeptSelection[i].orgName;
                }
            }
            this.dialogDept=false;
        },
        cancelDept: function () {
            this.dialogDept=false;
        },
        confimUser: function () {
            this.dialogUser=false;
        },
        cancelUser: function () {
            this.dialogUser=false;
        },
        searchUser: function () {
            //查询人员信息
            vm.reloadUser();
        },
        userHandleSizeChange: function (val) {
            this.userForm.pageSize=val;
            this.reloadUser();
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage=val;
            this.reloadUser();
        },
        chooseOrgDept: function(){
            this.dialogCompany = true
        },
        cancelCompany : function(){
            this.dialogCompany=false;
        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept=true;

        },
        CompanyClick: function(data){
            this.examConfig.organizedOrgCode = data.fullName;
            //data.fullName
            //data.id
        },
        saveCompany: function(){
            this.dialogCompany = false;
            
        },
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode=data.orgCode;
            this.reloadUser();
        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);

        },
        handleSelectionChange(val) {
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            for (var i=0;i<val.length;i++){
                if (this.examConfig.userId == "") {
                    this.examConfig.userId=val[i].id;
                    this.examConfig.userName=val[i].userName;
                }else{
                    this.examConfig.userId+=","+val[i].id;
                    this.examConfig.userName+=","+val[i].userName;
                }
            }

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser=true;

        },
        reloadUser: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getUorT?isMp=true",
                dataType: "json",
                data: vm.userForm,
                success: function (result) {
                    if (result.code == 0) {
                        vm.userTableData = result.page.list;
                        vm.userForm.currPage = result.page.currPage;
                        vm.userForm.pageSize = result.page.pageSize;
                        vm.userForm.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        closeDia: function () {
            this.randomQuesModal = false;
            // vm.reload();
        },
        closeSelf: function() {
            this.autonomyQuesModal = false;
        },
        // 多选
        selectionChangeHandle: function (val) {
            this.dataListSelections = val;
        },
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
        // 替换题目
        changeQuestion: function (index) {
            this.dialogChange = true
        },
        setCurrent(row) {
            this.$refs.singleTable.setCurrentRow(row);
        },
        handleCurrentChange(val) {
            this.currentRow = val;
        },
        submitChange(){
            console.info("选中的题目",this.currentRow);
            alert("选择了》》"+this.currentRow.name);
            this.dialogChange = false
        },
        gettrData(){
            var that = this;
            $.ajax({
                type: "GET",
                async: false,
                url: baseURL + "testQuestion/list",
                contentType: "application/json",
                data: {

                },
                success:function(result){
                    that.data2 = [];
                    result.page.list.map(function (info,index) {
                        that.data2.push({
                            disabled: false,
                            key: info.id,
                            label: info.comContent
                        })
                    })
                }});
        },
        getTestQue(value, direction, movedKeys) {
            vm.examConfig.idList=value;
            console.log(vm.examConfig.idList);
        }
    }
});
