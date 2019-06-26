<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="用户"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true,
						formatter:function(val,row){
							return row.user.id;
						}">编号</th>
				<th data-options="field:'realname',width:150,
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
				<th data-options="field:'sined',width:100,
						formatter:function(val,row){
							return val? '是' : '否';
						}">是否已签字</th>
				<th data-options="field:'operation',width:100,align:'center',formatter:formatOperation"/>操作</th>
			</tr>
		</thead>
	</table>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voteresult/${currentReviewProcess.id}/queryUserSubmitted',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			onLoadSuccess:function(row){
				$('.printCls').linkbutton({plain:true,iconCls:'icon-print'});
				$('.processCls').linkbutton({plain:true,iconCls:'icon-process'});
			}
		});
	});
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		htmlOperation += '<a class="printCls" onclick="selectSystemParameter(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="打印"/> |  ';
		htmlOperation += '<a class="processCls" onclick="process(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="评审流程"/> ';
		return htmlOperation;
	}
</script>
