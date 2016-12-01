package com.yuly.db;

import com.yuly.model.ColumnModel;
import com.yuly.model.TableModel;
import com.yuly.utils.DataSourceConf;
import com.yuly.utils.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuliyao on 2016/11/30.
 */
@Component
public class TableLoader {

    @Autowired
    private DataSourceConf dataSourceConf;

    @Autowired
    private DataSource dataSource;

    private DatabaseMetaData databaseMetaData;

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 加载表结构信息
     * @return
     */
    public TableModel loadTableInfo(String tableName) {
        DatabaseMetaData databaseMetaData = getDatabaseMetaData();
        //1.加载表基本信息
        TableModel tableModel = getTable(databaseMetaData,tableName);
        //2.加载主键
        List<String> primaryKeys = getPrimaryKeys(databaseMetaData,tableName);
        tableModel.setPrimaryKeys(primaryKeys);
        //3.加载列信息，并判断是否主键
        tableModel.setColumnModels(getColumns(databaseMetaData, tableName, primaryKeys));
        log.info("=========="+tableName+"表结构信息："+ JsonUtil.toJson(tableModel));
        return tableModel;
    }

    /**
     * 获取数据库元数据
     * @return
     */
    public DatabaseMetaData getDatabaseMetaData() {
        try(Connection connection = dataSource.getConnection()) {
            databaseMetaData = connection.getMetaData();
            return databaseMetaData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表基本信息
     * @param databaseMetaData
     * @return
     */
    public TableModel getTable(DatabaseMetaData databaseMetaData,String table) {
        TableModel tableModel = null;
        try {
            ResultSet rs = databaseMetaData.getTables(null, null, table, new String[]{});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String comment = rs.getString("REMARKS");
                tableModel = new TableModel(tableName,comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModel;
    }

    /**
     * 获取主键
     * @param databaseMetaData
     * @return
     */
    public List<String> getPrimaryKeys(DatabaseMetaData databaseMetaData,String tableName) {
        List<String> primaryKeys = new ArrayList<>();
        try {
            ResultSet rs = databaseMetaData.getPrimaryKeys(null, null, tableName);
            while (rs.next()) {
                primaryKeys.add(String.valueOf(rs.getObject(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return primaryKeys;
    }

    /**
     * 获取列
     *
     * @param databaseMetaData
     * @param primartyKeys
     * @return
     */
    public List<ColumnModel> getColumns(DatabaseMetaData databaseMetaData, String tableName, List<String> primartyKeys) {
        List<ColumnModel> columnModels = new ArrayList<>();
        try {
            ResultSet rs = databaseMetaData.getColumns(null, "%", tableName, "%");
            while (rs.next()) {
                String column = rs.getString("COLUMN_NAME");
                String type = rs.getString("TYPE_NAME");
                int dataType = rs.getInt("DATA_TYPE");
                int columnSize = rs.getInt("COLUMN_SIZE");
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");
                String comment = rs.getString("REMARKS");
                String autoIncrement = rs.getString("IS_AUTOINCREMENT");
                ColumnModel columnModel = new ColumnModel(column, comment, type, dataType, columnSize, decimalDigits, autoIncrement, primartyKeys);
                columnModels.add(columnModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnModels;
    }

}
