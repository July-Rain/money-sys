var menuId = getUrlParam('id');
var storage = window.sessionStorage;

var vm = new Vue({
    el: '#app',
    data: function(){
        //menuId:"",//菜单id
        var valiCheckNum = function (rule, value, callback) {
            if (vm.checkSetting.paperPerSetNum===''){
                callback(new Error('每套试卷阅卷人数不能为空'))
            }else{
                if (vm.checkSetting.paperPerSetNum =='2'&&vm.checkSetting.checkNum<=1){
                    callback(new Error('阅卷人数应大于等于每套试卷阅卷人数'))
                }else{
                    callback();
                }
            }
        };

       return {
           labelPosition: 'right',
           navData: [],//导航
           formInline: { // 搜索表单
               examName: '',
               startTime: '',
               endTime: '',
               page: 1,
               limit: 10,
               count: 0
           },
           checkSetting: {
               checkNum: '',
               paperPerSetNum: '',
               isAibitration: '',
               checkScoreDifference: ''
           },
           skOption: [],
           tableData: [],//表格数据
           visible: false,
           examConfig: {},
           checkSettingRules: {
               checkNum: [
                   {required: true, message: '请输入阅卷人数', trigger: 'blur'}
               ],
               paperPerSetNum: [
                   {required: true, trigger: 'blur', validator: valiCheckNum}
               ]
           },

           checkSettingDia: false,//阅卷设置弹出框
           viewCheckSettingDia: false,//查看阅卷信息弹出框
           title: "",//弹窗的名称
           delIdArr: [],//删除数据
           dialogAdd: false,
           dialogView: false,
           dialogEdit: false,
       }
    },

    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {
        timeout: function (time) {
            if(time === null){
                return true
            }else {
                var flag = new Date(Date.parse(time.replace(/-/g,  "/"))).getTime() > new Date().getTime();
                return flag;
            }
        },
        // 查询
        onSubmit: function () {
            this.reload();
        },
        handleSizeChange: function (val) {
            this.formInline.limit = val;
            this.reload();
        },

        handleCurrentChange: function (val) {
            this.formInline.page = val;
            this.reload();
        },
        addConfig: function () {
            // parent.location.href = baseURL + "modules/examCen/examConfig.html";
            storage.setItem("operate",0); //新增
            this.dialogAdd = true;
            document.getElementById("dialogAdd").contentWindow.location.reload(true);

        },
        resetForm: function (formName) {
            // this.$refs[formName].resetFields();
            vm.formInline={ // 搜索表单
                examName: '',
                    startTime: '',
                    endTime: '',
                    page: 1,
                    limit: 10,
                    count: 0
            };
            this.reload();
        },
        handleEdit: function (index, row) {
            storage.setItem("operate",2); //修改
            storage.setItem("examConId",row.id);
            this.dialogEdit = true;
            document.getElementById("dialogEdit").contentWindow.location.reload(true);

        },
        //序列号计算
        indexMethod: function (index) {
            return index + 1 + (vm.formInline.currPage - 1) * vm.formInline.pageSize;
        },
        handleDelExam: function (index, row) {
            var id = row.id;
            this.$confirm('此操作将永久删除该考试配置, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + 'exam/config/delete?id='+id,
                    async: true,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.code === 0) {
                            vm.reload();
                            vm.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            }).catch(function () {
                // vm.$message({
                //     type: 'info',
                //     message: '已取消删除'
                // });
            });

        },
        handleChange: function () {
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        closeCheckSettingDia: function () {
            this.checkSettingDia = false;
            vm.reload();
        },
        closeViewCheckSettingDia: function () {
            this.viewCheckSettingDia = false;
            vm.reload();
        },
        handleInfo: function(index, row){
            storage.setItem("operate",1); //查看
            storage.setItem("examConId",row.id);
            this.dialogView = true;
            document.getElementById("dialogView").contentWindow.location.reload(true);

        },

        saveCheckSet: function (formName){
            this.$refs[formName].validate(function (valid) {
                if (valid){
                    this.checkSettingDia = false;
                    console.info(vm.checkSetting);
                    $.ajax({
                        type: "POST",
                        url: baseURL + "exam/config/checkset",
                        dataType: "json",
                        data: JSON.stringify(vm.checkSetting),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code == 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: function () {
                                        vm.closeCheckSettingDia();
                                    }
                                });
                            } else {
                                alert(result.msg);
                            }
                        }
                    })
                }else{
                    return false
                }
            })
        },
        reload: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "exam/config/list",
                dataType: "json",
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.pageNo;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.count);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        generateOrViewCheck: function (index, row) {
            var checkPassword = row.checkPassword;
            if (checkPassword) {
                //查看
                this.getCheckSetting(row.id);
            } else {
                //生成
                console.info("b",this.$refs)

                this.checkSettingDia = true;
                setTimeout(function () {
                    vm.$refs["checkSetting"].resetFields();
                },0)

                console.info("c",this.$refs)
                vm.checkSetting.id = row.id;
            }
        },

        getCheckSetting : function(id){
            this.viewCheckSettingDia = true;
            $.ajax({
                type: "GET",
                url: baseURL + "exam/config/getCheckSetting?id="+id,
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.checkSetting = result.checkSetForm;
                    } else {
                        alert(result.msg);
                    }
                }
            })
        },
        toChild: function (item) {

            parent.location.href = baseURL + item.url + "?id=" + item.id;

        },
        toHome: function () {
            parent.location.reload()
        },
        copyCheckPass : function () {
            var Url2 = document.getElementById("checkPassword");
            Url2.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
        },
        copyAuditPass : function () {
            var Url2 = document.getElementById("auditCheckPass");
            Url2.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
        },
    }
});