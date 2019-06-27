<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="最终结果"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"/>
				<th data-options="field:'operation',width:100,align:'center',formatter:formatOperation"/>操作</th>
			    <th data-options="field:'id',hidden:true,">编号</th>
				<th data-options="field:'passSum',width:80">赞成票</th>
				<th data-options="field:'opposeSum',width:80">返对票</th>
				<th data-options="field:'abstainSum',width:80">弃权票</th>
				<th data-options="field:'selected',width:100,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否入围</th>
				<th data-options="field:'affirmVoteResulted',width:100,
						formatter:function(val,row){
							return val ? '是' : '';
						}">确认最终结果</th>
				<th data-options="field:'adjusted',width:100,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否调整入围</th>
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
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
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
	    	},
			onLoadSuccess:function(row){
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
