/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var editor = null;
var menuId = getUrlParam("id");
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
            stuLawid: ""
        },
        tableData: [],//表格数据
        visible: false,
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
            stuCreat:new Date(),//获取当前时间
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
                {required: true, message: '请添加内容', trigger: 'blur'},
            ],
            stuKnowledge: [
                {required: true, message: '请选择法律主题分类', trigger: 'blur'}
            ]
        },
        dialogStuMedia: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: [],//删除数据
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        fileList: [],//文件列表
        importFileUrl: baseURL + "sys/upload",//文件上传url
        dialogDept: false,//部门的弹窗
        dialogUser: false,//人员的弹窗
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
            userStatus:'2000'//查询有效的用户

        },//人员查询
        userTableData: [],//人员表格信息
        multipleSelection: [],//选中人员信息
        multipleDeptSelection: [],//选中部门信息
        stuPoliceclassOption: [],//所属警种
        videoFlag: false,
        videoUploadPercent: 0,
        deptCheckData: [],//部门默认选中节点
        saveUserTableData: [],//用于人员回显表格的对象  --回显需加
        dialogLaw:false,//法律法规主题分类弹窗
        lawCheckData:[],//法律法规回显表格数据
        multipleLawSelection: [],//选中法律法规信息
        lawData: [],//法律知识库分类树 --去除全部的

    },
    created: function () {
        this.$nextTick(function () {
            //法律分类树数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/alltree",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.treeData = result.classifyList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            //去除全部
            $.ajax({
                type: "POST",
                url: baseURL + "law/alltree?flag=true",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.lawData = result.classifyList;
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
        this.$nextTick(function () {
            this.reload();
            this.reloadUser();
            this.breadArr = getBreadcrumb(menuId);
        })
    },
    methods: {
        // 初始化videojs // videojs
        // initPlayer: function () {
        //     var options = {
        //         bigPlayButton: false,
        //     };
        //     vm.uploadedPlayer = videojs('video-uploaded', options);
        // },
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
                    var url = vm.stuMedia.id ? "stumedia/updateStuMedia" : "stumedia/insertStuMedia";
                    var deptArr = vm.stuMedia.deptIds ? vm.stuMedia.deptIds.split(",") : [];
                    var userArr = vm.stuMedia.userIds ? vm.stuMedia.userIds.split(",") : [];
                    vm.stuMedia.deptArr = deptArr;
                    vm.stuMedia.userArr = userArr;
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.stuMedia),
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.stuMedia.id = result.id;
                                        vm.closePlay();
                                        vm.dialogStuMedia = false;
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
            // if (!vm.stuMedia.stuLawid) {
            //     alert("请选择左侧专项知识");
            //     return;
            // }
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
                stuLawid: "",//专项知识id
                stuKnowledge: "",//专项知识
                videoPicAcc: "",//视频首页
                stuCreat:new Date()
            },
                //清空editor
                editor.txt.html("");
            this.title = "新增";
            this.dialogStuMedia = true;
            this.deptCheckData=[];
            this.lawCheckData = [];
        },
        handleEdit: function (index, row) {
            this.title = "编辑";

            this.deptCheckData = [];
            this.lawCheckData = [];
            editor.txt.html("");
            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.dialogStuMedia = true;
                        vm.stuMedia = result.data;
                        vm.deptCheckData = result.data.deptArr;
                        vm.lawCheckData = result.data.stuLawid.split(",");
                        editor.txt.html(vm.stuMedia.comContent);
                        if (vm.stuMedia.stuType != 'pic' && vm.stuMedia.comContent) {
                            vm.stuMedia.contentUrl = baseURL + "sys/download?accessoryId=" + vm.stuMedia.comContent;
                            if (vm.stuMedia.videoPicAcc) {
                                vm.stuMedia.videoPicAccUrl = baseURL + "sys/download?accessoryId=" + vm.stuMedia.videoPicAcc;
                            }

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
                    url: baseURL + 'stumedia/delete',
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
            this.dialogStuMedia = false;
            vm.reload();
        },
        closePlay:function(){
            //关闭播放器
            //关闭页面时 如果有视频或者音频暂停播放
            //播放时暂停别的正在播放的音频
            var player;
            if(vm.stuMedia.stuType=='audio'){
                player = document.getElementById("audio");

            }else if(vm.stuMedia.stuType=='video'){
                player = document.getElementById("video");
            }
            if(player!==null&&vm.stuMedia.stuType!='pic'){
                //检测播放是否已暂停.audio.paused 在播放器播放时返回false.在播放器暂停时返回true

                if(!player.paused)
                {
                    player.pause();// 这个就是暂停//audio.play();// 这个就是播放
                }
            }

        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/listICreate?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // el-tree节点点击事件
        handleNodeClick: function (data) {
            vm.stuMedia.stuLawid = data.id;
            vm.stuMedia.stuKnowledge = data.classifyName;
            vm.formInline.stuLawid = data.id;
            this.reload();
        },
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode = data.orgCode;
            vm.userForm.orgId = data.orgId;
            this.reloadUser();
        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);

        },
        uploadSuccess: function (response, file, fileList) {
            //var that = this;  // videojs
            this.videoFlag = false;
            this.videoUploadPercent = 0;
            if (response.code == 0) {
                vm.stuMedia.comContent = response.accessoryId;
                vm.stuMedia.contentUrl = baseURL + "sys/download?accessoryId=" + response.accessoryId;
                setTimeout(function () {
                    vm.stuMedia.stuTime = document.getElementsByClassName("avatar")[0].duration;
                    vm.stuMedia.stuTime = document.getElementsByClassName("avatar")[0].duration;
                    //that.initPlayer();
                    //console.info("啊啊啊",document.getElementsByClassName("avatar")[0].currentTime,document.getElementsByClassName("avatar")[0].duration);
                }, 800)
            } else {
                this.$message.error('视频上传失败，请重新上传！');
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
            console.log(vm.stuMedia.stuType);
            vm.stuMedia.comContent = "";
        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept = true;

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser = true;
            this.huixian(this.stuMedia.userArr) //  --回显需加


        },
        confimDept: function () {
            this.multipleDeptSelection = this.$refs.deptTree.getCheckedNodes();
            this.stuMedia.deptIds = [];
            this.stuMedia.deptName = [];
            for (var i = 0; i < this.multipleDeptSelection.length; i++) {
                if (!this.stuMedia.deptIds) {
                    this.stuMedia.deptIds = this.multipleDeptSelection[i].id;
                    this.stuMedia.deptName = this.multipleDeptSelection[i].orgName;
                } else {
                    if(this.stuMedia.deptIds.indexOf(this.multipleDeptSelection[i].id)!==-1){
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
            //  --回显需加
            var val = this.multipleSelection;
            //遍历最终的人员信息
            if(val.length==0){
                vm.stuMedia.userIds = "";
                vm.stuMedia.userName = "";
            }
            for (var i = 0; i < val.length; i++) {
                if (i == 0) {
                    vm.stuMedia.userIds = val[i].id;
                    vm.stuMedia.userName = val[i].userName;
                } else {
                    vm.stuMedia.userIds = vm.stuMedia.userIds + ',' + val[i].id;
                    vm.stuMedia.userName = vm.stuMedia.userName + ',' + val[i].userName;
                }
            }
            this.dialogUser = false;
        },
        cancelUser: function () {
            this.dialogUser = false;
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
                        console.info("1", vm.userTableData)
                        vm.userForm.currPage = result.page.currPage;
                        vm.userForm.pageSize = result.page.pageSize;
                        vm.userForm.totalCount = parseInt(result.page.totalCount);

                        // 点击展示回显内容：   --回显需加
                        vm.huixian(vm.stuMedia.userArr)
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
        huixian: function (ids) {
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
        handleSelectionChange(val) {
            //选择人员信息
            this.multipleSelection = val;


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
        chooseLaw:function(){
            //选择法律法规主题分类
            this.dialogLaw = true;
        },
        cancelLaw:function(){
            this.dialogLaw = false;
        },
        confimLaw:function(){
            vm.stuMedia.caseLawid = "";
            vm.stuMedia.caseLawname = "";
            this.multipleLawSelection = this.$refs.lawTree.getCheckedNodes();
            for (var i = 0; i < this.multipleLawSelection.length; i++) {
                if (!this.stuMedia.stuLawid) {
                    this.stuMedia.stuLawid = this.multipleLawSelection[i].classifyId;
                    this.stuMedia.stuKnowledge = this.multipleLawSelection[i].classifyName;
                } else {
                    this.stuMedia.stuLawid += "," + this.multipleLawSelection[i].classifyId;
                    this.stuMedia.stuKnowledge += "," + this.multipleLawSelection[i].classifyName;
                }
            }
            this.dialogLaw = false;
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
        vm.stuMedia.comContent = html;
    };
    editor.create();

}
