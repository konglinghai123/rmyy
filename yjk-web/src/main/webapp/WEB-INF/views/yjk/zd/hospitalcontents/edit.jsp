<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-院在用药品总目录"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/zd/hospitalcontents/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<input type="hidden" id="isSetValue" value="true">
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td width="20%"><form:label path="common">通用名(拼音)：</form:label></td>
						<td width="30%"><from:input id="cc_common" name="common" cssClass="validate[required]"/></td>
						<td width="20%"><form:label path="commonName">院用目录通用名：</form:label></td>
						<td width="30%"><form:input path="commonName" cssClass="validate[required]"/></td>			  	
					</tr>
		        	<tr>
						<td width="20%"><form:label path="specifications">规格：</form:label></td>
						<td width="30%"><form:input path="specifications"/></td>
						<td width="20%"><form:label path="amount">包装数量：</form:label></td>
						<td width="30%"><form:input path="amount"/></td>
					</tr>					
		        	<tr>
						<td width="20%"><form:label path="pill">剂型：</form:label></td>
						<td width="30%"><form:input path="pill"/></td>
						<td width="20%"><form:label path="productName">商品名：</form:label></td>
						<td width="30%"><form:input path="productName" /></td>						
					</tr>
					<tr>
						<td width="20%"><form:label path="contentCategory">目录分类：</form:label></td>
						<td width="30%"><form:input path="contentCategory"/></td>
						<td width="20%"><form:label path="bidPrice">中标价：</form:label></td>
						<td width="30%"><form:input path="bidPrice" cssClass="validate[custom[number]]" /></td>						
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
						<td width="20%"><form:label path="importEnterprise">进口企业：</form:label></td>
						<td width="30%"><form:textarea path="importEnterprise"	style="height:50px" /></td>
					</tr>	
					<tr>
						<td width="20%"><form:label path="medical">医保：</form:label></td>
						<td width="30%"><form:input path="medical" /></td>						
						<td width="20%"><form:label path="limitRange">限制范围：</form:label></td>
						<td width="30%"><form:input path="limitRange" /></td>
					</tr>											
					<tr>
						<td width="20%"><form:label path="remark1">备注1：</form:label></td>
						<td width="30%"><form:input path="remark1" /></td>
						<td width="20%"><form:label path="remark2">备注2：</form:label></td>
						<td width="30%"><form:input path="remark2" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="remark3">备注3：</form:label></td>
						<td width="30%"><form:input path="remark3" /></td>
						<td width="20%"></td>
						<td width="30%"></td>						
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
		var transTool = $('#cc_common').combogrid({
			panelWidth: 500,
            idField: 'id',
            textField: 'commonName',
            fitColumns: true,
            singleSelect:true,
            url:'${ctx}/yjk/zd/commonname/findbyspell',
            queryParams:{spell:'${m.common.spell}'},
            columns: [[
                {field:'id',title:'序号',width:80},
                {field:'commonName',title:'通用名',width:120},
                {field:'administration',title:'给药途径',width:80,
                	formatter:function(val,row){
						return (row.administration==null) ? '' : row.administration.name;
					}
				},
                {field:'number',title:'编号',width:80},
                {field:'drugCategoryInfo',title:'药品种类',width:200},
                {field:'spell',title:'全拼',width:100},
                {field:'spellSimplify',title:'简拼',width:100}
            ]],
            onLoadSuccess:function(){
            	if ($('#id').val() != '' && $('#isSetValue').val() == 'true'){
            		$('#cc_common').combogrid('setValue',${m.common.id});
            	}
            }
		});
		
		transTool.combogrid('textbox').keyup(function(event) {
			if (event.keyCode == 38){
			} else if (event.keyCode == 40){
			} else {
				newValue =  $('#cc_common').combogrid("getText");
				if(newValue != '' && newValue.length>1){
					$('#isSetValue').val('false');
					$('#cc_common').combogrid('grid').datagrid('reload', {
						spell : newValue
					});
				}
	 		}
       	});
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
	
	function pageSubmit() {
		if ($('#cc_common').val() == '') {
			 $.messager.alert('提示','通用名不能为空','info');
			return;
		}
		$('#editForm').submit();
	}
</script>
	