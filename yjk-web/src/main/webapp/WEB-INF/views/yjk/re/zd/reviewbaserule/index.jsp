<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-评审基本规则设置"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',sortable:true">编号</th>
				<th data-options="field:'ruleCnName',width:180">规则字段中文名</th>
				<th data-options="field:'injectDeclarationLimt',width:120,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">注射一品限数</th>
				<th data-options="field:'oralDeclarationLimt',width:120,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">口服一品限数</th>
				<th data-options="field:'otherDeclarationLimt',width:160,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">外用及其他一品限数</th>
				<th data-options="field:'isHospitalData',width:140,
						formatter:function(val,row){
							return val?'是':'否';
						}">是否显示院用数据</th>
				<th data-options="field:'displayColumnsNames',width:400">所要显示基本字段</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:600,height:400});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:600,height:400});">修改</a>       
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">规则字段名</td>
              			<td width="23%"><input type="text" name="LIKE_ruleName" style="width:140px;"/></td>
            			<td width="10%">规则字段中文名</td>
              			<td width="23%"><input type="text" name="LIKE_ruleCnName" style="width:140px;"/></td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/zd/reviewbaserule/query',
			toolbar:'#tb',
			fit:true,
			nowrap:false,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false
		});
		
	});


</script>