package com.dgsoft.dts.web.common.test.transfer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import com.dgsoft.dts.web.common.Message;
import com.dgsoft.dts.web.common.transfer.JSONDeserialization;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-3-18 下午5:38:22 
 * @version 1.0 
 */
public class JSONDeserializationTest {

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toBean(java.lang.String, java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testToBean() throws Exception {
        String strXML = 
                "{" + 
                    "\"data\":1," + 
                    "\"returnMessage\":\"22\"," + 
                    "\"success\":false," + 
                    "\"type\":\"\"," +
                    "\"array\":[" + 
                        "1," + 
                        "2," + 
                        "3" + 
                    "]," + 
                    "\"list\":[" + 
                        "1," + 
                        "2," + 
                        "3" + 
                    "]," + 
                    "\"set\":[" + 
                        "1," + 
                        "2," + 
                        "3," + 
                    "]," + 
                    "\"map\":{" + 
                        "\"K1\":1," + 
                        "\"K2\":2," + 
                        "\"K3\":[" + 
                            "1," + 
                            "2," + 
                            "3" + 
                        "]" + 
                    "}," + 
                    "\"child\":{" + 
                        "\"a\":1," + 
                        "\"b\":2," + 
                        "\"c\":3" + 
                    "}" + 
                "}";
        Message m = JSONDeserialization.toBean(strXML, Message.class);
        assertThat(m, notNullValue());
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toBean(java.lang.String, java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testToBean1() throws Exception {
        Message m = JSONDeserialization.toBean(null, Message.class);
        assertThat(m, nullValue());
    }
    
    /**
     * JSON不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toBean(java.lang.String, java.lang.Class)}.
     */
    @Test
    public void testToBean2() {
        String strXML = 
                "{" + 
                    "\"data\":1," + 
                    "\"returnMessage\":\"22\"," + 
                    "\"success\":false," + 
                    "\"type\":\"\"," +
                    "\"array\":[" + 
                        "1," + 
                        "2," + 
                        "3" + 
                    "]," + 
                    "\"list\":[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[" + 
                        "1," + 
                        "2," + 
                        "3" + 
                    "]," + 
                    "\"set\":[" + 
                        "1," + 
                        "2," + 
                        "3," + 
                    "]," + 
                    "\"map\":{" + 
                        "\"K1\":1," + 
                        "\"K2\":2," + 
                        "\"K3\":[" + 
                            "1," + 
                            "2," + 
                            "3" + 
                        "]" + 
                    "}," + 
                    "\"child\":{" + 
                        "\"a\":1," + 
                        "\"b\":2," + 
                        "\"c\":3" + 
                    "}" + 
                "}";
        Message m = null;
        try {
            m = JSONDeserialization.toBean(strXML, Message.class);
        } catch (Exception e) {
        }
        assertThat(m, nullValue());
    }

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toList(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToList() throws Exception {
        String strJson = 
                "[" + 
                    "{\"data\":1," + 
                    "\"returnMessage\":\"22\"," + 
                    "\"success\":false," + 
                    "\"type\":\"\"}," +
                    "{\"data\":4," + 
                    "\"returnMessage\":\"55\"," + 
                    "\"success\":false," + 
                    "\"type\":\"6\"}" +
                "]";
        List<Message> list = JSONDeserialization.toList(strJson, Message.class);
        assertThat(list, notNullValue());
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toList(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToList1() throws Exception {
        List<Message> list = JSONDeserialization.toList(null, Message.class);
        assertThat(list, nullValue());
    }
    
    /**
     * JSON不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toList(java.lang.String)}.
     */
    @Test
    public void testToList2() {
        String strJson = 
                "[" + 
                    "{}{}{}{}{}{}{{}{}{}{*^(*&*(&(*{\"data\":1," + 
                    "\"returnMessage\":\"22\"," + 
                    "\"success\":false," + 
                    "\"type\":\"\"}," +
                    "{\"data\":4," + 
                    "\"returnMessage\":\"55\"," + 
                    "\"success\":false," + 
                    "\"type\":\"6\"}" +
                "]";
        List<Message> list = null;
        try {
            list = JSONDeserialization.toList(strJson, Message.class);
        } catch (Exception e) {
        }
        assertThat(list, nullValue());
    }

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toMap(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToMap() throws Exception {
        String strJson = 
                "{\"M1\":" + 
                    "{\"data\":1," + 
                    "\"returnMessage\":\"22\"," + 
                    "\"success\":false," + 
                    "\"type\":\"\"}," +
                    "\"M2\":{\"data\":4," + 
                    "\"returnMessage\":\"55\"," + 
                    "\"success\":false," + 
                    "\"type\":\"6\"}" +
                "}";
        Map<String, Message> map = JSONDeserialization.toMap(strJson, Message.class);
        assertThat(map, notNullValue());
    }

    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toMap(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToMap1() throws Exception {
        Map<String, Message> map = JSONDeserialization.toMap(null, Message.class);
        assertThat(map, nullValue());
    }

    /**
     * JSON不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.JSONDeserialization#toMap(java.lang.String)}.
     */
    @Test
    public void testToMap2() {
        String strJson = 
                "{\"M1\"}}}}}}}}}}}}}}}:" + 
                    "{\"data\":1," + 
                    "\"returnMessage\":\"22\"," + 
                    "\"success\":false," + 
                    "\"type\":\"\"}," +
                    "\"M2\":{\"data\":4," + 
                    "\"returnMessage\":\"55\"," + 
                    "\"success\":false," + 
                    "\"type\":\"6\"}" +
                "}";
        Map<String, Message> map = null;
        try {
            map = JSONDeserialization.toMap(strJson, Message.class);
        } catch (Exception e) {
        }
        assertThat(map, nullValue());
    }
}
