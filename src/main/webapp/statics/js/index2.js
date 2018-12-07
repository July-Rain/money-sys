var vm = new Vue({
    el: '#app',
    data: {
        message: 'Hellow',
        todos: [
            { text: '学习 JavaScript' },
            { text: '学习 Vue' },
            { text: '整个牛项目' }
        ],
        tableData: []
    },
    methods: {
        vMessage: function () {
            this.message = this.message.split('').reverse().join('')
            vm.message = vm.message.split('').reverse().join('')
            console.log("触发一次vMessage",vm.message)
            this.todos.push({
                text: 'NEW add'
            })
        }
    },
    created: function () {
        console.log(this.message)
        console.log($("#app"))
        this.tableData = [{
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
        }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1517 弄'
        }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
        }, {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1516 弄'
        }]
        this.$nextTick(function () {
            console.log($("#app"))
        })
    }
})