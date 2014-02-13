package com.dgsoft.dts.web.common.transfer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.dgsoft.dts.web.common.internal.Reflect;

/**
 * JSON序列化类，无法保证对象内部的线程安全
 * @author li.zhou 
 * @dts.date 2013-3-1 上午8:54:36 
 * @version 1.0 
 */
public final class JSONSerialization {
    private final static Logger log = LogManager.getLogger(JSONSerialization.class.getName());
    
    /**
     * 将泛型对象转换为Json字符串，失败返回""
     * @param obj T 泛型对象
     * @return String json串
     * @throws Exception
     */ 
    public static <T> String from(T obj) throws Exception {
        String json = fromObject(obj).trim();
        if (json.length() < 3) {
            json = "";
        } else if ((json.charAt(0) != '{' && json.charAt(json.length() - 1) != '}') &&
                (json.charAt(0) != '[' && json.charAt(json.length() - 1) != ']')) {
            json = "";
        }
        return json;
    }
    
    /**
     * 将数组转换为Json字符串，失败返回"[]"
     * @param array Object[] 集合
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromArray(Object[] array) throws Exception {
        String json = "[]";
        log.debug(String.format("method start Object[][array:%s]", array));
        if (array != null && array.length > 0) {
            StringBuilder sb = new StringBuilder(array.length * 10); //假设一个元素平均长度为10
            sb.append("[");
            for (Object obj : array) {
                sb.append(fromObject(obj)).append(",");
            }
            sb.setCharAt(sb.length() - 1, ']');
            json = sb.toString();
        } 
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将JavaBean对象转换为Json字符串，失败返回"{}"
     * @param obj T JavaBean对象
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    private static <T> String fromBean(T obj) throws Exception {
        String json = "{}";
        log.debug(String.format("method start Object[obj:%s]", obj));
        if (obj != null) {   
            Class<?> cls = obj.getClass();
            Field[] fields = Reflect.getFields(cls);
            StringBuilder sb = new StringBuilder(fields.length * 10); //假设10是一个键值对的平均长度
            sb.append("{");
            Object value;
            for (Field field : fields) {
                value = Reflect.getFieldValue(obj, field);
                sb.append("\"").append(field.getName()).append("\":")
                    .append(fromObject(value)).append(",");
            }
            sb.setCharAt(sb.length() - 1, '}');
            json = sb.toString();
        }
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将集合转换为Json字符串，失败返回"[]"
     * @param collection Collection 集合
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromCollection(Collection<?> collection) throws Exception {
        String json = "[]";
        log.debug(String.format("method start Collection[collection:%s]", collection));
        if (collection != null && collection.size() > 0) {
            StringBuilder sb = new StringBuilder(collection.size() * 10); //假设一个元素平均长度为10
            sb.append("[");
            for (Object obj : collection) {
                sb.append(fromObject(obj)).append(",");
            }
            sb.setCharAt(sb.length() - 1, ']');
            json = sb.toString();
        } 
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
    
    /**
     * 将映射转换为Json字符串，失败返回"{}"
     * @param map Map 映射
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromMap(Map<?, ?> map) throws Exception {
        String json = "{}";
        log.debug(String.format("method start Map[map:%s]", map));
        if (map != null && map.size() > 0) {
            StringBuilder sb = new StringBuilder(map.size() * 10); //假设10是一个键值对的平均长度
            sb.append("{");
            Object obj;
            for (Object key : map.keySet()) {
                obj = map.get(key);
                sb.append("\"").append(key.toString()).append("\":").append(fromObject(obj)).append(",");
            }
            sb.setCharAt(sb.length() - 1, '}');
            json = sb.toString();
        } 
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }

    /**
     * 将泛型对象转换为Json字符串，失败返回"{}"或"[]"
     * @param obj T 泛型对象
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    private static <T> String fromObject(T obj) throws Exception {
        String json = "\"\"";
        if (obj != null) {
            if (obj instanceof String || obj instanceof Character || obj instanceof Date) { //字符和日期类型
                json = fromString(obj.toString());
            } else if (obj instanceof Boolean || obj instanceof Number) { //布尔和数字类型
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
    
    /**
     * 将字符串转换为Json字符串，失败返回""
     * @param s String 字符串
     * @return String json串
     * @throws Exception 内部会抛出异常
     */ 
    private static String fromString(String s) {
        String json = "\"\"";
        log.debug(String.format("method start String[s:%s]", s));
        if (s != null) {
            StringBuilder sb = new StringBuilder(s.length());
            sb.append("\"");
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                switch (ch) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') { //对于Unicode编码的处理
                        String hex = Integer.toHexString(ch).toUpperCase();
                        sb.append("\\u");
                        for (int j = 0, size = 4 - hex.length(); j < size; j++) {
                            sb.append('0');
                        }
                        sb.append(hex);
                    } else {
                        sb.append(ch);
                    }
                }
            }
            json = sb.append("\"").toString();
        }
        log.debug(String.format("method stop return:String[%s]", json));
        return json;
    }
}