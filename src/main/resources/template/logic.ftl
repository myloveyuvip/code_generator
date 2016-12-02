package ${config.packagePath}.logic;

import ${config.packagePath}.service.${table.upperJavaName}Service;
import ${config.packagePath}.model.${table.upperJavaName};
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import cn.nubia.tcm.common.Result;
import javax.servlet.http.HttpServletRequest;

@Component
public class ${table.upperJavaName}Logic extends BaseLogic {

    @Resource
    private ${table.upperJavaName}Service ${table.lowerJavaName}Service;

    public void ${table.lowerJavaName}PageList(HttpServletRequest request) {
    }

    public String ${table.lowerJavaName}List(HttpServletRequest request, ${table.upperJavaName} ${table.lowerJavaName}) {
        PagerBean<${table.upperJavaName}> pager = new PagerBean()<>;
        int offset = getIntParam(request, "offset").intValue();
        int limit = getIntParam(request, "limit").intValue();
        pager.setLimit(limit);
        pager.setOffset(offset);
        this.${table.lowerJavaName}Service.findPaginated(pager, ${table.lowerJavaName});
        return Result.returnDataResult(pager);
    }
}
