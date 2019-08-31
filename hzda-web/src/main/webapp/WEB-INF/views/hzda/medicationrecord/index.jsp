<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/hzda/medicationrecord/query/${generalInformationId}',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'medicationDate',width:120">日期</th>
			    <th data-options="field:'vitd',width:100">VitD</th>
				<th data-options="field:'calcium'">钙剂</th>
				<th data-options="field:'diphosphonate',width:100">双膦酸盐</th>
			    <th data-options="field:'calcitonin'">降钙素</th>
			    <th data-options="field:'teriparatide',width:100">特立帕肽</th>
				<th data-options="field:'denosumab'">地诺单抗</th>
				<th data-options="field:'chineseMedicine',width:100">中药</th>
				<th data-options="field:'other',width:100">其他</th>					
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<c:if test="${!user.admin}">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/hzda/medicationrecord/save/${generalInformationId}',title:'新增',width:780,height:360,left:100});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/hzda/medicationrecord/save/${generalInformationId}',title:'修改',width:780,height:360,left:100});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/hzda/medicationrecord/delete/${generalInformationId}',title:'删除'});">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$.ewcms.query();">刷新</a>
		</div>
		</c:if>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>