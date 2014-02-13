package com.dgsoft.dts.web.common.test.transfer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import com.dgsoft.dts.web.common.Message;
import com.dgsoft.dts.web.common.transfer.XMLSerialization;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-3-18 下午5:24:20 
 * @version 1.0 
 */
public class XMLSerializationTest {

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLSerialization#from(java.lang.Object)}.
     * @throws Exception 
     */
    @Test
    public void testFrom() throws Exception {
      Message[] m = new Message[2];
      m[0] = new Message();
      m[1] = new Message();
//      java.util.List<Message> m = new java.util.ArrayList<Message>();
//      m.add(new Message());
//      m.add(new Message());
//      java.util.Set<Message> m = new java.util.HashSet<Message>();
//      m.add(new Message());
//      m.add(new Message());
//      java.util.Map<String, Message> m = new java.util.HashMap<String, Message>();
//      m.put("M1", new Message());
//      m.put("M2", new Message());
      String xml = XMLSerialization.from(m, "wahaha");
      String strResult = "<?xml version='1.0' encoding='utf-8'?><wahaha><_><data></data><returnMessage></returnMessage><success>false</success><type></type></_><_><data></data><returnMessage></returnMessage><success>false</success><type></type></_></wahaha>";
//      String strResult = "<?xml version='1.0' encoding='utf-8'?><Root><M2><data></data><returnMessage></returnMessage><success>false</success><type></type></M2><M1><data></data><returnMessage></returnMessage><success>false</success><type></type></M1></Root>";
      assertThat(xml, is(strResult));
    }

    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLSerialization#from(java.lang.Object)}.
     * @throws Exception 
     */
    @Test
    public void testFrom1() throws Exception {
        String xml = XMLSerialization.from(null);
        String strResult = "";
        assertThat(xml, is(strResult));
    }
}
