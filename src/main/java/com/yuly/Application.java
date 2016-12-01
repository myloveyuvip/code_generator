package com.yuly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuly.db.TableLoader;
import com.yuly.generator.Generator;
import com.yuly.model.TableModel;
import com.yuly.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by yuliyao on 2016/11/30.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws JsonProcessingException {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        SpringUtil.setApplicationContext(applicationContext);

        TableLoader tableLoader = SpringUtil.getBean(TableLoader.class);
        TableModel tableModel = tableLoader.loadTableInfo("tbl_application_shop");
        Generator.createFile(tableModel);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(tableModel));

    }


}
