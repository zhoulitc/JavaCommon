package com.dgsoft.dts.web.common.data;

import java.util.ArrayList;

/**
 * 列集合，线程安全，但是无法保证内部List的线程安全性
 * @author li.zhou 
 * @dts.date 2013-1-18 下午5:29:48 
 * @version 1.0 
 */
public final class DataColumnCollection extends ArrayList<DataColumn>{
    private static final long serialVersionUID = 8998811251652662394L; 
    private final DataTable table;
        
    /**
     * 构造函数
     * @param table DataTable 表
     */ 
    public DataColumnCollection(DataTable table) {
        this.table = table;
    }
        
    /**
     * 添加一个数据列到列集合中
     * @return DataColumn 数据列对象
     */ 
    public DataColumn add() {
        return add("");
    }
    
    /** 
     * 添加一个列到列集合中
     * @param column DataColumn 列对象
     * @return 添加是否成功
     * @see java.util.ArrayList#add(java.lang.Object)
     */ 
    @Override
    public boolean add(DataColumn column) {  
        DataColumn dc = new DataColumn(this.table, column.getColumnName());
        column = null;
        return super.add(dc);
    }
    
    /**
     * 添加一个列到列集合中
     * @param columnName String 列名
     * @return DataColumn 列对象
     */ 
    public DataColumn add(String columnName) {  
        DataColumn column = new DataColumn(this.table, columnName);
        return super.add(column) ? column : null;
    }
    
    /**
     * 判断是否包含指定列
     * @param columnName String 列名
     * @return boolean 是否包含
     */ 
    public boolean contains(String columnName) {
        return get(columnName) != null;
    }
    
    /**
     * 从列集合中获取指定列名的列对象
     * @param strColumnName String 列名
     * @return DataColumn 列对象
     */ 
    public DataColumn get(String columnName) {
        DataColumn result = null;
        if (columnName != null && columnName.length() > 0) {
            for(DataColumn column : this) {
                if (column.getColumnName().equals(columnName)) {
                    result = column;
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * 根据列名移除指定列
     * @param columnName String 列名
     * @return 是否移除成功
     */ 
    public boolean remove(String columnName) {
        DataColumn column = get(columnName);
        if (column == null) {
            return false;
        } else {
            return remove(column);
        }
    }
}