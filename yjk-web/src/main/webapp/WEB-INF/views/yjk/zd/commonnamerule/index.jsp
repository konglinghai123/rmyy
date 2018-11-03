<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-匹配规则设置"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'ruleCnName',width:100">规则字段</th>
				<th data-options="field:'weight',width:100">排序号</th>
				<th data-options="field:'enabled',width:100,formatter:formatOperation">是否启用</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-hide',toggle:true">互换</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">规则名</td>
              			<td width="23%"><input type="text" name="LIKE_ruleName" style="width:140px;"/></td>
            			<td width="5%">规则中文名</td>
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
			url:'${ctx}/yjk/zd/commonnamerule/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false
		});
		
		$('#tb-exchange').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    
	    	if(rows.length != 2){
		        $.messager.alert('提示','请选择2条互换的记录','info');
		        return;
		    }
		    if(rows[0].id==1||rows[1].id==1||rows[0].id==2||rows[1].id==2){
		        $.messager.alert('提示','通用名和给药途径规则不能交换顺序','info');
		        return;
		    }
			$.messager.confirm('提示', '确定要互换所选记录吗?', function(r){
				if (r){
					if(rows[0].weight<rows[1].weight){
						$.post('${ctx}/yjk/zd/commonnamerule/' + rows[1].id + '/' + rows[0].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}else{
						$.post('${ctx}/yjk/zd/commonnamerule/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
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
	
	function deleteRule(){
		var rows = $('#tt').datagrid('getSelections');
	    
    	if(rows.length != 1){
	        $.messager.alert('提示','请选择1条记录进行删除','info');
	        return;
	    }
    	if(rows[0].id==1 || rows[0].id==2){
	        $.messager.alert('提示','通用名和给药途径规则不能删除','info');
	        return;
    	}
    	$.ewcms.remove({title:'删除'});
	}

	function formatOperation(val, row){
		if(row.id==1 || row.id==2){
			return val ? '是 ' : '否';
		}else{
			return val ? '是  <a class="resumedCls" onclick="javascript:closeEnabled(' + row.id + ');" href="javascript:void(0);">关闭</a>' : '否  <a class="resumedCls" onclick="javascript:openEnabled(' + row.id + ');" href="javascript:void(0);">启用</a>';
		}
		
	}
	
	function closeEnabled(id){
		$.post('${ctx}/yjk/zd/commonnamerule/' + id + '/close', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
	function openEnabled(id){
		$.post('${ctx}/yjk/zd/commonnamerule/' + id + '/open', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>