<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="公告栏"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/system/notice/query',nowrap:true,pagination:true,rownumbers:true,striped:true,border:false,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'title',width:150,sortable:true,formatter:formatTooltip">标题</th>
			    <th data-options="field:'externalLinks',width:200,formatter:formatTooltip">外部链接地址</th>
			    <th data-options="field:'content',width:300,formatter:formatTooltip">内容</th>
			    <th data-options="field:'createDate',width:150">创建时间</th>
			    <th data-options="field:'updateDate',width:150">修改时间</th>
			    <th data-options="field:'head',width:80,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">标题</th>
			    <th data-options="field:'release',width:80,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">发布</th>
			    <th data-options="field:'weight',width:80,sortable:true">顺序号</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:850,height:360});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:850,height:360});" href="javascript:void(0);">修改</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exchange'">互换</a>
		</div>
		<div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">标题</td>
              			<td width="23%"><input type="text" name="LIKE_title" style="width:140px"/></td>
    					<td width="5%">内容</td>
    					<td width="23%"><input type="text" name="LIKE_content" style="width:140px"/></td>
    					<td width="5%"></td>
    					<td width="23%"></td>
						<td width="16%" colspan="2">
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
<script type="text/javascript">
	$(function(){
		$('#tb-exchange').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    
	    	if(rows.length != 2){
		        $.messager.alert('提示','请选择2条互换的记录','info');
		        return;
		    }
			$.messager.confirm('提示', '确定要互换所选记录吗?', function(r){
				if (r){
					if(rows[0].weight<rows[1].weight){
						$.post('${ctx}/system/notice/' + rows[1].id + '/' + rows[0].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}else{
						$.post('${ctx}/system/notice/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}
						

				}
			});
		});
	});
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
</script>