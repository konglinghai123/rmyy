<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 -评审基本规则设置"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/re/zd/reviewbaserule/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="20%"><form:label path="ruleName">规则字段名：</form:label></td>
						<td width="80%"><form:input path="ruleName" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
					</tr>
		        	<tr>
						<td><form:label path="ruleCnName">规则字段中文名：</form:label></td>
						<td><form:input path="ruleCnName" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
					</tr>
					<tr>
						<td><form:label path="injectDeclarationLimt">注射一品限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="injectDeclarationLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>
		        	<tr>
						<td><form:label path="oralDeclarationLimt">口服一品限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="oralDeclarationLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>
		        	<tr>
						<td><form:label path="otherDeclarationLimt">外用及其他一品限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="otherDeclarationLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>
					<tr>
						<td><form:label path="isHospitalData">是否显示院用数据：</form:label></td>
						<td><form:checkbox path="isHospitalData"/></td>
					</tr>
					<tr>
						<td><form:label path="displayColumns">所要显示字段：</form:label></td>
						<td><form:input path="displayColumns" cssClass="easyui-combobox" data-options="url:'${ctx}/yjk/re/zd/displaycolumn/canUse',valueField:'id',textField:'ruleCnName',editable:false,multiple:true,multiline:true,panelHeight:140,height:100" cssStyle="width:100%;"/></td>
					</tr>								
				</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:resetForm();">重置</a>
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
	                "url": "${ctx}/yjk/re/zd/reviewbaserule/validate",
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
	    $('#displayColumns').combobox('setValues', ${m.displayColumnsIds});
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
	
	function resetForm(){
		$('#editForm').form('reset');
		$('#displayColumns').combobox('setValues', ${m.displayColumnsIds});
	}

</script>
	