<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 现病史"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/presentillness/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td>乏力：</td>
						<td>
							<form:label path="afterFatigue"><form:checkbox path="afterFatigue"/>活动后</form:label>&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="fatiguePersistence"/>持续性<form:input path="fatiguePersistenceHour" size="3" cssClass="validate[custom[number]]"/>小时
						</td>
					</tr>
					<tr>
						<td>疼痛部位：</td>
						<td>
							<form:checkbox path="lowBackPain"/>腰背&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="lowerLimbPain"/>下肢&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="upperLimbPain"/>上肢&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="soreRibs"/>肋骨&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="toAcheAllOver"/>全身&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="otherPain"/>其他<form:input path="otherPainDesc"/>
						</td>
					</tr>
					<tr>
						<td>脊柱变形：</td>
						<td>
							<form:checkbox path="heightShort"/>身高缩短<form:input path="heightShortNumber" size="3" cssClass="validate[custom[number]]"/>cm&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sideBend"/>侧弯&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="humpback"/>驼背&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="thoracocyllosis"/>胸廓畸形&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="extensionLimitation"/>伸展受限&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="otherDeformation"/>其他<form:input path="otherDeformationDesc"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">其他<form:input path="otherDesc"/></td>
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