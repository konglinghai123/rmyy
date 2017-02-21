<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="消息操作日志"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/empi/card/manage/messagelog/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'ip',width:150">ip地址</th>
			    <th data-options="field:'practiceNo',width:150">诊疗卡号</th>
			    <th data-options="field:'receiveDate',width:150">消息接收时间</th>
			    <th data-options="field:'sendDate',width:150">消息发送时间</th>
				<th data-options="field:'hapiOperateInfo',width:150">hapi操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">

</script>