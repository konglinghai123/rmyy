<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="诊疗卡合并"/>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" style="height:70%;">
		<table id="tt">
			<thead>
				<tr>
				    <th data-options="field:'ck',checkbox:true"></th>
				    <th data-options="field:'id',hidden:true">编号</th>
				    <th data-options="field:'practiceNo',width:100">诊疗卡号</th>
				    <th data-options="field:'createDate',width:150">发卡时间</th>
				    <th data-options="field:'balance',width:80">余额</th>
				    <th data-options="field:'statusInfo',width:80">诊疗卡状态</th>
				    <th data-options="field:'depositInfo',width:80">是否收取押金</th>
				    <th data-options="field:'deposit',width:80">押金金额</th>
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
				</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto;">
			<div style="padding-left:5px;">
	        	<form id="queryform" style="padding:0;margin:0;">
	        		<table class="formtable">
	              		<tr>
	    					<td >证件号码</td>
	    					<td ><input type="text" name="Like_patientBaseInfo.certificateNo" style="width:140px"/></td>              		
	    					<td >姓名</td>
	    					<td ><input type="text" name="LIKE_patientBaseInfo.name" style="width:140px"/></td>
							<td width="16%" colspan="2">
	            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
	           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="$('#queryform').form('reset');">清除</a>
	           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多...</span></a>
	           				</td>
	           			</tr>
	           			<tr>
	              			<td>诊疗卡号</td>
	              			<td><input type="text" name="LIKE_practiceNo" style="width:140px"/></td>
	           				<td>状态</td>
	    					<td>
	    						<form:select id="status" name="EQ_status" path="statusList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
						  			<form:option value="" label="------请选择------"/>
						  			<form:options items="${statusList}" itemLabel="info"/>
								</form:select>
							</td>
	           			</tr>           			
	           		</table>
	        	</form>
	        </div>
		</div>
	</div>
	<div data-options="region:'center'" style="height:30%;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',fit:true">
				<table>
				  <tr>
				    <td width="100">合并后诊疗卡号：</td>
				    <td><input type="text" name="combinePracticeNo" id="combinePracticeNo" style="width:140px"/></td>
				  </tr>
				</table>
	
				<table id="tt1">
					<thead>
						<tr>
						    <th data-options="field:'id',hidden:true">编号</th>
						    <th data-options="field:'practiceNo',width:100">诊疗卡号</th>
						    <th data-options="field:'createDate',width:150">发卡时间</th>
						    <th data-options="field:'balance',width:80">余额</th>
						    <th data-options="field:'statusInfo',width:80">诊疗卡状态</th>
						    <th data-options="field:'depositInfo',width:80">是否收取押金</th>
						    <th data-options="field:'deposit',width:80">押金金额</th>
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
						</tr>
					</thead>
				</table>	
			</div>
			<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
				<table style="width:100%">
					<tr>
						<td style="text-align: left;background-color:gray;width:30%;"><span id="combineResult" style="color:red;"></span></td>
						<td style="text-align: right">
							<a class="easyui-linkbutton" id="tb-combine" data-options="iconCls:'icon-undo'" href="javascript:void(0);">确认合并</a>
							<a class="easyui-linkbutton" id="tb-clearall" data-options="iconCls:'icon-undo'" href="javascript:void(0);">清屏</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
$(function(){
	$('#tb-clearall').bind('click', function(){
		$('#tt').datagrid('clearSelections');
		$('#tt').datagrid('loadData',{total:0,rows:[]}); 
		$('#queryform').form('reset');
		$('#tt1').datagrid('clearSelections');
		$('#tt1').datagrid('loadData',{total:0,rows:[]}); 
		$("#combineResult").html('');
		$("#combinePracticeNo").val('');
	});
	
	$('#tt').datagrid({
		url:'${ctx}/empi/card/manage/practicecard/query',
		toolbar:'#tb',
		nowrap:true,
		rownumbers:true,
		striped:true,
		fit:true,
		rowStyler: function(index, row){
    		if (row.statusInfo == '作废'){
    			return 'background-color:#ffb5b5;color:#000000;';
    		} else if (row.statusInfo == '挂失'){
    			return 'background-color:#ffd9ec;color:#000000;';
    		}
    	}
	}); 
	
	$('#tt1').datagrid({
		nowrap:true,
		rownumbers:true,
		striped:true,
		height:100
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
	
	$('#tb-combine').bind('click', function(){
		var defaults = {
				baseUrl: '',
				src: 'combine',
				grid : 'datagrid',
				gridId : '#tt',
				confirm : '确定要合并所选记录吗?',
				getId : function(row){
					return row.id;
				}
		};
		var opts = $.extend({}, defaults);
	    var rows = $(opts.gridId).datagrid('getSelections');
	    	
	   	if(rows.length == 0){
		  	$.messager.alert('提示','请选择要合并的记录','info');
		  	return;
		}
		var practiceNo = $("#combinePracticeNo").val();
		if(practiceNo == ""){
		  	$.messager.alert('提示','合并后的诊疗卡号不能为空','info');
		  	return;
		}
		
		var parameter='';
		var selectedError = false;
		$.each(rows,function(index,row){
			if(row.statusInfo == '作废'){
			  	selectedError = true;
			  	return false;
			}
		    parameter += 'selections=' + opts.getId(row) +'&';
		});

		parameter += 'practiceNo=' + practiceNo;
		if(selectedError){
		  	$.messager.alert('提示','请选择非作废状态的诊疗卡合并','info');
		  	return;
		}
		    
		$.messager.confirm('提示', opts.confirm, function(r){
		     if (r){
		            $.post(opts.src, parameter ,function(data){
		            	if (data.success){
	    					$("#tt1").datagrid({url:'${ctx}/empi/card/manage/practicecard/' + data.combineCardId + '/querycombine'});
	    					$("#tt1").datagrid('reload');
	    					$("#combineResult").html('回收诊疗卡：' + data.backCard +'张,共需退还病人押金：' + data.reDeposit + '元');
		            	}
		            	$.messager.alert('合并',data.message, 'info');
		            });
		    }
		});
	});
});
</script>