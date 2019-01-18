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
            seriesScoreData:[],//按照分数统计
            deptStatData:[],//部门统计数据
            tabLoading:true,
            opacity0:true,
            deptTempData:[],//部门临时数据
            activeName:"person"

        }
    },
    created: function () {

        this.$nextTick(function () {
            this.getExamDia();
            this.getExamDiaScore();
            this.initPie1();
            this.initPie2();
            //this.loadTabTreeData();
        })


    },
    methods: {
        onSubmit: function(){
            this.getStuDia();
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
                        data:vm.seriesScoreData.sort(function (a, b) { return a.value - b.value; }),
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
        getExamDia: function () {
            $.ajax({
                async:false,
                type: "POST",
                url: baseURL + "examstat/getDiaInfo",
                dataType: "json",
                data: vm.dateRange,
                success: function (result) {
                    if (result.code == 0) {
                        vm.seriesData = result.data;
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
                        vm.seriesScoreData = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        //初始话表格部门树
        loadTabTreeData :function (){
            $.ajax({
                type: "POST",
                url: baseURL + "examstat/orgExamStat",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.deptTempData = result.data;

                        console.log(vm.deptTempData)
                        vm.loadTabTreeFn();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        loadTabTreeFn :function (){
            this.showTableData();
            this.$nextTick(function (){
                var otb = null;

                otb = new oTreeTable('otb', document.getElementById('tb1'), {skin:'default',showIcon:false,openLevel:0});

                vm.tabLoading=false;
                setTimeout(function () {
                    vm.opacity0=false;
                });
            });
        },
        showTableData: function () {
            var map = new Map();
            for(var i = 0; i < vm.deptTempData.length; i++){
                var statInfo = {
                    id: "",
                    pid: "",
                    level: "",
                    data: vm.deptTempData[i]
                }
                statInfo.id=vm.deptTempData[i].orgId;
                statInfo.pid = vm.deptTempData[i].parentId;
                statInfo.level=vm.deptTempData[i].orgLevel==null?0:vm.deptTempData[i].orgLevel;
                map.set(vm.deptTempData[i].orgId,statInfo);
            }
            map.forEach(function(value, index, array) {
                vm.deptStatData.push(value);
            });
            console.log(vm.deptStatData);
        },
        handleTabClick:function (tab,event) {
            debugger
            if(tab.name=='org'){
                this.loadTabTreeData();
                console.log("加载数据");
            }else{
                this.getStuDia();
            }
        }
    },
    filters: {
        objTstring: function (value) {
            return JSON.stringify(value);
        }
    }
});
