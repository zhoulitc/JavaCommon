package com.dgsoft.dts.web.common.transfer.internal;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 常量类
 * @author li.zhou 
 * @dts.date 2013-3-19 下午2:25:34 
 * @version 1.0 
 */
public final class Constant {
    public final static Class<?> LIST_CLASS = List.class;
    public final static Class<?> SET_CLASS = Set.class;
    public final static Class<?> MAP_CLASS = Map.class;
    public final static String XML_LIST_FLAG = "_";
    public final static String XML_HEADER = "<?xml version='1.0' encoding='utf-8'?>";  
    public final static String XML_HEADER_FORMAT = XML_HEADER + "<%s>%s</%s>";  
    public final static String XML_DEFUALT_ROOT = "Root";
}