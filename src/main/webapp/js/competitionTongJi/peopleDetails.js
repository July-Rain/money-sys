/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */


var vm = new Vue({
    el: '#app',
    data: {
        userid:"",//人id
        tableData: [],//表格数据
        tableData2: [],//表格数据
        tableData3: [],//表格数据
        formInline: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        formInline2: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        formInline3: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },

        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },

        handleSizeChange2: function (val) {
            this.formInline2.pageSize = val;
            this.reload();
        },
        handleCurrentChange2: function (val) {
            this.formInline2.currPage = val;
            this.reload();
        },
        handleSizeChange3: function (val) {
            this.formInline3.pageSize = val;
            this.reload();
        },
        handleCurrentChange3: function (val) {
            this.formInline3.currPage = val;
            this.reload();
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {


            if(getUrlParam("uid")==null)
            {

                vm.userid=jsgetUser().id;//获得人id
                console.info( vm.userid);

            }
            else
            {

                vm.userid=getUrlParam("uid");//获得人id
                console.info(vm.userid);
            }

            $.ajax({
                type: "POST",
                url: baseURL + "competitionRecord/list?isMp=true&userid="+vm.userid,
                dataType: "json",
                async:false,
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


            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/list?userid="+vm.userid,
                dataType: "json",
                async:false,
                data: vm.formInline2,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData2 = result.page.list;
                        vm.formInline2.currPage = result.page.currPage;
                        vm.formInline2.pageSize = result.page.pageSize;
                        vm.formInline2.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/listByLeitai?userid="+vm.userid,
                dataType: "json",
                async:false,
                data: vm.formInline3,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData3 = result.page.list;
                        vm.formInline3.currPage = result.page.currPage;
                        vm.formInline3.pageSize = result.page.pageSize;
                        vm.formInline3.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
