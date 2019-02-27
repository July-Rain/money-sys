var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        page: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            typeId: '',
            questionType: '',
            comContent: '',
            specialKnowledgeId: '',
            questionDifficulty: '',
            legalBasis: '',
            answerId: '',
            isEnble: '0',
            optUser: '',
            stuOptdepartment: '',
            video: '',
            videoUrl: '',
            answerList: [],
            releaseStatus: '0'
        },
        formLabelWidth: '120px',
        fileList: [],
        diffList: [],
        typeList: [],
        qtList: [],
        topicList: [],
        importFileUrl: baseURL+"sys/upload",//文件上传url
        videoFlag:false,
        videoUploadPercent: 0,
        addConfigFlag: false,
        answer: {
            questionContent: '',
            score: 0,
            ordersort: 1,
            isAnswer: 0
        },
        formInline: {
            page: 1,
            limit: 10,
            count: 0
        },
        rules: {
            comContent: [
                {max: 100, message: '最大长度100', trigger: 'blur'},
                {required: true, message: '请输入试题描述信息', trigger: 'blur'}
            ],
            typeId: [
                {required: true, message: '请选择试题分类', trigger: 'blur'}
            ],
            questionType: [
                {required: true, message: '请选择试题类型', trigger: 'blur'}
            ],
            questionDifficulty: [
                {required: true, message: '请选择试题难度', trigger: 'blur'}
            ],
            answerList: [
                {required: true, message: '请设置试题选项信息', trigger: 'blur'}
            ],
            video: [
                {required: true, message: '请上传视频信息', trigger: 'blur'}
            ],
            specialKnowledgeId: [
                {required: true, message: '请选择主题类型', trigger: 'blur'}
            ]
        },
        isEdit: false
    },
    methods: {
        typeChange: function(event){
            if(event == 1){
                // 视频，加入视频校验
                vm.rules.video = [
                    {required: true, message: '请上传视频信息', trigger: 'blur'}
                ];
            } else {
                vm.rules.video = [];
            }
        },
        qtChange: function(event){
            // 主观题时，取消选项信息校验
            if(event == 10007){
                vm.rules.answerList = [];
            } else {
                vm.rules.answerList = [
                    {required: true, message: '请设置试题选项信息', trigger: 'blur'}
                ];
            }
        },
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.page-1) * vm.formInline.limit;
        },
        uploadVideoProcess(event, file, fileList){
            this.videoFlag = true;
            this.videoUploadPercent = file.percentage.toFixed(2);
        },
        beforeAvatarUpload: function (file) {
            var  isLt10M = file.size / 1024 / 1024  < 100;
            if (['video/mp4', 'video/ogg', 'video/flv','video/avi','video/wmv','video/rmvb'].indexOf(file.type) == -1) {
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
            if(response.code == 0){
                vm.form.video = response.accessoryId;
                vm.form.videoUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            }else{
                this.$message.error('视频上传失败，请重新上传！');
            }
        },
        layFn() {
            $(".el-dialog").css("height", "auto")
        },
        // 文件上传
        handleRemove(file, fileList) {

        },
        handlePreview(file) {

        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        handleSizeChange: function (val) {
            vm.formInline.limit = val;
            vm.reload();
        },
        handleCurrentChange: function (val) {
            vm.formInline.page = val;
            vm.reload();
        },
        addExam: function () {
            vm.dialogFormVisible = true;
            vm.isEdit = false;
        },
        batchImport: function () {

        },
        downloadTemp: function () {

        },
        handleWatch: function (id) {
            vm.handleEdit(id);
            vm.isEdit = true;
        },
        handleEdit: function (id) {
            vm.isEdit = false;
            $.ajax({
                type: "GET",
                url: baseURL + "/testQuestion/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.form = result.data;
                        vm.dialogFormVisible = true;
                        if(vm.form.typeId == '1'){
                            vm.form.videoUrl = '/law_school_war_exploded/sys/download?accessoryId=' + vm.form.video;
                        } else {
                            vm.form.videoUrl = '';
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "testQuestion/delete/" + row.id,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.code === 0) {
                            vm.reload();
                        } else {
                            alert(result.msg);
                        }
                    }
                });

            }).catch(function () {

            });
        },
        save: function (formName) {
            // this.$refs['form'].resetFields();
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "testQuestion/save",
                        contentType: "application/json",
                        data: JSON.stringify(vm.form),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.dialogFormVisible = false;
                                        vm.reload();
                                        vm.form = {
                                            typeId: '',
                                            questionType: '',
                                            comContent: '',
                                            specialKnowledgeId: '',
                                            questionDifficulty: '',
                                            legalBasis: '',
                                            answerId: '',
                                            isEnble: '0',
                                            optUser: '',
                                            stuOptdepartment: '',
                                            page: 1,
                                            limit: 10,
                                            count: 0,
                                            video: '',
                                            videoUrl: '',
                                            answerList: []
                                        };
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
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "testQuestion/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.page = result.page.pageNo;
                        vm.formInline.limit = result.page.pageSize;
                        vm.formInline.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        addAnswer: function () {
            vm.addConfigFlag = true;
        },
        sure: function () {
            vm.form.answerList.push(vm.answer);
            vm.answer = {
                questionContent: '',
                score: 0,
                ordersort: 1,
                isAnswer: 0
            }
            vm.addConfigFlag = false;
        },
        toHome: function () {
            parent.location.reload()
        },
        closeDia: function () {
            vm.dialogFormVisible = false;
            this.$refs['form'].resetFields();
            vm.form = {
                typeId: '',
                questionType: '',
                comContent: '',
                specialKnowledgeId: '',
                questionDifficulty: '',
                legalBasis: '',
                answerId: '',
                isEnble: '0',
                optUser: '',
                stuOptdepartment: '',
                video: '',
                videoUrl: '',
                answerList: []
            }
        }
    },
    created: function () {
        this.$nextTick(function () {
            vm.reload();

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
        })
    }

})