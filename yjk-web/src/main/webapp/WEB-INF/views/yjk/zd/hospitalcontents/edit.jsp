<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-院在用药品总目录"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/zd/hospitalcontents/save" commandName="m"  class="form-horizontal">
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
								<td width="30%"><form:input path="common"  class="easyui-combobox" data-options="
								valueField:'id',
								textField:'commonName',
								panelHeight:140,
								width:150,
								required:true,
								method:'get',
						        onChange: function (newValue, oldValue) {						        	
						        	if(newValue==$('#common').combobox('getText')){
						        		$('#common').combobox('reload','${ctx}/yjk/zd/commonname/findbyspell?spell='+newValue);
						    		}
						    	}"/>
	         					</td>
				    		</c:when>
				    		<c:otherwise>
				    			<form:hidden path="common.id"/>
				    			<td width="20%"><form:label path="common.commonName">通用名：</form:label></td>
								<td width="30%"><form:input path="common.commonName" readonly="true" cssStyle="background:grey"/></td>
				    		</c:otherwise>
				    	</c:choose>
						<td width="20%"><form:label path="commonName">院用目录通用名：</form:label></td>
						<td width="30%"><form:input path="commonName" cssClass="validate[required]"/></td>			  	
					</tr>
		        	<tr>
						<td width="20%"><form:label path="specifications">规格：</form:label></td>
						<td width="30%"><form:input path="specifications"/></td>
						<td width="20%"><form:label path="amount">数量：</form:label></td>
						<td width="30%"><form:input path="amount"/></td>
					</tr>					
		        	<tr>
						<td width="20%"><form:label path="pill">剂型：</form:label></td>
						<td width="30%"><form:input path="pill"/></td>
						<td width="20%"><form:label path="contentCategory">目录分类：</form:label></td>
						<td width="30%"><form:input path="contentCategory"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="drugMajor">药品分类大类：</form:label></td>
						<td width="30%"><form:input path="drugMajor"/></td>
						<td width="20%"><form:label path="drugCategory">药品分类：</form:label></td>
						<td width="30%"><form:input path="drugCategory"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="remark">备注：</form:label></td>
						<td width="30%"><form:input path="remark"/></td>
						<td width="20%"><form:label path="oldRemark">原备注：</form:label></td>
						<td width="30%"><form:input path="oldRemark"/></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="manufacturer">生产企业：</form:label></td>
						<td width="30%"><form:textarea path="manufacturer" style="height:50px"/></td>
						<td width="20%"><form:label path="discom">配送公司：</form:label></td>
						<td width="30%"><form:textarea path="discom" style="height:50px"/></td>
					</tr>	
		        	<tr>
						<td width="20%"><form:label path="originalCategory">原类别：</form:label></td>
						<td width="30%"><form:input path="originalCategory"/></td>
						<td width="20%"><form:label path="medicalInfo">医保等信息：</form:label></td>
						<td width="30%"><form:input path="medicalInfo"/></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="qualityLevel">原质量层次：</form:label></td>
						<td width="30%"><form:input path="qualityLevel" /></td>
						<td width="20%"><form:label path="remark1">备注1：</form:label></td>
						<td width="30%"><form:input path="remark1" /></td>
					</tr>	
					<tr>
						<td width="20%"><form:label path="remark2">备注2：</form:label></td>
						<td width="30%"><form:input path="remark2" /></td>
						<td width="20%"><form:label path="remark3">备注3：</form:label></td>
						<td width="30%"><form:input path="remark3" /></td>
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
	