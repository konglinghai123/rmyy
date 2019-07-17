<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="药品评审 - 标签页"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<c:if test="${!istbEnable}">
		<div data-options="region:'north'" style="text-align:center;height:40px;border:0">
			<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="10%">评审名称</td>
              			<td width="70%">
		           			<form:select  id="EQ_reviewMainId" name="EQ_reviewMainId" path="reviewMainList" cssClass="easyui-combobox"  cssStyle="width:160px;" data-options="panelHeight:'auto',editable:false">
								<form:option value="-1" label="--------请选择--------"/>
								<form:options items="${reviewMainList}" itemValue="id" itemLabel="name"/>
							</form:select>
						</td>
              			<td width="20%">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清除</a>
           				</td>
           			</tr>
           		</table>
           		<input type="hidden" id="reviewMainId"/>
          	</form>
		</div>
		</c:if>
		<div data-options="region:'center',border:false">
			 <div id="tab-voterecord" class="easyui-tabs" data-options="fit:true,tabPosition:'top',border:true,headerWidth:120">
			 	<c:if test="${istbEnable}">
				<div title="最终结果" data-options="" style="padding:2px;overflow:hidden;">
					<iframe id="ifr-last" name="ifr-last" class="editifr" src="${ctx}/yjk/re/drugvote/last"></iframe>
				</div>
				</c:if>
			 	<c:forEach items="${reviewProcessesList}" var="reviewProcess" varStatus="status">
				<div title="${reviewProcess.reviewBaseRule.ruleCnName}" data-options="tools:'#ptools-${reviewProcess.id}'" style="padding:2px;overflow:hidden;">
					<iframe id="ifr-${reviewProcess.id}" name="ifr-${reviewProcess.id}" class="editifr" src="${ctx}/yjk/re/drugvote/${reviewProcess.id}/record"></iframe>
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
	<c:if test="${!istbEnable}">
	$('#tb-query').bind('click', function(){
		$('#reviewMainId').val($('#EQ_reviewMainId').combobox('getValue'));
		if ($('#reviewMainId').val() != -1){
			$.ajax({
				 async:false,
				 type:'POST',
				 url:'${ctx}/yjk/re/drugvote/addTabs?reviewMainId=' + $('#reviewMainId').val(),
				 dataType : 'json',
				 success:function(data) {
					 tabsClose();
					 $.ewcms.openTab({id:'#tab-voterecord',title:'最终结果',src:'${ctx}/yjk/re/drugvote/last?reviewMainId=' + $('#reviewMainId').val(),closable:false});
					 $.map(data, function(row){
						 var src = '${ctx}/yjk/re/drugvote/' + row['id'] + '/record?reviewMainId=' + $('#reviewMainId').val();
						 var title = row['reviewBaseRule'].ruleCnName;
						 $.ewcms.openTab({id:'#tab-voterecord',title:title,src:src,closable:false});
					 });
					 tabsSelect();
				 }
			});
		}
	});
	$('#tb-clear').bind('click', function(){
		tabsClose();
		$('#reviewMainId').val(-1);
		$('#queryform').form('reset');
	});
	function tabsClose(){
		 $('.tabs-inner span').each(function(i,n){
			 var t = $(n).text();
		     $('#tab-voterecord').tabs('close',t);
		 });
	}
	function tabsSelect(){
		$('.tabs-inner span').each(function(i,n){
		     $('#tab-voterecord').tabs('select',0);
		     return;
		 });
	}
	</c:if>
</script>
