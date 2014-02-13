package com.dgsoft.dts.web.common.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.dgsoft.dts.web.common.data.internal.AndDataExperssion;
import com.dgsoft.dts.web.common.data.internal.CompareDataExperssion;
import com.dgsoft.dts.web.common.data.internal.NullDataExperssion;
import com.dgsoft.dts.web.common.data.internal.OrDataExperssion;

/**
 * 数据表达式构造工厂，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-22 上午9:51:47 
 * @version 1.0 
 */
public final class DataExperssionFactory {
    
    private final static Logger log = LogManager.getLogger(DataTable.class.getName());
    
    //目前支持的操作符
    private final static String[] opers = new String[]{ "=", "==", "!=", "<>", ">=", "<=", ">", "<", "like"};
    
    /**
     * 用and连接所有表达式
     * @param first DataExperssion 第一个表达式
     * @param second DataExperssion 第二个表达式
     * @param others DataExperssion 随后的表达式组
     * @return DataExperssion 表达式对象
     */ 
    public static DataExperssion and(DataExperssion first, DataExperssion second, DataExperssion ...others) {
        DataExperssion result;
        log.debug(String.format("method start DataExperssion[first:%s],DataExperssion[second:%s],DataExperssion...[others:%s]", 
                first, 
                second, 
                others));
        if (null == first || null == second) {
            result = NullDataExperssion.getInstance();
        } else{
            result = new AndDataExperssion(first, second);
            if (others != null) {
                for (int i = 0; i < others.length; i++) {
                    result = new AndDataExperssion(result, others[i]);
                }
            }
        }
        log.debug(String.format("method stop return:DataExperssion[%s]", result));
        return result;
    }
    
    /**
     * 用or连接所有表达式
     * @param first DataExperssion 第一个表达式
     * @param second DataExperssion 第二个表达式
     * @param others DataExperssion 随后的表达式组
     * @return DataExperssion 表达式对象
     */ 
    public static DataExperssion or(DataExperssion first, DataExperssion second, DataExperssion ...others) {
        DataExperssion result;
        log.debug(String.format("method start DataExperssion[first:%s],DataExperssion[second:%s],DataExperssion...[others:%s]", 
                first, 
                second, 
                others));
        if (null == first || null == second) {
            result = NullDataExperssion.getInstance();
        } else{
            result = new OrDataExperssion(first, second);
            if (others != null) {
                for (int i = 0; i < others.length; i++) {
                    result = new OrDataExperssion(result, others[i]);
                }
            }
        }
        log.debug(String.format("method stop return:DataExperssion[%s]", result));
        return result;
    }
    
    /**
     * 构造一个比较运算表达式
     * @param name String 字段名
     * @param oper String 操作符
     * @param value String 字段值
     * @return DataExperssion 表达式对象
     */ 
    public static DataExperssion compare(String name, String oper, String value) {
        DataExperssion result;
        log.debug(String.format("method start String[strName:%s],String[strOper:%s],String[strValue:%s]", 
                name, 
                oper, 
                value));
        if (null == name || 0 == name.length() ||
            null == oper || 0 == oper.length() || 
            null == value || 0 == value.length()) {
            result = NullDataExperssion.getInstance();
        } else {
            result = new CompareDataExperssion(name, oper, value);
        }
        log.debug(String.format("method stop return:DataExperssion[%s]", result));
        return result;
    }
    
    /**
     * 解析表达式字符串并构造一个表达式对象，and和or请小写
     * @param filterExperssion String 表达式字符串
     * @return DataExperssion 表达式对象
     */ 
    public static DataExperssion compute(String filterExperssion) {
        DataExperssion result = null;
        log.debug(String.format("method start String[filterExperssion:%s]", filterExperssion));
        if (null == filterExperssion || 0 == filterExperssion.length()) {
            result = NullDataExperssion.getInstance();
        } else {
            String experssion = filterExperssion.trim();
            int intAnd = experssion.indexOf(" and ");
            int or = experssion.indexOf(" or ");
            if (intAnd >= 0 && or >= 0) { //解析And/Or树型表达式
                result = resolveTreeExperssion(experssion);
            } else if (intAnd >= 0 && or < 0) { //解析And数组表达式
                result = resolveAndExperssion(experssion);
            } else if (intAnd < 0 && or >= 0) { //解析Or数组表达式
                result = resolveOrExperssion(experssion);
            } else { //解析简单表达式
                result = resolveCompareExperssion(experssion);
            }
        }
        log.debug(String.format("method stop return:DataExperssion[%s]", result));
        return result;
    }
    
    /**
     * 列表转数组
     * @param list List 列表
     * @return DataExperssion[] 数组
     */ 
    private static DataExperssion[] toArray(List<DataExperssion> list) {
        DataExperssion[] array = new DataExperssion[list.size()];
        list.toArray(array);
        return array;
    }
    
    /**
     * 用and和or分隔字符串，and和or不会被丢弃，存在于数组中
     * @param filterExperssion String 字符串表达式
     * @return String[] 分隔后的串数组
     */ 
    private static String[] getSplit(String filterExperssion) {
        List<String> list = new LinkedList<String>();
        int and = filterExperssion.indexOf(" and ");
        int or = filterExperssion.indexOf(" or ");
        int start = 0;
        int end = 0;
        if (and > 0 && (and < or || or < 0)) {
            end = and;
            list.add(filterExperssion.substring(start, end).trim());
            list.add("and");
            start = end + 5;
        } else if (or > 0 && (or < and || and < 0)) {
            end = or;
            list.add(filterExperssion.substring(start, end).trim());
            list.add("or");
            start = end + 4;
        }
        while (and > 0 || or > 0) {
            if (and > 0) {
                and = filterExperssion.indexOf(" and ", start);
            }
            if (or > 0) {
                or = filterExperssion.indexOf(" or ", start);
            }
            if (and > 0 && (and < or || or < 0)) {
                end = and;
                list.add(filterExperssion.substring(start, end).trim());
                list.add("and");
                start = end + 5;
            } else if (or > 0 && (or < and || and < 0)) {
                end = or;
                list.add(filterExperssion.substring(start, end).trim());
                list.add("or");
                start = end + 4;
            }
        }
        list.add(filterExperssion.substring(start).trim());
        String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }
    
    /**
     * 解析树型表达式，and和or的优先级一致，也就是谁在前就先计算谁。需要更改优先级请使用括号，例如a=b and (c=d or e=f)
     * @param filterExperssion String 表达式字符串
     * @return DataExperssion 表达式对象
     */ 
    private static DataExperssion resolveTreeExperssion(String filterExperssion) {
        DataExperssion result = null;
        String[] experssions = getSplit(filterExperssion);
        String experssion;
        StringBuilder subExperssion;
        int count;
        String andOr = "";
        for (int i = 0; i < experssions.length; i++) {
            experssion = experssions[i].trim();
            if (experssion.equals("and")) {
                andOr = "and";
            } else if (experssion.equals("or")) {
                andOr = "or";
            } else {
                count = 0;
                if (experssion.startsWith("(")) {
                    count++;
                    subExperssion = new StringBuilder(experssion);
                    for (int j = i + 1; j < experssions.length; j++) {
                        experssion = experssions[j].trim();
                        if (experssion.equals("and")) {
                            subExperssion.append(" and ");
                        } else if (experssion.equals("or")) {
                            subExperssion.append(" or ");
                        } else if (experssion.endsWith(")")) {
                            count--;
                            subExperssion.append(experssion);
                            if (0 == count) {
                                i = j + 1;
                                break;
                            } 
                        } else {
                            if (experssion.startsWith("(")) {
                                count++;
                            } 
                            subExperssion.append(experssion);
                        }
                    }
                    if (count > 0) {
                        i = experssions.length;
                    }
                    subExperssion = subExperssion.deleteCharAt(0).deleteCharAt(subExperssion.length() - 1);
                    if (null == result) {
                        result = resolveTreeExperssion(subExperssion.toString());
                    } else {
                        if (andOr.equals("or")) {
                            result = or(result, resolveTreeExperssion(subExperssion.toString()));
                        } else if (andOr.equals("and")) {
                            result = and(result, resolveTreeExperssion(subExperssion.toString()));
                        } else {
                            result = NullDataExperssion.getInstance();
                            break;
                        }
                    }
                } else {
                    if (null == result) {
                        result = resolveCompareExperssion(experssion);
                    } else {
                        if (andOr.equals("or")) {
                            result = or(result, resolveCompareExperssion(experssion));
                        } else if (andOr.equals("and")) {
                            result = and(result, resolveCompareExperssion(experssion));
                        } else {
                            result = NullDataExperssion.getInstance();
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 解析OR表达式
     * @param filterExperssion String 表达式字符串
     * @return DataExperssion 表达式对象
     */ 
    private static DataExperssion resolveOrExperssion(String filterExperssion) {
        DataExperssion result = null;
        String[] experssions = filterExperssion.trim().split(" or ");
        DataExperssion first = resolveCompareExperssion(experssions[0]);
        DataExperssion second = resolveCompareExperssion(experssions[1]);
        List<DataExperssion> list = new ArrayList<DataExperssion>(experssions.length - 2);
        for (int i = 2; i < experssions.length; i++) {
            if (experssions[i].trim().length() > 0) {
                list.add(resolveCompareExperssion(experssions[i]));
            }
        }
        result = or(first, second, toArray(list));
        return result;
    }
    
    /**
     * 解析AND表达式
     * @param filterExperssion String 表达式字符串
     * @return DataExperssion 表达式对象
     */ 
    private static DataExperssion resolveAndExperssion(String filterExperssion) {
        DataExperssion result = null;
        String[] experssions = filterExperssion.trim().split(" and ");
        DataExperssion first = resolveCompareExperssion(experssions[0]);
        DataExperssion second = resolveCompareExperssion(experssions[1]);
        List<DataExperssion> list = new ArrayList<DataExperssion>(experssions.length - 2);
        for (int i = 2; i < experssions.length; i++) {
            if (experssions[i].trim().length() > 0) {
                list.add(resolveCompareExperssion(experssions[i]));
            }
        }
        result = and(first, second, toArray(list));
        return result;
    }
    
    /**
     * 解析比较运算表达式
     * @param filterExperssion String 表达式字符串
     * @return DataExperssion 表达式对象
     */ 
    private static DataExperssion resolveCompareExperssion(String filterExperssion) {
        DataExperssion result = null;
        String experssion = filterExperssion.trim();
        String name;
        String oper;
        String value;
        int start = 0;
        for (int i = 0; i < opers.length; i++) {
            if ((start = experssion.indexOf(opers[i])) > 0) {
                name = experssion.substring(0, start).trim();
                oper = experssion.substring(start, start + opers[i].length()).trim();
                value = experssion.substring(start + opers[i].length()).trim();
                if (name.indexOf(" ") < 0 && value.indexOf(" ") < 0) {
                    result = new CompareDataExperssion(name, oper, value);
                } else {
                    start = 0;
                }
                break;
            }
        }
        if (start <= 0) {
            result = NullDataExperssion.getInstance();
        }
        return result;
    }
}