package ${config.packagePath}.service.impl;

import cn.nubia.tcm.framework.BaseService;
import cn.nubia.tcm.common.PagerBean;
import ${config.packagePath}.model.${table.upperJavaName};
import ${config.packagePath}.service.${table.upperJavaName}Service;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

@Service
public class ${table.upperJavaName}ServiceImpl extends BaseService<${table.upperJavaName}> implements ${table.upperJavaName}Service {

    @Override
    public PagerBean<${table.upperJavaName}> findPaginated(PagerBean<${table.upperJavaName}> pager, ${table.upperJavaName} ${table.lowerJavaName}) {
        List list = getBaicHqlAndParam(${table.lowerJavaName});
        String hql = (String) list.get(0);
        Map map = (Map) list.get(1);
        long totalCount = queryCount(hql, map);
        List<${table.upperJavaName}> ${table.lowerJavaName}List = findPaginated(pager.getPageNo(), pager.getLimit(), hql, map);
        pager.setRows(${table.lowerJavaName}List);
        pager.setTotal(totalCount);
        return pager;
    }

    @Override
    public List<${table.upperJavaName}> queryList(${table.upperJavaName} ${table.lowerJavaName}) {
        List list = getBaicHqlAndParam(${table.lowerJavaName});
        return find((String) list.get(0), (Map) list.get(1));
    }

    <#list table.columnModels as column>
    <#if column.lowerJavaName == "orderId">
    @Override
    public List<${table.upperJavaName}> queryByOrderId(Integer orderId) {
        Preconditions.checkNotNull(orderId, "orderId不能为空");
        String hql = "from ${table.upperJavaName} where orderId = ? ";
        return find(hql, Arrays.asList(orderId));
    }

    @Override
    public ${table.upperJavaName} getByOrderId(Integer orderId) {
    List<${table.upperJavaName}> ${table.lowerJavaName}s = queryByOrderId(orderId);
        if (${table.lowerJavaName}s != null && ${table.lowerJavaName}s.size() > 0) {
            return ${table.lowerJavaName}s.get(0);
        }
        return null;
    }
    </#if>
    </#list>

    private List getBaicHqlAndParam(${table.upperJavaName} ${table.lowerJavaName}) {
        StringBuffer hql = new StringBuffer("from ${table.upperJavaName} where 1=1 ");
        Map<String, Object> map = new HashMap();
        <#list table.columnModels as column>
        <#if column.javaType == "String">
        if (!Strings.isNullOrEmpty(${table.lowerJavaName}.get${column.upperJavaName}())) {
            hql.append(" and ${column.lowerJavaName} = :${column.lowerJavaName} ");
            map.put("${column.lowerJavaName}", ${table.lowerJavaName}.get${column.upperJavaName}());
        }
        <#else>
        if (${table.lowerJavaName}.get${column.upperJavaName}() != null) {
            hql.append(" and ${column.lowerJavaName} = :${column.lowerJavaName} ");
            map.put("${column.lowerJavaName}", ${table.lowerJavaName}.get${column.upperJavaName}());
        }
        </#if>
        </#list>
        return Arrays.asList(hql.toString(), map);
    }

}