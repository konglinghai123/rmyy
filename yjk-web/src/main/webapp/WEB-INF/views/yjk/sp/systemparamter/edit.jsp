<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 -系统参数设置"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/sp/systemparamter/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="30%"><form:label path="applyStartDate">申请开始时间：</form:label></td>
						<td width="70%"><input type="text" id="applyStartDate" name="applyStartDate"  class="easyui-datetimebox" style="width:145px" data-options="
							editable:false,
							required: true,
							onSelect:function(d){
								var now = new Date();
					        	ed = new Date($('#applyEndDate').datebox('getValue'));
					            if (d < now) {
					            	alert('开始日期不能小于当前日期');
					            	$('#applyStartDate').datebox('setValue', '').datebox('showPanel');
					            	return;
					            }
					            if (ed < d) {
					            	alert('结束日期不能小于开始日期');
					                $('#applyStartDate').datebox('setValue', '').datebox('showPanel');
					            }
					        }"/>
						</td>
					</tr>
		        	<tr>
						<td width="30%"><form:label path="applyEndDate">申请结束时间：</form:label></td>
						<td width="70%"><input type="text" id="applyEndDate" name="applyEndDate"  class="easyui-datetimebox"  style="width:145px" data-options="
							editable:false,
							required: true,
							onSelect:function(d){
								var now = new Date();
					        	sd = new Date($('#applyStartDate').datebox('getValue'));
					            if (d < now) {
					            	alert('结束日期不能小于当前日期');
					            	$('#applyEndDate').datebox('setValue', '').datebox('showPanel');
					            	return;
					            }					        	
					            if (d < sd) {
					               	alert('结束日期不能小于开始日期');
					                $('#applyEndDate').datebox('setValue', '').datebox('showPanel');
					            }
					        }"/>
						</td>
					</tr>
		        	<tr>
						<td width="30%"><form:label path="declarationLimt">申报限数：</form:label></td>
						<td width="70%"><form:input path="declarationLimt" cssClass="validate[required,custom[integer]]"/></td>
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
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
	