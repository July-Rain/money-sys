var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//评论数据
        menuIndex: 0,
        navList: [{
            name: "全部",
            alias: ""
        }, {
            name: "试题",
            alias: "st"
        }, {
            name: "试题报错",
            alias: "stbc"
        }, {
            name: "学习",
            alias: "xx"
        }, {
            name: "案例",
            alias: "al"
        },{
            name: "建议",
            alias: "jy"
        }, {
            name: "常见问题解答",
            alias: "cjwtjd"
        }
        ],
        mineMenuIndex : 0,
        formInline:{
            limit: 10,
            page: 1,
            count: 0,
            subordinateColumn: '',
            status:'1'
        }
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
            this.getTypeNum();
        })
    },
    methods: {
        handleCurrentChange: function (val) {
            this.formInline.page = val;
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.limit = val;
            this.reload();
        },
        report: function (id, index ,taName ) {
            $.ajax({
                type: "POST",
                url: baseURL + 'post/report/' + id,
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('举报成功，审核通过前将不会显示此条内容' ,'提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                if (taName ==='tableData' ){
                                    vm.reload();
                                }else {
                                    vm.mineInfoReload()
                                }

                            }
                        });
                    }else{
                        vm.$alert(result.msg ,'提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.reload();
                            }
                        });
                    }

                }
            })
        },
        changeSubordinate : function(index, alias){
            //切换tab页
            // index tab
            if (index||index === 0) {
                vm.menuIndex = index;
            }
            vm.formInline.subordinateColumn = alias;
            vm.formInline.limit = 10;
            vm.formInline.page = 1;
            vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "post/list",
                dataType: "json",
                data:vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.page = result.page.currPage;
                        vm.formInline.limit = result.page.pageSize;
                        vm.formInline.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        audit: function (id,index,status) {
            
        }
    }
});

