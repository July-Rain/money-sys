
var vm = new Vue({
    el: '#app',
    data: {
        name: '徐州市公安厅',
        dataByNum:[],
        dataBySorce:[],
        dataByCorrect:[],
        deptcode:"",
        colorList: ['#52c9e7','#3e98e8','#81bdd8','#5ebd5c','#feae24','#f97a1f','#f26443','#b97deb','#7e72f2','#4f7ee9'],
        // 表格
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
    },
    created: function () {
        this.$nextTick(function () {
            vm.deptcode='320000000000';
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
                    if (data.code == 0) {
                        vm.dataByNum.push({value:Number(data.count), name:'竞赛闯关'});
                    }else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataByNum.push({value:Number(data.count), name:'在线比武'});
                    }else {
                        alert(data.msg);
                    }
                }
            });
            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/leitaiCountBydept",
                Type: "json",
                data: {"deptcode":vm.deptcode},
                async:false,
                success: function (data) {
                    if (data.code == 0) {
                        vm.dataByNum.push({value:Number(data.count), name:'擂台赛'});
                    }else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataBySorce.push({value:Number(data.Sorce), name:'竞赛闯关'});
                    }else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataBySorce.push({value:Number(data.Sorce), name:'在线比武'});
                    }else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataBySorce.push({value:Number(data.Sorce), name:'擂台赛'});
                    }else {
                        alert(data.msg);
                    }
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
            this.pieReload();
            this.reload();
        })
    },
    methods: {
        /*echartsOption: function (myChart, option) {
            // this[chartName].clear()
            myChart.setOption(option)
            window.addEventListener('resize', function () {
                myChart.resize()
            })
            // this[chartName].hideLoading() // Chart提示关闭
        },*/
        toHome: function () {
            parent.location.pieReload()
        },
        initPie1: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie1'));
            // 指定图表的配置项和数据
            var option = {
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '参与次数',
                        type: 'pie',
                        radius : ['25%','45%'],
                        center: ['50%', '50%'],
                        labelLine:{
                            lineStyle:{
                                color:'#a6a9ad'
                            }
                        },
                        label:{
                            color:'#3333333',
                            fontSize: 14
                        },
                        data: vm.dataByNum,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList = vm.colorList;
                                    return colorList[params.dataIndex]
                                }
                            },
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            echartsOption(myChart, option)
        },
        initPie2: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie2'));
            // 指定图表的配置项和数据
            var option = {
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '参与次数',
                        type: 'pie',
                        radius : ['25%','45%'],
                        center: ['50%', '50%'],
                        labelLine:{
                            lineStyle:{
                                color:'#a6a9ad'
                            }
                        },
                        label:{
                            color:'#3333333',
                            fontSize: 14
                        },
                        data: vm.dataBySorce,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList = vm.colorList;
                                    return colorList[params.dataIndex]
                                }
                            },
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            echartsOption(myChart, option)
        },
        /*initPie3: function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pie3'));
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
                        colorLightness: [0, 1]
                    }
                },
                series : [
                    {
                        name:'游戏类型',
                        type:'pie',
                        radius : [30, 150],
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
        },*/
        pieReload: function () {
            this.initPie1();
            this.initPie2();
            // this.initPie3();
        },

        // 表格相关方法
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
        look: function(index,row)
        {
            vm.dialog2=true;
            vm.src=baseURL+"modules/contestTongJi/peopleDetails.html?uid="+row.userid;
        },
        reload: function () {
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

        }
    }
});
