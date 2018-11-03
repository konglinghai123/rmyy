<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf"%>

<ewcms:head title="审批-系统参数设置" />
<table id="tt">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true" />
			<th data-options="field:'id',hidden:true">编号</th>
			<th data-options="field:'applyStartDate',width:150">申请开始时间</th>
			<th data-options="field:'applyEndDate',width:150">申请结束时间</th>
			<th data-options="field:'declarationLimt',width:60">申报限数</th>
			<th data-options="field:'organizationNames',width:200">科室/病区</th>
			<th data-options="field:'departmentAttributeNames',width:200">科室属性</th>
			<th data-options="field:'professionNames',width:200">执业类别</th>
			<th data-options="field:'technicalTitleNames',width:200">技术职称(资格)</th>
			<th data-options="field:'appointmentNames',width:200">聘任</th>
			<th data-options="field:'percent',width:60,
					formatter:function(val,row){
						return val != null ? val + '%' : '';
					}">百分比</th>
			<th data-options="field:'randomNumber',width:60">随机人数</th>
			<th data-options="field:'departmentNumber',width:60">科室人数</th>
			<th data-options="field:'enabled',width:100,formatter:formatOperation">操作</th>
		</tr>
	</thead>
</table>
<div id="tb" style="padding: 5px; height: auto;">
	<div class="toolbar" style="margin-bottom: 2px">
		<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-add',toggle:true"
			onclick="$.ewcms.add({title:'新增',width:600,height:500});">新增</a> <a
			id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-edit',toggle:true"
			onclick="$.ewcms.edit({title:'修改',width:600,height:500});">修改</a>
	</div>
	<div style="padding-left: 5px;">
		<form id="queryform" style="padding: 0; margin: 0;">
			<table class="formtable">
				<tr>
					<td width="10%">申请开始时间从</td>
					<td colspan="2"><input type="text" id="applyStartDate"
						name="GTE_applyStartDate" class="easyui-datetimebox"
						style="width: 145px" data-options="editable:false" /> 至 <input
						type="text" id="applyStartDate1" name="LTE_applyStartDate"
						class="easyui-datetimebox" style="width: 145px"
						data-options="editable:false" /></td>
					<td width="16%" colspan="2"><a id="tb-query"
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
						<a id="tb-clear" href="javascript:void(0);"
						class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
						onclick="javascript:$('#queryform').form('reset');">清除</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>
<ewcms:editWindow />
<ewcms:footer />
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			url : '${ctx}/yjk/sp/systemparamter/query',
			toolbar : '#tb',
			fit : true,
			nowrap : false,
			pagination : true,
			rownumbers : true,
			striped : true,
			pageSize : 20,
			border : false
		});
	});

	function formatOperation(val, row) {
		currentTimestamp = new Date().getTime();
		applyEndDateTimestamp = new Date(Date.parse(row.applyEndDate.replace(/-/g, "/"))).getTime();
		applyStartDatetamp = new Date(Date.parse(row.applyStartDate.replace(/-/g, "/"))).getTime();
		if (val) {
			return '<a class="resumedCls" onclick="closeDeclare(' + row.id
					+ ')" href="javascript:void(0);">关闭申报</a> ';
		} else {
			if (applyStartDatetamp <= currentTimestamp
					&& currentTimestamp <= applyEndDateTimestamp) {
				return '<a class="resumedCls" onclick="openDeclare(' + row.id
						+ ')" href="javascript:void(0);">启动申报</a> ';
			} else {
				return '';
			}
		}
	}

	/*
	function deleteSystemParameter(id){
		$.post('${ctx}/yjk/sp/systemparamter/' + id + '/delete', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}*/

	function closeDeclare(id) {
		loadingEnable();
		$.messager.confirm('提示', '确定要关闭申报吗?<br/><font color="red">关闭后所有人员将无法进行新药申报!</font>', function(r) {
			if (r) {
				$.post('${ctx}/yjk/sp/systemparamter/' + id + '/closedeclare',{}, function(result) {
					if (result.success) {
						$('#tt').datagrid('reload');
						$('.datagrid-mask').remove();
						$('.datagrid-mask-msg').remove();
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
		
	}

	function openDeclare(id) {
		loadingEnable();
		$.messager.confirm('提示', '确定要启动申报吗?<br/><font color="red">启动后将根据所选条件进行人员重新筛选!</font>', function(r) {
			if (r) {
				loadingEnable();
				$.post('${ctx}/yjk/sp/systemparamter/' + id + '/opendeclare',{}, function(result) {
					if (result.success) {
						$('#tt').datagrid('reload');
						$('.datagrid-mask').remove();
						$('.datagrid-mask-msg').remove();
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
		
	}
	function loadingEnable(){
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\"></div>").html("<font size='9'>正在处理，请稍候。。。</font>").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	}
</script>