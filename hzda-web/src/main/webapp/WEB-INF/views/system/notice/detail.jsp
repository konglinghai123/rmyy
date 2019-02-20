<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="内容明细"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">	
			<table class="formtable" style="width:100%">
				<tr>
					<td width="20%">标题：</td>
					<td width="80%">${title}</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
				    	${detail}
					</td>
			  	</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>