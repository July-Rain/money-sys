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
            limit: 10,
            page: 1,
            count: 0,
        },
        formLabelWidth: '120px',
        isReceive : [],
        user:{},

    },
    mounted: function () {

    },
    methods: {
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.form.page - 1) * vm.form.limit;
        },
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

        handleSizeChange: function (event) {
            vm.form.limit = event;
            vm.reload();
        },
        handleCurChange: function (event) {
            vm.form.page = event;
            vm.reload();
        },
        addExam: function () {

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

                        vm.$message({
                            message: '保存成功',
                            type: 'success'
                        });
                        vm.dialogFormVisible = false;
                        vm.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        wear: function (index,row) {



            vm.user.myMedal=row.badge;

            //修改这个人身上背的勋章
              $.ajax({
                type: "GET",
                // url: baseURL + "sys/updateBymyMedal?myMedal="+row.badge,
                  url: baseURL + "sys/updateBymyMedal?myMedal="+row.id,
                  dataType: "json",
                async:false,
                success: function (result) {
                    if (result.code == 0) {
                        vm.$message({
                            message: '佩戴成功',
                            type: 'success'
                        });


                        parent.window.vm.iconString=row.badge;


                         vm.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });

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
                        vm.form.page = result.page.pageNo;
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

        })
    }

})