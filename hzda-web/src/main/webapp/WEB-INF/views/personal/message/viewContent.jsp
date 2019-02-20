<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="查看 - 消息内容"/>
	<div id="view-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">	
		  	<table class="formtable">
		        <tr>
				  	<td width="100%">内容：</td>
			    </tr>
			    <tr>
	             	<td>${m.content.content}</td>
			    </tr>
			</table>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
			<c:if test="${m.senderId ne user.id}">
			<a id="tb-store" class="easyui-linkbutton" data-options="iconCls:'',plain:true"  href="${ctx}/personal/message/${m.id}/reply">回复</a>
			<a id="tb-store" class="easyui-linkbutton" data-options="iconCls:'',plain:true"  href="${ctx}/personal/message/${m.id}/forward">转发</a>
			</c:if>
			<a id="tb-store" class="easyui-linkbutton" data-options="iconCls:'',plain:true"  href="javascript:void(0);" >收藏</a>
			<c:if test="${(m.senderId eq user.id and m.senderState ne 'trash_box') or (m.receiverId eq user.id and m.receiverState ne 'trash_box') }">
			<a id="tb-remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
			</c:if>
			<c:if test="${(m.senderId eq user.id and m.senderState eq 'trash_box') or (m.receiverId eq user.id and m.receiverState eq  'trash_box') }">
			<a id="tb-remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
			</c:if>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
</script>