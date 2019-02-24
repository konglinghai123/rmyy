<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 婚育史"/>
	<c:choose>
	<c:when test="${isShow}">
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/marriagebearing/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td>
		        			月经初潮年龄<form:input path="menarcheAge" size="3" cssClass="validate[custom[number]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			绝经年龄<form:input path="menopauseAge" size="3" cssClass="validate[custom[number]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			孕<form:input path="pregnant" size="3" cssClass="validate[custom[integer]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			产<form:input path="delivery" size="3" cssClass="validate[custom[integer]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			活产<form:input path="liveBirth" size="3" cssClass="validate[custom[integer]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			死产<form:input path="deadBirth" size="3" cssClass="validate[custom[integer]]"/>
		        		</td>
					</tr>
					<tr>
						<td>
							自然流产<form:input path="naturalAbortion" size="3" cssClass="validate[custom[integer]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
							人工流产<form:input path="abactio" size="3" cssClass="validate[custom[integer]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
							药物流产<form:input path="drugAbortion" size="3" cssClass="validate[custom[integer]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
							首次妊娠年龄<form:input path="primigravida" size="3" cssClass="validate[custom[number]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
							末次妊娠年龄<form:input path="lastGestationalAge" size="3" cssClass="validate[custom[number]]"/>&nbsp;&nbsp;&nbsp;&nbsp;
							哺乳总时间<form:input path="totalLactationTime" size="3" cssClass="validate[custom[number]]"/>月
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
	</c:when>
	<c:otherwise>
	<div class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">	
			<h1 class="title">男性患者不需要填写婚育史</h1>
		</div>
	</div>
	</c:otherwise>
</c:choose>
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