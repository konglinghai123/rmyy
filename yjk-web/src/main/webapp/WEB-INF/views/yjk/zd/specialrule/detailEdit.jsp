<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 通用名字典库"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/zd/specialrule/${specialRuleId}/save" class="form-horizontal">
			  	<table class="formtable">
		        	<tr>
						<td width="20%">通用名(拼音)：</td>
						<td width="80%"><input id="cc_common" name="commonNameId" class="validate[required]"/></td>
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
	            {field:'number',title:'编号',width:80},
	            {field:'drugCategoryInfo',title:'药品种类',width:200}
	        ]]
		});
		
		transTool.combogrid('textbox').keyup(function(event) {
			if (event.keyCode == 38){
			} else if (event.keyCode == 40){
			} else {
				$('#cc_common').combogrid('grid').datagrid('reload', {
					spell : $('#cc_common').combogrid("getText")
				});
	 		}
	   	});

		var validationEngine = $("#editForm").validationEngine({
	    	promptPosition:'bottomRight',
	    	showOneMessage: true
	    });

	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
	