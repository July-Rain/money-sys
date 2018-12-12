var menuId = $("#menuId").val();
var vm = new Vue({
    el: '#app',
    data: {
        //menuId:"",//菜单id
        navData: [],//导航
        details: [],
        formInline: { // 搜索表单
            value: '',
            name: '',
            status: "",
            pageNo: 1,
            pageSize: 1,
            limit: 10,
            count: 0,
            defaultPageSize: 10,
            options: []
        },
        tableData: [],//表格数据
        visible: false,
        examConfig: {
            id: '',
            examName: '',
            examType: '',
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
        randomQuesModal: false,//table弹出框可见性
        autonomyQuesModal: false,
        title: "",//弹窗的名称
        delIdArr: []//删除数据
    },
    created: function () {
        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav?id=" + menuId,
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.reload();
        })

        this.$nextTick(function () {
            //下拉框选项
            $.ajax({
                type: "GET",
                url: baseURL + "topic/list?pageSize=-1",
                contentType: "application/json",
                success: function (result) {
                    vm.formInline.options = result.page.list;
                }
            });
            this.reload();
        })
    },
    methods: {
        // 查询
        onSubmit: function () {
            this.reload();
        },
        /*handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },*/
        randomQues : function(){
            this.title = "随机出题配置";
            this.randomQuesModal = true;
            alert(111);
        },
        // 保存和修改
        saveOrUpdate: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    var url = vm.sysConfig.id ? "sysconfig/update" : "sysconfig/insert";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysConfig),
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
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        loadData(oid) {
            this.axios.get('/ServiceAction/com.eweaver.workflow.workflow.servlet.WorkflowRelateAction?action=load&oid=' + oid).then(response => {
                this.details = response.data.detail;
                this.flowName = response.data.flowName;
            }).catch(err => {
                console.log(err)
            })
        },
        handleAdd() {
            this.details.push({
                id: null,
                workflowid: null,
                workflowName: null,
                formType: null,
                formid: null,
                formName: null,
                fieldid: null,
                fieldName: null,
                condition: null
            });
            alert(111);
        },
        handleDelete() {
            this.multipleSelection.forEach(element => {
                if (element.id && this.deleteIds.indexOf(element.id) === -1) {
                    this.deleteIds.push(element.id);
                }
                this.details.splice(this.details.indexOf(element), 1)
            });
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleWorkflowBrowserClick(index) {
            this.$store.commit('switch_workflowDialog');
            this.currentIndex = index;
        },
        handleFormBrowserClick(index) {
            this.$store.commit('switch_FormDialog', {
                workflowid: this.details[index].workflowid,
                formType: this.details[index].formType
            });
            this.currentIndex = index;
        },
        handleFieldBrowserClick(index) {
            this.$store.commit('switch_FieldDialog', this.details[index].formid);
            this.currentIndex = index;
        },
        handleSave() {
            if (this.details.length > 0 || this.deleteIds.length > 0) {
                let params = new URLSearchParams();
                params.append('workflowid', this.$store.state.globalStore.workflowid);
                params.append('list', JSON.stringify(this.details));
                params.append('deleteList', JSON.stringify(this.deleteIds));
                this.axios.post('/ServiceAction/com.eweaver.workflow.workflow.servlet.WorkflowRelateAction?action=save', params,
                    {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}}).then(response => {
                    this.$message.info("主人，保存成功！");
                }).catch(error => {
                    console.log(error);
                })
            } else {
                this.showMessage()
            }
        },
        showMessage() {
            this.$message.error('主人，小的发现无数据可提交哦');
        }
    },
        watch: {
            workflowRowData: function (newData, oldData) {
                this.details[this.currentIndex].workflowid = newData.workflowid;
                this.details[this.currentIndex].workflowName = newData.workflowName;
            },
            formRowData: function (newData, oldData) {
                this.details[this.currentIndex].formid = newData.formid;
                this.details[this.currentIndex].formName = newData.formName;
            },
            fieldRowData: function (newData, oldData) {
                this.details[this.currentIndex].fieldid = newData.fieldid;
                this.details[this.currentIndex].fieldName = newData.fieldName;
            },
            oid: function (newData, oldData) {
                this.loadData(newData);
            },
        closeDia: function () {
            this.randomQuesModal = false;
            vm.reload();
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
                        vm.formInline.totalCount = parseInt(result.page.count)+1;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
