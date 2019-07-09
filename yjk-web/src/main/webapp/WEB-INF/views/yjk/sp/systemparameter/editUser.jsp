<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf"%>

<ewcms:head title="申报-用户选择" />
<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border: 0;">
	<ewcms:showMessage />
	<div data-options="region:'center',border:false">
		<form:form id="editForm" method="post" action="${ctx}/yjk/sp/systemparameter/${systemParameterId}/saveUser" class="form-horizontal">
			<table class="formtable">
				<tr>
					<td width="10%">用户名：</td>
					<td><input id="cc_userIds" name="userIds" class="validate[required]"/></td>
				</tr>
			</table>
		</form:form>
	</div>
	<div data-options="region:'south'" style="text-align: center; height: 30px; border: 0">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit()">提交</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
	</div>
</div>
<ewcms:footer />
<script type="text/javascript">
	$(function() {
		var transTool = $('#cc_userIds').combogrid({
			panelWidth: 600,
            idField: 'id',
            textField: 'realname',
            fitColumns: true,
            pagination:true,
            url:'${ctx}/yjk/sp/systemparameter/canUseUser',
            //queryParams:{name:''},
            columns: [[
                {field:'id',title:'编号',hidden:true},
                {field:'username',title:'登录名',width:80,sortable:true},
				{field:'realname',title:'实名',width:80,sortable:true},
				{field:'sex',title:'性别',width:60,sortable:true,
						formatter:function(val,row){
							return row.sexDescription;
						}
				},
				{field:'mobilePhoneNumber',title:'手机号',width:100,sortable:true}
            ]]
		});
		
		transTool.combogrid('textbox').keyup(function(event) {
			if (event.keyCode == 38){
			} else if (event.keyCode == 40){
			} else {
				$('#cc_userIds').combogrid('grid').datagrid('reload', {
					name : $('#cc_userIds').combogrid('getText')
				});
	 		}
       	});
		
		var validationEngine = $("#editForm").validationEngine({
			promptPosition : 'bottomRight',
			showOneMessage : true
		});
	});
</script>
