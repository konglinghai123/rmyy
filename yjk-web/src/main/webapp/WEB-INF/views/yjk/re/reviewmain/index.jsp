<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审主表"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',width:100">编号</th>
				<th data-options="field:'name',width:300">名称</th>
				<th data-options="field:'createDate',width:150">创建时间</th>
				<th data-options="field:'extractDate',width:150">筛选时间</th>
				<th data-options="field:'systemParameterRange',width:300">评审申报范围</th>
				<th data-options="field:'enabled',width:400,halign:'center',formatter:formatOperation">操作</th>
				<th data-options="field:'remark',width:300">说明</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:750,height:265});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'"	onclick="updateReviewMain();">修改</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px;"/></td>
            			<td width="5%">创建时间</td>
              			<td width="23%"><td colspan="2"><input type="text" id="createDate" name="GTE_createDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="createDate" name="LTE_createDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
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
			nowrap:true,
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
				$('.openCls').linkbutton({text:'启动评审',plain:true,iconCls:'icon-operate'});
				$('.closeCls').linkbutton({text:'关闭评审',plain:true,iconCls:'icon-exit'});
				$('.verifyCls').linkbutton({text:'筛选用户',plain:true,iconCls:'icon-verify'});
				$('.previewCls').linkbutton({text:'查看用户',plain:true,iconCls:'icon-preview'});
				$('.selectCls').linkbutton({text:'选择范围',plain:true,iconCls:'icon-connect'});
			}
		});
	});
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		if (row.extractDate == null) {
			htmlOperation = '<a class="verifyCls" onclick="filter(' + row.id + ');" href="javascript:void(0);" style="height:24px;">筛选用户</a> | ';
		} else {
			htmlOperation = '<a class="previewCls" onclick="preview(' + row.id + ');" href="javascript:void(0);" style="height:24px;">查看用户</a> | ';
		}
		if (val) {
			htmlOperation = htmlOperation + '<a class="closeCls" onclick="closeDeclare(' + row.id + ')" href="javascript:void(0);">关闭申报</a> | ';
		} else {
			htmlOperation = htmlOperation + '<a class="openCls" onclick="openDeclare(' + row.id + ')" href="javascript:void(0);">启动申报</a> | ';
		}
		htmlOperation = htmlOperation + '<a class="selectCls" onclick="selectSystemParameter(' + row.id + ')" href="javascript:void(0);">选择范围</a> ';
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
    	$.ewcms.edit({title:'修改',width:750,height:265});
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
		$.ewcms.openWindow({src:'${ctx}/yjk/re/reviewmain/' + id + '/indexSystemParameter',title:'查看用户 - 有权限评审用户',width:850,height:550});
	}
</script>