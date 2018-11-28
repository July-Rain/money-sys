package law_school;

import com.lawschool.beans.User;
import com.lawschool.beans.UserTest;
import com.lawschool.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserServiceTest  extends SpringTestCase{
    @Autowired
    UserService userService;

    @Test
    public void selectUserByIdTest(){
        //System.out.println(ClassPath.getClassPath());
        User user = userService.selectUserByUserId("1841669");
        System.out.println(user);
    }

    @Test
    public void selectAllUsers(){
        //System.out.println(ClassPath.getClassPath());
        List<User> users = userService.selectAllUsers();
        System.out.println(users);
    }

    @Test
    public void addUser(){
        //System.out.println(ClassPath.getClassPath());
        User user=new User();
        user.setPassword("123456");
        user.setBirthday(new Date());
        user.setFullName("测试用户");
        user.setUserId("1111");
        BigDecimal decimal=new BigDecimal(1794561);
        user.setId(decimal);
        int i = userService.addUser(user);
       System.out.println(user);
    }

    @Test
    public void login(){
        int i = userService.login("1111","123456");
        System.out.println(i);
    }

    @Test
    public void updatePassword(){
        int i = userService.updatePassword("1111","123456","654321");
        System.out.println(i);
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
        List<User> users = userService.selectOnlineUser();
        System.out.println(users);
    }

}
