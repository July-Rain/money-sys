/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            dateRange: { //学情看板日期区间
                startTime: '',
                endTime: '',
            },
            echartsTab: '',//学情看板分页
            seriesData:[{name: "视频中心", type: "videocen", countTime: 0, ratio: "0.00%"}],
            colorList: ['#52c9e7','#3e98e8','#81bdd8','#5ebd5c','#feae24','#f97a1f','#f26443','#b97deb','#7e72f2','#4f7ee9'],
            stuInfo:{
                stuCount:0,//学习时长
                ratio:"0%",//超过百分比
                type:"",//类型
                name:""//文字描述
            },//学情统计数据
            seriesNumData:[],//学习情况统计
            seriesExamData:[],
            seriesExamScoreData:[],
            overall: [],
            total: 0,
            right: 0,
            themeAnswerNum: [],
            themeRightNum: [],
            themeAnswerPieData: [],
            themeRightPieData: [],
            showNames: [],
            showValues:[],
            dataByNum:[],
            dataBySorce:[],
            dataByCorrect:[],
            user:{},
            showText:false,
            strokeWidth: 8
        }
    },
    created: function () {

        this.$nextTick(function () {
            this.getStuDia();
            this.getExamDia();
            this.getExamDiaScore();
            this.getGameDia();
            this.getPracDia();
            this.initPie1();
            this.initPieNum();
            this.initPieExam1();
            this.initPieExam2();
            this.initBar1();
            this.initPieGame1();
            this.initPieGame2();
            this.initPieGame3();
            this.initPiePrac1();
            this.initPiePrac2();
        });



    },
    methods: {
        colorPicker: function () {
            var colorList = this.colorList;
            return colorList[Math.round(Math.random()*this.colorList.length)];
        },
        onSubmit: function(){
            this.getStuDia();
            this.getExamDia();
            this.getExamDiaScore();
        },
        // 表单立即创建
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
        },
        //echarts
        initPie1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('canvas1'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',


                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                series : [
                    {
                        name:'学习时长统计',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.seriesData.sort(function (a, b) { return a.value - b.value; }),
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
                                color: this.colorPicker
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
        initPieNum: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('canvas2'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',


                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                color: ["#ff0000"],
                series : [
                    {
                        name:'学习情况统计',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.seriesNumData.sort(function (a, b) { return a.value - b.value; }),
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
                                color: this.colorPicker
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
        //echarts
        initPieExam1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pieExam1'));
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
                    }
                },
                series : [
                    {
                        name:'考试次数',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.seriesExamData.sort(function (a, b) { return a.value - b.value; }),
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
                                color: this.colorPicker
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
        initPieExam2: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pieExam2'));
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
                    }
                },
                series : [
                    {
                        name:'考试分数分布',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.seriesExamScoreData.sort(function (a, b) { return a.value - b.value; }),
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
                                color: this.colorPicker
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
                        data: vm.showNames,
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
                            formatter: '{value}'
                        },
                        interval: 25,
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
                                // 定制显示（按顺序）
                                color: this.colorPicker
                            },
                        },
                        data: vm.showValues
                    }
                ]
            }
            vm.echartsOption(myChart, option)
        },
        initPieGame1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pieGame1'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',

                //目前没有title
                // title: {
                //     text: '',
                //     left: 'center',
                //     top: 20,
                //     textStyle: {
                //         color: 'red'
                //     }
                // },

                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} 次"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                series : [
                    {
                        name:'游戏类型',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.dataByNum,
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
                                color: this.colorPicker
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
        initPieGame2: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pieGame2'));
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
                    formatter: "{a} <br/>{b} : {c} 分"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                series : [
                    {
                        name:'游戏类型',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.dataBySorce,
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
                                color: this.colorPicker
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
        initPieGame3: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pieGame3'));
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
                    formatter: "{a} <br/>{b} : {c} %"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                series : [
                    {
                        name:'游戏类型',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.dataByCorrect,
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
                                color: this.colorPicker
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
        initPiePrac1: function () {

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('piePrac1'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',

                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} 次"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                series : [
                    {
                        name:'主题回答数量(单位:题)',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.themeAnswerPieData,
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
                                color: this.colorPicker
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
        initPiePrac2: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('piePrac2'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',

                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} 分"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                    }
                },
                series : [
                    {
                        name:'主题回答正确数量(单位:题)',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:vm.themeRightPieData,
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
                                color: this.colorPicker
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
        getStuDia: function () {
            var loadInline={
                currPage: 1,
                pageSize: 2,
                totalCount:0};
            $.ajax({
                async:false,
                type: "POST",
                url: baseURL + "diagnosis/getStuDiagnosis",
                dataType: "json",
                data: vm.dateRange,
                success: function (result) {
                    if (result.code == 0) {
                        // vm.seriesData = result.data;
                        console.info(result.data)
                        vm.stuInfo=result.stuInfo;
                        vm.seriesNumData = result.stuCount;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getExamDia: function () {
            $.ajax({
                async:false,
                type: "POST",
                url: baseURL + "examstat/getDiaInfo",
                dataType: "json",
                data: vm.dateRange,
                success: function (result) {
                    if (result.code == 0) {
                        vm.seriesExamData = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getExamDiaScore: function () {
            $.ajax({
                async:false,
                type: "POST",
                url: baseURL + "examstat/DiaStat",
                dataType: "json",
                data: vm.dateRange,
                success: function (result) {
                    if (result.code == 0) {
                        vm.seriesExamScoreData = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getPracDia:function(){
            $.ajax({
                type: "GET",
                async:false,
                url: baseURL + "/analysis/exercise/count",
                contentType: "application/json",
                success: function (result) {
                    if (result.code === 0) {
                        vm.overall = result.map.overall;
                        vm.total = result.map.total;
                        vm.right = result.map.right;
                        vm.themeAnswerNum = result.map.themeAnswerNum;
                        vm.themeRightNum = result.map.themeRightNum;
                        for(var key in result.map.overall){
                            vm.showNames.push(key);
                            vm.showValues.push(result.map.overall[key]);
                        }
                        vm.themeAnswerPieData=[];
                        for(var info in vm.themeAnswerNum){
                            var answerinfo={
                                name:info,
                                value:vm.themeAnswerNum[info]
                            };
                            vm.themeAnswerPieData.push(answerinfo);
                        }
                        vm.themeRightPieData=[];
                        for(var info in vm.themeRightNum){
                            var answerinfo={
                                name:info,
                                value:vm.themeRightNum[info]
                            };
                            vm.themeRightPieData.push(answerinfo);
                        }

                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        getGameDia: function () {
                this.user=jsgetUser();
//先得到数据dataByNum---------------------------------------------------------------------------------------------------------------
                // 查次数//最笨的一个个查  没什么影响
                //       ------1查闯关次数
                $.ajax({
                    type: "POST",
                    url: baseURL + "competitionRecord/chuangguanCountByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataByNum.push({value:Number(data.count), name:'竞赛闯关'});
                    }
                });
                //       ------2查在线pk次数（包括随机，code码  组队）
                $.ajax({
                    type: "POST",
                    url: baseURL + "battlePlatform/PkCountByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataByNum.push({value:Number(data.count), name:'在线比武'});
                    }
                });
                $.ajax({
                    type: "POST",
                    url: baseURL + "battlePlatform/leitaiCountByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataByNum.push({value:Number(data.count), name:'擂台赛'});
                    }
                });



//然后的数据dataBySorce【】-----------------------------------------------------------------------------------------------------
                //       ------1查闯关分数
                $.ajax({
                    type: "POST",
                    url: baseURL + "integral/chuangguanCountByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataBySorce.push({value:Number(data.CheckpointSorce), name:'竞赛闯关'});
                    }
                });
                //       ------2查在线pk分数
                $.ajax({
                    type: "POST",
                    url: baseURL + "integral/pkByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataBySorce.push({value:Number(data.PkSorce), name:'在线比武'});
                    }
                });
                //       ------3查打擂台分数
                $.ajax({
                    type: "POST",
                    url: baseURL + "integral/leitaiByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataBySorce.push({value:Number(data.leitaiSorce), name:'擂台赛'});
                    }
                });
                //然后的数据dataByCorrect【】-----------------------------------------------------------------------------------------------------
                $.ajax({
                    type: "POST",
                    url: baseURL + "userQuestRecord/CheckpointByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataByCorrect.push({value:Number(data.CheckpointByUser), name:'竞赛闯关'});
                    }
                });

                $.ajax({
                    type: "POST",
                    url: baseURL + "userQuestRecord/OnlinByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataByCorrect.push({value:Number(data.OnlinByUser), name:'在线比武'});
                    }
                });
                $.ajax({
                    type: "POST",
                    url: baseURL + "userQuestRecord/leitaiByUser",
                    Type: "json",
                    data: {"uid":this.user.id},
                    async:false,
                    success: function (data) {
                        vm.dataByCorrect.push({value:Number(data.leitaiByUser), name:'擂台赛'});
                    }
                });
        },
        toHome: function () {
            parent.location.reload()
        }
    }
});
