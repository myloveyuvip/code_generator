package com.yuly.model;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.yuly.utils.SpringUtil;

import java.util.List;

/**
 * Created by yuliyao on 2016/12/1.
 */
public class TableModel {

    private String tableName;

    private String tableComment;

    private String javaName;

    private List<String> primaryKeys;

    private List<ColumnModel> columnModels;

    public TableModel(String tableName, String tableComment) {
        this.tableName = tableName;
        this.tableComment = tableComment;
        this.javaName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, removePrefix(tableName));
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

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public List<ColumnModel> getColumnModels() {
        return columnModels;
    }

    public void setColumnModels(List<ColumnModel> columnModels) {
        this.columnModels = columnModels;
    }

    public String removePrefix(String tableName) {
        GenConfig genConfig = SpringUtil.getBean(GenConfig.class);
        String prefix = genConfig.getTableNamePrefix();
        if (!Strings.isNullOrEmpty(genConfig.getTableNamePrefix())) {
            if (prefix.equalsIgnoreCase(tableName.substring(0, prefix.length()))) {
                return tableName.substring(prefix.length() - 1);
            }
        }
        return tableName;
    }
}
