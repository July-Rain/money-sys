var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        page: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            titleName: '',
            integral: '',
            credit: '',
            badge:'',
            page: 1,
            limit: 10,
            count: 0
        },
        formLabelWidth: '120px',
        isReceive : [],
        user:{},

    },
    mounted: function () {

    },
    methods: {
        layFn() {
            $(".el-dialog").css("height", "auto")
        },
        // 文件上传
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },

        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        addExam: function () {
            console.log(22)
            vm.dialogFormVisible = true
        },
        batchImport: function () {

        },
        downloadTemp: function () {

        },
        handleWatch: function (index, row) {
            console.table({
                "row": row
            })
        },
        handleEdit: function () {

        },
        receive: function (index,row) {
            console.log(index,row)
            $.ajax({
                type: "POST",
                url: baseURL + "medal/saveMedal/" + row.id,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.dialogFormVisible = false;
                                vm.reload();
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        wear: function (index,row) {
            console.info(index);
            console.info(row.badge);
            vm.user.myMedal=row.badge;

            //修改这个人身上背的勋章
              $.ajax({
                type: "GET",
                url: baseURL + "sys/updateBymyMedal?myMedal="+row.badge,
                  dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == 0) {
                        alert("佩戴成功");
                         vm.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });

            // $.ajax({
            //     type: "POST",
            //     url: baseURL + "medal/wear/" + row.id,
            //     contentType: "application/json",
            //     success: function (result) {
            //         if (result.code === 0) {
            //             vm.$alert('操作成功', '提示', {
            //                 confirmButtonText: '确定',
            //                 callback: function () {
            //                     vm.dialogFormVisible = false;
            //                     vm.reload();
            //                 }
            //             });
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });
        },
        reload: function () {


            //得到当前人的信息   主要是要勋章
            $.ajax({
                type: "GET",
                url: baseURL + "sys/getUser2",
                dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == 0) {

                        vm.user=result.info;

                    } else {
                        alert(result.msg);
                    }
                }
            });



            //得到当前人的积分 与学分
            $.ajax({
                type: "GET",
                url: baseURL + "userIntegral/info",
                dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == 0) {
                        vm.form.jifen=result.info.integralPoint;
                        vm.form.xuefen=result.info.creditPoint;
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "GET",
                url: baseURL + "medal/list",
                contentType: "application/json",
                data: vm.form,
                async:false,
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.form.page = result.page.page;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });

        },
        toHome: function () {
            parent.location.reload()
        }
    },
    created: function () {
        this.$nextTick(function () {

            vm.reload();
            // $.ajax({
            //     type: "GET",
            //     url: baseURL + "medal/myMedal",
            //     dataType: "json",
            //     success: function (result) {
            //         if (result.code == 0) {
            //             vm.isReceive = result.list;
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });

        })
    }

})