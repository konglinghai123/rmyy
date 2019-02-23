<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 骨折史"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/cataclasis/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td colspan="3">患者是否骨折过：<form:checkbox path="boneDismantling"/>是&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3">患者过去一年跌倒过<form:input path="fall" size="3" cssClass="validate[custom[integer]]"/>次</td>
					</tr>
					<tr>
						<td colspan="3">骨折次数<form:input path="number" size="3" cssClass="validate[custom[integer]]"/></td>
					</tr>
					<tr>
						<td width="20%">骨折年龄<form:input path="age1" size="3" cssClass="validate[custom[integer]]"/></td>
						<td width="30%"><form:checkbox path="brittleness1"/>脆性&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="violence1"/>暴力</td>
						<td width="50%">骨折部位<form:input path="position1"/></td>
					</tr>
					<tr>
						<td >骨折年龄<form:input path="age2" size="3" cssClass="validate[custom[integer]]"/></td>
						<td><form:checkbox path="brittleness2"/>脆性&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="violence2"/>暴力</td>
						<td>骨折部位<form:input path="position2"/></td>
					</tr>
					<tr>
						<td >骨折年龄<form:input path="age3" size="3" cssClass="validate[custom[integer]]"/></td>
						<td><form:checkbox path="brittleness3"/>脆性&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="violence3"/>暴力</td>
						<td>骨折部位<form:input path="position3"/></td>
					</tr>
					<tr>
						<td >骨折年龄<form:input path="age4" size="3" cssClass="validate[custom[integer]]"/></td>
						<td><form:checkbox path="brittleness4"/>脆性&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="violence4"/>暴力</td>
						<td>骨折部位<form:input path="position4"/></td>
					</tr>
					<tr>
						<td >骨折年龄<form:input path="age5" size="3" cssClass="validate[custom[integer]]"/></td>
						<td><form:checkbox path="brittleness5"/>脆性&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="violence5"/>暴力</td>
						<td>骨折部位<form:input path="position5"/></td>
					</tr>
				</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		var validationEngine = $("#editForm").validationEngine({
			promptPosition:'bottomLeft',
			showOneMessage: true
		});
    	<ewcms:showFieldError commandName="m"/>
	});
</script>