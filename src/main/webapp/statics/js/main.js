var auditTypeNumber;
var auditId;
var sourceCode;
var requsetId;
var userId;
var userInfo={};
var rowId;
var pendingdGrid;

var src="main";
$(function () {
    $.ajax({
        type: "POST",
        url: baseURL + 'mgt/audit/queryInfos?commission_state=0',
        dataType: "json",
        data: {limit:10,page:1},
        success: function (result) {
            vm.tableData=result.page.list;
        }
    });
    $.ajax({
        type: "POST",
        url: baseURL + 'sys/count/countAllInfo',
        dataType: "json",
        success: function (result) {

            vm.countAllInfo.planMonthNum=result.planMonthNum;
            vm.countAllInfo.planExamineNum=result.planExamineNum;
            vm.countAllInfo.reviewDelayNum=result.reviewDelayNum;
            vm.countAllInfo.reviewNum=result.reviewNum;
            vm.countAllInfo.caseNum=result.caseNum;
            vm.countAllInfo.caseDealyNum=result.caseDealyNum;
        }
    });

    setInterval(function () {
        $.ajax({
            type: "POST",
            url: baseURL + 'mgt/audit/queryInfos?commission_state=0',
            dataType: "json",
            data: {limit:10,page:1},
            success: function (result) {
                vm.tableData=result.page.list;
            }
        })
    },5000000)
});

var vm = new Vue({
    el:"#app",
    data:{
        tableData: [],
        tableData2:[],//区县表格数据
        formInline: {
            user:"",
            region:"",
            number:"",
            startTime:"",
            endTime:"",
            department:"",
            personnel:"",
            from:"",
            condition:"",
            dqid:"",
            dqbs:""
        },
        auditId:"",
        countAllInfo:{
            planMonthNum:0,
            planExamineNum:0,
            reviewDelayNum:0,
            reviewNum:0,
            caseNum:0,
            caseDealyNum:0,
        },
        columnData:[],//柱状图数据
        columnType:0,//柱状图展示类型
        edit:true,
        isAdd:false,
        noticeId:'',//通知公告id
        noticeList:[],//通知公告数据
        noticeNow:'',//通知公告当前时间
        mapData:[],//当前地图数据
        cityData:{
            name:'',
            dqid:''
        },//记录城市信息,方便返回上一级
        countyTh:[{
            tabOne:'企业对象',
            tabTwo:'检查日期',
            tabThr:'检查方式',
            tabFou:'检查人员',
        },{
            tabOne:'企业名称',
            tabTwo:'检查时间',
            tabThr:'所属行业',
            tabFou:'检查人员',
        },{
        	tabOne:'案件名称',
        	tabTwo:'案由',
        	tabThr:'承办人',
        	tabFou:'企业名称',
        },{
        	tabOne:'案件名称',
        	tabTwo:'案由',
        	tabThr:'承办人',
        	tabFou:'企业名称',
        }]
    },
    methods:{
        validateForm:function(title) {
            this.$message({
                message: title,
                type: 'error'
            });
        },
        tabClick:function (columnType){
            $(".tabs-cont .home-tabs li").removeClass("this");
            $(".tabs-cont .home-tabs li").eq(columnType).addClass("this");
            vm.columnType=columnType;
            vm.columnChart(vm.columnType);
            var obj = {};
            if(!isBlank(this.formInline.startTime)){
                obj.kssj = this.formInline.startTime;
            }
            if(!isBlank(this.formInline.endTime)){
                obj.jssj = this.formInline.endTime;
            }
            if(!isBlank(this.formInline.dqid)){
                obj.dept_code = this.formInline.dqid;
                obj.dept_name = this.formInline.dqid;
            }

            if(!isBlank(this.formInline.dqbs)){
                obj.level = this.formInline.dqbs;
            }
            obj.type = columnType;
            if(vm.formInline.dqbs==3) vm.countForTab(obj);
        },
        countForTab: function(obj){
        	$.ajax({
                type: "POST",
                url: baseURL + 'mgt/analysis/countForTab',
                dataType: "json",
                data: obj,
                success: function (result) {
                    vm.tableData2=result.resultList;
                    vm.columnChart(vm.columnType);
                }
            })
        },
        noticeInfo:function (){
            //通知公告
            /**
             *
             * 获取当前时间
             */
            function p(s) {
                return s < 10 ? '0' + s: s;
            }
            var myDate = new Date();
            //获取当前年
            var year=myDate.getFullYear();
            //获取当前月
            var month=myDate.getMonth()+1;
            //获取当前日
            var date=myDate.getDate();
            //获取当前星期X(0-6,0代表星期天)
            var weekArr=['周日','周一','周二','周三','周四','周五','周六'];
            var week=weekArr[myDate.getDay()];
            // var h=myDate.getHours();       //获取当前小时数(0-23)
            // var m=myDate.getMinutes();     //获取当前分钟数(0-59)
            // var s=myDate.getSeconds();
            this.noticeNow=year+'/'+p(month)+"/"+p(date)+"    "+week;

            this.$nextTick(function () {
                $.ajax({
                    type: "POST",
                    url: baseURL+"mgt/examine/getcreator",
                    dataType: "json",
                    async:false,
                    success: function (result) {

                        if (result.code == 0) {

                            $.ajax({
                                type: "POST",
                                url: baseURL + 'mgt/notice/getBydeptid/'+result.deptId,
                                dataType: "json",
                                async:false,
                                success: function (result) {
                                    vm.noticeList=result.notices;
                                    vm.$nextTick(function () {
                                        //通知公告显示隐藏
                                        $(".notice-btn").hover(function () {
                                            //显示公告
                                            if ($(".notice").css('right')=='-280px'){
                                                $(".notice").animate({right:0});
                                            }
                                        });
                                        $(".notice").hover(function () {

                                        },function(){
                                            //取消hover
                                            if ($(".notice").css('right')=='0px'){
                                                $(".notice").animate({right:'-280px'});
                                            }

                                        });
                                    });
                                }
                            });


                        } else {
                            alert(result.msg);
                        }
                    }
                });
            })
        },
        openNoticeInfo:function(id){
            vm.noticeId=id;
            open_win(baseURL+'modules/mgt/noticeBulletinInfo.html'," 通知公告",'880','520');
        },
        openNoticeAll:function (){
            open_win(baseURL+'modules/mgt/noticeBulletin.html',"通知公告信息",'1020','540');
        },
        isShowColumn:function (){
            if(vm.formInline.dqbs!=3){
                $("#column-echarts").css("display",'block');
            }else {
                $("#column-echarts").css("display",'none');
            }
        },
        search:function (){//搜索
            if (isBlank(vm.formInline.startTime)){
                this.validateForm("请设置开始日期");
                return;
            }

            if (isBlank(vm.formInline.endTime)){
                this.validateForm("请设置结束日期");
                return;
            }
            if (vm.formInline.startTime > vm.formInline.endTime) {
                this.validateForm("开始日期不能大于结束日期");
                return;
            }
            vm.columnGetData();
        },
        columnGetData:function (){
            var obj = {};
            if(!isBlank(this.formInline.startTime)){
                obj.kssj = this.formInline.startTime;
            }
            if(!isBlank(this.formInline.endTime)){
                obj.jssj = this.formInline.endTime;
            }
            if(!isBlank(this.formInline.dqid)){
                obj.dept_code = this.formInline.dqid;
                obj.dept_name = this.formInline.dqid;
            }

            if(!isBlank(this.formInline.dqbs)){
                obj.level = this.formInline.dqbs;
            }

            $.ajax({
                type: "POST",
                url: baseURL + 'mgt/analysis/countForMap',
                dataType: "json",
                data: obj,
                success: function (result) {
                    vm.columnData=result.resultList;
                    vm.columnChart(vm.columnType);
                }
            })
        },
        columnChart:function (type){
            //柱形图
            var xAxisData=[],
                seriesData=[],
                opc='';
            switch (type) {
                case 0:
                    opc = "planNums";// 制定检查数
                    break;
                case 1:
                    opc = "inspectNums";// 企业检查数
                    break;
                case 2:
                    opc = "punishNums";// 行政处罚数
                    break;
                case 3:
                    opc = "settleNums";// 结案数
                    break;
            }

            vm.columnData.map(function (val,index) {
                xAxisData.push(val.name);
                seriesData.push(val[opc]);
            });
            var columnChart= echarts.init(document.getElementById('column-echarts'));
            columnChart.clear();
            if (columnChart != null) {
                columnChart.off('click');
            }
            var columnOption = {
                color: ['#3398DB'],
                tooltip : {
                    showDelay: 20,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                    hideDelay: 50,            // 隐藏延迟，单位ms
                    trigger: 'axis',
                    axisPointer : {                 // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow',        // 默认为直线，可选为：'line' | 'shadow'
                        shadowStyle : {              // 阴影指示器样式设置
                            width: 'auto',         // 阴影大小
                            color: 'rgba(150,150,150,0.3)'  // 阴影颜色
                        }
                    },
                },
                grid: {
                    left: '2%',
                    right: '4%',
                    top: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : xAxisData,
                        axisTick: {
                            alignWithLabel: true
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#fff', // x坐标轴的轴线颜色
                                width:0 //这里是坐标轴的宽度,为0就是不显示
                            }
                        },
                        axisLabel: {
                            interval: 0,
                            formatter:function(value)
                            {
                                var ret = "";//拼接加\n返回的类目项
                                var maxLength = 1;//每项显示文字个数
                                var valLength = value.length;//X轴类目项的文字个数
                                var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数
                                if (rowN > 1)//如果类目项的文字大于3,
                                {
                                    for (var i = 0; i < rowN; i++) {
                                        var temp = "";//每次截取的字符串
                                        var start = i * maxLength;//开始截取的位置
                                        var end = start + maxLength;//结束截取的位置
                                        //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                                        temp = value.substring(start, end) + "\n";
                                        ret += temp; //凭借最终的字符串
                                    }
                                    return ret;
                                }
                                else {
                                    return value;
                                }
                            }
                        }


                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            show: true //这行代码控制着坐标轴x轴的文字是否显示
                        },
                        splitLine: {
                            show: true, // 网格线是否显示
                            // 改变样式
                            lineStyle: {
                                color: '#fff' // 修改网格线颜色
                            }
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#fff', // x坐标轴的轴线颜色
                                width:0 //这里是坐标轴的宽度,为0就是不显示
                            }
                        }
                    }
                ],
                series : [
                    {
                        name:'直接访问',
                        type:'bar',
                        barWidth : 12,//柱图宽度
                        itemStyle : {
                            normal: {
                                show: true,
                                position: 'inside',
                                barBorderColor: '#fff',
                                barBorderRadius: [ 4, 4],
                                borderColor:'#fff',
                                borderWidth:4,
                                label : {show: false},
                                color: function(params) {
                                    var colorList = [     //柱子颜色
                                        '#f9853f'
                                    ];
                                    return colorList[0]
                                }
                            }
                        },
                        label: {
                            normal: {
                                show: true,
                                position: 'top',
                                color:"#fff"
                            }
                        },
                        data:seriesData
                    }
                ]
            };
            //市区柱形图

            // 使用刚指定的配置项和数据显示图表。
            columnChart.setOption(columnOption);
            //点击柱状图功能，其中param.name参数为横坐标的值
            columnChart.on('click', function (params) {
                var myChartparams={
                    data:{
                        name:params.name,
                        dqid:''
                    }
                };
                vm.mapData.forEach(function (val) {
                    if(val.name==params.name){
                        myChartparams.data.dqid=val.dqid;
                    }
                });
                if (myChartparams.data.dqid=='')return;

                if(vm.formInline.dqbs==1){
                    //变更下全局变量,方便返回上一级
                    mapInfo.cityName = myChartparams.data.name;
                    mapInfo.cityId = myChartparams.data.dqid;
                    mapInfo.areaName = null;
                    mapInfo.areaId = null;
                    mapInfo.mapLevel = 2;
                    //loadCityMap一定要在修改了全局变量之后调用
                    loadCityMap(myChartparams.data.name, myChartparams.data.dqid);//加载市级地图
                    vm.formInline.dqid = myChartparams.data.dqid;
                    vm.formInline.dqbs = 2;
                    vm.cityData={
                        name:myChartparams.data.name,
                        dqid:myChartparams.data.dqid
                    };
                    vm.isShowColumn();//是否显示ColumnEchart
                    vm.columnGetData();
                }else {
                    //变更下全局变量,方便返回上一级
                    //mapInfo.cityName = vm.cityData.name;
                    //mapInfo.cityId = vm.cityData.dqid;
                    //加上上面两句在：地图--》市--》柱形图-》市（页面出错）
                    mapInfo.areaName = myChartparams.data.name;
                    mapInfo.areaId = myChartparams.data.dqid;
                    mapInfo.mapLevel = 3;
                    console.log(mapInfo)
                    //loadAreaMap一定要在修改全局编练之后调用
                    loadAreaMap(myChartparams.data.name, myChartparams.data.dqid);//加载区县地图
                    vm.formInline.dqid = myChartparams.data.dqid;
                    vm.formInline.dqbs = 3;
                    vm.isShowColumn();//是否显示ColumnEchart
                     //vm.countForTab(vm.formInline);
                    vm.getCountyTable();//区县table数据
                }
            });

            //  自适应echarts宽度
            $(window).resize(function() {
                columnChart.resize();
            });
        },
        getCountyTable:function (){

            // console.log("这个地方进行 table 数据表的切换")
            //vm.columnChart(vm.columnType);
            var obj = {};
            if(!isBlank(this.formInline.startTime)){
                obj.kssj = this.formInline.startTime;
            }
            if(!isBlank(this.formInline.endTime)){
                obj.jssj = this.formInline.endTime;
            }
            if(!isBlank(this.formInline.dqid)){
                obj.dept_code = this.formInline.dqid;
                obj.dept_name = this.formInline.dqid;
            }

            if(!isBlank(this.formInline.dqbs)){
                obj.level = this.formInline.dqbs;
            }
            obj.type = vm.columnType;
            if(vm.formInline.dqbs==3) vm.countForTab(obj);
        },
        handleClick:function (index, row) {//处置
            rowId=row.auditId;
            vm.auditId = row.auditId;
            vm.auditInfo = row;
            $.ajax({
                type: "POST",
                url: baseURL + "mgt/audit/getMsgType",
                dataType: "json",
                async:false,
                data: {auditId: rowId},
                success: function (result) {
                    if (result.code === 0) {
                        sourceCode = result.sourceCode;
                        auditTypeNumber = result.auditTypeNumber;
                        requsetId = result.requestId;
                        open_win(baseURL+'modules/mgt/prohandle.html',requsetId,'1200','650')
                    }
                }
            });
        },
        reload:function () {
            $.ajax({
                type: "POST",
                url: baseURL + 'mgt/audit/queryInfos?commission_state=0',
                dataType: "json",
                data: {limit:10,page:1},
                success: function (result) {
                    vm.tableData=result.page.list;
                }
            });
        },
    },
    created:function () {
        this.columnGetData();
        this.noticeInfo();//通知公告
    },
    filters: {
        InterceptSpace: function (value) {
            return value.split(" ")[0].replace(/-/g,'/');
        },
        disabled : function (value) {
            if (!value){
                return true
            }else {
                return false
            }

        },

    },
});


//计划到期提醒
function jhdqtx()
{
    // 跳转新的页面
    open_win(baseURL+'modules/mgt/planmonthly-index.html','getPlanCode','1200','650');
}
//检查方案到期提醒
function jcfadq()
{
    // 跳转新的页面modules/mgt/planexamine.html
    open_win(baseURL+'modules/mgt/planexamine-index.html','getPlanCode','1200','650');
}
//责令限期整改到期提醒
function zlxqzgdq()
{
    // 跳转新的页面mgt/examinerectify.html
    open_win(baseURL+'modules/mgt/examinerectify-index-3.html','getPlanCode','1200','650');
}
//责令限期整改延期到期提醒
function zlxqzgyqdq()
{
    // 跳转新的页面
    open_win(baseURL+'modules/mgt/examinerectify-index-4.html','getPlanCode','1200','650');
}
//案件办理到期预警
function ajbldq()
{
    // 跳转新的页面I:\idea-pro\aj\anjian\dc-admin\src\main\resources\templates\modules\mgt\caseregistr-index.html
    open_win(baseURL+'modules/mgt/caseregistr-index.html','getPlanCode','1200','650');
}
//案件办理延期到期预警
function ajblyqdq()
{
    // 跳转新的页面
    open_win(baseURL+'modules/mgt/caseregistr-index2.html','getPlanCode','1200','650');
}