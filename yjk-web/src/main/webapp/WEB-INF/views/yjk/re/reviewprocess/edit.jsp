<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 -评审专家设置"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/re/reviewprocess/${reviewMainId}/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="weight"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="30%"><form:label path="reviewBaseRule">评审基本规则：</form:label></td>
						<td width="70%">
							<form:select path="reviewBaseRule" cssClass="validate[required, ajax[ajaxCall]]" cssStyle="width:200px;">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${baseRuleList}" itemLabel="ruleCnName" itemValue="id"/>
							</form:select>
						</td>
					</tr>
		        	<tr id="generalName1">
						<td><form:label path="generalNameChinese">拟新增通用名中成药：</form:label></td>
						<td><form:input path="generalNameChinese" cssClass="validate[required, integer]"/></td>
					</tr>
					<tr id="generalName2">
						<td><form:label path="generalNameWestern">拟新增通用名西药：</form:label></td>
						<td><form:input path="generalNameWestern" cssClass="validate[required, integer]"/></td>
					</tr>
					<tr id="formula1">
						<td><form:label path="formulaChinese">拟新增剂型/规格中成药：</form:label></td>
						<td><form:input path="formulaChinese" cssClass="validate[required, integer]"/></td>
					</tr>
					<tr id="formula2">
						<td><form:label path="formulaWestern">拟新增剂型/规格西药：</form:label></td>
						<td><form:input path="formulaWestern" cssClass="validate[required, integer]"/></td>
					</tr>
					<c:if test="${not empty m.id}">
					<tr>
						<td><form:label path="displayColumns">所要显示字段：</form:label></td>
						<td><form:input path="displayColumns" cssClass="easyui-combobox" data-options="url:'${ctx}/yjk/re/zd/displaycolumn/canUse',valueField:'id',textField:'ruleCnName',editable:false,multiple:true,multiline:true,panelHeight:140,height:100" cssStyle="width:100%;"/></td>
					</tr>
					</c:if>
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
		typeSelect($("#reviewBaseRule").val());
	    $('#reviewBaseRule').bind('change', function(){
	    	typeSelect($("#reviewBaseRule").val());
	    });
		  <c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    		$.validationEngineLanguage.allRules.ajaxCall= {
	                url: '${ctx}/yjk/re/reviewprocess/${reviewMainId}/validate',
	                extraDataDynamic : ['#id'],
	                alertTextLoad: '* 正在验证，请稍等。。。'
	            };
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	    $('#displayColumns').combobox('setValues', ${m.displayColumnsIds});
	    
	    
	});
	
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
	
	function resetForm(){
		$('#editForm').form('reset');
		$('#displayColumns').combobox('setValues', ${m.displayColumnsIds});
	}
	
	function typeSelect(checkValue){
    	if (checkValue == 1 || checkValue == 3){
    		$('#generalName1,#generalName2').show();
    		$('#formula1,#formula2').hide();
    	} else if (checkValue == 2 || checkValue == 4){
    		$('#generalName1,#generalName2').hide();
    		$('#formula1,#formula2').show();
    	} else {
    		$('#generalName1,#generalName2').hide();
    		$('#formula1,#formula2').hide();
    	}
	}
</script>
	