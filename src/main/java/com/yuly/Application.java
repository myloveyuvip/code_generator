package com.yuly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuly.db.TableLoader;
import com.yuly.generator.Generator;
import com.yuly.model.TableModel;
import com.yuly.utils.PropertiesUtil;
import com.yuly.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by yuliyao on 2016/11/30.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws JsonProcessingException {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        SpringUtil.setApplicationContext(applicationContext);

        TableLoader tableLoader = SpringUtil.getBean(TableLoader.class);
        String tableNames = PropertiesUtil.getProperty("tableName");
        String toBeCreate = PropertiesUtil.getProperty("toBeCreate");
        for (String tableName : tableNames.split(",")) {
            List<TableModel> tableModelList = tableLoader.loadTableInfo(tableName);
            Generator generator = new Generator();
            if (tableModelList != null && tableModelList.size() > 0) {
                for (TableModel tableModel : tableModelList) {
                    if (toBeCreate.contains("model")) {
                        generator.createModel(tableModel);
                    }
                    if (toBeCreate.contains("service")) {
                        generator.createService(tableModel);
                    }
                    if (toBeCreate.contains("logic")) {
                        generator.createLogic(tableModel);
                    }
                    if (toBeCreate.contains("controller")) {
                        generator.createController(tableModel);
                    }
                    if (toBeCreate.contains("view")) {
                        generator.createView(tableModel);
                    }
                }
            }
        }
        //关闭应用
        SpringApplication.exit(applicationContext);

    }


}
