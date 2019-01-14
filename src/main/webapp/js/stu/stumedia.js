/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:配置
 */
var editor;
var menuId=$("#menuId").val();
var vm = new Vue({
    el: '#app',
    data: {
        formInline: { // 搜索表单
            stuTitle: '',
            stuPoliceclass: '',
            stuType: "",
            currPage: 1,
            pageSize: 10,
            totalCount:0,
            stuLawid:""
        },
        tableData: [],//表格数据
        visible: false,
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
        rules: {//表单验证规则
            stuType: [
                {required: true, message: '请选择资料类型', trigger: 'blur'}
            ],
            stuTitle: [
                {required: true, message: '请输入标题', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            comContent: [
                {required: true, message: '请添加内容', trigger: 'blur'}
            ]
        },
        dialogStuMedia: false,//table弹出框可见性
        title:"",//弹窗的名称
        delIdArr: [],//删除数据
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'list',
            label: 'classifyName'
        },
        fileList:[],//文件列表
        importFileUrl:baseURL+"sys/upload",//文件上传url
        dialogDept: false,//部门的弹窗
        dialogUser: false,//人员的弹窗
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
        userTableData:[],//人员表格信息
        multipleSelection:[],//选中人员信息
        multipleDeptSelection:[],//选中部门信息
        stuPoliceclassOption:[],//所属警种
        videoFlag:false,
        videoUploadPercent:0,
        deptCheckData:[]//部门默认选中节点
    },
    created: function () {

        this.$nextTick(function () {
            //法律分类树数据
            $.ajax({
                type: "POST",
                url: baseURL + "law/tree",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.treeData = result.classifyList;
                    }else{
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
                    }else{
                        alert(result.msg);
                    }
                }
            });
            // 所属警种
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"POLICACLASS",Parentcode:"0"},
                success: function (result) {
                    vm.stuPoliceclassOption=result.dictlist;
                }
            });
        })
        this.$nextTick(function () {
            this.reload();
            this.reloadUser();
        })

    },
    methods: {
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize=val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage=val;
            this.reload();
        },
        // 保存和修改
        saveOrUpdate: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.stuMedia.id?"stumedia/updateStuMedia": "stumedia/insertStuMedia";
                    var deptArr = vm.stuMedia.deptIds?vm.stuMedia.deptIds.split(","):[];
                    var userArr = vm.stuMedia.userIds?vm.stuMedia.userIds.split(","):[];
                    vm.stuMedia.deptArr=deptArr;
                    vm.stuMedia.userArr=userArr;
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.stuMedia),
                        success: function(result){
                            if(result.code === 0){
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.stuMedia.id=result.id;
                                        vm.dialogStuMedia=false;
                                        vm.reload();
                                    }
                                });
                            }else{
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
        },
        addStuMedia: function () {
            if(!vm.stuMedia.stuLawid){
                alert("请选择左侧专项知识");
                return ;
            }
            var lawId=vm.stuMedia.stuLawid;
            var lawName=vm.stuMedia.stuKnowledge;
            this.stuMedia= {
                id:"",
                stuType: "1",
                stuTitle: "",
                comContent: "",
                deptIds: "",
                userIds: "",
                stuDescribe:"",
                userName:"",//适用人员姓名
                deptName:"",//适用部门姓名
                stuLawid:lawId,//专项知识id
                stuKnowledge:lawName,//专项知识
                videoPicAcc:"",//视频首页
            },
            this.title="新增";
            this.dialogStuMedia=true;
        },
        handleEdit: function (index, row){
            this.title="修改";
            this.dialogStuMedia=true;
            this.deptCheckData=[];
            $.ajax({
                type: "POST",
                url: baseURL + 'stumedia/info?id=' + row.id,
                contentType: "application/json",
                success: function (result) {
                    if(result.code === 0){
                        debugger
                        vm.stuMedia = result.data;
                        vm.deptCheckData=result.data.deptArr;
                        editor.txt.html(vm.stuMedia.comContent);
                        for (var i=0;i<vm.stuMedia.length;i++){
                            if(vm.stuMedia.stuType!='1'&&vm.stuMedia.comContent){
                                vm.stuMedia.contentUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                                if(vm.stuMedia.videoPicAcc){
                                    vm.stuMedia.videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].videoPicAcc;
                                }

                            }
                        }
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel: function (index, row){
            vm.delIdArr.push(row.id);
            var that = this
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'stumedia/delete' ,
                    async:true,
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
                that.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        closeDia : function(){
            this.dialogStuMedia=false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/list?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        debugger
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
            debugger
            vm.stuMedia.stuLawid=data.id;
            vm.stuMedia.stuKnowledge=data.classifyName;
            vm.formInline.stuLawid=data.id;
            this.reload();
            //console.log(data);
        },
        //部门人员控件中点击事件
        handleDeptNodeClick: function (data) {
            this.userForm.orgCode=data.orgCode;
            this.reloadUser();
        },
        handleCheckChange: function (data, checked, indeterminate) {
            console.log(data);

        },
        uploadSuccess: function (response, file, fileList) {
            this.videoFlag = false;
            this.videoUploadPercent = 0;
            if(response.code == 0){
                vm.stuMedia.comContent=response.accessoryId;
                vm.stuMedia.contentUrl=baseURL+"sys/download?accessoryId="+response.accessoryId;
                setTimeout(function () {
                    vm.stuMedia.stuTime=document.getElementsByClassName("avatar")[0].duration;
                    console.info("啊啊啊",document.getElementsByClassName("avatar")[0].currentTime,document.getElementsByClassName("avatar")[0].duration);
                },800)
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
        uploadError: function () {

        },
        beforeAvatarUpload: function (file) {
            /*if(!checkFile(file)) return false;*/
            var  isLt10M = file.size / 1024 / 1024  < 10;
            if (['video/mp4', 'video/ogg', 'video/flv','video/avi','video/wmv','video/rmvb'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的视频格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传视频大小不能超过10MB哦!');
                return false;
            }

        },
        beforeAudioUpload: function (file) {
            /*if(!checkFile(file)) return false;*/
            var  isLt10M = file.size / 1024 / 1024  < 10;
            if (['audio/ogg', 'audio/mpeg', 'audio/mp3', 'audio/wav'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的音频格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传音频大小不能超过10MB哦!');
                return false;
            }

        },
        beforePicUpload: function (file) {
            //图片上传之前的判断
            /*if(!checkFile(file)) return false;*/
            var  isLt10M = file.size / 1024 / 1024  < 10;
            if (['image/jpeg', 'image/jpg', 'image/png','image/gif','image/bpm'].indexOf(file.type) == -1) {
                this.$message.error('请上传正确的图片格式');
                return false;
            }
            if (!isLt10M) {
                this.$message.error('上传图片大小不能超过10MB哦!');
                return false;
            }

        },
        uploadVideoProcess(event, file, fileList){
            this.videoFlag = true;
            this.videoUploadPercent = file.percentage;
        },

        changeStuType: function () {
            //修改资料类型
            console.log(vm.stuMedia.stuType);
            vm.stuMedia.comContent="";
        },
        chooseDept: function () {
            //选择部门
            console.log(vm.deptData);
            this.dialogDept=true;

        },
        chooseUser: function () {
            //选择人员
            this.dialogUser=true;

        },
        confimDept: function () {
            this.multipleDeptSelection=this.$refs.deptTree.getCheckedNodes();
            for(var i=0;i<this.multipleDeptSelection.length;i++){
                if (!this.stuMedia.deptIds) {
                    this.stuMedia.deptIds=this.multipleDeptSelection[i].id;
                    this.stuMedia.deptName=this.multipleDeptSelection[i].orgName;
                }else{
                    this.stuMedia.deptIds+=","+this.multipleDeptSelection[i].id;
                    this.stuMedia.deptName+=","+this.multipleDeptSelection[i].orgName;
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
        reloadUser: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getAllUsers",
                dataType: "json",
                data: vm.userForm,
                success: function (result) {
                    if (result.code == 0) {
                        vm.userTableData = result.page.list;
                        vm.userForm.currPage = result.page.currPage;
                        vm.userForm.pageSize = result.page.pageSize;
                        vm.userForm.totalCount = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        userHandleSizeChange: function (val) {
            this.userForm.pageSize=val;
            this.reloadUser();
        },
        userHandleCurrentChange: function (val) {
            this.userForm.currPage=val;
            this.reloadUser();
        },
        /*toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
            });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },*/
        handleSelectionChange(val) {
            //选择人员信息
            this.multipleSelection = val;
            //遍历最终的人员信息
            for (var i=0;i<val.length;i++){
                if (!this.stuMedia.userIds) {
                    this.stuMedia.userIds=val[i].id;
                    this.stuMedia.userName=val[i].userName;
                }else{
                    this.stuMedia.userIds+=","+val[i].id;
                    this.stuMedia.userName+=","+val[i].userName;
                }
            }

        },
        changeStuType:function () {
            vm.stuMedia.comContent="";
            vm.stuMedia.contentUrl="";
            vm.stuMedia.videoPicAcc="";
            vm.stuMedia.videoPicAccUrl="";
            if(vm.stuMedia.stuType=='1'){
                loadEditor();
            }
        }
    },
    mounted() {
        this.$refs.stuDialog.open();
        setTimeout(() => {
            this.$refs.stuDialog.close();
            loadEditor();
    }, 2000);
    }
});
function loadEditor(){
    var E = window.wangEditor
    editor = new E('#editor')
    // 或者 var editor = new E( document.getElementById('editor') )
    //显示“上传图片”的tab
    editor.customConfig.uploadImgServer = baseURL+"sys/upload";// 上传图片到服务器
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
            }
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        vm.stuMedia.comContent=html;
    }
    editor.create();

}
