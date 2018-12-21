<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf"%>

<ewcms:head title="字典-大通用名总目录" />
<div id="edit-from" class="easyui-layout" data-options="fit:true"
	style="border: 0;">
	<ewcms:showMessage />
	<div data-options="region:'center',border:false">
		<form:form id="editForm" method="post"
			action="${ctx}/yjk/zd/commonnamecontents/save" commandName="m"
			class="form-horizontal">
			<ewcms:showGlobalError commandName="m" />
			<form:hidden path="id" />
			<input type="hidden" id="isSetValue" value="true">
			<c:forEach var="selection" items="${selections}">
				<input type="hidden" name="selections" value="${selection}" />
			</c:forEach>
			<table class="formtable">
				<tr>
					<td width="20%"><form:label path="common">通用名(拼音)<font color="red">*</font>：</form:label></td>
					<td width="30%"><from:input id="cc_common" name="common" cssClass="validate[required]"/>
					<td width="20%"><form:label path="commonName">大目录通用名<font color="red">*</font>：</form:label></td>
					<td width="30%"><form:input path="commonName" cssClass="validate[required]" /></td>
				</tr>
				<tr>
					<td width="20%"><form:label path="bidDrugId">省招标药品ID<font color="red">*</font>：</form:label></td>
					<td width="30%"><form:input path="bidDrugId" cssClass="validate[required]" /></td>				
					<td><form:label path="administration">给药途径<font color="red">*</font>：</form:label></td>
					<td><form:input path="administration" class="easyui-combobox" data-options="
						width:150,
						panelWidth:150,
						panelHeight:130,
						url:'${ctx}/yjk/zd/administration/canUse',
						method:'get',
						valueField:'id',
						textField:'name',
						editable:false,
						onLoadSuccess:function(){
							$(this).combobox('setValue', ${m.administration.id});
						}
						"/>
					</td>	
				</tr>
				<tr>			
					<td width="20%"><form:label path="projectName">项目名称：</form:label></td>
					<td width="30%">
						<form:select path="projectName" cssClass="easyui-combobox" data-options="editable:true">
							<form:option value="" label="---请选择---"/>
					  		<form:options items="${projectNameList}"/>					
						</form:select>
					</td>			
					<td width="20%"><form:label path="productName">商品名：</form:label></td>
					<td width="30%"><form:input path="productName" /></td>
				</tr>
				<tr>
					<td width="20%"><form:label path="countryId">国家ID：</form:label></td>
					<td width="30%"><form:input path="countryId" /></td>					
					<td width="20%"><form:label path="consistencyEvaluation">一致性评价：</form:label></td>
					<td width="30%"><form:input path="consistencyEvaluation" /></td>
				</tr>				
				<tr>
					<td width="20%"><form:label path="specifications">规格：</form:label></td>
					<td width="30%"><form:input path="specifications" /></td>
					<td width="20%"><form:label path="amount">包装数量：</form:label></td>
					<td width="30%"><form:input path="amount" /></td>
				</tr>
				<tr>
					<td width="20%"><form:label path="pill">剂型：</form:label></td>
					<td width="30%"><form:input path="pill" /></td>
					<td width="20%"><form:label path="purchasePrice">采购价：</form:label></td>
					<td width="30%"><form:input path="purchasePrice"	cssClass="validate[custom[number]]" /></td>
				</tr>
				<tr>

					<td width="20%"><form:label path="batch">批次：</form:label></td>
					<td width="30%"><form:input path="batch" /></td>					
					<td width="20%"><form:label path="packageUnit">包装单位：</form:label></td>
					<td width="30%"><form:input path="packageUnit" /></td>
				</tr>

				<tr>
					<td width="20%"><form:label path="importEnterprise">进口企业：</form:label></td>
					<td width="30%"><form:textarea path="importEnterprise"	style="height:50px" /></td>
					<td width="20%"><form:label path="manufacturer">生产企业<font color="red">*</font>：</form:label></td>
					<td width="30%"><form:textarea path="manufacturer"	style="height:50px" cssClass="validate[required]"/></td>
				</tr>
				<tr>
					<td width="20%"><form:label path="declared">是否允许申报：</form:label></td>
					<td width="30%"><form:checkbox path="declared" /></td>
					<td width="20%"><form:label path="medicalDirNo">医保目录编号：</form:label></td>
					<td width="30%"><form:input path="medicalDirNo" /></td>
				</tr>
				<tr>
					<td width="20%"><form:label path="medicalCategory">医保类别：</form:label></td>
					<td width="30%"><form:input path="medicalCategory" /></td>
					<td width="20%"><form:label path="medicalRemark">医保备注：</form:label></td>
					<td width="30%"><form:input path="medicalRemark" /></td>
				</tr>	
				
				<tr>
					<td width="20%"><form:label path="heds">基药：</form:label></td>
					<td width="30%"><form:input path="heds" /></td>
					<td width="20%"><form:label path="gynaecology">妇科：</form:label></td>
					<td width="30%"><form:input path="gynaecology" /></td>
				</tr>	
				<tr>
					<td width="20%"><form:label path="pediatric">儿科：</form:label></td>
					<td width="30%"><form:input path="pediatric" /></td>
					<td width="20%"><form:label path="firstAid">急救：</form:label></td>
					<td width="30%"><form:input path="firstAid" /></td>
				</tr>	
				<tr>
					<td width="20%"><form:label path="basicInfusion">基础输液：</form:label></td>
					<td width="30%"><form:input path="basicInfusion" /></td>
					<td width="20%"><form:label path="cheapShortage">廉价短缺：</form:label></td>
					<td width="30%"><form:input path="cheapShortage" /></td>
				</tr>						
				<tr>
					<td width="20%"><form:label path="negotiationVariety">国家谈判品种：</form:label></td>
					<td width="30%"><form:input path="negotiationVariety" /></td>
					<td width="20%"><form:label path="licenseNumber">批准文号：</form:label></td>
					<td width="30%"><form:input path="licenseNumber" /></td>
				</tr>						
				<tr>
					<td width="20%"><form:label path="bidPill">招标剂型：</form:label></td>
					<td width="30%"><form:input path="bidPill" /></td>
					<td width="20%"><form:label path="bidSpecifications">招标规格：</form:label></td>
					<td width="30%"><form:input path="bidSpecifications" /></td>
				</tr>	
				<tr>
					<td width="20%"><form:label path="bidUnit">招标单位：</form:label></td>
					<td width="30%"><form:input path="bidUnit" /></td>
					<td width="20%"><form:label path="packageMaterials">包材：</form:label></td>
					<td width="30%"><form:input path="packageMaterials" /></td>
				</tr>					
				<tr>

					<td width="20%"><form:label path="minimalUnit">最小制剂单位：</form:label></td>
					<td width="30%"><form:input path="minimalUnit" /></td>			
					<td width="20%"><form:label path="remark1">备注1</form:label></td>
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
	<div data-options="region:'south'"
		style="text-align: center; height: 30px; border: 0">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"
			href="javascript:void(0);" onclick="javascript:pageSubmit();">提交</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'"
			href="javascript:void(0);"
			onclick="javascript:$('#editForm').form('reset');">重置</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0);"
			onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
	</div>
</div>
<ewcms:footer />
<script type="text/javascript">
	$(function() {
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
                {field:'commonName',title:'通用名',width:200},
                {field:'matchNumber',title:'匹配编号',width:80},
                {field:'drugCategoryInfo',title:'药品种类',width:80},
                {field:'spell',title:'全拼',width:100},
                {field:'spellSimplify',title:'简拼',width:80}
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
					promptPosition : 'bottomRight',
					showOneMessage : true
				});
				<ewcms:showFieldError commandName="m"/>
			</c:otherwise>
		</c:choose>
	});
	$.ewcms.refresh({
		operate : '${operate}',
		data : '${lastM}'
	});

	function pageSubmit() {
		
		if ($('#cc_common').val() == '') {
			 $.messager.alert('提示','通用名不能为空','info');
			return;
		}
		if ($('#administration').val() == '') {
			$.messager.alert('提示','给药途径不能为空','info');
			return;
		}
		$('#editForm').submit();
	}
</script>
