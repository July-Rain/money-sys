package law_school;


import java.util.List;

import com.lawschool.beans.SysMenu;
import com.lawschool.service.SysMenuService;
import org.aspectj.apache.bcel.util.ClassPath;
import org.junit.Test;  
import org.springframework.beans.factory.annotation.Autowired;

import com.lawschool.service.UserService;
import com.lawschool.beans.User;

public class UserServiceTest extends SpringTestCase {

    @Autowired  
    private UserService userService;
    @Autowired
    private SysMenuService sysmenuservice;
    @Test  
    public void selectUserByIdTest(){
    	//System.out.println(ClassPath.getClassPath());
       User user = userService.selectUserById(1);
       System.out.println(user.getLoginName() + ":" + user.getUserPassword());

       List<User> users=userService.selectAllUsers();

       System.out.println("count:    "+users.size());

       User u=new User();
       u.setUserId(4);
       u.setLoginName("test4");
       u.setUserPassword("444444");

       userService.addUser(u);

       System.out.println(u.getLoginName()+"    新增成功");
       
    }

    @Test
    public void menu()
    {
        SysMenu menu=sysmenuservice.selectById("1");
        System.out.println(menu);
    }
}