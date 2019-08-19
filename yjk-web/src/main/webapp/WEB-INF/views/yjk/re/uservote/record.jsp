<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>
<ewcms:head title="专家评审投票"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'hemicalSubCategor',hidden:true">化药小类</th>
			    <th data-options="field:'voteTypeInfo',width:80">投票操作</th>
 				<c:forEach items="${reviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${reviewProcess.reviewBaseRule.ruleName == acn||reviewProcess.reviewBaseRule.ruleName == asap}">
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
							<th data-options="field:'${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}',width:${displayColumn.width},
									formatter:function(val,row){
										if(row.commonNameContents==null){
										 	return '';
										}else{
											return formatTooltip(row.${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}, row);
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
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-groupview.js"></script>
<script type="text/javascript">
	var caption = '';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voterecord/${user.id}/${reviewProcess.id}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			border:false,
            singleSelect: true,
            groupField:'chemicalSubCategory',
		    view: groupview,
		    groupFormatter:function(value, rows){
		        return value + ' - ' + rows.length + ' 条';
		    }
		});
	});
	
	function print(){
		caption='用户：${user.realname} 在 ${reviewProcess.reviewBaseRule.ruleCnName} 中的投票结果明细';
		$('#tt').datagrid('toExcel','userVoteRecord.xls');
	}

	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
</script>