<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="药品评审 - 标签页"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">
			 <div id="tab-voterecord" class="easyui-tabs" data-options="fit:true,tabPosition:'top',border:true,headerWidth:120">
			 	<c:forEach items="${reviewProcessesList}" var="reviewProcess" varStatus="status">
					<div title="${reviewProcess.reviewBaseRule.ruleCnName}" data-options="tools:'#ptools-${reviewProcess.id}'" style="padding:2px;overflow:hidden;">
						<iframe id="ifr-${reviewProcess.id}" name="editcomplainedifr" class="editifr" src="${ctx}/yjk/re/drugvote/${reviewProcess.id}/record"></iframe>
						<div id="ptools-${reviewProcess.id}">
							<a href="javascript:void(0)" class="icon-reload" onclick="$('#ifr-${reviewProcess.id}')[0].contentWindow.$('#tt').datagrid('reload');"></a>
							<a href="javascript:void(0)" class="icon-print" onclick="$('#ifr-${reviewProcess.id}')[0].contentWindow.print();"></a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
</script>
