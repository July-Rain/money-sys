
var vm = new Vue({
    el: '#app',
    data: {
            userid:"",//人id
            img:'',
            name:'蒋丽婷',
            dataByNum:[],
            dataBySorce:[],
            dataByCorrect:[],
            user:{},


            tableData: [],//表格数据
            tableData2: [],//表格数据
            tableData3: [],//表格数据
            formInline: { // 搜索表单
                currPage: 1,
                pageSize: 10,
                totalCount: 0
            },
            formInline2: { // 搜索表单
                currPage: 1,
                pageSize: 10,
                totalCount: 0
            },
            formInline3: { // 搜索表单
                currPage: 1,
                pageSize: 10,
                totalCount: 0
            },

        dialogConfig: false,//table弹出框可见性
        title: "",//弹窗的名称
            colorList: ['#52c9e7','#3e98e8','#81bdd8','#5ebd5c','#feae24','#f97a1f','#f26443','#b97deb','#7e72f2','#4f7ee9']
    },
    created: function () {
        this.$nextTick(function () {
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
                    if (data.code == 0) {
                        vm.dataByNum.push({value: Number(data.count), name: '竞赛闯关'});
                    } else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataByNum.push({value: Number(data.count), name: '在线比武'});
                    }else {
                        alert(data.msg);
                    }
                }
            });
            $.ajax({
                type: "POST",
                url: baseURL + "battlePlatform/leitaiCountByUser",
                Type: "json",
                data: {"uid":this.user.id},
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
                url: baseURL + "integral/chuangguanCountByUser",
                Type: "json",
                data: {"uid":this.user.id},
                async:false,
                success: function (data) {
                    if (data.code == 0) {
                        vm.dataBySorce.push({value:Number(data.CheckpointSorce), name:'竞赛闯关'});
                    } else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataBySorce.push({value:Number(data.PkSorce), name:'在线比武'});
                    }else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        vm.dataBySorce.push({value:Number(data.leitaiSorce), name:'擂台赛'});
                    }else {
                        alert(data.msg);
                    }
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
            this.reload();
            this.roload2();
        })
    },
    methods: {
        // ECharts - 绘制图表
        // echartsOption: function (myChart, option) {
        //     // this[chartName].clear()
        //     myChart.setOption(option)
        //     window.addEventListener('resize', function () {
        //         myChart.resize()
        //     })
        // },
        toHome: function () {
            parent.location.reload()
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
                        name: '获得积分',
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
        // initPie3: function () {
        //     // 基于准备好的dom，初始化echarts实例
        //     var myChart = echarts.init(document.getElementById('pie3'));
        //     // 指定图表的配置项和数据
        //     var option = {
        //         backgroundColor: '#fff',
        //
        //         title: {
        //             text: '',
        //             left: 'center',
        //             top: 20,
        //             textStyle: {
        //                 color: '#ccc'
        //             }
        //         },
        //
        //         tooltip : {
        //             trigger: 'item',
        //             formatter: "{a} <br/>{b} : {c} %"
        //         },
        //
        //         visualMap: {
        //             show: false,
        //             min: 80,
        //             max: 600,
        //             inRange: {
        //                 colorLightness: [0, 1]
        //             }
        //         },
        //         series : [
        //             {
        //                 name:'游戏类型',
        //                 type:'pie',
        //                 radius : [30, 150],
        //                 center: ['50%', '50%'],
        //                 data:vm.dataByCorrect,
        //                 roseType: 'radius',
        //                 label: {
        //                     normal: {
        //                         textStyle: {
        //                             color: '#666'
        //                         }
        //                     }
        //                 },
        //                 labelLine: {
        //                     normal: {
        //                         lineStyle: {
        //                             color: '#666'
        //                         },
        //                         smooth: 0.2,
        //                         length: 10,
        //                         length2: 20
        //                     }
        //                 },
        //                 itemStyle: {
        //                     normal: {
        //                         // 定制显示（按顺序）
        //                         color: function(params) {
        //                             var colorList = ["#146084","#1978a5","#de676f","#feaf25","#219dd9","#5ebd5c","#55b6e5"];
        //                             return colorList[params.dataIndex]
        //                         }
        //                     },
        //                 },
        //
        //                 animationType: 'scale',
        //                 animationEasing: 'elasticOut',
        //                 animationDelay: function (idx) {
        //                     return Math.random() * 200;
        //                 }
        //             }
        //         ]
        //     };
        //     // 使用刚指定的配置项和数据显示图表。
        //     vm.echartsOption(myChart, option)
        // },
        handleSizeChange: function (val) {
            this.formInline.pageSize = val;
            this.roload2();
        },
        handleCurrentChange: function (val) {
            this.formInline.currPage = val;
            this.roload2();
        },

        handleSizeChange2: function (val) {
            this.formInline2.pageSize = val;
            this.roload2();
        },
        handleCurrentChange2: function (val) {
            this.formInline2.currPage = val;
            this.roload2();
        },
        handleSizeChange3: function (val) {
            this.formInline3.pageSize = val;
            this.roload2();
        },
        handleCurrentChange3: function (val) {
            this.formInline3.currPage = val;
            this.roload2();
        },
        closeDia: function () {
            this.dialogConfig = false;
            vm.roload2();
        },
        reload: function () {
            this.initPie1();
            this.initPie2();
            // this.initPie3();

        },

        roload2:function(){
            if(getUrlParam("uid")==null)
            {

                vm.userid=jsgetUser().id;//获得人id
                console.info( vm.userid);

            }
            else
            {

                vm.userid=getUrlParam("uid");//获得人id
                console.info(vm.userid);
            }

            $.ajax({
                type: "POST",
                url: baseURL + "competitionRecord/list?isMp=true&userid="+vm.userid,
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


            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/list?userid="+vm.userid,
                dataType: "json",
                async:false,
                data: vm.formInline2,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData2 = result.page.list;
                        vm.formInline2.currPage = result.page.currPage;
                        vm.formInline2.pageSize = result.page.pageSize;
                        vm.formInline2.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });

            $.ajax({
                type: "POST",
                url: baseURL + "battleRecord/listByLeitai?userid="+vm.userid,
                dataType: "json",
                async:false,
                data: vm.formInline3,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData3 = result.page.list;
                        vm.formInline3.currPage = result.page.currPage;
                        vm.formInline3.pageSize = result.page.pageSize;
                        vm.formInline3.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
});
