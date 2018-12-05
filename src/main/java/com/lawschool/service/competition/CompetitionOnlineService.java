package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.CompetitionOnline;

import java.util.List;

/**
 * 
 * @Descriptin  在线比武配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface CompetitionOnlineService extends IService<CompetitionOnline> {

    public List<CompetitionOnline>  list();

    public CompetitionOnline info(String id);


    public void save();

    public  void deleteComOnline(String id);

    public void  updateComOnline();
}
