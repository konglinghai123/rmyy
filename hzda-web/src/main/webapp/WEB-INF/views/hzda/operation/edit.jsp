<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 手术史"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/operation/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td>
		        			<form:checkbox path="hysterectomy"/>子宫切除&nbsp;&nbsp;&nbsp;&nbsp;(年龄<form:input path="hysterectomyAge" size="3" cssClass="validate[custom[integer]]"/>)
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		        			<form:checkbox path="oophorectomize"/>卵巢切除&nbsp;&nbsp;&nbsp;&nbsp;(年龄<form:input path="oophorectomizeAge" size="3" cssClass="validate[custom[integer]]"/>)
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		        			<form:checkbox path="thyroidectomy"/>甲状腺切除&nbsp;&nbsp;&nbsp;&nbsp;(年龄<form:input path="thyroidectomyAge" size="3" cssClass="validate[custom[integer]]"/>)
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		        			<form:checkbox path="parathyroidectomy"/>甲状旁腺切除&nbsp;&nbsp;&nbsp;&nbsp;(年龄<form:input path="parathyroidectomyAge" size="3" cssClass="validate[custom[integer]]"/>)
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		        			<form:checkbox path="usedBoneCement"/>使用过骨水泥&nbsp;&nbsp;&nbsp;&nbsp;(年龄<form:input path="usedBoneCementAge" size="3" cssClass="validate[custom[integer]]"/>)
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		        			<form:checkbox path="other"/>其他&nbsp;&nbsp;&nbsp;&nbsp;(年龄<form:input path="otherAge" size="3" cssClass="validate[custom[integer]]"/>)
		        		</td>
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