package com.yuly.model;

import com.google.common.base.CaseFormat;
import com.yuly.utils.DbDataTypesUtils;

import java.util.List;

/**
 * Created by yuliyao on 2016/12/1.
 */
public class ColumnModel {

    private String columnName;

    private String columnComment;

    private String typeName;

    private int dataType;

    private int columnSize;

    private int decimalDigits;

    private String autoIncrement;

    private String upperJavaName;

    private String lowerJavaName;

    private String javaType;

    private boolean isPrimaryKey = false;

    public ColumnModel(String columnName, String columnComment, String typeName, int dataType, int columnSize, int decimalDigits, String
            autoIncrement, List<String>
            primaryKeys) {
        this.columnName = columnName;
        this.columnComment = columnComment;
        this.typeName = typeName;
        this.dataType = dataType;
        this.columnSize = columnSize;
        this.decimalDigits = decimalDigits;
        this.autoIncrement = autoIncrement;
        this.upperJavaName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName);
        this.lowerJavaName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
        this.javaType = formatJavaType(DbDataTypesUtils.getPreferredJavaType(dataType, columnSize, decimalDigits));
        if (primaryKeys != null && primaryKeys.contains(columnName)) {
            isPrimaryKey = true;
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpperJavaName() {
        return upperJavaName;
    }

    public void setUpperJavaName(String upperJavaName) {
        this.upperJavaName = upperJavaName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getLowerJavaName() {
        return lowerJavaName;
    }

    public void setLowerJavaName(String lowerJavaName) {
        this.lowerJavaName = lowerJavaName;
    }

    public String getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(String autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String formatJavaType(String javaType) {
        if (javaType != null) {
            return javaType.substring(javaType.lastIndexOf(".")+1);
        }
        return javaType;
    }
}
