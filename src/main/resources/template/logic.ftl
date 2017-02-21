package ${config.packagePath}.logic;

import ${config.packagePath}.cache.RedisCacheManage;
import ${config.packagePath}.common.Constants;
import ${config.packagePath}.common.DataTypeConstants;
import ${config.packagePath}.common.ModelConstants;
import ${config.packagePath}.common.PagerBean;
import ${config.packagePath}.common.Result;
import ${config.packagePath}.model.${table.upperJavaName};
import ${config.packagePath}.service.${table.upperJavaName}Service;
import ${config.packagePath}.util.fileutil.FileUtils;
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
        format${table.upperJavaName}Show(pager.getRows());
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

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Integer id) {
        ${table.lowerJavaName}Service.delete(id);
    }
}
