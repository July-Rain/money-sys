package law_school;

import com.lawschool.beans.Dict;
import com.lawschool.beans.UserTest;
import com.lawschool.service.DictService;
import com.lawschool.service.UserTestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DictServiceTest extends SpringTestCase{
    @Autowired
    private DictService aaa;

    @Test
    public void selectUserByIdTest(){

         Dict dict=   aaa.selectByDictId(1);
         System.out.println(dict);
         dict.setName("001");
         aaa.updateByDict(dict);
         aaa.selectByDictId(1);

         System.out.println(dict);
         aaa.deleteByDictId(1);
         aaa.selectByDictId(1);
         System.out.println(aaa+"已删除");
    }
}
