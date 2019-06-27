<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审监控 - 标签页"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'north'" style="text-align:center;height:30px;border:0">
			<b>评审流程：</b>
	  		<c:forEach items="${reviewProcessesList}" var="reviewProcess" varStatus="status">
				<c:choose>
					<c:when test="${reviewProcess.reviewBaseRule.ruleName == currentReviewProcess.reviewBaseRule.ruleName}">
						<font color="green">${reviewProcess.reviewBaseRule.ruleCnName}</font>
					</c:when>
					<c:otherwise>
						${reviewProcess.reviewBaseRule.ruleCnName}
					</c:otherwise>
				</c:choose>
				<c:if test="${!status.last}">
	    	   			<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">
	    	   	</c:if>	
			</c:forEach>
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
					<iframe id="editresultifr"  name="editpresentillnessifr" class="editifr" src=""></iframe>	
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
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
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
	});
</script>
