<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-通用名"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',width:80">序号</th>
				<th data-options="field:'commonName',sortable:true,width:200">通用名</th>
				<th data-options="field:'administration',sortable:true,width:100,
						formatter:function(val,row){
							return (row.administration==null) ? '' : row.administration.name;
						}">给药途径</th>
				<th data-options="field:'number',sortable:true,width:100">编号</th>
				<th data-options="field:'drugCategoryInfo',width:100">药品种类</th>
				<th data-options="field:'spell',sortable:true,width:200">全拼</th>
				<th data-options="field:'spellSimplify',sortable:true,width:150">简拼</th>
				<th data-options="field:'enabled',width:100,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否启用</th>
				<!--  <th data-options="field:'deleted',width:100,formatter:formatOperation">是否删除</th>-->				
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({src:'${ctx}/yjk/zd/specialrule/${specialRuleId}/save',title:'新增',width:600,height:365});">新增</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({src:'${ctx}/yjk/zd/specialrule/${specialRuleId}/delete',title:'删除'});">删除</a>
		</div>
        <div>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/zd/specialrule/${specialRuleId}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			border:true
		});
	});
</script>