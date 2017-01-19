<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="诊疗卡流水帐查询"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/empi/card/manage/practicecardjournal/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'practiceNo',width:130">诊疗卡号</th>			    
			    <th data-options="field:'journalDate',width:150">操作日期</th>
			    <th data-options="field:'journalOperateInfo',width:80">流水操作</th>
			    <th data-options="field:'journalSum',width:80">金额</th>
			    <th data-options="field:'operateUserName',width:80">操作员</th>
			    <th data-options="field:'remark',width:80">说明</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td>诊疗卡号</td>
              			<td><input type="text" name="LIKE_practiceCard.practiceNo" style="width:140px"/></td>
    					<td >操作日期从</td>
    					<td ><input type="text" name="GTE_journalDate" class="easyui-datetimebox" style="width:100px" data-options="editable:false"/> 至 <input type="text" name="LTE_journalDate" class="easyui-datetimebox" style="width:100px" data-options="editable:false"/></td>
           				<td>流水操作</td>
    					<td>
    						<form:select  name="EQ_journalOperate" path="journalOperateList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${journalOperateList}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<tr>
    					<td >操作员</td>
    					<td ><input type="text" name="LIKE_operateUserName" style="width:140px"/></td>
    					<td ></td>
    					<td ></td>
    					<td></td>
    					<td></td>
           			</tr> 
           		</table>
        	</form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
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
</script>