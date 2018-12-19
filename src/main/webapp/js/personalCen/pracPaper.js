var params = window.location.search;

var vm = new Vue({
    el: '#app',
    data:{
        //choice:["A","B","C","D","E","F","G","H","I"],
        practiceConfiguration:{
            id:"",
            questionType:"",
            primaryCount:"",
            intermediateCount:"",
            seniorCount:"",
            specialKnowledgeId:""
        },
        tableList:[],//前端填写的配置
        dataList:[],//接收的后台数据，题目
        obj: null,
    },
    methods: {
        //获取url路径中参数
        getRequest:function(){
          var url = location.search;
          if(url.indexOf("?")!=-1){
            var str = url.substr(1);
            strs = str.split("=");
            alert(strs[1]);

          }
        },
    },
    created: function(){
        // this.tableList.push(this.practiceConfiguration);
        this.$nextTick(function () {
            var cs = getUrlParam('id');//获取路径中参数

            $.ajax({
                type: "POST",
                url: baseURL + "pracConfiguration/createPaper",
                data: {"id":cs},
                dataType: "json",
                success: function (result) {
                    console.info(result);

                    if (result.code === 0) {
                        vm.dataList = result.practicePaper;//返回试题列表list
                        if(result.practicePaper.length == 0){
                            alert('本次练习已全部结束，请直接提交本次练习');
                        }
                    } else {
                        alert(result.msg);
                    }
                }
            });
        })
    }

});