/**
 * Author: Zhujunwen
 * Date: 2018/12/13
 * Description:我的收藏 -重点课程
 */


var vm = new Vue({
    el: '#app',
    data: {
        navData: [],//导航
        formInline: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        tableData: [],//表格数据
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        visible: false,
        delIdArr: {
            id:""
        }//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
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
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        formatterColumn: function(row, column){
            return  formatTime(row.collecttime);
        },
        // handleEdit: function (index, row) {
        //     this.title = "修改参数";
        //     this.dialogConfig = true;
        //     $.ajax({
        //         type: "POST",
        //         url: baseURL + 'sysconfig/info?id=' + row.id,
        //         contentType: "application/json",
        //         success: function (result) {
        //             if (result.code === 0) {
        //                 vm.sysConfig = result.data;
        //             } else {
        //                 alert(result.msg);
        //             }
        //         }
        //     });
        // },
        handleStudy:function(id){

        },
        handleDel: function (id) {
            // vm.delIdArr.push(row.collectionId);
            vm.delIdArr.id=id;
            console.log(row);
            this.$confirm('此操作将取消收藏, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'coll/delColl',
                    async: true,
                    data: JSON.stringify(vm.delIdArr),
                    contentType: "application/json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/mycollection",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.result.list;
                        vm.formInline.currPage = result.result.currPage;
                        vm.formInline.pageSize = result.result.pageSize;
                        vm.formInline.totalCount = parseInt(result.result.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});


