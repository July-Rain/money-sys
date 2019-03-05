var editor = null;
var vm = new Vue({
    el: "#app",
    data: {
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
            score: 0,
            ordersort: 1,
            isAnswer: 0
        },
        form: {
            typeId: '',
            questionType: '',
            comContent: '',
            specialKnowledgeId: '',
            questionDifficulty: '',
            legalBasis: '',
            answerId: '',
            isEnble: 2,
            optUser: '',
            stuOptdepartment: '',
            video: '',
            videoUrl: '',
            releaseStatus: 1,
            answerList: []
        },
        importFileUrl: baseURL+"sys/upload",//文件上传url
        addConfigFlag:  false,
        videoFlag:false,
        dialogStuMedia: false,
        stuMedia: {
            id:"",
            stuType: "1",
            stuTitle: "",
            comContent: "",
            deptIds: "",
            userIds: "",
            stuDescribe:"",
            userName:"",//适用人员姓名
            deptName:"",//适用部门姓名
            stuLawid:"",//专项知识id
            stuKnowledge:"",//专项知识
            stuPoliceclass:"",//所属警种
            videoPicAcc:"",//视频首页
        },
        stuPoliceclassOption: [],

    },
    methods: {
        handleSizeChange: function (val) {
            vm.formInline.limit = val;
            vm.refresh();
        },
        handleCurChange: function (val) {
            vm.formInline.page = val;
            vm.refresh();
        },
        refresh: function () {
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
        edit: function(id){
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
                        if(type == 0){
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
        uploadStuSuccess: function (response, file, fileList) {
            this.videoFlag = false;
            this.videoUploadPercent = 0;
            if(response.code == 0){
                vm.stuMedia.comContent = response.accessoryId;
                vm.stuMedia.contentUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            }else{
                this.$message.error('视频上传失败，请重新上传！');
            }
        },
        handlePicSuccess: function (response, file, fileList) {
            if(response.code == 0){
                vm.stuMedia.videoPicAcc=response.accessoryId;
                vm.stuMedia.videoPicAccUrl=baseURL+"sys/download?accessoryId="+response.accessoryId;
            }else{
                this.$message.error('图片上传失败，请重新上传！');
            }
        },
        beforePicUpload: function (file) {
            //图片上传之前的判断
            var  isLt10M = file.size / 1024 / 1024  < 100;
            if (['image/jpeg', 'image/jpg', 'image/png','image/gif','image/bpm'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的图片格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传图片大小不能超过100MB哦!');
                return false;
            }

        },
        closeDia : function(){
            this.dialogStuMedia=false;
            vm.refresh();
        },
        beforeAudioUpload: function (file) {
            var  isLt10M = file.size / 1024 / 1024  < 100;
            if (['audio/ogg', 'audio/mpeg', 'audio/mp3', 'audio/wav'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的音频格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传音频大小不能超过100MB哦!');
                return false;
            }

        },
        examine: function (id) {
            vm.form.opinion="";
            vm.stuMedia.opinion="";
            $.ajax({
                type: "GET",
                url: baseURL + "manuscript/info",
                data: {
                    id: id
                },
                contentType: "application/json",
                success: function (result) {

                    console.info(result);
                    if (result.code == 0) {
                        vm.manu = result.data;
                        var type = vm.manu.type;
                        if(type == 0){
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
        saveExamine: function (typea,type) {

            if(typea==1)
            {
                var opinion = vm.form.opinion;
            }
            else if(typea==2)
            {
                var opinion = vm.stuMedia.opinion;
            }
            var id = vm.manu.id;

            var obj = {
               key: id,
               value: type,
                opinion: opinion,
            };
            $.ajax({
                type: "POST",
                url: baseURL + "manuscript/examine",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function(result){
                    if(result.code === 0){
                        vm.$message({
                            type: 'success',
                            message: '审核完成!'
                        });
                        vm.dialogFormVisible = false;
                        vm.dialogStuMedia = false;
                        vm.refresh();
                    }else{
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

        })
    },
    mounted: function() {
        // var that = this;
        // this.$refs.stuDialog.open();
        // this.$nextTick(function () {
        //     setTimeout(function () {
        //         that.$refs.stuDialog.close();
        //         loadEditor();
        //     },200);
        // });
    }
});

/**
 * wangEditor 富文本框初始
 *
 */
function loadEditor(){
    var E = window.wangEditor;
    editor = new E('#editor');
    // 或者 var editor = new E( document.getElementById('editor') )
    //显示“上传图片”的tab
    editor.customConfig.uploadImgServer = baseURL+"sys/upload";// 上传图片到服务器
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
            var url = baseURL+"sys/download?accessoryId="+result.accessoryId;
            insertImg(url)
        }
    };
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        vm.caseAna.caseContent=html;
    };
    editor.create();
}
