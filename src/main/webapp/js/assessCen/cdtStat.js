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
            orgCode:'320000000000',
            currPage: 1,
            pageSize:10,
            totalCount: 0
        },//检索条件
        tableData: [],//表格数据

        userStatDiv:true,//显示人员维度
        // deptStatDiv:false,//显示单位维度

        tableData2:[],//部门数据
        orgList:[],

        treeData: [],//部门数据
        defaultProps: { // el-tree
            children: 'child',
            label: 'localOrgName'
        },
        tabLoading:true,
        opacity0:true,
    },
    created: function () {
        this.$nextTick(function () {
            //加载部门树
            $.ajax({
                type: "POST",
                url: baseURL + "org/tree",
                contentType: "application/json",
                success: function(result){

                    if(result.code === 0){
                        vm.treeData = result.orgList;
                        console.info("tree",vm.treeData)
                    }else{
                        alert(result.msg);
                    }
                }
            });
        })
        this.$nextTick(function () {
            this.redeptReload();
        })
    },


    methods: {
        // 查询
        onSubmit: function () {
            this.reload();
        },

        //部门
        redeptReload:function(){
            $.ajax({
                type: "POST",
                url: baseURL + "userIntegral/crdStatUser",
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

        // 部门点击事件
        handleNodeClick:function(data){
            vm.formInline.orgCode=data.localOrgCode;

            this.redeptReload();
        },

        toUserDiv:function(){
            vm.userStatDiv=true;
        },

        todeptDiv:function(){
            vm.userStatDiv=false;
            vm.obtainTableData();
        },

        //分页
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },

        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.redeptReload();
        },

        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
            vm.formInline.orgCode='320000000000',
            vm.reload();
        },


        obtainTableData:function(){
            $.ajax({
                type: "POST",
                url: baseURL + "userIntegral/crdStatOrg",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.orgList = result.orgList;

                        console.log(vm.orgList)
                        vm.tabTreeFn();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },


        //初始话表格部门树
        tabTreeFn :function (){
            this.showTableData();
            this.$nextTick(function (){
                var otb = null;

                otb = new oTreeTable('otb', document.getElementById('tb1'), {skin:'default',showIcon:false,openLevel:0});

                vm.tabLoading=false;
                setTimeout(function () {
                    vm.opacity0=false;
                });
            });
        },
        showTableData:function(){
            var map = new Map();
            for(var i = 0; i < vm.orgList.length; i++){
                var statInfo = {
                    id: "",
                    pid: "",
                    level: "",
                    data: {
                        orgName: ""
                    }
                }
                statInfo.id=vm.orgList[i].orgId;
                statInfo.pid = vm.orgList[i].parentId;
                statInfo.level=vm.orgList[i].orgLevel==null?0:vm.orgList[i].orgLevel;
                statInfo.data.orgName = vm.orgList[i].orgName;
                statInfo.data.orgAllCrd = vm.orgList[i].orgAllCrd==null?0:vm.orgList[i].orgAllCrd;
                statInfo.data.orgAllCrdRank = vm.orgList[i].orgAllCrdRank;
                map.set(vm.orgList[i].orgId,statInfo);
            }
            map.forEach(function(value, index, array) {
                vm.tableData2.push(value);
            });
            console.log(vm.tableData2);
        },



        //刷新页面
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "userIntegral/lst?isMp=true",
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
    },
    filters: {
        objTstring: function (value) {
            return JSON.stringify(value);
        }
    }
});
