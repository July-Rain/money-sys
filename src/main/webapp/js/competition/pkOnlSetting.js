/**
 * Author:
 * Date: 2018/12/7
 * Description:
 */


var vm = new Vue({
    el: '#app',
    data: {

        tableData: [],//表格数据
        daguannum:'',
        daguanArray: [],
        formInline: { // 搜索表单
            currPage: 1,
            pageSize: 10,
            totalCount: 0
        },
        rules: {//表单验证规则
             topicNum: [
                 {required: true, message: '请选择', trigger: 'blur'},
             ],
            uniformRules: [
                {required: true, message: '请选择', trigger: 'blur'},

            ],
            answerTime: [
                {required: true, message: '请选择', trigger: 'blur'},

            ],
        },
        dialogConfig: false,//table弹出框可见性
        dialog2:false,//查看小关详情弹出框
        title: "",//弹窗的名称

 //关卡集合
        guankaList:[],
//专项知识
        zhuanxiangzhishiList:[],
//试题类型
        itemtype:[],
//试题难度
        itemjibie:[],
 //在线比武实体
        competitionOnline:
       {
           id:'',
           battleTopicSettingList:[],
       },
//选择题量数据
        checkNum:[],
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        // 保存和修改
        saveOrUpdate: function (formName) {
            this.$refs[formName].validate(function (valid) {

                    if (valid) {
                        //保存前默认先删除一波
                        $.ajax({
                            type: "POST",
                            url: baseURL + 'competitionOnline/deleteAll',
                            async:false,
                            dataType: "json",
                            success: function (result) {
                                var url ="competitionOnline/save";
                                $.ajax({
                                    type: "POST",
                                    url: baseURL + url,
                                    contentType: "application/json",
                                    data: JSON.stringify(vm.competitionOnline),
                                    success: function (result) {
                                        if (result.code === 0) {
                                            vm.$alert('操作成功', '提示', {
                                                confirmButtonText: '确定',
                                                callback: function () {
                                                    vm.dialogConfig = false;
                                                    vm.reload();
                                                }
                                            });
                                        } else {
                                            alert(result.msg);
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else {
                        return false;
                    }
            });



        },
        add: function () {

            // 每次进来先制空
            vm.checkNum=[];
            vm.competitionOnline=
                {
                    id:'',
                    battleTopicSettingList:[],
                };
            //每次打开添加按钮时候 取后台获取 字典表中大关和小关数量的配置
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"LITCHECKNUM",Parentcode:"99994"},
                success: function (result) {
                    if (result.code == 0) {
                        console.info(result);
                        console.info(result.dictlist[0].value);
                        //区间也就2个值 也排序过了
                        var checknum1 =  Number(result.dictlist[0].value);
                        var checknum2 =  Number(result.dictlist[1].value);

                        for(var i=checknum1;i<=checknum2;i++)
                        {
                            vm.checkNum.push({
                               value: i,
                               label: i
                           })
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });

            //专项知识
            $.ajax({
                type: "POST",
                url: baseURL + "recruitConfiguration/findAllTopic",
                dataType: "json",
                async:false,
                success: function (result) {

                    vm.zhuanxiangzhishiList=result.data;
                }
            });

            // 试题类型
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"QUESTION_TYPE",Parentcode:"0"},
                success: function (result) {
                    // vm.itemtype=result.dictlist;
                    vm.itemtype=[];
                    for(var tt=0;tt<result.dictlist.length;tt++)
                    {
                        if((result.dictlist[tt].value=="单选题")||(result.dictlist[tt].value=="判断题") )
                        {
                            vm.itemtype.push(result.dictlist[tt]);
                        }
                    }
                }
            });

            //试题难度
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"QUESTION_DIFF",Parentcode:"0"},
                success: function (result) {
                    vm.itemjibie=result.dictlist;
                }
            });
            $.ajax({
                type: "POST",
                url: baseURL + 'competitionOnline/list',
                dataType: "json",
                async:false,
                success: function (result) {
                    if(result.page.list.length!="0")
                    {

                        vm.$alert('请先删除原有配置，再添加新的配置');
                        vm.dialogConfig = false;
                    }
                    else
                    {
                        vm.title = "新增在线比武配置";
                        vm.dialogConfig = true;
                    }

                }
            });

        },
        onselect:function (num) {//点完选择选择题量事件
            vm.competitionOnline=
            {
                id:'',
                topicNum:num,
                battleTopicSettingList:[
                    {id:'',},
                ],
            };
        },
        onselectuniformRules:function (num) {//点完是否统一配置触发事件

                  var topicnum=  Number(num.topicNum);  //题目数量

                  var uniformRules=num.uniformRules  //是否统一配置

                  if(!topicnum)
                  {
                      vm.competitionOnline.uniformRules='';
                      vm.$message({
                          type: 'info',
                          message: '请先设置题目数量!'
                      });
                      return;
                  }
                  //不管选的是是还是否  都先清除一遍  在加
                    num.battleTopicSettingList=[];
                  if(uniformRules=="0")//0不是统一配置
                  {
                        for(var p=0;p<topicnum;p++)
                        {
                            num.battleTopicSettingList.push
                            (
                                {id:'',howManySmall:p+1,questionDifficulty:'10001',knowledgeId:'1072387696247562241',questionType:'10004'}

                            )
                        }

                  }
                  else if(uniformRules=="1")//1是  是统一
                  {
                      num.battleTopicSettingList.push
                      (
                          {id:'',questionDifficulty:'10001',knowledgeId:'1072387696247562241',questionType:'10004'}
                      )
                  }

        },
        look: function (index, row) {
            vm.title = "查看题目配置";
            vm.guankaList =[];//每次打开前 都删一边
            $.ajax({
                type: "POST",
                url: baseURL + 'competitionOnline/getSonList',
                dataType: "json",
                async:false,
                data:{"id": row.id},
                success: function (result) {


                    if (result.code === 0) {
                        // 返回的是一个集合   不想做成在翻页   直接做成循环table
                        vm.guankaList = result.data;
                        vm.dialog2 = true;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        del: function () {
            if(vm.tableData.length=='0'){
                vm.$message({
                    type: 'info',
                    message: '占无数据!'
                });
                return;
            }
            this.$confirm('此操作将永久删除在线比武配置, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'competitionOnline/deleteAll',
                    async:false,
                    dataType: "json",
                    success: function (result) {
                        vm.reload();
                        vm.$message({
                            type: 'success',
                            message: '删除成功!'
                        });

                    }
                });
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        update: function () {

            if(vm.tableData.length=='0'){
                vm.$message({
                    type: 'info',
                    message: '占无数据!'
                });
                return;
            }
            // 每次进来先制空
            vm.checkNum=[];
            vm.competitionOnline=[];
                // {
                //     id:'',
                //     battleTopicSettingList:[],
                // };
            //每次打开添加按钮时候 取后台获取 字典表中大关和小关数量的配置
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"LITCHECKNUM",Parentcode:"99994"},
                success: function (result) {
                    if (result.code == 0) {

                        //区间也就2个值 也排序过了
                        var checknum1 =  Number(result.dictlist[0].value);
                        var checknum2 =  Number(result.dictlist[1].value);

                        for(var i=checknum1;i<=checknum2;i++)
                        {
                            vm.checkNum.push({
                                value: i,
                                label: i
                            })
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
            //专项知识
            $.ajax({
                type: "POST",
                url: baseURL + "recruitConfiguration/findAllTopic",
                dataType: "json",
                async:false,
                success: function (result) {

                    vm.zhuanxiangzhishiList=result.data;
                }
            });

            // 试题类型
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"QUESTION_TYPE",Parentcode:"0"},
                success: function (result) {

                     vm.itemtype=[];
                    for(var tt=0;tt<result.dictlist.length;tt++)
                    {
                        if((result.dictlist[tt].value=="单选题")||(result.dictlist[tt].value=="判断题") )
                        {
                            vm.itemtype.push(result.dictlist[tt]);
                        }
                    }
                }
            });

            //试题难度
            $.ajax({
                type: "POST",
                url: baseURL + "dict/getByTypeAndParentcode",
                dataType: "json",
                async:false,
                data: {type:"QUESTION_DIFF",Parentcode:"0"},
                success: function (result) {
                    vm.itemjibie=result.dictlist;
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + 'competitionOnline/findAll',
                dataType: "json",
                async:false,
                success: function (result) {
                    console.info(result);
                    console.info(result.data.length);
                    if (result.code === 0) {
                        if(result.data.length=="0")
                        {
                            vm.$alert('暂无数据');

                        }
                        else
                        {
                            vm.title = "编辑";

                            vm.competitionOnline = result.data;
                            vm.dialogConfig = true;
                            // vm.daguannum = result.data.length;
                        }

                    } else {
                        alert(result.msg);
                    }
                }
            });

            console.info(vm.competitionOnline);
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        closedialog2: function () {
            vm.dialog2 = false;
            // vm.reload();
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "competitionOnline/list",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        toHome: function () {
            parent.location.reload()
        },
        toMain: function () {
            window.location.href = baseURL + 'modules/manageCen/compSetting.html'
        }
    }
});
