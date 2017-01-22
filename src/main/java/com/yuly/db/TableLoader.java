package com.yuly.db;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.yuly.model.ColumnModel;
import com.yuly.model.TableModel;
import com.yuly.utils.JsonUtil;
import com.yuly.utils.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuliyao on 2016/11/30.
 */
@Component
public class TableLoader {

    @Autowired
    private DataSource dataSource;

    private DatabaseMetaData databaseMetaData;

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 加载表结构信息
     *
     * @return
     */
    public List<TableModel> loadTableInfo(String... tableNames) {
        DatabaseMetaData databaseMetaData = getDatabaseMetaData();
        Preconditions.checkNotNull(tableNames, "表名不能为空!");
        List<TableModel> tableModelList = new ArrayList<>();
        for (String tableName : tableNames) {
            //1.加载表基本信息
            List<TableModel> tableModels = getTables(databaseMetaData, tableName);
            if (tableModels != null && tableModels.size() > 0) {
                for (TableModel tableModel : tableModels) {
                    //2.加载主键
                    List<String> primaryKeys = getPrimaryKeys(databaseMetaData, tableModel.getTableName());
                    tableModel.setPrimaryKeys(primaryKeys);
                    //3.加载列信息，并判断是否主键
                    tableModel.setColumnModels(getColumns(databaseMetaData, tableModel.getTableName(), primaryKeys));
                    log.info("==========" + tableModel.getTableName() + "表结构信息：" + JsonUtil.toJson(tableModel));
                }
            }
            tableModelList.addAll(tableModels);
        }
        return tableModelList;
    }

    /**
     * 获取数据库元数据
     *
     * @return
     */
    public DatabaseMetaData getDatabaseMetaData() {
        try {
            return getConnection().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*try (Connection connection = dataSource.getConnection()) {
            this.connection = connection;
            databaseMetaData = connection.getMetaData();
            return databaseMetaData;
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    /**
     * 获取表基本信息
     *
     * @param databaseMetaData
     * @return
     */
    public List<TableModel> getTables(DatabaseMetaData databaseMetaData, String table) {
        List<TableModel> tableModels = new ArrayList<>();
        try {
            ResultSet rs = databaseMetaData.getTables(null, null, table, new String[]{});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                //得到表注释
                String comment = rs.getString("REMARKS");
                //mysql通过以上取不到表注释，不知道为什么，若没有采取以下再取一次
                if (Strings.isNullOrEmpty(comment)) {
                    String sql = "select TABLE_COMMENT tableComment from information_schema.TABLES where TABLE_SCHEMA = ? and TABLE_NAME = ?";
                    PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
                    preparedStatement.setString(1, getConnection().getCatalog());
                    preparedStatement.setString(2, tableName);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        comment = resultSet.getString("tableComment");
                    }
                }
                TableModel tableMode = new TableModel(tableName, comment);
                tableModels.add(tableMode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModels;
    }

    /**
     * 获取主键
     *
     * @param databaseMetaData
     * @return
     */
    public List<String> getPrimaryKeys(DatabaseMetaData databaseMetaData, String tableName) {
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
                //RMS将时间类型设为int，此处做转换
                if (dataType == Types.INTEGER && (column.contains("time") || column.contains("date"))) {
                    dataType = Types.TIMESTAMP;
                }
                int columnSize = rs.getInt("COLUMN_SIZE");
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");
                String comment = rs.getString("REMARKS");
                String autoIncrement = rs.getString("IS_AUTOINCREMENT");
                ColumnModel columnModel = new ColumnModel(column, comment, type, dataType, columnSize, decimalDigits, autoIncrement, primartyKeys);
                //判断字段是否在常量表中配置成下拉框
                String sql = "select count(*) cnt from tbl_constants_data d where d.model=? and type=?";
                PreparedStatement pst = getConnection().prepareStatement(sql);
                pst.setString(1, PropertiesUtil.getProperty("module"));
                pst.setString(2, column);
                ResultSet cntRs = pst.executeQuery();
                while (cntRs.next()) {
                    if (cntRs.getInt("cnt") > 0) {
                        columnModel.setConstantData(true);
                    }
                }
                columnModels.add(columnModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnModels;
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
