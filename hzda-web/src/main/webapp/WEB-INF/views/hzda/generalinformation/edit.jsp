<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 一般信息"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/generalinformation/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="20%"><form:label path="totalNumber">总编号：</form:label></td>
						<td width="30%"><form:input path="totalNumber" readonly="true" placeholder="自动生成，无需填写"/></td>
						<td width="20%"><form:label path="transplantNumber">移植编号：</form:label></td>
						<td width="30%"><form:input path="transplantNumber"/></td>
					</tr>
					<tr>
						<td><form:label path="name">姓名：</form:label></td>
						<td><form:input path="name" cssClass="validate[required]"/></td>
						<td><form:label path="hospitalizationNumber">住院号：</form:label></td>
						<td><form:input path="hospitalizationNumber"/></td>
					</tr>
					<tr>
						<td><form:label path="dxaNumber">DXA号：</form:label></td>
						<td><form:input path="dxaNumber"/></td>
						<td><form:label path="sex">性别：</form:label></td>
						<td><form:radiobuttons path="sex" items="${sexList}" itemLabel="description" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td><form:label path="birthday">出生年月：</form:label></td>
						<td>
							<input type="text" id="birthday_show" class="validate[custom[date]]" value="${m.birthday}" style="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
							<form:input path="birthday" cssStyle="margin-left:0px;z-index:1;position:absolute;"/>
						</td>
						<td><form:label path="nation">民族：</form:label></td>
						<td>
							<form:input path="nation.name" id="nationName" cssStyle="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
							<form:input path="nation.id" id="nationId" cssStyle="margin-left:0px;z-index:1;position:absolute;"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="degreeEducation">文化程度：</form:label></td>
						<td><form:input path="degreeEducation"/></td>
						<td><form:label path="occupation">职业：</form:label></td>
						<td><form:input path="occupation"/></td>
					</tr>
					<tr>
						<td><form:label path="address">现住址：</form:label></td>
						<td><form:input path="address"/></td>
						<td><form:label path="certificateType">证件类型：</form:label></td>
						<td>
							<form:input path="certificateType.name" id="certificateTypeName" cssStyle="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
							<form:input path="certificateType.id" id="certificateTypeId" cssStyle="margin-left:0px;z-index:1;position:absolute;"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="certificateNumber">证件号：</form:label></td>
						<td><form:input path="certificateNumber"/></td>
						<td><form:label path="medicalInsuranceNumber">医保号：</form:label></td>
						<td><form:input path="medicalInsuranceNumber"/></td>
					</tr>
					<tr>
						<td><form:label path="mobilePhoneNumber">手机号码：</form:label></td>
						<td><form:input path="mobilePhoneNumber"/></td>
						<td><form:label path="otherTelephone">其他联系电话：</form:label></td>
						<td><form:input path="otherTelephone"/></td>
					</tr>
					<tr>
						<td>特殊患者标记：</td>
						<td><form:radiobuttons path="specialTab" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
						<td>特殊患者编号：</td>
						<td><form:input path="specialTabNumber"/></td>
					</tr>
					<tr>
						<td><form:label path="remakrs">备注：</form:label></td>
						<td><form:textarea path="remakrs" style="width:200px;height:60px"/></td>
						<td>eGFR：</td>
						<td><form:input path="eGFR"/>
					</tr>
					<tr>
						<td>OSTA评分：</td>
						<td colspan="3">
							<form:input path="oSTA" readonly="true" placeholder="(体重-年龄)×0.2;结果>-1为低,-1至-4为中,<-4为高"/>&nbsp;&nbsp;体重：<form:input path="weight" size="3" cssClass="validate[custom[integer]]"/>Kg&nbsp;&nbsp;年龄：<form:input path="age" size="3" cssClass="validate[custom[integer]]"/>岁&nbsp;&nbsp;
							<a id="tb-calculate" class="easyui-linkbutton" data-options="iconCls:'icon-sum'" href="javascript:void(0);">计算</a>
						</td>
					</tr>
					<tr>
						<td>FRAX评估：主要部位骨折风险</td>
						<td><form:input path="fRAXMain"/></td>
						<td>FRAX评估：髋部骨折风险</td>
						<td><form:input path="fRAXHipbone"/></td>
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
		    	$('#birthday').datebox({
					onSelect:function(date){
						$('#birthday_show').val(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
					}
				});
				$('#nationId').combobox({
					panelWidth:150,
					panelHeight:130,
					url:'${ctx}/hzda/zd/nation/canUse',
					method:'get',
					valueField:'id',
					textField:'name',
					editable:false,
					onSelect:function(record){
						$('#nationName').val(record.name);
					}
				});
				$('#certificateTypeId').combobox({
					panelWidth:150,
					panelHeight:130,
					url:'${ctx}/hzda/zd/certificatetype/canUse',
					method:'get',
					valueField:'id',
					textField:'name',
					editable:false,
					onSelect:function(record){
						$('#certificateTypeName').val(record.name);
					}
				});
				
				$('#tb-calculate').bind('click', function(){
					if ($('#weight').val() != '' && $('#age').val() != ''){
						var osta = ($('#weight').val() - $('#age').val())*0.2
						if (osta > -1){
							$('#oSTA').val('低');
						} else if (osta < -4) {
							$('#oSTA').val('高');
						} else {
							$('#oSTA').val('中');
						}
					}
				})
				
				var validationEngine = $("#editForm").validationEngine({
	    			relative: true,
	    	        overflownDIV: '#divOverflown',
	    	    	promptPosition:'topLeft',
	    	    	showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
	