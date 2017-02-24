require(['select2', 'select2CN', 'bootstrapTable', 'bootstrapTableCN', 'bootstrap', 'layer'], function () {

    layer.config({
        path: '/res/js/plugins/layer/',
        skin: 'layer-ext-moon',
        extend: 'skin/moon/style.css'
    });

    var buildParams = function(params) {
        var params2 = {}
        $.each($('#form').serializeArray(),function(i,e) {
            params2[e.name] = e.value;
        })
        $.extend(params2, params);
        return params2;
    };

    $('#table').bootstrapTable({
        striped: 'true',
        pagination: "true",
        sidePagination: "server",
        pageSize: "20",
        pageList: "[10, 20, 50]",
        method: "post",
        contentType:"application/x-www-form-urlencoded",
        url:"/${table.lowerJavaName}/list.do",
        queryParams: function(params){
            return buildParams(params);
        }
    });


    //查询
    $('#query-data-btn').click(function () {
        $('#table').bootstrapTable("refresh");
    });


    //清空
    $('#reset-btn').click(function () {
        $('#form')[0].reset();
    });

    // 导出
    $('#export-btn').click(function(){
        var rowData = $('#table').bootstrapTable('getData');
        if (rowData.length <= 0) {
            parent.layer.alert('没有数据需要导出！');
            return false;
        }

        //弹出加载框
        var index = layer.load(0,{shade:false});

        $.ajax({
            type: "POST",
            url: "/${table.lowerJavaName}/export.do",
            data: buildParams(),
            success:function (result) {
                //关闭加载框
                layer.close(index);
                if(result.code==0){
                    location.href = result.data;
                    msg.alert("下载成功");
                }else{
                    msg.error(result.message || "操作失败");
                }
            }
        });
    })

    //绑定下拉框
    <#list table.columnModels as column>
        <#if column.constantData==true>
    bindSelect("${column.lowerJavaName}", "/select/getCommonSelect.do?model=${config.module}&type=${column.columnName}");   //${column.columnComment}
        </#if>
    </#list>

})

var operateFormatter = function (value, row) {
    var detailUrl = "/${table.lowerJavaName}/${table.lowerJavaName}Detail.do";
    //var show = "<a onclick='OpenNewIframe(\"" + detailUrl + "?id=" + row.${table.primaryKeys[0]} + "\",\"详情\")'>详情</a>";
    var show = "";
    show += "&nbsp;&nbsp; <a onclick='deleteItem(" + row.id + ")'>删除</a>";
    return show;
}

var deleteItem = function (id, title) {
    if (!title) {
        title = "该数据";
    }
    var hint = "确定要删除" + title + "吗";
    msg.confirm(hint, function (index) {
        layer.close(index);
        msg.load();
        $.getJSON("/${table.lowerJavaName}/delete.do?id=" + id, function (jsonData) {
            msg.load(true);
            if (jsonData.code != 0) {
                msg.warn(jsonData.message);
            } else {
                $("#table").bootstrapTable("refresh");
            }
        });
    });
}
