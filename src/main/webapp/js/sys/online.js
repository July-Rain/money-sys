var menuId =getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        idArr:[],
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单
            id:'',
            userName: '',
            userCode: '',
            orgCode:'',
            identify:'0',// 表明是用户
            userStatus:'2000',//查询有效的用户
            isOnline:'1',//表明查询在线
            currPage: 1,
            pageSize:10,
            totalCount: 0
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
        tchId:"",
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
                        // 默认展开第一级
                        vm.treeData.map(function (m) {
                            vm.idArr.push(m.id)
                        });
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
        //序列号计算
        indexMethod:function (index) {
            return index + 1 + (vm.formInline.currPage-1) * vm.formInline.pageSize;
        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleOff : function(index,row){
          $.ajax({
              type : "POST",
              url: baseURL + "sys/offlineUser?id="+row.id,
              contentType: "application/json",
              success:function (result) {
                  if(result.code==0){
                      alert("下线成功");
                      vm.reload();
                  }else{
                      alert(result.msg);
                  }
              }
          })
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
            vm.reload();
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
            vm.formInline.orgCode= data.localOrgCode;
            vm.formInline.orgId= data.orgId;
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
        toHome: function () {
            parent.location.reload()
        }
    }
});
