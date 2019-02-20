<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="任务设置"/>
	<table id="tt" data-options="toolbar:'#tb',fit:true,url:'${ctx}/system/scheduling/jobinfo/query',nowrap:true,pagination:true,rownumbers:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'label',width:150">名称</th>
			    <th data-options="field:'version',width:40">版本</th>
			    <th data-options="field:'jobClassName',width:100">作业名称</th>
			    <th data-options="field:'state',width:80">状态</th>
			    <th data-options="field:'startTime',width:145">开始时间</th>
			    <th data-options="field:'previousFireTime',width:145">上次执行时间</th>
			    <th data-options="field:'nextFireTime',width:145">下次执行时间</th>
			    <th data-options="field:'endTime',width:145">结束时间</th>
			    <th data-options="field:'remark',width:280">附加说明</th>
			    <th data-options="field:'operation',width:70,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/system/scheduling/jobinfo/false/edit',title:'新增',width:1040,height:510});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/system/scheduling/jobinfo/true/edit',title:'修改',width:1040,height:560});" href="javascript:void(0);">修改</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/system/scheduling/jobinfo/delete',title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="6%">名称：</td>
              			<td width="19%"><input type="text" name="LIKE_label" style="width:120px;"/></td>
            			<td width="6%">作业名称：</td>
              			<td width="19%">
    						<form:select id="jobClassId" name="EQ_jobClass.id" path="jobClasses"  cssClass="easyui-combobox" cssStyle="width:120px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="所有"/>
					  			<form:options items="${jobClasses}" itemValue="id" itemLabel="name"/>
							</form:select>
						</td>
            			<td width="6%">状态：</td>
              			<td width="19%">
    						<form:select id="jobInfoStates" name="CUSTOM_state" path="jobInfoStates"  cssClass="easyui-combobox" cssStyle="width:120px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="所有"/>
					  			<form:options items="${jobInfoStates}" itemLabel="info"/>
							</form:select>
						</td>
              			<td width="25%" colspan="2">
            				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();" href="javascript:void(0);">查询</a>
           					<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');" href="javascript:void(0);">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			onLoadSuccess:function(row){
				$('.pauseCls').linkbutton({text:'暂停',plain:true,iconCls:'icon-pause'});
				$('.resumedCls').linkbutton({text:'恢复',plain:true,iconCls:'icon-resumed'});
			}
		});
	});

	function formatOperation(val, row){
		var operation = "";
		if (row.state=='正常'){
			operation = '<a class="pauseCls" onclick="pause(' + row.id + ')" href="javascript:void(0);">暂停</a>';
		} else if (row.state=='暂停'){
			operation = '<a class="resumedCls" onclick="resumed(' + row.id + ')" href="javascript:void(0);">恢复</a>';
		}
		return operation;
	}
	
	function pause(id){
		$.post('${ctx}/system/scheduling/jobinfo/' + id + '/pause', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
	
	function resumed(id){
		$.post('${ctx}/system/scheduling/jobinfo/' + id + '/resumed', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>