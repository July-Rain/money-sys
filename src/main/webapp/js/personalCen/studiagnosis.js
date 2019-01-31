/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            userName: '111',
            sum: '222',
            dateRange: { //学情看板日期区间
                startTime: '',
                endTime: '',
            },
            echartsTab: '',//学情看板分页
            seriesData:[],
            seriesNumData:[],//学习情况统计
            stuInfo:[{
                // countTime:0,//学习时长
                ratio:"0%",//超过百分比
                type:"",//类型
                name:"",//文字描述
                per: ''
            }],//学情统计数据
            options: [{
                value: '0',
                label: '本学年'
            }],
            selected: '',
            colorList: ['#52c9e7','#3e98e8','#81bdd8','#5ebd5c','#feae24','#f97a1f','#f26443','#b97deb','#7e72f2','#4f7ee9'],
            studyPerNames: [],
            studyPerData: [],//学习占比
            studyCountNames: [],
            studyCountData: []//学习次数
        }
    },
    created: function () {
        $.ajax({
            type: "POST",
            url: baseURL + "diagnosis/getStuDiagnosis",
            dataType: "json",
            // data: vm.dateRange,
            success: function (result) {
                if (result.code == 0) {
                    vm.seriesData = result.data;
                    vm.seriesNumData = result.stuCount;
                    vm.stuInfo=result.stuInfo;

                    vm.seriesData.map(function (value) {
                        vm.studyPerNames.push(value.name);
                        vm.studyPerData.push({
                            value: value.value,
                            name: value.name
                        });
                    });

                    vm.seriesNumData.map(function (value) {
                        vm.studyCountNames.push(value.name);
                        vm.studyCountData.push({
                            value: value.value,
                            name: value.name
                        });
                    })

                    vm.stuInfo.map(function (value) {
                        value.per = value.ratio.slice(0,value.ratio.length-1)
                    })
                    vm.$nextTick(function () {
                        // this.getStuDia();
                        vm.initPie1();
                        vm.initPieNum();
                    })
                } else {
                    alert(result.msg);
                }
            }
        });

    },
    methods: {
        // ECharts - 绘制图表
        echartsOption: function (myChart, option) {
            // this[chartName].clear()
            myChart.setOption(option)
            window.addEventListener('resize', function () {
                myChart.resize()
            })
        },
        toHome: function () {
            parent.location.reload()
        },
        //echarts
        initPie1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie1'));
            // 指定图表的配置项和数据
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                grid: {
                    left: '8%',
                    right: '8%',
                    bottom: '3%',
                    containLabel: true
                },
                legend: {
                    orient: 'vertical',
                    right: '5%',
                    y:'center',
                    itemWidth: 24,
                    itemHeight: 14,
                    textStyle: {
                        fontSize: 14
                    },
                    data:vm.studyPerNames
                },
                series: [
                    {
                        name:'学习时长统计',
                        type:'pie',
                        center: ['40%', '50%'],
                        radius: ['35%', '55%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '20',
                                    fontWeight: 'bold',
                                    color: '#333'
                                },
                                formatter:"{d}%\n{b}"
                            },

                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:vm.studyPerData,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList = vm.colorList;
                                    return colorList[params.dataIndex]
                                }
                            },
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            vm.echartsOption(myChart, option)
        },
        initPieNum: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie2'));
            // 指定图表的配置项和数据
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                grid: {
                    left: '8%',
                    right: '8%',
                    bottom: '3%',
                    containLabel: true
                },
                legend: {
                    orient: 'vertical',
                    y:'center',
                    right: '5%',
                    itemWidth: 24,
                    itemHeight: 14,
                    textStyle: {
                        fontSize: 14
                    },
                    data:vm.studyCountNames
                },
                series: [
                    {
                        name:'学习次数',
                        type:'pie',
                        center: ['40%', '50%'],
                        radius: ['35%', '55%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '20',
                                    fontWeight: 'bold',
                                    color: '#333'
                                },
                                formatter:"{c}\n{b}"
                            },

                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:vm.studyCountData,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList = vm.colorList;
                                    return colorList[params.dataIndex]
                                }
                            },
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            vm.echartsOption(myChart, option)
        }
    }
});
