<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="选择 - 提交申报"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
			<table id="tt">
				<thead>
					<tr>
					    <th data-options="field:'ck',checkbox:true"/>
					    <th data-options="field:'id',hidden:true">id</th>
					    <th data-options="field:'formatId',width:100">编号</th>
						<c:forEach items="${commonNameRuleList}" var="commonNameRule">
							<c:choose>
								<c:when test="${commonNameRule.ruleName == 'common.administration.id'}">
									<th data-options="field:'name',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.common.administration.name;
											}">${commonNameRule.ruleCnName}</th>
								</c:when>
								<c:when test="${commonNameRule.ruleName == 'common.drugCategory'}">
									<th data-options="field:'drugCategory',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.common.drugCategoryInfo;
											}">${commonNameRule.ruleCnName}</th>
								</c:when>								
								<c:otherwise>
									<th data-options="field:'${commonNameRule.ruleName}',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.${commonNameRule.ruleName};
											}">${commonNameRule.ruleCnName}</th>
								</c:otherwise>
							</c:choose>			
						</c:forEach>
						<th data-options="field:'auditStatusInfo',width:120">审核状态</th>	
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a id="tb-declare" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:saveDeclareSubmit();">提交申报</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/sb/drugform/querydeclaresubmit',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false
		});
	});
	
	function saveDeclareSubmit(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择要申报的记录','info');
	        return;
	    }
    	
	    var parameter='';
	    $.each(rows,function(index,row){
	    	parameter += 'selections=' + row.id +'&';
	    });
	    
	    $.messager.confirm('提示', '确定要申报所选记录吗', function(r){
	        if (r){
	            $.post('${ctx}/yjk/sb/drugform/savedeclaresubmit', parameter ,function(data){
	            	if(data.success){
	            		alert(data.message+'\n请及时打印申报好的新药纸质申请表，并签字、盖章送至药学部（住培楼5楼）初审!');
	            		parent.$('#edit-window').window('close');
	            	}
	            	$.messager.alert('提示', data.message, 'info');
	            });
	        }
	    });
	}
</script>
	