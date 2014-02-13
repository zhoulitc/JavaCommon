package com.dgsoft.dts.web.common.data;

/**
 * 数据表达式接口
 * @author li.zhou 
 * @dts.date 2013-1-22 上午9:34:33 
 * @version 1.0 
 */
public interface DataExperssion {
    /**
     * 判断表达式是否通过
     * @param row DataRow 行对象
     * @return boolean 是否通过
     */ 
    boolean isPass(DataRow row);
}