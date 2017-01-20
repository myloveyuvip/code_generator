package com.yuly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuly.db.TableLoader;
import com.yuly.generator.Generator;
import com.yuly.model.TableModel;
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
        List<TableModel> tableModelList = tableLoader.loadTableInfo("tbl_sms_log");
        if (tableModelList != null && tableModelList.size() > 0) {
            for (TableModel tableModel : tableModelList) {
                Generator.createModel(tableModel);
                Generator.createService(tableModel);
                Generator.createLogic(tableModel);
                Generator.createController(tableModel);
                Generator.createView(tableModel);
            }
        }

    }


}
