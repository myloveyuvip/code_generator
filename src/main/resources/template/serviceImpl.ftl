package ${config.packagePath}.service.impl;

import ${config.packagePath}.common.PagerBean;
import ${config.packagePath}.framework.BaseService;
import ${config.packagePath}.model.${table.upperJavaName};
import ${config.packagePath}.service.${table.upperJavaName}Service;
import ${config.packagePath}.util.DateTool;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ${table.upperJavaName}ServiceImpl extends BaseService<${table.upperJavaName}> implements ${table.upperJavaName}Service {

    @Override
    public PagerBean<${table.upperJavaName}> findPaginated(PagerBean<${table.upperJavaName}> pager, ${table.upperJavaName} ${table.lowerJavaName}) {
        List list = getBasicHqlAndParam(${table.lowerJavaName});
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
        List list = getBasicHqlAndParam(${table.lowerJavaName});
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

    private List getBasicHqlAndParam(${table.upperJavaName} ${table.lowerJavaName}) {
        StringBuffer hql = new StringBuffer("from ${table.upperJavaName} where 1=1 ");
        Map<String, Object> map = new HashMap();
        <#list table.columnModels as column>
        <#if column.javaType == "String">
        if (!Strings.isNullOrEmpty(${table.lowerJavaName}.get${column.upperJavaName}())) {
        <#else>
        if (${table.lowerJavaName}.get${column.upperJavaName}() != null) {
        </#if>
            <#--如果字段名带有name/code，则使用模糊查询-->
            <#if column.upperJavaName?contains("Name") || column.upperJavaName?contains("Code")>
            hql.append(" and ${column.lowerJavaName} like :${column.lowerJavaName} ");
            map.put("${column.lowerJavaName}", "%" + ${table.lowerJavaName}.get${column.upperJavaName}() + "%");
            <#else>
            hql.append(" and ${column.lowerJavaName} = :${column.lowerJavaName} ");
            map.put("${column.lowerJavaName}", ${table.lowerJavaName}.get${column.upperJavaName}());
            </#if>
        }
        <#--时间类型生成开始和结束查询条件-->
        <#if column.dataType == 93>
        try {
            if (!Strings.isNullOrEmpty(${table.lowerJavaName}.getBegin${column.upperJavaName}())) {
                hql.append(" and ${column.lowerJavaName} >= :begin${column.upperJavaName} ");
                map.put("begin${column.upperJavaName}", DateTool.stringDateToInt(${table.lowerJavaName}.getBegin${column.upperJavaName}()));
            }
            if (!Strings.isNullOrEmpty(${table.lowerJavaName}.getEnd${column.upperJavaName}())) {
                hql.append(" and ${column.lowerJavaName} <= :end${column.upperJavaName} ");
                map.put("end${column.upperJavaName}", DateTool.stringDateToInt(${table.lowerJavaName}.getEnd${column.upperJavaName}() + " 23:59:59"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        </#if>
        </#list>
        return Arrays.asList(hql.toString(), map);
    }

}
