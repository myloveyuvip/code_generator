package ${config.packagePath}.logic;

import cn.nubia.tcm.cache.RedisCacheManage;
import cn.nubia.tcm.common.Constants;
import cn.nubia.tcm.common.DataTypeConstants;
import cn.nubia.tcm.common.ModelConstants;
import cn.nubia.tcm.common.PagerBean;
import cn.nubia.tcm.common.Result;
import ${config.packagePath}.model.${table.upperJavaName};
import ${config.packagePath}.service.${table.upperJavaName}Service;
import cn.nubia.tcm.util.fileutil.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class ${table.upperJavaName}Logic extends BaseLogic {

    @Resource
    private ${table.upperJavaName}Service ${table.lowerJavaName}Service;

    @Resource
    private RedisCacheManage redisCacheManage;

    public void ${table.lowerJavaName}PageList(HttpServletRequest request) {
    }

    public PagerBean<${table.upperJavaName}> ${table.lowerJavaName}List(PagerBean<${table.upperJavaName}> pager, ${table.upperJavaName} ${table
.lowerJavaName}) {
        ${table.lowerJavaName}Service.findPaginated(pager, ${table.lowerJavaName});
        format${table.upperJavaName}Show(pager.rows());
        return pager;
}

    public Result export(${table.upperJavaName} ${table.lowerJavaName}) {
        PagerBean<${table.upperJavaName}> pager = new PagerBean<>(100000, 0);
        ${table.lowerJavaName}List(pager, ${table.lowerJavaName});
        //生成导出文件
        String xmlPath = Constants.CONFIG_PATH + "/export/${table.lowerJavaName}_export.xml";
        String url = FileUtils.exportExcel(xmlPath, "${table.lowerJavaName}_data_" + System.currentTimeMillis() + ".xlsx", pager.getRows());
        return new Result(0, "", url);
    }

    /**
     * 转换实体输出
     *
     * @param ${table.lowerJavaName}s
     */
    public void format${table.upperJavaName}Show(List<${table.upperJavaName}> ${table.lowerJavaName}s) {
        if (${table.lowerJavaName}s != null) {
            ${table.lowerJavaName}s.forEach(this::format${table.upperJavaName}Show);
        }
    }

    /**
     * 转换实体输出
     *
     * @param ${table.lowerJavaName}
     */
    public void format${table.upperJavaName}Show(${table.upperJavaName} ${table.lowerJavaName}) {
        <#list table.columnModels as column>
        <#if column.constantData==true>
        //${column.columnComment}
        String ${column.lowerJavaName}Show = redisCacheManage.getConstantsDataCodeName(
                ModelConstants.${config.module?upper_case}, DataTypeConstants.${column.columnName?upper_case}, ${table.lowerJavaName}.get${column
        .upperJavaName}());
        ${table.lowerJavaName}.set${column.upperJavaName}Show(${column.lowerJavaName}Show);

        </#if>
        </#list>
    }
}
