// import "${rc.contextPath}/statics/plugins/video/video.min.js";
/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: function () {
        var validateDate = function (rule, value, callback) {
            if (vm.ruleForm.endDate === '') {
                callback()
            } else {
                if (!(vm.ruleForm.startDate < vm.ruleForm.endDate)) {
                    callback(new Error('结束日期必须大于开始日期'))
                } else {
                    callback()
                }
            }
        }
        return {
            videoDataId: ["SM20190130160129526544", "SM20190126164415860356"],
            navData: [],//导航
            learnTasks: 4,//学习任务
            practiceTasks: 5,//练习任务
            indexs: ['A', 'B', 'C', 'D', 'E', 'F'],
            data: [
                {// el-tree数据
                    label: '一级 1',
                    children: [{
                        label: '二级 1-1',
                        children: [{
                            label: '三级 1-1-1'
                        }]
                    }]
                }, {
                    label: '一级 2',
                    children: [{
                        label: '二级 2-1',
                        children: [{
                            label: '三级 2-1-1'
                        }]
                    }, {
                        label: '二级 2-2',
                        children: [{
                            label: '三级 2-2-1'
                        }]
                    }]
                }, {
                    label: '一级 3',
                    children: [{
                        label: '二级 3-1',
                        children: [{
                            label: '三级 3-1-1'
                        }]
                    }, {
                        label: '二级 3-2',
                        children: [{
                            label: '三级 3-2-1'
                        }]
                    }]
                }],
            defaultProps: { // el-tree
                children: 'children',
                label: 'label'
            },
            formInline: { // 搜索表单
                user: '',
                region: '',
                chooseDate: '',
                value11: '',
                options: [
                    { // 多选下拉框
                        value: '选项1',
                        label: '黄金糕'
                    }, {
                        value: '选项2',
                        label: '双皮奶'
                    }, {
                        value: '选项3',
                        label: '蚵仔煎'
                    }, {
                        value: '选项4',
                        label: '龙须面'
                    }, {
                        value: '选项5',
                        label: '北京烤鸭'
                    }]
            },
            value1: '', // 日期选择
            value11: [], // 多选下拉框值
            tableData: [
                {
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                }, {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                }, {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1516 弄'
                }],//表格数据
            currentPage4: 4,//分页：当前页
            visible: false,
            ruleForm: {
                name: '',
                phone: '',
                region: '',
                date1: '',
                date2: '',
                value6: '',
                startDate: '',//开始日期
                endDate: '',//结束日期
                delivery: false,//配送状态
                type: [],//活动性质
                resource: '',//特殊资源
                desc: '',//活动形式
                visible: false,
                parentId: 0,
                parentName: '',
                menuId: '',
                menuList: [
                    {// el-tree数据
                        label: '一级 1',
                        children: [{
                            label: '二级 1-1',
                            children: [{
                                label: '三级 1-1-1'
                            }]
                        }]
                    }, {
                        label: '一级 2',
                        children: [{
                            label: '二级 2-1',
                            children: [{
                                label: '三级 2-1-1'
                            }]
                        }, {
                            label: '二级 2-2',
                            children: [{
                                label: '三级 2-2-1'
                            }]
                        }]
                    }, {
                        label: '一级 3',
                        children: [{
                            label: '二级 3-1',
                            children: [{
                                label: '三级 3-1-1'
                            }]
                        }, {
                            label: '二级 3-2',
                            children: [{
                                label: '三级 3-2-1'
                            }]
                        }]
                    }],
                menuListTreeProps: {
                    children: 'children',
                    label: 'label'
                }
            },
            textmag:"",
            myAnswer:"",//我的答案ID
            questionForm: {},
            answers:[],//我的答案
            //接收每日一题
            currentConfig:{
                id:"",
            },//当前每日一题配置
            singleAnswer:{
                id:""
            },
            qtOption: [],//题目类型
            showDetail:true,//展示细节
            answerShow: false,//是否显示答案
            legalBasisShow:false,//显示法律依据
            radioData: "",
            radio_disabled: false,
            checkboxdisabled:false,//多选情况下 复选框 禁用 选项
            dialogyes: false,//答对提示
            examList:[],
            chuangguan:"",
            onlPkSum:"",
            leitaiCount:"",
            everyDay:"",
            rules: {//表单验证规则
                name: [
                    {required: true, message: '请输入活动名称', trigger: 'blur'},
                    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                ],
                phone: [
                    {required: true, message: '不能为空', trigger: 'blur'},
                    { pattern: regularExp("phone"), message: '正则不匹配' }
                ],
                region: [
                    {required: true, message: '请选择活动区域', trigger: 'change'}
                ],
                date1: [
                    {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
                ],
                date2: [
                    {type: 'date', required: true, message: '请选择时间', trigger: 'change'}
                ],
                type: [
                    {type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change'}
                ],
                resource: [
                    {required: true, message: '请选择活动资源', trigger: 'change'}
                ],
                desc: [
                    {required: true, message: '请填写活动形式', trigger: 'blur'}
                ],
                endDate: [
                    {validator: validateDate, trigger: 'blur'}
                ]
            },
            videoData: [],//视频教程
            videoDataId: [],
            newsData: [
                {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: true,
                    date: '[18/10/09]'
                }, {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: true,
                    date: '[18/10/09]'
                }, {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: true,
                    date: '[18/10/09]'
                }, {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: false,
                    date: '[18/10/09]'
                }, {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: true,
                    date: '[18/10/09]'
                }, {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: false,
                    date: '[18/10/09]'
                }, {
                    title: '智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面智能管理下全面打造案管智能管理下全面打造案管智能管理下全面打造案管全面',
                    tipShow: false,
                    date: '[18/10/09]'
                }],//新法速递数据
            dateRange: { //学情看板日期区间
                startDate: '',
                endDate: '',
            },
            echartsTab: '',//学情看板分页
            popover://简易提示框
                {
                    title: '标题',
                    content: '这是一段内容,这是一段内容,这是一段内容,这是一段内容。'
                },
            dialogTableVisible: false,//table弹出框可见性
            dialogFormVisible: false,//form弹出框可见性
            formLabelWidth: '120px',
            tab: 'learn',
            data11:[],//题型data
            typeOptions: [
                {
                    value: 0,
                    label: '单选'
                },
                {
                    value: 1,
                    label: '多选'
                },
                {
                    value: 0,
                    label: '填空'
                },
                {
                    value: 1,
                    label: '主观'
                }
            ],
            dataListSelections: [],//选中行
            deleteIds:[],
            yearPlan:{
                credit : 0,//目标学分
                integral : 0,//目标积分
                credited: 0,
                integraled: 0
            },
            playTime:0,//播放时间
            oldTime:0,//原来播放时间
            seriesData:[],
            stuInfo:{
                stuCount:0,//学习时长
                ratio:"0%",//超过百分比
                type:"",//类型
                name:""//文字描述
            },//学情统计数据
            popoverTitle: "考试提示标题",
            popoverContent: "考试提示内容",

            // 播放器
        }
    },
    created: function () {
        console.log("container加载")
        this.$nextTick(function () {
            // 视频播放
            // var myPlayer = videojs('my-video');
            // videojs("my-video").ready(function () {
            //     myPlayer = this;
            //     myPlayer.play();
            // });
            this.loadVideo();
            this.loadNewsData();
            this.loadTasks();
        });

        this.$nextTick(function () {
            this.getStuDia();
            this.initPie1();
            this.initBar1();
            // this.initPlayer();
        });
        this.$nextTick(function () {

            //学年目标
            $.ajax({
                type: "GET",
                url: baseURL + "schoolYearPlan/yearPlan",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.yearPlan.credit = parseInt(result.data.credit);
                        vm.yearPlan.integral = parseInt(result.data.integral);
                    } else {
                        alert(result.msg);
                    }
                }
            });

            //学年目标
            $.ajax({
                type: "GET",
                url: baseURL + "userIntegral/info",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.yearPlan.credited = result.info.creditPoint;
                        vm.yearPlan.integraled = result.info.integralPoint;

                    } else {
                         alert(result.msg);

                    }
                }
            });
        });
        //竞赛中心
        this.$nextTick(function () {
            this.getChuangguan();
            this.onlPk();
            this.leitai();
        });

        //每日一题
        this.$nextTick(function () {

            $.ajax({
                type: "GET",
                url: baseURL + "dailyQuestion/getQuestion",
                data:{
                    date: '-1'
                },
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionForm = result.question;
                        if(result.question != null){

                            var userAnswer = vm.questionForm.userAnswer;
                            if(userAnswer == null || userAnswer == ''){
                                vm.everyDay="未完成";
                                vm.radio_disabled = false;
                            } else {
                                vm.radio_disabled = true;
                                var type = vm.questionForm.questionType;
                                if(type == '10005'){
                                    // 多选题
                                    vm.answers = userAnswer.split(',');
                                } else {
                                    vm.answers = userAnswer;
                                }
                                vm.showAnswer = true;
                                vm.everyDay="已完成";
                            }

                        } else {
                            vm.everyDay="暂无题目";
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        });
        //我的考试
        this.$nextTick(function () {
            $.ajax({
                type:"POST",
                url: baseURL + "user/exam/list",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                       vm.examList = result.page.list;
                    }
                }
            })
        })
    },
    mounted: function () {
      this.initPlayer();
    },
    methods: {
        getTsxx: function(userAnswer, rightAnswer){
            // userAnswer、rightAnswer可能多选
            var userArr = new Array();
            var rightArr = new Array();
            userArr = userAnswer.split(',');
            rightArr = rightAnswer.split(',');
            var rightAnswerStr = '';
            var userAnswerStr = '';

            for(var i=0; i<vm.questionForm.answer.length; i++){
                if(rightArr.indexOf(vm.questionForm.answer[i].id) > -1){
                    rightAnswerStr += vm.indexs[i];
                }
                if(userArr.indexOf(vm.questionForm.answer[i].id) > -1){
                    userAnswerStr += vm.indexs[i];
                }
            }

            if(rightAnswerStr == userAnswerStr){
                vm.questionForm.right = 1;
            }else {
                vm.questionForm.right = 0;
            }
            vm.radio_disabled = true;

        },
        commit: function () {
            // 提交
            if(vm.answers.length == 0){
                vm.$alert('请选择您的答案！', '提示', {
                    confirmButtonText: '确定',
                    callback: function () {
                        return;
                    }
                });
            }
            var lx = typeof vm.answers;
            var str = '';
            if(lx == 'string'){
                str = vm.answers;
            } else {
                str = vm.answers.join(',');
            }
            vm.getTsxx(str, vm.questionForm.answerId);
            var obj = {
                qId: vm.questionForm.id,
                answer: str,
                right: vm.questionForm.right,
                themeName: vm.questionForm.themeName
            };

            $.ajax({
                type: "POST",
                url: baseURL + "dailyQuestion/saveAnswer",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function (result) {
                    if (result.code === 0) {
                        vm.questionForm.recordId = result.recordId;
                        vm.questionForm.isCollect = 0;
                        vm.everyDay = '已完成';
                    } else {
                        alert(result.msg);
                    }
                }
            });


        },
        initPlayer: function () {
            var that = this;
            window.onload = function () {
                console.log('视频id');
                var options = {
                    controls: true,
                    bigPlayButton: true,
                    controlBar:{
                        //设置是否显示该组件
                        playToggle: false,
                        remainingTimeDisplay: true,
                        fullscreenToggle: false,
                        volumePanel: false
                    },
                };
                that.videoDataId.forEach(function (val, index) {
                    var myPlayer = videojs(val, options);
                    var bigButton = document.getElementsByClassName('vjs-big-play-button')[index];
                    bigButton.style.outline = 'none';
                    myPlayer.on('play', function () {
                        bigButton.style.display = 'none';
                    });
                    myPlayer.on('pause', function () {
                        bigButton.style.display = 'block';
                    });
                })
            }
        },
        onPlay:function (id,accId) {
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/updateCount?stuId="+id+"&stuType=stu_video&stuFrom=videocen",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        onPause: function (id,event) {
            var el = event.currentTarget;
            var temp =0;
            //var playTime= $(el).currentTime;
            vm.oldTime=vm.playTime;

            vm.playTime= el.currentTime;

            temp=vm.playTime-vm.oldTime;

            var finishFlag =false;
            if(el.currentTime == el.duration ){
                finishFlag=true;
            }
            //获取当前选择对象

            //媒介因素暂停事件
            //请求后台记录观看时长
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/countTime?stuId="+id+"&stuFrom=videocen&playTime="+temp+"&finishFlag="+finishFlag,
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        // 导航栏
        handleSelect: function (key, keyPath) {
            console.log(key, keyPath);
        },
        // 查询
        onSubmit: function () {
            this.getStuDia();
            console.log('submit!');
            console.log(vm.formInline);
        },
        // el-tree节点点击事件
        handleNodeClick: function (data) {
            console.log(data);
        },
        handleSizeChange: function (val) {
            console.log('每页' + val + '条');
        },
        handleCurrentChange: function (val) {
            console.log('当前页:' + val);
        },
        // 表单立即创建
        submitForm: function (formName) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    alert('submit!');
                    console.log(vm.ruleForm)//多的表单
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        // 表单重置
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        },
        // ECharts - 绘制图表
        echartsOption: function (myChart, option) {
            // this[chartName].clear()
            myChart.setOption(option)
            window.addEventListener('resize', function () {
                myChart.resize()
            })
            // this[chartName].hideLoading() // Chart提示关闭
        },
        //echarts
        initPie1: function (_data) {
            console.info("data",_data,typeof _data);
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie1'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',
                title: {
                    text: '',
                    left: 'center',
                    top: 20,
                    textStyle: {
                        color: '#ccc'
                    }
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                        // colorLightness: [0, 1]
                    }
                },
                series : [
                    {
                        name:'学习时长统计',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data: vm.seriesData.sort(function (a, b) { return a.value - b.value; }),
                        roseType: 'radius',
                        label: {
                            normal: {
                                textStyle: {
                                    color: '#666'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                lineStyle: {
                                    color: '#666'
                                },
                                smooth: 0.2,
                                length: 10,
                                length2: 20
                            }
                        },
                        itemStyle: {
                            normal: {
                                // 定制显示（按顺序）
                                color: function(params) {
                                    var colorList = ["#146084","#1978a5","#de676f","#feaf25","#219dd9","#5ebd5c","#55b6e5"];
                                    return colorList[params.dataIndex]
                                }
                            },
                        },

                        animationType: 'scale',
                        animationEasing: 'elasticOut',
                        animationDelay: function (idx) {
                            return Math.random() * 200;
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            vm.echartsOption(myChart, option)
        },
        initBar1: function () {
            var myChart = echarts.init(document.getElementById('bar1'));
            var option = {
                color: ['#3398DB'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        // },
                        // formatter: function (value) {
                        //     return value[0].axisValue + ':<br/>' + num[value[0].dataIndex] + '人（' + value[0].data + '%）'
                    }
                },
                grid: {
                    left: '6%',
                    right: '7%',
                    bottom: '5%',
                    top: '10%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: ['刑法', '宪法', '民商法', '行政法', '社会法', '经济法', '诉讼及非讼程序法'],
                        axisTick: {
                            alignWithLabel: true
                        },
                        // 设置坐标轴字体颜色和宽度
                        axisLine: {
                            lineStyle: {
                                color: ['#abafb2']
                            }
                        },
                        axisLabel: {
                            interval: 0,
                            // rotate: 20,
                            textStyle: {
                                fontSize: 12 // 让字体变大
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} %'
                        },
                        interval: 25,
                        max: 100,
                        min: 0,
                        // 设置坐标轴字体颜色和宽度
                        axisLine: {
                            lineStyle: {
                                width: 0,
                                color: ['#abafb2']
                            }
                        }
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        barWidth: 25,
                        // itemStyle: {
                        //     normal: {
                        //         color: new echarts.graphic.LinearGradient(
                        //             0, 0, 0, 0.3,
                        //             [
                        //                 {offset: 0, color: '#1994d7'},
                        //                 {offset: 1, color: '#004b95'}
                        //             ]
                        //         )
                        //     }
                        // },
                        itemStyle: {
                            normal: {
                                // 定制显示（按顺序）
                                color: function(params) {
                                    var colorList = ["#36aae0","#feae24","#de6870","#1381e3","#81bdd8","#f97a1f","#5ebd5c"];
                                    return colorList[params.dataIndex]
                                }
                            },
                        },
                        data: [82, 52, 62, 41, 93, 63, 43]
                    }
                ]
            }
            vm.echartsOption(myChart, option)
        },
        // 简易提示框
        open2: function () {
            var that = this
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                that.$message.success('恭喜你，这是一条成功消息')
            }).catch(function () {
                that.$message.error('错了哦，这是一条错误消息')
            });
        },
        toChild: function (item) {
            if (item.list.length == 0) {
                alert("暂无子菜单");
            } else {
                parent.location.href = baseURL + item.list[0].url + "?id=" + item.id;
            }
        },
        // inToCompetition: function () {
        //     //这是要跳转了
        //     //     parent.location.href =baseURL+item.list[0].url+"?id="+item.id;
        //     alert("wo jinlaile !!!!");
        // },
        // 菜单树选中
        menuListTreeCurrentChangeHandle: function (data) {
            // this.ruleForm.parentId = data.menuId
            vm.ruleForm.parentName = data.label
            vm.ruleForm.visible = false
        },
        // 菜单树设置当前选中节点
        // menuListTreeSetCurrentNode: function () {
        //     this.$refs.menuListTree.setCurrentKey(this.ruleForm.parentId)
        //     this.ruleForm.parentName = (this.$refs.menuListTree.getCurrentNode() || {})['name']
        // }，
        // 多选
        selectionChangeHandle: function (val) {
            this.dataListSelections = val
        },
        //新增行
        handleAdd: function () {
            this.data11.push({
                type: null,
                num: null,
                score: null
            })
        },
        handleDelete: function () {
            alert('批量删除')
        },
        loadVideo: function () {//加载视频课程
            var loadInline={
                stuType:"video",
                currPage: 1,
                pageSize: 2,
                totalCount:0,
                stuLawName:"",
                stuIssuer:"",
                startTime:"",
                endTime:""};
            $.ajax({
                type: "POST",
                url: baseURL + "stumedia/list?isMp=true",
                dataType: "json",
                data: loadInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.videoData = result.page.list;
                        console.log(vm.videoData);
                        for(var i=0;i<vm.videoData.length;i++){
                            vm.videoDataId.push(vm.videoData[i].id);
                            vm.videoData[i].contentUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].comContent;
                            if(vm.videoData[i].videoPicAcc){
                                vm.videoData[i].videoPicAccUrl=baseURL+"sys/download?accessoryId="+vm.videoData[i].videoPicAcc;
                            }else{
                                vm.videoData[i].videoPicAccUrl="http://temp.im/640x260";
                            }
                        }
                        console.info("videoData",vm.videoData)
                        console.info("videoDataId",vm.videoDataId)
                    } else {
                        alert(result.msg);
                    }
                }
            });

        },
        loadNewsData: function () {//新法速递
            var loadInline={
                currPage: 1,
                pageSize: 8,
                totalCount:0,};
            $.ajax({
                type: "POST",
                url: baseURL + "classdesic/list?isMp=true",
                dataType: "json",
                data: loadInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.newsData = result.page.list;
                    } else {
                        alert(result.msg);
                    }
                }
            });

        },
        goLaw: function (id) {
            //查看详情
            //记录学习记录
            this.insertRecord(id);
        },
        insertRecord: function (id) {
            //插入学习记录
            //请求后台修改播放量 记录学习记录
            $.ajax({
                type: "POST",
                url: baseURL +  "sturecord/insertRecord?stuId="+id+"&stuType=law"+"&stuFrom=law",
                contentType: "application/json",
                success: function(result){
                    if(result.code === 0){
                        //vm.treeData = result.classifyList;
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        loadTasks: function () {//学习任务
            $.ajax({
                type: "POST",
                url: baseURL + "learntasks/getCount",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.learnTasks = result.learnTasks;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            var loadInline={
                limit: 10,
                page: 1,
                count: 0};
            //练习任务
            $.ajax({
                type: "GET",
                url: baseURL + "exercise/task/list",
                dataType: "json",
                data:loadInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.practiceTasks = result.page.count;
                    } else {
                        alert(result.msg);
                    }
                }
            });

        },
        getStuDia: function () {
            vm.seriesData=[];
            $.ajax({
                async:false,
                type: "POST",
                url: baseURL + "diagnosis/getStuDiagnosis",
                dataType: "json",
                data: vm.dateRange,
                success: function (result) {
                    if (result.code == 0) {
                        for(var i = 0;i<result.data.length;i++){
                            var _info = {
                                value: result.data[i].value?result.data[i].value:0,
                                name: result.data[i].name
                            };
                            vm.seriesData.push(_info)
                        }
                        vm.stuInfo=result.stuInfo;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getChuangguan: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "competitionRecord/chuangGuanRankingByUser",//闯关排名
                Type: "json",
                async:false,
                success: function (data) {
                    if(data.competitionRecord){
                        vm.chuangguan="第"+data.competitionRecord.howBig+"大关，第"+data.competitionRecord.howLit+"小关";
                    }

                }
            });
        },
        onlPk: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/pkSum",//闯关排名
                Type: "json",
                async:false,
                success: function (data) {
                    vm.onlPkSum=data.pkSum;
                }
            });
        },
        leitai: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/leitaiWinCount",//闯关排名
                Type: "json",
                async:false,
                success: function (data) {
                    vm.leitaiCount=data.leitaiWinCount;
                }
            });
        },
        everyTi:function() {
            if(vm.questionForm != null){
                vm.dialogyes= true;
            }
        },
        onSubmit:function(answerId) {
            console.info(vm.answers)
            var answer = answerId.split(',');
            console.info(answer);
            //先判断个数一不一样
            if (answer.length != vm.answers.length) {
                //个数不一样，说明错了
                vm.questionError();
                console.info("出错")
                return;
            } else if (answer.length == vm.answers.length) {
                for (var i = 0; i < answer.length; i++) {
                    if ($.inArray(answer[i], vm.answers) == "-1") {
                        //如果-1 就是不包含  就错了
                        vm.questionError();
                        console.info("出错")
                        alert("答错了");
                        return;
                    }
                }

                //对的
                vm.questionYes();
                console.info("对的")
                alert("答对了");
            }
        },

        //单选题的答案结果
        radioCheck: function (id, answerId, typeName) {
            vm.radio_disabled = true;
            // var answer = vm.answers[0];
            // alert(vm.answers);
            // alert("我选的"+id);
            // alert("正确的"+answerId);
            //如果答对了
            if (id == answerId) {
                vm.questionYes();
                alert("答对了");
            } else {
                vm.questionError();
                alert("答错了");
            }

        },
        questionError:function()
        {
            //答错了  0分
            vm.recordScore('0');
            vm.oryesorno();//答完题目入库保存记录
        },
        questionYes:function(){
            //答对了 才会 添加分数啊
            vm.recordScore(vm.curretConfig.obtainPoint);
            vm.oryesorno();//答完题目入库保存记录
        },
        //不管答对答错 都要入库方法
        oryesorno:function () {
            vm.checkboxdisabled=true;
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/saveQuestion?myanswer='+vm.answers,
                contentType: "application/json",
                async:false,
                data: JSON.stringify(vm.questionForm),
                success: function (result) {

                }
            });

        },

        recordScore: function (sorce) {
            $.ajax({
                type: "POST",
                url: baseURL + 'dailyQuestion/recordScore',
                dataType: "json",
                async: false,
                data: {"sorce": sorce},
                success: function (result) {

                }
            });
        },
        startExam : function (list) {
            var parentWin = window.parent;
            var id = list.id;
            var examStatus = list.examStatus;
            var userExamId = list.userExamId==null?'':list.userExamId;
            parentWin.document.getElementById("container").src
                = 'modules/examCen/testPaper.html?id='+id+'&examStatus='+examStatus+'&userExamId='+userExamId;
        },
        viewExam : function (list) {
            var parentWin = window.parent;
            var id = list.id;
            var examStatus = list.examStatus;
            var userExamId = list.userExamId==null?'':list.userExamId;
            parentWin.document.getElementById("container").src
                = 'modules/personalCen/myScoreReport.html?id='+id+'&examStatus='+examStatus+'&userExamId='+userExamId;
        },
        carouselChange:function (index) {
            vm.popoverTitle = "试卷名称："+vm.examList[index].examName;
            var msg = '';
            if (vm.examList[index].isMustTest==='10029'){
                msg = '是';
            }else if(vm.examList[index].isMustTest==='10030'){
                msg = '否';
            }
            vm.popoverContent ="是否必考："+ msg;
            vm.popoverContent = vm.popoverContent +"组考单位："+ vm.examList[index].organizedOrgCode;
        }
    },
    computed: {
        integralPer: function(){
            // alert(this.yearPlan.integraled/this.yearPlan.integral*100||0)
            if(this.yearPlan.integral){
                return this.yearPlan.integraled/this.yearPlan.integral*100||0
            }

        },
        creditPer: function () {
            // alert(this.yearPlan.credited/this.yearPlan.credit*100||0)
            if(this.yearPlan.credit){
                return this.yearPlan.credited/this.yearPlan.credit*100||0
            }

        }
    }
});
