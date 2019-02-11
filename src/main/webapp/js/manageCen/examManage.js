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
            isEnble: true,
            optUser: '',
            stuOptdepartment: '',
            page: 1,
            limit: 10,
            count: 0,
            video: '',
            videoUrl: '',
            answerList: []
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
        }
    },
    mounted: function () {

    },
    methods: {
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
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
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
            vm.dialogFormVisible = true
        },
        batchImport: function () {

        },
        downloadTemp: function () {

        },
        handleWatch: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "/testQuestion/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.form = result.data;
                        vm.dialogFormVisible = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleEdit: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "/testQuestion/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.form = result.data;
                        vm.dialogFormVisible = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
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

        save: function () {
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
                                    isEnble: true,
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