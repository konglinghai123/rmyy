<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 公告栏"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/system/notice/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="10%"><form:label path="title">标题：</form:label></td>
						<td id="tdTitle" colspan="3">
							<form:input path="title" cssClass="inputtext validate[required,ajax[ajaxTitleCall]]" cssStyle="width:300px;background:url(${ctx}/static/image/notice/rule.gif) repeat-x left bottom;" maxlength="25"/>
							<form:hidden path="titleStyle"/>
						</td>
					</tr>
					<tr>
						<td width="10%"><form:label path="head">是否标题：</form:label></td>
						<td width="40%"><form:checkbox path="head"/></td>
						<td width="10%"><form:label path="release">是否发布：</form:label>
						<td width="40%"><form:checkbox path="release"/></td>
					</tr>
					<tr>
						<td><form:label path="externalLinks">外部链接地址：</form:label>
						<td colspan="3"><form:input path="externalLinks" size="50"/></td>
					</tr>
					<tr>
						<td><form:label path="content">内容：</form:label></td>
						<td colspan="3"><form:textarea path="content" style="width:100%;height:200px" placeholder="内容"/></td>
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
<script type="text/javascript" src="${ctx}/static/js/notice-toolbar.js"></script>
<script type="text/javascript">
	$(function(){
		  <c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
				$.validationEngineLanguage.allRules.ajaxTitleCall= {
	                "url": "${ctx}/system/notice/validate",
	                extraDataDynamic : ['#id'],
	                "alertTextLoad": "* 正在验证，请稍等。。。"
	            };
				
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomLeft',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	    
	    var st = new ArticleToolbar('title', 'tdTitle', 'FontColor,Bold,Italic,UnderLine,FontFamily,FontSize');
		st.show();
		if ($('#titleStyle').val != ''){
			var styleValue = $('#titleStyle').val();
			var titleStyle = $('#title').attr('style') + ';' + styleValue;
			$('#title').attr('style', titleStyle);
			$('#titleStyle').attr('style', styleValue);
			toolbarStyle('title', styleValue);
		}
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
	
	function toolbarStyle(styleID, styleValue){
		var styleArr = styleValue.split(";");
		for (var i = 0 ; i < styleArr.length ; i++){
			var styleKey = styleArr[i].split(":")[0];
			var styleValue = styleArr[i].split(":")[1];
			if ($.trim(styleKey) == "font-weight"){//粗体
				if ($.trim(styleValue) == "bold"){
					$("#" + styleID + "_Bold").val("bold");
					$("#" + styleID + "_Bold_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
					$("#" + styleID + "_Bold_Div").get()[0].isClicked = true;
				}else{
					$("#" + styleID + "_Bold").val("normal");
					$("#" + styleID + "_Bold_Div").attr("style","border:none;background:none");
					$("#" + styleID + "_Bold_Div").get()[0].isClicked = false;
				}
			}else if ($.trim(styleKey) == "font-style"){//斜体
				if ($.trim(styleValue) == "italic"){
					$("#" + styleID + "_Italic").val("italic");
					$("#" + styleID + "_Italic_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
					$("#" + styleID + "_Italic_Div").get()[0].isClicked = true;
				}else{
					$("#" + styleID + "_Italic").val("normal");
					$("#" + styleID + "_Italic_Div").attr("style","border:none;background:none");
					$("#" + styleID + "_Italic_Div").get()[0].isClicked = false;
				}
			}else if ($.trim(styleKey) == "text-decoration"){//下划线
				if ($.trim(styleValue) == "underline"){
					$("#" + styleID + "_UnderLine").val("underline");
					$("#" + styleID + "_UnderLine_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
					$("#" + styleID + "_UnderLine_Div").get()[0].isClicked = true;
				}else{
					$("#" + styleID + "_UnderLine").val("none");
					$("#" + styleID + "_UnderLine_Div").attr("style","border:none;background:none");
					$("#" + styleID + "_UnderLine_Div").get()[0].isClicked = false;
				}
			}else if ($.trim(styleKey) == "font-family"){//字体
				$("#" + styleID + "_FontFamily").val($.trim(styleValue));
				$("#" + styleID + "_FontFamily_Select").val($.trim(styleValue));
			}else if ($.trim(styleKey) == "font-size"){//字号
				$("#" + styleID + "_FontSize").val($.trim(styleValue));
				$("#" + styleID + "_FontSize_Select").val($.trim(styleValue));
			}
		}
	}
</script>
	