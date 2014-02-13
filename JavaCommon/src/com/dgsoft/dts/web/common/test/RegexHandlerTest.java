package com.dgsoft.dts.web.common.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import com.dgsoft.dts.web.common.RegexHandler;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-1-15 下午12:51:05 
 * @version 1.0 
 */
public class RegexHandlerTest {

    /**
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidEmail() {
        String strDate = "zhouli.11@yahoo.cn";
        boolean result = RegexHandler.isValidEmail(strDate);
        assertThat(result, is(true));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidEmail1() {
        boolean result = RegexHandler.isValidEmail(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidEmail2() {
        String strDate = "@.zhouli.11@yahoo.cn";
        boolean result = RegexHandler.isValidEmail(strDate);
        assertThat(result, is(false));
    }

    /**
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidDoEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidDoEmail() {
        String strDate = "@163.com";
        boolean result = RegexHandler.isValidDoEmail(strDate);
        assertThat(result, is(true));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidDoEmail1() {
        boolean result = RegexHandler.isValidDoEmail(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidDoEmail2() {
        String strDate = "@@yahoo.cn";
        boolean result = RegexHandler.isValidDoEmail(strDate);
        assertThat(result, is(false));
    }

    /**
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isURL(java.lang.String)}.
     */
    @Test
    public void testIsURL() {
        String strDate = "https://www1.baidu1.cn";
        boolean result = RegexHandler.isURL(strDate);
        assertThat(result, is(true));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsURL1() {
        boolean result = RegexHandler.isURL(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsURL2() {
        String strDate = "https12ws://www1.baidu1.cn";
        boolean result = RegexHandler.isURL(strDate);
        assertThat(result, is(false));
    }

    /**
     * TODO 什么情况是错误的，什么情况是正确的？
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isSafeSqlString(java.lang.String)}.
     */
    @Test
    public void testIsSafeSqlString() {
        String strDate = "SELECT COUNT(%) FROM TABLE1";
        boolean result = RegexHandler.isSafeSqlString(strDate);
        assertThat(result, is(true));
    }

    /**
     * TODO 什么情况是错误的，什么情况是正确的？
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isSafeUserInfoString(java.lang.String)}.
     */
    @Test
    public void testIsSafeUserInfoString() {
        String strDate = "admin";
        boolean result = RegexHandler.isSafeUserInfoString(strDate);
        assertThat(result, is(true));
    }

    /**
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isInt(java.lang.String)}.
     */
    @Test
    public void testIsInt() {
        String strDate = "000123";
        boolean result = RegexHandler.isInt(strDate);
        assertThat(result, is(true));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsInt1() {
        boolean result = RegexHandler.isInt(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsInt2() {
        String strDate = "https12ws://www1.baidu1.cn";
        boolean result = RegexHandler.isInt(strDate);
        assertThat(result, is(false));
    }

    /**
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isDate(java.lang.String)}.
     */
    @Test
    public void testIsDate() {
        //String strDate = "2002-01-01";
        //String strDate = "2002-01-01 23:46:46";
        String strDate = "1602-01-31 19:51:22";
        boolean result = RegexHandler.isDate(strDate);
        assertThat(result, is(true));
    }

    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsDate1() {
        boolean result = RegexHandler.isDate(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsDate2() {
        String strDate = "https12ws://www1.baidu1.cn";
        boolean result = RegexHandler.isDate(strDate);
        assertThat(result, is(false));
    }
    
    /**
     * 解析成功
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsDouble() {
        String strDate = "123.123";
        boolean result = RegexHandler.isDouble(strDate);
        assertThat(result, is(true));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsDouble1() {
        boolean result = RegexHandler.isDouble(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsDouble2() {
        String strDate = "123.123.123";
        boolean result = RegexHandler.isDouble(strDate);
        assertThat(result, is(false));
    }
    
    /**
     * 解析成功
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsBool() {
        String strDate = "false";
        boolean result = RegexHandler.isBool(strDate);
        assertThat(result, is(true));
    }
    
    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsBool1() {
        boolean result = RegexHandler.isBool(null);
        assertThat(result, is(false));
    }
    
    /**
     * 解析失败
     * Test method for {@link com.dgsoft.dts.web.common.RegexHandler#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsBool2() {
        String strDate = "123.123.123";
        boolean result = RegexHandler.isBool(strDate);
        assertThat(result, is(false));
    }
}
