<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="诊疗卡管理"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'practiceNo',width:100">诊疗卡号</th>
			    <th data-options="field:'createDate',width:150">上传时间</th>
			    <th data-options="field:'patientId',width:150">唯一索引号</th>
			    <th data-options="field:'patientBaseInfo.name',width:80,
						formatter:function(val,row){
							return row.patientBaseInfo.name;
						}">患者姓名</th>
						
				<th data-options="field:'patientBaseInfo.telephone',width:130,
						formatter:function(val,row){
							return row.patientBaseInfo.telephone;
						}">电话号码</th>
			    <th data-options="field:'patientBaseInfo.certificateNo',width:130,
						formatter:function(val,row){
							return row.patientBaseInfo.certificateNo;
						}">证件号码</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="tb-distribute" onclick="$.ewcms.add({title:'发诊疗卡',width:700,height:380,src:'${ctx}/empi/card/manage/practicecard/distribute'});" href="javascript:void(0);">发诊疗卡</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="tb-distribute" onclick="$.ewcms.add({title:'自动合并',width:400,height:300,src:'${ctx}/empi/card/manage/practicecard/autocombine'});" href="javascript:void(0);">自动合并</a>
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              		    <td >证件号码</td>
    					<td ><input type="text" name="Like_patientBaseInfo.certificateNo" style="width:140px"/></td>
              			<td>诊疗卡号</td>
              			<td><input type="text" name="LIKE_practiceNo" style="width:140px"/></td>
    					<td >姓名</td>
    					<td ><input type="text" name="LIKE_patientBaseInfo.name" style="width:140px"/></td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<tr>
    					<td >发卡日期从</td>
    					<td ><input type="text" name="GTE_createDate" class="easyui-datetimebox" style="width:150px" data-options="editable:false"/> 至 <input type="text" name="LTE_createDate" class="easyui-datetimebox" style="width:150px" data-options="editable:false"/></td>
           				<td></td>
    					<td></td>
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
		url:'${ctx}/empi/card/manage/practicecard/query',
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
				content: '<iframe src="${ctx}/empi/card/manage/practicecardindexhistory/' + rowData.practiceNo + '/detail" frameborder="0" width="100%" height="200px" scrolling="auto"></iframe>',
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
});
</script>