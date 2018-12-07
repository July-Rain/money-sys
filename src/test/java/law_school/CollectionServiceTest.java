package law_school;/**
 * @Title: CollectionServiceTest
 * @ProjectName law_school
 * @Description: TODO
 * @author zhujunwen
 * @date 2018-11-3014:23
 */

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.Answer;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.dao.AnswerDao;
import com.lawschool.dao.CollectionDao;
import com.lawschool.service.CollectionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Title: CollectionServiceTest
 * @ProjectName law_school
 * @Description: TODO
 * @author zjw
 * @date 2018-11-3014:23
 */
public class CollectionServiceTest extends SpringTestCase {

    @Autowired
    public CollectionService service;

    @Autowired
    public CollectionDao mapper;
    @Autowired
    public AnswerDao mapper1;

    @Test
    public void test1(){
        Collection collection = new Collection();
        collection.setComStucode("3");
        collection.setType((short) 10);
        User user=new User();
        user.setId("U931078457");
        user.setFullName("nick");
        int i = service.addCollection(collection, user);
        System.out.println(i);
    }

    @Test
    public void test2(){
        Collection collection = new Collection();
        collection.setId("CO20181205161556103718");
        User user=new User();
        user.setId("U931078457");
        int i = service.delCollection(collection,user);
        System.out.println(i);
    }


    @Test
    public void test3(){
        List<Answer> answers = mapper1.selectList(new EntityWrapper<Answer>());

    }
}
