package law_school;

import com.lawschool.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

public class RoleServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RoleService roleService;

    @Test
    public void findByRoleId(){
        Role role = roleService.findByRoleId("1");
        System.out.println(role);
    }

    @Test
    public void deleteById(){
//        Role role = roleService.deleteById();
//        System.out.println(role);
    }

    @Test
    public void add(){
        Role role =  new Role();
        role.setRoleId("1");
        role.setRoleName("zhangqqi");
        role.setCreateTime(new Date());
        role.setRemark("早上好");
       roleService.add(role);
        System.out.println(role);
    }

    @Test
    public void updaterRole(){
//        Role role = new Role();
//        roleService.updaterRole("");
//        System.out.println(role);
    }
}