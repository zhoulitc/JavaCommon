package com.dgsoft.dts.web.common.data.internal;

import com.dgsoft.dts.web.common.data.DataExperssion;
import com.dgsoft.dts.web.common.data.DataRow;

/**
 * 空表达式类，总是返回false，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-22 上午9:58:16 
 * @version 1.0 
 */
public final class NullDataExperssion implements DataExperssion {
    
    /**
     * Null Object 总是单例的
     * @return NullDataExperssion的唯一实例
     */ 
    public static NullDataExperssion getInstance() {
        return instance;
    }
    private NullDataExperssion() {}
    private final static NullDataExperssion instance = new NullDataExperssion();
    
    /** 
     * 判断某行是否通过表达式验证
     * @param row DataRow 行对象
     * @return boolean 是否通过
     * @see com.dgsoft.dts.web.common.data.DataExperssion#isPass(com.dgsoft.dts.web.common.data.DataRow)
     */ 
    public boolean isPass(DataRow row) {
        return false;
    }
    
    /** 
     * 空表达式的字符串描述
     * @return String 字符串描述
     * @see java.lang.Object#toString()
     */ 
    public String toString() {
        return "1=2";
    }
}