<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 匹配规则字段信息"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,bordar:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/empi/card/manage/clientenroll/save" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
				  		<td width="30%"><form:label path="ip">ip地址：</form:label></td>
				  		<td width="70%"><form:input path="ip" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
			    	</tr>
		        	<tr>
				  		<td width="30%"><form:label path="mac">网卡物理地址：</form:label></td>
				  		<td width="70%"><form:input path="mac" cssClass="validate[required]"/></td>
			    	</tr>
			    	<tr>
				  		<td width="30%"><form:label path="userName">用户名：</form:label></td>
				  		<td width="70%"><form:input path="userName" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
			    	</tr>
			    	<tr>
				  		<td width="30%"><form:label path="password">密码：</form:label></td>
				  		<td width="70%"><form:input path="password" cssClass="validate[required]"/></td>
			    	</tr>
			    	<tr>
				  		<td width="30%"><form:label path="department">所属科室：</form:label></td>
				  		<td width="70%"><form:input path="department" /></td>
			    	</tr>
		        	<tr>
				  		<td width="30%"><form:label path="hapiVersion">客户端hapi版本：</form:label></td>
				  		<td><form:select path="hapiVersion" items="${hapiVersionList}" itemLabel="info" cssClass="easyui-combobox" data-options="panelWidth:80,panelHeight:80,editable:false"/></td>
			    	</tr>
			    	<tr>
				  		<td width="30%"><form:label path="auth">是否需要认证：</form:label></td>
				  		<td width="70%"><form:checkbox path="auth"/></td>
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
	                "url": "${ctx}/empi/card/manage/clientenroll/validate",
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