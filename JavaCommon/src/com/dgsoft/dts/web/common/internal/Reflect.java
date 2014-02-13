package com.dgsoft.dts.web.common.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射处理类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-28 下午3:28:55 
 * @version 1.0 
 */
public final class Reflect {
    
    /**
     * 获取一个类的所有字段，包括父类
     * @param cls Class 类对象
     * @return Field[] 字段数组
     */ 
    public static Field[] getFields(Class<?> cls) {
        Field[] afidResult;
        if (cls != null) {
            List<Field> list = new ArrayList<Field>();
            getFields(cls, list);
            afidResult = new Field[list.size()];
            list.toArray(afidResult);
        } else {
            afidResult = new Field[0];
        }
        return afidResult;
    }
    
    /**
     * 递归获取包括父类中的字段
     * @param cls 类型
     * @param list 字段列表
     */ 
    private static void getFields(Class<?> cls, List<Field> list) {
        if (cls.getSimpleName().equals("Object")) {
            return;
        }
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if ((fields[i].getModifiers() & Modifier.STATIC) !=  Modifier.STATIC) { //排除static
                list.add(fields[i]);
            }
        }
        getFields(cls.getSuperclass(), list);
    }
    
    /**
     * 获取字段的值
     * @param bean 类的实例
     * @param field 类的字段类型
     * @return Object 要获取的值
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */ 
    public static Object getFieldValue(Object bean, Field field) throws
            SecurityException, 
            IllegalArgumentException, 
            NoSuchMethodException, 
            IllegalAccessException, 
            InvocationTargetException {
        Object objResult = null;
        if (bean != null && field != null) {
            if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                objResult = field.get(bean);
            } else {
                objResult = getPropertyValue(bean, field);
            }
        }
        return objResult;
    }
    
    /**
     * 获取属性的值
     * @param bean 类的实例
     * @param field 类的字段类型
     * @return Object 要获取的值
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */ 
    private static Object getPropertyValue(Object bean, Field field) throws 
            SecurityException, 
            NoSuchMethodException, 
            IllegalArgumentException, 
            IllegalAccessException, 
            InvocationTargetException {
        Object objResult = null;
        Method method = null;
        if (field.getType().getSimpleName().toLowerCase().equals("boolean")) {
            method = bean.getClass().getMethod(String.format("is%s%s", 
                    field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1)));
        } else {
            method = bean.getClass().getMethod(String.format("get%s%s", 
                    field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1)));
        }
        if (method != null) {
            objResult = method.invoke(bean);
        }
        return objResult;
    }
    
    /**
     * 设置字段的值
     * @param bean 类的实例
     * @param field 类的字段类型
     * @param type 类的字段类型名
     * @param value 要设置的值
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InvocationTargetException 
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */ 
    public static void setFieldValue(Object bean, Field field, String type, Object value) throws 
            IllegalArgumentException, 
            IllegalAccessException, 
            SecurityException, 
            NoSuchMethodException, 
            InvocationTargetException {
        if (bean != null && field != null && type != null) {
            if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC){
                if (type.equals("boolean")) {
                    field.setBoolean(bean, Boolean.valueOf(value.toString()));
                } else if (type.equals("int")) {
                    field.setInt(bean, Integer.valueOf(value.toString()));
                } else if (type.equals("byte")) {
                    field.setByte(bean, Byte.valueOf(value.toString()));
                } else if (type.equals("double")) {
                    field.setDouble(bean, Double.valueOf(value.toString()));
                } else if (type.equals("float")) {
                    field.setFloat(bean, Float.valueOf(value.toString()));
                }  else if (type.equals("long")) {
                    field.setLong(bean, Long.valueOf(value.toString()));
                } else if (type.equals("short")) {
                    field.setShort(bean, Short.valueOf(value.toString()));
                }  else if (type.equals("char")) {
                    field.setChar(bean, value.toString().charAt(0));
                }  else {
                    field.set(bean, value);
                }
            } else if ((field.getModifiers() & Modifier.FINAL)  != Modifier.FINAL) { //不为final
                setPropertyValue(bean, field, type, value);
            }
        }
    }
    
    /**
     * 设置属性的值
     * @param bean 类的实例
     * @param field 类的字段类型
     * @param type 类的字段类型名
     * @param value 要设置的值
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */ 
    private static void setPropertyValue(Object bean, Field field, String type, Object value) throws 
            SecurityException, 
            NoSuchMethodException, 
            IllegalArgumentException, 
            IllegalAccessException, 
            InvocationTargetException {
        String name = String.format("set%s%s", 
                field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1));
        Method method = bean.getClass().getMethod(name, field.getType());
        if (method != null && type != null) {
            if (type.equals("boolean")) { 
                method.invoke(bean, Boolean.valueOf(value.toString()));
            } else if (type.equals("int")) {
                method.invoke(bean, Integer.valueOf(value.toString()));
            } else if (type.equals("byte")) {
                method.invoke(bean, Byte.valueOf(value.toString()));
            } else if (type.equals("double")) {
                method.invoke(bean, Double.valueOf(value.toString()));
            } else if (type.equals("float")) {
                method.invoke(bean, Float.valueOf(value.toString()));
            }  else if (type.equals("long")) {
                method.invoke(bean, Long.valueOf(value.toString()));
            } else if (type.equals("short")) {
                method.invoke(bean, Short.valueOf(value.toString()));
            }  else if (type.equals("char")) {
                method.invoke(bean, value.toString().charAt(0));
            } else {
                method.invoke(bean, value);
            }
        }
    }
}