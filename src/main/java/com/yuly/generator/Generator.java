package com.yuly.generator;

import com.yuly.model.GenConfig;
import com.yuly.model.TableModel;
import com.yuly.utils.SpringUtil;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuliyao on 2016/12/1.
 */
public class Generator {

    public static void createFile(TableModel tableModel) {
        Configuration config = new Configuration();
        //URL path = FreeMarkerTest.class.getResource("");
        String path = new File("").getAbsolutePath();
        ClassPathResource resource = new ClassPathResource("template/model.ftl");
        try {
            config.setObjectWrapper(new DefaultObjectWrapper());
            config.setDirectoryForTemplateLoading(resource.getFile().getParentFile());
            Template template = config.getTemplate("model.ftl","UTF-8");
            Map root = new HashMap();
            root.put("table", tableModel);
            GenConfig genConfig = SpringUtil.getBean(GenConfig.class);
            root.put("config", genConfig);
            File file = new File("E:\\00code\\code_generator\\src\\main\\java\\com\\yuly\\model\\TblApplicationShop.java");
            if(!file.exists()){
                //System.out.println("file exist");
                file.createNewFile();
            }
            Writer out = new BufferedWriter(new FileWriter(file));
            template.process(root, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

}
