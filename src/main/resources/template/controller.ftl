package ${config.packagePath}.controller;

import ${config.packagePath}.logic.${table.upperJavaName}Logic;
import ${config.packagePath}.model.${table.upperJavaName};
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import cn.nubia.tcm.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nubia.tcm.framework.annotation.RmsDataRight;
import cn.nubia.tcm.framework.annotation.RmsModelOperation;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/${table.lowerJavaName}")
public class ${table.upperJavaName}Controller extends BaseController {

    @Resource
    private ${table.upperJavaName}Logic ${table.lowerJavaName}Logic;

    @RequestMapping("/page/list")
    @RmsModelOperation(modelName = "模块", operation = "查询页面")
    public String ${table.lowerJavaName}PageList(HttpServletRequest request) {
        this.${table.lowerJavaName}Logic.${table.lowerJavaName}PageList(request);
        return "/${table.lowerJavaName}/list${table.upperJavaName}";
    }

    @RmsModelOperation(modelName = "模块", operation = "查询列表信息")
    @RmsDataRight(cityId = "cityId", subRegionId = "subRegion", salesRegionId = "salesRegion")
    @RequestMapping("/list")
    @ResponseBody
    public String ${table.lowerJavaName}List(HttpServletRequest request, ${table.upperJavaName} ${table.lowerJavaName}Form) {
        return this.${table.lowerJavaName}Logic.${table.lowerJavaName}List(request, ${table.lowerJavaName}Form);
    }
}
