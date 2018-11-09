<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="导入 - 院用目录"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="importForm" method="post" action="${ctx}/yjk/zd/hospitalcontents/saveimport" enctype="multipart/form-data" class="form-horizontal">
			  	<table class="formtable">
		        	<tr>
						<td width="20%">通用名文件：</td>
						<td width="80%"><input type="file" id="excelFile" name="excelFile" size="50"/></td>
					</tr>
		        	<tr>
						<td width="20%">导入方式：</td>
						<td width="80%">
						<select id="isDisabledOriginalData" name="isDisabledOriginalData">
							<option value="false">增量导入</option>
							<option value="true">作废以前数据再导入</option>
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
			 if($('#isDisabledOriginalData').val()=='true'){
				$.messager.confirm('提示', '确定要作废以前数据再导入吗？ <br/><font color="red">该操作会删除数据库院用目录所有数据，请慎重操作！！！</font>', function(r) {
					if (r) {
						document.forms[0].submit();
						loadingEnable();
					}
				});			 
		 	}else{
				$.messager.confirm('提示', '确定要增量导入新数据吗？</font>', function(r) {
					if (r) {
						document.forms[0].submit();
						loadingEnable();
					}
				});		 		
		 	}
		});
	});
	function loadingEnable(){
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\"></div>").html("<font size='9'>正在处理，请稍候。。。</font>").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	}
</script>
	