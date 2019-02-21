var vm = new Vue({
    el: '#app',
    data: {
        src: "../../statics/img/dog.jpg",
        info: {
            name: "", // 用户名
            number: "", // 警号
            id: "", // 身份证号
            department: "", // 部门名称
            oldPassword: "", // 旧密码
            newPassword: "", // 新密码
            newPassValidator: "" // 新密码确认
        },
        rules: {
            name: [
                { required: true, message: '用户名不能为空', trigger: 'blur'}
            ],
            number: [
                { required: true, message: '警号不能为空', trigger: 'blur'}
            ],
            id: [
                { required: true, message: '身份证不能为空', trigger: 'blur'},
                { type: 'number', message: '身份证必须为数字值' ,trigger: 'change'}
            ],
            department: [
                { required: true, message: '部门名称不能为空', trigger: 'blur'}
            ],
            oldPassword: [
                { required: true, message: '旧密码不能为空', trigger: 'blur'}
            ],
            newPassword: [
                { required: true, message: '新密码不能为空', trigger: 'blur'}
            ],
            newPassValidator: [
                { required: true, message: '请再输入一次新密码', trigger: 'blur'},
                { validator: this.checkPass, trigger: 'blur'}
            ]
        },
    },
    methods: {
        //获取url路径中参数
        toHome: function () {
            parent.location.reload()
        },
        submitForm: function(info) {
            this.checkPass();
            this.$refs.form.validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    alert('error submit!!');
                    return false;
                }
            });
        },
        changeAvatar: function () {
            this.src = "../../statics/img/new.png"
        },
        // 验证新旧密码是否相同
        checkPass: function (rule, value, callback) {
            console.log(value);
            if (value !== this.info.newPassword) {
                callback(new Error('两次输入密码不一致！'));
            } else {
                callback();
            }
        },
    },
    created: function(){
        /*this.$nextTick(function () {

            //学年目标
            $.ajax({
                type: "GET",
                url: baseURL + "schoolYearPlan/yearPlan",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {

                    } else {

                    }
                }
            });
        });*/
        // 模拟数据
        this.info.name = 'Sansa';
        this.info.number = 321;
        this.info.id = 123;
        this.info.department = '执勤';
    }
});