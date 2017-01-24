package ${config.packagePath}.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    <#--时间类型生成开始和结束查询条件-->
    <#if column.dataType == 93>

    <#if column.columnComment!="">
    /**
     * ${column.columnComment}开始
     */
    </#if>
    @Transient
    private String begin${column.upperJavaName};

    <#if column.columnComment!="">
    /**
     * ${column.columnComment}结束
     */
    </#if>
    @Transient
    private String end${column.upperJavaName};
    </#if>
    <#--是否配置为常量-->
    <#if column.constantData==true>

    /**
     * ${column.columnComment}名称展示
     */
    @Transient
    private String ${column.lowerJavaName}Show;
    </#if>
</#list>
<#list table.columnModels as column>

    public ${column.javaType} get${column.upperJavaName}() {
        return ${column.lowerJavaName};
    }

    public void set${column.upperJavaName}(${column.javaType} ${column.lowerJavaName}) {
        this.${column.lowerJavaName} = ${column.lowerJavaName};
    }
    <#--时间类型生成开始和结束查询条件-->
    <#if column.dataType == 93>

    public String getBegin${column.upperJavaName}() {
        return begin${column.upperJavaName};
    }

    public void setBegin${column.upperJavaName}(String begin${column.upperJavaName}) {
        this.begin${column.upperJavaName} = begin${column.upperJavaName};
    }

    public String getEnd${column.upperJavaName}() {
        return end${column.upperJavaName};
    }

    public void setEnd${column.upperJavaName}(String end${column.upperJavaName}) {
        this.end${column.upperJavaName} = end${column.upperJavaName};
    }
    </#if>
    <#--是否配置为常量-->
    <#if column.constantData==true>

    public String get${column.upperJavaName}Show() {
        return ${column.lowerJavaName}Show;
    }

    public void set${column.upperJavaName}Show(String ${column.lowerJavaName}Show) {
        this.${column.lowerJavaName}Show = ${column.lowerJavaName}Show;
    }
    </#if>
</#list>
}