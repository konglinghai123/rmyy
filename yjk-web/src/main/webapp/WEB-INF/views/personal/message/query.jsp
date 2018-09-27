<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="${state.info}"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',width:60">编号</th>
			    <th data-options="field:'title',width:400,formatter:formatterTitle">标题</th>
			    <c:if test="${state ne 'out_box'}">
			    	<th data-options="field:'senderUsername',width:100">发件人</th>
			    </c:if>
			    <c:if test="${state ne 'in_box'}">
			    	<th data-options="field:'receiverUsername',width:100">收件人</th>
			    </c:if>
			    <th data-options="field:'sendDate',width:145">发送时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-message" class="easyui-linkbutton" data-options="iconCls:'icon-subscription-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/personal/message/send',width:800,height:450});" href="javascript:void(0);">发送新信息</a>
			<c:if test="${state eq 'in_box'}">
			<a id="tb-markread" class="easyui-linkbutton" data-options="iconCls:'icon-markread',plain:true"  href="javascript:void(0);">标记为已读</a>
			</c:if>
			<c:if test="${state ne 'draft_box' and state ne 'store_box' and state ne 'trash_box'}">
			<a id="tb-store" class="easyui-linkbutton" data-options="iconCls:'',plain:true"  href="javascript:void(0);">收藏</a>
			</c:if>
			<c:if test="${state eq 'trash_box'}">
			<a id="tb-restore" class="easyui-linkbutton" data-options="iconCls:'',plain:true"  href="javascript:void(0);">恢复到收藏箱</a>
			</c:if>
			<a id="tb-remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
			<a id="tb-clear" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">清空${state.info}</a>
		</div>
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
                		<td width="6%">标题：</td>
                		<td width="19%"><input type="text" name="LIKE_title" style="width:120px;"/></td>
                		<td width="6%">类型：</td>
                		<td width="19%"></td>
                		<td width="6%">发送时间：</td>
                		<td width="19%"><input type="text" id="sendDateStart" name="GTE_sendDate" class="easyui-datebox" data-options="editable:false" style="width:100px"/>&nbsp;至&nbsp;<input type="text" id="sendDateEnd" name="LTE_sendDate" class="easyui-datebox" data-options="editable:false" style="width:100px"/></td>
						<td width="25%" colspan="2">
            				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();" href="javascript:void(0);">查询</a>
           					<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');" href="javascript:void(0);">清除</a>
                		</td>
              		</tr>
            	</table>
           	</form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/personal/message/${state}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			singleSelect:false,
			striped:true,
			pageSize:20
		});
	});
	
	function formatterTitle(val, row){
		<c:choose>
			<c:when test="${state eq 'draft_box'}">
				return '<a href="${ctx}/personal/message/draft/' + row.id + '/send">' + val + '</a>';
			</c:when>
			<c:otherwise>
				var html = '<a href="${ctx}/personal/message/' + row.id + '">' + val + '</a>';
				if (!row.read){
					html += '<span>未读</span>';
				}
				return html;
			</c:otherwise>
		</c:choose>
	}
</script>