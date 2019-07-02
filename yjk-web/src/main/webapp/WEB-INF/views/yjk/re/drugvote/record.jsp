<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="药品投票情况"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true,">编号</th>
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
						}">最终结果</th>
			    <c:forEach items="${reviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${reviewProcess.reviewBaseRule.ruleName == 'addCommonName'||reviewProcess.reviewBaseRule.ruleName == 'addSpecificationsAndPill'}">
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
											if(row.commonNameContents==null){
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
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	var caption = '所有用户在 ${reviewProcess.reviewBaseRule.ruleCnName} 中投票结果统计';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/drugvote/${reviewProcess.id}/queryVoteResult?reviewMainId=${reviewMainId}',
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
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/yjk/re/drugvote/${reviewProcess.id}/' + rowData.id + '/user?reviewMainId=${reviewMainId}" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			},
			onBeforeLoad:function(param){
				param['parameters']=$('#queryform').serializeObject();
			}
		});
	});
	
	function print(){
		$('#tt').datagrid('toExcel','drugvote.xls');
	}
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		return htmlOperation;
	}
</script>
