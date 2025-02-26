<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="选择 - 申报范围"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
			<table id="tt">
				<thead>
					<tr>	
						<th data-options="field:'ck',checkbox:true"/>
						<th data-options="field:'id',sortable:true">编号</th>
						<th data-options="field:'projectRemark',width:120">项目说明</th>
						<th data-options="field:'applyStartDate',width:150">申请开始时间</th>
						<th data-options="field:'applyEndDate',width:150">申请结束时间</th>
						<th data-options="field:'repeatDeclared',width:120,
								formatter:function(val,row){
									return val?'是':'否';
								}">可否重复申报</th>				
						<th data-options="field:'declarationLimt',width:80">院用限数</th>
						<th data-options="field:'declareTotalLimt',width:80">总报限数</th>
						<th data-options="field:'nodeclareNumber',width:100">未提交初审数</th>
						<th data-options="field:'initNumber',width:100">已提交初审数</th>
						<th data-options="field:'passedNumber',width:110">初审核已通过数</th>
						<th data-options="field:'unPassedNumber',width:110">初审核未通过数</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
			<c:if test="${spId==-1}">
	  		<a id="tb-declare" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:saveSelect();">提交选择</a>
	  		</c:if>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url : '${ctx}/yjk/re/reviewmain/canUseSystemParameter',
			toolbar : '#tb',
			fit : true,
			nowrap : false,
			pagination : true,
			rownumbers : true,
			striped : true,
			pageSize : 20,
			border : false,
			singleSelect:true,
			onLoadSuccess:function(data){
				$.each(data.rows, function(i, e){
					if (e.id == ${spId}){
						$('#tt').datagrid('selectRow', i);
					}
				})
			}
		});
	});

	function saveSelect(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择要评审范围的记录','info');
	        return;
	    }
    	
	    $.messager.confirm('提示', '确定要选择此行做为评审范围吗？', function(r){
	        if (r){
	            $.post('${ctx}/yjk/re/reviewmain/${reviewMainId}/saveSelect', {systemParameterId:rows[0].id} ,function(data){
	            	if(data.success){
	            		alert(data.message);
	            		parent.$('#edit-window').window('close');
	            	}
	            	$.messager.alert('提示', data.message, 'info');
	            });
	        }
	    });
	}
</script>