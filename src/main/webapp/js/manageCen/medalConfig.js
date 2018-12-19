var vm = new Vue({
    el: '#app',
    data: {
        tableData: [
            {
                name: '初试牛刀',
                integral: '100',
                credit: '0'
            },{
                name: '熟能生巧',
                integral: '1000',
                credit: '10'
            },{
                name: '炉火纯青',
                integral: '5200',
                credit: '20'
            },{
                name: '登峰造极',
                integral: '10000',
                credit: '25'
            }],//表格数据
        currentPage4: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            name: '',
            integral: '',
            credit: '',

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

        }
    }

})