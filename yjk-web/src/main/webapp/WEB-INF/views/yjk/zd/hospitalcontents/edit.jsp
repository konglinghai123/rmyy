<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-医院在用药品总目录"/>
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
						<td width="20%"><form:label path="genericDrugName">药品通用名：</form:label></td>
						<td width="30%"><form:input path="genericDrugName"  cssClass="validate[required]"/></td>
						<td width="20%"><form:label path="drugCode">药品代码：</form:label></td>
						<td width="30%"><form:input path="drugCode"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="acidGroup">酸根：</form:label></td>
						<td width="30%"><form:input path="acidGroup"/></td>
						<td width="20%"><form:label path="chemicalName">化学名：</form:label></td>
						<td width="30%"><form:input path="chemicalName"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="pill">剂型：</form:label></td>
						<td width="30%"><form:input path="pill"/></td>
						<td width="20%"><form:label path="pill1">剂型：</form:label></td>
						<td width="30%"><form:input path="pill1"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="productName">商品名：</form:label></td>
						<td width="30%"><form:input path="productName"/></td>
						<td width="20%"><form:label path="specNumber">规格*数量：</form:label></td>
						<td width="30%"><form:input path="specNumber"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="manufacturer">生产企业：</form:label></td>
						<td width="30%"><form:input path="manufacturer"/></td>
						<td width="20%"><form:label path="importEnterprise">进口企业：</form:label></td>
						<td width="30%"><form:input path="importEnterprise"/></td>
					</tr>					
		        	<tr>
						<td width="20%"><form:label path="bidPrice">中标价：</form:label></td>
						<td width="30%"><form:input path="bidPrice"/></td>
						<td width="20%"><form:label path="medical">医保：</form:label></td>
						<td width="30%"><form:input path="medical"/></td>
					</tr>
		        	<tr>
						<td width="20%"><form:label path="limitRange">限制范围：</form:label></td>
						<td width="30%"><form:input path="limitRange"/></td>
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
						<td width="20%"><form:label path="discom">配送公司：</form:label></td>
						<td width="30%"><form:input path="discom"/></td>
						<td width="20%"><form:label path="remark">备注：</form:label></td>
						<td width="30%"><form:input path="remark"/></td>
					</tr>

		        	<tr>
						<td width="20%"><form:label path="oldRemark">原备注：</form:label></td>
						<td width="30%"><form:input path="oldRemark"/></td>
						<td width="20%"></td>
						<td width="30%"></td>
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
	