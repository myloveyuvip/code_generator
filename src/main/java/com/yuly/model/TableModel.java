package com.yuly.model;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.yuly.utils.PropertiesUtil;

import java.util.List;

/**
 * Created by yuliyao on 2016/12/1.
 */
public class TableModel {

    private String tableName;

    private String tableComment;

    private String upperJavaName;

    private String lowerJavaName;

    private List<String> primaryKeys;

    private List<ColumnModel> columnModels;

    public TableModel(String tableName, String tableComment) {
        this.tableName = tableName;
        this.tableComment = tableComment;
        this.upperJavaName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, removePrefix(tableName));
        this.lowerJavaName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, removePrefix(tableName));
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public String getUpperJavaName() {
        return upperJavaName;
    }

    public void setUpperJavaName(String upperJavaName) {
        this.upperJavaName = upperJavaName;
    }

    public String getLowerJavaName() {
        return lowerJavaName;
    }

    public void setLowerJavaName(String lowerJavaName) {
        this.lowerJavaName = lowerJavaName;
    }

    public List<ColumnModel> getColumnModels() {
        return columnModels;
    }

    public void setColumnModels(List<ColumnModel> columnModels) {
        this.columnModels = columnModels;
    }

    public String removePrefix(String tableName) {
        String prefix = PropertiesUtil.getProperty("tableNamePrefix");
        if (!Strings.isNullOrEmpty(prefix)) {
            if (prefix.equalsIgnoreCase(tableName.substring(0, prefix.length()))) {
                return tableName.substring(prefix.length());
            }
        }
        return tableName;
    }
}
