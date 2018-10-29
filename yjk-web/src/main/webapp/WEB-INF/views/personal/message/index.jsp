<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="个人中心 - 消息"/>
	<div id="tab-message" class="easyui-tabs" data-options="border:false,fit:true,tabPosition:'right',headerWidth:75">
		<c:forEach items="${states}" var="s">
            <c:if test="${s ne 'delete_box'}">
            	<div id="${s}" title="${s.info}" style="overflow:hidden;">
            		<iframe id="iframe-${s}" name="iframe-${s}" class="editifr" src="${ctx}/personal/message/${s}/index"></iframe>
            	</div>
            </c:if>
		</c:forEach>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tab-message').tabs({
			onSelect:function(title, index){
				var pp = $('#tab-message').tabs('getTab',index);
                var id = pp.panel('options').id;
                $('#iframe-' + id).attr('src', '${ctx}/personal/message/' + id + '/index');
			}
		});
	});
</script>