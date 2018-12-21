package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;

/**
 * 
 * @Descriptin  对战平台service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface BattlePlatformService extends IService<BattlePlatform>{

        public BattlePlatform save(User u);

        public void updata(BattlePlatform battlePlatform,String play2Id);
}
