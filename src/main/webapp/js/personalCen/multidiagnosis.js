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
            seriesData:[],
            stuInfo:{
                stuCount:0,//学习时长
                ratio:"0%",//超过百分比
                type:"",//类型
                name:""//文字描述
            },//学情统计数据
            seriesNumData:[],//学习情况统计

        }
    },
    created: function () {

        this.$nextTick(function () {
            this.getStuDia();
            this.initPie1()
            this.initPieNum()
        })


    },
    methods: {
        onSubmit: function(){
            this.getStuDia();
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
                        colorLightness: [0, 1]
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
        initPieNum: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pienum'));
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
                        colorLightness: [0, 1]
                    }
                },
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
        /*initBar1: function () {
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
        },*/
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
                        vm.seriesData = result.data;
                        vm.stuInfo=result.stuInfo;
                        vm.seriesNumData = result.stuCount;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
