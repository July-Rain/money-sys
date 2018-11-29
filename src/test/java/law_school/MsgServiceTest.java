package law_school;


import com.lawschool.beans.Msg;
import com.lawschool.service.MsgService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


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
