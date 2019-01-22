var vm = new Vue({
    el: '#app',
    data: {
        levelList: [],
        position: [{left: 0, top: 102}, {left: 471, top: 0}, {left: 942, top: 102},{left: 1342, top: 102}],
        moveClass: false
    },
    mounted() {
        this.getLevel(2);
        this.rushAnimation()
    },
    methods: {
        // index 为当前面对关数
        getLevel: function (index) {
            this.levelList = [];
            for (var i = 0; i < 4; i++) {
                this.levelList.push({
                    indexName: index + i - 1
                })
            }
            console.info("1", this.levelList)
        },
        rushAnimation: function(index) {
            if(index!=1){
                return
            }
            this.moveClass = true;
        }
    },
    filters: {
        capitalize: function (value) {
            return ArabiSimplified(value)
        }
    }

});