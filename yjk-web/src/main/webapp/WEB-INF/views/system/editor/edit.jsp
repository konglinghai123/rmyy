<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 在线编辑"/>
	<%@ include file="/WEB-INF/views/jspf/import-codemirror-css.jspf" %>
	<div id="edit-form" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',fit:true,border:false">	
			<form:form id="editForm" action="${ctx}/system/editor/save" method="post" class="form-horizontal">
	  			<h1 class="title">当前位置：<span id="currentPath" style="color:red;"></span></h1>
	  			<fieldset>
					<table class="formtable" >
		  				<tr>
							<td style="padding:0px;">
								<input type="hidden" id="path" name="path" value="${path}">
								<textarea id="content" name="content" style="width:99%;height:280px;padding-right:10px;">${content}</textarea>
							</td>
		  				</tr>
		  			</table>
		  		</fieldset>
			</form:form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;height:30px">
	  		<a id="tb-submit" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<%@ include file="/WEB-INF/views/jspf/import-codemirror-js.jspf" %>
<script type="text/javascript">
	$(function() {
		<c:if test="${close}">
			$.ewcms.refresh({grid : 'treegrid', operate : 'update', data : '${lastM}'});
			parent.$('#edit-window').window('close');
		</c:if>
		
		var modeName = 'xml';
		var fileName = decodeURIComponent('${path}');
		if(fileName.lastIndexOf(".") > 0){
			var fileExt = fileName.substring(fileName.lastIndexOf(".")+1) ;
			if (fileExt == 'htm' || fileExt == 'html'){
				modeName = 'text/html';
			} else if (fileExt == 'css'){
				modeName = 'css';
			} else if (fileExt == 'js'){
				modeName = 'javascript';
			}
		}
		
		$('#currentPath').html(decodeURIComponent('${path}'));
		
		var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
			mode: modeName,
			lineNumbers: true,
			lineWrapping: true,
	        matchBrackets: true,
	        indentUnit: 4,
	        extraKeys: {"Ctrl-Q": function(cm){ cm.foldCode(cm.getCursor()); }},
	        foldGutter: true,
	        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
		});
	});
	
</script>