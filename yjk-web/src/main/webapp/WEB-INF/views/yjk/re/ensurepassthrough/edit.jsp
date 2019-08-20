<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 特定科室通过数"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/re/ensurepassthrough/${reviewProcessId}/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="weight"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td width="30%"><form:label path="organizations">科室/病区：</form:label></td>
						<td width="70%"><form:input path="organizations" cssClass="easyui-combotree" data-options="url:'${ctx}/security/organization/organization/tree',editable:false,multiple:true,width:200,editable:false,multiple:true,onlyLeafCheck:true,onBeforeSelect:function(node){return false;}"/><font color="red">不选择为所有科室</font></td>
					</tr>	
					<tr>
						<td><form:label path="chineseNumber">确保申报科室通过中成药数：</form:label></td>
						<td><form:input path="chineseNumber" cssClass="validate[custom[integer]]" maxlength="4" size="10"/></td>
					</tr>
					<tr>
						<td><form:label path="westernNumber">确保申报科室通过西药数：</form:label></td>
						<td><form:input path="westernNumber" cssClass="validate[custom[integer]]" maxlength="4" size="10"/></td>
					</tr>
				</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:resetForm();">重置</a>
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
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	    
	    $('#organizations').combotree('setValues', ${m.organizationIds});
	});
	
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
	
	function resetForm(){
		$('#editForm').form('reset');
	    $('#organizations').combotree('setValues', ${m.organizationIds});
	}

</script>
	