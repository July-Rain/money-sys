package com.lawschool.service.impl.practicecenter;

/**
 * @Auther: Moon
 * @Date: 2019/1/8 09:22
 * @Description:
 */
public class CommonUtils {
    /**
     * 计算分页起始范围
     * @param total 总数
     * @param answerNum 已答数
     * @param limit 每页显示数
     * @return 返回数组，0为start，1为end, 2为当前页数, 3为总页数
     */
    public static Integer[] computeDataRange(int total, int answerNum, int limit, int page){
        // 返回的数组，0为start，1为end
        Integer[] result = new Integer[4];
        int start = 0;// 起始条数（包含）
        int end = 0;// 结束条数（包含）

        if(limit <= 0){
            throw new RuntimeException("分页参数错误");
        }

        if(page != -1){
            // 只要page 不为-1，就直接取page算范围
            start = limit * (page-1) + 1;
            end = limit * page;
        } else {
            if(answerNum >= total){// 毋须再请求题目，已全部答完
                start = -1;
                result[0] = start;
                return result;
            }

            // 求商
            int quotient = answerNum / (limit + 1);
            // 取余数
            int remainder = answerNum % (limit + 1);

            page = quotient + (remainder>0 ? 1 : 0);

            start = limit * (page - 1) + 1;
            end = limit * page;

        }

        result[0] = start > total ? -1 : start;
        result[1] = end > total ? total : end;
        result[2] = page;

        // 计算总页数
        int num_s = total / limit;// 商
        int num_y = total % limit;// 余数
        result[3] = num_s + (num_y > 0 ? 1 : 0);

        return result;
    }
}
