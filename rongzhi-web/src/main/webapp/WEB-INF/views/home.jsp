<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>融智数据采集</title>
    	<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css" title="default">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
    </head>
    <body class="easyui-layout">      
      	<div data-options="region:'center'" style="overflow:hidden;">
            <div class="easyui-tabs" id="systemtab" fit="true" border="false">
                <div title="首页" style="padding:5px;overflow:hidden;text-align:center">
                  <h1> 欢迎使用融智数据采集系统!</h1>
                  <table>
                  	<tr>
	                  	<td><div id="Column2D"></div></td>   
	                  	<td><div id="Column2D1"></div></td>
                  	</tr>
                  </table>
                </div>
            </div>      	
      	</div>
    </body>
</html>
 <script type="text/javascript"> 
	var ctx = '${ctx}';
</script>
<script src="${ctx}/static/fcf/js/FusionCharts.js"></script>
<script src="${ctx}/static/js/polling.js"></script>
<script type="text/javascript"> 
    /* var jsondata='{"graph":{"caption":"每月销售额柱形图","xAxisName":"月份","yAxisName":"Units"},"set":[{"name":"一月","value":"462","color":"AFD8F8"},{"name":"二月","value":"857","color":"F6BD0F"},{"name":"三月","value":"671","color":"8BBA00"}]}';
    var chart1 = new FusionCharts(ctx+"/static/fcf/swf/Column3D.swf", "myChartId1", "200", "400");  
    chart1.setJSONData(jsondata);
    chart1.render("Column2D1");   */
    
 	$.post(ctx + "/fcf/test", {}, function(result) {
	    var myChart = new FusionCharts(ctx + "/static/fcf/swf/Column3D.swf?ChartNoDataText=无数据显示", "myChartId", "200", "300");
	    myChart.setJSONData(result);      
		myChart.render("Column2D1");
	}); 
    var poll = new Poll();

	var dataXml = "<graph caption='每月销售额柱形图' xAxisName='月份' yAxisName='Units' showNames='1' decimalPrecision='0' formatNumberScale='0'><set name='一月' value='462' color='AFD8F8' /><set name='二月' value='857' color='F6BD0F' /><set name='三月' value='671' color='8BBA00' /><set name='四月' value='494' color='FF8E46' /><set name='五月' value='761' color='008E8E' /><set name='六月' value='960' color='D64646' /><set name='七月' value='629' color='8E468E' /><set name='八月' value='622' color='588526' /><set name='九月' value='376' color='B3AA00' /><set name='十月' value='494' color='008ED6' /><set name='十一月' value='761' color='9D080D' /><set name='十二月' value='960' color='A186BE' /></graph>";
    var chart = new FusionCharts(ctx+"/static/fcf/swf/Column3D.swf", "myChartId", "500", "400");  
    chart.setDataXML(dataXml);
    chart.render("Column2D");  
 </script>　