<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 评审主表"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/re/reviewmain/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="30%"><form:label path="name">名称：</form:label></td>
						<td width="70%"><form:input path="name" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
					</tr>
					<!--  
					<tr>
						<td><form:label path="generalNameChinese">拟新增通用名中成药：</form:label></td>
						<td><form:input path="generalNameChinese" cssClass="validate[required, integer]"/></td>
					</tr>
					<tr>
						<td><form:label path="generalNameWestern">拟新增通用名西药：</form:label></td>
						<td><form:input path="generalNameWestern" cssClass="validate[required, integer]"/></td>
					</tr>
					<tr>
						<td><form:label path="formulaChinese">拟新增剂型/规格中成药：</form:label></td>
						<td><form:input path="formulaChinese" cssClass="validate[required, integer]"/></td>
					</tr>
					<tr>
						<td><form:label path="formulaWestern">拟新增剂型/规格西药：</form:label></td>
						<td><form:input path="formulaWestern" cssClass="validate[required, integer]"/></td>
					</tr>
					-->
		        	<tr>
						<td><form:label path="remark">说明：</form:label></td>
						<td><form:textarea path="remark" style="width:300px;height:50px" cssClass="validate[required]"/></td>
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
	                "url": "${ctx}/yjk/re/reviewmain/validate",
	                extraDataDynamic : ['#id'],
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
	