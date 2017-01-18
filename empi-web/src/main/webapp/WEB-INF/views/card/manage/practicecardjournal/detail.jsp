<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="诊疗卡流水帐"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/card/manage/practicecardjournal/${practiceCardId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'journalDate',width:150">操作日期</th>
			    <th data-options="field:'journalOperateInfo',width:80">流水操作</th>
			    <th data-options="field:'journalSum',width:80">金额</th>
			    <th data-options="field:'operateUserName',width:80">操作员</th>
			    <th data-options="field:'remark',width:80">说明</th>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		      			
           		</table>
        	</form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	

</script>