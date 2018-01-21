<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="唯一索引号变更轨迹"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/empi/card/manage/practicecardindexhistory/${practiceNo}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			<tr>
				<th data-options="field:'beforePatientId',width:150">变更前唯一索引号</th>
			    <th data-options="field:'afterPatientId',width:150">变更后唯一索引号</th>
			    <th data-options="field:'remark',width:150">变更说明</th>
			    <th data-options="field:'changeDate',width:150">变更时间</th>
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