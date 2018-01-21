<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="个人中心 - 消息"/>
	<div id="tab-message" class="easyui-tabs" data-options="border:false,fit:true,tabPosition:'right',headerWidth:75">
		<c:forEach items="${states}" var="s">
            <c:if test="${s ne 'delete_box'}">
            	<div title="${s.info}" style="overflow:hidden;">
            		<iframe id="${s}" name="${s}" class="editifr" src="${ctx}/personal/message/${s}/index"></iframe>
            	</div>
            </c:if>
		</c:forEach>
	</div>
<ewcms:footer/>