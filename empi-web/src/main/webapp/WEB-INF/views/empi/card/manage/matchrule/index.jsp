<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="匹配规则管理"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/empi/card/manage/matchrule/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:50">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'fieldName',width:150">字段名称</th>
			    <th data-options="field:'cnName',width:150">中文名称</th>
			    <th data-options="field:'matched',width:90,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否为匹配项</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.add({title:'匹配规则设置',width:600,height:300,src:'${ctx}/empi/card/manage/matchrule/match'});" href="javascript:void(0);">匹配规则设置</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:400,height:200});" href="javascript:void(0);">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
		</div>
	</div>
<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">

</script>