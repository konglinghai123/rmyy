<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="bean数据源"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',sortable:true">编号</th>
				<th data-options="field:'name',width:100">名称</th>
				<th data-options="field:'beanName',width:200">Bean名称</th>
				<th data-options="field:'beanMethod',width:200">Bean方法</th>
				<th data-options="field:'remarks',width:300">说明</th>
				<th data-options="field:'operation',width:120,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px;"/></td>
            			<td width="5%">Bean名称</td>
              			<td width="23%"><input type="text" name="LIKE_beanName" style="width:140px;"/></td>
            			<td width="5%">Bean方法</td>
              			<td width="23%"><input type="text" name="LIKE_beanMethod" style="width:140px;"/></td>
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
			url:'${ctx}/system/externalds/bean/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
			onLoadSuccess:function(row){
				$('.connect').linkbutton({text:'连接测试',plain:true,iconCls:'icon-connect'});
			}
		});
	});

	function formatOperation(val, row){
		return '<a class="connect" onclick="isConnect(' + row.id + ')" href="javascript:void(0);">连接测试</a>';
	}
	
	function isConnect(id){
		$.post('${ctx}/system/externalds/isConnect/' + id , {}, function(data) {
			$.messager.alert('提示', data, 'info');
		});
	}
</script>