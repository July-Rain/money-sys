package law_school;

import com.lawschool.beans.Role;
import com.lawschool.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class RoleServiceTest extends SpringTestCase {

    @Autowired
    private RoleService roleService;

  /*  @Test
    public void findByRoleId(){
        Role role = roleService.findByRoleId("");
        System.out.println(role);
    }

    @Test
    public void deleteById(){
        Role role = roleService.deleteById();
        System.out.println(role);
    }

    @Test
    public void add(){
        Role role =  new Role();
        role.setRoleId(new BigDecimal(1));
        role.setRoleName("zhangqqi");
        role.setCreateTime(new Date());
        role.setRemark("早上好");
       roleService.add(role);
        System.out.println(role);
    }

    @Test
    public void updaterRole(){
        Role role = new Role();
        roleService.updaterRole("");
        System.out.println(role);
    }
*/
}