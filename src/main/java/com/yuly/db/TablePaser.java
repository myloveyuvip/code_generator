package com.yuly.db;

import com.yuly.utils.DataSourceConf;
import com.yuly.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

/**
 * Created by yuliyao on 2016/11/30.
 */
public class TablePaser {

    @Autowired
    private DataSourceConf dataSourceConf;

    private String dbFile = "/application.properties";

    public void getColumns() {
        String driverName = PropertiesUtil.getProperty(dbFile, "spring.datasource.driver-class-name");
        String url = PropertiesUtil.getProperty(dbFile, "spring.datasource.url");
        String userName = PropertiesUtil.getProperty(dbFile, "spring.datasource.username");
        String password = PropertiesUtil.getProperty(dbFile, "spring.datasource.password");
        try {

            Class.forName(driverName).newInstance();
            Connection connection = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(databaseMetaData.getColumns(null,"test","test","*"));
            ResultSet rs = databaseMetaData.getColumns(connection.getCatalog(), "%", "test", "%");
            while (rs.next()) {
                String column = rs.getString("COLUMN_NAME");
                String type = rs.getString("TYPE_NAME");
                System.out.println(String.format("columnName:%s,type:%s", column, type));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TablePaser tablePaser = new TablePaser();
        tablePaser.getColumns();
    }

}
