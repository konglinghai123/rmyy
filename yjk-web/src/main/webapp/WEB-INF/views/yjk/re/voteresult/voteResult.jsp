<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="最终结果"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true,">编号</th>
				<th data-options="field:'passSum',width:60">赞成票</th>
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
				<th data-options="field:'affirmVoteResulted',width:100,
						formatter:function(val,row){
							return val ? '是' : '';
						}">确认最终结果</th>
			    <c:forEach items="${currentReviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${currentReviewProcess.reviewBaseRule.ruleName == 'addCommonName'||currentReviewProcess.reviewBaseRule.ruleName == 'addSpecificationsAndPill'}">
							<th data-options="field:'${displayColumn.ruleName}',width:${displayColumn.width},
									formatter:function(val,row){
										if(row.drugForm.commonNameContents==null){
										 	return '';
										}else{
											return formatTooltip(row.${displayColumn.ruleName}, row);
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:when>
						<c:otherwise>
							<th data-options="field:'${fn:substring(displayColumn.ruleName,6,fn:length(displayColumn.ruleName)-6)}',width:${displayColumn.width},
									formatter:function(val,row){
										if(row.drugForm.commonNameContents==null){
										 	return '';
										}else{
											return formatTooltip(row.${fn:substring(displayColumn.ruleName,6,fn:length(displayColumn.ruleName)-6)}, row);
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
			<a id="tb-adjust" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-forced-open',plain:true">调入</a>
			<a id="tb-cancel" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-forced-closure'">调出</a>
			<a id="tb-print" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">打印</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-export.js"></script>
<script type="text/javascript">
	var caption = '${currentReviewProcess.reviewBaseRule.ruleCnName}';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voteresult/${currentReviewProcess.id}/queryVoteResult',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			rowStyler: function(index,row){
				if (row.selected){
	    			return 'background-color:#DDDDFF;color:#000000;';
	        	}
	    	},
			onLoadSuccess:function(row){
			}
		});
	});
	
	$('#tb-print').bind('click', function(){
		$('#tt').datagrid('toExcel','dg.xls');
	});
	
	$('#tb-adjust').bind('click', function(){
		$.messager.confirm('提示', '确定要把选中的记录调整到入围里吗？', function(r){
			if (r){
				var rows = $('#tt').datagrid('getSelections');
		    	
		    	if(rows.length == 0){
			        $.messager.alert('提示','请选择入围的记录','info');
			        return;
			    }
			    
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/${currentReviewProcess.id}/adjust', parameter, function(result) {
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
		$.messager.confirm('提示', '确定要把选中的记录调整到出围里吗？', function(r){
			if (r){
				var rows = $('#tt').datagrid('getSelections');
		    	
		    	if(rows.length == 0){
			        $.messager.alert('提示','请选择出围的记录','info');
			        return;
			    }
			    
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/${currentReviewProcess.id}/cancel', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		return htmlOperation;
	}
</script>
