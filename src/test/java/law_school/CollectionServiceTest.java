package law_school;/**
 * @Title: CollectionServiceTest
 * @ProjectName law_school
 * @Description: TODO
 * @author zhujunwen
 * @date 2018-11-3014:23
 */

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.beans.Answer;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.dao.AnswerMapper;
import com.lawschool.dao.CollectionMapper;
import com.lawschool.service.CollectionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

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
    public CollectionMapper mapper;
    @Autowired
    public AnswerMapper mapper1;

    @Test
    public void test1(){
        Collection collection = new Collection();
        collection.setComStucode("3");
        collection.setType((short) 10);
        User user=new User();
        user.setUserId("1");
        user.setFullName("nick");
        int i = service.addCollection(collection, user);
        System.out.println(i);
    }

    @Test
    public void test2(){
        Collection collection = new Collection();
        collection.setId("1150e50c5fc342c6975f3689ece1d84b");
        User user=new User();
        user.setUserId("1");
        int i = service.delCollection(collection,user);
        System.out.println(i);
    }


    @Test
    public void test3(){
        List<Answer> answers = mapper1.selectList(new EntityWrapper<Answer>());

    }
}