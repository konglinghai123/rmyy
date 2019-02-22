<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑- 用药记录"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/hzda/medicationrecord/save/${generalInformationId}" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td width="20%"><form:label path="medicationDate">日期：</form:label></td>
						<td width="30%"><form:input path="medicationDate"  class="easyui-datebox" style="width:200px"  /></td>
						<td width="20%"><form:label path="vitd">VitD：</form:label></td>
						<td width="30%"><form:input path="vitd" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="calcium">钙剂：</form:label></td>
						<td width="30%"><form:input path="calcium"/></td>
						<td width="20%"><form:label path="diphosphonate">双膦酸盐：</form:label></td>
						<td width="30%"><form:input path="diphosphonate" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="calcitonin">降钙素：</form:label></td>
						<td width="30%"><form:input path="calcitonin"/></td>
						<td width="20%"><form:label path="teriparatide">特立帕肽：</form:label></td>
						<td width="30%"><form:input path="teriparatide" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="denosumab">地诺单抗：</form:label></td>
						<td width="30%"><form:input path="denosumab" /></td>
						<td width="20%"><form:label path="chineseMedicine">中药：</form:label></td>
						<td width="30%"><form:input path="chineseMedicine" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="other">其他：</form:label></td>
						<td width="30%"><form:input path="other"/></td>
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