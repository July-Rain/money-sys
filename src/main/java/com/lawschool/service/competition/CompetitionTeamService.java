package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.CompetitionTeam;

import java.util.List;

/**
 * 
 * @Descriptin  比武战队service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface CompetitionTeamService extends IService<CompetitionTeam> {


        public List<CompetitionTeam> findAll();

        public CompetitionTeam info(String id);

        public void save();

        public void deleteId(String id);
}
