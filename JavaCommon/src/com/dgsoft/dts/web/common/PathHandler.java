package com.dgsoft.dts.web.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 路径处理类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-30 上午10:50:55 
 * @version 1.0 
 */
public final class PathHandler {
    private final static Logger log = LogManager.getLogger(PathHandler.class.getName());
    
    /**
     * 获取当前类的根路径，通常以bin、war、jar、classes结尾。失败返回""
     * @param cls Class 指定类型，不要用Class.class
     * @return String 所在路径
     */ 
    public static String getClassRootPath(Class<?> cls) {
        String strPath = "";
        if (cls != null) {
            try {
                strPath = URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            } 
        }
        return strPath;  
    }
    
    /**
     * 获取当前项目的根路径，通常以bin、classes结尾。失败返回""
     * @param cls Class 指定类型，不要用Class.class
     * @return String 所在路径
     */ 
    public static String getProjectRootPath(Class<?> cls) {
        String strPath = "";
        if (cls != null) {
            try {
                strPath = URLDecoder.decode(cls.getResource("/").getPath(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            } 
        }
        return strPath;  
    }
    
    /**
     * 获取当前Web项目的根路径。失败返回""
     * @param cls Class 指定类型，不要用Class.class
     * @return String 所在路径
     */ 
    public static String getWebRootPath(Class<?> cls) {
        String strPath = getProjectRootPath(cls);
        int index = strPath.indexOf("WEB-INF");  
        if (index > 0) {  
            strPath = strPath.substring(0, index);  
        } else {
            strPath = "";
        }
        return strPath;  
    }
}