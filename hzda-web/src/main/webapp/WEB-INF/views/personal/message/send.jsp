<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="发邮件"/>
	<style>
		.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
		.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
		.autocomplete-selected { background: #F0F0F0; }
		.autocomplete-suggestions strong { font-weight: normal; color: #3399FF; }
		.autocomplete-group { padding: 2px 5px; }
		.autocomplete-group strong { display: block; border-bottom: 1px solid #000; }
	</style>
	<c:if test="${op eq '回复消息'}">
		
	</c:if>
	<div id="edit-form" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
			<form:form id="editForm" method="post" modelAttribute="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
            	<form:hidden path="content.id"/>
            	<form:hidden path="parentId"/>
           		<form:hidden path="parentIds"/>
				<form:hidden path="receiverId"/>
				<table class="formtable">
					<c:if test="${op eq '发送新消息' or op eq '转发消息' or op eq '发送草稿'}">
						<tr>
							<td><form:label path="receiverId">收件人：</form:label></td>
							<td>
								<c:set var="receiver" value="${not empty receiver ? receiver : param.receiver}"/>
								<input id="receiverId_msg" name="receiver" value="${receiver}" placeholder="输入收件人用户名">
								<form:hidden path="receiverId"/>
							</td>
						</tr>
					</c:if>
					<tr>
						<td width="20%"><form:label path="title">标题：</form:label></td>
						<td width="80%"><form:input path="title" size="50" cssClass="validate[required,minSize[5],maxSize[200]]" placeholder="长度在5-200个字符之间"/></td>
	  				</tr>
	  				<tr>
  						<td><form:label path="content.content">内容：</form:label></td>
						<td><form:textarea id="content" path="content.content" data-promptPosition="topRight:-200" cssStyle="width: 680px;height: 300px;"/></td>
	  				</tr>
				</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a id="tb-submit" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">${op}</a>
	  		<a id="tb-draft" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">存草稿</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
	$(function(){
        var form = $('#editForm');
        var validationEngine = form.validationEngine({prettySelect:true, useSuffix : '_msg'});
        <ewcms:showFieldError commandName='m'/>
        
        var transTool = $('#receiverId_msg').combogrid({
			panelWidth: 500,
		    idField: 'username',
		    textField: 'realname',
		    fitColumns: true,
		    multiple:true,
		    url:'${ctx}/security/user/user/findbyrealname',
		    columns: [[
		    	{field:'id',hidden:true},
                {field:'username',hidden:true},
                {field:'realname',title:'姓名',width:130},
                {field:'sex',title:'性别',width:60,
                	formatter:function(val,row){
						return row.sexDescription;
					}		
                },
                {field:'mobilePhoneNumber',title:'手机号',width:120},
                {field:'organizationNames',title:'科室名称',width:150}
		    ]]
		});
			
		transTool.combogrid('textbox').keyup(function(event) {
			if (event.keyCode == 38){
			} else if (event.keyCode == 40){
			} else {
				$('#receiverId_msg').combogrid('grid').datagrid('reload', {
					realname : $('#receiverId_msg').combogrid("getText")
				});
		 	}
		});
        //var $username = $('#receiverId_msg');
        //if($username[0]){
        //	$.ewcms.autoComplete({
        //		input : $('#receiverId_msg'),
        //		source : '${ctx}/security/user/user/ajax/autocomplete',
        //		select : function(event, ui){
        //			$username.val(ui.item.label);
        //			return false;
        //		}
        //	});
        //}
        $('#receiverId_msg').autocomplete({
        	serviceUrl : '${ctx}/security/user/user/ajax/autocomplete',
        	onSelect: function (suggestion) {
                //alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
                $('#receiverId').val(suggestion.data);
            }
        });
        
        $(window).bind('beforeunload',function() {
            if($username.val() || $('#title').val() || $('content').val()) {
                return '确定离开当前编辑页面吗？';
            }
        });
        
        $('#tb-submit').bind('click', function(){
        	$(window).unbind('beforeunload');
        	form.validationEngine('detach');
        	var url = '';
        	<c:if test="${op eq '发送草稿'}">
        		url = '${ctx}/personal/message/draft/${m.id}/send';
        	</c:if>
        	<c:if test="${op eq '发送新消息'}">
        		url = '${ctx}/personal/message/send';
        	</c:if>
        	<c:if test="${op eq '回复消息'}">
        		url = '${ctx}/personal/message/${parent.id}/reply';
        	</c:if>
        	<c:if test="${op eq '转发消息'}">
        		url = '${ctx}/personal/message/${parent.id}/forward';
        	</c:if>
        	form.attr('action', url).submit();
        });
        
        $('#tb-draft').bind('click', function(){
        	//必须调用 尽管 form.submit绑定了释放，但是 form.validationEngine("detach"); 会off掉
            $(window).unbind('beforeunload');
            form.validationEngine('detach');
            form.attr('action', '${ctx}/personal/message/draft/save').submit();
        })
	});
</script>