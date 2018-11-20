<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="外部数据源 - 标签页"/>
	<div id="tab-externalds" class="easyui-tabs" data-options="fit:true,tabPosition:'right',border:false,headerWidth:70">
		<div title="JDBC" style="padding:2px;overflow:hidden;">
			<iframe id="editjdbcifr" name="editjdbcifr" class="editifr" src="${ctx}/system/externalds/jdbc/index"></iframe>
		</div>			
		<div title="JNDI" style="padding:2px;overflow:hidden;">
			<iframe id="editjndiifr"  name="editjndiifr" class="editifr" src="${ctx}/system/externalds/jndi/index"></iframe>	
		</div>
		<div title="BEAN" style="padding:2px;overflow:hidden;">
			<iframe id="editbeanifr"  name="editbeanifr" class="editifr" src="${ctx}/system/externalds/bean/index"></iframe>	
		</div>					
		<div title="CUSTOM" style="padding:2px;overflow:hidden;">
			<iframe id="editcustomifr"  name="editcustomifr" class="editifr" src="${ctx}/system/externalds/custom/index"></iframe> 
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	function isConnect(id){
		$.post('${ctx}/system/externalds/isConnect/' + id , {}, function(result) {
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>