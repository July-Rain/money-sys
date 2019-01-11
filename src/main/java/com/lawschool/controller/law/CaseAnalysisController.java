package com.lawschool.controller.law;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.service.law.CaseAnalysisService;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * ClassName: CaseAnalysisController
 * Description: 案例分析controller
 * date: 2018-12-22 15:02
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("caseana")
public class CaseAnalysisController extends AbstractController {

    @Autowired
    private CaseAnalysisService analysisService;

    @Autowired
    private StuRecordService recordService;

    /**
     * @Author MengyuWu
     * @Description 案例分析列表查询
     * @Date 15:04 2018-12-22
     * @Param [params]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String,Object> params){
        PageUtils page = analysisService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * @Author MengyuWu
     * @Description 添加案例分析
     * @Date 15:14 2018-12-22
     * @Param [analysisEntity]
     * @return com.lawschool.util.Result
     **/

    @SysLog("添加案例分析")
    @RequestMapping("/insertCaseAna")
    public Result insertStuMedia(@RequestBody CaseAnalysisEntity analysisEntity){
        User user=getUser();
        analysisEntity.setId(GetUUID.getUUIDs("CA"));
        analysisService.insertCaseAnaly(analysisEntity,getUser());
        return Result.ok().put("id",analysisEntity.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 修改案例分析
     * @Date 15:14 2018-12-22
     * @Param [analysisEntity]
     * @return com.lawschool.util.Result
     **/


    @SysLog("修改案例分析")
    @RequestMapping("/updateCaseAna")
    public Result updateStuMedia(@RequestBody CaseAnalysisEntity analysisEntity){
        User user=getUser();
        analysisService.updateCaseAnaly(analysisEntity,getUser());
        return Result.ok().put("id",analysisEntity.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 查看详情
     * @Date 15:15 2018-12-22
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/info")
    public Result info(String id){
        CaseAnalysisEntity analysisEntity = analysisService.selectCaseAnalyInfo(id);
        return Result.ok().put("data",analysisEntity);
    }


    /**
     * @Author MengyuWu
     * @Description 删除案例分析
     * @Date 15:15 2018-12-22
     * @Param [ids]
     * @return com.lawschool.util.Result
     **/
    

    @SysLog("删除案例分析")
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] ids){
        analysisService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }
    /**
     * @Author MengyuWu
     * @Description 案例分析
     * @Date 15:10 2019-1-10
     * @Param [id, stuType, stuFrom, taskId]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/updateCount")
    public Result updateCount(String id,String stuType,String stuFrom,String taskId){
        //获取当前登陆人
        User user=  getUser();
        //更新
        analysisService.updateCount(id );
        //插入学习记录
        recordService.insertStuRecord(user,id,stuType,stuFrom,taskId);
        return Result.ok();
    }
}
