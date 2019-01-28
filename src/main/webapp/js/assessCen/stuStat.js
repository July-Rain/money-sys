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
            tableData:[],//表格中的数据

        }
    },
    created: function () {

        this.$nextTick(function () {
            this.getStuDia();
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
        getStuDia: function () {
            debugger
            $.ajax({
                async:false,
                type: "POST",
                url: baseURL + "diagnosis/getAllStuDiagnosis?isMp=true",
                dataType: "json",
                data: vm.dateRange,
                success: function (result) {
                    if (result.code == 0) {
                        vm.tableData = result.page.list;
                        vm.dateRange.currPage = result.page.currPage;
                        vm.dateRange.pageSize = result.page.pageSize;
                        vm.dateRange.totalCount = parseInt(result.page.totalCount);
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        handleSizeChange: function (val) {
            this.dateRange.pageSize = val;
            this.getStuDia();
        },
        handleCurrentChange: function (val) {
            this.dateRange.currPage = val;
            this.getStuDia();
        },
        //初始话表格部门树
        loadTabTreeData :function (){
            $.ajax({
                type: "POST",
                url: baseURL + "diagnosis/getOrgDiaStat",
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
