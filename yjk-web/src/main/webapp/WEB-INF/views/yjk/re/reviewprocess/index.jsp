<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审流程"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true" rowspan="2"/>
			    <th data-options="field:'weight',width:80" rowspan="2">排序号</th>
				<th data-options="field:'ruleCnName',width:150,
						formatter:function(val,row){
							return row.reviewBaseRule != null ? row.reviewBaseRule.ruleCnName : '';
						}" rowspan="2">规格名称</th>
				<th data-options="field:'finished', width:100,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							else return val ? '是' : '否';
						}" rowspan="2">是否完成</th>
				<th colspan="2">拟新增通用名</th>
				<th colspan="2">拟新增剂型/规格</th>
				<th data-options="field:'displayColumnRuleCnNames',width:300" rowspan="2">显示字段集合</th>
			</tr>
			<tr>
				<th data-options="field:'generalNameChinese',width:100">中成药</th>
				<th data-options="field:'generalNameWestern',width:100">西药</th>
				<th data-options="field:'formulaChinese',width:100">中成药</th>
				<th data-options="field:'formulaWestern',width:100">西药</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/yjk/re/reviewprocess/${reviewMainId}/save',title:'新增',width:750,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({src:'${ctx}/yjk/re/reviewprocess/${reviewMainId}/save',title:'修改',width:750,height:450});">修改</a>
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-hide'">互换</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/reviewprocess/${reviewMainId}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:true,
	        rowStyler: function(index,row){
	        	if (row.enabled){
	    			return 'background-color:#FF0000;color:#FFFFFF;';
	        	}
	    	},
			onBeforeLoad:function(param){
				param['parameters']=$('#queryform').serializeObject();
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
						$.post('${ctx}/yjk/re/reviewprocess/' + rows[1].id + '/' + rows[0].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}else{
						$.post('${ctx}/yjk/re/reviewprocess/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
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
</script>