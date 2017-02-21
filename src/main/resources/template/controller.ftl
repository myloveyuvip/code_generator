package ${config.packagePath}.controller;

import ${config.packagePath}.common.PagerBean;
import ${config.packagePath}.common.Result;
import ${config.packagePath}.framework.annotation.RmsDataRight;
import ${config.packagePath}.framework.annotation.RmsModelOperation;
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
    @RmsModelOperation(modelName = "${config.moduleName}模块", operation = "查询页面跳转")
    public String ${table.lowerJavaName}PageList(HttpServletRequest request) {
        ${table.lowerJavaName}Logic.${table.lowerJavaName}PageList(request);
        return "/${config.module}/list${table.upperJavaName}";
    }

    @RmsModelOperation(modelName = "${config.moduleName}模块", operation = "查询列表信息")
    @RmsDataRight(cityId = "cityId", subRegionId = "subRegion", salesRegionId = "salesRegion")
    @RequestMapping("/list")
    @ResponseBody
    public PagerBean<${table.upperJavaName}> ${table.lowerJavaName}List(HttpServletRequest request, ${table.upperJavaName} ${table.lowerJavaName}Form) {
        PagerBean<${table.upperJavaName}> pager = new PagerBean<>();
        pager.setLimit(getIntParam(request, "limit").intValue());
        pager.setOffset(getIntParam(request, "offset").intValue());
        return ${table.lowerJavaName}Logic.${table.lowerJavaName}List(pager, ${table.lowerJavaName}Form);
    }

    @RmsModelOperation(modelName = "${config.moduleName}模块", operation = "列表信息导出")
    @RequestMapping("/export")
    @ResponseBody
    public Result export(HttpServletRequest request, ${table.upperJavaName} ${table.lowerJavaName}Form) {
        return ${table.lowerJavaName}Logic.export(${table.lowerJavaName}Form);
    }

    @RmsModelOperation(modelName="${config.moduleName}模块",operation="删除")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(HttpServletRequest request, Integer id) {
        ${table.lowerJavaName}Logic.delete(id);
        return new Result();
    }
}
