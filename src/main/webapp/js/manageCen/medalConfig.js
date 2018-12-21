var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//表格数据
        pageNo: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            name: '',
            integral: '',
            credit: '',
            badge:''
        },
        formLabelWidth: '200px',
        formLabelWidthS: '143px',

        fileList: []
    },
    mounted: function () {

    },
    methods:{
        indexMethod(index) {
            return index+1;
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
            return this.$confirm(`确定移除 ${ file.name }？`);
        },

        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        addMedal: function () {
            console.log(22)
            vm.dialogFormVisible = true
        },

        handleEdit: function () {

        },
        handleDel: function () {

        },
        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "medal/save",
                contentType: "application/json",
                data: JSON.stringify(vm.form),
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
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "medal/list",
                dataType: "json",
                data: {
                    pageNo: 1,
                    limit: 10
                },
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.form.pageNo = result.page.pageNo;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    created: function(){
        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "medal/list",
                contentType: "application/json",
                data:vm.form,
                success: function (result) {
                    if (result.code === 0) {
                        vm.tableData = result.page.list;
                        vm.form.pageNo = result.page.pageNo;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

})