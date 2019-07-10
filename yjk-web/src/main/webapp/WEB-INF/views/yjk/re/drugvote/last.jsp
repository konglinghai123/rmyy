<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>
<c:choose>
<c:when test="${isLast}">
<ewcms:head title="最终结果"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'passSum',width:60">通过票</th>
				<th data-options="field:'opposeSum',width:60">反对票</th>
				<th data-options="field:'abstainSum',width:60">弃权票</th>
				<th data-options="field:'selected',width:80,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否入围</th>
			    <c:forEach items="${currentReviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${currentReviewProcess.reviewBaseRule.ruleName == acn||currentReviewProcess.reviewBaseRule.ruleName == asap}">
							<th data-options="field:'${displayColumn.ruleName}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return formatTooltip(row.${displayColumn.ruleName}, row);
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:when>
						<c:otherwise>
							<th data-options="field:'${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return formatTooltip(row.${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}, row);
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:otherwise>
					</c:choose>
 				</c:forEach>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-print" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">打印</a>
		</div>
		<div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">显示情况</td>
              			<td width="23%">
              				<select class="easyui-combobox" name="CUSTOM_show" data-options="editable:false,width:100,panelHeight:'auto'">
              					<option value="all" selected="selected">全部</option>
              					<option value="selected">入围</option>
              				</select>
              			</td>
            			<td width="5%"></td>
              			<td width="23%"></td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          	</form>
    	</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	var caption = '最终入围结果统计';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/drugvote/queryLastVoteResult?reviewMainId=${reviewMainId}',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			rowStyler: function(index,row){
				if (row.selected){
					if( row.drugForm.commonNameContents.common.drugCategory=='Z'){
						return 'background-color:#DDDDFF;color:#000000;';
					}else{
						return 'background-color:#C4E1FF;color:#000000;';
					}
	    			
	        	}
	    	}
		});
	});
	
	$('#tb-print').bind('click', function(){
		$('#tt').datagrid('toExcel','lastVoteResult.xls');
	});
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
</script>
</c:when>
<c:otherwise>
<ewcms:head title="药品投票情况 - 最终结果"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',fit:true" style="border:0;">	
			<h1 class="title">所有流程还未结束，不能查看最终结果！</h1>
		</div>
	</div>
<ewcms:footer/>
</c:otherwise>
</c:choose>