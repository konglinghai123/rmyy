<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="用户"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'username',width:150,sortable:true">登录名</th>
				<th data-options="field:'realname',width:150,sortable:true">姓名</th>
				<th data-options="field:'sex',width:60,sortable:true,
						formatter:function(val,row){
							return row.sexDescription;
						}">性别</th>
				<th data-options="field:'email',width:200,sortable:true">邮箱</th>
				<th data-options="field:'mobilePhoneNumber',width:100,sortable:true">手机号</th>
				<th data-options="field:'departmentAttribute',width:100,sortable:true,
						formatter:function(val,row){
							return row.departmentAttribute != null ? row.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,sortable:true,
						formatter:function(val,row){
							return row.profession != null ? row.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:120,sortable:true,
						formatter:function(val,row){
							return row.technicalTitle != null ? row.technicalTitle.name : '';
						}">技术职称(资格)</th>
				<th data-options="field:'appointment',width:100,sortable:true,
						formatter:function(val,row){
							return row.appointment != null ? row.appointment.name : '';
						}">是否聘任</th>
				<th data-options="field:'director',width:120,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否科主任</th>
				<th data-options="field:'secondDirector',width:120,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否科副主任</th>
				<th data-options="field:'pharmacy',width:140,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否药事会成员</th>
				<th data-options="field:'science',width:170,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否院学术委员会成员</th>
				<th data-options="field:'antibiosis',width:200,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否抗菌药物遴选小组成员</th>
				<th data-options="field:'createDate',width:200,sortable:true">注册日期</th>
				<th data-options="field:'admin',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">管理员</th>
				<th data-options="field:'status',width:120,sortable:true,
						formatter:function(val,row){
							return row.statusInfo;
						}">状态</th>
				<th data-options="field:'deleted',width:80,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">删除</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:750,height:565});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:750,height:565});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
			<a id="tb-password" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-password',plain:true">修改密码</a>
			<a id="tb-recycle" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-recycle',plain:true">还原删除用户</a>
			<a id="tb-status" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-status',iconCls:'icon-status'">状态</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-import'" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入用户信息',src:'${ctx}/security/user/user/import'});">导入</a>
		</div>
		<div id="menu-status" style="width:150px">
			<div id="menu-status-show" data-options="iconCls:'icon-status-show'" onclick="$.ewcms.status({status:'normal',info:'解封用户',prompt:true});">解封用户</div>
			<div id="menu-status-hide" data-options="iconCls:'icon-status-hide'" onclick="$.ewcms.status({status:'blocked',info:'封禁用户',prompt:true});">封禁用户</div>
		</div>
        <div>
        	<form id="queryform">
        		<table class="formtable">
              		<tr>
              			<td width="5%">登录名</td>
              			<td width="23%"><input type="text" name="LIKE_username" style="width:140px"/></td>
    					<td width="5%">姓名</td>
    					<td width="23%"><input type="text" name="LIKE_realname" style="width:140px"/></td>
    					<td width="5%">手机号</td>
    					<td width="23"><input type="text" name="LIKE_mobilePhoneNumber" style="width:140px"/></td>
						<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton" onclick="$.ewcms.moreQuery();"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<tr style="display: none;">
           				<td>状态</td>
    					<td>
    						<form:select id="status" name="EQ_status" path="statusList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${statusList}" itemLabel="info"/>
							</form:select>
						</td>
           				<td>是否删除</td>
           				<td>
           					<form:select id="deleted" name="EQ_deleted" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
           				</td>
           				<td>注册日期</td>
           				<td colspan="2"><input type="text" id="createDate" name="GTE_createDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="createDate" name="LTE_createDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
           			</tr>
           			<tr style="display: none;">
           				<td>是否科主任</td>
    					<td>
    						<form:select id="director" name="EQ_director" path="booleanList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
						</td>
           				<td>是否科副主任</td>
           				<td>
           					<form:select id="secondDirector" name="EQ_secondDirector" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
           				</td>
           				<td>是否药事会成员</td>
           				<td colspan="2">
           					<form:select id="pharmacy" name="EQ_pharmacy" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
						</td>
           			</tr>
           			<tr style="display: none;">
           				<td>是否院学术委员会成员</td>
    					<td>
    						<form:select id="science" name="EQ_science" path="booleanList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
						</td>
           				<td>是否抗菌药物遴选小组成员</td>
           				<td>
           					<form:select id="antibiosis" name="EQ_antibiosis" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
           				</td>
           				<td>执业类别</td>
           				<td colspan="2">
           					<form:select id="profession" name="EQ_profession" path="professionList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${professionList}" itemValue="id" itemLabel="name"/>
							</form:select>
						</td>
           			</tr>
           			<tr style="display: none;">
           				<td>技术职称(资格)</td>
    					<td>
    						<form:select id="technicalTitle" name="EQ_technicalTitle" path="technicalTitleList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${technicalTitleList}" itemValue="id" itemLabel="name"/>
							</form:select>
						</td>
           				<td>聘任</td>
           				<td>
           					<form:select id="appointment" name="EQ_appointment" path="appointmentList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${appointmentList}" itemValue="id" itemLabel="name"/>
							</form:select>
           				</td>
           				<td>科室属性</td>
           				<td colspan="2">
           					<form:select id="departmentAttribute" name="EQ_departmentAttribute" path="departmentAttributeList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${departmentAttributeList}" itemValue="id" itemLabel="name"/>
							</form:select>
						</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/security/user/user/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			view : detailview,
			border:false,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/security/user/userOrganizationJob/' + rowData.id + '/index" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			},
			onBeforeLoad:function(param){
				param['parameters']=$('#queryform').serializeObject();
			}
		});
		
		$('#tb-recycle').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    	
	    	if(rows.length == 0){
		        $.messager.alert('提示','请选择还原的记录','info');
		        return;
		    }
		    
		    var parameter='';
		    $.each(rows,function(index,row){
		    	parameter += 'selections=' + row.id +'&';
		    });
		    
			$.messager.confirm('提示', '确定要还原所选记录吗?', function(r){
				if (r){
					$.post('${ctx}/security/user/user/recycle', parameter ,function(data){
						if (data.success){
	    					$('#tt').datagrid('clearSelections');
	    					$('#tt').datagrid("reload");
						}
						$.messager.alert('还原用户', data.message, 'info');
					});
				}
			});
		});
		
		$('#tb-password').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    	
	    	if(rows.length == 0){
		        $.messager.alert('提示','请选择修改密码的记录','info');
		        return;
		    }
		    
		    var parameter='';
		    $.each(rows,function(index,row){
		    	parameter += 'selections=' + row.id +'&';
		    });
		    
			$.messager.confirm('提示', '确定要修改所选记录的密码吗?', function(r){
				if (r){
			    	$.messager.prompt('修改密码', '新密码：', function(newPassword){
			    		if (newPassword){
			    			parameter += 'newPassword=' + newPassword;
							$.post('${ctx}/security/user/user/changePassword', parameter ,function(data){
								if (data.success){
			    					$('#tt').datagrid('clearSelections');
			    					$('#tt').datagrid("reload");
								}
								$.messager.alert('修改密码', data.message, 'info');
							});
			    		}
			    	});
				}
			});
		});
	});
</script>
