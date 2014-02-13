package com.dgsoft.dts.web.common.transfer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.dgsoft.dts.web.common.internal.Reflect;
import com.dgsoft.dts.web.common.transfer.internal.Constant;

/**
 * JSON反序列化类，无法保证对象内部的线程安全<br>
 * JSONArray=>ArrayList, JSONObject=>HashMap
 * @author li.zhou 
 * @dts.date 2013-3-1 上午9:06:42 
 * @version 1.0 
 */
public final class JSONDeserialization {
    private final static Logger log = LogManager.getLogger(JSONDeserialization.class.getName());
    
    /**
     * 将Json字符串转换为JavaBean对象，失败返回null
     * @param json String Json字符串
     * @param cls Class JavaBean的具体类型，需要提供无参的构造函数
     * @return T 一个JavaBean对象
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, Class<?> cls) throws Exception {
        T bean = null;
        log.debug(String.format("method start String[json:%s]", json));
        JSON JSON = resolveJson(json);
        if (JSON != null && !JSON.isArray()) {
            bean = (T)toBean((JSONObject)JSON, cls);
        }
        log.debug(String.format("method stop return:T[%s]", bean));
        return bean;
    } 
    
    /**
     * 将Json字符串转换为列表，失败返回null
     * @param json String Json字符串
     * @return List 列表
     * @throws Exception 内部会抛出异常
     */ 
    public static List<?> toList(String json) throws Exception {
        return toList(json, null);
    }
    
    /**
     * 将Json字符串转换为列表，失败返回null
     * @param json String Json字符串
     * @param cls Class value的bean类型，需要提供无参的构造函数
     * @return List 指定了bean类型的泛型列表对象
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String json, Class<?> cls) throws Exception {
        List<T> list = null;
        log.debug(String.format("method start String[json:%s]", json));
        JSON JSON = resolveJson(json);
        if (JSON != null && JSON.isArray()) {
            list = (List<T>)toList((JSONArray)JSON, cls); 
        }
        log.debug(String.format("method stop return:List[%s]", list));
        return list;
    }
    
    /**
     * 将Json字符串转换为映射，失败返回null
     * @param json String Json字符串
     * @return Map 映射
     * @throws Exception 内部会抛出异常
     */ 
    public static Map<String, ?> toMap(String json) throws Exception {
        return toMap(json, null);
    }
   
    /**
     * 将Json字符串转换为映射，失败返回null
     * @param json String Json字符串
     * @param cls Class value的bean类型，需要提供无参的构造函数
     * @return Map 指定了bean类型的泛型映射对象
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toMap(String json, Class<?> cls) throws Exception {
        Map<String, T> map = null;
        log.debug(String.format("method start String[json:%s]", json));
        JSON JSON = resolveJson(json);
        if (JSON != null && !JSON.isArray()) {
            map = (Map<String, T>)toMap((JSONObject)JSON, cls);
        }
        log.debug(String.format("method stop return:Map[%s]", map));
        return map;
    }
    
    /**
     * 将Json字符串转换为集合，失败返回null
     * @param json JSONArray Json数组
     * @return Array 数组
     * @throws Exception 内部会抛出异常
     */ 
    private static Object[] toArray(JSONArray json) throws Exception {
        Object[] array = null;
        log.debug(String.format("method start JSONArray[json:%s]", json));
        if (json != null) {   
            array = new Object[json.size()];
            Object value;
            for (int i = 0, size = json.size(); i < size; i++) {
                value = json.get(i);
                if (value instanceof JSONObject) {
                    value = toMap((JSONObject)value, null);
                } else if (value instanceof JSONArray) {
                    value = toArray((JSONArray)value);
                }
                array[i] = getValue(value);
            }
            json.clear();
        }
        log.debug(String.format("method stop return:Object[][%s]", array));
        return array;
    }
    
    /**
     * 将Json字符串转换为JavaBean对象，失败返回null
     * @param json JSONObject Json对象
     * @param cls Class JavaBean的具体类型，需要提供无参的构造函数
     * @return Object 一个JavaBean对象，返回后需要转型成指定的Bean
     * @throws Exception 内部会抛出异常
     */ 
    private static Object toBean(JSONObject json, Class<?> cls) throws Exception {
        Object obj = null; 
        log.debug(String.format("method start JSONObject[json:%s]", json));
        if (json != null && cls != null) {
            Field[] fields = Reflect.getFields(cls);
            Object bean;
            if (cls.getName().lastIndexOf('$') > 0) { //内部类
                Class<?> father = Class.forName(cls.getName().substring(0, cls.getName().lastIndexOf('$')));
                Constructor<?> ctor = cls.getDeclaredConstructors()[0];
                ctor.setAccessible(true);
                bean = ctor.newInstance(father.newInstance());
            } else {
                bean = cls.newInstance();
            }
            Object value;
            Class<?> fieldClass;
            String fieldName;
            String fieldType;
            for (Field field : fields) {
                fieldName = field.getName().trim();
                if (json.containsKey(fieldName)) {
                    value = json.get(fieldName);
                    fieldClass = field.getType();
                    if (fieldClass != null) {
                        fieldType = fieldClass.getSimpleName().toLowerCase();
                        if (value instanceof JSONObject) {
                            if (Constant.MAP_CLASS.isAssignableFrom(fieldClass)) { //map
                                value = toMap((JSONObject)value, null);
                            } else { //bean
                                value = toBean((JSONObject)value, fieldClass);
                            }
                        } else if (value instanceof JSONArray) {
                            if (fieldClass.isArray()) { //array
                                value = toArray((JSONArray)value);
                            } else if (Constant.LIST_CLASS.isAssignableFrom(fieldClass)) { //list
                                value = toList((JSONArray)value, null);
                            } else if (Constant.SET_CLASS.isAssignableFrom(fieldClass)) { //set
                                value = toSet((JSONArray)value);
                            }
                        }
                        Reflect.setFieldValue(bean, field, fieldType, getValue(value));
                    }
                }
            }
            obj = bean;
            json.clear();
        }
        log.debug(String.format("method stop return:Object[%s]", obj));
        return obj;
    } 
    
    /**
     * 将Json字符串转换为列表，失败返回null
     * @param json JSONArray Json数组
     * @param cls Class value的bean类型
     * @return List 列表
     * @throws Exception 内部会抛出异常
     */ 
    //@SuppressWarnings("unchecked")
    private static List<?> toList(JSONArray json, Class<?> cls) throws Exception {
        List<Object> list = null;
        log.debug(String.format("method start JSONArray[json:%s]", json));
        if (json != null) {   
            list = new ArrayList<Object>(json.size());
            //list = (List<Object>)json;
            Object value;
            for (int i = 0, size = json.size(); i < size; i++) {
                value = json.get(i);
                if (value instanceof JSONObject) {
                    if (cls != null) {
                        value = toBean((JSONObject)value, cls);
                    } else {
                        value = toMap((JSONObject)value, null);
                    }
                } else if (value instanceof JSONArray) {
                    value = toList((JSONArray)value, null);
                }
                list.add(getValue(value));
                //list.set(i, value);
            }
            json.clear();
        }
        log.debug(String.format("method stop return:List[%s]", list));
        return list;
    }
    
    /**
     * 将Json字符串转换为映射，失败返回null
     * @param json JSONObject Json对象
     * @param cls Class value的bean类型
     * @return Map 映射
     * @throws Exception 内部会抛出异常
     */ 
    private static Map<String, ?> toMap(JSONObject json, Class<?> cls) throws Exception {
        Map<String, Object> map = null;
        log.debug(String.format("method start JSONObject[json:%s]", json));
        if (json != null) {   
            map = new HashMap<String, Object>(json.size());
            //map = (Map<String, Object>)jsonObject;
            String key;
            Object value;
            for (Iterator<?> i = json.keys(); i.hasNext();) {
                key = i.next().toString();
                value = json.get(key);
                if (value instanceof JSONObject) {
                    if (cls != null) {
                        value = toBean((JSONObject)value, cls);
                    } else {
                        value = toMap((JSONObject)value, null);
                    }
                } else if (value instanceof JSONArray) {
                    value = toList((JSONArray)value, null);
                }
                map.put(key, getValue(value));
            }
            json.clear();
        }
        log.debug(String.format("method stop return:Map[%s]", map));
        return map;
    }
    
    /**
     * 将Json字符串转换为集合，失败返回null
     * @param json JSONArray Json数组
     * @return Set 集合
     * @throws Exception 内部会抛出异常
     */ 
    private static Set<?> toSet(JSONArray json) throws Exception {
        Set<Object> set = null;
        log.debug(String.format("method start JSONArray[json:%s]", json));
        if (json != null) {   
            set = new HashSet<Object>(json.size());
            Object value;
            for (int i = 0, size = json.size(); i < size; i++) {
                value = json.get(i);
                if (value instanceof JSONObject) {
                    value = toMap((JSONObject)value, null);
                } else if (value instanceof JSONArray) {
                    value = toSet((JSONArray)value);
                }
                set.add(getValue(value));
            }
            json.clear();
        }
        log.debug(String.format("method stop return:Set[%s]", set));
        return set;
    }
    
    private static Object getValue(Object value) {
        if (value instanceof String) {
            String s = (String)value;
            if (s.startsWith("\"{") && s.endsWith("}\"") || 
                s.startsWith("\"[") && s.endsWith("]\""))
                value = s.substring(1, s.length() - 1);
        }
        return value;
    }
    
    /**
     * 解析json串，将其转化为json对象
     * @param json String json字符串
     * @return JSON json对象
     * @throws Exception 转换会抛出异常
     */ 
    private static JSON resolveJson(String json) throws Exception {
        JSON JSON= null;
        String s = json == null ? "" : json.trim();
        if (s.isEmpty()) {
            return JSON;
        }
        if (s.charAt(0) == '\"' && s.charAt(s.length() - 1) == '\"') { //排除序列化带来的双引号
            s = s.substring(1, s.length() - 1);
        }
        if (s.charAt(0) == '{') {
            JSON = JSONObject.fromObject(s);
        } else if (s.charAt(0) == '[') {
            JSON = JSONArray.fromObject(s);
        }
        return JSON;
    }
}