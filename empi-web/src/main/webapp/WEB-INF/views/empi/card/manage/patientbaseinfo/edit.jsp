<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑- 患者基本信息"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,bordar:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/empi/card/manage/patientbaseinfo/save" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
				  		<td width="80"><form:label path="name">姓名：</form:label></td>
				  		<td><form:input path="name" cssClass="validate[required]"/></td>
				  		<td><form:label path="sex">性别：</form:label></td>
						<td><form:select path="sex.id" id="sexId" items="${sexList}" itemValue="id" itemLabel="cnName" cssClass="easyui-combobox" cssStyle="margin-left:0px;z-index:1;position:absolute;" data-options="panelWidth:150,panelHeight:130,editable:false"></form:select></td>	
			    		<td><form:label path="birthday">出生日期：</form:label></td>
				  		<td><input type="text" id="birthday_show" value="${m.birthday}" class="validate[required, custom[date]]" style="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
						    <form:input id="birthday" cssClass="inputempty" path="birthday"  cssStyle="margin-left:0px;z-index:1;position:absolute;" /></td>
			    	</tr>
			    	<tr>						    
				  		<td><form:label path="certificateType">证件类型：</form:label></td>
				  		<td><form:input path="certificateType" cssClass="validate[required]"/></td>
				  		<td><form:label path="certificateNo">证件号码：</form:label></td>
				  		<td><form:input path="certificateNo" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>	
						<td><form:label path="sourcePlace">来源地：</form:label></td>
				  		<td><form:input path="sourcePlace" cssClass="inputempty"/></td>				  		
			    	</tr>
			    	<tr>				  		
						<td><form:label path="telephone">联系电话：</form:label></td>
				  		<td><form:input path="telephone" cssClass="inputempty"/></td>
				  		<td><form:label path="countryCode">国籍：</form:label></td>
						<td><form:select path="countryCode.id" id="countryCodeId" items="${countryCodeList}" itemValue="id" itemLabel="cnName" cssClass="easyui-combobox" cssStyle="margin-left:0px;z-index:1;position:absolute;" data-options="panelWidth:150,panelHeight:130,editable:false"></form:select></td>	
				  		<td><form:label path="nation">民族：</form:label></td>
						<td><form:input path="nation" cssClass="inputempty"/></td>
			    	</tr>			    		
		        	<tr>			  		
				  		<td><form:label path="workUnit">工作单位：</form:label></td>
				  		<td colspan="2"><form:input path="workUnit" cssClass="inputempty" size="35"/></td>
				  		
				  		<td><form:label path="address">通讯地址：</form:label></td>
				  		<td colspan="2"><form:input path="address"  cssClass="inputempty" size="35"/></td>
			    	</tr>	
			    	<tr>				  		
						<td><form:label path="province">省：</form:label></td>
				  		<td><form:input path="province" cssClass="inputempty"/></td>
				  		<td><form:label path="city">市：</form:label></td>
				  		<td><form:input path="city" cssClass="inputempty"/></td>
				  		<td><form:label path="birthPlace">出生地：</form:label></td>
						<td><form:input path="birthPlace" cssClass="inputempty"/></td>
			    	</tr>
			    	<tr>
						<td><form:label path="profession">职业：</form:label></td>
				  		<td><form:input path="profession" cssClass="inputempty"/></td>
				  		<td><form:label path="marital">婚姻状况：</form:label></td>
						<td><form:select path="marital.id" id="maritalId" items="${maritalList}" itemValue="id" itemLabel="cnName" cssClass="easyui-combobox" cssStyle="margin-left:0px;z-index:1;position:absolute;" data-options="panelWidth:150,panelHeight:130,editable:false"></form:select></td>	
				  		<td><form:label path="medicalType">医保类别：</form:label></td>
				  		<td><form:input path="medicalType" cssClass="inputempty"/></td>
			    	</tr>
			    	<tr>
				  		<td><form:label path="medicalOrganize">医保机构：</form:label></td>
						<td><form:input path="medicalOrganize" cssClass="inputempty"/></td>
						<td><form:label path="medicalAccount">医保账号：</form:label></td>
				  		<td><form:input path="medicalAccount" cssClass="inputempty"/></td>
				  		<td><form:label path="patientType">病人类别：</form:label></td>
				  		<td><form:input path="patientType" cssClass="inputempty"/></td>
			    	</tr>	
			    	<tr>				  		
				  		<td><form:label path="contactTelephone">联系人电话：</form:label></td>
				  		<td><form:input path="contactTelephone" cssClass="inputempty"/></td>
				  		<td><form:label path="contactRelation">与联系人关系：</form:label></td>
						<td><form:input path="contactRelation" cssClass="inputempty"/></td>
						<td><form:label path="contactAddress">联系人地址：</form:label></td>
				  		<td><form:input path="contactAddress" cssClass="inputempty"/></td>
			    	</tr>	
			    	<tr>				  		
				  		<td><form:label path="allergyHistory">过敏史：</form:label></td>
				  		<td colspan="5"><form:input path="allergyHistory" cssClass="inputempty" size="97"/></td>
			    	</tr>	
			    	<tr>
				  		<td><form:label path="familyHistory">家族史：</form:label></td>
						<td colspan="5"><form:input path="familyHistory" cssClass="inputempty" size="97"/></td>
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
				$.validationEngineLanguage.allRules.ajaxNameCall= {
	                "url": "${ctx}/empi/card/manage/patientbaseinfo/validate",
	                extraDataDynamic : ['#id'],
	                "alertTextLoad": "* 正在验证，请稍等。。。"
	            };
				
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
		$('#birthday').datebox({
			onSelect:function(date){
				$('#birthday_show').val(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			}
		});
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>