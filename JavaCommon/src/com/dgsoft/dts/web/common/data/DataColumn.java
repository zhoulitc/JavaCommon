package com.dgsoft.dts.web.common.data;

/**
 * 列对象，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-18 下午5:21:36 
 * @version 1.0 
 */
public final class DataColumn {
    private final DataTable table; 
    private String columnName;
    private Object defaultValue;

    /**
     * 构造函数
     * @param table DataTable 绑定的表对象
     * @param columnName String 构造列名
     */ 
    public DataColumn(DataTable table, String columnName) {
        this.columnName = columnName;
        this.table = table;
        this.defaultValue = null;
    }
    
    /**
     * @return Object
     */
    public synchronized Object getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * 为了线程安全牺牲性能，可以考虑用volatile关键字代替？
     * @param defaultValue Object
     */
    public synchronized void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return String 列名
     */
    public synchronized String getColumnName() {
        return this.columnName;
    }

    /**
     * 为了线程安全牺牲性能，可以考虑用volatile关键字代替？
     * @param columnName String 新列名
     */
    public synchronized void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * @return DataTable 数据表
     */
    public DataTable getTable() {
        return this.table;
    }
     
    /** 
     * 获取列名的字符串描述
     * @return String 列名的字符串描述
     * @see java.lang.Object#toString()
     */ 
    @Override
    public String toString(){
        return this.columnName;
    }
}