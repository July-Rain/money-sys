/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
        tableData:[],//评论数据
        form:{
            content : "",
            subordinateColumn : ""
        },
        reply:{
            postId : "",
            content : "",
            replyObject : ""
        },
        info : {
            userName : "",
            release : "",
            comment :"",
            collection : ""
        },
        menuIndex: 0,
        navList: ["全部","试题","试题报错","学习","案例","建议","常见问题解答","我的参与"]
    },
    created: function (){

        this.$nextTick(function () {
            $.ajax({
                type: "GET",
                url: baseURL + "post/list",
                contentType: "application/json",
                data:vm.form,
                success: function (result) {
                    if (result.code === 0) {

                        vm.tableData = result.page.list;
                        console.info("create1",vm.tableData);
                        for (var i = 0; i < vm.tableData.length; i++) {
                            vm.tableData[i].commentShow = false
                        }
                        console.info("create2",vm.tableData);
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
                data:vm.form,
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
    methods:{
        // 发表帖子
        save: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "post/save",
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
        // 发表评论
        saveReply: function (postId, replyObject) {
            vm.reply.postId = postId;
            vm.reply.replyObject = replyObject||null;
            $.ajax({
                type: "POST",
                url: baseURL + "reply/save",
                contentType: "application/json",
                data: JSON.stringify(vm.reply),
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
        // 查询评论并展示
        showComment: function(id,index){
            $.ajax({
                type: "GET",
                url: baseURL + "reply/list",
                dataType: "json",
                data: {
                    postId:id
                },
                success: function (result) {
                    console.info("result",result);
                    vm.tableData[index].child = result.page.list;
                    vm.tableData[index].commentShow = true;
                    var arr = vm.tableData;
                    vm.tableData = [];
                    vm.tableData = arr;
                    console.info("showCom",vm.tableData)
                }
            })
        },
        collection: function(id,index){
            $.ajax({
                type: "POST",
                url: baseURL+'post/collection/'+id,
                dataType: "json",
                success:function (result) {
                    console.info("哈哈哈",result)
                }
            })
        },
        reload: function (index) {
            this.menuIndex = index;
            $.ajax({
                type: "GET",
                url: baseURL + "post/list",
                dataType: "json",
                data: {
                    page: 1,
                    limit: 10
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
