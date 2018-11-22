<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="过滤 - 大总目录是否允许申报"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="importForm" method="post" action="${ctx}/yjk/zd/commonnamecontents/savefilterdeclared" class="form-horizontal">
			  	<table class="formtable">
					<tr>
						<td width="40%">选择允许申报的项目：</td>
						<td width="60%">
							<select  name="projectDeclareds" class="easyui-combobox" data-options="editable:false,multiple:true,width:400">
								<option value="省直联合体项目">省直联合体项目</option>
								<option value="增补待谈价">增补待谈价</option>
								<option value="双信封中标项目">双信封中标项目</option>
								<option value="国家谈判项目">国家谈判项目</option>
								<option value="定点生产项目">定点生产项目</option>
								<option value="省级谈判项目">省级谈判项目</option>		
								<option value="市场撮合项目">市场撮合项目</option>								
							</select>
						
						</td>
					</tr>				
				</table>
			</form:form>
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
						loadingEnable();
					}
				});			 
		});
	});
	
	function loadingEnable(){
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\"></div>").html("<font size='9'>正在处理，请稍候。。。</font>").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	}
</script>
	