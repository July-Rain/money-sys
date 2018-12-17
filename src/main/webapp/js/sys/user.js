var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            id:'',
            userName: '',
            userCode: '',
            orgCode:'',
            pageNo: 1,
            limit:10,
            count: 0
        },
        tableData: [],//表格数据
        visible: false,
        dialogConfig: false,//table弹出框可见性
        title:"",//弹窗的名称
        delIdArr: [],//删除数据
        treeData: [],//法律知识库分类树
        defaultProps: { // el-tree
            children: 'child',
            label: 'localOrgName'
        },
    },
    created: function () {

        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.treeData = result.orgList;
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
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleEdit : function(index,row){
          $.ajax({
              type : "POST",
              url: baseURL + "sys/resetPassword?id="+row.id,
              contentType: "application/json",
              success:function (result) {
                  if(result.code==0){
                      alert("密码重置成功");
                  }else{
                      alert(result.msg);
                  }
              }
          })
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize=val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage=val;
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
                url: baseURL + "system/getAllUsers",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.users.list;
                        vm.formInline.pageNo = result.users.pageNo;
                        vm.formInline.pageSize = result.users.pageSize;
                        vm.formInline.count = parseInt(result.users.count);
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
    }
});
