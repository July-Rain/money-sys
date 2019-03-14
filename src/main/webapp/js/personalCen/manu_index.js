var editor = null;
var vm = new Vue({
    el: "#app",
    data: {
        idArr:[],// 部门Tree默认展开数据
        tableData: [],
        formInline: {
            limit: 10,
            page: 1,
            count: 0
        },
        dialogFormVisible: false,
        manu: {
            type: 0,
            test: {},
            stu: {}
        },
        typeList: [],
        qtList: [],
        fileList: [],
        diffList: [],
        formLabelWidth: '120px',
        topicList: [],
        answer: {
            questionContent: '',
            score: '',
            ordersort: '',
            isAnswer: 0
        },
        form: {
            typeId: '',
            questionType: '',
            comContent: '',
            specialKnowledgeId: '',
            questionDifficulty: '',
            answerId: '',
            isEnble: 2,
            optUser: '',
            stuOptdepartment: '',
            video: '',
            videoUrl: '',
            releaseStatus: 1,
            answerList: [],
            status:'1',
        },
        importFileUrl: baseURL + "sys/upload",//文件上传url
        addConfigFlag: false,
        videoFlag: false,
        dialogStuMedia: false,
        stuMedia: {
            id: "",
            stuType: "pic",
            stuTitle: "",
            comContent: "",
            deptIds: "",
            userIds: "",
            stuDescribe: "",
            userName: "",//适用人员姓名
            deptName: "",//适用部门姓名
            stuLawid: "",//专项知识id
            stuKnowledge: "",//专项知识
            stuPoliceclass: "",//所属警种
            videoPicAcc: "",//视频首页
        },
        stuPoliceclassOption: [],
        dialogDept: false,
        dialogUser: false,
        defaultDeptProps: {
            children: 'child',
            label: 'orgName'
        },//部门树的默认格式
        deptData: [],//部门树数据
        userData: [],//人员树数据
        userTableData: [],//人员表格信息
        deptCheckData: [],//部门默认选中节点
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
            userStatus:'2000'//查询有效的用户

        },//人员查询
         lookType:true,

        rules: {//表单验证规则

            comContent: [
                {required: true, message: '请输入试题描述', trigger: 'blur'},
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ],

            answerDescrible: [
                {required: true, message: '答案描述', trigger: 'blur'},
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ]
        },
        rules2: {//表单验证规则
            stuTitle: [
                {required: true, message: '请输入标题', trigger: 'blur'},
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ],
            stuPoliceclass: [
                {required: true, message: '请选择所属警种', trigger: 'blur'},
            ],
            // deptName: [
            //     {required: true, message: '请选择使用部门', trigger: 'blur'},
            // ],
            //
            // userName: [
            //     {required: true, message: '请选择使用人员', trigger: 'change'}
            // ],
            stuDescribe: [
                {required: true, message: '请输入描述', trigger: 'blur'},
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ],
        },
        rules3: {//表单验证规则
            questionContent: [
                {required: true, message: '请输入选项描述', trigger: 'blur'},
                {max: 200, message: '最大长度200', trigger: 'blur'}
            ],
            // score: [
            //     {required: true, message: '请输入分数', trigger: 'blur'},
            //     { pattern: regularExp("number"), message: '请输入正整数',trigger: 'blur' },
            //     {max: 3, message: '最大长度3', trigger: 'blur'},
            // ],
            ordersort: [
                {required: true, message: '请输入排序', trigger: 'blur'},
                { pattern: regularExp("number"), message: '请输入正整数' ,trigger: 'blur'},
                {max: 3, message: '最大长度3', trigger: 'blur'},
            ],

            isAnswer: [
                {required: true, message: '请选择是否为答案', trigger: 'change'}
            ],

        },
    },
    methods: {

//序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page - 1) * vm.formInline.limit;
        },
//序列号计算
        indexUserMethod: function (index) {
            return index + 1 + (vm.userForm.currPage - 1) * vm.userForm.pageSize;
        },
        searchUser: function () {
            //查询人员信息
            vm.reloadUser();
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
        userHandleSizeChange: function (val) {
            this.userForm.pageSize = val;
            this.reloadUser();
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage = val;
            this.reloadUser();
        },
        handleSelectionChange(val) {
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            for (var i = 0; i < val.length; i++) {
                if (!this.stuMedia.userIds) {
                    this.stuMedia.userIds = val[i].id;
                    this.stuMedia.userName = val[i].userName;
                } else {
                    this.stuMedia.userIds += "," + val[i].id;
                    this.stuMedia.userName += "," + val[i].userName;
                }
            }

        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode = data.orgCode;
            this.reloadUser();
        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);
        },
        confimDept: function () {
            this.multipleDeptSelection = this.$refs.deptTree.getCheckedNodes();
            this.stuMedia.deptIds = '';
            this.stuMedia.deptName = '';
            for (var i = 0; i < this.multipleDeptSelection.length; i++) {
                if (!this.stuMedia.deptIds) {
                    this.stuMedia.deptIds = this.multipleDeptSelection[i].id;
                    this.stuMedia.deptName = this.multipleDeptSelection[i].orgName;
                } else {
                    if(this.stuMedia.deptIds.indexOf(this.multipleDeptSelection[i].id) === -1){
                        this.stuMedia.deptIds += "," + this.multipleDeptSelection[i].id;
                        this.stuMedia.deptName += "," + this.multipleDeptSelection[i].orgName;
                    }
                }
            }
            this.stuMedia.deptIds = delFirstStr(this.stuMedia.deptIds,',');
            this.stuMedia.deptName = delFirstStr(this.stuMedia.deptName,',');
            this.dialogDept = false;
        },
        cancelDept: function () {
            this.dialogDept = false;
        },
        confimUser: function () {
            this.dialogUser = false;
        },
        cancelUser: function () {
            this.dialogUser = false;
        },
        handleSizeChange: function (val) {
            vm.formInline.limit = val;
            vm.refresh();
        },
        handleCurChange: function (val) {
            vm.formInline.page = val;
            vm.refresh();
        },
        refresh: function () {

            vm.formInline.createUser=jsgetUser().id;

            $.ajax({
                type: "GET",
                url: baseURL + "manuscript/list",
                data: vm.formInline,
                contentType: "application/json",
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
        edit: function (id) {

            vm.lookType=true;
            $.ajax({
                type: "GET",
                url: baseURL + "manuscript/info",
                data: {
                    id: id
                },
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.manu = result.data;
                        var type = vm.manu.type;
                        if (type == 0) {
                            vm.form = vm.manu.test;
                            vm.manu.stu = {};
                            vm.dialogFormVisible = true;
                        } else {
                            vm.stuMedia = vm.manu.stu;
                            vm.manu.test = {};
                            vm.dialogStuMedia = true;
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        look: function (id) {
            vm.lookType=false;
            $.ajax({
                type: "GET",
                url: baseURL + "manuscript/info",
                data: {
                    id: id
                },
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.manu = result.data;
                        var type = vm.manu.type;
                        if (type == 0) {
                            vm.form = vm.manu.test;
                            vm.manu.stu = {};
                            vm.dialogFormVisible = true;
                        } else {
                            vm.stuMedia = vm.manu.stu;
                            vm.manu.test = {};
                            vm.dialogStuMedia = true;
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        addQue: function () {
            vm.manu.id = '';
            vm.form = {
                status:'1',
                id:'',
                answerList: [],
                isEnble: 2,
                releaseStatus: 1,
                typeId: '0',
                questionType: vm.qtList[0].key,
                questionDifficulty: vm.diffList[0].key,
                specialKnowledgeId: vm.topicList[0].key
            };

            vm.lookType=true;
            // 显示试题新增框
            vm.dialogFormVisible = true;
        },
        addStu: function () {
            vm.manu.id = '';
            vm.lookType=true;
            vm.dialogStuMedia = true;
            this.stuMedia = {
                id: "",
                stuType: "pic",
                stuTitle: "",
                comContent: "",
                deptIds: "",
                userIds: "",
                stuDescribe: "",
                userName: "",//适用人员姓名
                deptName: "",//适用部门姓名
                stuLawid: '',//专项知识id
                stuKnowledge: '',//专项知识
                videoPicAcc: "",//视频首页
            };
            loadEditor();
            //清空editor
            editor.txt.html("");

        },
        save: function (formName) {

            this.$refs[formName].validate(function (valid) {

                    if (valid) {

                        var yesAnswer=0;//设置为正确答案的 个数
                        //先校验 下答题配置选项 问题

                        //如果没有 配置过
                        if(vm.form.answerList.length=='0')
                        {

                            vm.$message({
                                type: 'info',
                                message: '请先添加配置选项!'
                            });
                            return;
                        }

                        //看看 当前的试题类型
                        //10004  单选题      10005  多选题      10006  判断题
                        if(vm.form.questionType=='10004')
                        {
                            yesAnswer=0;//每次进来 先重置下；
                            if(vm.form.answerList.length=='1')
                            {
                                vm.$message({
                                    type: 'info',
                                    message: '请至少添加2个配置选项!'
                                });
                                return;
                            }

                            else if(vm.form.answerList.length>'1')
                            {
                                //有2个 以上的答案。。  但是要确保有一个是正确答案
                                //循环
                                $.each(vm.form.answerList, function(){
                                    if(this.isAnswer=='1')
                                    {
                                        yesAnswer=yesAnswer+1;
                                    }
                                });

                                if(yesAnswer==0)
                                {
                                    vm.$message({
                                        type: 'info',
                                        message: '请选择一个正确配置选项!'
                                    });
                                    return;
                                }
                                else if(yesAnswer>1)
                                {
                                    vm.$message({
                                        type: 'info',
                                        message: '请选择一个正确配置选项!'
                                    });
                                    return;
                                }
                            }
                        }

                        if(vm.form.questionType=='10005')
                        {
                            yesAnswer=0;//每次进来 先重置下；
                            if(vm.form.answerList.length<='2')
                            {
                                vm.$message({
                                    type: 'info',
                                    message: '请至少添加3个配置选项!'
                                });
                                return;
                            }

                            else if(vm.form.answerList.length>'1')
                            {
                                //有2个 以上的答案。。  但是要确保有一个是正确答案
                                //循环
                                $.each(vm.form.answerList, function(){
                                    if(this.isAnswer=='1')
                                    {
                                        yesAnswer=yesAnswer+1;
                                    }
                                });

                                if(yesAnswer<2)
                                {
                                    vm.$message({
                                        type: 'info',
                                        message: '请最少选择两个正确配置选项!'
                                    });
                                    return;
                                }
                                else if(yesAnswer>=vm.form.answerList.length)
                                {
                                    vm.$message({
                                        type: 'info',
                                        message: '请选择一个错误配置选项!'
                                    });
                                    return;
                                }
                            }
                        }
                        if(vm.form.questionType=='10006')
                        {
                            yesAnswer=0;//每次进来 先重置下；
                            if(vm.form.answerList.length!='2')
                            {
                                vm.$message({
                                    type: 'info',
                                    message: '请添加2个配置选项!'
                                });
                                return;
                            }

                            else if(vm.form.answerList.length=='2')
                            {
                                //有2个 以上的答案。。  但是要确保有一个是正确答案
                                //循环
                                $.each(vm.form.answerList, function(){
                                    if(this.isAnswer=='1')
                                    {
                                        yesAnswer=yesAnswer+1;
                                    }
                                });

                                if(yesAnswer!=1)
                                {
                                    vm.$message({
                                        type: 'info',
                                        message: '请选择1个正确配置选项!'
                                    });
                                    return;
                                }
                            }
                        }



                        vm.manu.test = vm.form;
                        vm.manu.type = 0;
                        $.ajax({
                            type: "POST",
                            url: baseURL + "manuscript/save",
                            data: JSON.stringify(vm.manu),
                            contentType: "application/json",
                            success: function (result) {
                                if (result.code === 0) {
                                    vm.manu.test = {};
                                    vm.dialogFormVisible = false;
                                    vm.refresh();
                                } else {
                                    alert(result.msg);
                                }
                            }
                        });
                    }
                    else {
                        console.log('error submit!!');
                        return false;
                    }
            });

        },
        toHome: function () {

        },
        handlePreview: function () {

        },
        handleRemove: function () {

        },
        beforeRemove: function () {

        },
        handleExceed: function () {

        },
        uploadVideoProcess(event, file, fileList) {
            this.videoFlag = true;
            this.videoUploadPercent = file.percentage.toFixed(2);
        },
        beforeAvatarUpload: function (file) {
            var isLt10M = file.size / 1024 / 1024 < 100;
            if (['video/mp4', 'video/ogg', 'video/flv', 'video/avi', 'video/wmv', 'video/rmvb'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的视频格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传视频大小不能超过100MB哦!');
                return false;
            }

        },
        uploadSuccess: function (response, file, fileList) {
            this.videoFlag = false;
            this.videoUploadPercent = 0;
            if (response.code == 0) {
                vm.form.video = response.accessoryId;
                vm.form.videoUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            } else {
                this.$message.error('视频上传失败，请重新上传！');
            }
        },
        uploadStuSuccess: function (response, file, fileList) {
            this.videoFlag = false;
            this.videoUploadPercent = 0;
            if (response.code == 0) {
                vm.stuMedia.comContent = response.accessoryId;
                vm.stuMedia.contentUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            } else {
                this.$message.error('视频上传失败，请重新上传！');
            }
        },
        addAnswer: function () {
            vm.addConfigFlag = true;
        },
        sure: function (formName) {

            this.$refs[formName].validate(function (valid) {

                    if (valid) {
                        vm.form.answerList.push(vm.answer);
                        vm.answer = {
                            questionContent: '',
                            score: "",
                            ordersort: "",
                            isAnswer: 0
                        };
                        vm.addConfigFlag = false;
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
            });

        },


        delanswer:function (scope) {

            vm.form.answerList.splice(scope.$index,1);
        },

        changeStuType: function () {
            vm.stuMedia.comContent = "";
            vm.stuMedia.contentUrl = "";
            vm.stuMedia.videoPicAcc = "";
            vm.stuMedia.videoPicAccUrl = "";
            if (vm.stuMedia.stuType == 'pic') {
                loadEditor();
            }
        },
        handlePicSuccess: function (response, file, fileList) {
            if (response.code == 0) {
                vm.stuMedia.videoPicAcc = response.accessoryId;
                vm.stuMedia.videoPicAccUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            } else {
                this.$message.error('图片上传失败，请重新上传！');
            }
        },
        beforePicUpload: function (file) {
            //图片上传之前的判断
            var isLt10M = file.size / 1024 / 1024 < 100;
            if (['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/bpm'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的图片格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传图片大小不能超过100MB哦!');
                return false;
            }

        },
        saveOrUpdate: function (formName) {

            this.$refs[formName].validate(function (valid) {

                    if (valid) {
                        vm.manu.stu = vm.stuMedia;
                        vm.manu.type = 1;
                        $.ajax({
                            type: "POST",
                            url: baseURL + "manuscript/save",
                            data: JSON.stringify(vm.manu),
                            contentType: "application/json",
                            success: function (result) {
                                if (result.code === 0) {
                                    vm.manu.stu = {};
                                    vm.dialogStuMedia = false;
                                    vm.refresh();
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
            this.dialogStuMedia = false;
            vm.refresh();
        },
        chooseDept: function () {
            //选择部门
            this.dialogDept = true;
        },
        chooseUser: function () {
            //选择人员
            this.dialogUser = true;
        },
        beforeAudioUpload: function (file) {
            var isLt10M = file.size / 1024 / 1024 < 100;
            if (['audio/ogg', 'audio/mpeg', 'audio/mp3', 'audio/wav'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的音频格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传音频大小不能超过100MB哦!');
                return false;
            }

        },
        remove: function (id) {
            $.ajax({
                type: "POST",
                url: baseURL + "manuscript/delete/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.refresh();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.refresh();

            $.ajax({
                type: "GET",
                url: baseURL + "exercise/random/dict",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.diffList = result.diffList;
                        vm.typeList = result.typeList;
                        vm.qtList = result.qtList;

                        vm.topicList = result.topicList;

                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.deptData = result.orgList;
                        vm.userData = result.orgList;
                        // 默认展开第一级
                        vm.deptData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
            // 所属警种
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async: false,
                data: {type: "POLICACLASS", Parentcode: "0"},
                success: function (result) {
                    vm.stuPoliceclassOption = result.dictlist;
                }
            });
        })
    },
    mounted() {
        var that = this;
        this.$refs.stuDialog.open();
        setTimeout(function () {
            that.$refs.stuDialog.close();
            loadEditor();
        }, 200);
    }
});

/**
 * wangEditor 富文本框初始
 *
 */
function loadEditor() {
    var E = window.wangEditor;
    editor = new E('#editor');
    // 或者 var editor = new E( document.getElementById('editor') )
    //显示“上传图片”的tab
    editor.customConfig.uploadImgServer = baseURL + "sys/upload";// 上传图片到服务器
    // 自定义菜单配置
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        // 'backColor',  // 背景颜色
        // 'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        // 'emoticon',  // 表情
        'image',  // 插入图片
        'table',  // 表格
        'video',  // 插入视频
        // 'code',  // 插入代码
        'undo',  // 撤销
        'redo'  // 重复
    ];
    editor.customConfig.uploadFileName = 'importfile';
    editor.customConfig.uploadImgHooks = {
        // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            var url = baseURL + "sys/download?accessoryId=" + result.accessoryId;
            insertImg(url)
        }
    }
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        vm.stuMedia.comContent = html;
    };
    editor.create();

}