<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="用户投票情况"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true,
						formatter:function(val,row){
							return row.user.id;
						}">编号</th>
				<th data-options="field:'voteTypeInfo',width:80">投票操作</th>
				<th data-options="field:'realname',width:120,
						formatter:function(val,row){
							return row.user.realname;
						}">姓名</th>
				<th data-options="field:'sex',width:60,
						formatter:function(val,row){
							return row.user.sexDescription;
						}">性别</th>
				<th data-options="field:'mobilePhoneNumber',width:100,
						formatter:function(val,row){
							return row.user.mobilePhoneNumber;
						}">手机号</th>
				<th data-options="field:'user.organizationNames',
						formatter:function(val,row){
							return row.user.organizationNames;
						}">科室名称</th>	
				<th data-options="field:'departmentAttribute',width:100,
						formatter:function(val,row){
							return row.user.departmentAttribute != null ? row.user.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,
						formatter:function(val,row){
							return row.user.profession != null ? row.user.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:120,
						formatter:function(val,row){
							return row.user.technicalTitle != null ? row.user.technicalTitle.name : '';
						}">技术职称(资格)</th>
				<th data-options="field:'appointment',width:100,
						formatter:function(val,row){
							return row.user.appointment != null ? row.user.appointment.name : '';
						}">聘任</th>
				<th data-options="field:'appointment',width:100,
						formatter:function(val,row){
							return row.user.appointment != null ? row.user.appointment.name : '';
						}">聘任</th>
			</tr>
		</thead>
	</table>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	var caption = '';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/drugvote/${reviewProcessId}/${voteResultId}/queryVoteUser?reviewMainId=${reviewMainId}',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			border:true,
			singleSelect:true,
			onLoadSuccess:function(row){
			}
		});
	});
</script>
