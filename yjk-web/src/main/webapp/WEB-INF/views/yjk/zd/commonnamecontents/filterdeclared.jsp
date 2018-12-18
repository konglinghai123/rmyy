<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="过滤 - 大总目录是否允许申报"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form method="post" action="${ctx}/yjk/zd/commonnamecontents/savefilterdeclared" class="form-horizontal">
			  	<table class="formtable">
					<tr>
						<td width="40%">选择允许申报的项目：</td>
						<td width="60%">
							<form:select  name="projectDeclareds" path="projectNameList" cssClass="easyui-combobox" data-options="panelHeight:'auto',editable:false,multiple:true,width:400">
								<form:options items="${projectNameList}"/>							
							</form:select>
						</td>
					</tr>				
				</table>
			</form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a id="tb-import" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#importForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		 $("#tb-import").bind('click', function(){
				$.messager.confirm('提示', '确定要按所选项目过滤允许申报的大目录', function(r) {
					if (r) {
						document.forms[0].submit();
						$.ewcms.addLoading();
					}
				});			 
		});
	});
</script>
	