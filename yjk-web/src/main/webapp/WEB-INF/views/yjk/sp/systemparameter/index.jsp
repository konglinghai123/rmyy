<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf"%>

<ewcms:head title="申报 - 系统参数设置" />
	<table id="tt">
		<thead> 	
			<tr>	
				<th data-options="field:'ck',checkbox:true"/>
				<th data-options="field:'enabled',width:160,align:'center',formatter:formatOperation">操作</th>
				<th data-options="field:'id',sortable:true">编号</th>
				<th data-options="field:'applyStartDate',width:150">申请开始时间</th>
				<th data-options="field:'applyEndDate',width:150">申请结束时间</th>
				<th data-options="field:'repeatDeclared',width:120,
						formatter:function(val,row){
							return val?'是':'否';
						}">可否重复申报</th>				
				<th data-options="field:'injectDeclarationLimt',width:120,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">注射一品限数</th>
				<th data-options="field:'oralDeclarationLimt',width:120,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">口服一品限数</th>
				<th data-options="field:'otherDeclarationLimt',width:160,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">外用及其他一品限数</th>
				<th data-options="field:'declareTotalLimt',width:140,
						formatter:function(val,row){
							return val==0?'不限数':val;
						}">每个人总报限数</th>
				<th data-options="field:'projectRemark',width:120">项目说明</th>
				<th data-options="field:'nodeclareNumber',width:100">未提交初审数</th>
				<th data-options="field:'initNumber',width:100">已提交初审数</th>
				<th data-options="field:'passedNumber',width:110">初审核已通过数</th>
				<th data-options="field:'unPassedNumber',width:110">初审核未通过数</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 5px; height: auto;">
		<div class="toolbar" style="margin-bottom: 2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$.ewcms.add({title:'新增',width:600,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="updateSystemParameter();">修改</a>
		</div>
		<div>
			<form id="queryform" style="padding: 0; margin: 0;">
				<table class="formtable">
					<tr>
						<td width="10%">申请时间从</td>
						<td colspan="2">
							<input type="text" id="applyStartDate" name="GTE_applyStartDate" class="easyui-datetimebox" style="width: 145px" data-options="editable:false" />
							 至 
							<input type="text" id="applyStartDate1" name="LTE_applyStartDate" class="easyui-datetimebox" style="width: 145px" data-options="editable:false" />
						</td>
						<td width="16%" colspan="2">
							<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
							<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<ewcms:editWindow />
<ewcms:footer />
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			url : '${ctx}/yjk/sp/systemparameter/query',
			toolbar : '#tb',
			fit : true,
			nowrap : false,
			pagination : true,
			rownumbers : true,
			striped : true,
			pageSize : 20,
			border : false,
	        rowStyler: function(index,row){
	        	if (row.enabled){
	    			return 'background-color:#FF9999;color:#0000FF;';
	        	}
	    	},
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/yjk/sp/systemexpert/' + rowData.id + '/index" frameborder="0" width="100%" height="450px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
		},
		onLoadSuccess:function(row){
			$('.runCls').linkbutton({plain:true,iconCls:'icon-run'});
			$('.closeCls').linkbutton({plain:true,iconCls:'icon-exit'});
			$('.verifyCls').linkbutton({plain:true,iconCls:'icon-verify'});
			$('.previewCls').linkbutton({plain:true,iconCls:'icon-preview'});
		}
		});
	});

	function formatOperation(val, row) {
		currentTimestamp = new Date().getTime();
		applyEndDateTimestamp = new Date(Date.parse(row.applyEndDate.replace(/-/g, "/"))).getTime();
		applyStartDatetamp = new Date(Date.parse(row.applyStartDate.replace(/-/g, "/"))).getTime();
		var htmlOperation = '<a class="verifyCls" onclick="filter(' + row.id + ');" href="javascript:void(0);" style="height:24px;" title="筛选用户"/> | ';
		htmlOperation += '<a class="previewCls" onclick="preview(' + row.id + ');" href="javascript:void(0);" style="height:24px;" title="查看用户"/> | '
		if (val) {
			htmlOperation += '<a class="closeCls" onclick="closeDeclare(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="关闭申报"/> ';
		} else {
			htmlOperation +=  '<a class="runCls" onclick="openDeclare(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="启动申报"/> ';
		}
		return htmlOperation;
	}
	
	function closeDeclare(id) {
		$.messager.confirm('提示', '确定要关闭申报吗?<br/><font color="red">关闭后所有人员将无法进行新药申报!</font>', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/yjk/sp/systemparameter/' + id + '/closedeclare',{}, function(result) {
					if (result.success) {
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
				$.ewcms.removeLoading();
			}
		});
		
	}

	function openDeclare(id) {
		$.messager.confirm('提示', '确定要启动申报吗?<br/><font color="red">启动后筛选用户可进行申报新药!</font>', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/yjk/sp/systemparameter/' + id + '/opendeclare',{}, function(result) {
					if (result.success) {
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
				$.ewcms.removeLoading();
			}
		});
	}
	
	
	function updateSystemParameter(){
		var rows = $('#tt').datagrid('getSelections');
	    
    	if(rows.length != 1){
	        $.messager.alert('提示','请选择一条记录进行修改','info');
	        return;
	    }
    	if(rows[0].enabled){
	        $.messager.alert('提示','启动记录不能修改，关闭以后才可修改','info');
	        return;
    	}
    	$.ewcms.edit({title:'修改',width:600,height:450});
	}
	
	function filter(id){
		$.messager.confirm('提示', '确定要生成申报人员吗?', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/yjk/sp/systemparameter/' + id + '/filter', {}, function(result){
					if (result.success){
						$('#tt').datagrid('reload');
						$.ewcms.removeLoading();
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
	}
	
	function preview(id){
		$.ewcms.openWindow({src:'${ctx}/yjk/sp/systemparameter/' + id + '/indexUser',title:'查看用户 - 有权限申报新药用户',width:850,height:550});
	}
</script>