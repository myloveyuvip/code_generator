# code_generator

##简介
spring boot搭建的一个代码自动生成工具，可自动生成model/service/controller/jsp/js

###功能：
+ 查询页面（支持导出）
+ 删除功能

##默认约定规则
1. 类名及属性取自数据库字段（下划线转驼峰）；中文显示取自注释
2. 表字段带有ID字样，在页面上不生成查询条件及不展示列；
3. 数据库字段配置为时间格式（Types.TIMESTAMP）时，jsp页面上查询条件会对应加上开始/结束时间，model会生成两个属性，service层会加上查询条件；
4. 数据库字段在常量表(tbl_constant_data)中配置为常量，则在jsp页面中生成为下拉框，并在js初始化中通过ajax获取下拉框中的值
5. 导出功能：时间字段默认格式化为yyyy-MM-dd

##使用指南
1. 规范填写数据库注释