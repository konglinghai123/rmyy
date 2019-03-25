<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/hzda/bonedensity/query/${generalInformationId}',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true" rowspan="2"></th>
			    <th data-options="field:'id',hidden:true" rowspan="2">编号</th>
			    <th data-options="field:'examinationDate',width:120" rowspan="2">日期</th>
			    <th data-options="field:'idNo',width:100" rowspan="2">ID号</th>
			    <th colspan="3">腰椎(LI-4)</th>
			    <th colspan="3">股骨颈 </th>
			    <th colspan="3">髋总</th>
				<th data-options="field:'remark'" rowspan="2">备注</th>
			</tr>
			<tr>
				<th data-options="field:'lumbarBMD',width:100">BMD(g/cm&sup2;)</th>
			    <th data-options="field:'lumbarT',width:100">T值</th>
			    <th data-options="field:'lumbarZ',width:100">Z值</th>
				<th data-options="field:'femoralDiameterBMD',width:100">BMD(g/cm&sup2;)</th>
				<th data-options="field:'femoralDiameterT',width:100">T值</th>
				<th data-options="field:'femoralDiameterZ',width:100">Z值</th>
				<th data-options="field:'coxaBMD',width:100">BMD(g/cm&sup2;)</th>
			    <th data-options="field:'coxaT',width:100">T值</th>
			    <th data-options="field:'coxaZ',width:100">Z值</th>				
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<c:if test="${!user.admin}">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/hzda/bonedensity/save/${generalInformationId}',title:'新增',width:500,height:260,left:100});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/hzda/bonedensity/save/${generalInformationId}',title:'修改',width:500,height:260,left:100});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/hzda/bonedensity/delete/${generalInformationId}',title:'删除'});">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$.ewcms.query();">刷新</a>
		</div>
		</c:if>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>