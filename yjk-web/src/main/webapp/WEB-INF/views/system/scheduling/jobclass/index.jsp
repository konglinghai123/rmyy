<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="类设置"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/system/scheduling/jobclass/query',nowrap:true,pagination:true,rownumbers:true,pageSize:30">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'name',width:200">名称</th>
			    <th data-options="field:'className',width:500">类实体</th>
			    <th data-options="field:'description',width:400">描述</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:700,height:210});" href="javascript:void(0);">新增</a> 
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:700,height:210});" href="javascript:void(0);">修改</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
		<div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="6%">名称：</td>
              			<td width="19%"><input type="text" name="LIKE_name" style="width:120px"/></td>
    					<td width="6%">类实体：</td>
    					<td width="19%"><input type="text" name="LIKE_className" style="width:120px"/></td>
    					<td width="6%">描述：</td>
    					<td width="19%"><input type="text" name="LIKE_description" style="width:120px"/></td>
						<td width="25%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
        	</form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>