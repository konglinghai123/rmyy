<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 通用名"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/zd/commonname/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="antibacterialsed"/>
		    	<form:hidden path="antibacterialseCategory"/>
		    	<form:hidden path="antibacterialseNumber"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="30%"><form:label path="commonName">通用名：</form:label></td>
						<td width="70%"><form:input path="commonName" cssClass="validate[required]"/></td>
					</tr>
					<tr>
						<td><form:label path="bidCommonName">省招标通用名：</form:label></td>
						<td><form:input path="bidCommonName"/></td>
					</tr>					
					<tr>
						<td><form:label path="drugCategory">药品种类：</form:label></td>
						<td>		           					
							<form:select path="drugCategory" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
							  	<form:options items="${drugCategoryList}" itemLabel="info"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td><form:label path="matchNumber">匹配编号：</form:label></td>
						<td><form:input path="matchNumber" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
					</tr>
					<tr>
						<td><form:label path="chemicalBigCategory">化药大类：</form:label></td>
						<td><form:input path="chemicalBigCategory"/></td>
					</tr>
					<tr>
						<td><form:label path="chemicalSubCategory">化药小类：</form:label></td>
						<td><form:input path="chemicalSubCategory"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="enabled">是否启用：</form:label></td>
						<td width="30%"><form:checkbox path="enabled"/></td>
					</tr>
					<c:if test="${!empty(m.id)}">
					<tr>
						<td><form:label path="spell">全拼：</form:label></td>
						<td><form:input path="spell" cssClass="validate[required]"/></td>
					</tr>
					<tr>
						<td><form:label path="spellSimplify">简拼：</form:label></td>
						<td><form:input path="spellSimplify" cssClass="validate[required]"/></td>
					</tr>										
					</c:if>
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
	                "url": "${ctx}/yjk/zd/commonname/validate",
	                extraDataDynamic : ['#id'],
	                "alertTextLoad": "* 正在验证，请稍等。。。"
	            };
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
	