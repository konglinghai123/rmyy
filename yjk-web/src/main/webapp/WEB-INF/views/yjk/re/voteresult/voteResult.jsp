<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>
<c:choose>
	<c:when test="${isResult}">
<ewcms:head title="最终结果"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',sortable:true">编号</th>
				<th data-options="field:'passSum',width:60">通过票</th>
				<th data-options="field:'opposeSum',width:60">反对票</th>
				<th data-options="field:'abstainSum',width:60">弃权票</th>
				<th data-options="field:'selected',width:80,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否入围</th>
				<th data-options="field:'adjusted',width:80,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否调整</th>
				<th data-options="field:'affirmVoteResulted',width:80,
						formatter:function(val,row){
							return val ? '是' : '';
						}">本轮结果</th>
			    <c:forEach items="${currentReviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${currentReviewProcess.reviewBaseRule.ruleName == acn||currentReviewProcess.reviewBaseRule.ruleName == asap}">
							<th data-options="field:'${displayColumn.ruleName}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return formatTooltip(row.${displayColumn.ruleName}, row);
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:when>
						<c:otherwise>
							<th data-options="field:'${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return formatTooltip(row.${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}, row);
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:otherwise>
					</c:choose>
 				</c:forEach>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
        	<c:if test="${!isClose }">
			<a id="tb-adjust" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-forced-open',plain:true">调入</a>
			<a id="tb-cancel" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-forced-closure'">调出</a>
			<a id="tb-affirm" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">本轮确认</a>
			<c:choose>
			<c:when test="${isNextEnable}">
			<a id="tb-next" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-next'">进入下一轮</a>
			</c:when>
			<c:otherwise>
			<a id="tb-exit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'">结束投票</a>
			</c:otherwise>
			</c:choose>
			</c:if>
			<a id="tb-print" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">打印</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	var caption = '所有用户在 ${currentReviewProcess.reviewBaseRule.ruleCnName} 中投票结果统计';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voteresult/queryVoteResult',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			rowStyler: function(index,row){
				if (row.selected){
					if( row.drugForm.commonNameContents.common.drugCategory=='Z'){
						return 'background-color:#DDDDFF;color:#000000;';
					}else{
						return 'background-color:#C4E1FF;color:#000000;';
					}
	    			
	        	}
	    	},
			onLoadSuccess:function(row){
			}
		});
	});
	
	$('#tb-print').bind('click', function(){
		$('#tt').datagrid('toExcel','voteResult.xls');
	});
	
	$('#tb-adjust').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择入围的记录','info');
	        return;
	    }
		$.messager.confirm('提示', '确定要把选中的记录调整到入围里吗？', function(r){
			if (r){
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/adjust', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	$('#tb-cancel').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择出围的记录','info');
	        return;
	    }
    	
		$.messager.confirm('提示', '确定要把选中的记录调整到出围里吗？', function(r){
			if (r){
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/cancel', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	$('#tb-affirm').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择确认结果的记录','info');
	        return;
	    }
    	$.messager.confirm('提示', '确定要把选中的记录变成最终结果吗？如果点确认后选中的记录将不能进行任何调整，要继续操作吗？', function(r){
			if (r){
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/affirm', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	$('#tb-next').bind('click', function(){
    	$.messager.confirm('提示', '确认要进入下一轮投票流程吗？', function(r){
			if (r){
				$.post('${ctx}/yjk/re/voteresult/next', {reason:'进入下一轮'}, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
						parent.$('#ifrprocess')[0].contentWindow.location.reload();
					}
					alert(result.message);
					window.location.reload();
				});
			}
	 	});
	});
	
	$('#tb-exit').bind('click', function(){
		$.messager.confirm('提示', '确认要结束投票吗？', function(r){
			if (r){
				$.post('${ctx}/yjk/re/voteresult/next', {reason:'结束投票'}, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
						parent.$('#ifrprocess')[0].contentWindow.location.reload();
					}
					alert(result.message);
					window.location.reload();
				});
			}
	 	});
	});
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
</script>
</c:when>
<c:otherwise>
<ewcms:head title="评审监控 - 投票结果"/>
			<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
				<div data-options="region:'center',fit:true" style="border:0;">	
					<h1 class="title">本轮投票还未完成，还不能生成投票结果！</h1>
				</div>
			</div>
		<ewcms:footer/>
</c:otherwise>
</c:choose>