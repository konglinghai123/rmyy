<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审流程"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'weight',width:100">排序号</th>
				<th data-options="field:'ruleCnName',width:150,
						formatter:function(val,row){
							return row.reviewBaseRule != null ? row.reviewBaseRule.ruleCnName : '';
						}">规格名称</th>
				<th data-options="field:'finished', width:100">是否完成</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
        	<c:if test="${isOperation}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/save',title:'新增',width:750,height:350,left:100});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({src:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/save',title:'修改',width:850,height:450,left:100});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="$.ewcms.remove({src:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/delete',title:'删除'});">删除</a>
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-hide'">互换</a>
			</c:if>
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
	        	if (!row.enabled){
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