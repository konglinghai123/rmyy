<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="用户"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'operation',width:80,align:'center',formatter:formatOperation"/>操作</th>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'realname',width:120">姓名</th>
				<th data-options="field:'sex',width:60,
						formatter:function(val,row){
							return row.sexDescription;
						}">性别</th>
				<th data-options="field:'mobilePhoneNumber',width:100">手机号</th>
				<th data-options="field:'organizationNames',width:150">科室名称</th>	
				<th data-options="field:'departmentAttribute',width:100,
						formatter:function(val,row){
							return row.departmentAttribute != null ? row.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,
						formatter:function(val,row){
							return row.profession != null ? row.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:120,
						formatter:function(val,row){
							return row.technicalTitle != null ? row.technicalTitle.name : '';
						}">技术职称(资格)</th>
				<th data-options="field:'appointment',width:100,
						formatter:function(val,row){
							return row.appointment != null ? row.appointment.name : '';
						}">聘任</th>
			</tr>
		</thead>
	</table>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voteresult/${currentReviewProcess.id}/queryUserNoSubmitted',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			onLoadSuccess:function(row){
				$('.giveupCls').linkbutton({plain:true,iconCls:'icon-give-up'});
			}
		});
	});
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		htmlOperation += '<a class="giveupCls" onclick="giveUp(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="中止投票"/> ';
		return htmlOperation;
	}

	function giveUp(id){
		 $.messager.confirm('提示', '确定要中止本轮投票的权利吗？中止后此人本轮将不能进行投票了，请谨慎操作！', function(r){
				if (r){
					$.post('${ctx}/yjk/re/voteresult/' + id + '/${reviewMainId}/${currentReviewProcess.id}/giveUp', {}, function(result) {
						if (result.success){
							$('#tt').datagrid('clearSelections');
							$('#tt').datagrid('reload');
						}
						$.messager.alert('提示', result.message, 'info');
					});
				}
		 });
	}
</script>
