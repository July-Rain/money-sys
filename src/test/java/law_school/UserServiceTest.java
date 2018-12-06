package law_school;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.User;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserServiceTest  extends SpringTestCase {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper mapper;
    
    @Test
    public void tes1(){
        List<User> users = mapper.selectList(new EntityWrapper<User>());
    }

    @Test
    public void selectUserByIdTest(){
        //System.out.println(ClassPath.getClassPath());
        User user = userService.selectUserByUserId("1841669");
        System.out.println(user);
    }

    @Test
    public void selectAllUsers(){
        //System.out.println(ClassPath.getClassPath());
        PageUtils pageUtils = userService.selectAllUsers(new HashedMap());
        System.out.println(pageUtils);
    }

    @Test
    public void addUser(){
        //System.out.println(ClassPath.getClassPath());
        User user=new User();
        user.setPassword("123456");
        user.setBirthday(new Date());
        user.setUserCode("931078457");
        user.setFullName("wj");
        //BigDecimal decimal=new BigDecimal(1794561);
        //user.setId(decimal);
        int i = userService.addUser(user);
       System.out.println(user);
    }

    @Test
    public void login(){
//        int i = userService.login("1111","123456");
//        System.out.println(i);
    }

    @Test
    public void updatePassword(){
//        int i = userService.updatePassword("1111","123456","654321");
//        System.out.println(i);
    }

    @Test
    public void updateUserStatus(){
        int i = userService.updateUserStatus("2022506",2000,800);
        System.out.println(i);
    }

    @Test
    public void updateUserOnlineStatus(){
        int i = userService.updateUserOnlineStatus("2022506","0","1");
        System.out.println(i);
    }

    @Test
    public void selectOnlineUser(){
        PageUtils pageUtils = userService.selectOnlineUser(new HashedMap());
        System.out.println(pageUtils);
    }

}
