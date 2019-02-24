<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 家庭史"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/family/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td>
		        			是否有直系亲属患者有骨质疏松症：<form:checkbox path="inealKinOsteoporosis"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			与患者关系<form:input path="relationsWithPatients" size="3"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        			是否骨折过：<form:checkbox path="fracture"/>
		        		</td>
					</tr>
					<tr>
		        		<td>
							患者母亲是否骨折过：<form:checkbox path="patientMother"/>&nbsp;&nbsp;&nbsp;&nbsp;
							骨折部位<form:input path="montherFracture" size="3"/>&nbsp;&nbsp;&nbsp;&nbsp;
							是否脆性骨折：<form:checkbox path="montherBrittleness"/>
						</td>
					</tr>
					<tr>
		        		<td>
							患者父亲是否骨折过：<form:checkbox path="patientFather"/>&nbsp;&nbsp;&nbsp;&nbsp;
							骨折部位<form:input path="fatherFracture" size="3"/>&nbsp;&nbsp;&nbsp;&nbsp;
							是否脆性骨折：<form:checkbox path="fatherBrittleness"/>
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