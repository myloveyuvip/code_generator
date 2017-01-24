package ${config.packagePath}.service;

import cn.nubia.tcm.framework.Service;
import cn.nubia.tcm.common.PagerBean;
import ${config.packagePath}.model.${table.upperJavaName};

import java.util.List;

public interface ${table.upperJavaName}Service extends Service<${table.upperJavaName}> {

    /**
     * 查询分页
     */
    PagerBean<${table.upperJavaName}> findPaginated(PagerBean<${table.upperJavaName}> pager, ${table.upperJavaName} ${table.lowerJavaName});

    /**
     * 查询列表
     */
    List<${table.upperJavaName}> queryList(${table.upperJavaName} ${table.lowerJavaName});

    <#list table.columnModels as column>
    <#if column.lowerJavaName == "orderId">
    /**
     * 根据申请单ID查询列表
     */
    List<${table.upperJavaName}> queryByOrderId(Integer orderId);

    /**
     * 根据申请单ID获取对象
     */
    ${table.upperJavaName} getByOrderId(Integer orderId);
    </#if>
    </#list>

}
