<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="发诊疗卡"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,bordar:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/empi/card/manage/practicecard/distribute" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" id="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
			  		<tr>
				  		<td width="100%" colspan="6" style="padding:0px;">
				  			<table style="width:100%">
					        	<tr>
							  		<td><form:label path="practiceNo">诊疗卡号：</form:label>&nbsp;<form:input path="practiceNo" cssClass="validate[required,ajax[ajaxNameCall]]" size="35"/></td>
						    	</tr>				  			
				  			</table>
				  		</td>
			    	</tr>			    	
		        	<tr>
				  		<td><form:label path="patientBaseInfo.certificateType">证件类型：</form:label></td>
				  		<td><form:input path="patientBaseInfo.certificateType" id="certificateType" cssClass="validate[required]"/></td>
				  		<td><form:label path="patientBaseInfo.certificateNo">证件号码：</form:label></td>
				  		<td><form:input path="patientBaseInfo.certificateNo" id="certificateNo" cssClass="validate[required]"/></td>			        	
				  		<td><form:label path="patientBaseInfo.sex"></form:label>性别：</td>
			    		<td><form:input path="patientBaseInfo.sex" id="sex" cssClass="inputempty"/></td>	
			    	</tr>
			    	<tr>
				  		<td width="80"><form:label path="patientBaseInfo.name">姓名：</form:label></td>
				  		<td><form:input path="patientBaseInfo.name" id="name" cssClass="validate[required]"/></td>	
			    		<td><form:label path="patientBaseInfo.birthday">出生日期：</form:label></td>
				  		<td><input type="text" id="birthday_show" value="${m.patientBaseInfo.birthday}" class="validate[required, custom[date]]" style="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
						    <form:input id="birthday" cssClass="inputempty" path="patientBaseInfo.birthday"  cssStyle="margin-left:0px;z-index:1;position:absolute;" /></td>
						<td><form:label path="patientBaseInfo.sourcePlace">来源地：</form:label></td>
				  		<td><form:input path="patientBaseInfo.sourcePlace" id="sourcePlace" cssClass="inputempty"/></td>
			    	</tr>
			    	<tr>	
			    		<td><form:label path="patientBaseInfo.telephone">联系电话：</form:label></td>
				  		<td><form:input path="patientBaseInfo.telephone" id="telephone" cssClass="inputempty"/></td>
				  		<td><form:label path="patientBaseInfo.nationlity">国籍：</form:label></td>
				  		<td><form:input path="patientBaseInfo.nationlity" id="nationlity" cssClass="inputempty"/></td>
				  		<td><form:label path="patientBaseInfo.nation">民族：</form:label></td>
				  		<td><form:input path="patientBaseInfo.nation" id="nation" cssClass="inputempty"/></td>
			    	</tr>			    		
		        	<tr>				  		
				  		<td><form:label path="patientBaseInfo.workUnit">工作单位：</form:label></td>
				  		<td colspan="2"><form:input path="patientBaseInfo.workUnit" id="workUnit" cssClass="inputempty" size="35"/></td>
				  		
				  		<td><form:label path="patientBaseInfo.address">通讯地址：</form:label></td>
				  		<td colspan="2"><form:input path="patientBaseInfo.address" id="address" cssClass="inputempty" size="35"/></td>
			    	</tr>	
			    	<tr>
						<td><form:label path="patientBaseInfo.profession">职业：</form:label></td>
				  		<td><form:input path="patientBaseInfo.profession" id="profession" cssClass="inputempty"/></td>
				  		<td><form:label path="patientBaseInfo.maritalStatus">婚姻状况：</form:label></td>
				  		<td><form:input path="patientBaseInfo.maritalStatus" id="maritalStatus" cssClass="inputempty"/></td>
				  		<td><form:label path="patientBaseInfo.medicalType">医保类别：</form:label></td>
				  		<td><form:input path="patientBaseInfo.medicalType" id="medicalType" cssClass="inputempty"/></td>
			    	</tr>
			    	<tr>
				  		<td><form:label path="patientBaseInfo.medicalOrganize">医保机构：</form:label></td>
						<td><form:input path="patientBaseInfo.medicalOrganize" id="medicalOrganize" cssClass="inputempty"/></td>
						<td><form:label path="patientBaseInfo.medicalAccount">医保账号：</form:label></td>
				  		<td><form:input path="patientBaseInfo.medicalAccount" id="medicalAccount" cssClass="inputempty"/></td>
				  		<td><form:label path="patientBaseInfo.patientType">病人类别：</form:label></td>
				  		<td><form:input path="patientBaseInfo.patientType" id="patientType" cssClass="inputempty"/></td>
			    	</tr>	
			    	<tr>				  		
				  		<td><form:label path="patientBaseInfo.contactTelephone">联系人电话：</form:label></td>
				  		<td><form:input path="patientBaseInfo.contactTelephone" id="contactTelephone" cssClass="inputempty"/></td>
				  		<td><form:label path="patientBaseInfo.contactRelation">与联系人关系：</form:label></td>
						<td><form:input path="patientBaseInfo.contactRelation" id="contactRelation" cssClass="inputempty"/></td>
						<td><form:label path="patientBaseInfo.contactAddress">联系人地址：</form:label></td>
				  		<td><form:input path="patientBaseInfo.contactAddress" id="contactAddress" cssClass="inputempty"/></td>
			    	</tr>	
			    	<tr>				  		
				  		<td><form:label path="patientBaseInfo.allergyHistory">过敏史：</form:label></td>
				  		<td colspan="5"><form:input path="patientBaseInfo.allergyHistory" id="allergyHistory" cssClass="inputempty" size="97"/></td>
			    	</tr>	
			    	<tr>
				  		<td><form:label path="patientBaseInfo.familyHistory">家族史：</form:label></td>
						<td colspan="5"><form:input path="patientBaseInfo.familyHistory" id="familyHistory" cssClass="inputempty" size="97"/></td>
			    	</tr>	    							    				    				    				    				    			    				    			    							    				    				    				    				    			    	
			  	</table>
		  	</form:form>
    	</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交(F10)</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置(F6)</a>
	  		<a class="easyui-linkbutton" id="readPatientByCertificate" data-options="iconCls:'icon-undo'" href="javascript:void(0);">证件读取(F8)</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭(F9)</a>
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
	                "url": "${ctx}/empi/card/manage/practicecard/validate",
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
		
		$(document).keydown(function(e){
			if(e.ctrlKey && e.which == 121) {
				$('#editForm').submit();
			}
			if(e.ctrlKey && e.which == 117) {
				$('#editForm').form('reset');
			}
			if(e.ctrlKey && e.which == 119) {
				readPatientByCertificate();
			}
			if(e.ctrlKey && e.which == 120) {
				parent.$('#edit-window').window('close');
			}
		});	
		
		$('#readPatientByCertificate').bind('click', function(){
			readPatientByCertificate();
		});
		
	});
	
	function readPatientByCertificate(){
	 	 $.ajax({url:"${ctx}/empi/card/manage/patientbaseinfo/readpatient",
			 async:false,
			 type:'get',
			 dataType:"json",
			 data:{certificateNo:$('#certificateNo').val(),certificateType:$('#certificateType').val()},
			 success: function(data){
				 $("#name").val(data.name);
				 $('#birthday_show').val(data.birthday);
				 $("#sourcePlace").val(data.sourcePlace);
				 $("#telephone").val(data.telephone);
				 $("#workUnit").val(data.workUnit);
				 $("#address").val(data.address);
				 $("#profession").val(data.profession);
				 $("#maritalStatus").val(data.maritalStatus);
				 $("#medicalType").val(data.medicalType);
				 $("#medicalOrganize").val(data.medicalOrganize);
				 $("#medicalAccount").val(data.medicalAccount);
				 $("#patientType").val(data.patientType);
				 $("#contactTelephone").val(data.contactTelephone);
				 $("#contactRelation").val(data.contactRelation);
				 $("#contactAddress").val(data.contactAddress);
				 $("#allergyHistory").val(data.allergyHistory);
				 $("#familyHistory").val(data.familyHistory);
				 $("#sex").val(data.sex);
				 $("#nation").val(data.nation);
				 $("#nationlity").val(data.nationlity);
				 $("#birthday").datebox('setValue',data.birthday);
			 },
			 error: function(data){
				 $.messager.alert('提示','未找到匹配的数据','info');
			 }
		 });		
	}
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>