package com.dgsoft.dts.web.common.test.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import com.dgsoft.dts.web.common.data.DataExperssion;
import com.dgsoft.dts.web.common.data.DataExperssionFactory;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-1-22 下午12:35:02 
 * @version 1.0 
 */
public class DataExperssionFactoryTest {

    /**
     * 成功
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#And(com.dgsoft.dts.web.common.data.DataExperssion, com.dgsoft.dts.web.common.data.DataExperssion[])}.
     */
    @Test
    public void testAnd() {
        String e = "(a=b and c=d)";
        DataExperssion de = DataExperssionFactory.and(
                DataExperssionFactory.compare("a", "=", "b"),
                DataExperssionFactory.compare("c", "=", "d"));
        String f = de.toString();
        assertThat(f, is(e));
    }
    
    /**
     * 参数为空
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#And(com.dgsoft.dts.web.common.data.DataExperssion, com.dgsoft.dts.web.common.data.DataExperssion[])}.
     */
    @Test
    public void testAnd1() {
        DataExperssion de = DataExperssionFactory.and(null, null);
        String f = de.toString();
        assertThat(f, is("1=2"));
    }
    
    /**
     * 失败
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#And(com.dgsoft.dts.web.common.data.DataExperssion, com.dgsoft.dts.web.common.data.DataExperssion[])}.
     */
    @Test
    public void testAnd2() {
        String e = "a = b and1 c = d";
        DataExperssion de = DataExperssionFactory.and(
                DataExperssionFactory.compare("a", "=", "b"),
                DataExperssionFactory.compare("c", "=", "d"));
        String f = de.toString();
        assertThat(f, not(e));
    }

    /**
     * 成功
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Or(com.dgsoft.dts.web.common.data.DataExperssion, com.dgsoft.dts.web.common.data.DataExperssion[])}.
     */
    @Test
    public void testOr() {
        String e = "(a=b or c=d)";
        DataExperssion de = DataExperssionFactory.or(
                DataExperssionFactory.compare("a", "=", "b"),
                DataExperssionFactory.compare("c", "=", "d"));
        String f = de.toString();
        assertThat(f, is(e));
    }
    
    /**
     * 参数为空
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#And(com.dgsoft.dts.web.common.data.DataExperssion, com.dgsoft.dts.web.common.data.DataExperssion[])}.
     */
    @Test
    public void testOr1() {
        DataExperssion de = DataExperssionFactory.or(null, null);
        String f = de.toString();
        assertThat(f, is("1=2"));
    }
    
    /**
     * 失败
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#And(com.dgsoft.dts.web.common.data.DataExperssion, com.dgsoft.dts.web.common.data.DataExperssion[])}.
     */
    @Test
    public void testOr2() {
        String e = "a = b and1 c = d";
        DataExperssion de = DataExperssionFactory.or(
                DataExperssionFactory.compare("a", "=", "b"),
                DataExperssionFactory.compare("c", "=", "d"));
        String f = de.toString();
        assertThat(f, not(e));
    }

    /**
     * 成功
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compare(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCompare() {
        String e = "a>=b";
        DataExperssion de = DataExperssionFactory.compare("a", ">=", "b");
        String f = de.toString();
        assertThat(f, is(e));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compare(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCompare2() {
        DataExperssion de = DataExperssionFactory.compare(null, "1>=", "？");
        String f = de.toString();
        assertThat(f, is("1=2"));
    }
    
    /**
     * 失败
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compare(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCompare1() {
        String e = "a >>= b";
        DataExperssion de = DataExperssionFactory.compare("a", ">=", "b");
        String f = de.toString();
        assertThat(f, not(e));
    }
    
    /**
     * 成功
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compute(java.lang.String)}.
     */
    @Test
    public void testCompute1() {
        String e = "a=b and c=d and e=f";
        String ee = "((a=b and c=d) and e=f)";
        DataExperssion de = DataExperssionFactory.compute(e);
        String f = de.toString();
        assertThat(f, is(ee));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compute(java.lang.String)}.
     */
    @Test
    public void testCompute4() {
        DataExperssion de = DataExperssionFactory.compute(null);
        String f = de.toString();
        assertThat(f, is("1=2"));
    }
    
    /**
     * 失败
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compute(java.lang.String)}.
     */
    @Test
    public void testCompute2() {
        String e = "a = b and c = d and1 e = f";
        DataExperssion de = DataExperssionFactory.compute(e);
        String f = de.toString();
        assertThat(f, not(e));
    }

    /**
     * 处理and+or的情况
     * Test method for {@link com.dgsoft.dts.web.common.data.DataExperssionFactory#Compute(java.lang.String)}.
     */
    @Test
    public void testCompute() {
        String e = "((((a=b and c=d) or e=f) and g=g) or (h=h and f=f))";
        DataExperssion de = DataExperssionFactory.compute(e);
        String f = de.toString();
        assertThat(f, is("1=2"));
    }

}
