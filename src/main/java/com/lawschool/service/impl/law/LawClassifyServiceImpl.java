package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.dao.law.LawClassifyDao;
import com.lawschool.service.law.LawClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: LawClassifyServiceImpl
 * Description: 法律主题分类 impl
 * date: 2018-12-7 15:14
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class LawClassifyServiceImpl extends ServiceImpl<LawClassifyDao,LawClassifyEntity> implements LawClassifyService {
    @Autowired
    private LawClassifyDao classifyDao;
    public List<TaskDesicEntity> queryClassTree(){
        //查询树节点数据
        List<TaskDesicEntity> allList=classifyDao.queryClassTree();
        allList.stream().forEach(e->{
            if("law_0".equals(e.getInfoParentId())){
                e.setInfoParentId("law_90001");
            }
            else if("stu_pic_0".equals(e.getInfoParentId())){
                e.setInfoParentId("stu_90004");
            }
            else if("stu_audio_0".equals(e.getInfoParentId())){
                e.setInfoParentId("stu_90003");
            }
            else if("stu_video_0".equals(e.getInfoParentId())){
                e.setInfoParentId("stu_90002");
            }
            else if("case_pic_0".equals(e.getInfoParentId())){
                e.setInfoParentId("case_90008");
            }
            else if("case_audio_0".equals(e.getInfoParentId())){
                e.setInfoParentId("case_90007");
            }
            else if("case_video_0".equals(e.getInfoParentId())){
                e.setInfoParentId("case_90006");
            }
        });
       // TaskDesicEntity task=new TaskDesicEntity("","law_");
        return allList;
    }
}
