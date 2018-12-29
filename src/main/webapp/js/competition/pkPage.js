/**
 * Author: qj
 * Date: 2018/12/29
 * Description:
 */
var menuId =getUrlParam('id');

var vm = new Vue({
    el: '#app',
    data: {
        navData: [],//导航
        headInfo:[
            {
                img: 'head.png',
                name: '飞翔的企鹅飞翔的企鹅',
                score: 2155
            },
            {
                img: 'head.png',
                name: '飞翔的小鸡',
                score: 2155
            }
        ],
        questionList: [
            {
                questionNum: '第五题',//题号
                questionName: '请分析甲、乙、丙的刑事责任包括犯罪性质即罪名、犯罪形态、共同犯罪、数罪并罚等请分析甲、乙、丙的刑事责任包括犯罪性质即罪名、犯罪形态、共同犯罪、数罪并罚等',
                answerList: [
                    {
                        choose: 'A',
                        answer: '此案生效后当事人向检察院申诉，程序要求'
                    },
                    {
                        choose: 'B',
                        answer: '此案生效后当事人向检察院申诉，程序要求'
                    },
                    {
                        choose: 'C',
                        answer: '此案生效后当事人向检察院申诉，程序要求'
                    },
                    {
                        choose: 'D',
                        answer: '此案生效后当事人向检察院申诉，程序要求'
                    }
                ]
            }
        ]
    },
    created: function () {
    },
    methods: {
    }
});
