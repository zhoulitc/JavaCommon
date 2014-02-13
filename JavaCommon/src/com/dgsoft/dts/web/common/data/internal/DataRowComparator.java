package com.dgsoft.dts.web.common.data.internal;

import java.util.Comparator;
import java.util.regex.Pattern;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.dgsoft.dts.web.common.data.DataRow;

/**
 * 行比较器，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-22 下午1:43:01 
 * @version 1.0 
 */
public final class DataRowComparator implements Comparator<DataRow>{
    private final static Logger log = LogManager.getLogger(DataRowComparator.class.getName());
    private final static String INTEGER_REGEX = "^-?[0-9]*$";
    private final static String DOUBLE_REGEX = "^-?[0-9]*(\\.[0-9]*)?$";
    
    private final boolean isDesc;
    private final String sortName;
    
    /**
     * 构造函数
     * @param strSortFields String 排序列集合
     */ 
    public DataRowComparator(String sortFields) {
        String fields = sortFields.trim();
        if (fields.indexOf(",") > 0) { //暂未实现处理多个列名的情况
            sortName = "";
            isDesc = false;
        } else {
            if (fields.toUpperCase().endsWith(" DESC")) {
                sortName = fields.substring(0, fields.lastIndexOf(" ")).trim();
                isDesc = true;
            } else {
                isDesc = false;
                if (fields.toUpperCase().endsWith(" ASC")) {
                    sortName = fields.substring(0, fields.lastIndexOf(" ")).trim();
                } else {
                    sortName = fields;
                }
            }
        }
    }
    
    /** 
     * 比较
     * @param row1 DataRow 比较的行对象
     * @param row2 DataRow 比较的行对象
     * @return int 比较值：大于0表示dr1>dr2, 小于0表示dr1<dr2, 等于0表示dr1==dr2
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */ 
    public int compare(DataRow row1, DataRow row2) {  
        int result = 0;
        if (row1 != null && row2 != null && sortName.length() > 0) {
            log.debug(String.format("method start DataRow[row1:%s],DataRow[row2:%s]", row1, row2));
            Object object1 = row1.getValue(sortName);
            Object object2 = row2.getValue(sortName);
            if (object1 != null && object2 == null) {
                result = isDesc ? -1 : 1;
            } else if (object1 == null && object2 != null) {
                result = isDesc ? 1 : -1;
            } else if (object1 != null && object2 != null) {
                String value1 = object1.toString();
                String value2 = object2.toString();
                if (Pattern.matches("^(true)|(false)$", value1.toLowerCase())) { //布尔值
                    result = booleanCompare(Boolean.valueOf(value1), Boolean.valueOf(value2));
                } else if (Pattern.matches(DOUBLE_REGEX, value1)) { //浮点数
                    result = doubleCompare(Double.valueOf(value1), Double.valueOf(value2));
                } else if (Pattern.matches(INTEGER_REGEX, value1)){ //整数
                    result = integerCompare(Integer.valueOf(value1), Integer.valueOf(value2));
                } else { //字符串
                    result = value1.compareTo(value2);
                    if (isDesc) {
                        result = -result;
                    }
                }
            } 
        }
        log.debug(String.format("method stop return:int[%d]", result));
        return result;
    }
    
    /**
     * 比较布尔值
     * @param b1
     * @param b2
     * @return int 比较值
     */ 
    private int booleanCompare(boolean b1, boolean b2) {
        int result = 0;
        if (b1 && !b2) {
            result = isDesc ? -1 : 1;
        } else if (b2 && !b1) {
            result = isDesc ? 1 : -1;
        }
        return result;
    }
    
    /**
     * 比较浮点数
     * @param d1 double
     * @param d2 double
     * @return int 比较值
     */ 
    private int doubleCompare(double d1, double d2) {
        int result = 0;
        if (d1 > d2) {
            result = isDesc ? -1 : 1;
        } else if (d2 < d1) {
            result = isDesc ? 1 : -1;
        }
        return result;
    }
    
    /**
     * 比较整数
     * @param i1 int
     * @param i2 int
     * @return int 比较值
     */ 
    private int integerCompare(int i1, int i2) {
        int result = 0;
        if (i1 > i2) {
            result = isDesc ? -1 : 1;
        } else if (i2 < i1) {
            result = isDesc ? 1 : -1;
        }
        return result;
    }
}