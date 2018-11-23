<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审主表"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:300">名称</th>
				<th data-options="field:'createDate',width:150">创建时间</th>
				<th data-options="field:'extractDate',width:150">筛选时间</th>
				<th data-options="field:'remark',width:500">说明</th>
				<th data-options="field:'enabled',width:150,formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:750,height:265});">新增</a>
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
				$('.verifyCls').linkbutton({text:'筛选',plain:true,iconCls:'icon-verify'});
				$('.previewCls').linkbutton({text:'查看',plain:true,iconCls:'icon-preview'});
			}
		});
	});
	
	function formatOperation(val, row) {
		if (row.extractDate == null) {
			return '<a class="verifyCls" onclick="filter(' + row.id + ');" href="javascript:void(0);" style="height:24px;">筛选</a>|';
		} else {
			return '<a class="previewCls" onclick="preview(' + row.id + ');" href="javascript:void(0);" style="height:24px;">查看</a>|';
		}
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
		$.ewcms.openWindow({src:'${ctx}/yjk/re/reviewmain/' + id + '/indexUser',title:'查看筛选评审专家'});
	}
</script>