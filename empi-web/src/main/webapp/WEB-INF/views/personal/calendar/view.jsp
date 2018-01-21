<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="查看 - 提醒事项"/>
	<div id="view-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">	
		  	<table class="formtable">
		        <tr>
				  	<td width="20%">标题：</td>
				  	<td width="80%"><div style="float: left;" title="${calendar.title}">${calendar.title}</div></td>
			    </tr>
			    <tr>
	             	<td>开始日期：</td>
			  		<td>
			  			<div style="float: left;">
            				<fmt:formatDate value="${calendar.startDate}" pattern="yyyy-MM-dd"/>
            				<c:if test="${not empty calendar.startTime}">
                			<fmt:formatDate value="${calendar.startTime}" pattern="HH:mm:ss"/>
            				</c:if>
						</div>
					</td>
			    </tr>
			    <tr>
			    	<td>结束时间：</td>
			    	<td>
			    		<div style="float: left;">
            				<fmt:formatDate value="${calendar.endDate}" pattern="yyyy-MM-dd"/>
            				<c:if test="${not empty calendar.endTime}">
                			<fmt:formatDate value="${calendar.endTime}" pattern="HH:mm:ss"/>
            				</c:if>
            				<c:if test="${empty calenadr.startTime and empty calendar.endTime}">
                			（全天提醒事项）
            				</c:if>
						</div>
			    	</td>
			    </tr>
			    <tr>
			    	<td>备注：</td>
			    	<td>
			    		<div style="float: left;">
            				<textarea>${calendar.details}</textarea>
						</div>
					</td>
			    </tr>
			</table>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
			<a id="tb-delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" href="javascript:void(0);" data-id="${calendar.id}">删除提醒事项</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tb-delete').bind('click', function(){
			$.messager.confirm('提示', '确认删除提醒事项吗？', function(r){
            	if (r){
            		var url = '${ctx}/personal/calendar/delete?id=${calendar.id}';
					$.post(url, function(data){
						if (data.success){
							parent.calendar.fullCalendar("refetchEvents");
							parent.$('#edit-window').window('close');
						}
					});
            	}
			});
		});
	});
</script>