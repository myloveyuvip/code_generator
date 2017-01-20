package com.yuly.generator;

import com.google.common.base.Strings;
import com.yuly.model.GenConfig;
import com.yuly.model.TableModel;
import com.yuly.utils.PropertiesUtil;
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

    public static void createFile(TableModel tableModel, String modelName, String fileNameSuffix) {
        Configuration config = new Configuration();
        ClassPathResource resource = new ClassPathResource("template/" + modelName + ".ftl");
        try {
            config.setObjectWrapper(new DefaultObjectWrapper());
            config.setDirectoryForTemplateLoading(resource.getFile().getParentFile());
            Template template = config.getTemplate(modelName + ".ftl", "UTF-8");
            Map root = new HashMap();
            root.put("table", tableModel);
            GenConfig genConfig = new GenConfig();
            genConfig.setPackagePath(PropertiesUtil.getProperty("package.path"));
            root.put("config", genConfig);
            String outDir = getOutDir(modelName);
            File file = new File(outDir + "/" + tableModel.getUpperJavaName() + fileNameSuffix + ".java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
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

    public static void createModel(TableModel tableModel) {
        createFile(tableModel, "model", "");
    }

    public static void createService(TableModel tableModel) {
        createFile(tableModel, "service", "Service");
        createFile(tableModel, "service.impl", "ServiceImpl");
    }

    public static void createLogic(TableModel tableModel) {
        createFile(tableModel, "logic", "Logic");
    }

    public static void createController(TableModel tableModel) {
        createFile(tableModel, "controller", "Controller");
    }

    public static void createView(TableModel tableModel) {
        createFile(tableModel, "list.jsp", "List");
        createFile(tableModel, "list.js", "List");
    }

    private static String getOutDir(String modelName) {
        String outDir = PropertiesUtil.getProperty("out.dir") + "/" + PropertiesUtil.getProperty("package.path") + "/" + modelName;
        String outDir2 = "";
        if (!Strings.isNullOrEmpty(outDir)) {
            for (String pack : outDir.split("\\.")) {
                outDir2 += "/" + pack;
            }
        }
        return outDir2;
    }

}
