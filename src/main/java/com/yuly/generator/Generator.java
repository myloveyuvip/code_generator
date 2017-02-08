package com.yuly.generator;

import com.google.common.base.Strings;
import com.yuly.model.GenConfig;
import com.yuly.model.TableModel;
import com.yuly.utils.PropertiesUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yuliyao on 2016/12/1.
 */
public class Generator {

    public void createFile(TableModel tableModel, String tplFileName, String outPath) {
        try {
            Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            config.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(),"template");
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setLogTemplateExceptions(false);
            Template template = config.getTemplate(tplFileName, "UTF-8");
            //传参生成文件
            Map root = new HashMap();
            root.put("table", tableModel);
            GenConfig genConfig = new GenConfig();
            genConfig.setPackagePath(PropertiesUtil.getProperty("package.path"));
            genConfig.setModule(PropertiesUtil.getProperty("module"));
            genConfig.setModuleName(PropertiesUtil.getProperty("moduleName"));
            root.put("config", genConfig);
            File file = new File(outPath);
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

    public  void createModel(TableModel tableModel) {
        String outPath = getJavaOutDir("model") + "/" + tableModel.getUpperJavaName() + ".java";
        createFile(tableModel, "model.ftl", outPath);
    }

    public void createService(TableModel tableModel) {
        String servicePath = getJavaOutDir("service") + "/" + tableModel.getUpperJavaName() + "Service.java";
        createFile(tableModel, "service.ftl", servicePath);
        String serviceImplPath = getJavaOutDir("service/impl") + "/" + tableModel.getUpperJavaName() + "ServiceImpl.java";
        createFile(tableModel, "serviceImpl.ftl", serviceImplPath);
    }

    public void createLogic(TableModel tableModel) {
        String outPath = getJavaOutDir("logic") + "/" + tableModel.getUpperJavaName() + "Logic.java";
        createFile(tableModel, "logic.ftl", outPath);
    }

    public void createController(TableModel tableModel) {
        String outPath = getJavaOutDir("controller") + "/" + tableModel.getUpperJavaName() + "Controller.java";
        createFile(tableModel, "controller.ftl", outPath);
    }

    public void createView(TableModel tableModel) {
        //带ID的字段在页面不展示
        for (int i = tableModel.getColumnModels().size() - 1; i >= 0; i--) {
            if (tableModel.getColumnModels().get(i).getUpperJavaName().endsWith("Id")) {
                tableModel.getColumnModels().remove(i);
            }
        }
        String jspPath = PropertiesUtil.getProperty("out.dir") + "/src/main/webapp/" + PropertiesUtil.getProperty("module") + "/list" + tableModel
                .getUpperJavaName()
                + ".jsp";
        createFile(tableModel, "list.jsp.ftl", jspPath);
        String jsPath = PropertiesUtil.getProperty("out.dir") + "/src/main/webapp/" + PropertiesUtil.getProperty("module") + "/list" + tableModel
                .getUpperJavaName()
                + ".js";
        createFile(tableModel, "list.js.ftl", jsPath);
        // TODO: 2017/1/23  默认生成导出功能，回头改成可配置
        String xmlPath = PropertiesUtil.getProperty("out.dir") + "/src/main/webapp/config/export/" + tableModel.getLowerJavaName() + "_export.xml";
        createFile(tableModel, "export.xml.ftl", xmlPath);
    }

    private String getJavaOutDir(String modelName) {
        String outDir = PropertiesUtil.getProperty("out.dir") + "/src/main/java/" + PropertiesUtil.getProperty("package.path") + "/" + modelName;
        String outDir2 = "";
        if (!Strings.isNullOrEmpty(outDir)) {
            for (String pack : outDir.split("\\.")) {
                outDir2 += "/" + pack;
            }
        }
        return outDir2;
    }

}
