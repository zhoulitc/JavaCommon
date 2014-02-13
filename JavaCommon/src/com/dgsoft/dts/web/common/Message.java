package com.dgsoft.dts.web.common;

import java.io.Serializable;

/**
 * 消息实体类，线程非安全<br>
 * 用与DTSWeb与浏览器js脚本通信的javaBean对象
 * @author li.zhou 
 * @dts.date 2013-1-14 下午3:50:05 
 * @version 1.0 
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -8077191514649030784L;

    private Object data;
    private String returnMessage;
    private boolean success;
    private String type;
    
    /**
     * @return Object
     */
    public final Object getData() {
        return data;
    }

    /**
     * @param data Object
     */
    public final void setData(Object data) {
        this.data = data;
    }

    /**
     * @return String
     */
    public final String getReturnMessage() {
        return returnMessage;
    }

    /**
     * @param returnMessage String
     */
    public final void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    /**
     * @return boolean
     */
    public final boolean isSuccess() {
        return success;
    }

    /**
     * @param success boolean
     */
    public final void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return String
     */
    public final String getType() {
        return type;
    }

    /**
     * @param type String
     */
    public final void setType(String type) {
        this.type = type;
    }
    
    /**
     * 构造函数
     */ 
    public Message() {
        data = null;
        returnMessage = "";
        type = "";
        success = false;
    }
    
    /** 
     * 获取实体的字符串描述
     * @return String 实体的字符串描述
     * @see java.lang.Object#toString()
     */ 
    public final String toString() {
        return String.format("{\"type\":\"%s\",\"success\":\"%s\",\"returnMessage\":\"%s\",\"data\":\"%s\"}",
                type, success, returnMessage, data);
    }
}