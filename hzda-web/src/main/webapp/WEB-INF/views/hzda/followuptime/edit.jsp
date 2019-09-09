<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑- 下一次随访时间"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/hzda/followuptime/save/${generalInformationId}" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="code"/>
		    	<form:hidden path="message"/>
		    	<form:hidden path="requestId"/>
		    	<form:hidden path="sms"/>
		    	<form:hidden path="smsDate"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td width="20%"><form:label path="nextTime">下一次随访时间：</form:label></td>
						<td width="30%"><form:input path="nextTime"  class="easyui-datebox" style="width:120px"/></td>
						<td width="20%"><form:label path="tip">是否提醒：</form:label></td>
						<td width="30%"><form:radiobuttons path="tip" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
				</table>
			</form:form>
		</div>
		<c:if test="${!user.admin}">
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
		</c:if>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		  <c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
				
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomLeft',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>