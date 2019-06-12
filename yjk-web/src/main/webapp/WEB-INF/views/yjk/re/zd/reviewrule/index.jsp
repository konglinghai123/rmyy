<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-评审规则设置"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'ruleCnName',width:180">规则字段中文名</th>
				<th data-options="field:'weight',width:100">排序号</th>
				<th data-options="field:'enabled',width:130,formatter:formatOperation">是否启用</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:500,height:265});">新增</a>
			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>        
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-hide'">互换</a>
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
			url:'${ctx}/yjk/re/zd/reviewrule/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
			onLoadSuccess:function(row){
				$('.closeCls').linkbutton({text:'关闭',plain:true,iconCls:'icon-exit'});
				$('.openCls').linkbutton({text:'启用',plain:true,iconCls:'icon-operate'});
			}
		});
		
		$('#tb-exchange').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    
	    	if(rows.length != 2){
		        $.messager.alert('提示','请选择2条互换的记录','info');
		        return;
		    }
			$.messager.confirm('提示', '确定要互换所选记录吗?', function(r){
				if (r){
					if(rows[0].weight<rows[1].weight){
						$.post('${ctx}/yjk/re/zd/reviewrule/' + rows[1].id + '/' + rows[0].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}else{
						$.post('${ctx}/yjk/re/zd/reviewrule/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
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

	function formatOperation(val, row){
		if(row.id==1 || row.id==2){
			return val ? '是 ' : '否';
		}else{
			return val ? '是&nbsp;|&nbsp;<a class="closeCls" onclick="javascript:closeEnabled(' + row.id + ');" href="javascript:void(0);">关闭</a>' : '否&nbsp;|&nbsp;<a class="openCls" onclick="javascript:openEnabled(' + row.id + ');" href="javascript:void(0);">启用</a>';
		}
		
	}
	
	function closeEnabled(id){
		$.post('${ctx}/yjk/re/zd/reviewrule/' + id + '/close', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
	function openEnabled(id){
		$.post('${ctx}/yjk/re/zd/reviewrule/' + id + '/open', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>