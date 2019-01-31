/**
 * Author: Zhujunwen
 * Date: 2018/12/20
 * Description:我的积分
 */


var vm = new Vue({
    el: '#app',
    data: {
        formInline: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0,
            startTime:"",
            endTime:"",
            type:"",
        },
        tableData: [],//表格数据
        info:{
            integralPoint: "" ,
            creditPoint: "",
            allItrRank: "",
            orgItrRank: "",
            allCdtRank: "",
            orgCdtRank: "",
        },
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "userIntegral/info",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.info = result.info;
                        vm.info.integralPoint=result.info.integralPoint==null?0:result.info.integralPoint;
                        vm.info.allItrRank=result.info.allItrRank==0?'1':result.info.allItrRank;
                        vm.info.orgItrRank=result.info.orgItrRank==0?'1':result.info.orgItrRank;
                        vm.info.creditPoint=result.info.creditPoint==null?0:result.info.creditPoint;
                        vm.info.allCdtRank=result.info.allCdtRank==0?'1':result.info.allCdtRank;
                        vm.info.orgCdtRank=result.info.orgCdtRank==0?'1':result.info.orgCdtRank;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.reload();
        })
    },
    methods: {
        onSubmit: function () {
            this.reload();
        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        formatterColumn: function(row, column){
            return  formatTime(row.collecttime);
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "integral/list?isMp=true",
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
        toHome: function () {
            parent.location.reload()
        }
    }
});


