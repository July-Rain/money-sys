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
    },
    mounted: function () {

    },
    methods:{
        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        addExam: function () {
            console.log(111)
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