<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审流程操作记录"/>
	<table id="tt">
		<thead>
			<tr>
				 <th data-options="field:'id',sortable:true">编号</th>
			    <th data-options="field:'userName',width:150">操作员</th>
				<th data-options="field:'operateDate',width:150">操作时间</th>
				<th data-options="field:'remark',width:300">操作原因</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/reviewprocessrecord/${reviewProcessId}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			pagination:true,
			striped:true,
			pageSize:20,
			border:true
		});
	});
</script>