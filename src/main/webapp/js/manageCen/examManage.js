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
            releaseStatus: '0',
            status: '1'
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
            score: '',
            ordersort: '',
            isAnswer: 0
        },
        formInline: {

            comContent: '',
            isEnble: '',
            page: 1,
            limit: 10,
            count: 0
        },

        title:"新增",
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
            vm.title="新增"
            vm.form.status= '1'
            vm.dialogFormVisible = true;
            vm.isEdit = false;
        },
        batchImport: function () {

        },
        downloadTemp: function () {

        },
        handleWatch: function (id) {

            vm.handleEdit(id);
            vm.title="查看"
            vm.isEdit = true;
        },
        handleEdit: function (id) {
            vm.title="编辑"
            vm.isEdit = false;
            $.ajax({
                type: "GET",
                url: baseURL + "/testQuestion/info/" + id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        console.info(result);
                        vm.form = result.data;
                        vm.dialogFormVisible = true;
                        if (vm.form.typeId != '0' && vm.form.video) {
                            vm.form.videoUrl = baseURL + "sys/download?accessoryId=" + vm.form.video;
                        }

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        //假删除
        handleDel: function (index, row) {
            this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
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



        // 查询
        onSubmit: function () {


            this.reload();
        },
        // 表单重置
        resetForm: function (formName) {

            vm.formInline= {

                comContent: '',
                 isEnble:  '',
                    page:  1,
                    limit:  10,
                    count:  0,
            }
            this.reload();
        },

        save: function (formName) {
            // this.$refs['form'].resetFields();
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

                //判断试题名称  重复问题
                    var mytype = "1"

                    if (vm.form.id != null && vm.form.id != '') {

                        mytype = "2"
                    }








                    console.info(vm.form);



                    $.ajax({
                        type: "POST",
                        url: baseURL + "testQuestion/tqComContent?comContent=" + vm.form.comContent + "&mytype=" + mytype + "&id=" + vm.form.id,
                        dataType: "json",
                        async: false,
                        success: function (result) {

                            if (result.code == 0) {
                                if (result.type == "1")//说明找到了
                                    {
                                        alert("存在重复的试题描述，添加失败");
                                        return;
                                    }
                                else if(result.type == "0")
                                    {
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
                                                                // page: 1,
                                                                // limit: 10,
                                                                // count: 0,
                                                                video: '',
                                                                videoUrl: '',
                                                                answerList: [],
                                                                status:'1',
                                                            };
                                                        }
                                                    });
                                                } else {
                                                    alert(result.msg);
                                                }
                                            }
                                        });
                                    }
                            }
                            else {
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

        changisEnble: function (row,isEnble) {
            $.ajax({
                type: "POST",
                url: baseURL + "testQuestion/changisEnble?id="+row.id+"&isEnble="+isEnble,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.reload();
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

        toHome: function () {
            parent.location.reload()
        },
        closeDia: function () {
            this.closePlay2();
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
        },
        closePlay2:function(){
            //关闭播放器
            //关闭页面时 如果有视频或者音频暂停播放
            //播放时暂停别的正在播放的音频
            var player=null;

            if(vm.form.typeId=='1'){
                player = document.getElementById("video2");
            }
            if( player!=null ) {
                //检测播放是否已暂停.audio.paused 在播放器播放时返回false.在播放器暂停时返回true
                if (!player.paused) {
                    player.pause();// 这个就是暂停//audio.play();// 这个就是播放
                }
            }
        },
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