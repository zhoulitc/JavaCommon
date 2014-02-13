package com.dgsoft.dts.web.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 未分类的常用类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-14 下午3:29:46 
 * @version 1.0 
 */
public final class Utils {
    
    private final static Logger log = LogManager.getLogger(Utils.class.getName());
    
    /**
     * 截取邮箱中的主机名，失败返回""
     * @param strEmail String 邮箱全名
     * @return String 截取后的邮箱主机名
     */ 
    public static String getEmailHostName(String strEmail) {
        String strResult = "";
        log.debug(String.format("method start String[strEmail:%s]", strEmail));
        if (strEmail != null) {
            int index = strEmail.lastIndexOf("@");
            if (index >= 0) {
                strResult = strEmail.substring(index).toLowerCase();
            }
        }
        log.debug(String.format("method stop return:String[%s]", strResult));
        return strResult;
    }
}