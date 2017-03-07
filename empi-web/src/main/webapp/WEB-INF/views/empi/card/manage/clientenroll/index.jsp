<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="客户端注册"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/empi/card/manage/clientenroll/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'ip',width:150">ip地址</th>
			    <th data-options="field:'userName',width:150">用户名</th>
			    <th data-options="field:'mac',width:150">网卡物理地址</th>
			    <th data-options="field:'department',width:150">所属科室</th>
			    <th data-options="field:'createDate',width:150">注册时间</th>
				<th data-options="field:'hapiVersionInfo',width:150">客户端hapi版本</th>
				<th data-options="field:'auth',width:80,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否认证</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:400,height:300});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:400,height:300});" href="javascript:void(0);">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
		</div>
	</div>
<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">

</script>