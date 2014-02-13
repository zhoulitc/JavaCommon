package com.dgsoft.dts.web.common.test.transfer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import com.dgsoft.dts.web.common.Message;
import com.dgsoft.dts.web.common.transfer.JSONSerialization;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-3-18 下午5:02:26 
 * @version 1.0 
 */
public class JSONSerializationTest {

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONSerialization#from(java.lang.Object)}.
     * @throws Exception 
     */
    @Test
    public void testFrom() throws Exception {
//        Message[] m = new Message[2];
//        m[0] = new Message();
//        m[1] = new Message();
//        java.util.List<Message> m = new java.util.ArrayList<Message>();
//        m.add(new Message());
//        m.add(new Message());
//        java.util.Set<Message> m = new java.util.HashSet<Message>();
//        m.add(new Message());
//        m.add(new Message());
        java.util.Map<String, Message> m = new java.util.HashMap<String, Message>();
        m.put("M1", new Message());
        m.put("M2", new Message());
        String json = JSONSerialization.from(m);
//        String strResult = "[{\"data\":\"\",\"returnMessage\":\"\",\"success\":false,\"type\":\"\"},{\"data\":\"\",\"returnMessage\":\"\",\"success\":false,\"type\":\"\"}]";
        String strResult = "{\"M2\":{\"data\":\"\",\"returnMessage\":\"\",\"success\":false,\"type\":\"\"},\"M1\":{\"data\":\"\",\"returnMessage\":\"\",\"success\":false,\"type\":\"\"}}";
        assertThat(json, is(strResult));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONSerialization#from(java.lang.Object)}.
     * @throws Exception 
     */
    @Test
    public void testFrom1() throws Exception {
        String json = JSONSerialization.from(null);
        String strResult = "";
        assertThat(json, is(strResult));
    }
}