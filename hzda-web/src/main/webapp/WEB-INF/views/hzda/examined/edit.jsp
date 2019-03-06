<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 查体"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/examined/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
						<td colspan="2">
							身高<form:input path="height" size="4" cssClass="validate[custom[number]]"/>cm&nbsp;&nbsp;&nbsp;&nbsp;
							体重<form:input path="weight" size="4" cssClass="validate[custom[number]]"/>Kg&nbsp;&nbsp;&nbsp;&nbsp;
							BMI<form:input path="BMI" size="5" cssClass="validate[custom[number]]"/>kg/m&sup2;
						</td>
					</tr>
					<tr>
						<td dth="150">脊柱生理曲度：</td>
						<td>
							<form:checkbox path="normal"/>正常&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="straighten"/>变直&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sideBending"/>侧弯

						</td>
					</tr>
					<tr>
						<td dth="150">行动状态：</td>
						<td>
							<form:checkbox path="ambulation"/>独立行走&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="abduction"/>需拄拐或他人搀扶行走&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="wheelChair"/>依靠轮椅&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="bedCare"/>长期卧床
						</td>
					</tr>					
					<tr>
						<td dth="150">是否有压痛及压痛部位：</td>
						<td>
							<form:checkbox path="centrumPain"/>椎体&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="lowerLimbPain"/>下肢&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="upperLimbPain"/>上肢&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="basinPain"/>骨盆&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="hipPain"/>髋部压痛&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="otherPain"/>其他<form:input path="otherPainPart"/>
						</td>
					</tr>
					<tr>
						<td dth="150">是否有活动受限及活动受限部位：</td>
						<td>
							<form:checkbox path="centrumConstrain"/>椎体&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="lowerLimbConstrain"/>下肢&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="upperLimbConstrain"/>上肢&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="basinConstrain"/>骨盆&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="hipConstrain"/>髋部压痛&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="otherConstrain"/>其他<form:input path="otherConstrainPart"/>
						</td>
					</tr>				
					<tr>
						<td dth="150">专科查体出现的其他症状或体征其他症状或体征</td>
						<td><form:textarea path="sign" style="width:400px;height:50px"/></td>
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