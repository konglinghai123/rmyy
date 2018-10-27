<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-匹配规则设置"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'ruleName',width:100">规则名</th>
				<th data-options="field:'ruleCnName',width:100">规则中文名</th>
				<th data-options="field:'weight',width:100">排序号</th>
				<th data-options="field:'deleted',width:100,formatter:formatOperation">是否删除</th>	
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:400,height:265});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:400,height:265});">修改</a>
			<a id="tb-down" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-show',toggle:true">下移</a>
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-hide',toggle:true">互换</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
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
		
		$('#tb-down').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    	
	    	if(rows.length == 0){
		        $.messager.alert('提示','请选择下移的记录','info');
		        return;
		    }
	    	if(rows.length > 1){
		        $.messager.alert('提示','请选择一条下移的记录','info');
		        return;
		    }
		    
			$.messager.confirm('提示', '确定要下移所选记录吗?', function(r){
				if (r){
					$.post('${ctx}/yjk/zd/commonnamerule/' + rows[0].weight + '/' + Number(rows[0].weight)+1 + '/down', {}, function(result) {
						if (result.success){
							$('#tt').datagrid('clearSelections');
							$('#tt').datagrid('reload');
						}
						$.messager.alert('提示', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-exchange').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    
	    	if(rows.length != 2){
		        $.messager.alert('提示','请选择2条互换的记录','info');
		        return;
		    }
		    
			$.messager.confirm('提示', '确定要互换所选记录吗?', function(r){
				if (r){
					$.post('${ctx}/yjk/zd/commonnamerule/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
						if (result.success){
							$('#tt').datagrid('clearSelections');
							$('#tt').datagrid('reload');
						}
						$.messager.alert('提示', result.message, 'info');
					});
				}
			});
		});
		
		
	});
	
	function formatOperation(val, row){
		return val ? '<font color=red>已删除</font>  <a class="resumedCls" onclick="restore(' + row.id + ')" href="javascript:void(0);">还原</a>' : '';
	}
	
	function restore(id){
		$.post('${ctx}/yjk/zd/commonnamerule/' + id + '/restore', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>