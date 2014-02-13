package com.dgsoft.dts.web.common.transfer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.dgsoft.dts.web.common.internal.Reflect;
import com.dgsoft.dts.web.common.transfer.internal.Constant;

/**
 * XML反序列化类，无法保证对象内部的线程安全
 * @author li.zhou 
 * @dts.date 2013-3-1 上午9:40:56 
 * @version 1.0 
 */
public class XMLDeserialization {
    private final static Logger log = LogManager.getLogger(XMLDeserialization.class.getName());

    /**
     * 将XML字符串转换为JavaBean对象，失败返回null
     * @param xml String XML字符串
     * @param cls Class JavaBean的具体类型，需要提供无参的构造函数
     * @return T 一个JavaBean对象
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xml, Class<?> cls) throws Exception {
        T obj = null;
        log.debug(String.format("method start String[xml:%s]", xml));
        Document document = resolveXml(xml);
        if (document != null) {
            obj = (T)toBean(document.getRootElement(), cls);
        }
        log.debug(String.format("method stop return:Object[%s]", obj));
        return obj;
    }
    
    /**
     * 将XML字符串转换成列表，失败返回null
     * @param xml String XML字符串
     * @return List 列表对象
     * @throws Exception 内部会抛出异常
     */ 
    public static List<?> toList(String xml) throws Exception {
        return toList(xml, null);
    }

    /**
     * 将XML字符串转换成列表，失败返回null
     * @param xml String XML字符串
     * @param cls Class value的bean类型，需要提供无参的构造函数
     * @return List 指定了bean类型的泛型列表对象
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String xml, Class<?> cls) throws Exception {
        List<T> list = null;
        log.debug(String.format("method start String[xml:%s]", xml));
        Document document = resolveXml(xml);
        if (document != null) {
            list = (List<T>)toList(document.getRootElement(), cls);
        }
        log.debug(String.format("method stop return:List[%s]", list));
        return list;
    }
    
    /**
     * 将XML字符串转换成映射，失败返回null
     * @param xml String XML字符串
     * @return Map 映射对象
     * @throws Exception 内部会抛出异常
     */ 
    public static Map<String, ?> toMap(String xml) throws Exception {
        return toMap(xml, null);
    }

    /**
     * 将XML字符串转换成映射，失败返回null
     * @param xml String XML字符串
     * @param cls Class value的bean类型，需要提供无参的构造函数
     * @return Map 指定了bean类型的泛型映射对象
     * @throws Exception 内部会抛出异常
     */ 
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toMap(String xml, Class<?> cls) throws Exception {
        Map<String, T> map = null;
        log.debug(String.format("method start String[xml:%s]", xml));
        Document document = resolveXml(xml);
        if (document != null) {
            map = (Map<String, T>)toMap(document.getRootElement(), cls);
        }
        log.debug(String.format("method stop return:Map[%s]", map));
        return map;
    }
    
    /**
     * 将Element对象转换成数组，失败返回null
     * @param parent Element 父节点对象
     * @return Array 数组对象
     * @throws Exception 内部会抛出异常
     */ 
    private static Object[] toArray(Element parent) throws Exception {
        Object[] array = null;
        log.debug(String.format("method start Element[parent:%s]", parent));
        if (parent != null) {
            List<?> table = parent.elements();
            array = new Object[table.size()];
            Object value;
            Element child;
            for (int i = 0, size = table.size(); i < size; i++) {
                child = (Element)table.get(i);
                if (child.elements().size() > 0) { //包含嵌套复杂对象
                    if (child.elements(Constant.XML_LIST_FLAG).size() == 0) { //map
                        value = toMap(child, null);
                    } else { //array
                        value = toArray(child);
                    }
                } else {
                    value = child.getTextTrim();
                }
                array[i] = value;
            }
        }
        log.debug(String.format("method stop return:Object[][%s]", array));
        return array;
    }
    
    /**
     * 将Element对象转换为JavaBean对象，失败返回null
     * @param parent Element 父节点对象
     * @param cls Class JavaBean的具体类型
     * @return Object 一个JavaBean对象，返回后需要转型成指定的Bean
     * @throws Exception 内部会抛出异常
     */ 
    private static Object toBean(Element parent, Class<?> cls) throws Exception {
        Object obj = null;
        log.debug(String.format("method start Element[parent:%s]", parent));
        if (parent != null && cls != null) {   
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
            Class<?> fieldClass;
            String fieldName;
            String fieldType;
            Element child;
            Object value;
            for (Field field : fields) {
                fieldName = field.getName().trim();
                fieldClass = field.getType();
                if (fieldClass != null) {
                    fieldType = fieldClass.getSimpleName().toLowerCase();
                    child = parent.element(fieldName);
                    if (child != null) {
                        if (child.elements().size() > 0) {
                            if (fieldClass.isArray()) { //array
                                value = toArray(child);
                            } else if (Constant.LIST_CLASS.isAssignableFrom(fieldClass)) { //list
                                value = toList(child, null);
                            } else if (Constant.SET_CLASS.isAssignableFrom(fieldClass)) { //set
                                value = toSet(child);
                            } else if (Constant.MAP_CLASS.isAssignableFrom(fieldClass)) { //map
                                value = toMap(child, null);
                            } else { //bean
                                value = toBean(child, fieldClass);
                            } 
                        } else {
                            value = child.getTextTrim();
                        }
                        Reflect.setFieldValue(bean, field, fieldType, value);
                    }
                }
            }
            obj = bean;
        }
        log.debug(String.format("method stop return:Object[%s]", obj));
        return obj;
    }
    
    /**
     * 将Element对象转换成列表，失败返回null
     * @param parent Element 父节点对象
     * @param cls Class value的bean类型
     * @return List 列表对象
     * @throws Exception 内部会抛出异常
     */ 
    private static List<?> toList(Element parent, Class<?> cls) throws Exception {
        List<Object> list = null;
        log.debug(String.format("method start Element[parent:%s]", parent));
        if (parent != null) {
            List<?> table = parent.elements();
            list = new ArrayList<Object>(table.size());
            Object value;
            Element child;
            for (int i = 0, size = table.size(); i < size; i++) {
                child = (Element)table.get(i);
                if (child.elements().size() > 0) { //包含嵌套复杂对象
                    if (child.elements(Constant.XML_LIST_FLAG).size() == 0) {
                        if (cls != null) { //bean
                            value = toBean(child, cls);
                        } else { //map
                            value = toMap(child, null);
                        }
                    } else { //list
                        value = toList(child, null);
                    }
                } else {
                    value = child.getTextTrim();
                }
                list.add(value);
            }
        }
        log.debug(String.format("method stop return:List[%s]", list));
        return list;
    }
    
    /**
     * 将Element对象转换成映射，失败返回null
     * @param parent Element 父节点对象
     * @param cls Class value的bean类型
     * @return Map 映射对象
     * @throws Exception 内部会抛出异常
     */ 
    private static Map<String, ?> toMap(Element parent, Class<?> cls) throws Exception {
        Map<String, Object> map = null;
        log.debug(String.format("method start Element[parent:%s]", parent));
        if (parent != null) {
            List<?> table = parent.elements();
            map = new HashMap<String, Object>(table.size());
            Object value;
            Element child;
            for (int i = 0, size = table.size(); i < size; i++) {
                child = (Element)table.get(i);
                if (child.elements().size() > 0) { //包含嵌套复杂对象
                    if (child.elements(Constant.XML_LIST_FLAG).size() == 0) {
                        if (cls != null) { //bean
                            value = toBean(child, cls);
                        } else { //map
                            value = toMap(child, null);
                        }
                    } else { //list
                        value = toList(child, null);
                    }
                } else {
                    value = child.getTextTrim();
                }
                map.put(child.getName(), value);
            }
        }
        log.debug(String.format("method stop return:Map[%s]", map));
        return map;
    }
    
    /**
     * 将Element对象转换成集合，失败返回null
     * @param parent Element 父节点对象
     * @return Set 集合对象
     * @throws Exception 内部会抛出异常
     */ 
    private static Set<?> toSet(Element parent) throws Exception {
        Set<Object> set = null;
        log.debug(String.format("method start Element[parent:%s]", parent));
        if (parent != null) {
            List<?> table = parent.elements();
            set = new HashSet<Object>(table.size());
            Object value;
            Element child;
            for (int i = 0, size = table.size(); i < size; i++) {
                child = (Element)table.get(i);
                if (child.elements().size() > 0) { //包含嵌套复杂对象
                    if (child.elements(Constant.XML_LIST_FLAG).size() == 0) { //map
                        value = toMap(child, null);
                    } else { //set
                        value = toSet(child);
                    }
                } else {
                    value = child.getTextTrim();
                }
                set.add(value);
            }
        }
        log.debug(String.format("method stop return:Set[%s]", set));
        return set;
    }
    
    /**
     * 将XML字符串转换成XML对象
     * @param xml String XML字符串
     * @return Document XML对象
     * @throws DocumentException 
     */ 
    private static Document resolveXml(String xml) throws DocumentException {
        Document document = null;
        String s = xml == null ? "" : xml.trim();
        if (s.isEmpty()) {
            return document;
        }
        if (!s.startsWith(Constant.XML_HEADER)) {
            s = Constant.XML_HEADER + s;
        }
        document = DocumentHelper.parseText(s);
        return document;
    }
}