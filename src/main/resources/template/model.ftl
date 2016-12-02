package ${config.packagePath}.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by CodeGenerator on ${config.dateString}.
<#if table.tableComment!="">
 * ${table.tableComment}
</#if>
 */
@Entity
@Table(name = "${table.tableName}")
public class ${table.upperJavaName} {
<#list table.columnModels as column>

    <#if column.columnComment!="">
    /**
     * ${column.columnComment}
     */
    </#if>
    <#if column.isPrimaryKey == true>
    @Id
    </#if>
    <#if column.autoIncrement == "YES">
    @GeneratedValue
    </#if>
    @Column(name = "${column.columnName}")
    private ${column.javaType} ${column.lowerJavaName};
</#list>
<#list table.columnModels as column>

    public ${column.javaType} get${column.upperJavaName}() {
        return ${column.lowerJavaName};
    }

    public void set${column.upperJavaName}(${column.javaType} ${column.lowerJavaName}) {
        this.${column.lowerJavaName} = ${column.lowerJavaName};
    }
</#list>

}