package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.TeamUser;

/**
 * 
 * @Descriptin  战队人员表service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface TeamUserService extends IService<TeamUser> {


    public void save(String teamId,String uid);

    public void deleteTeamUser(String teamId,String uid);

}
