/**
 * Author: MengyuWu
 * Date: 2018/12/7
 * Description:参数配置
 */
var i=null;
var menuId = $("#menuId").val();

//部门结构树
var dept_ztree;
var dept_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        formInline: { // 搜索表单

        },
        knowledgeIds:[],//法律类型
        tableData: [],//表格数据
        visible: false,
        specialKnowledgeId:"",
        test: {
            primaryCount:"",//初级数量
            intermediateCount:"",//中级数量
            seniorCount:"",//高级数量
            specialKnowledgeId:"",//知识点ID
            specialKnowledgeName:""//知识点ID
        },
        dept:{
            deptId:"",
        },
        rules: {//表单验证规则
            value: [
                {required: true, message: '请输入参数名', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入参数值', trigger: 'blur'},
                {max: 50, message: '最大长度50', trigger: 'blur'}
            ]
        },
        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
        delIdArr: [],//删除数据


        practiceConfiguration:{
            id:"",
            list:[],
            practiceName:"",
            questionType:""//试题类型
        },
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            this.reload();
            this.loadTopic();
            this.getDept();
        })
        //查询法律类型下拉框

    },
    methods: {
        //保存并跳转练习卷
        createPaper: function() {
            vm.practiceConfiguration.list=vm.tableData;

            $.ajax({
                type:"POST",
                url:baseURL+"pracConfiguration/saveAndShow",//保存主、从表配置
                contentType: "application/json",
                async:false,
                data: JSON.stringify(vm.practiceConfiguration),
                success:function(result){
                    console.info(result);
                    if(result.code===0){
                        vm.practiceConfiguration.id = result.id;//返回配置表id
                        vm.$alert('操作成功','提示',{
                            confirmButtonText:'确定',
                            callback:function(){
                                vm.practiceConfiguration = false;
                                vm.reload();
                            }
                            }
                        );
                    }else{
                        alert(result.msg);
                    }
                }
            });
            window.location.href = baseURL + "pracConfiguration/pracPaper?id="+vm.practiceConfiguration.id;
        },
        //生成练习卷名
        createName:function(){
          $.ajax({
              type:"GET",
              url:baseURL+"pracConfiguration/createPracName?prefix="+vm.practiceConfiguration.prefix,
              dataType:"json",
              success:function(result){
                  vm.practiceConfiguration.prefix = result.paperName.paperName;
              }
          });
        },

        getDept: function(){
            //加载部门树
            /*$.get(baseURL + "sys/dept/listAll", function(result){
                data_ztree = $.fn.zTree.init($("#deptTree"), dept_setting,result);
                //展开所有节点
                data_ztree.expandAll(true);
            });*/
            },
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
                //     var url = vm.test.id ? "/pracConfiguration/update" : "pracConfiguration/save";
                //     $.ajax({
                //         type: "POST",
                //         url: baseURL + url,
                //         contentType: "application/json",
                //         data: JSON.stringify(vm.test),
                //         success: function (result) {
                //             if (result.code === 0) {
                //                 vm.$alert('操作成功', '提示', {
                //                     confirmButtonText: '确定',
                //                     callback: function () {
                //                         vm.test = false;
                //                         vm.reload();
                //                     }
                //                 });
                //             } else {
                //                 alert(result.msg);
                //             }
                //         }
                //     });
                // } else {
                //     console.log('error submit!!');
                //     return false;
                    vm.tableData.push(vm.test);
                    vm.dialogConfig = false;

       }

            });
        },
        addConfig: function () {
            this.test = {
                specialKnowledgeId: '',
                primaryCount: '',
                intermediateCount: '',
                seniorCount: '',
                remark: ""
            };
            this.title = "新增";
            this.dialogConfig = true;
        },
        handleEdit: function (index,row) {
            console.info(row.id)
            this.title = "修改参数";
            this.dialogConfig = true;
            $.ajax({
                type: "GET",
                url: baseURL + 'pracConfiguration/info/'+row.id,
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.test = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleDel : function(index,row){
            console.log("删除前"+vm.tableData)
            vm.tableData.splice(index,1);
            console.log("删除前"+vm.tableData)
        },
        /*handleDel: function (index, row) {
            //this.delIdArr.push(row.id);
            console.log(row)
            console.log()
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {

                $.ajax({
                    type: "POST",
                    url: baseURL + "pracConfiguration/delete/"+row.id,
                    dataType: "json",
                    //data: JSON.stringify(row.id),
                    success: function (result) {
                        if (result.code === 0) {
                            vm.reload();
                        } else {
                            alert(result.msg);
                        }
                    }
                });

            }).catch(function () {
                vm.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },*/
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        reload: function () {
            // $.ajax({
            //     type: "POST",
            //     url: baseURL + "pracConfiguration/list",
            //     dataType: "json",
            //     data: vm.formInline,
            //     success: function (result) {
            //         if (result.code === 0) {
            //             vm.tableData = result.list;
            //             // vm.test.pageNo = result.pageNo;
            //             // vm.test.pageSize = result.pageSize;
            //             // vm.test.count = result.count;
            //         } else {
            //             alert(result.msg);
            //         }
            //     }
            // });
        },
        loadTopic: function(){
            $.ajax({
                type: "GET",
                url: baseURL + "topic/list",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.knowledgeIds=result.page.list;
                    } else {
                        alert(result.page);
                    }
                }
            });

        },
        changeLocationValue(val){
            //locations是v-for里面的也是datas里面的值
            console.log(val);
            console.log(this);
            let obj = {};
            obj = vm.knowledgeIds.find((item)=>{
                return item.id === val;
            });
            vm.test.specialKnowledgeName=obj.name;
        }
    }
});
