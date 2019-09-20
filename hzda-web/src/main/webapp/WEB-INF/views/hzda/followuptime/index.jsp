<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'nextTime',sortable:true,width:150">下一次随访时间</th>
			    <th data-options="field:'tip',width:100,
						formatter:function(val,row){
							if (val != null) {
								return val ? '是':'否';
							}
						}">是否提醒</th>
				<th data-options="field:'sms',width:100,
						formatter:function(val,row){
							if (val != null) {
								return val ? '是':'否';
							}
						}">是否短信通知</th>
				<th data-options="field:'smsDate',sortable:true,width:150">发送时间</th>
				<th data-options="field:'message',sortable:true,width:200,
						formatter:function(val,row){
							if (val != null) {
								if (val == 'OK'){
									return '发送成功';
								} else {
									return val;
								}
							}
						}">发送说明</th>
				<th data-options="field:'operator',width:80,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<c:if test="${!user.admin}">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/hzda/followuptime/save/${generalInformationId}',title:'新增',width:500,height:360,left:100});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/hzda/followuptime/save/${generalInformationId}',title:'修改',width:500,height:360,left:100});">修改</a>
			<!-- 
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/hzda/followuptime/delete/${generalInformationId}',title:'删除'});">删除</a>
			 -->
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$.ewcms.query();">刷新</a>
		</div>
		</c:if>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			url:'${ctx}/hzda/followuptime/query/${generalInformationId}',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:10,
			onLoadSuccess:function(row){
				$('.runCls').linkbutton({plain:true,iconCls:'icon-run'});
			}
		});
	});

	function formatOperation(val, row) {
		if (!row.sms){
			return '<a class="runCls" onclick="sendSms(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="发送"/>';
		}
	}
	
	function sendSms(id){
		$.messager.confirm('提示', '确定要发送短信信息吗？', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/hzda/followuptime/send',{'id':id}, function(result) {
					$('#tt').datagrid('reload');
					$.messager.alert('提示', result.message, 'info');
					$.ewcms.removeLoading();
				});
			}
		});
	}
</script>