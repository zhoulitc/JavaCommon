package com.dgsoft.dts.web.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Random;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 验证码处理类，线程非安全
 * @author li.zhou 
 * @dts.date 2013-1-14 下午3:55:55 
 * @version 1.0 
 */
public final class ValidationCode {
    
    private final static Logger log = LogManager.getLogger(ValidationCode.class.getName());
    
    private String validationCode = "";
    private int width;
    private int height;
    
    private static final Random RANDOM;
    private static final Font FONT;
    private static final String FONT_FACE;
    private static final int FONT_SIZE;
    private static final Color FORE_COLOR;
    private static final Color BACK_COLOR;
    private static final Color LINE_COLOR;
    
    static {
        RANDOM = new Random();
        FONT_FACE = "Tahoma";
        FONT_SIZE = 28;
        FONT = new Font(FONT_FACE, Font.ROMAN_BASELINE, FONT_SIZE);
        FORE_COLOR = new Color(128, 128, 255);
        //BACK_COLOR = new Color(100, 150, 155); //背景绿色
        BACK_COLOR = new Color(190, 190, 190); //背景灰色
        //BACK_COLOR = new Color(192, 64, 0); //背景红色
        LINE_COLOR = new Color(220, 220, 220);
    }
    
    public String getValidationCode() {
        return validationCode;
    }
    
    /**
     * 获得一个验证码图片，暂时采用64*30的大小
     * @return RenderedImage 图片对象
     */ 
    public RenderedImage nextImage() {
        return nextImage(4, true);
    }
    
    /**
     * 获得一个验证码图片
     * @param length int 验证码产生的码个数
     * @return RenderedImage 图片对象
     */ 
    public RenderedImage nextImage(int length) {
        return nextImage(length, true);
    }
    
    /**
     * 获得一个验证码图片
     * @param length int 验证码产生的码个数，允许的区间为4~8位，其他都默认4位
     * @param allowMixedLines boolean 是否产生干扰线
     * @return RenderedImage 图片对象
     */ 
    public RenderedImage nextImage(int length, boolean allowMixedLines) {
        log.debug(String.format("method start int[length:%d],boolean[allowMixedLines:%s]", length, allowMixedLines));
        if (length <= 4 || length > 8) {
            length = 4; 
        }
        
        //synchronized(this) {
            validationCode = getRandomCode(length);
            width = FONT.getSize() * length / 2 + length * 1 + 4;
            height = FONT.getSize() * 1 + 2;
        //}
        
        //生成验证码图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);       
        Graphics graphics = image.getGraphics();   
        drawBackground(graphics);
        drawValidationCode(graphics, validationCode);
        if (allowMixedLines) {
            drawMixedLine(graphics);
        }
        graphics.dispose();
        log.debug("method stop return:BufferedImage");
        return image;
    }
    
    /**
     * 绘制图片背景
     * @param graphics Graphics 绘图对象
     */ 
    private void drawBackground(Graphics graphics) { 
        graphics.setColor(BACK_COLOR);
        graphics.fillRect(0, 0, width, height);   
    }

    /**
     * 绘制随机码
     * @param graphics Graphics 绘图对象
     * @param strCode String 随机码字符串
     */ 
    private void drawValidationCode(Graphics graphics, String strCode) {
        graphics.setFont(FONT);
        graphics.setColor(FORE_COLOR);
        graphics.drawString(strCode, 2, 25);
    }
    
    /**
     * 绘制干扰线
     * @param graphics Graphics 绘图对象
     */ 
    private void drawMixedLine(Graphics graphics) { 
        graphics.setColor(LINE_COLOR);
        for (int i = 0, size = RANDOM.nextInt(8) + 8; i < size; i++) {
            graphics.drawLine(randomPoint(), randomPoint(), randomPoint(), randomPoint());
        }
    }

    /**
     * 获取随机码
     * @param length
     * @return
     */ 
    private String getRandomCode(int length) {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < length; i++) {
            sb.append(getAZ09());
        }
        return sb.toString();
    }

    /**
     * 获取随机字符码
     * @return char A-Z,0-9的随机字符码 
     */ 
    private char getAZ09() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
        return (char)(RANDOM.nextInt(10) + 48);
    }

    /**
     * 随机生成一个干扰点
     * @return int 点坐标值
     */ 
    private int randomPoint() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
        Point point = new Point(RANDOM.nextInt(width), RANDOM.nextInt(height));
        return RANDOM.nextBoolean() ? point.y : point.x;
    }
}
