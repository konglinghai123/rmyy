<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="导入 - 通用名"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="importForm" method="post" action="${ctx}/yjk/zd/commonname/saveimport" enctype="multipart/form-data" class="form-horizontal">
			  	<table class="formtable">
		        	<tr>
						<td width="20%">通用名文件：</td>
						<td width="80%"><input id="excelFile" name="excelFile" class="easyui-filebox" data-options="buttonText:'选择文件...',prompt:'选择文件...',accept:'application/vnd.ms-excel'"/></td>
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
			document.forms[0].submit();
			$.ewcms.addLoading();
		});
	});
</script>
	