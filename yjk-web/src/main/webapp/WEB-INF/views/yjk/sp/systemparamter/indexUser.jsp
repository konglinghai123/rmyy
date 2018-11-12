<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="申报-用户"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'username',width:80,sortable:true">用户名</th>
				<th data-options="field:'realname',width:150,sortable:true">实名</th>
				<th data-options="field:'sex',width:60,sortable:true,
						formatter:function(val,row){
							return row.sexDescription;
						}">性别</th>
				<th data-options="field:'email',width:100,sortable:true">邮箱</th>
				<th data-options="field:'mobilePhoneNumber',width:100,sortable:true">手机号</th>
				<th data-options="field:'departmentAttribute',width:100,sortable:true,
						formatter:function(val,row){
							return row.departmentAttribute != null ? row.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,sortable:true,
						formatter:function(val,row){
							return row.profession != null ? row.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:100,sortable:true,
						formatter:function(val,row){
							return row.technicalTitle != null ? row.technicalTitle.name : '';
						}">技术职称（资格）</th>
				<th data-options="field:'appointment',width:100,sortable:true,
						formatter:function(val,row){
							return row.appointment != null ? row.appointment.name : '';
						}">是否聘任</th>
				<th data-options="field:'director',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否科主任</th>
				<th data-options="field:'secondDirector',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否科副主任</th>
				<th data-options="field:'pharmacy',width:120,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否药事会成员</th>
				<th data-options="field:'science',width:150,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否院学术委员会成员</th>
				<th data-options="field:'antibiosis',width:170,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否抗菌药物遴选小组成员</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
        	<c:if test="${m.enabled}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/yjk/sp/systemparamter/${m.id}/saveUser',title:'添加',width:750,height:265});">添加</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/yjk/sp/systemparamter/${m.id}/removeUser',title:'移除',width:750,height:265});">移除</a>
			</c:if>
		</div>
        <div  style="padding-left:5px;">
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/sp/systemparamter/${m.id}/queryUser',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			pagination:true
		});
	});
</script>