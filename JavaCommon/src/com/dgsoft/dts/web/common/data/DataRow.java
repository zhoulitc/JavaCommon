package com.dgsoft.dts.web.common.data;

import java.util.LinkedHashMap;

/**
 * 行对象，线程安全，但是无法保证内部Map的线程安全性
 * @author li.zhou 
 * @dts.date 2013-1-18 下午5:18:00 
 * @version 1.0 
 */
public final class DataRow extends LinkedHashMap<String,Object> {
    private static final long serialVersionUID = 2517747482952421761L;
    private final DataTable table; 
         
     /**
     * 构造函数
     * @param table DataTable 数据表对象
     * @param intCapacity int 行的初始容量
     */ 
    public DataRow(DataTable table, int intCapacity) {
        super(intCapacity);
        this.table = table;
     }

     /**
     * 获取此数据行对象所属的数据表
     * @return DataTable 数据表对象
     */ 
    public DataTable getTable() {
        return this.table;
    }

     /**
     * 设定单元格的值
     * @param index int 列的索引
     * @param value Object 值
     */ 
    public void setValue(int index, Object value) {
        if (isBounds(index)) {
            setValue(this.table.columns.get(index), value);
        }
    }
       
     /**
     * 设定单元格的值
     * @param name String 列名称
     * @param value Object 值
     */ 
    public void setValue(String name, Object value) {
        this.put(name, value);
    }
    
    /**
     * 设定单元格的值
     * @param name DataColumn 列对象
     * @param value Object 值
     */ 
    public void setValue(DataColumn column, Object value) {
        if (column != null) {
            this.put(column.getColumnName(), value);
        }
    }

    /**
     * 获取单元格的值
     * @param index int 列索引
     * @return Object 值
     */ 
    public Object getValue(int index) {
        Object result = null;
        if (isBounds(index)) {
            result = this.getValue(this.table.columns.get(index));
        }
        return result;
    }
     
    /**
     * 获取单元格的值
     * @param name String 列名称
     * @return Object 值
     */ 
    public Object getValue(String name) {
        return super.get(name);
    }
       
    /**
     * 获取单元格的值
     * @param column DataColumn 列对象
     * @return Object 值
     */ 
    public Object getValue(DataColumn column) {
        Object result = null;
        if (column != null) {
            result = super.get(column.getColumnName());
        }
        return result;
    }
    
    /**
     * 判断索引是否越界
     * @param index int 列索引
     * @return 是否越界
     */ 
    private boolean isBounds(int index) {
        return index >= 0 && index < this.table.columns.size();   
    }
}