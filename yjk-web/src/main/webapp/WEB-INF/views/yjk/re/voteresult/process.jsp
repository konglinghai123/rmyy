<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="投票结果 - 流程"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false" style="text-align:center">
			<c:choose>
				<c:when test="${isClose}">
					本次评审的流程已全部结束，以下为最终结果！
				</c:when>
				<c:otherwise>
				评审流程：
		  		<c:forEach items="${reviewProcessesList}" var="reviewProcess" varStatus="status">
					<c:choose>
						<c:when test="${reviewProcess.reviewBaseRule.ruleName == currentReviewProcess.reviewBaseRule.ruleName}">
							<font color="red" style="font-size:14px;"><b>${reviewProcess.reviewBaseRule.ruleCnName}</b></font>
						</c:when>
						<c:otherwise>
							${reviewProcess.reviewBaseRule.ruleCnName}
						</c:otherwise>
					</c:choose>
					<c:if test="${!status.last}">
		    	   			<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">
		    	   	</c:if>	
				</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
</script>
