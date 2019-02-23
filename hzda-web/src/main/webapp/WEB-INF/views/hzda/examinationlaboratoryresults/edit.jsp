<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑- 检查及化验结果"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/hzda/examinationlaboratoryresults/save/${generalInformationId}" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td width="20%"><form:label path="examinationDate">日期：</form:label></td>
						<td width="30%"><form:input path="examinationDate"  class="easyui-datebox" style="width:120px"  /></td>
						<td width="20%"><form:label path="bloodALP">血液ALP：</form:label></td>
						<td width="30%"><form:input path="bloodALP" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="bloodCr">血液Cr：</form:label></td>
						<td width="30%"><form:input path="bloodCr"/></td>
						<td width="20%"><form:label path="bloodCa">血液Ca：</form:label></td>
						<td width="30%"><form:input path="bloodCa" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="bloodP">血液P：</form:label></td>
						<td width="30%"><form:input path="bloodP"/></td>
						<td width="20%"><form:label path="bloodTco2">血液Tco2：</form:label></td>
						<td width="30%"><form:input path="bloodTco2" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="bloodOsteocalcin">血液骨钙素：</form:label></td>
						<td width="30%"><form:input path="bloodOsteocalcin" /></td>
						<td width="20%"><form:label path="bloodPINP">血液PINP：</form:label></td>
						<td width="30%"><form:input path="bloodPINP" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="bloodCTX">血液CTX：</form:label></td>
						<td width="30%"><form:input path="bloodCTX"/></td>
						<td width="20%"><form:label path="bloodVitD">血液VitD：</form:label></td>
						<td width="30%"><form:input path="bloodVitD" /></td>
					</tr>	
					<tr>
						<td width="20%"><form:label path="urinePTH">24h尿PTH：</form:label></td>
						<td width="30%"><form:input path="urinePTH"/></td>
						<td width="20%"><form:label path="urineCa">24h尿Ca：</form:label></td>
						<td width="30%"><form:input path="urineCa" /></td>
					</tr>	
					<tr>
						<td width="20%"><form:label path="urineP">24h尿P：</form:label></td>
						<td width="30%"><form:input path="urineP"/></td>
						<td width="20%"></td>
						<td width="30%"></td>
					</tr>
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