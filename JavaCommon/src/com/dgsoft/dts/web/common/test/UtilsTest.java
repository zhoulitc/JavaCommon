package com.dgsoft.dts.web.common.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.dgsoft.dts.web.common.Utils;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-1-15 下午4:13:22 
 * @version 1.0 
 */
public class UtilsTest {

    /**
     * Test method for {@link com.dgsoft.dts.web.common.Utils#getEmailHostName(java.lang.String)}.
     */
    @Test
    public void testGetEmailHostName() {
        String pwd = "zhouli.con@yahoo.com.cn";
        String pwd2 = Utils.getEmailHostName(pwd);
        assertThat(pwd2, not(""));
    }

    /**
     * 参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.Utils#getEmailHostName(java.lang.String)}.
     */
    @Test
    public void testGetEmailHostName1() {
        String pwd = null;
        String pwd2 = Utils.getEmailHostName(pwd);
        assertThat(pwd2, is(""));
    }
}
