package law_school;


import java.util.List;

import com.lawschool.beans.User;
import com.lawschool.beans.UserTest;
import com.lawschool.service.UserService;
import com.lawschool.service.UserTestService;
import org.aspectj.apache.bcel.util.ClassPath;
import org.junit.Test;  
import org.springframework.beans.factory.annotation.Autowired;

import com.lawschool.service.UserTestService;
import com.lawschool.beans.UserTest;

public class UserTestServiceTest extends SpringTestCase {

    @Autowired  
    private UserTestService userService;

    @Test  
    public void selectUserByIdTest(){
    	//System.out.println(ClassPath.getClassPath());
       UserTest user = userService.selectUserById(1);
       System.out.println(user.getLoginName() + ":" + user.getUserPassword());
       
       List<UserTest> users=userService.selectAllUsers();
       
       System.out.println("count:    "+users.size());

        UserTest u=new UserTest();
       u.setUserId(4);
       u.setLoginName("test4");
       u.setUserPassword("444444");
       
       userService.addUser(u);
       
       System.out.println(u.getLoginName()+"    新增成功");
       
    }

}