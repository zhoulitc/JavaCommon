package com.dgsoft.dts.web.common.data.internal;

import com.dgsoft.dts.web.common.data.DataExperssion;
import com.dgsoft.dts.web.common.data.DataRow;

/**
 * 逻辑或数据表达式类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-22 上午8:42:09 
 * @version 1.0 
 */
public final class OrDataExperssion implements DataExperssion {
    private final DataExperssion left;
    private final DataExperssion right;
    
    /**
     * 构造函数
     * @param deLeft DataExperssion 左表达式
     * @param deRight DataExperssion 右表达式
     */ 
    public OrDataExperssion(DataExperssion left, DataExperssion right) {
        this.left = left != null ? left : NullDataExperssion.getInstance();
        this.right = right != null ? right : NullDataExperssion.getInstance();
    }
    
    /** 
     * 判断某行是否通过表达式验证
     * @param row DataRow 行对象
     * @return boolean 是否通过
     * @see com.dgsoft.dts.web.common.data.DataExperssion#isPass(com.dgsoft.dts.web.common.data.DataRow)
     */ 
    public boolean isPass(DataRow row) {
        return left.isPass(row) || right.isPass(row);
    }
    
    /** 
     * or表达式的字符串描述
     * @return String 字符串描述
     * @see java.lang.Object#toString()
     */ 
    public String toString() {
        return String.format("(%s or %s)", left.toString(), right.toString());
    }
}
