package com.dgsoft.dts.web.common.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.dgsoft.dts.web.common.AESHelper;


/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-1-15 上午8:50:43 
 * @version 1.0 
 */
public class AESHelperTest {

    /**
     * 正确
     */ 
    @Test
    public void test() {
        String pwd = "111111";
        String pwd2 = "suapaL++PRREtOvCo4mu6Q==";
        String result = AESHelper.Encrypt(pwd);
        assertThat(result, is(pwd2));
        assertTrue(true);
    }
   
    /**
     * 传入参数不合法
     */ 
    @Test
    public void test1() {
        String result = AESHelper.Encrypt(null);
        assertThat(result, equalTo(""));
    }
}
