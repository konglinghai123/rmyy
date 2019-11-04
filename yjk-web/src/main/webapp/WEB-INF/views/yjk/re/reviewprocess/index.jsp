<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审流程"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true" rowspan="2"/>
			    <th data-options="field:'id'" rowspan="2">编号</th>
			    <th data-options="field:'weight',hidden:true" rowspan="2">排序号</th>
				<th data-options="field:'ruleCnName',width:150,
						formatter:function(val,row){
							return row.reviewBaseRule != null ? row.reviewBaseRule.ruleCnName : '';
						}" rowspan="2">规格名称</th>
				<th data-options="field:'givenOrgan',width:130,formatter:formatOperation" rowspan="2">特定科室/病区</th>
				<th data-options="field:'hospitalData',width:140,
						formatter:function(val,row){
							return val?'是':'否';
						}"  rowspan="2">是否显示院用数据</th>
				<th data-options="field:'finished', width:100,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							else return val ? '是' : '否';
						}" rowspan="2">是否完成</th>
				<th colspan="2">拟新增通用名</th>
				<th colspan="2">拟新增剂型/规格</th>
				<th data-options="field:'displayColumnRuleCnNames',width:300,formatter:formatTooltip" rowspan="2">显示字段集合</th>
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
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/yjk/re/reviewprocess/${reviewMainId}/save',title:'新增',width:750,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({src:'${ctx}/yjk/re/reviewprocess/${reviewMainId}/save',title:'修改',width:750,height:450});">修改</a>
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exchange'">互换位置</a>
			<a id="tb-forced" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-forced',iconCls:'icon-forced'">强制</a>
		</div>
		<div id="menu-forced" style="width:150px">
			<div id="menu-forced-open" data-options="iconCls:'icon-forced-open'" onclick="$.ewcms.status({status:false,info:'强制开启',prompt:true});">开启</div>
			<div id="menu-forced-closure" data-options="iconCls:'icon-forced-closure'" onclick="$.ewcms.status({status:true,info:'强制结束',prompt:true});">结束</div>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
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
			url:'${ctx}/yjk/re/reviewprocess/${reviewMainId}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:false,
			rownumbers:true,
			pagination:true,
			striped:true,
			pageSize:20,
			border:true,
	        rowStyler: function(index,row){
	        	if (row.enabled){
	    			return 'background-color:#FF0000;color:#FFFFFF;';
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
					content: '<iframe src="${ctx}/yjk/re/reviewprocessrecord/' + rowData.id + '/index" frameborder="0" width="100%" height="250px" scrolling="auto"></iframe>',
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
				$('.selectCls').linkbutton({plain:true,iconCls:'icon-connect'});
			}
		});
		
		$('#tb-exchange').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    
	    	if(rows.length != 2){
		        $.messager.alert('提示','请选择2条互换的记录','info');
		        return;
		    }
		    $.messager.confirm('提示', '确定要互换所选记录吗?', function(r){
				if (r){
					if(rows[0].weight<rows[1].weight){
						$.post('${ctx}/yjk/re/reviewprocess/' + rows[1].id + '/' + rows[0].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}else{
						$.post('${ctx}/yjk/re/reviewprocess/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}
				}
			});
		});
	});
	
	function formatOperation(val, row) {
		var	htmlOperation = '<a class="selectCls" onclick="openEnsurePassThrough(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="特定科室"/>';
		if (row.isEnsurePassThrough){
			htmlOperation += " | 有";
		} else {
			htmlOperation += " | 无";
		}
		//htmlOperation += "特定科室/病区";
		return htmlOperation;
	}
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
	
	function openEnsurePassThrough(id){
		$.ewcms.openWindow({src:'${ctx}/yjk/re/ensurepassthrough/' + id + '/index',title:'科室 - 特定科室'});
	}
</script>