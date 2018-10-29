<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="审批-系统参数设置"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'applyStartDate',width:150">申请开始时间</th>
				<th data-options="field:'applyEndDate',width:150">申请结束时间</th>
				<th data-options="field:'declarationLimt',width:150">申报限数</th>
				<th data-options="field:'enabled',width:100,formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:400,height:350});">新增</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="10%">申请开始时间从</td>
              			<td colspan="2"><input type="text" id="applyStartDate" name="GTE_applyStartDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="applyStartDate1" name="LTE_applyStartDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
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
			url:'${ctx}/yjk/sp/systemparamter/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false
		});
	});
	
	function formatOperation(val, row){
		currentTimestamp=new Date().getTime();
		applyEndDateTimestamp=new Date(Date.parse(row.applyEndDate.replace(/-/g,"/"))).getTime();
		applyStartDatetamp=new Date(Date.parse(row.applyStartDate.replace(/-/g,"/"))).getTime();
		if(val){
			return '<a class="resumedCls" onclick="closeDeclare(' + row.id + ')" href="javascript:void(0);">关闭申报</a> ';
		}else{
			if(applyStartDatetamp<=currentTimestamp&& currentTimestamp<=applyEndDateTimestamp){
				return '<a class="resumedCls" onclick="openDeclare(' + row.id + ')" href="javascript:void(0);">启动申报</a> ';
			}else{
				return '';
			}	
		}
	}
	
	/*
	function deleteSystemParameter(id){
		$.post('${ctx}/yjk/sp/systemparamter/' + id + '/delete', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}*/
	
	function closeDeclare(id){
		$.messager.confirm('提示', '确定要关闭申报吗?', function(r){
			if (r){
				$.post('${ctx}/yjk/sp/systemparamter/' + id + '/closedeclare', {}, function(result) {
					if (result.success){
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
	}
	
	function openDeclare(id){
		$.messager.confirm('提示', '确定要启动申报吗?', function(r){
			if (r){
				$.post('${ctx}/yjk/sp/systemparamter/' + id + '/opendeclare', {}, function(result) {
					if (result.success){
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
	}
</script>