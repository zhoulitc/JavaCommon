package com.dgsoft.dts.web.common.transfer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.dgsoft.dts.web.common.internal.Reflect;
import com.dgsoft.dts.web.common.transfer.internal.Constant;

/**
 * XML序列化类，无法保证对象内部的线程安全
 * @author li.zhou 
 * @dts.date 2013-3-1 上午9:32:14 
 * @version 1.0 
 */
public final class XMLSerialization {
    private final static Logger log = LogManager.getLogger(XMLSerialization.class.getName());
    
    /**
     * 将泛型对象转换为xml字符串，失败返回""
     * @param obj T 泛型对象
     * @return String xml串
     * @throws Exception
     */ 
    public static <T> String from(T obj) throws Exception {
        return from(obj, Constant.XML_DEFUALT_ROOT);
    }
    
    /**
     * 将泛型对象转换为xml字符串，失败返回""
     * @param obj T 泛型对象
     * @param root String 根元素名称
     * @return String xml串
     * @throws Exception
     */ 
    public static <T> String from(T obj, String root) throws Exception {
        String json = fromObject(obj).trim();
        if (json.length() < 3) {
            json = "";
        } else if ((json.charAt(0) != '<' && json.charAt(json.length() - 1) != '>')) {
            json = "";
        } else {
            if (root == null || root.isEmpty()) {
                root = Constant.XML_DEFUALT_ROOT;
            }
            json = String.format(Constant.XML_HEADER_FORMAT, root, json, root); 
        }
        return json;
    }
    
    /**
     * 将数组转换为xml字符串，失败返回""
     * @param array Object[] 集合
     * @return String xml串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromArray(Object[] array) throws Exception {
        String json = "";
        log.debug(String.format("method start Object[][array:%s]", array));
        if (array != null && array.length > 0) {
            StringBuilder sb = new StringBuilder(array.length * 20); //假设一个元素平均长度为20
            for (int i = 0, size = array.length; i < size; i++) {
                sb.append('<').append(Constant.XML_LIST_FLAG).append('>')
                    .append(fromObject(array[i]))
                    .append("</").append(Constant.XML_LIST_FLAG).append('>');
            }
            json = sb.toString();
        } 
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将JavaBean对象转换为xml字符串，失败返回""
     * @param obj T JavaBean对象
     * @return String xml串
     * @throws Exception 内部会抛出异常
     */ 
    private static <T> String fromBean(T obj) throws Exception {
        String json = "";
        log.debug(String.format("method start Object[obj:%s]", obj));
        if (obj != null) {   
            Class<?> cls = obj.getClass();
            Field[] fields = Reflect.getFields(cls);
            StringBuilder sb = new StringBuilder(fields.length * 20); //假设20是一个键值对的平均长度
            String name;
            Object value;
            for (Field field : fields) {
                name = field.getName();
                value = Reflect.getFieldValue(obj, field);
                sb.append('<').append(name).append('>')
                    .append(fromObject(value))
                    .append("</").append(name).append('>');
            }
            json = sb.toString();
        }
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将集合转换为xml字符串，失败返回""
     * @param collection Collection 集合
     * @return String xml串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromCollection(Collection<?> collection) throws Exception {
        String json = "";
        log.debug(String.format("method start Collection[collection:%s]", collection));
        if (collection != null && collection.size() > 0) {
            StringBuilder sb = new StringBuilder(collection.size() * 20); //假设一个元素平均长度为20
            for (Object obj : collection) {
                sb.append('<').append(Constant.XML_LIST_FLAG).append('>')
                    .append(fromObject(obj))
                    .append("</").append(Constant.XML_LIST_FLAG).append('>');  
            }
            json = sb.toString();
        } 
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将映射转换为xml字符串，失败返回""
     * @param map Map 映射
     * @return String xml串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromMap(Map<?, ?> map) throws Exception {
        String json = "";
        log.debug(String.format("method start Map[map:%s]", map));
        if (map != null && map.size() > 0) {
            StringBuilder sb = new StringBuilder(map.size() * 20); //假设20是一个键值对的平均长度
            String name;
            Object value;
            for (Object key : map.keySet()) {
                name = key.toString().trim();
                value = map.get(key);
                sb.append('<').append(name).append('>')
                    .append(fromObject(value))
                    .append("</").append(name).append('>');
            }
            json = sb.toString();
        } 
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将泛型对象转换为xml字符串，失败返回""
     * @param obj T 泛型对象
     * @return String xml串
     * @throws Exception 内部会抛出异常
     */ 
    private static <T> String fromObject(T obj) throws Exception {
        String json = "";
        if (obj != null) {
            if (obj instanceof String || obj instanceof Character || obj instanceof Date || 
                obj instanceof Boolean || obj instanceof Number) { //布尔、数字、字符和日期类型
                json = obj.toString();
            } else if (obj instanceof Collection) { //集合类型，包括list和set
                json =fromCollection((Collection<?>) obj);
            } else if (obj instanceof Map) { //映射类型
                json = fromMap((Map<?, ?>) obj);
            } else if (obj instanceof Object[]) { //数组类型
                json = fromArray((Object[]) obj);
            } else { //bean
                json = fromBean(obj);
            }
        } 
        return json;
    }
}