var menuId = getUrlParam('id');
var vm = new Vue({
    el: '#app',
    data: {
        overall: [],
        total: 0,
        right: 0,
        themeAnswerNum: [],
        themeRightNum: [],
        showNames: [],
        showValues:[],
        themeAnswerNames:[],
        themeAnswerData:[],
        themeRightNames:[],
        themeRightData:[],
        userName: '',
        sum: 0,
        options: [{
            value: '0',
            label: '本年度'
        }],
        selected: '0',
        colorList: ['#52c9e7','#3e98e8','#81bdd8','#5ebd5c','#feae24','#f97a1f','#f26443','#b97deb','#7e72f2','#4f7ee9'],
        breadArr: []//面包屑数据
    },
    methods: {
        initBar1: function () {
            var myChart = echarts.init(document.getElementById('bar1'));
            var option = {
                color: ['#3398DB'],
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '8%',
                    right: '8%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type: 'category',
                        data: vm.showNames,
                        axisTick: {
                            show: false,
                            alignWithLabel: true
                        },
                        nameTextStyle: {
                            color: '#333',
                            fontSize: '14'
                        },
                        axisLabel: {
                            color: '#333333',
                            fontSize: 14
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#c5c5c5'
                            }
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                          color: '#333333',
                            fontSize: 14
                        },
                        axisLine: {
                          lineStyle: {
                              color: '#c5c5c5'
                          }
                        },
                        axisTick: {
                            show: false
                        },
                        splitLine: {
                            show: false
                        }
                    }
                ],
                series : [
                    {
                        name:'答题数量',
                        type:'bar',
                        barWidth: '40%',
                        data:vm.showValues,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList = vm.colorList;
                                    return colorList[params.dataIndex]
                                }
                            },
                        },
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        }
                    }
                ]
            };
            vm.echartsOption(myChart, option)
        },
        initBar2: function (){
            var myChart = echarts.init(document.getElementById('bar2'));
            var option2 = {
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
                    orient: 'horizontal',
                    x: 'center',
                    y:'bottom',
                    left: '10%',
                    right: '10%',
                    itemWidth: 24,
                    itemHeight: 14,
                    textStyle: {
                        fontSize: 14
                    },
                    data:vm.themeAnswerNames
                },
                series: [
                    {
                        name:'题目占比',
                        type:'pie',
                        center: ['50%', '40%'],
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
                                formatter:"{c}题\n{b}"
                            },

                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:vm.themeAnswerData,
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
            vm.echartsOption(myChart, option2);

        },
        initBar3: function (){
            var myChart = echarts.init(document.getElementById('bar3'));
            var option3 = {
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
                    orient: 'horizontal',
                    x: 'center',
                    y:'bottom',
                    left: '10%',
                    right: '10%',
                    itemWidth: 24,
                    itemHeight: 14,
                    textStyle: {
                        fontSize: 14
                    },
                    data:vm.themeRightNames
                },
                series: [
                    {
                        name:'题目占比',
                        type:'pie',
                        center: ['50%', '40%'],
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
                                formatter:"{c}题\n{b}"
                            },

                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:vm.themeRightData,
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
            vm.echartsOption(myChart, option3)
        },
        echartsOption: function (myChart, option) {
            myChart.setOption(option)
            myChart.dispatchAction({type: 'highlight',seriesIndex: 0,dataIndex: 0})

            myChart.on('mouseover',function(v){
                if(v.dataIndex != 0){
                    myChart.dispatchAction({
                        type: 'downplay',
                        seriesIndex: 0,
                        dataIndex: 0
                    });
                }
            });
            myChart.on('mouseout',function(v){

                    myChart.dispatchAction({type: 'highlight',seriesIndex: 0,dataIndex: 0});

            });
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
                    vm.userName = result.map.userName;
                    vm.sum = result.map.sum;

                    for(var key in vm.overall){
                        vm.showNames.push(key);
                        vm.showValues.push(vm.overall[key]);
                    }

                    for(var key in vm.themeAnswerNum){
                        vm.themeAnswerNames.push(key);
                        vm.themeAnswerData.push({
                            value: vm.themeAnswerNum[key],
                            name: key
                        })
                    }
                    for(var key in vm.themeRightNum){
                        vm.themeRightNames.push(key);
                        vm.themeRightData.push({
                            value: vm.themeRightNum[key],
                            name: key
                        })
                    }

                    vm.initBar1();
                    vm.initBar2();
                    vm.initBar3();

                } else {
                    alert(result.msg);
                }
            }
        });
        this.breadArr=getBreadcrumb(menuId);
    }
});
