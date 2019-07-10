<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 -系统参数设置"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/sp/systemparameter/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="30%"><form:label path="applyStartDate">申请开始时间<font color="red">*</font>：</form:label></td>
						<td width="70%"><form:input path="applyStartDate"  class="easyui-datetimebox" style="width:200px" data-options="
							editable:false,
							required: true,
							onSelect:function(d){
								var now = new Date();
					        	ed = new Date($('#applyEndDate').datebox('getValue'));
					            if (d < now) {
					            	alert('开始日期不能小于当前日期');
					            	$(this).datebox('setValue', '').datebox('showPanel');
					            	return;
					            }
					            if (ed < d) {
					            	alert('结束日期不能小于开始日期');
					                $(this).datebox('setValue', '').datebox('showPanel');
					            }
					        }"/>
						</td>
					</tr>
		        	<tr>
						<td><form:label path="applyEndDate">申请结束时间<font color="red">*</font>：</form:label></td>
						<td><form:input path="applyEndDate"  class="easyui-datetimebox"  style="width:200px" data-options="
							editable:false,
							required: true,
							onSelect:function(d){
								var now = new Date();
					        	sd = new Date($('#applyStartDate').datebox('getValue'));
					            if (d < now) {
					            	alert('结束日期不能小于当前日期');
					            	$(this).datebox('setValue', '').datebox('showPanel');
					            	return;
					            }					        	
					            if (d < sd) {
					               	alert('结束日期不能小于开始日期');
					                $(this).datebox('setValue', '').datebox('showPanel');
					            }
					        }"/>
						</td>
					</tr>
		        	<tr>
						<td><form:label path="repeatDeclared">是否可以重复申报：</form:label></td>
						<td><form:checkbox path="repeatDeclared"/></td>
					</tr>					
		        	<tr>
						<td><form:label path="injectDeclarationLimt">注射两规限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="injectDeclarationLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>
		        	<tr>
						<td><form:label path="oralDeclarationLimt">口服两规限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="oralDeclarationLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>
		        	<tr>
						<td><form:label path="otherDeclarationLimt">外用及其他两规限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="otherDeclarationLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>										
		        	<tr>
						<td><form:label path="declareTotalLimt">每个人总报限数<font color="red">*</font>：</form:label></td>
						<td><form:input path="declareTotalLimt" cssClass="validate[required,custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>	
					<tr>
						<td><form:label path="projectRemark">项目说明：</form:label></td>
						<td><form:input path="projectRemark" cssStyle="width:200px;" cssClass="validate[required]"/></td>
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
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
	
	function pageSubmit(){
		if($('#applyStartDate').val() == ''){
			$.messager.alert('提示','申请开始时间不能为空','info');
			return;
		}
		if($('#applyEndDate').val() == ''){
			$.messager.alert('提示','申请结束时间不能为空','info');
			return;
		}
		$('#editForm').submit();
	}
</script>
	