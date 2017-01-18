<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="诊疗卡管理"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'practiceNo',width:100">诊疗卡号</th>
			    <th data-options="field:'createDate',width:150">发卡时间</th>
			    <th data-options="field:'balance',width:80">余额</th>
			    <th data-options="field:'statusInfo',width:80">诊疗卡状态</th>
			    <th data-options="field:'deposit',width:80,
			    	formatter:function(val,row){
							return val ? '是' : '否';
						}">是否收取押金</th>
			    <th data-options="field:'patientBaseInfo.name',width:80,
						formatter:function(val,row){
							return row.patientBaseInfo.name;
						}">患者姓名</th>
						
				<th data-options="field:'patientBaseInfo.certificateTypeName',width:130,
						formatter:function(val,row){
							return row.patientBaseInfo.certificateTypeName;
						}">证件类型</th>
			    <th data-options="field:'patientBaseInfo.certificateNo',width:130,
						formatter:function(val,row){
							return row.patientBaseInfo.certificateNo;
						}">证件号码</th>
				<th data-options="field:'patientBaseInfo.patientId',width:80,
						formatter:function(val,row){
							return row.patientBaseInfo.patientId;
						}">患者索引号</th>
				<th data-options="field:'history',width:80,formatter:formatHistory">操作历史</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'发诊疗卡',width:700,height:350});" href="javascript:void(0);">发诊疗卡</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="tb-loss" href="javascript:void(0);">挂失</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="tb-cancelloss" href="javascript:void(0);">取消挂失</a>
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td>诊疗卡号</td>
              			<td><input type="text" name="LIKE_practiceNo" style="width:140px"/></td>
    					<td >姓名</td>
    					<td ><input type="text" name="LIKE_patientBaseInfo.name" style="width:140px"/></td>
           				<td>状态</td>
    					<td>
    						<form:select id="status" name="EQ_status" path="statusList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${statusList}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<tr>
    					<td >患者索引号</td>
    					<td ><input type="text" name="LIKE_patientBaseInfo.patientId" style="width:140px"/></td>
    					<td >证件号码</td>
    					<td ><input type="text" name="Like_patientBaseInfo.certificateNo" style="width:140px"/></td>
    					<td></td>
    					<td></td>
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
		url:'${ctx}/card/manage/practicecard/query',
		toolbar:'#tb',
		fit:true,
		nowrap:true,
		pagination:true,
		rownumbers:true,
		striped:true,
		pageSize:20,
		view : detailview,
		detailFormatter : function(rowIndex, rowData) {
			return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
		},
		onExpandRow: function(rowIndex, rowData){
			$('#ddv-' + rowIndex).panel({
				border:false,
				cache:false,
				content: '<iframe src="${ctx}/card/manage/practicecardjournal/' + rowData.id + '/detail" frameborder="0" width="100%" height="300px" scrolling="auto"></iframe>',
				onLoad:function(){
					$('#tt').datagrid('fixDetailRowHeight',rowIndex);
				}
			});
			$('#tt').datagrid('fixDetailRowHeight',rowIndex);
		}
	}); 	
	$("form table tr").next("tr").hide();
	$('#tb-more').bind('click', function(){
	   	var showHideLabel_value = $('#showHideLabel').text();
		$('form table tr').next('tr').toggle();
	 	if (showHideLabel_value == '收缩'){
	 		$('#showHideLabel').text('更多...');
		}else{
			$('#showHideLabel').text('收缩');
		}
		$('#tt').datagrid('resize');
	});
	
	$('#tb-loss').bind('click', function(){
		var defaults = {
				baseUrl: '',
				src: 'loss',
				grid : 'datagrid',
				gridId : '#tt',
				confirm : '确定要挂失所选记录吗?',
				getId : function(row){
					return row.id;
				}
		};
		var opts = $.extend({}, defaults);
	    var rows = $(opts.gridId).datagrid('getSelections');
	    	
	   	if(rows.length == 0){
		  	$.messager.alert('提示','请选择要挂失的记录','info');
		  	return;
		}
		    
		var parameter='';
		var selectedError = false;
		$.each(rows,function(index,row){
			if(row.statusInfo != '正常'){
			  	selectedError = true;
			  	return false;
			}
		    parameter += 'selections=' + opts.getId(row) +'&';
		});
		
		if(selectedError){
		  	$.messager.alert('提示','请选择正常状态的诊疗卡','info');
		  	return;
		}
		    
		$.messager.confirm('提示', opts.confirm, function(r){
		     if (r){
		            $.post(opts.src, parameter ,function(data){
		            	if (data.success){
	    					$(opts.gridId).datagrid('clearSelections');
	    					$(opts.gridId).datagrid('reload');
		            	}
    					$.messager.alert('挂失', data.message, 'info');
		            });
		    }
		});
	});
	
	$('#tb-cancelloss').bind('click', function(){
		var defaults = {
				baseUrl: '',
				src: 'cancelloss',
				grid : 'datagrid',
				gridId : '#tt',
				confirm : '确定要取消挂失所选记录吗?',
				getId : function(row){
					return row.id;
				}
		};
		var opts = $.extend({}, defaults);
	    var rows = $(opts.gridId).datagrid('getSelections');
	    	
	   	if(rows.length == 0){
		  	$.messager.alert('提示','请选择要取消挂失的记录','info');
		  	return;
		}
		    
		var parameter='';
		var selectedError = false;
		$.each(rows,function(index,row){
			if(row.statusInfo != '挂失'){
			  	selectedError = true;
			  	return false;
			}
		    parameter += 'selections=' + opts.getId(row) +'&';
		});
		
		if(selectedError){
		  	$.messager.alert('提示','请选择挂失状态的诊疗卡','info');
		  	return;
		}
		
		$.messager.confirm('提示', opts.confirm, function(r){
		     if (r){
		            $.post(opts.src, parameter ,function(data){
		            	if (data.success){
	    					$(opts.gridId).datagrid('clearSelections');
	    					$(opts.gridId).datagrid('reload');
		            	}
    					$.messager.alert('取消挂失', data.message, 'info');
		            });
		    }
		});
	});
});
	
function formatHistory(val, row){
	return  '(<a href="javascript:void(0);" onclick="$.ewcms.openWindow({src:\'${ctx}/card/manage/practicecardhistory/' + row.id + '/detail\',width:550,height:350,title:\'查看\'});">详细</a>)';
}
</script>