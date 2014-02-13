package com.dgsoft.dts.web.common;

import java.util.regex.Pattern;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 常用正则表达式处理类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-14 下午3:27:38 
 * @version 1.0 
 */
public final class RegexHandler {
    
    private final static Logger log = LogManager.getLogger(RegexHandler.class.getName());
    
    /**
     * 检测是否符合email格式
     * @param strEmail String 邮箱格式的字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isValidEmail(String strEmail) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strEmail:%s]", strEmail));
        if (strEmail != null) {
            blnResult = Pattern.matches("^((\\w+)\\.?)+(\\w+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", strEmail);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }

    /**
     * 检测是否符合email格式
     * @param strEmail String 邮箱格式的字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isValidDoEmail(String strEmail) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strEmail:%s]", strEmail));
        if (strEmail != null) {
            return Pattern.matches("^@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", strEmail);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }

    /**
     * 检测是否是正确的URL
     * @param strUrl String URL格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isURL(String strUrl) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strUrl:%s]", strUrl));
        if (strUrl != null) {
            blnResult = Pattern.matches("^(http|https)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{1,10}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\+&%\\$#\\=~_\\-]+))*$", strUrl);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }

    /**
     * 检测是否有SQL危险字符
     * @param strSQL String SQL格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isSafeSqlString(String strSQL) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strSQL:%s]", strSQL));
        if (strSQL != null) {
            blnResult = !Pattern.matches("[-|;|,|\\/|\\(|\\)|\\[|\\]|\\}|\\{|%|@|\\*|!|\\']", strSQL);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }
    
    /**
     * 检测是否有危险的可能用于链接的字符串
     * @param strUserInfo String 用户信息格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isSafeUserInfoString(String strUserInfo) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strUserInfo:%s]", strUserInfo));
        if (strUserInfo != null) {
            blnResult = !Pattern.matches("^\\s*$|^c:\\\\con\\\\con$|[%,\\*\"\\s\\t\\<\\>\\&]|游客|^Guest", strUserInfo);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }
    
    /**
     * 判断字符串是否为数字
     * @param strInt String 数字格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isInt(String strInt) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strInt:%s]", strInt));
        if (strInt != null) {
            blnResult = Pattern.matches("^-?[0-9]*$", strInt);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }
    
    /**
     * 判断字符串是否为浮点数字
     * @param strInt String 数字格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isDouble(String strDouble) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strInt:%s]", strDouble));
        if (strDouble != null) {
            blnResult = Pattern.matches("^-?[0-9]*(\\.[0-9]*)?$", strDouble);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }
    
    /**
     * 判断字符串是否为浮点数字
     * @param strInt String 数字格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isBool(String strBool) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strInt:%s]", strBool));
        if (strBool != null) {
            blnResult = Pattern.matches("^(true)|(false)$", strBool);
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }

    /**
     * 判断字符串是否为时间，格式范围为：[1600-01-01 00:00:00, 9999-12-31 23:59:59]
     * @param strDate String 时间格式字符串
     * @return boolean 是否匹配
     */ 
    public static boolean isDate(String strDate) {
        boolean blnResult = false;
        log.debug(String.format("method start String[strDate:%s]", strDate));
        if (strDate != null) {
            StringBuilder strRegex = new StringBuilder(100);
            
            //日期匹配，格式范围为：1600-01-01 => 9999-12-31
            strRegex.append("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))");
            
            //时间匹配，格式范围为： 00:00:00 => 23:59:59
            strRegex.append("(\\s(((0?[0-9])|(1[0-9])|(2[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
            int intOption = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE | Pattern.COMMENTS;
            Pattern pattern = Pattern.compile(strRegex.toString(), intOption);
            blnResult = pattern.matcher(strDate).matches();
        }
        log.debug(String.format("method stop return:boolean[%s]", blnResult));
        return blnResult;
    }
}