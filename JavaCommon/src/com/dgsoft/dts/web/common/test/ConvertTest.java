package com.dgsoft.dts.web.common.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import com.dgsoft.dts.web.common.ConvertHandler;
import com.dgsoft.dts.web.common.data.DataTable;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-3-19 上午9:52:20 
 * @version 1.0 
 */
public class ConvertTest {

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.ConvertHandler#serializeDataTableToXml(com.dgsoft.dts.web.common.data.DataTable)}.
     * @throws Exception 
     */
    @Test
    public void testSerializeDataTableToXml() throws Exception {
        DataTable dt = new DataTable();
        dt.setTableName("table");
        dt.columns.add("col1");
        dt.columns.add("col2");
        dt.rows.add(1, 2);
        dt.rows.add(3, 4);
        String xml = ConvertHandler.serializeDataTableToXml(dt);
        String strResult = "<?xml version='1.0' encoding='utf-8'?>" +
        		"<table>" +
        		    "<_>" +
        		        "<col1>1</col1>" +
        		        "<col2>2</col2>" +
    		        "</_>" +
    		        "<_>" +
    		            "<col1>3</col1>" +
    		            "<col2>4</col2>" +
		            "</_>" +
	            "</table>";
        assertThat(xml, is(strResult));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.ConvertHandler#serializeDataTableToXml(com.dgsoft.dts.web.common.data.DataTable)}.
     * @throws Exception 
     */
    @Test
    public void testSerializeDataTableToXml1() throws Exception {
        String xml = ConvertHandler.serializeDataTableToXml(null);
        String strResult = "";
        assertThat(xml, is(strResult));
    }

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.ConvertHandler#deserializeXmlToDataTable(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeserializeXmlToDataTable() throws Exception {
        String xml = "<?xml version='1.0' encoding='utf-8'?>" +
                "<table>" +
                    "<____>" +
                        "<col1>1</col1>" +
                        "<col2>2</col2>" +
                    "</____>" +
                    "<____>" +
                        "<col1>3</col1>" +
                        "<col2>4</col2>" +
                    "</____>" +
                "</table>";
        DataTable dt = ConvertHandler.deserializeXmlToDataTable(xml);
        assertThat(dt, notNullValue());
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.ConvertHandler#deserializeXmlToDataTable(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeserializeXmlToDataTable1() throws Exception {
        DataTable dt = ConvertHandler.deserializeXmlToDataTable(null);
        assertThat(dt, notNullValue());
    }
}
