package law_school;


import com.lawschool.beans.Msg;
import com.lawschool.service.MsgService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


public class MsgServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    MsgService msgService;

    @Test
    public void testMsgService(){

        Msg msg=new Msg();
        msg.setId("1");
        msg.setTitle("我我我我");


    }
}
