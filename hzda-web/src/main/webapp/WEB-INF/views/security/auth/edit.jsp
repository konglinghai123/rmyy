<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 授权"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/security/auth/save" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<h1 class="title">基本信息</h1>
		    	<fieldset>
				  	<table class="formtable">
			        	<tr>
							<td width="20%"><form:label path="type">类型：</form:label></td>
							<td width="80%"><form:select path="type" items="${types}" itemLabel="info" data-options="width:150,panelHeight:'auto',editable:false"/></td>
				    	</tr>
				    	<tr id="tr-user">
		            		<td>用户：</td>
				  			<td>
								<input id="userIds" name="userIds" class="validate[required]"/>
							</td>
				    	</tr>
				    	<tr id="tr-group">
				    		<td><span id="span-group"></span>：</td>
				    		<td>
				    			<input id="groupIds" name="groupIds" class="validate[required]"/>
				    		</td>
				    	</tr>
				    	<tr id="tr-organization_job-1">
				    		<td>组织机构：</td>
				    		<td>
							    <c:choose>
									<c:when test="${empty(m.organizationId)}">
									  	<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree"/>
									  </c:when>
									<c:otherwise>
									  	<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree/${m.organizationId}/singleChecked"/>
									</c:otherwise>
								</c:choose>
								<input id="organizationIds" name="organizationIds" class="validate[required]" data-options="url:'${organizationUrl}',editable:false,multiple:true"/>
							</td>
				    	</tr>
				    	<tr id="tr-organization_job-2">
				    		<td>工作职务：</td>
				    		<td>
				    			<c:choose>
									<c:when test="${empty(m.jobId)}">
									  	<c:set var="jobUrl" value="${ctx}/security/organization/job/tree"/>
									  </c:when>
									<c:otherwise>
									  	<c:set var="jobUrl" value="${ctx}/security/organization/job/tree/${m.jobId}/singleChecked"/>
									</c:otherwise>
								</c:choose>
								<input id="jobIds" name="jobIds" class="validate[required]" data-options="url:'${jobUrl}',editable:false,multiple:true"/>
				    		</td>
				    	</tr>
				  	</table>
				</fieldset>
				<h1 class="title">角色信息</h1>
		    	<fieldset>
		    		<table class="formtable">
		    			<tr>
							<td width="20%"><form:label path="roleIds">角色：</form:label></td>
							<td width="80%"><form:input path="roleIds" cssClass="easyui-combobox" data-options="url:'${ctx}/security/permission/role/canUse',method:'get',valueField:'id',textField:'name',panelHeight:140,editable:false,multiple:true,multiline:true,height:50" cssStyle="width:100%;"/></td>
				    	</tr>
		    		</table>
		    	</fieldset>
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
		$('#type').combobox({
			onSelect:function(record){
				typeSelect(record.value)
			}
		});
		
		typeSelect($('#type').combobox('getValue'));
		
		<c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			validateNonVisibleFields:true,
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
		</c:choose>
	});
	
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});

	function typeSelect(value){
		$('tr[id^="tr-"]').css('display', 'none');
		if (value == 'user_group'){
			$('#span-group').text('用户分组');
			$('#tr-group').css('display', '');
		} else if (value == 'organization_group'){
			$('#span-group').text('组织机构分组');
			$('#tr-group').css('display', '');
		} else {
			$('tr[id*="tr-' + value + '"]').css('display', '');
		}
		
		if (value == 'user'){
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
                    {field:'organizationNames',title:'医院名称',width:150}
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
		} else if (value == 'user_group' || value == 'organization_group'){
			var groupType = (value == 'user_group') ? 'user' : 'organization';
			$('#groupIds').combobox({
				width:150,
				panelWidth:150,
				panelHeight:130,
				url:'${ctx}/security/group/group/canUse?type=' + groupType,
				method:'get',
				valueField:'id',
				textField:'name',
				editable:false,
				multiple:true,
				onLoadSuccess:function(){
					$(this).combobox('setValue','${groupIds}');
				}
			});
		} else if (value == 'organization_job'){
			$('#organizationIds, #jobIds').combotree({
				width:150,
		    	panelWidth:200,
		    	panelHeight:130,
		    	editable:false,
		    	multiple:true,
		    	onlyLeafCheck:true,
				onBeforeSelect : function(node){
					return false;
				},
				onLoadSuccess:function(){
					$('#organizationIds').combobox('setValue','${organizationIds}');
					$('#jobIds').combobox('setValue','${jobIds}');
				}
			});
		}
	}
</script>