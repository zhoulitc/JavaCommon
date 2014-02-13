package com.dgsoft.dts.web.common.data;

import java.util.ArrayList;

/**
 * 行集合，线程安全，但是无法保证内部List的线程安全性
 * @author li.zhou 
 * @dts.date 2013-1-18 下午6:07:58 
 * @version 1.0 
 */
public class DataRowCollection extends ArrayList<DataRow>{
    private static final long serialVersionUID = -7093927138595626595L;
    private final DataTable table;

    /**
     * 构造函数
     * @param table DataTable 表对象
     */ 
    public DataRowCollection(DataTable table) {
        this.table = table;
    }
    
    /** 
     * 添加一行对象
     * @param row DataRow 行对象
     * @return 添加是否成功
     * @see java.util.ArrayList#add(java.lang.Object)
     */ 
    public boolean add(DataRow row) {
        boolean result = false;
        if (row.getTable() == this.table) {
            result = super.add(row);
        }
        return result;
    }
    
    /**
     * 添加一行数据
     * @param values Object 行数据集合
     * @return 添加是否成功
     */ 
    public boolean add(Object ...values) {
        final int length = values.length > this.table.columns.size() ? this.table.columns.size() : values.length;
        DataRow row = new DataRow(this.table, length);
        for (int i = 0; i < length; i++) {
            row.setValue(i, values[i]);
        }
        return super.add(row);
    }
    
    /** 
     * 清除所有行数据
     * @see java.util.ArrayList#clear()
     */ 
    @Override
    public void clear() {
        for (int i = 0, size = size(); i < size; i++) {
            get(i).clear();
        }
        super.clear();
    }
}