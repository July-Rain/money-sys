/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        src:"",//路劲
        dialog2:false,//查看详情弹出框
        formInline: { // 搜索表单
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

        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },

        look: function(index,row)
        {
            console.info(index);
            console.info(row);
            vm.dialog2=true;
            vm.src=baseURL+"modules/competitionTongJi/peopleDetails.html?uid="+row.userid;
        },
        reload: function () {

            $.ajax({
                type: "POST",
                url: baseURL + "integral/userByDeptList",
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

        }
    }
});
