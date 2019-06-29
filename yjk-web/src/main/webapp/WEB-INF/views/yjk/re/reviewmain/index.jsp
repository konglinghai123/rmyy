<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审主表"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true" rowspan="2"/>
				<th data-options="field:'enabled',width:200,halign:'center',formatter:formatOperation" rowspan="2">操作</th>
			    <th data-options="field:'id',width:80" rowspan="2">编号</th>
				<th data-options="field:'name',width:300" rowspan="2">名称</th>
				<th data-options="field:'createDate',width:150" rowspan="2">创建时间</th>
				<th data-options="field:'extractDate',width:150" rowspan="2">筛选用户时间</th>
				<th data-options="field:'systemParameterProjectRemark',width:120" rowspan="2">申报项目说明</th>
				<th data-options="field:'systemParameterRange',width:320" rowspan="2">评审申报范围</th>
				<th colspan="2">拟新增通用名总数</th>
				<th colspan="2">拟新增剂型/规格总数</th>
				<th data-options="field:'remark',width:300" rowspan="2">说明</th>
			</tr>
			<tr>
				<th data-options="field:'generalNameChinese',width:100">中成药</th>
				<th data-options="field:'generalNameWestern',width:100">西药</th>
				<th data-options="field:'formulaChinese',width:100">中成药</th>
				<th data-options="field:'formulaWestern',width:100">西药</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:750,height:365});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="updateReviewMain();">修改</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px;"/></td>
            			<td width="5%">创建时间</td>
              			<td width="23%"><input type="text" id="createDate" name="GTE_createDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="createDate" name="LTE_createDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
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
			url:'${ctx}/yjk/re/reviewmain/query',
			toolbar:'#tb',
			fit:true,
			nowrap:false,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
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
					content: '<iframe src="${ctx}/yjk/re/reviewexpert/' + rowData.id + '/index" frameborder="0" width="100%" height="450px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			},
			onBeforeLoad:function(param){
				param['parameters']=$('#queryform').serializeObject();
			},
			onLoadSuccess:function(row){
				$('.openCls').linkbutton({plain:true,iconCls:'icon-operate'});
				$('.closeCls').linkbutton({plain:true,iconCls:'icon-exit'});
				$('.verifyCls').linkbutton({plain:true,iconCls:'icon-verify'});
				$('.previewCls').linkbutton({plain:true,iconCls:'icon-preview'});
				$('.selectCls').linkbutton({plain:true,iconCls:'icon-connect'});
				$('.processCls').linkbutton({plain:true,iconCls:'icon-process'});
			}
		});
	});
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		if (row.extractDate == null) {
			htmlOperation = '<a class="verifyCls" onclick="filter(' + row.id + ');" href="javascript:void(0);" style="height:24px;" title="筛选用户"/> | ';
		} else {
			htmlOperation = '<a class="previewCls" onclick="preview(' + row.id + ');" href="javascript:void(0);" style="height:24px;" title="查看用户"/> | ';
		}
		if (val) {
			htmlOperation += '<a class="closeCls" onclick="closeDeclare(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="关闭申报"/> | ';
		} else {
			htmlOperation += '<a class="openCls" onclick="openDeclare(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="启动申报"/> | ';
		}
		htmlOperation += '<a class="selectCls" onclick="selectSystemParameter(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="选择范围"/> |  ';
		htmlOperation += '<a class="processCls" onclick="process(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="评审流程"/> |  ';
		return htmlOperation;
	}
	
	function closeDeclare(id) {
		$.messager.confirm('提示', '确定要关闭评审吗?<br/><font color="red">关闭后所有人员将无法进行评审!</font>', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/yjk/re/reviewmain/' + id + '/closedeclare',{}, function(result) {
					if (result.success) {
						$('#tt').datagrid('reload');
						$.ewcms.removeLoading();
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
		
	}
	
	function openDeclare(id) {
		$.messager.confirm('提示', '确定要启动评审吗?<br/><font color="red">启动后筛选用户可进行评审!</font>', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/yjk/re/reviewmain/' + id + '/opendeclare',{}, function(result) {
					if (result.success) {
						$('#tt').datagrid('reload');
						$.ewcms.removeLoading();
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
		});
	}
	
	function updateReviewMain(){
		var rows = $('#tt').datagrid('getSelections');
	    
    	if(rows.length != 1){
	        $.messager.alert('提示','请选择一条记录进行修改','info');
	        return;
	    }
    	if(rows[0].enabled){
	        $.messager.alert('提示','启动记录不能修改，关闭以后才可修改','info');
	        return;
    	}
    	$.ewcms.edit({title:'修改',width:750,height:365});
	}
	
	function filter(id){
		$.messager.confirm('提示', '确定要生成评审专家吗?<br/><font color="red">筛选后将不能重新生成，请谨慎使用!</font>', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/yjk/re/reviewmain/' + id + '/filter', {}, function(result){
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
		$.ewcms.openWindow({src:'${ctx}/yjk/re/reviewmain/' + id + '/indexUser',title:'查看用户 - 有权限评审用户'});
	}
	
	function selectSystemParameter(id){
		//$.messager.confirm('提示', '确定要选择申报数据吗?<br/><font color="red">申报的数据只能选择一次，请谨慎选择！</font>', function(r) {
		//	if (r) {
				$.ewcms.openWindow({src:'${ctx}/yjk/re/reviewmain/' + id + '/indexSystemParameter',title:'选择范围 - 有申报数据',width:850,height:550});
		//	}
		//});
	}
	
	function process(id){
		$.ewcms.openWindow({src:'${ctx}/yjk/re/reviewprocess/' + id + '/index',title:'评审流程 - 选择评审流程',width:1130,height:550});
	}
</script>