<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 组织、职务"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/security/group/groupRelation/${group.id}/save" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  		<table class="formtable">
			  			<c:if test="${group.type eq 'user'}">
			  				<tr>
			  					<td>用户：</td>
			  					<td>
			  						<input id="userIds" name="userIds"/>
									（<input type="checkbox" id="checkAll" name="checkAll" style="vertical-align: middle;"/><label for="checkAll" style="vertical-align: middle;">&nbsp;全选</label>）
			  					</td>
			  				</tr>
			  			</c:if>
			  			<c:if test="${group.type eq 'organization'}">
		        		<tr>
				  			<td width="20%">组织机构：</td>
				  			<td width="80%">
						    	<c:choose>
								  	<c:when test="${empty(m.organizationId)}">
								  		<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree"/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree/${m.organizationId}/singleChecked"/>
								  	</c:otherwise>
								</c:choose>
								<input id="organizationIds" name="organizationIds"/>
								（<input type="checkbox" id="checkAll" name="checkAll" style="vertical-align: middle;"/><label for="checkAll" style="vertical-align: middle;">&nbsp;全选</label>）
			    			</td>
			    		</tr>
			    		</c:if>
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
<script type="text/javascript">
	$(function(){
		<c:if test="${group.type eq 'user'}">
			var transTool = $('#userIds').combogrid({
				panelWidth: 500,
		        idField: 'id',
		        textField: 'realname',
		        fitColumns: true,
		        multiple:true,
		        url:'${ctx}/security/user/user/findbyrealname',
		        columns: [[
		        	{field:'id',hidden:true},
                    {field:'username',title:'登录名',width:80},
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
					$('#userIds').combogrid('grid').datagrid('reload', {
						realname : $('#userIds').combogrid("getText")
					});
		 		}
		   	});
		</c:if>
		<c:if test="${group.type eq 'organization'}">
			$('#organizationIds').combotree({
		    	panelWidth:200,
		    	panelHeight:130,
		    	url:'${organizationUrl}',
		    	editable:false,
		    	multiple:true,
		    	onlyLeafCheck:true,
				onBeforeSelect : function(node){
					return false;
					//if (node.attributes.root){
					//	$.messager.alert('提示', '根节点不能被选中，请重新选择', 'info');
					//	return false;
					//}
				}
			});
		</c:if>
		
		
		<c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    	function ajaxValidationCallback(status, form, json, options){
    			if ($('#editForm').validationEngine('validate') == true){
    				if (status == true){
	    				if (json[1] == 0){
	    					$.messager.alert('提示', json[2], 'info');
	    				} else {
	    					form.validationEngine('detach');
	    					form.submit();
	    				}
	    			}
    			}
    		};
    		var validationEngine = $("#editForm").validationEngine({
    			promptPosition:'bottomRight',
    			validateNonVisibleFields:true,
    			showOneMessage: true,
    			ajaxFormValidation: true,
    			ajaxFormValidationURL: '${ctx}/security/group/groupRelation/${group.id}/validate',
    			ajaxFormValidationMethod: 'post',
    			onAjaxFormComplete: ajaxValidationCallback
    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>