/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var editor = null;
var menuId = getUrlParam("id");
var createUser=getUrlParam("createUser");
var from=getUrlParam("menu");
// 视频上传DOM 改为由v-show控制以实现上传视频替换功能后，会有一个warn，于功能无碍。待定。
var vm = new Vue({
    el: '#app',
    data: {
        breadArr: [],
        idArr: [],
        formInline: { // 搜索表单
            stuTitle: '',
            stuPoliceclass: '',
            stuType: "",
            currPage: 1,
            pageSize: 10,
            totalCount: 0,
            stuLawid: "",
            from:from,
            createUser:createUser
        },
        tableData: [],//表格数据
        visible: false,
        stuMediaTch: {
            id: "",
            stuType: "pic",
            stuTitle: "",
            comContent: "",
            deptIds: "",
            userIds: "",
            stuDescribe: "",
            videoPicAcc: "",//视频首页
            accessoryId:""
        },
        uploadedPlayer: null, // 上传视频实例
        rules: {//表单验证规则
            stuType: [
                {required: true, message: '请选择资料类型', trigger: 'blur'}
            ],
            stuTitle: [
                {required: true, message: '请输入标题', trigger: 'blur'},
                {max: 100, message: '最大长度100', trigger: 'blur'}
            ],
            comContent: [
                {required: true, message: '请添加内容', trigger: 'blur'}
            ],
            accessoryId: [
                {required: true, message: '请选择资料', trigger: 'blur'}
            ]
        },
        dialogStuMediaTch: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: [],//删除数据
        fileList: [],//文件列表
        importFileUrl: baseURL + "sys/upload",//文件上传url
        videoFlag: false,
        videoUploadPercent: 0,
        isEdit:true,//是否是修改状态
        fromFlag:from,//来源标记
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
            this.breadArr = getBreadcrumb(menuId);
            if(this.breadArr.length==0){
                this.breadArr=[{name:"教官中心"},{name:"教官课程"}]

            }
        })
    },
    methods: {
        // 初始化videojs // videojs
        initPlayer: function () {
            var options = {
                bigPlayButton: false,
            };
            this.uploadedPlayer = videojs('video-uploaded', options);
        },
        // videojs
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.currPage - 1) * vm.formInline.pageSize;
        },
        //序列号计算
        indexUserMethod: function (index) {
            return index + 1 + (vm.userForm.currPage - 1) * vm.userForm.pageSize;
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
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.stuMediaTch.id ? "stumediatch/updateStuMedia" : "stumediatch/insertStuMedia";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        async: false,
                        contentType: "application/json",
                        data: JSON.stringify(vm.stuMediaTch),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.stuMediaTch.id = result.id;
                                        vm.closePlay();
                                        vm.dialogStuMediaTch = false;
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
            vm.reload();
        },
        addStuMedia: function () {
            this.stuMediaTch = {
                id: "",
                stuType: "pic",
                stuTitle: "",
                comContent: "",
                deptIds: "",
                userIds: "",
                stuDescribe: "",
                videoPicAcc: "",//视频首页
                accessoryId:""
            },
                //清空editor
                editor.txt.html("");
            // 开启编辑功能
            editor.$textElem.attr('contenteditable', true);
            this.title = "新增";
            this.isEdit=true;
            this.dialogStuMediaTch = true;
        },
        handleEdit: function (index, row) {
            this.title = "编辑";
            this.dialogStuMediaTch = true;
            this.deptCheckData = [];
            this.isEdit=true;
            editor.txt.html("");
            // 开启编辑功能
            editor.$textElem.attr('contenteditable', true);
            $.ajax({
                type: "POST",
                url: baseURL + 'stumediatch/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.stuMediaTch = result.data;
                        if(vm.stuMediaTch.accessoryInfoList){
                            vm.fileList=vm.stuMediaTch.accessoryInfoList;
                            for(var i=0;i<vm.fileList.length;i++){
                                vm.fileList[i].name = vm.fileList[i].accessoryName;
                                vm.fileList[i].url = baseURL + "sys/download?accessoryId=" + vm.fileList[i].id;
                            }
                        }
                        if (vm.stuMediaTch.stuType != 'pic' && vm.stuMediaTch.comContent) {
                            vm.stuMediaTch.contentUrl = baseURL + "sys/download?accessoryId=" + vm.stuMediaTch.comContent;
                            if (vm.stuMediaTch.videoPicAcc) {
                                vm.stuMediaTch.videoPicAccUrl = baseURL + "sys/download?accessoryId=" + vm.stuMediaTch.videoPicAcc;
                            }

                        }else if(vm.stuMediaTch.comContent){
                            editor.txt.html(vm.stuMediaTch.comContent);
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        //查看详情
        handleDetail: function (index, row) {
            this.title = "查看";
            this.dialogStuMediaTch = true;
            this.deptCheckData = [];
            this.isEdit=false;
            // 禁用编辑功能
            editor.$textElem.attr('contenteditable', false)
            editor.txt.html("");
            $.ajax({
                type: "POST",
                url: baseURL + 'stumediatch/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    debugger
                    if (result.code === 0) {
                        vm.stuMediaTch = result.data;
                        if(vm.stuMediaTch.accessoryInfoList){
                            vm.fileList=vm.stuMediaTch.accessoryInfoList;
                            for(var i=0;i<vm.fileList.length;i++){
                                vm.fileList[i].name = vm.fileList[i].accessoryName;
                                vm.fileList[i].url = baseURL + "sys/download?accessoryId=" + vm.fileList[i].id;
                            }
                        }
                        if (vm.stuMediaTch.stuType != 'pic' && vm.stuMediaTch.comContent) {
                            vm.stuMediaTch.contentUrl = baseURL + "sys/download?accessoryId=" + vm.stuMediaTch.comContent;
                            if (vm.stuMediaTch.videoPicAcc) {
                                vm.stuMediaTch.videoPicAccUrl = baseURL + "sys/download?accessoryId=" + vm.stuMediaTch.videoPicAcc;
                            }

                        }else if(vm.stuMediaTch.comContent){
                            editor.txt.html(vm.stuMediaTch.comContent);
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row) {
            vm.delIdArr.push(row.id);
            var that = this
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'stumediatch/delete',
                    async: true,
                    data: JSON.stringify(vm.delIdArr),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        that.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
            }).catch(function () {
                // that.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        closeDia: function () {
            this.closePlay();
            this.dialogStuMediaTch = false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumediatch/listICreate?isMp=true&from="+from,
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = [];
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        uploadSuccess: function (response, file, fileList) {
            var that = this;  // videojs
            this.videoFlag = false;
            this.videoUploadPercent = 0;
            if (response.code == 0) {
                vm.stuMediaTch.comContent = response.accessoryId;
                vm.stuMediaTch.contentUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
                setTimeout(function () {
                    vm.stuMediaTch.stuTime = document.getElementsByClassName("avatar")[0].duration;
                    vm.stuMediaTch.stuTime = document.getElementsByClassName("avatar")[0].duration;
                    that.initPlayer();
                    //console.info("啊啊啊",document.getElementsByClassName("avatar")[0].currentTime,document.getElementsByClassName("avatar")[0].duration);
                }, 800)
            } else {
                this.$message.error('视频上传失败，请重新上传！');
            }
        },
        handlePicSuccess: function (response, file, fileList) {
            if (response.code == 0) {
                vm.stuMediaTch.videoPicAcc = response.accessoryId;
                vm.stuMediaTch.videoPicAccUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            } else {
                this.$message.error('图片上传失败，请重新上传！');
            }
        },
        uploadError: function () {

        },
        beforeAvatarUpload: function (file) {
            /*if(!checkFile(file)) return false;*/
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
        beforeAudioUpload: function (file) {
            /*if(!checkFile(file)) return false;*/
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
        beforePicUpload: function (file) {
            //图片上传之前的判断
            /*if(!checkFile(file)) return false;*/
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
        uploadVideoProcess(event, file, fileList) {
            this.videoFlag = true;
            this.videoUploadPercent = parseInt(file.percentage.toFixed(2));
        },

        changeStuType: function () {
            //修改资料类型
            console.log(vm.stuMediaTch.stuType);
            vm.stuMediaTch.comContent = "";
        },
        changeStuType: function () {
            vm.stuMediaTch.comContent = "";
            vm.stuMediaTch.contentUrl = "";
            vm.stuMediaTch.videoPicAcc = "";
            vm.stuMediaTch.videoPicAccUrl = "";
            if (vm.stuMediaTch.stuType == '1') {
                loadEditor();
            }
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(files, fileList) {
            this.$message.warning("当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件");
        },
        beforeRemove(file, fileList) {
            return this.$confirm("确定移除 ${ file.name }？");
        },
        handleAssSuccess: function (response, file, fileList) {
            if (response.code == 0) {
                vm.stuMediaTch.accessoryId = response.accessoryId;
                fileList[fileList.length-1].url = baseURL + "sys/download?accessoryId=" + response.accessoryId;
            } else {
                this.$message.error('图片上传失败，请重新上传！');
            }
        },
        closePlay:function(){
            //关闭播放器
            //关闭页面时 如果有视频或者音频暂停播放
            //播放时暂停别的正在播放的音频
            if(vm.stuMediaTch.stuType=='video'){
                var  player = document.getElementById("video");
                if(player!==null){
                    //检测播放是否已暂停.audio.paused 在播放器播放时返回false.在播放器暂停时返回true

                    if(!player.paused)
                    {
                        player.pause();// 这个就是暂停//audio.play();// 这个就是播放
                    }
                }
            }


        },
        toHome: function () {
            parent.location.reload()
        }
    },
    mounted: function () {
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
        vm.stuMediaTch.comContent = html;
    };
    editor.create();

}
