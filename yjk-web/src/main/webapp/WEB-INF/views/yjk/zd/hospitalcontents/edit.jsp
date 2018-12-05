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
						<td width="20%"><form:label path="projectName">项目名称：</form:label></td>
						<td width="30%"><form:select path="projectName">
								<form:option value="省直联合体项目">省直联合体项目</form:option>
								<form:option value="省直增补">省直增补</form:option>
								<form:option value="省级谈判">省级谈判</form:option>
								<form:option value="省直联合体">省直联合体</form:option>
								<form:option value="直接挂网（无配送企业）">直接挂网（无配送企业）</form:option>
								<form:option value="双信封">双信封</form:option>
								<form:option value="国家谈判">国家谈判</form:option>
								<form:option value="直接挂网">直接挂网</form:option>
								<form:option value="联合体议价/36种国家谈判">联合体议价/36种国家谈判</form:option>
								<form:option value="毒性药品">毒性药品</form:option>
								<form:option value="联合体议价/双信封限价">联合体议价/双信封限价</form:option>
								<form:option value="联合体议价">联合体议价</form:option>
								<form:option value="麻醉药品">麻醉药品</form:option>
								<form:option value="撮合品种">撮合品种</form:option>
								<form:option value="备选目录">备选目录</form:option>
								<form:option value="定点生产">定点生产</form:option>
								<form:option value="一类精神">一类精神</form:option>
								<form:option value="二类精神">二类精神</form:option>
								<form:option value="未议价">未议价</form:option>					
							</form:select></td>		
						<td><form:label path="administration">给药途径：</form:label></td>
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
						<td width="20%"><form:label path="productName">商品名：</form:label></td>
						<td width="30%"><form:input path="productName" /></td>
						<td width="20%"><form:label path="bidPrice">中标价：</form:label></td>
						<td width="30%"><form:input path="bidPrice" cssClass="validate[custom[number]]" /></td>						
					</tr>					
		        	<tr>
						<td width="20%"><form:label path="specifications">规格：</form:label></td>
						<td width="30%"><form:input path="specifications"/></td>
						<td width="20%"><form:label path="amount">包装数量：</form:label></td>
						<td width="30%"><form:input path="amount"/></td>
					</tr>					
		        	<tr>
						<td width="20%"><form:label path="drugCode">药品代码：</form:label></td>
						<td width="30%"><form:input path="drugCode"/></td>		        	
						<td width="20%"><form:label path="pill">剂型：</form:label></td>
						<td width="30%"><form:input path="pill"/></td>
					</tr>					
		        	<tr>
						<td width="20%"><form:label path="manufacturer">生产企业：</form:label></td>
						<td width="30%"><form:textarea path="manufacturer" style="height:50px"/></td>
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
						<td width="20%"><form:label path="remark">备注：</form:label></td>
						<td width="30%"><form:input path="remark"/></td>					
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
		if ($('#administration').val() == '') {
			$.messager.alert('提示','给药途径不能为空','info');
			return;
		}
		$('#editForm').submit();
	}
</script>
	