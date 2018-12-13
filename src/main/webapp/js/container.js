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
            navData: [],//导航
            learnTasks: 4,//学习任务
            practiceTasks: 5,//练习任务
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
            rules: {//表单验证规则
                name: [
                    {required: true, message: '请输入活动名称', trigger: 'blur'},
                    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
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
            videoData: [
                {
                    videoUrl: 'http://vjs.zencdn.net/v/oceans.mp4',
                    lawType: '刑法',
                    title: '2019考研政治独家点题的打得2019考研政治独家点题',
                    dept: '公安部门',
                    date: '2018-10-23'
                }, {
                    videoUrl: 'http://vjs.zencdn.net/v/oceans.mp4',
                    lawType: '刑法',
                    title: '2019考研政治独家点题的打得2019考研政治独家点题',
                    dept: '公安部门',
                    date: '2018-10-23'
                }],
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
            deleteIds:[]
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
        })

        this.$nextTick(function () {
            this.initPie1()
            this.initBar1()
        })

        this.$nextTick(function () {
            //加载菜单
            $.ajax({
                type: "POST",
                url: baseURL + "menu/nav",
                contentType: "application/json",
                success: function (result) {

                    if (result.code === 0) {
                        vm.navData = result.menuList;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })

    },
    methods: {
        // 导航栏
        handleSelect: function (key, keyPath) {
            console.log(key, keyPath);
        },
        // 查询
        onSubmit: function () {
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
        initPie1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie1'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#2c343c',
                title: {
                    left: 'center',
                    top: 20,
                    textStyle: {
                        color: '#ccc'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                        colorLightness: [0, 1]
                    }
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '50%'],
                        data: [
                            {value: 335, name: '直接访问'},
                            {value: 310, name: '邮件营销'},
                            {value: 274, name: '联盟广告'},
                            {value: 235, name: '视频广告'},
                            {value: 400, name: '搜索引擎'}
                        ].sort(function (a, b) {
                            return a.value - b.value;
                        }),
                        roseType: 'radius',
                        label: {
                            normal: {
                                textStyle: {
                                    color: 'rgba(255, 255, 255, 0.3)'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                lineStyle: {
                                    color: 'rgba(255, 255, 255, 0.3)'
                                },
                                smooth: 0.2,
                                length: 10,
                                length2: 20
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#c23531',
                                shadowBlur: 200,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
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
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 0.3,
                                    [
                                        {offset: 0, color: '#1994d7'},
                                        {offset: 1, color: '#004b95'}
                                    ]
                                )
                            }
                        },
                        data: [32, 12, 22, 11, 13, 3, 23]
                    }
                ]
            }
            vm.echartsOption(myChart, option)
        },
        // 简易提示框
        open2: function () {
            var that = this
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
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
        inToCompetition: function () {
            //这是要跳转了
            //     parent.location.href =baseURL+item.list[0].url+"?id="+item.id;
            alert("wo jinlaile !!!!");
        },
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
    }
});