# code_generator
spring boot搭建的一个代码自动生成工具，可自动生成controller/service/model/jsp/js

A java code generator using spring boot

##生成规则
1. 表字段带有ID字样，在页面上不生成查询条件及不展示列；
2. 数据库字段配置为时间格式（Types.TIMESTAMP）时，jsp页面上查询条件会对应加上开始/结束时间，model会生成两个属性，service层会加上查询条件；
3. 数据库字段在常量表(tbl_constant_data)中配置为常量，则在jsp页面中生成为下拉框，并在js初始化中通过ajax获取下拉框中的值