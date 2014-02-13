package com.dgsoft.dts.web.common.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.dgsoft.dts.web.common.ValidationCode;

/**
 * TODO(一句话描述此类的功能)
 * @author li.zhou 
 * @dts.date 2013-1-14 下午5:21:43 
 * @version 1.0 
 */
public class ValidationCodeTest {

    /**
     * 正确
     * Test method for {@link com.dgsoft.dts.web.common.ValidationCode#nextImage(int, boolean)}.
     */
    @Test
    public void testNextImage1() {
        ValidationCode vc = new ValidationCode();
        RenderedImage image = vc.nextImage(4, true);
        try {
            ImageIO.write(image, "PNG", new File("d:\\test\\test.png")); 
        } catch(Exception e) {
            
        }
        String str= vc.getValidationCode();
        assertThat(str.length(),  is(4));
    }
    
    /**
     * 传入参数不合法
     * Test method for {@link com.dgsoft.dts.web.common.ValidationCode#nextImage(int, boolean)}.
     */
    @Test
    public void testNextImage() {
        ValidationCode vc = new ValidationCode();
        RenderedImage image = vc.nextImage(11111111, true);
        try {
            ImageIO.write(image, "PNG", new File("d:\\test\\test.png")); 
        } catch(Exception e) {
            
        }
        String str= vc.getValidationCode();
        assertThat(str.length(), is(4));
    }
}