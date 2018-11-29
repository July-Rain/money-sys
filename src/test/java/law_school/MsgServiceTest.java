package law_school;


import com.lawschool.beans.Msg;
import com.lawschool.service.MsgService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class MsgServiceTest extends SpringTestCase{

    @Autowired
    MsgService msgService;

    @Test
    public void testMsgService(){

        Msg msg=new Msg();
        msg.setId("1");
        msg.setTitle("我我我我");


    }
}
