package ${config.packagePath}.logic;

import cn.nubia.tcm.common.Constants;
import cn.nubia.tcm.common.PagerBean;
import cn.nubia.tcm.common.Result;
import ${config.packagePath}.model.${table.upperJavaName};
import ${config.packagePath}.service.${table.upperJavaName}Service;
import cn.nubia.tcm.util.fileutil.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class ${table.upperJavaName}Logic extends BaseLogic {

    @Resource
    private ${table.upperJavaName}Service ${table.lowerJavaName}Service;

    public void ${table.lowerJavaName}PageList(HttpServletRequest request) {
    }

    public PagerBean<${table.upperJavaName}> ${table.lowerJavaName}List(PagerBean<${table.upperJavaName}> pager, ${table.upperJavaName} ${table
.lowerJavaName}) {
        return ${table.lowerJavaName}Service.findPaginated(pager, ${table.lowerJavaName});
    }

    public Result export(${table.upperJavaName} ${table.lowerJavaName}) {
        PagerBean<${table.upperJavaName}> pager = new PagerBean<>(100000, 0);
        ${table.lowerJavaName}List(pager, ${table.lowerJavaName});
        //生成导出文件
        String xmlPath = Constants.CONFIG_PATH + "/export/${table.lowerJavaName}_export.xml";
        String url = FileUtils.exportExcel(xmlPath, "${table.lowerJavaName}_data_" + System.currentTimeMillis() + ".xlsx", pager.getRows());
        return new Result(0, "", url);
    }
}
