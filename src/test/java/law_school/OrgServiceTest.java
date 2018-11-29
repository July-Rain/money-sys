package law_school;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawschool.beans.Org;
import com.lawschool.beans.User;
import com.lawschool.service.OrgService;

public class OrgServiceTest extends SpringTestCase{

	@Autowired
	private OrgService orgService;
	
	@Test
	public void selectOrgById() {
//		Org org = orgService.selectOrgById(16842111L); 
////		
//		System.out.println("orgName="+org.getFullName());
//		
//		List<Org> list = orgService.selectAllOrg();
//		
//		System.out.println(list.size());
//		
//		List<Org> list1 = orgService.findOrgByParentId(10000L);
//		
//		System.out.println(list1.size());
		
		List<User> list2 = orgService.findUserByOrg("320000000000");
		
		System.out.println(list2.size());
	}
}
