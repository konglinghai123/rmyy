<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="自动合并诊疗卡"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,bordar:false">
		<c:choose>
			<c:when test="${empty(matchFields)}">
				未设置匹配规则，请先设置好匹配规则
			</c:when>
			<c:otherwise>
				<div data-options="region:'center',border:false">	
				 	<h2>自动匹配规则如下：</h2><br><br>
				 	${matchFields}
		    	</div>
				<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
			  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="tb-autocombine" href="javascript:void(0);" >自动合并</a>
			  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
				</div>
			</c:otherwise>
		</c:choose>

	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		
		$('#tb-autocombine').bind('click', function(){
			var defaults = {
					baseUrl: '',
					src: 'autocombine',
					confirm : '确定要根据当前匹配字段自动合并诊疗卡吗?',
			};
			var opts = $.extend({}, defaults);
			
			$.messager.confirm('提示', opts.confirm, function(r){
			     if (r){
			            $.post(opts.src, '' ,function(data){
			            	$.messager.alert('自动合并', '合并诊疗卡操作在后台运行', 'info');
			            	if (data.success){
			            	}
	    					
			            });
			    }
			});
		});
	});

</script>