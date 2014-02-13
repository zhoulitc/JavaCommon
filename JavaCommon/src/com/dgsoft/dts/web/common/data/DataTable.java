package com.dgsoft.dts.web.common.data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.dgsoft.dts.web.common.data.internal.DataRowComparator;
import com.dgsoft.dts.web.common.internal.Reflect;

/**
 * 表对象，线程非安全
 * @author li.zhou 
 * @dts.date 2013-1-18 下午5:22:45 
 * @version 1.0 
 */
public final class DataTable {
    private final static Logger log = LogManager.getLogger(DataTable.class.getName());
    
    /** 
     * 表的行数据集合
     */ 
    public final DataRowCollection rows;
    
    /** 
     * 表的列字段集合
     */ 
    public final DataColumnCollection columns;
    
    private String tableName;

    /**
     * @return String 表名
     */
    public synchronized String getTableName() {
        return tableName;
    }

    /**
     * @param tableName String 新的表名
     */
    public synchronized void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 构造函数
     */ 
    public DataTable() {
        this("");
    }
   
    /**
     * 构造函数
     * @param tableName String 表名
     */ 
    public DataTable(String tableName) {
        this.columns = new DataColumnCollection(this);
        this.rows = new DataRowCollection(this);
        setTableName(tableName);
    }
   
    /**
     * 构造函数
     * @param list List<?> 载入的list对象
     */ 
    public DataTable(List<?> list) {
        this("");
        try {
            init(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.columns.clear();
            this.rows.clear();
        }
    }
   
    /**
     * 构造函数
     * @param tableName String 表名
     * @param list List<?> 载入的list对象
     */ 
    public DataTable(String tableName, List<?> list) {
        this(tableName);
        try {
            init(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.columns.clear();
            this.rows.clear();
        }
    }
    
    /**
     * 清除所有行对象中的数据
     */ 
    public void clear()  {
        this.rows.clear();
    }
    
    /**
     * 浅复制一个表，只复制表结构
     * @return DataTable 表对象
     */ 
    public DataTable clone()  {
        DataTable table = new DataTable(getTableName());
        table.columns.addAll(this.columns);
        return table;
    }
    
    /**
     * 深复制一个表，包括表数据
     * @return DataTable 表对象
     */ 
    public DataTable copy()  {
        DataTable table = clone();
        DataRow row;
        for (int i = 0, size = this.rows.size(); i < size; i++) {
            row = table.newRow(false);
            row.putAll(this.rows.get(i));
            table.rows.add(row);
        }
        return table;
    }
    
    /**
     * 销毁此表的表名、列信息和行数据
     */ 
    public void dispose() {
        clear();
        this.columns.clear();
        setTableName("");
    }
    
    /**
     * 获取单元格的布尔值
     * @param row int 行索引
     * @param column int 列索引
     * @return boolean 布尔值
     */ 
    public boolean getCellBool(int row, int column) {
        boolean result = false;
        try {
            result = Boolean.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的布尔值
     * @param row int 行索引
     * @param column String 列名
     * @return boolean 布尔值
     */ 
    public boolean getCellBool(int row, String column) {
        boolean result = false;
        try {
            result = Boolean.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的字符值
     * @param row int 行索引
     * @param column int 列索引
     * @return Char 字符值
     */ 
    public char getCellChar(int row, int column) {
        String result = getCellString(row, column);
        if (1 != result.length()) {
            result = " ";
        }
        return result.charAt(0);
    }

    /**
     * 获取单元格的字符值
     * @param row int 行索引
     * @param column String 列名
     * @return Char 字符值
     */ 
    public char getCellChar(int row, String column) {
        String result = getCellString(row, column);
        if (1 != result.length()) {
            result = " ";
        }
        return result.charAt(0);
    }
    
    /**
     * 获取单元格的8位整数值
     * @param row int 行索引
     * @param column int 列索引
     * @return byte 8位整数值
     */ 
    public byte getCellByte(int row, int column) {
        byte result = 0;
        try {
            result = Byte.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的8位整数值
     * @param row int 行索引
     * @param column String 列名
     * @return byte 8位整数值
     */ 
    public byte getCellByte(int row, String column) {
        byte result = 0;
        try {
            result = Byte.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的16位整数值
     * @param row int 行索引
     * @param column int 列索引
     * @return short 16位整数值
     */ 
    public short getCellShort(int row, int column) {
        short result = 0;
        try {
            result = Short.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的16位整数值
     * @param row int 行索引
     * @param column String 列名
     * @return byte 16位整数值
     */ 
    public short getCellShort(int row, String column) {
        short result = 0;
        try {
            result = Short.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的32位整数值
     * @param row int 行索引
     * @param column int 列索引
     * @return int 32位整数值
     */ 
    public int getCellInt(int row, int column) {
        int result = 0;
        try {
            result = Integer.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的32位整数值
     * @param row int 行索引
     * @param column String 列名
     * @return int 32位整数值
     */ 
    public int getCellInt(int row, String column) {
        int result = 0;
        try {
            result = Integer.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的64位整数值
     * @param row int 行索引
     * @param column int 列索引
     * @return long 64位整数值
     */ 
    public long getCellLong(int row, int column) {
        long result = 0;
        try {
            result = Long.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的64位整数值
     * @param row int 行索引
     * @param column String 列名
     * @return long 64位整数值
     */ 
    public long getCellLong(int row, String column) {
        long result = 0;
        try {
            result = Long.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的32位浮点数值
     * @param row int 行索引
     * @param column int 列索引
     * @return float 32位浮点数值
     */ 
    public float getCellFloat(int row, int column) {
        float result = 0;
        try {
            result = Float.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的32位浮点数值
     * @param row int 行索引
     * @param column String 列名
     * @return float 32位浮点数值
     */ 
    public float getCellFloat(int row, String column) {
        float result = 0;
        try {
            result = Float.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的64位浮点数值
     * @param row int 行索引
     * @param column int 列索引
     * @return double 64位浮点数值
     */ 
    public double getCellDouble(int row, int column) {
        double result = 0;
        try {
            result = Double.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取单元格的64位浮点数值
     * @param row int 行索引
     * @param column String 列名
     * @return double 64位浮点数值
     */ 
    public double getCellDouble(int row, String column) {
        double result = 0;
        try {
            result = Double.valueOf(getCellString(row, column));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取单元格的字符串值
     * @param row int 行索引
     * @param column int 列索引
     * @return String 字符串值
     */ 
    public String getCellString(int row, int column) {
        Object result = getCellValue(row, column);
        return result == null ? "" : result.toString();
    }

    /**
     * 获取单元格的字符串值
     * @param row int 行索引
     * @param column String 列名
     * @return String 字符串值
     */ 
    public String getCellString(int row, String column) {
        Object result = getCellValue(row, column);
        return result == null ? "" : result.toString();
    }
    
    /**
     * 获取单元格的值
     * @param row int 行索引
     * @param column int 列索引
     * @return Object 值
     */ 
    public Object getCellValue(int row, int column) {
        Object result = null;
        if (isBounds(row)) {
            result = this.rows.get(row).getValue(column);
        }
        return result;
    }

    /**
     * 获取单元格的值
     * @param row int 行索引
     * @param column String 列名
     * @return Object 值
     */ 
    public Object getCellValue(int row, String column) {
        Object result = null;
        if (isBounds(row)) {
            result = this.rows.get(row).getValue(column);
        }
        return result;
    }
    
    /**
     * 根据分页值获取DataTable内部特定数据行，失败返回原DataTable。
     * @param index int 当前页码
     * @param size int 页大小
     * @return DataTable 过滤后的表对象
     */ 
    public DataTable getPage(int index, int size) {
        DataTable result = this;
        DataRow row;
        log.debug(String.format("method start int[index:%d],int[size:%d]", index, size));
        if (size > 0) {
            int total = this.rows.size();
            if (index <= 0) {
                index = 1;
            }
            int begin = (index - 1) * size;
            int end = index * size;
            if (begin > total) {
                begin = (total % size == 0 ? total - size : total - (size - 1) * (total - 1)) + 1;
                end = total;
            } else {
                if (end > total) {
                    end = total;
                }
            }
            result = clone();
            for (int i = begin; i < end; i++) {
                row = result.newRow(false);
                row.putAll(this.rows.get(i));
                result.rows.add(row);
            }
        }
        log.debug(String.format("method stop return:DataTable[%s]", result));
        return result;
    }
    
    /**
     * 将另一个表的DataRow中的数据导入到此表中<br>
     * 只导入table中存在的columnName对应的value
     * @param row DataRow 数据行
     */ 
    public void importRow(DataRow row) {
        if (row != null && row.getTable() != this) {
            DataRow dr = newRow(false);
            String columnName;
            for (int i = 0, size = row.getTable().columns.size(); i < size; i++) {
                columnName = row.getTable().columns.get(i).getColumnName();
                if (this.columns.contains(columnName)) {
                    dr.put(columnName, row.getValue(columnName));
                }
            }
            this.rows.add(dr);
        }
    }
    
    /**
     * 合并另一个列相同的DataTable<br>
     * 先将两个table的columns合并，然后再合并数据
     * @param table
     */ 
    public void merge(DataTable table) {
        if (table != null && table != this) {
            String columnName;
            for (int i = 0, size = table.columns.size(); i < size; i++) {
                columnName = table.columns.get(i).getColumnName();
                if (!this.columns.contains(columnName)) {
                    this.columns.add(columnName);
                }
            }
            DataRow dr;
            for (int i = 0, size = table.rows.size(); i < size; i++) {
                dr = newRow(false);
                for (int j = 0, count = this.columns.size(); j < count; j++) {
                    columnName = this.columns.get(i).getColumnName();
                    dr.put(columnName, table.rows.get(i).getValue(columnName));
                }
                this.rows.add(dr);
            }
        }
    }
    
    /**
     * 新建一个DataRow，并用默认值填充整行数据
     * @return DataRow 行对象
     */ 
    public DataRow newRow()  {
        return newRow(true);
    }
    
    /**
     * 新建一个DataRow
     * @param isFill boolean 指定是否用默认值填充整行数据
     * @return DataRow 行对象
     */ 
    public DataRow newRow(boolean isFill)  {
        DataRow row = new DataRow(this, this.columns.size());
        if (isFill) {
            DataColumn column;
            for (int i = 0, size = this.columns.size(); i < size; i++) {
                column = this.columns.get(i);
                row.put(column.getColumnName(), column.getDefaultValue());
            } 
        }
        return row;
    }
    
    /**
     * 根据表达式筛选行对象数组，关键字请小写
     * @param filterExperssion String 筛选表达式串
     * @return DataTable 筛选后的表对象
     */ 
    public DataTable select(String filterExperssion) {
        DataTable result;
        if (filterExperssion != null && filterExperssion.length() > 0) {
            result = select(DataExperssionFactory.compute(filterExperssion.trim()));
        } else {
            result = clone();
        }
        return result;
    }
    
    /**
     * 根据表达式筛选行对象数组，关键字请小写
     * @param de experssion 筛选表达式对象，利用DataExperssionFactory的静态方法构造表达式
     * @return DataTable 筛选后的表对象
     */ 
    public DataTable select(DataExperssion experssion) {
        DataTable result;
        log.debug(String.format("method start DataExperssion[experssion:%s]", experssion));
        if (experssion != null) {
            result = clone();
            DataRow row;
            DataRow newRow;
            for (int i = 0, size = rows.size(); i < size; i++) {
                row = this.rows.get(i);
                if (experssion.isPass(row)) {
                    newRow = result.newRow(false);
                    newRow.putAll(row);
                    result.rows.add(newRow);
                }
            }
        } else {
            result = this;
        }
        log.debug(String.format("method stop return:DataTable[%s]", result));
        return result;
    }
   
    /**
     * 设置单元格的值
     * @param row int 行索引
     * @param column int 列索引
     * @param value Object 值
     */ 
    public void setCellValue(int row, int column, Object value) {
        if (isBounds(row)) {
            this.rows.get(row).setValue(column, value);
        }
    }

    /**
     * 设置单元格的值
     * @param row int 行索引
     * @param column String 列名
     * @param value Object 值
     */ 
    public void setCellValue(int row, String column, Object value) {
        if (isBounds(row)) {
            this.rows.get(row).setValue(column, value);
        }
    }
    
    /**
     * 根据列名和指定ASC/DESC进行排序，不指定默认为ASC。注意目前只能根据单列进行排序
     * @param sortFields String 排序列表，可以在每个列名后面指定asc或者desc
     * @return DataTable 排序后的表对象
     */ 
    public DataTable sort(String sortFields) {
        DataRow[] array = new DataRow[rows.size()];
        rows.toArray(array);
        Arrays.sort(array, new DataRowComparator(sortFields));
        DataTable table = clone();
        DataRow row;
        for (int i = 0; i < array.length; i++) {
            row = table.newRow(false);
            row.putAll(array[i]);
            table.rows.add(row);
        }
        return table;
    }
    
    /** 
     * 提供表对象的字符串描述
     * @return String 表对象的字符串描述
     * @see java.lang.Object#toString()
     */ 
    @Override
    public String toString() {
        return String.format("{\"TableName\":\"%s\",\"Columns\":%d,\"Rows\":%d}", getTableName(), columns.size(), rows.size());
    }
    
    /**
     * 判断索引是否越界
     * @param index int 列索引
     * @return 是否越界
     */ 
    private boolean isBounds(int index) {
        return index >= 0 && index < rows.size();   
    }
    
    /**
     * 载入一个List到DataTable中
     * @param list List 列表对象
     * @throws Exception
     */ 
    private void init(List<?> list) throws Exception {
        log.debug(String.format("method start List[list:%s]", list));
        if (list != null && list.size() > 0) {
            Object obj = list.get(0);
            if (obj instanceof Map) { //Map
                Map<?, ?> map = (Map<?, ?>)obj;
                for (Object key : map.keySet()) {
                    columns.add(key.toString());
                }
                DataRow row;
                for (int j = 0, size = list.size(); j < size; j++) {
                    map = (Map<?, ?>)list.get(j);
                    row = new DataRow(this, map.size());
                    for (Object key : map.keySet()) {
                        row.setValue(key.toString(), map.get(key));
                    }
                    this.rows.add(row);
                }
            } else { //Bean
                Class<?> cls = obj.getClass();
                Field[] fields = Reflect.getFields(cls);
                for (int i = 0; i < fields.length; i++) {
                    this.columns.add(fields[i].getName());
                }
                Object bean;
                DataRow row;
                if (this.columns.size() > 0) {
                    for (int j = 0, size = list.size(); j < size; j++) {
                        bean = list.get(j);
                        row = new DataRow(this, fields.length);
                        for (int i = 0; i < fields.length; i++) {
                            row.setValue(fields[i].getName(), Reflect.getFieldValue(bean, fields[i]));
                        }
                        this.rows.add(row);
                    }
                }
            }
        } 
        log.debug("method stop");
    }
}