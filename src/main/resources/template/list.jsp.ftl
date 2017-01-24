<%@page pageEncoding="UTF-8" language="java" import="cn.nubia.tcm.framework.JSVersion" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.nubia.cn/tag/rbac" prefix="rbac" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${table.tableComment}查询</title>
    <link href="/res/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/res/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="/res/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/res/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="/res/css/style_tcm.css?v=4.1.0" rel="stylesheet">
    <link href="/res/js/plugins/select2/css/select2.css" rel="stylesheet">
    <link href="/res/css/animate.min.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">

            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>${table.tableComment}查询</h5>
                </div>

                <div class="ibox-content store">

                    <form id="form" class="form-horizontal">

                    <#list table.columnModels as column>
                        <#if column_index%3==0>
                        <div class="form-group">
                        </#if>
                            <label class="control-label col-sm-1">${column.columnComment}</label>
                        <#if column.dataType==93>
                            <div class="col-sm-3">
                                <div class="form-inline">
                                    <div class="input-group">
                                        <input id="begin${column.upperJavaName}" readonly=true name="begin${column.upperJavaName}" class="form-control layer-date"
                                               onclick="laydate({format: 'YYYY-MM-DD',max:$('#end${column.upperJavaName}').val()})">
                                    </div>
                                    <label>至</label>
                                    <div class="input-group">
                                        <input id="end${column.upperJavaName}" readonly=true name="end${column.upperJavaName}" class="form-control layer-date"
                                               onclick="laydate({format: 'YYYY-MM-DD',min:$('#begin${column.upperJavaName}').val()})">
                                    </div>
                                </div>
                            </div>
                        <#elseif  column.constantData==true>
                            <div class="col-sm-2">
                                <select id="${column.lowerJavaName}" name="${column.lowerJavaName}" class="form-control"></select>
                            </div>
                        <#else>
                            <div class="col-sm-2">
                                <input id="${column.lowerJavaName}" name="${column.lowerJavaName}" class="form-control">
                            </div>
                        </#if>
                        <#if column_index%3==2>
                        </div>
                        </#if>

                    </#list>
                        <#if table.columnModels?size%3==0>
                        <div class="form-group">
                            <div class="col-sm-3 col-sm-offset-9 form-inline">
                                <button type="button" class="btn btn-info" id="query-btn">查询</button>
                                <button type="button" class="btn btn-info" id="reset-btn" style="margin-left:5px">重置</button>
                                <button type="button" class="btn btn-info" id="export-btn" style="margin-left:5px">导出</button>
                            </div>
                        </div>
                        <#else>
                            <div class="col-sm-3">
                                <button type="button" class="btn btn-info" id="query-btn">查询</button>
                                <button type="button" class="btn btn-info" id="reset-btn" style="margin-left:5px">重置</button>
                                <button type="button" class="btn btn-info" id="export-btn" style="margin-left:5px">导出</button>
                            </div>
                        </div>
                        </#if>
                    </form>
                    <table id="table">
                        <thead>
                            <#list table.columnModels as column>
                            <#if column.dataType==93>
                            <th data-field="${column.lowerJavaName}" data-formatter="tableDateFormat">${column.columnComment}</th>
                            <#elseif column.constantData==true>
                            <th data-field="${column.lowerJavaName}Show" >${column.columnComment}</th>
                            <#else>
                            <th data-field="${column.lowerJavaName}">${column.columnComment}</th>
                            </#if>
                            </#list>
                            <th data-formatter="operateFormatter" data-align="center">操作</th>
                        </thead>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/res/js/require.js"></script>
<script src="/res/js/require-config.js"></script>
<script src="/res/js/content.min.js?v=1.0.0"></script>
<script src="/res/js/plugins/layer/laydate/laydate.js"></script>
<script src="/res/js/common/common.js?<%= JSVersion.jsVersion %>"></script>
<script src="/${config.module}/list${table.upperJavaName}.js?<%= JSVersion.jsVersion %>"></script>
</body>
</html>