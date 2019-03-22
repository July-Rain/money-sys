/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
        tableData: [],//评论数据
        form: {
            content: "",
            subordinateColumn: "st"
        },
        reply: {
            postId: "",
            content: "",
            replyObject: ""
        },
        info: {
            userName: "",
            release: "",
            comment: "",
            collection: ""
        },
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
        },
       /*     {
            name: "我的参与",
            alias: ""
        }*/
        ],
        rules:{
            content: [
                {required: true,trigger: 'blur'},
                {max: 500, message: '最大长度500', trigger: 'blur'}
            ],
            subordinateColumn:[
                {required: true, message: '请选择所属分类', trigger: 'blur'},
            ]
        },
        allInfo: true,
        myTableData : [],  //用于存放展示我的评论使用
        mineForm:{
            page: 1,
            limit: 10,
            count :0 ,
            subordinateColumn: '',
            type : '',
            status: '0'
        },
        mineMenuIndex : 0,
        formInline:{
            limit: 10,
            page: 1,
            count: 0,
            subordinateColumn: '',
            status:'0'
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
        mineHandleCurrentChange: function (val) {
            this.mineForm.page = val;
            this.mineInfoReload();
        },
        mineHandleSizeChange: function (val) {
            this.mineForm.limit = val;
            this.mineInfoReload();
        },
        getTypeNum : function(){
            $.ajax({
                type: "GET",
                url: baseURL + "post/count",
                contentType: "application/json",
                data: vm.form,
                success: function (result) {
                    if (result.code === 0) {
                        vm.info = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 发表帖子
        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "post/save",
                contentType: "application/json",
                data: JSON.stringify(vm.form),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('发布成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.reload();
                                vm.getTypeNum();
                                vm.form.content = '';
                            }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 发表评论
        saveReply: function (postId, content, replyObject) {
            vm.reply.content = vm.reply.content.replace(/(^\s*)|(\s*$)/g, "");
            if(vm.reply.content.length==0){
                vm.$alert('请输入评论内容', '操作失败', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return false;
                    }
                });
                return false;
            }
            var _reply = {
                postId: postId,
                replyObject: replyObject || null,
                content: vm.reply.content
            };
            vm.reply.postId = postId;
            vm.reply.replyObject = replyObject || null;
            $.ajax({
                type: "POST",
                url: baseURL + "reply/save",
                contentType: "application/json",
                data: JSON.stringify(_reply),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('评论成功', '提示', {
                            confirmButtonText: '确定',
                            callback: function () {
                                vm.reload();
                                vm.getTypeNum();
                            }
                        });
                    }
                    else if(result.code === 1){
                        alert("评论失败,包含敏感词"+result.word);
                    }
                    else {
                        alert(result.msg);
                    }
                }
            });
        },
        // 查询评论并展示
        showComment: function (id, index,taName) {
            $.ajax({
                type: "GET",
                url: baseURL + "reply/list",
                dataType: "json",
                data: {
                    postId: id
                },
                success: function (result) {
                    if (result.code === 0) {
                        vm[taName][index].child = result.page.list;
                        vm[taName][index].commentShow = true;
                        var arr = vm[taName];
                        vm[taName] = [];
                        vm[taName] = arr;
                    }else {
                        alert( result.msg );
                    }
                }
            })
        },
        collection: function (id, index,taName) {
            $.ajax({
                type: "POST",
                url: baseURL + 'post/collection/' + id,
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('收藏成功' ,'提示', {
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
        getDetail : function (type) {
            vm.mineForm.type = type;
            vm.allInfo = false;
            vm.mineInfoReload(vm.mineMenuIndex,'');
        },
        mineInfoReload:function(index,alias){
            if (index||index === 0) {
                vm.menuIndex = index;
            }
            vm.mineForm.subordinateColumn = alias;
            $.ajax({
                type: "GET",
                url: baseURL + "post/mineList",
                dataType: "json",
                data:vm.mineForm,
                success: function (result) {
                    if (result.code == 0) {
                        vm.myTableData = result.page.list;
                        vm.mineForm.mineFormPage = result.page.page;
                        vm.mineForm.mineFormLimit = result.page.pageSize;
                        vm.mineForm.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        showAllInfo : function () {
            vm.allInfo = true ;
            vm.reload();
        }
    }
});

