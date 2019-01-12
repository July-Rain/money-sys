
var vm = new Vue({
    el: '#app',
    data: {
            dataByNum:[],
            dataBySorce:[],
            user:{},

    },
    created: function () {
        this.$nextTick(function () {
           this.user=jsgetUser();
//先得到数据dataByNum
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



            //然后的数据dataBySorce【】
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

            this.reload();
        })
    },
    methods: {
        echartsOption: function (myChart, option) {
            // this[chartName].clear()
            myChart.setOption(option)
            window.addEventListener('resize', function () {
                myChart.resize()
            })
            // this[chartName].hideLoading() // Chart提示关闭
        },
        initPie1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie1'));
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
                        colorLightness: [0, 1]
                    }
                },
                series : [
                    {
                        name:'游戏类型',
                        type:'pie',
                        radius : [30, 150],
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
        initPie2: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie2'));
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
                        colorLightness: [0, 1]
                    }
                },
                series : [
                    {
                        name:'游戏类型',
                        type:'pie',
                        radius : [30, 150],
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
        reload: function () {
            this.initPie1()
            this.initPie2()
        }
    }
});
