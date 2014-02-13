package com.dgsoft.dts.web.common.data.internal;

import com.dgsoft.dts.web.common.data.DataExperssion;
import com.dgsoft.dts.web.common.data.DataRow;

/**
 * 逻辑与数据表达式类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-22 上午8:38:13 
 * @version 1.0 
 */
public final class AndDataExperssion implements DataExperssion {
    private final DataExperssion left;
    private final DataExperssion right;
    
    /**
     * 构造函数
     * @param deLeft DataExperssion 左表达式
     * @param deRight DataExperssion 右表达式
     */ 
    public AndDataExperssion(DataExperssion left, DataExperssion right) {
        this.left = left != null ? left : NullDataExperssion.getInstance();
        this.right = right != null ? right : NullDataExperssion.getInstance();
    }
    
    /** 
     * 判断表达式是否通过
     * @param row DataRow 行对象
     * @return boolean 是否通过
     * @see com.dgsoft.dts.web.common.data.DataExperssion#isPass(com.dgsoft.dts.web.common.data.DataRow)
     */ 
    public boolean isPass(DataRow row) {
        return left.isPass(row) && right.isPass(row);
    }
    
    /** 
     * and表达式的字符串描述
     * @return String 字符串描述
     * @see java.lang.Object#toString()
     */ 
    public String toString() {
        return String.format("(%s and %s)", left.toString(), right.toString());
    }
}