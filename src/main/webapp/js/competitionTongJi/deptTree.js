/**
 * Author: crain
 * Date: 2018/12/3
 * Description:
 */
var vm = new Vue({
    el: '#app',
    data: {
            dataByNum:[],
            dataBySorce:[],
            dataByCorrect:[],
            deptcode:"",


            tableData: [],//表格数据
            src:"",//路劲
            dialog2:false,//查看详情弹出框
            formInline: { // 搜索表单
                currPage: 1,
                pageSize: 10,
                totalCount: 0
            },
            dialogConfig: false,//table弹出框可见性
            title: "",//弹窗的名称



            dateRange: { //学情看板日期区间
                startTime: '',
                endTime: '',
                currPage: 1,
                pageSize: 10,
                totalCount:0
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
            deptStatData:[],//部门统计数据
            tabLoading:true,
            opacity0:true,
            deptTempData:[],//部门临时数据
            activeName:"person",
    },
    created: function () {
        this.$nextTick(function () {
            this.reload();
        })
    },
    methods: {
        one:function () {

                vm.deptcode=jsgetUser().orgCode;
//先得到数据dataByNum---------------------------------------------------------------------------------------------------------------
                // 查次数//最笨的一个个查  没什么影响
                //       ------1查闯关次数
                $.ajax({
                    type: "POST",
                    url: baseURL + "competitionRecord/chuangguanCountBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataByNum.push({value:Number(data.count), name:'竞赛闯关'});
                    }
                });
                //       ------2查在线pk次数（包括随机，code码  组队）
                $.ajax({
                    type: "POST",
                    url: baseURL + "battleRecord/PkCountBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataByNum.push({value:Number(data.count), name:'在线比武'});
                    }
                });
                $.ajax({
                    type: "POST",
                    url: baseURL + "battleRecord/leitaiCountBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataByNum.push({value:Number(data.count), name:'擂台赛'});
                    }
                });



//然后的数据dataBySorce【】-----------------------------------------------------------------------------------------------------
                //       ------1查闯关分数
                $.ajax({
                    type: "POST",
                    url: baseURL + "competitionRecord/chuangguanSorceBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataBySorce.push({value:Number(data.Sorce), name:'竞赛闯关'});
                    }
                });
                //       ------2查在线pk分数
                $.ajax({
                    type: "POST",
                    url: baseURL + "battleRecord/pkSorceBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataBySorce.push({value:Number(data.Sorce), name:'在线比武'});
                    }
                });
                //       ------3查打擂台分数
                $.ajax({
                    type: "POST",
                    url: baseURL + "battleRecord/leitaiSorceBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataBySorce.push({value:Number(data.Sorce), name:'擂台赛'});
                    }
                });
//然后的数据dataByCorrect【】-----------------------------------------------------------------------------------------------------
                $.ajax({
                    type: "POST",
                    url: baseURL + "userQuestRecord/chuangguanCorrectBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataByCorrect.push({value:Number(data.Correct), name:'竞赛闯关'});
                    }
                });

                $.ajax({
                    type: "POST",
                    url: baseURL + "userQuestRecord/OnlinCorrectBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataByCorrect.push({value:Number(data.Correct), name:'在线比武'});
                    }
                });
                $.ajax({
                    type: "POST",
                    url: baseURL + "userQuestRecord/leitaiCorrectBydept",
                    Type: "json",
                    data: {"deptcode":vm.deptcode},
                    async:false,
                    success: function (data) {
                        vm.dataByCorrect.push({value:Number(data.Correct), name:'擂台赛'});
                    }
                });


        },
        // ECharts - 绘制图表
        echartsOption: function (myChart, option) {
            // this[chartName].clear()
            myChart.setOption(option)
            window.addEventListener('resize', function () {
                myChart.resize()
            })
        },

        //初始话表格部门树
        loadTabTreeData :function (){
            $.ajax({
                type: "POST",
                url: baseURL + "integral/getOrgDiaStat",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.deptTempData = result.data;

                        console.log(vm.deptTempData);
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
        initPie1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie1'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',

                //目前没有title
                title: {
                    text: '竞赛次数统计',
                    left: 'center',
                    top: 0,
                    textStyle: {
                        color: 'red'
                    }
                },

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
                    text: '竞赛积分统计',
                    left: 'center',
                    top: 0,
                    textStyle: {
                        color: 'red'
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
        initPie3: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie3'));
            // 指定图表的配置项和数据
            var option = {
                backgroundColor: '#fff',

                title: {
                    text: '正确率统计',
                    left: 'center',
                    top: 0,
                    textStyle: {
                        color: 'red'
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
                        colorLightness: [0, 1]
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

        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.reload();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.reload();
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.reload();
        },
        look: function(index,row) {

            vm.dialog2=true;
            vm.src=baseURL+"modules/contestTongJi/peopleDetails.html?uid="+row.userid;
        },
        reload: function () {
            this.one();
            //个人统计饼图
            this.initPie1();
            this.initPie2();
            this.initPie3();
            ///个人统计详情
            $.ajax({
                type: "POST",
                url: baseURL + "integral/userByDeptList",
                dataType: "json",
                async:false,
                data: vm.formInline,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.formInline.currPage = result.page.currPage;
                        vm.formInline.pageSize = result.page.pageSize;
                        vm.formInline.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.loadTabTreeData();
            console.log("加载数据");
        },
    },
    filters: {
        objTstring: function (value) {
            return JSON.stringify(value);
        }
    }
});
