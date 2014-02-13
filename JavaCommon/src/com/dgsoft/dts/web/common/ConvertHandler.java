package com.dgsoft.dts.web.common;

import java.util.List;
import java.util.Map;
import com.dgsoft.dts.web.common.data.DataTable;
import com.dgsoft.dts.web.common.transfer.JSONDeserialization;
import com.dgsoft.dts.web.common.transfer.JSONSerialization;
import com.dgsoft.dts.web.common.transfer.XMLDeserialization;
import com.dgsoft.dts.web.common.transfer.XMLSerialization;

/**
 * 提供数据结构之间的转换，线程非安全
 * @author li.zhou 
 * @dts.date 2013-1-23 下午1:28:54 
 * @version 1.0 
 */
public final class ConvertHandler {
    
    /*
     * Any => XML
     */
    /**
     * 将DataTable对象转换成XML字符串，失败返回""
     * @param dt DataTable 表对象
     * @return String XML字符串
     * @throws Exception 
     */ 
    public static String serializeDataTableToXml(DataTable dt) throws Exception {
        return (dt != null) ? XMLSerialization.from(dt.rows, dt.getTableName()) : "";
    }
    
    /**
     * 将List{Map{String,?}}对象转换成XML字符串，失败返回""
     * @param list List{Map{String,?}} 列表对象
     * @return String XML字符串
     * @throws Exception 
     */ 
    public static String serializeListToXml(List<?> list) throws Exception {
        return XMLSerialization.from(list);
    }
    
    /**
     * 将List{Map{String,?}}对象转换成XML字符串，失败返回""
     * @param list List{Map{String,?}} 列表对象
     * @param strRootName String XML根名称
     * @return String XML字符串
     * @throws Exception 
     */ 
    public static String serializeListToXml(List<?> list, String strRootName) throws Exception {
        return XMLSerialization.from(list, strRootName);
    }

    /*
     * Any <= XML
     */
    /**
     * 将XML字符串转换成DataTable，失败返回空DataTable
     * @param strXML String XML字符串
     * @return DataTable 表对象
     * @throws Exception 
     */ 
    public static DataTable deserializeXmlToDataTable(String strXML) throws Exception{
        DataTable dt = new DataTable(XMLDeserialization.toList(strXML));
        if (strXML != null) {
            int index = strXML.indexOf("?>");
            if (index > 0) {
                int start = strXML.indexOf('<', index + 2) + 1;
                int end = strXML.indexOf('>', index + 3);
                if (start > 0 && end > 0) {
                    dt.setTableName(strXML.substring(start,  end));
                }
            }
        }
        return dt;
    }
    
    /**
     * 将XML字符串转换成List{Map{String,String}}，失败返回null
     * @param strXML String XML字符串
     * @return List{Map{String,String}} 列表对象
     */ 
    public static List<?> deserializeXmlToList(String strXML) throws Exception {
        return XMLDeserialization.toList(strXML);
    }

    /**
     * 将XML字符串转换成List<?>，失败返回null
     * @param strXML String XML字符串
     * @param cls Class<?> list中的指定类型
     * @return List<?> 列表对象
     * @throws Exception 内部会抛出异常
     */ 
    public static <T> List<T> deserializeXmlToList(String strXML, Class<?> cls) throws Exception {
        return XMLDeserialization.toList(strXML, cls);
    }
    
    /**
     * 将XML字符串转换为JavaBean对象，失败返回null
     * @param strXML String XML字符串
     * @param cls Class<?> JavaBean的具体类型
     * @return Object 一个JavaBean对象，返回后需要转型成指定的Bean
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> T deserializeXmlToBean(String strXML, Class<?> cls) throws Exception {
        return (T)XMLDeserialization.toBean(strXML, cls);
    }

    /*
     * Any => JSON
     */
    
    /**
     * 将数据表转换为Json字符串，失败返回""<br>
     * 转换不会带表名，只是一个list(map)
     * @param dt DataTable 数据表
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    public static String serializeDataTableToJson(DataTable dt) throws Exception {
        return JSONSerialization.from(dt.rows);
    }
    
    /**
     * 将数组转换为Json字符串，失败返回""
     * @param array Object[] 集合
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    public static String serializeArrayToJson(Object[] array) throws Exception {
        return JSONSerialization.from(array);
    }
    
    /**
     * 将列表转换为Json字符串，失败返回""
     * @param list List 集合
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    public static String serializeListToJson(List<?> list) throws Exception {
        return JSONSerialization.from(list);
    }
    
    /**
     * 将映射转换为Json字符串，失败返回""
     * @param map Map 映射
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    public static String serializeMapToJson(Map<String, ?> map) throws Exception {
        return JSONSerialization.from(map);
    }
    
    /**
     * 将JavaBean对象转换为Json字符串，失败返回""
     * @param obj Object JavaBean对象
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    public static <T> String serializeBeanToJson(T obj) throws Exception {
        return JSONSerialization.from(obj);
    }
    
    /*
     * Any <= JSON
     */
    /**
     * 将Json字符串转换为JavaBean对象，失败返回null
     * @param strJson String Json字符串
     * @param cls Class JavaBean的具体类型
     * @return Object 一个JavaBean对象，返回后需要转型成指定的Bean
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> T deserializeJsonToBean(String strJson, Class<?> cls) throws Exception {
        return (T)JSONDeserialization.toBean(strJson, cls);
    }

    /**
     * 将Json字符串转换为DataTable，失败返回new DataTable()
     * @param strJson String Json字符串
     * @return DataTable 数据表
     * @throws Exception 内部会抛出异常
     */ 
    public static DataTable deserializeJsonToDataTable(String strJson) throws Exception {
        return new DataTable(JSONDeserialization.toList(strJson));
    }
    
    /**
     * 将Json字符串转换为列表，失败返回null
     * @param strJson String Json字符串
     * @return List 列表
     * @throws Exception 内部会抛出异常
     */ 
    public static List<?> deserializeJsonToList(String strJson) throws Exception {
        return JSONDeserialization.toList(strJson);
    }
    
    /**
     * 将Json字符串转换为列表，失败返回null
     * @param strJson String Json字符串
     * @return List 列表
     * @throws Exception 内部会抛出异常
     */ 
    public static <T> List<T> deserializeJsonToList(String strJson, Class<?> cls) throws Exception {
        return JSONDeserialization.toList(strJson, cls);
    }
    
    /**
     * 将Json字符串转换为映射，失败返回null
     * @param strJson String Json字符串
     * @return Map 映射
     * @throws Exception 内部会抛出异常
     */ 
    public static Map<String, ?> deserializeJsonToMap(String strJson) throws Exception {
        return JSONDeserialization.toMap(strJson);
    }
    
    /**
     * 将Json字符串转换为列表，失败返回null
     * @param strJson String Json字符串
     * @return List 列表
     * @throws Exception 内部会抛出异常
     */ 
    public static <T> Map<String, T> deserializeJsonToMap(String strJson, Class<?> cls) throws Exception {
        return JSONDeserialization.toMap(strJson, cls);
    }
}