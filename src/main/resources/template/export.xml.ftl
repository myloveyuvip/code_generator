<?xml version="1.0" encoding="UTF-8"?>
<workbook>
    <!--
		@name: column name
		@desc: column title
		@type: column type(1:string 2:numeric 3:datatime)
		@format: column format(numeric format:0.00|#.##..., datatime format:yyyy-MM-dd...)
		@align: column align(1:left 2:center 3:right)
		@width: column width
	-->
    <sheet name="UTIL_SCHEDULE" desc="${table.tableComment}数据">
        <header isshow="true">
            <#list table.columnModels as column>
            <cell
                    name="${column.lowerJavaName}"
                    desc="${column.columnComment}"
                    <#--判断字段类型，默认文本类型；时间类型则默认格式化为yyyy-MM-dd -->
                    <#if column.dataType == 93>
                    type="3"
                    format="yyyy-MM-dd"
                    <#elseif column.javaType=='Integer' || column.javaType=='int' || column.javaType=='Long' || column.javaType=='long' || column
                    .javaType=='Double' || column.javaType=='double' || column.javaType=='BigDecimal'>
                    type="2"
                    <#else>
                    type="1"
                    </#if>
                    align="2"
                    width="5000"
            />
            </#list>
        </header>
    </sheet>
</workbook>