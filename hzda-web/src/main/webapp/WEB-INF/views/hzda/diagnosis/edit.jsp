<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 诊断"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/diagnosis/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td><form:label path="boneNormal"><form:checkbox path="boneNormal"/>骨量正常</form:label></td>
					</tr>
		        	<tr>
		        		<td><form:label path="osteopenia"><form:checkbox path="osteopenia"/>骨量减少</form:label></td>
					</tr>
		        	<tr>
		        		<td><form:label path="primaryOsteoporosis"><form:checkbox path="primaryOsteoporosis"/>原发性骨质疏松症</form:label></td>
					</tr>
					<tr>
		        		<td><form:label path="primaryOsteoporosisFracture"><form:checkbox path="primaryOsteoporosisFracture"/>原发性骨质疏松症病理性骨折</form:label></td>
					</tr>
		        	<tr>
		        		<td><form:label path="secondaryOsteoporosis"><form:checkbox path="secondaryOsteoporosis"/>继发性骨质疏松症(病因:<form:input path="secondaryOsteoporosisReason"/>)</form:label></td>
					</tr>
					<tr>
		        		<td><form:label path="secondaryOsteoporosisFracture"><form:checkbox path="secondaryOsteoporosisFracture"/>继发性骨质疏松症病理性骨折(病因:<form:input path="secondaryOsteoporosisFractureReason"/>)</form:label></td>
					</tr>
		        	<tr>
		        		<td><form:label path="other"><form:checkbox path="other"/>其它<form:input path="otherReason"/></form:label></td>
					</tr>					
				</table>
			</form:form>
		</div>
		<c:if test="${!user.admin}">
			<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
			</div>
		</c:if>
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