var vm = new Vue({
    el: '#app',
    data: {
        overall: [],
        total: 0,
        right: 0,
        themeAnswerNum: [],
        themeRightNum: [],
        showNames: [],
        showValues:[]
    },
    methods: {
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
                                color: function(params) {
                                    var colorList = ["#36aae0","#feae24","#de6870","#1381e3","#81bdd8","#f97a1f","#5ebd5c"];
                                    return colorList[params.dataIndex]
                                }
                            },
                        },
                        data: vm.showValues
                    }
                ]
            }
            vm.echartsOption(myChart, option)
        },
        echartsOption: function (myChart, option) {
            myChart.setOption(option)
            window.addEventListener('resize', function () {
                myChart.resize()
            })
        },
        toHome: function () {
            parent.location.reload()
        }
    },
    created: function () {
        $.ajax({
            type: "GET",
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

                    vm.initBar1();

                } else {
                    alert(result.msg);
                }
            }
        });
    }
});