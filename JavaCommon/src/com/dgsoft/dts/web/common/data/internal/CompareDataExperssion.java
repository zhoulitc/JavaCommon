package com.dgsoft.dts.web.common.data.internal;

import java.util.regex.Pattern;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.dgsoft.dts.web.common.data.DataExperssion;
import com.dgsoft.dts.web.common.data.DataRow;

/**
 * 比较运算数据表达式，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-22 上午8:44:02 
 * @version 1.0 
 */
public final class CompareDataExperssion implements DataExperssion {
    private final static Logger log = LogManager.getLogger(CompareDataExperssion.class.getName());
    private final String name;
    private final String oper;
    private final String value;
    private enum StringCompare { EQ, NE, LIKE };
    private final static String INTEGER_REGEX = "^-?[0-9]*$";
    private final static String DOUBLE_REGEX = "^-?[0-9]*(\\.[0-9]*)?$";
    
    /**
     * 构造函数
     * @param strName String 字段名
     * @param strOper String 操作符
     * @param strValue String 字段值
     */ 
    public CompareDataExperssion(String name, String oper, String value) {
        this.name = initName(name);
        this.oper = oper != null ? oper : "=";
        this.value = initValue(value);
    }
    
    /** 
     * 判断某行是否通过表达式验证
     * @param row DataRow 行对象
     * @return boolean 是否通过
     * @see com.dgsoft.dts.web.common.data.DataExperssion#isPass(com.dgsoft.dts.web.common.data.DataRow)
     */ 
    public boolean isPass(DataRow row) {
        boolean result = false;
        if (row != null && this.name.length() > 0 && this.value.length() > 0) {
            log.debug(String.format("method start DataRow[row:%s]", row));
            Object value = row.getValue(this.name);
            if (value != null) {
                if (this.oper.equals("=") || this.oper.equals("==")) { //只用字符串判断等于、不等于和模糊匹配操作
                    result = computeStringExperssion(value.toString(), StringCompare.EQ);
                } else if (this.oper.equals("!=") || this.oper.equals("<>")) {
                    result = computeStringExperssion(value.toString(), StringCompare.NE);
                } else if (this.oper.equals("like")) { 
                    result = computeStringExperssion(value.toString(), StringCompare.LIKE);
                } else {
                    if (Pattern.matches(DOUBLE_REGEX, this.value)) { //浮点数
                        try {
                            result = computeDoubleExperssion(Double.valueOf(value.toString()), Double.valueOf(this.value));
                        } catch (NumberFormatException e) {
                            log.error(e.getMessage(), e);
                            result = false;
                        }
                    } else if (Pattern.matches(INTEGER_REGEX, this.value)){ //整数
                        try {
                            result = computeIntegerExperssion(Integer.valueOf(value.toString()), Integer.valueOf(this.value));
                        } catch (NumberFormatException e) {
                            log.error(e.getMessage(), e);
                            result = false;
                        }
                    }
                }
            }
        }
        log.debug(String.format("method stop return:boolean[%s]", result));
        return result;
    }
    
    /** 
     * 比较运算表达式的字符串描述
     * @return String 字符串描述
     * @see java.lang.Object#toString()
     */ 
    public String toString() {
        return String.format("%s%s%s", name, oper, value);
    }
    
    /**
     * 初始化表达式名称
     * @param name String 表达式名称
     * @return String 表达式名称
     */ 
    private String initName(String name) {
        String result = "";
        if (name != null) {
            if (name.startsWith("(") && !name.contains(")")) {
                result = name.substring(1);
            } else {
                result = name;
            }
        }
        return result.trim();
    }
    
    /**
     * 初始化表达式值
     * @param value String 表达式值
     * @return String 表达式值
     */ 
    private String initValue(String value) {
        String result = "";
        if (value != null) {
            if (value.startsWith("'") && value.endsWith("'")) {
                value = value.substring(1, value.length() - 1);
            }
            if (value.endsWith(")") && !value.contains("(")) {
                result = value.substring(0, value.length() - 1);
            } else {
                result = value;
            }
        }
        return result.trim();
    }
    
    /**
     * 解析字符串比较表达式
     * @param value Object 比较值
     * @return boolean 是否匹配
     */ 
    private boolean computeStringExperssion(String value, StringCompare compare) {
        boolean result;
        switch (compare) {
            case EQ:
                result = value.equals(this.value);
                break;
            case NE:
                result = !value.equals(this.value);
                break;
            case LIKE:
                if ((this.value.startsWith("%") && this.value.endsWith("%")) || 
                        (this.value.startsWith("*") && this.value.endsWith("*"))) { //前后匹配
                    result = value.contains(this.value.substring(1, this.value.length() - 1));
                } else if (this.value.endsWith("%") || this.value.endsWith("*")) { //后匹配
                    result = value.startsWith(this.value.substring(this.value.length() - 1));
                } else {
                    result = false;
                }
                break;
            default: 
                result = false;
                break;
        }
        return result;
    }
    
    /**
     * 解析整数比较表达式
     * @param value1 int 比较值
     * @param value2 int 比较值
     * @return boolean 是否匹配
     */ 
    private boolean computeIntegerExperssion(int value1, int value2) throws NumberFormatException {
        boolean result;
        if (this.oper.equals(">=")) {
            result = value1 >= value2;
        } else if (this.oper.equals("<=")) {
            result = value1 <= value2;
        } else if (this.oper.equals(">")) {
            result = value1 > value2;
        } else if (this.oper.equals("<")) {
            result = value1 < value2;
        } else {
            result = false;
        }
        return result;
    }
    
    /**
     * 解析浮点数比较表达式
     * @param value1 double 比较值
     * @param value2 double 比较值
     * @return boolean 是否匹配
     */ 
    private boolean computeDoubleExperssion(double value1, double value2) throws NumberFormatException  {
        boolean result;
        if (this.oper.equals(">=")) {
            result = value1 >= value2;
        } else if (this.oper.equals("<=")) {
            result = value1 <= value2;
        } else if (this.oper.equals(">")) {
            result = value1 > value2;
        } else if (this.oper.equals("<")) {
            result = value1 < value2;
        } else {
            result = false;
        }
        return result;
    }
}