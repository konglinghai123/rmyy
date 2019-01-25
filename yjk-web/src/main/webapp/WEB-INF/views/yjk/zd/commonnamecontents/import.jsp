<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="导入 - 大总目录"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="importForm" method="post" action="${ctx}/yjk/zd/commonnamecontents/saveimport" enctype="multipart/form-data" class="form-horizontal">
			  	<table class="formtable">
		        	<tr>
						<td width="20%">通用名文件：</td>
						<td width="80%"><input id="excelFile" name="excelFile" class="easyui-filebox" data-options="buttonText:'选择文件...',prompt:'选择文件...',accept:'application/vnd.ms-excel'"/></td>
					</tr>
		        	<tr>
						<td width="20%">导入方式：</td>
						<td width="80%">
						<select id="isDisabledOriginalData" name="isDisabledOriginalData" class="easyui-combobox" data-options="panelHeight:'auto',panelWidth:'auto',editable:false,width:150">
							<option value="">--选择导入方式--</option>
							<option value="false">增量导入</option>
							<option value="true">初始或作废以前数据再导入</option>
						</select>
					</tr>					
				</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a id="tb-import" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">导入</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#importForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		 $("#tb-import").bind('click', function(){
			 if($('#isDisabledOriginalData').val()==''){
				 $.messager.alert('提示','请选择导入方式','info');
				 return;
			 }
			 if($('#isDisabledOriginalData').val()=='true'){
				$.messager.confirm('提示', '确定要作废以前数据再导入吗？ <br/><font color="red">该操作会先删除数据库大总目录所有数据再导入新数据，请慎重操作！！！</font>', function(r) {
					if (r) {
						document.forms[0].submit();
						$.ewcms.addLoading();
					}
				});			 
		 	}else{
				$.messager.confirm('提示', '确定要增量导入新数据吗？<br/><font color="red">按照匹配编号 ,省招标药品ID查重，重复记录自动修改，不重复的记录新增，省招标药品ID为空的不导入</font>', function(r) {
					if (r) {
						document.forms[0].submit();
						$.ewcms.addLoading();
					}
				});		 		
		 	}
		});
	});
</script>
	