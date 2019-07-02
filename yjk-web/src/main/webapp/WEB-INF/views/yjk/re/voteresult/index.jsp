<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审监控 - 标签页"/>
	<c:choose>
	<c:when test="${isOpenReview}">
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'north'" style="text-align:center;height:30px;border:0;overflow:hidden;">
			<iframe id="ifrprocess" name="ifrprocess" class="editifr" src="${ctx}/yjk/re/voteresult/process"></iframe>
		</div>	
		<div data-options="region:'center',border:false">
			 <div id="tab-voteresult" class="easyui-tabs" data-options="fit:true,tabPosition:'top',border:true,headerWidth:120">
				<div title="未投用户" data-options="tools:'#p0-tools'" style="padding:2px;overflow:hidden;">
					<iframe id="editnotsubmitifr" name="editcomplainedifr" class="editifr" src="${ctx}/yjk/re/voteresult/userNoSubmitted"></iframe>
				</div>			
				<div title="已投用户" data-options="tools:'#p1-tools'" style="padding:2px;overflow:hidden;">
					<iframe id="editsubmitifr"  name="editriskevaluationifr" class="editifr" src=""></iframe>	
				</div>
				<div title="投票结果" data-options="tools:'#p2-tools'" style="padding:2px;overflow:hidden;">
					<iframe id="editresultifr" name="editpresentillnessifr" class="editifr" src=""></iframe>	
				</div>
			</div>
		</div>
		<div id="p0-tools">
			<a href="javascript:void(0)" class="icon-reload" onclick="$('#editnotsubmitifr')[0].contentWindow.$('#tt').datagrid('reload');"></a>
		</div>
		<div id="p1-tools">
			<a href="javascript:void(0)" class="icon-reload" onclick="$('#editsubmitifr')[0].contentWindow.$('#tt').datagrid('reload');"></a>
		</div>
		<div id="p2-tools">
			<a href="javascript:void(0)" class="icon-reload" onclick="$('#editresultifr')[0].contentWindow.$('#tt').datagrid('reload');"></a>
		</div>
	</div>
	</c:when>
	<c:otherwise>
		<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
			<div data-options="region:'center',fit:true" style="border:0;">	
				<h1 class="title">评审监控必须在评审流程开启时才能使用！</h1>
			</div>
		</div>
	</c:otherwise>
	</c:choose>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		<c:if test="${isOpenReview}">
		$('#tab-voteresult').tabs({
			onSelect:function(title){
				if (title == '未投用户'){
					$('#editnotsubmitifr').attr('src', '${ctx}/yjk/re/voteresult/userNoSubmitted');
				} else if (title == '已投用户'){
					$('#editsubmitifr').attr('src','${ctx}/yjk/re/voteresult/userSubmitted');
				} else if (title == '投票结果'){
					$('#editresultifr').attr('src','${ctx}/yjk/re/voteresult/voteResult');
				}
			}
		});
		</c:if>
	});
</script>
