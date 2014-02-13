package com.dgsoft.dts.web.common.test.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import com.dgsoft.dts.web.common.Message;
import com.dgsoft.dts.web.common.data.*;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-1-21 上午9:30:39 
 * @version 1.0 
 */
public class DataTableTest {

    /**
     * Test method for {@link com.dgsoft.dts.web.common.data.DataTable#newRow()}.
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    @Test
    public void test() throws Exception {
        //list=>datatable
       List<Message> list = new ArrayList<Message>();
       Message m = new Message();
       m.setData("data5");
       m.setReturnMessage("rm1");
       m.setSuccess(true);
       m.setType("type1");
       list.add(m);
       m = new Message();
       m.setData("data2");
       m.setReturnMessage("rm2");
       m.setSuccess(false);
       m.setType("type4");
       list.add(m);
       m = new Message();
       m.setData("data3");
       m.setReturnMessage("rm3");
       m.setSuccess(false);
       m.setType("type3");
       list.add(m);
       
       // sort请不要使用多个列名
       // select, sort, init 
       DataTable t1 = new DataTable(list);
       DataTable sor = t1.sort("type desc");
       DataTable adr = t1.select("data = 'data2' and returnMessage = 'rm1'");
       DataTable adr1 = t1.select("data = 'data2' and (returnMessage = 'rm1' or success = false)");
       
       //用DataExperssionFactory构造复杂的select表达式
       //"data = 'data1' and (returnMessage = 'rm1' or type like 'type%')"
       DataTable ad2r = t1.select(DataExperssionFactory.and(
               DataExperssionFactory.compare("data", "=", "'data1'"), 
               DataExperssionFactory.or(
                       DataExperssionFactory.compare("returnMessage", "=", "'rm1'"), 
                       DataExperssionFactory.compare("type", "like", "'type%'"))));
       
       //listmap=>datatable
       List<Map> list1 = new ArrayList<Map>();
       Map m1 = new HashMap();
       m1.put("col1", "data1");
       m1.put("col2", "rm1");
       m1.put("col3", true);
       m1.put("col4", "type1");
       list1.add(m1);
       m1 = new HashMap();
       m1.put("col1", "data2");
       m1.put("col2", "rm2");
       m1.put("col3", false);
       m1.put("col4", "type2");
       list1.add(m1);
       DataTable t2 = new DataTable("t2", list1);
        
        //table、column
        DataTable table = new DataTable("test");
        table.columns.add("col1");
        table.columns.add("col2");
        table.columns.add("col3");
        DataColumn dc = table.columns.add();
        dc.setColumnName("col4");
        
        //row
        DataRow dr = table.newRow();
        dr.setValue("col1", 1);
        dr.setValue("col2", 2);
        dr.setValue(2, 3);
        dr.setValue("col4", 4);
        Object value = dr.getValue(10);
        assertThat(value, nullValue());
        table.rows.add(dr);
        table.rows.add(1,2,3,4);
        
        
        //copy、clone、clear
        DataTable dt = table.copy();
        DataTable dt1 = table.clone();
        dt1.rows.add(11,22,33,44);
        dt1.rows.add(dr);
        dt.columns.add("col5");
        table.clear();
        
        assertThat(table, anything());
    }
}
