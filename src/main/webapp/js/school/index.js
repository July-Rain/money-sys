/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */

// var a = [{
//     b:[]
// },{
//     b:[]
// },{
//     b:[]
// }];
// var c = {
//     dd:111,
//     ee:222,
//     cc:[{rrr:"123"}]
// };
// a.map(function (value, index, array) {
//     value.b.push(c)
// })
// console.log(JSON.stringify(a));
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
        }, {
            name: "我的参与",
            alias: ""
        }],
        rules:{
            content: [
                {required: true, message: '请输入......', trigger: 'blur'},
                {max: 500, message: '最大长度500', trigger: 'blur'}
            ],
            subordinateColumn:[
                {required: true, message: '请选择所属分类', trigger: 'blur'},
            ]
        }
        // navList: ["全部", "试题", "试题报错", "学习", "案例", "建议", "常见问题解答", "我的参与"]
    },
    created: function () {

        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "post/list",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                        vm.tableData = result.page.list;
                        console.info("create1", vm.tableData);
                        for (var i = 0; i < vm.tableData.length; i++) {
                            vm.tableData[i].commentShow = false;
                        }
                        console.info("create2", vm.tableData);
                        vm.form.page = result.page.page;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });

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
        })
    },
    methods: {
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
                            //    vm.dialogFormVisible = false;
                                vm.reload();
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
                            //    vm.dialogFormVisible = false;
                                vm.reload();
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
        showComment: function (id, index) {
            $.ajax({
                type: "GET",
                url: baseURL + "reply/list",
                dataType: "json",
                data: {
                    postId: id
                },
                success: function (result) {
                    console.info("result", result);
                    vm.tableData[index].child = result.page.list;
                    vm.tableData[index].commentShow = true;
                    var arr = vm.tableData;
                    vm.tableData = [];
                    vm.tableData = arr;
                    console.info("showCom", vm.tableData)
                }
            })
        },
        collection: function (id, index) {
            $.ajax({
                type: "POST",
                url: baseURL + 'post/collection/' + id,
                dataType: "json",
                success: function (result) {
                    vm.reload();
                }
            })
        },
        report: function (id, index) {
            vm.$alert('举报id：' + id, '确认举报', {
                confirmButtonText: '确定',
                callback: function () {
                  //  vm.dialogFormVisible = false;
                    vm.reload();
                }
            });
        },
        reload: function (index, alias) {
            // index tab
            if (index) {
                this.menuIndex = index;
            }
            $.ajax({
                type: "GET",
                url: baseURL + "post/list",
                dataType: "json",
                data: {
                    page: 1,
                    limit: 10,
                    subordinateColumn: alias || null
                },
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.form.page = result.page.page;
                        vm.form.pageSize = result.page.pageSize;
                        vm.form.count = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});

