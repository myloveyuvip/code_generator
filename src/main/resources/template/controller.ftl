package ${config.packagePath}.controller;

import cn.nubia.tcm.common.PagerBean;
import cn.nubia.tcm.framework.annotation.RmsDataRight;
import cn.nubia.tcm.framework.annotation.RmsModelOperation;
import ${config.packagePath}.logic.${table.upperJavaName}Logic;
import ${config.packagePath}.model.${table.upperJavaName};
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/${table.lowerJavaName}")
public class ${table.upperJavaName}Controller extends BaseController {

    @Resource
    private ${table.upperJavaName}Logic ${table.lowerJavaName}Logic;

    @RequestMapping("/page/list")
    @RmsModelOperation(modelName = "${config.moduleName}模块", operation = "查询跳转")
    public String ${table.lowerJavaName}PageList(HttpServletRequest request) {
        this.${table.lowerJavaName}Logic.${table.lowerJavaName}PageList(request);
        return "/${config.module}/list${table.upperJavaName}";
    }

    @RmsModelOperation(modelName = "${config.moduleName}模块", operation = "查询列表信息")
    @RmsDataRight(cityId = "cityId", subRegionId = "subRegion", salesRegionId = "salesRegion")
    @RequestMapping("/list")
    @ResponseBody
    public PagerBean<${table.upperJavaName}> ${table.lowerJavaName}List(HttpServletRequest request, ${table.upperJavaName} ${table.lowerJavaName}Form) {
        return ${table.lowerJavaName}Logic.${table.lowerJavaName}List(request, ${table.lowerJavaName}Form);
    }
}
