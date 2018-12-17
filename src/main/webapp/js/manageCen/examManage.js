var vm = new Vue({
    el: '#app',
    data: {
        tableData: [
            {
                info: '法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规',
                type: '选择题',
                itemclass: '11',
                answer: 'a',
                project:'a:111 b:111;c:222;d:222',
                difficulty: 'bb,',
                class: 'cals',
                partof: 'qq',
                active: '1'
            },{
                info: '法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规',
                type: '选择题',
                itemclass: '11',
                answer: 'a',
                project:'a:111;b:111;c:222;d:222',
                difficulty: 'bb,',
                class: 'cals',
                partof: 'qq',
                active: '1'
            },{
                info: '法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规法律法规',
                type: '选择题',
                itemclass: '11',
                answer: 'a',
                project:'a:111;b:111;c:222;d:222',
                difficulty: 'bb,',
                class: 'cals',
                partof: 'qq',
                active: '1'
            }],//表格数据
        currentPage4: 1,//分页：当前页
        dialogFormVisible: false,
        form: {
            class: '',
            type: '',
            title: '',
            info: '',
            belong: '',
            level: '',
            basis: '',
            judgement: '',
            radio: '',
            check: [],
            notes: '',
            upload: '',
            disable: '',
            remarks: '',
            person: '',
            department: ''
        },
        formLabelWidth: '120px',
        fileList: []
    },
    mounted: function () {

    },
    methods:{
        layFn(){
            $(".el-dialog").css("height","auto")
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
        addExam: function () {
            console.log(22)
            vm.dialogFormVisible = true
        },
        batchImport: function () {
            
        },
        downloadTemp: function () {
            
        },
        handleWatch: function () {

        },
        handleEdit: function () {

        },
        handleDel: function () {

        }
    }

})