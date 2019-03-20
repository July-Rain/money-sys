var menuId = $("#menuId").val();
var storage = window.sessionStorage;
var operate = storage.getItem("operate");
var vm = new Vue({
    el: '#app',
    data: function () {

        var data = []
        var validateDate = function (rule, value, callback) {
            if (vm.examConfig.endTime === '') {
                callback(new Error('请输入结束时间'))
            } else {
                if (!(new Date(Date.parse(vm.examConfig.startTime.replace(/-/g, "/"))).getTime() <
                    new Date(Date.parse(vm.examConfig.endTime.replace(/-/g, "/"))).getTime())) {
                    callback(new Error('结束日期必须大于开始日期'))
                } else {
                    callback()
                }
            }
        };
        var validateStartTime = function (rule, value, callback) {
            if (vm.examConfig.startTime === '') {
                callback(new Error('开始时间或者结束时间不能为空'))
            } else {
                if (new Date(Date.parse(vm.examConfig.startTime.replace(/-/g, "/"))).getTime() < new Date().getTime()) {
                    callback(new Error('开始时间应大于当前时间'))
                } else {
                    callback()
                }
            }
        };
        var validateQueWay = function (rule, value, callback) {
            if (vm.examConfig.questionWay === '') {
                callback(new Error('出题方式不能为空'))
            } else {
                if (vm.examConfig.groupForm === '10028' && vm.examConfig.questionWay == '10034') {
                    callback(new Error('选择随机组卷时出题方式只能为随机出题'))
                } else {
                    callback()
                }
            }
        };

        var validateReachReward = function (rule, value, callback) {

            if (vm.examConfig.reachRewardType != '10038' && vm.examConfig.reachRewardType != '') {

                if (vm.examConfig.reachReward === '') {

                    callback(new Error('请输入达标奖励分数'))
                } else {
                    callback();
                }
            } else {
                callback();
            }
        };
        return {
            idArr: [],// 部门Tree默认展开数据
            value2: [],
            navData: [],//导航
            details: [],
            options: [],
            tableData: [],//表格数据
            visible: false,
            examConfig: {
                deptIds: "",
                userIds: "",
                reachRewardType: "",
                enabled: "1",
                isMustTest: "10029",
                topicOrderType: "10032",
                optionOrderType: "10032",
                examTime: 120,
                startTime: '',
                endTime: '',
                examScore: 100,
                passPnt: 60,
                reachRewardType: "10038",
                examType: "10025",
                groupForm: "10028",
                questionWay: "10033",
                reachReward: 0
            },
            rules: {//表单验证规则
                examName: [
                    {required: true, message: '请输入考试名称', trigger: 'blur'},
                    {max: 50, message: '最大长度50', trigger: 'blur'}
                ],
                examType: [
                    {required: true, message: '请选择考试类型', trigger: 'change'},
                ],
                groupForm: [
                    {required: true, message: '请选择组卷方式', trigger: 'change'},
                ],
                isMustTest: [
                    {required: true, message: '请选择是否必考', trigger: 'change'}
                ],
                questionWay: [
                    {validator: validateQueWay, trigger: 'change', required: true}
                ],
                topicOrderType: [
                    {required: true, message: '请选择题目顺序', trigger: 'change'}
                ],
                optionOrderType: [
                    {required: true, message: '请选择选项顺序', trigger: 'change'}
                ],
                startTime: [
                    {required: true, message: '请输入开始时间', trigger: 'blur', validator: validateStartTime}
                ],
                endTime: [
                    {validator: validateDate, trigger: 'blur', required: true}
                ],
                examTime: [
                    {required: true, message: '请输入考试时长', trigger: 'blur'}
                ],
                answerShowRule: [
                    {required: true, message: '请选择答案显示规则', trigger: 'change'}
                ],
                examScore: [
                    {required: true, message: '请输入考试总分', trigger: 'blur'}
                ],
                passPnt: [
                    {required: true, message: '请输入达标线', trigger: 'blur'}
                ],
                reachRewardType: [
                    {required: true, message: '请选择达标奖励', trigger: 'change'}
                ],
                reachReward: [
                    {validator: validateReachReward, trigger: 'blur'}
                ]
            },
            title: "",//弹窗的名称
            delIdArr: [],//删除数据
            data: [],//题型data
            deleteIds: [],
            etOption: [],
            gfOption: [],
            imtOption: [],
            qwOption: [],
            otOption: [],
            asuOption: [],
            rrtOption: [],
            ctOption: [],
            answers: [],
            previewList: [],
            radio_disabled: false,
            skOption: [],
            qtOption: [],
            userTableData: [],//人员表格信息
            multipleSelection: [],//选中人员信息
            multipleDeptSelection: [],//选中部门信息
            deptCheckData: [],//部门默认选中节点
            dialogOrgDept: false,
            dialogDept: false,//部门的弹窗
            dialogUser: false,//人员的弹窗
            dialogCompany: false,//单位弹框
            dialogWatch: false,
            dialogChange: false,
            deptData: [],//部门树数据
            userData: [],//人员树数据
            defaultDeptProps: {
                children: 'child',
                label: 'orgName'
            },//部门树的默认格式
            defaultUserProps: {
                children: 'child',
                label: 'orgName'
            },//部门人员的默认格式
            userForm: {
                userCode: "",
                userName: "",
                orgCode: "",
                currPage: 1,
                pageSize: 10,
                totalCount: 0,
                identify: '',// 表明是用户
                userStatus: '2000'//查询有效的用户

            },//人员查询
            sinMultipleSelection: [],//单选题多选框
            mulMultipleSelection: [],//多选题
            judgeMultipleSelection: [],//判断题
            subMultipleSelection: [],  //主观题
            sinMultScore: 1,
            mulMultScore: 1,
            judgeMultScore: 1,
            subMultScore: 1,
            randomQuesModal: false,
            randomQuesData: [{
                questionType: '10004',
                specialKnowledgeArr: [],
                questionNumber: '',
                everyQuestionScore: '',
                questionScore: 0
            },
                {
                    questionType: '10005',
                    specialKnowledgeArr: [],
                    questionNumber: '',
                    everyQuestionScore: '',
                    questionScore: 0
                },
                {
                    questionType: '10006',
                    specialKnowledgeArr: [],
                    questionNumber: '',
                    everyQuestionScore: '',
                    questionScore: 0
                },
                {
                    questionType: '10007',
                    specialKnowledgeArr: [],
                    questionNumber: '',
                    everyQuestionScore: '',
                    questionScore: 0
                },
            ],
            dataListSelections: [],//选中行
            view: false,
            setExam: false,//自主出题设置考试弹框
            setQuestion: false,// 设置题目弹框
            changeQuestionDialog: false,
            tableData3: [],
            queformInline: {
                page: 1,
                limit: 10,
                count: 0,
                comContent: '',
                specialKnowledgeId: '',
                ids: []
            },
            queNum: 0,
            queScore: 0,
            examConfigForm: {},
            previewExam: false,
            changeQues: '',// 选中题目
            saveChangeQues: {},
            saveChangeQuesList: [],
            changeIndex: {
                index: 0,
                type: ''
            },
            handleType: '',
            saveUserTableData: [],//用于人员回显表格的对象  --回显需加
            optionIndex: ['A', 'B', 'C', 'D', 'E', 'F'],
            subAllScore: 0,
            saveButton: false,
            sinMultIds: [],
            mulMultIds: [],
            judgeMultIds: [],
            subMultIds: [],
            genRandQue: false,
            changeWay: false,
            saveChangePageDatas: []//保存人员信息（解决分页回显问题）
        };
    },

    created: function () {
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
                success: function (result) {
                    if (result.code === 0) {
                        vm.deptData = result.orgList;
                        vm.userData = result.orgList;
                        // 默认展开第一级
                        vm.userData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
            if (operate === '0') {
                //新增
                vm.view = false;
                vm.handleType = 'add';
            } else if (operate === '1') {
                //查看
                var examConfigId = storage.getItem("examConId");
                vm.view = true;
                vm.handleType = 'view';
                this.getExamDetail(examConfigId);
            } else {
                //修改
                var examConfigId = storage.getItem("examConId");
                vm.view = false;
                vm.handleType = 'edit';
                this.getExamDetail(examConfigId);
            }
            ;
            this.reloadUser();

        });
    },
    methods: {
        //序列号计算
        indexUserMethod: function (index) {
            return index + 1 + (vm.userForm.currPage - 1) * vm.userForm.pageSize;
        },
        getPassPnt: function () {
            vm.examConfig.passPnt = (vm.examConfig.examScore * 0.6).toFixed(0);
        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        getQuestionWay: function () {
            if (vm.examConfig.groupForm === '10028') {
                vm.examConfig.questionWay === '10033'
                vm.changeWay = true;
            } else {
                vm.changeWay = false;
            }
        },
        getExamDetail: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "exam/config/getExamDetail?id=" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.examConfig = result.examConfig;
                        vm.examConfig.reachReward = 0;
                        if (result.examQueConfiglist.length > 0) {
                            vm.randomQuesData = result.examQueConfiglist;
                            console.info(vm.randomQuesData);
                        }
                        vm.sinMultipleSelection = result.sinChoicList;
                        vm.mulMultipleSelection = result.mulChoicList;
                        vm.judgeMultipleSelection = result.judgeList;
                        vm.subMultipleSelection = result.subjectList;
                        /*sinMultScore:1,
                            mulMultScore:1,
                            judgeMultScore:1,*/
                        if (vm.sinMultipleSelection.length > 0) {
                            vm.sinMultScore = vm.sinMultipleSelection[0].perScore;
                        }
                        if (vm.mulMultipleSelection.length > 0) {
                            vm.mulMultScore = vm.mulMultipleSelection[0].perScore;
                        }
                        if (vm.judgeMultipleSelection.length > 0) {
                            vm.judgeMultScore = vm.judgeMultipleSelection[0].perScore;
                        }

                        for (var i = 0; i < vm.sinMultipleSelection.length; i++) {
                            vm.sinMultIds.push(vm.sinMultipleSelection[i].id);

                        }
                        for (var i = 0; i < vm.mulMultipleSelection.length; i++) {
                            vm.mulMultIds.push(vm.mulMultipleSelection[i].id);
                        }
                        for (var i = 0; i < vm.judgeMultipleSelection.length; i++) {
                            vm.judgeMultIds.push(vm.judgeMultipleSelection[i].id);
                        }
                        for (var i = 0; i < vm.subMultipleSelection.length; i++) {
                            vm.subMultIds.push(vm.subMultipleSelection[i].id);
                            vm.subAllScore += vm.subMultipleSelection[i].perScore;
                        }

                    } else {
                        alert(result.msg);
                    }
                }
            })
        },
        cancel: function () {
            if (operate === '0') {
                //新增
                window.parent.vm.dialogAdd = false
                /* if((vm.examConfig.id!=null||vm.examConfig.id=='')&&vm.handleType==='add'){
                     $.ajax({
                         type: "POST",
                         url: baseURL + 'exam/config/delete?id='+vm.examConfig.id,
                         async: true,
                         contentType: "application/json",
                         success: function (result) {
                             window.parent.vm.reload();
                         }
                     });
                 }*/
            } else if (operate === '1') {
                //查看
                window.parent.vm.dialogView = false
            } else {
                //编辑
                window.parent.vm.dialogEdit = false
            }
        },
        save: function (formName) {
            vm.saveButton = true;
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    vm.examConfig.qfList = vm.previewList;
                    var deptArr = vm.examConfig.deptIds ? vm.examConfig.deptIds.split(",") : [];
                    var userArr = vm.examConfig.userIds ? vm.examConfig.userIds.split(",") : [];
                    vm.examConfig.deptArr = deptArr;
                    vm.examConfig.userArr = userArr;
                    //
                    $.ajax({
                        type: "POST",
                        url: baseURL + "exam/config/saveOrUpdate",
                        data: JSON.stringify(
                            vm.examConfig
                        ),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$message({
                                    type: 'success',
                                    message: '保存成功!'
                                });
                                vm.examConfig = result.examConfig;
                                if (vm.examConfig.questionWay == '10033') {
                                    vm.randomQuesModal = true;
                                } else {
                                    vm.setExam = true;
                                }
                                vm.saveButton = false;
                                window.parent.vm.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                } else {
                    vm.saveButton = false;
                    return false;
                }
            })

        },
        open: function () {

            if (vm.examConfig.questionWay == "10033") {
                this.randomQuesModal = true;
            } else {
                this.openView();
            }
        },
        editExam: function () {
            if (vm.examConfig.questionWay == "10033") {
                this.randomQuesModal = true;
            } else {
                this.openView();
            }
        },
        confimDept: function () {

            this.multipleDeptSelection = this.$refs.deptTree.getCheckedNodes();
            this.examConfig.deptIds = '';
            this.examConfig.deptName = '';
            for (var i = 0; i < this.multipleDeptSelection.length; i++) {
                if (!this.examConfig.deptIds) {
                    this.examConfig.deptIds = this.multipleDeptSelection[i].id;
                    this.examConfig.deptName = this.multipleDeptSelection[i].orgName;
                } else {
                    if (this.examConfig.deptIds.indexOf(this.multipleDeptSelection[i].id) === -1) {
                        this.examConfig.deptIds += "," + this.multipleDeptSelection[i].id;
                        this.examConfig.deptName += "," + this.multipleDeptSelection[i].orgName;
                    }
                }
            }

            this.examConfig.deptIds = delFirstStr(this.examConfig.deptIds, ',');
            this.examConfig.deptName = delFirstStr(this.examConfig.deptName, ',');
            this.dialogDept = false;
        },
        cancelDept: function () {
            this.dialogDept = false;
        },
        confimUser: function () {
            vm.saveStep1()
            vm.saveStep2()
            //  --回显需加
            var val = this.saveChangePageDatas;//提交时候不能保存当前选中的，要保存所有的
            this.saveChangePageDatas = [];//保存后清空避免影响下次

            if (val.length == 0) {
                vm.examConfig.userIds = "";
                vm.examConfig.userName = "";
            }
            //遍历最终的人员信息
            for (var i = 0; i < val.length; i++) {
                if (i == 0) {
                    vm.examConfig.userIds = val[i].id;
                    vm.examConfig.userName = val[i].userName;
                } else {
                    vm.examConfig.userIds = vm.examConfig.userIds + ',' + val[i].id;
                    vm.examConfig.userName = vm.examConfig.userName + ',' + val[i].userName;
                }
            }
            this.dialogUser = false;
        },
        cancelUser: function () {
            this.dialogUser = false;
            // window.parent.vm.dialogAdd = false;
        },
        searchUser: function () {
            //查询人员信息
            vm.reloadUser();
        },
        userHandleSizeChange: function (val) {
            this.userForm.pageSize = val;
            this.reloadUser();
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage = val;
            this.reloadUser();
        },
        chooseOrgDept: function () {
            this.dialogCompany = true
        },
        cancelCompany: function () {
            this.dialogCompany = false;
        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept = true;

        },
        saveCompany: function () {
            this.dialogCompany = false;
        },
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode = data.orgCode;
            this.reloadUser();
        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);
        },
        handleSelectionChange(val) {
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            // for (var i = 0; i < val.length; i++) {
            //     if (!this.examConfig.userIds) {
            //         this.examConfig.userIds = val[i].id;
            //         this.examConfig.userName = val[i].userName;
            //     } else {
            //         this.examConfig.userIds += "," + val[i].id;
            //         this.examConfig.userName += "," + val[i].userName;
            //     }
            // }

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser = true;
            //this.reloadUser();
            this.huixian(this.examConfig.userArr) //  --回显需加
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
                        // 点击展示回显内容：   --回显需加
                        vm.huixian(vm.examConfig.userArr)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        huixian: function (ids) {
            console.info('ids',ids)
            // saveUserTableData    --回显需加
            if (!ids) {
                return
            }
            var that = this;
            ids.map(function (_id) {
                that.userTableData.map(function (_data) {
                    if (_id == _data.id) {
                        that.saveUserTableData.push(_data)
                    }
                })

            });
            console.info("saveUserTableData", that.saveUserTableData);
            this.$nextTick(function () {
                // vm.$refs.userTable.toggleRowSelection()
                vm.userToggleSelection(that.saveUserTableData)

            })
        },
        userToggleSelection(rows) {
            //  --回显需加
            if (rows) {
                rows.map(function (row) {
                    vm.$refs.userTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.userTable.clearSelection();
            }
        },

        <!-- 自主出题开始-->
        quehandleSizeChange: function (val) {
            this.queformInline.limit = val;
            this.queReload();
        },
        quehandleCurrentChange: function (val) {
            this.queformInline.page = val;
            this.queReload();
        },
        setExamFn: function (index, row) {
            vm.examConfig = row;
            if (row.questionWay === '10033') {
                this.randomQuesModal = true
                this.getDict();
            } else {
                this.setExam = true;
                this.dataListSelections = [];
            }
        },
        handleQueSelectionChange(val) {
            this.dataListSelections = val;
        },
        handleChange: function () {

        },
        cancleSetQue: function () {
            this.setQuestion = false;
        },
        addQuestion: function (type) {
            this.setQuestion = true;
            vm.queformInline.questionType = type;
            this.queReload();
            this.getDict();
        },
        onQueSubmit: function () {
            this.queReload();
        },
        //根据下标删除题目
        delSinMul: function (index) {
            this.sinMultIds.splice(index, 1)
            this.sinMultipleSelection.splice(index, 1);
        },
        delMulMul: function (index) {
            this.mulMultIds.splice(index, 1)
            this.mulMultipleSelection.splice(index, 1);
        },
        delJudMul: function (index) {
            this.judgeMultIds.splice(index, 1)
            this.judgeMultipleSelection.splice(index, 1);
        },
        delSubMul: function (index) {
            this.subMultIds.splice(index, 1)
            this.subMultipleSelection.splice(index, 1);
            this.calaSubAllScore();
        },
        getDict: function () {
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
        queReload: function () {
            var allIds = [];
            vm.queformInline.ids = [];
            vm.queformInline.comContent='';
            vm.queformInline.specialKnowledgeId = '';
            allIds = allIds.concat(vm.sinMultIds).concat(vm.mulMultIds).concat(vm.judgeMultIds).concat(vm.subMultIds);
            for (var i = 0; i < allIds.length; i++) {
                if (vm.queformInline.ids.indexOf(allIds[i]) == -1) {
                    vm.queformInline.ids.push(allIds[i]);
                }
            }
            var url = '';
            if (vm.queformInline.ids.length <= 0) {
                url = 'testQuestion/list';
            } else {
                url = 'exam/config/getQueList';
            }
            $.ajax({
                type: "GET",
                url: baseURL + url,
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
        getQue: function () {
            if (this.dataListSelections.length > 0) {
                if (this.dataListSelections[0].questionType === '10004') {
                    this.sinMultipleSelection = this.sinMultipleSelection.concat(this.dataListSelections);
                    for (var i = 0; i < this.dataListSelections.length; i++) {
                        this.sinMultIds.push(this.dataListSelections[i].id);
                    }
                } else if (this.dataListSelections[0].questionType === '10005') {
                    this.mulMultipleSelection = this.mulMultipleSelection.concat(this.dataListSelections);
                    for (var i = 0; i < this.dataListSelections.length; i++) {
                        this.mulMultIds.push(this.dataListSelections[i].id);
                    }
                } else if (this.dataListSelections[0].questionType === '10006') {
                    this.judgeMultipleSelection = this.judgeMultipleSelection.concat(this.dataListSelections);
                    for (var i = 0; i < this.dataListSelections.length; i++) {
                        this.judgeMultIds.push(this.dataListSelections[i].id);
                    }
                } else {
                    this.subMultipleSelection = this.subMultipleSelection.concat(this.dataListSelections);
                    for (var i = 0; i < this.dataListSelections.length; i++) {
                        this.subMultIds.push(this.dataListSelections[i].id);
                    }
                }

            }
            this.calaSubAllScore();
            this.setQuestion = false;
        },
        closeAutoQue: function () {
            this.setExam = false;
        },
        genAutoQue: function () {
            vm.examConfigForm.id = vm.examConfig.id;
            vm.examConfigForm.sinMultScore = vm.sinMultScore;
            vm.examConfigForm.mulMultScore = vm.mulMultScore;
            vm.examConfigForm.judgeMultScore = vm.judgeMultScore;
            vm.examConfigForm.sinMultipleSelection = vm.sinMultipleSelection;
            vm.examConfigForm.mulMultipleSelection = vm.mulMultipleSelection;
            vm.examConfigForm.judgeMultipleSelection = vm.judgeMultipleSelection;
            vm.examConfigForm.subMultipleSelection = vm.subMultipleSelection;
            console.info(vm.subMultipleSelection)
            var subScore = 0;
            for (var i = 0; i < vm.subMultipleSelection.length; i++) {
                subScore += parseFloat(vm.subMultipleSelection[i].perScore);
            }
            subScore = subScore + parseFloat(vm.sinMultipleSelection.length * vm.sinMultScore)
                + parseFloat(vm.mulMultipleSelection.length * vm.mulMultScore) + parseFloat(vm.judgeMultipleSelection.length * vm.judgeMultScore);
            if (subScore != vm.examConfig.examScore) {
                vm.$alert('配置规则总分应与试题总分相等', '操作失败', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return false;
                    }
                });
            } else {
                $.ajax({
                    type: "POST",
                    url: baseURL + "exam/config/examConfig/genAutoQue",
                    data: JSON.stringify(
                        vm.examConfigForm
                    ),
                    contentType: "application/json",
                    success: function (result) {
                        if (result.code === 0) {
                            vm.$alert('生成试卷成功', '提示', {
                                confirmButtonText: '确定',
                                callback: function () {
                                    vm.setExam = false;
                                    if (operate == 0) {
                                        window.parent.vm.dialogAdd = false;
                                    } else if (operate == 2) {
                                        window.parent.vm.dialogEdit = false;
                                    }
                                    window.parent.vm.reload();
                                }
                            });

                        } else {
                            alert(result.msg);
                        }
                    }
                })
            }

        },
        <!-- 随机出题开始-->
        //清空行
        handleDel: function (index) {
            vm.randomQuesData[index].specialKnowledgeId = [];
            vm.randomQuesData[index].questionNumber = 0;
            vm.randomQuesData[index].everyQuestionScore = 0;
            vm.randomQuesData[index].questionScore = 0;
        },
        changeQuestionFn: function (type, index) {
            vm.changeIndex = {
                index: index,
                type: type
            };
            vm.changeQuestionDialog = true;
            vm.queformInline.questionType = type;
            vm.queReload();
            vm.getDict();
        },
        getTemplateRow: function (index, row) {
            this.saveChangeQues = row;
        },
        openView: function () {
            this.setExam = true;
        },

        preview: function (randomQuesData) {
            console.info(randomQuesData);
            for (i = 0; i < randomQuesData.length; i++) {
                randomQuesData[i].questionScore = randomQuesData[i].questionNumber * randomQuesData[i].everyQuestionScore;
            }
            vm.randomQuesData = randomQuesData;
            if (randomQuesData.length <= 0 || randomQuesData == '') {
                vm.$alert('请配置随机出题规则', '操作失败', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return false;
                    }
                });
            } else {
                var isRight = true;
                var msg = '';
                var subScore = 0;
                for (var i = 0; i < randomQuesData.length; i++) {
                    subScore += parseFloat(randomQuesData[i].questionScore);
                }
                if (!isRight) {
                    vm.$alert(msg, '操作失败', {
                        confirmButtonText: '确定',
                        callback: function () {
                            return false;
                        }
                    });
                } else if (subScore != vm.examConfig.examScore) {
                    vm.$alert('配置规则总分应与试题总分相等', '操作失败', {
                        confirmButtonText: '确定',
                        callback: function () {
                            return false;
                        }
                    });
                } else {
                    vm.examConfigForm.id = vm.examConfig.id;
                    vm.examConfigForm.examQueConfigList = randomQuesData;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "exam/config/examConfig/preview",
                        data: JSON.stringify(
                            vm.examConfigForm
                        ),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                vm.previewExam = true;
                                vm.sinMultipleSelection = result.sinList;
                                for (var i = 0; i < vm.sinMultipleSelection.length; i++) {
                                    vm.sinMultIds.push(vm.sinMultipleSelection[i].id);
                                }
                                vm.mulMultipleSelection = result.mulList;
                                for (var i = 0; i < vm.mulMultipleSelection.length; i++) {
                                    vm.mulMultIds.push(vm.mulMultipleSelection[i].id);
                                }
                                vm.judgeMultipleSelection = result.judList;
                                for (var i = 0; i < vm.judgeMultipleSelection.length; i++) {
                                    vm.judgeMultIds.push(vm.judgeMultipleSelection[i].id);
                                }
                                vm.subMultipleSelection = result.subList;
                                for (var i = 0; i < vm.subMultipleSelection.length; i++) {
                                    vm.subMultIds.push(vm.subMultipleSelection[i].id);
                                }
                            } else {
                                alert(result.msg);
                            }
                        }
                    })
                }
            }
        },
        closePreview: function () {
            this.previewExam = false;
        },
        genRandQueAfterPreview: function () {
            vm.genRandQue = true;
            vm.examConfigForm.id = vm.examConfig.id;
            vm.examConfigForm.examQueConfigList = vm.randomQuesData;
            vm.examConfigForm.mustQueList = vm.saveChangeQuesList;
            $.ajax({
                type: "POST",
                url: baseURL + "exam/config/examConfig/genRanQueAfterPreview",
                data: JSON.stringify(
                    vm.examConfigForm
                ),
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('生成试卷成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.randomQuesModal = false;
                                vm.genRandQue = false;
                                if (operate == 0) {
                                    window.parent.vm.dialogAdd = false;
                                } else if (operate == 2) {
                                    window.parent.vm.dialogEdit = false;
                                }
                                window.parent.vm.reload();
                            }
                        });
                    } else {
                        vm.$message({
                            message: result.msg,
                            type: 'warning'
                        });
                        vm.genRandQue = false;
                    }
                }
            })
        },
        handleSave: function (randomQuesData) {
            vm.genRandQue = true;
            console.info(randomQuesData);
            for (i = 0; i < randomQuesData.length; i++) {
                randomQuesData[i].questionScore = randomQuesData[i].questionNumber * randomQuesData[i].everyQuestionScore;
            }
            console.info(randomQuesData);
            vm.randomQuesData = randomQuesData;
            if (randomQuesData.length <= 0 || randomQuesData == '') {
                vm.$alert('请配置随机出题规则', '操作失败', {
                    confirmButtonText: '确定',
                    callback: function () {
                        vm.genRandQue = false;
                        return false;
                    }
                });
            } else {
                var isRight = true;
                var msg = '';
                var subScore = 0;
                for (var i = 0; i < randomQuesData.length; i++) {
                    subScore += parseFloat(randomQuesData[i].questionScore);
                }
                if (!isRight) {
                    vm.$alert(msg, '操作失败', {
                        confirmButtonText: '确定',
                        callback: function () {
                            vm.genRandQue = false;
                            return false;
                        }
                    });
                } else if (subScore != vm.examConfig.examScore) {
                    vm.$alert('配置规则总分应与试题总分相等', '操作失败', {
                        confirmButtonText: '确定',
                        callback: function () {
                            vm.genRandQue = false;
                            return false;
                        }
                    });
                } else {
                    vm.examConfigForm.id = vm.examConfig.id;
                    vm.examConfigForm.examQueConfigList = randomQuesData;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "exam/config/examConfig/genRandomQue",
                        data: JSON.stringify(
                            vm.examConfigForm
                        ),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('生成试卷成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.randomQuesModal = false;
                                        vm.genRandQue = false;
                                        if (operate == 0) {
                                            window.parent.vm.dialogAdd = false;
                                        } else if (operate == 2) {
                                            window.parent.vm.dialogEdit = false;
                                        }
                                        window.parent.vm.reload();
                                    }
                                });
                            } else {
                                alert(result.msg);
                                vm.genRandQue = false;
                            }
                        }
                    })
                }
            }
        },
        closeRanDia: function () {
            vm.randomQuesModal = false;
        },
        resetQueForm: function (name) {
            this.$refs[name].resetFields();
            this.queformInline.page = 1;
            this.queReload();
        },
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.queformInline.page - 1) * vm.queformInline.limit;
        },
        saveChange: function () {
            var _index = this.changeIndex.index;
            var _saveQuse = this.saveChangeQues;
            if (_saveQuse) {
                switch (this.changeIndex.type) {
                    case '10004':
                        vm.removeAndReplace(vm.sinMultipleSelection[_index].id, _saveQuse);
                        vm.sinMultipleSelection[_index] = _saveQuse;
                        vm.sinMultIds[_index] = _saveQuse.id;
                        break;
                    case '10005':
                        vm.removeAndReplace(vm.mulMultipleSelection[_index].id, _saveQuse);
                        vm.mulMultipleSelection[_index] = _saveQuse;
                        vm.mulMultIds[_index] = _saveQuse.id;
                        break;
                    case '10006':
                        vm.removeAndReplace(vm.judgeMultipleSelection[_index].id, _saveQuse);
                        vm.judgeMultipleSelection[_index] = _saveQuse;
                        vm.judgeMultIds[_index] = _saveQuse.id;
                        break;
                    case '10007':
                        vm.removeAndReplace(vm.subMultipleSelection[_index].id, _saveQuse);
                        vm.subMultipleSelection[_index] = _saveQuse;
                        vm.subMultIds[_index] = _saveQuse.id;
                        break;
                }
                vm.changeQuestionDialog = false;
            } else {
                alert("请你选择一下")
            }
        },
        removeAndReplace: function (_before, _after) {
            this.saveChangeQuesList.push(_after.id)
            if (this.saveChangeQuesList.length === 0) {
                return;
            }
            if (this.saveChangeQuesList.indexOf(_before) > -1) {
                this.saveChangeQuesList.remove(_before);
            }
        },
        closeViewWindow: function () {
            if (handleType == 'view') {
                window.parent.vm.dialogView = false
            }
        },
        calaSubAllScore: function () {
            vm.subAllScore = 0;
            for (var i = 0; i < vm.subMultipleSelection.length; i++) {
                vm.subAllScore += vm.subMultipleSelection[i].perScore;
            }
        },
        saveStep1:function(){
            vm.multipleSelection.map(function (multipleSel) {
                vm.saveChangePageDatas.push(multipleSel)
            });
        },
        saveStep2:function () {
            vm.saveChangePageDatas = (function (arr) {

                var allArr = [];
                for (var i = 0; i < arr.length; i++) {
                    var flag = true;
                    for (var j = 0; j < allArr.length; j++) {
                        if (arr[i].id == allArr[j].id) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        allArr.push(arr[i]);
                    }
                }
                return allArr

            })(vm.saveChangePageDatas)


            var _arr = [];
            vm.userTableData.map(function (data1) {
                vm.saveChangePageDatas.map(function (data2) {
                    if (data1.id === data2.id) {
                        _arr.push(data1)
                    }
                })
            });
            vm.userToggleSelection(_arr)
        }
    },
    watch: {
        userTableData() {
            vm.saveStep1()
            this.$nextTick(function(){

                vm.saveStep2()
            })
        }
    }
});
