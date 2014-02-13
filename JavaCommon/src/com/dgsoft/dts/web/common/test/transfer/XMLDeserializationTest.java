package com.dgsoft.dts.web.common.test.transfer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import com.dgsoft.dts.web.common.Message;
import com.dgsoft.dts.web.common.transfer.XMLDeserialization;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-3-18 下午5:38:44 
 * @version 1.0 
 */
public class XMLDeserializationTest {

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toBean(java.lang.String, java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testToBean() throws Exception {
        String strXML = 
                "<?xml version='1.0' encoding='utf-8'?>" + 
                "<g>" + 
                    "<data>1</data>" + 
                    "<returnMessage>22</returnMessage>" + 
                    "<success>false</success>" + 
                    "<type></type>" +
                    "<array>" + 
                        "<_>1</_>" + 
                        "<_>2</_>" + 
                        "<_>3</_>" + 
                    "</array>" + 
                    "<list>" + 
                        "<_>1</_>" + 
                        "<_>2</_>" + 
                        "<_>3</_>" + 
                    "</list>" + 
                    "<set>" + 
                        "<_>1</_>" + 
                        "<_>2</_>" + 
                        "<_>3</_>" + 
                    "</set>" + 
                    "<map>" + 
                        "<K1>1</K1>" + 
                        "<K2>2</K2>" + 
                        "<K3>" +
                            "<_>1</_>" + 
                            "<_>2</_>" + 
                            "<_>3</_>" + 
                        "</K3>" + 
                    "</map>" + 
                    "<child>" + 
                        "<a>1</a>" + 
                        "<b>2</b>" + 
                        "<c>3</c>" + 
                    "</child>" + 
                "</g>";
        Message m = XMLDeserialization.toBean(strXML, Message.class);
        assertThat(m, notNullValue());
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toBean(java.lang.String, java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testToBean1() throws Exception {
        Message m = XMLDeserialization.toBean(null, Message.class);
        assertThat(m, nullValue());
    }
    
    /**
     * XML不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toBean(java.lang.String, java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testToBean2() {
        String strXML = "<1?xml version='1.0' encoding='utf-8'?><wahaha list_flag='' ><0><data></data><returnMessage></returnMessage><success>false</success><type></type></0><1><data></data><returnMessage></returnMessage><success>false</success><type></type></1></wahaha>";
        Message m = null;
        try {
            m = XMLDeserialization.toBean(strXML, Message.class);
        } catch (Exception e) {
        }
        assertThat(m, nullValue());
    }

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toList(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToList() throws Exception {
        String strXML = "<?xml version='1.0' encoding='utf-8'?><wahaha><_><_>1</_><_>2</_><_>false</_><_>3</_></_><_><data>4</data><returnMessage>5</returnMessage><success>false</success><type>6</type></_></wahaha>";
        List<Message> list = XMLDeserialization.toList(strXML, Message.class);
        assertThat(list, notNullValue());
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toList(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToList1() throws Exception {
        List<Message> list = XMLDeserialization.toList(null, Message.class);
        assertThat(list, nullValue());
    }
    
    /**
     * XML不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toList(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToList2() {
        String strXML = "<?xml version='1.0' encoding='utf-8'?><1wahaha><_><_>1</_><_>2</_><_>false</_><_>3</_></_><_><data>4</data><returnMessage>5</returnMessage><success>false</success><type>6</type></_></wahaha>";
        List<Message> list = null;
        try {
            list = XMLDeserialization.toList(strXML, Message.class);
        } catch (Exception e) {
        }
        assertThat(list, nullValue());
    }

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toMap(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToMap() throws Exception {
        String strXML = "<?xml version='1.0' encoding='utf-8'?><wahaha><M1><data>1</data><type>2</type><success>false</success><returnMessage>3</returnMessage></M1><M2><data>4</data><returnMessage>5</returnMessage><success>false</success><type>6</type></M2></wahaha>";
        Map<String, Message> map = XMLDeserialization.toMap(strXML, Message.class);
        assertThat(map, notNullValue());
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toMap(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testToMap1() throws Exception {
        Map<String, Message> map = XMLDeserialization.toMap(null, Message.class);
        assertThat(map, nullValue());
    }
    
    /**
     * XML不合法
     * Test method for {@link com.dgsoft.dts.web.common.transfer.XMLDeserialization#toMap(java.lang.String)}.
     */
    @Test
    public void testToMap2() {
        String strXML = "<?xml version='1.0' enco1sdsading='utf-8'?><1wahaha><_><<_>1</_sfsada><_>2</_><_>false</_><_>3</_></_><_><data>4</data><returnMessage>5</returnMessage><success>false</success><type>6</type></_></wahaha>";
        Map<String, Message> map = null;
        try {
            map = XMLDeserialization.toMap(strXML, Message.class);
        } catch (Exception e) {
        }
        assertThat(map, nullValue());
    }
}
