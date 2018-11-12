<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 -评审专家设置"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/re/reviewexpert/${reviewMainId}/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="30%"><form:label path="director">是否科主任：</form:label></td>
						<td width="70%"><form:radiobuttons path="director" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
					</tr>
		        	<tr>
						<td><form:label path="secondDirector">是否科副主任：</form:label></td>
						<td><form:radiobuttons path="secondDirector" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
					</tr>					
		        	<tr>
						<td><form:label path="pharmacy">是否药事会成员：</form:label></td>
						<td><form:radiobuttons path="pharmacy" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
					</tr>					
		        	<tr>
						<td><form:label path="science">是否院学术委员会成员：</form:label></td>
						<td><form:radiobuttons path="science" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
					</tr>					
		        	<tr>
						<td><form:label path="antibiosis">是否院抗菌药物遴选小组成员：</form:label></td>
						<td><form:radiobuttons path="antibiosis" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
					</tr>					
					<tr>
						<td><form:label path="organizations">科室/病区：</form:label></td>
						<td><form:input path="organizations" cssClass="easyui-combotree" data-options="url:'${ctx}/security/organization/organization/tree',editable:false,multiple:true,width:200,editable:false,multiple:true,onlyLeafCheck:true,onBeforeSelect:function(node){return false;}"/><font color="red">不选择为所有科室</font></td>
					</tr>	
					<tr>
						<td><form:label path="departmentAttributes">科室属性：</form:label></td>
						<td><form:input path="departmentAttributes" cssClass="easyui-combobox" data-options="url:'${ctx}/security/dictionary/departmentAttribute/canUse',valueField:'id',textField:'name',editable:false,multiple:true,width:200"/><font color="red">不选择为所有科室属性</font></td>
					</tr>
					<tr>
						<td><form:label path="professions">执业类别：</form:label></td>
						<td><form:input path="professions" cssClass="easyui-combobox" data-options="url:'${ctx}/security/dictionary/profession/canUse',valueField:'id',textField:'name',editable:false,multiple:true,width:200"/><font color="red">不选择为所有执业类别</font></td>
					</tr>
					<tr>
						<td><form:label path="technicalTitles">技术职称(资格)：</form:label></td>
						<td><form:input path="technicalTitles" cssClass="easyui-combobox" data-options="url:'${ctx}/security/dictionary/technicalTitle/canUse',valueField:'id',textField:'name',editable:false,multiple:true,width:200"/><font color="red">不选择为所有技术职称(资格)</font></td>
					</tr>
					<tr>
						<td><form:label path="appointments">聘任：</form:label></td>
						<td><form:input path="appointments" cssClass="easyui-combobox" data-options="url:'${ctx}/security/dictionary/appointment/canUse',valueField:'id',textField:'name',editable:false,multiple:true,width:200"/><font color="red">不选择为所有聘任</font></td>
					</tr>
					<tr height="80">
						<td><form:label path="percent">比率：</form:label></td>
						<td height="280"><form:input path="percent" class="easyui-slider" style="width:300px" data-options="showTip: true,value:100,rule: [0,'|',25,'|',50,'|',75,'|',100],tipFormatter: function(value){return value+'%';}"/></td>
					</tr>
					<tr>
						<td><form:label path="randomNumber">随机人数：</form:label></td>
						<td><form:input path="randomNumber" cssClass="validate[custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
					</tr>
					<tr>
						<td><form:label path="departmentNumber">确保每部门人数：</form:label></td>
						<td><form:input path="departmentNumber" cssClass="validate[custom[integer]]" maxlength="4" size="10"/><font color="red">0代表不限</font></td>
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
	    
	    $('#organizations').combotree('setValues', ${m.organizationIds});
	    $('#departmentAttributes').combobox('setValues', ${m.departmentAttributeIds});
	    $('#technicalTitles').combobox('setValues', ${m.technicalTitleIds});
	    $('#professions').combobox('setValues', ${m.professionIds});
	    $('#appointments').combobox('setValues', ${m.appointmentIds});
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
	