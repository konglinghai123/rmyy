<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑- 骨密度"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/hzda/bonedensity/save/${generalInformationId}" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td width="20%"><form:label path="examinationDate">日期：</form:label></td>
						<td width="30%"><form:input path="examinationDate"  class="easyui-datebox" style="width:120px"  /></td>
						<td width="20%"><form:label path="lumbarBMD">腰椎BMD：</form:label></td>
						<td width="30%"><form:input path="lumbarBMD" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="lumbarT">腰椎T值：</form:label></td>
						<td width="30%"><form:input path="lumbarT"/></td>
						<td width="20%"><form:label path="lumbarZ">腰椎Z值：</form:label></td>
						<td width="30%"><form:input path="lumbarZ" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="femoralDiameterBMD">股骨颈BMD：</form:label></td>
						<td width="30%"><form:input path="femoralDiameterBMD"/></td>
						<td width="20%"><form:label path="femoralDiameterT">股骨颈T值：</form:label></td>
						<td width="30%"><form:input path="femoralDiameterT" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="femoralDiameterZ">股骨颈Z值：</form:label></td>
						<td width="30%"><form:input path="femoralDiameterZ" /></td>
						<td width="20%"><form:label path="coxaBMD">髋总BMD：</form:label></td>
						<td width="30%"><form:input path="coxaBMD" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="coxaT">髋总T值：</form:label></td>
						<td width="30%"><form:input path="coxaT"/></td>
						<td width="20%"><form:label path="coxaZ">髋总Z值：</form:label></td>
						<td width="30%"><form:input path="coxaZ" /></td>
					</tr>					
					<tr>
						<td width="20%"><form:label path="remark">备注：</form:label></td>
						<td width="30%"><form:input path="remark"/></td>
						<td width="20%"></td>
						<td width="30%"></td>
					</tr>
				</table>
			</form:form>
		</div>
		<c:if test="${!user.admin}">
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
		</c:if>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		  <c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
				
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