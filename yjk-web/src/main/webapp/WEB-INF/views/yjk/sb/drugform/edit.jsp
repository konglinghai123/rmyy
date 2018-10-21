<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="申报-新药申报单填写"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/sb/drugform/save1" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
			        	<c:choose>
				    		<c:when test="${empty(m.common.id)}">
								<td width="20%"><form:label path="common">通用名：</form:label></td>
								<td width="30%"><form:input path="common"  cssClass="validate[required]" class="easyui-combox" data-options="valueField:'id',textField:'commonName',panelHeight:140"/></td>
				    		</c:when>
				    		<c:otherwise>
				    			<form:hidden path="common.id"/>
				    			<td width="20%"><form:label path="common.commonName">通用名：</form:label></td>
								<td width="30%"><form:input path="common.commonName" readonly="true" cssStyle="background:grey"/></td>
				    		</c:otherwise>
				    	</c:choose>
						<td width="20%"><form:label path="extractCommonName">提取通用名：</form:label></td>
						<td width="30%"><form:input path="extractCommonName"/></td>
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
	$('#common').combobox({
		method: 'GET',
        onChange: function (newValue, oldValue) {
        	if(newValue==$("#common").combobox("getText")){
        		$('#common').combobox('reload','${ctx}/yjk/zd/commonname/findbyspell?spell='+newValue);
    		}
        	
         }
	});
	
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
	