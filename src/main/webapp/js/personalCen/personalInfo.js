
var vm = new Vue({
    el: '#app',
    data: function (){
        var validatePass= function (rule, value, callback) {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (vm.password.newPassValidator !== '') {
                    vm.$refs.password.validateField('newPassValidator');
                }
                callback();
            }
        };
        var validatePass2 = function (rule, value, callback) {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== vm.password.newPassword) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            src: baseURL+"/statics/img/police_head.png",
            importFileUrl:baseURL+"sys/upload",
            password:{
                oldPassword: "", // 旧密码
                newPassword: "", // 新密码
                newPassValidator: "", // 新密码确认
            },
            info: {},
            rules: {
                oldPassword: [
                    { required: true, message: '旧密码不能为空', trigger: 'blur'}
                ],
                newPassword: [
                    { required: true, message: '新密码不能为空', trigger: 'blur',validator: validatePass}
                ],
                newPassValidator: [
                    { required: true, message: '',trigger: 'blur',validator: validatePass2}
                ]
            }
        }
    },
    methods: {
        //获取url路径中参数
        toHome: function () {
            parent.location.reload()
        },
        // 保存
        submitForm: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    console.log(valid)
                    var url = "sys/udtPsw";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        dataType: "json",
                        data: vm.password,
                        success: function (result) {
                            if (result.code === 0) {
                                vm.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    // callback: function () {
                                    //     vm.dialogConfig = false;
                                    //     vm.reload();
                                    // }
                                });
                            } else {
                                alert("原密码错误,修改密码失败");
                            }
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        // changeAvatar: function () {
        //     this.src = "../../statics/img/new.png"
        // },
        //上传
        uploadSuccess: function(response, file, fileList) {
            debugger
            vm.info.photo=response.accessoryId;
            $.ajax({
                type: "POST",
                url: baseURL + "sys/updata",
                contentType: "application/json",
                data: JSON.stringify(vm.info),
                success: function (result) {
                    if (result.code === 0) {
                        vm.$alert('操作成功', '提示', {
                            confirmButtonText: '确定',
                            // callback: function () {
                            //     vm.dialogConfig = false;
                            //     vm.reload();
                            // }
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        beforeUpload: function () {
            //to do
        },

        uploadProcess: function () {

        },

    },
    created: function(){
        this.info=getUser();
    },
});