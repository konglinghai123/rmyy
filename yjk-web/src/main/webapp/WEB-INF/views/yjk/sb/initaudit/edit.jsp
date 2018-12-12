<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="申报-新药初审"/>
<c:choose>
	<c:when test="${empty(selections)}">
		<ewcms:showMessage/>
	</c:when>
	<c:otherwise>
		<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
			<ewcms:showAlertMessage/>
			<div data-options="region:'center',border:false">
				<form:form id="editForm" method="post" action="${ctx}/yjk/sb/initaudit/save" commandName="m"  class="form-horizontal">
	        		<ewcms:showGlobalError commandName="m"/>
			    	<c:forEach var="selection" items="${selections}">
		  				<input type="hidden" name="selections" value="${selection}" />
					</c:forEach>
				  	<table class="formtable">
			        	<tr>
							<td width="25%">初审是否通过：</td>
							<td width="75%">
								<select id="isAuditPassed" name="isAuditPassed" class="validate[required]">
									<option value="">--选择初审结果--</option>
									<option value="false">初审不通过</option>
									<option value="true">初审通过</option>
								</select>
						</tr>
						<tr>
							<td width="25%">初审说明：</td>
							<td width="75%"><textarea name="remark"	id="remark" style="width:200px;height:100px" cssClass="validate[required]"> </textarea></td>
						</tr>					
					</table>          	
				</form:form>
			</div>
			<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:pageSubmit();">提交</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
			</div>
		</div>

		
		<script type="text/javascript">
			function pageSubmit(){
				if ($('#isAuditPassed').val() == '') {
					 $.messager.alert('提示','请选择初审是否通过','info');
					return;
				}  
				
				if ($('#remark').val() == '') {
					 $.messager.alert('提示','初审说明不能为空','info');
					return;
				} 
				$('#editForm').submit();
			}
		</script>
	</c:otherwise>
</c:choose>
<ewcms:footer/>
	