

var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            id:'',
            userName: '',
            userPoliceId: '',
            orgCode:'',
            identify:'1',//表明是教官
            currPage: 1,
            pageSize:10,
            totalCount: 0,
            userStatus:'2000'
        },
        tchData:[1],//教官数据
        newsData:[],//最新课程数据

        visible: false,
        dialogConfig: false,//table弹出框可见性
        title:"",//弹窗的名称
        tchId:"",//教官的id
    },
    created: function () {
        this.$nextTick(function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/list",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        vm.newsData = result.page.list;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        })
        this.$nextTick(function () {
            this.reload();
        })

    },
    methods: {
        toTchvedio:function(id){
            vm.tchId=id;
            window.location="./tchcourse.html?createUser="+id+"&addsrc=1";
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
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        closeDia : function(){
            this.dialogConfig=false;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/getUorT?isMp=true",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tchData = result.page.list;
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
            vm.formInline.orgCode= data.localOrgCode;
            this.reload();
        },
        uploadSuccess: function (response, file, fileList) {
            vm.stuMedia.comContent=response.filePath;
        },
        uploadError: function () {

        },
        beforeAvatarUpload: function () {
            if(!checkFile(file)) return false;
        },
        toUrl: function (url) {
            window.location.href = baseURL + url
        }
    }
}).$mount('#app');
