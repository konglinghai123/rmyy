<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>融智数据采集</title>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css" title="default">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
    </head>
    <body class="easyui-layout">      
      	<div data-options="region:'center'" style="overflow:hidden;">
            <div class="easyui-tabs" id="systemtab" fit="true" border="false">
                <div title="首页" style="padding:5px;overflow:hidden;text-align:center">
                  <h1> 欢迎使用融智数据采集系统!</h1>
                 <div id="Column2D"></div>  
                </div>
            </div>      	
      	</div>
    </body>
</html>
 
<script src="${ctx}/static/fcf/js/FusionCharts.js"></script>
<!--  
<script type="text/javascript"> 
    var ctx = ${pageContext.request.contextPath};
	$.post(ctx + "/fcf/test", {}, function(result) {
	    var myChart = new FusionCharts(ctx + "/static/fcf/swf/MSColumn2D.swf?ChartNoDataText=无数据显示", "myChartId", "400", "300");
	    myChart.setJSONData(result);      
		myChart.render("Column2D");
	});  
</script>  
--> 

<script type="text/javascript">  
	var ctx = '${ctx}';
    var chart = new FusionCharts(ctx+"/static/fcf/swf/MSColumn3D.swf", "myChartId", "500", "400");  
    chart.setDataXML('<graph basefontsize="12" showNames="1" decimalPrecision="0" formatNumberScale="0"><set name="01月" value="0" color="FF0000"/><set name="02月" value="0" color="FF0000"/><set name="03月" value="0" color="FF0000"/><set name="04月" value="0" color="FF0000"/><set name="05月" value="0" color="FF0000"/><set name="06月" value="0" color="FF0000"/><set name="07月" value="0" color="FF0000"/><set name="08月" value="0" color="FF0000"/><set name="09月" value="0" color="FF0000"/><set name="10月" value="0" color="FF0000"/><set name="11月" value="0" color="FF0000"/><set name="12月" value="0" color="FF0000"/></graph>');
    chart.render("Column2D");  
 </script>　