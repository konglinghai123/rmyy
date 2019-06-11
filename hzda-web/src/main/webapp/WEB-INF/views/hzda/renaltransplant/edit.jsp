<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 肾移植患者"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/renaltransplant/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td>慢性肾脏病病因：</td>
						<td>
							1糖尿病肾病<form:input path="diabeticNephropathyTime" size="3" cssClass="validate[custom[number]]"/>年
							2高血压肾病<form:input path="hypertensiveNephropathyTime" size="3" cssClass="validate[custom[number]]"/>年
							3肾小球肾炎<form:input path="glomerulonephritisTime" size="3" cssClass="validate[custom[number]]"/>年
							4多囊肾病<form:input path="polycysticKidneyTime" size="3" cssClass="validate[custom[number]]"/>年
							5狼疮肾炎<form:input path="lupusNephritisTime" size="3" cssClass="validate[custom[number]]"/>年
							6IgA肾病<form:input path="igANephropathyTime" size="3" cssClass="validate[custom[number]]"/>年
							7其他（<form:input path="otherNephropathy" size="8" />）<form:input path="otherNephropathyTime" size="3" cssClass="validate[custom[number]]"/>年
						</td>
					</tr>
					<tr>
						<td>移植前情况：</td>
						<td>
							维持性透析<form:input path="dialysisTime" size="3" cssClass="validate[custom[number]]"/>年
							1血液透析<form:input path="hemodialysisTime" size="3" cssClass="validate[custom[number]]"/>年
							2腹膜透析<form:input path="peritonealDialysisTime" size="3" cssClass="validate[custom[number]]"/>年
							3未行透析
						</td>
					</tr>
					<tr>
						<td>肾移植情况：</td>
						<td>
							1肾移植时年龄<form:input path="transplantationAge" size="3" cssClass="validate[custom[number]]"/>岁，
							肾移植时间<form:input path="transplantationYear" size="3" cssClass="validate[custom[number]]"/>年<form:input path="transplantationMonth" size="3" cssClass="validate[custom[number]]"/>月
							（或肾移植龄<form:input path="renalAge" size="3" cssClass="validate[custom[number]]"/>年）
							<br>
							2移植肾来源<form:input path="renalOrigin" size="3" cssClass="validate[custom[number]]"/>
							（1）亲属供肾（2）尸体供肾（3）其他
							<br>
							3发生急性排斥反应次数<form:input path="rejectionNumber" size="3" cssClass="validate[custom[number]]"/>次
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