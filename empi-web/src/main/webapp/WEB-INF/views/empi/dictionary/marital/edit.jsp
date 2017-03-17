<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 -婚姻状况字典"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,bordar:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/empi/dictionary/marital/save" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
			  		<c:choose>
			    		<c:when test="${empty(m.id)}">
			    			<tr>
			    				<td><form:label path="id">关键值：</form:label></td>
			    				<td><form:input path="id" cssClass="validate[required,,ajax[ajaxNameCall]]" /></td>
			    			</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<form:hidden path="id"/>
			    		</c:otherwise>
			    	</c:choose>
		        	<tr>
				  		<td width="20%"><form:label path="cnName">中文名：</form:label></td>
				  		<td width="80%"><form:input path="cnName" cssClass="validate[required]"/></td>
			    	</tr>	
			    	<tr>
				  		<td width="20%"><form:label path="enName">英文名：</form:label></td>
				  		<td width="80%"><form:input path="enName"/></td>
			    	</tr>		    				    				    				    			    	
			  	</table>
		  	</form:form>
    	</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
	    <c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
				$.validationEngineLanguage.allRules.ajaxNameCall= {
	                "url": "${ctx}/empi/dictionary/marital/validate",
	                "alertTextLoad": "* 正在验证，请稍等。。。"
	            };
				
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>