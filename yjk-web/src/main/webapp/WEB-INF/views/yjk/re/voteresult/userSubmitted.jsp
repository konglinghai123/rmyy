<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>
<ewcms:head title="已投用户"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'operation',width:100,align:'center',formatter:formatOperation">操作</th>
			    <th data-options="field:'id',
						formatter:function(val,row){
							return row.user.id;
						}">编号</th>
				<th data-options="field:'username',width:80,
						formatter:function(val,row){
							return row.user.username;
						}">用户名</th>
				<th data-options="field:'realname',width:100,
						formatter:function(val,row){
							return row.user.realname;
						}">姓名</th>
				<th data-options="field:'sex',width:60,
						formatter:function(val,row){
							return row.user.sexDescription;
						}">性别</th>
				<th data-options="field:'mobilePhoneNumber',width:100,
						formatter:function(val,row){
							return row.user.mobilePhoneNumber;
						}">手机号</th>
				<th data-options="field:'user.organizationNames',
						formatter:function(val,row){
							return row.user.organizationNames;
						}">科室名称</th>	
				<th data-options="field:'departmentAttribute',width:100,
						formatter:function(val,row){
							return row.user.departmentAttribute != null ? row.user.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,
						formatter:function(val,row){
							return row.user.profession != null ? row.user.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:120,
						formatter:function(val,row){
							return row.user.technicalTitle != null ? row.user.technicalTitle.name : '';
						}">技术职称(资格)</th>
				<th data-options="field:'appointment',width:100,
						formatter:function(val,row){
							return row.user.appointment != null ? row.user.appointment.name : '';
						}">聘任</th>
				<th data-options="field:'appointment',width:100,
						formatter:function(val,row){
							return row.user.appointment != null ? row.user.appointment.name : '';
						}">聘任</th>
				<th data-options="field:'signed',width:60,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">已签字</th>
			</tr>
		</thead>
	</table>
	<table id="ttPrint">
		<thead>   		
			<tr>
			    <th data-options="field:'voteTypeInfo',width:80">投票操作</th>
 				<c:forEach items="${currentReviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${currentReviewProcess.reviewBaseRule.ruleName == acn||currentReviewProcess.reviewBaseRule.ruleName == asap}">
							<th data-options="field:'${displayColumn.ruleName}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm==null && row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return row.${displayColumn.ruleName};
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:when>
						<c:otherwise>
							<th data-options="field:'${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.commonNameContents==null){
											 	return '';
											}else{
												return row.${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))};
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:otherwise>
					</c:choose>
 				</c:forEach>
			</tr>
		</thead> 
	</table>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	var caption = '';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voteresult/queryUserSubmitted',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			border:false,
			singleSelect:true,
			rowStyler: function(index,row){
	        	if (!row.signed){
	    			return 'background-color:#C4E1FF;color:#000000;';
	        	}
	    	},
			onLoadSuccess:function(row){
				$('.printCls').linkbutton({plain:true,iconCls:'icon-print'});
				$('.signCls').linkbutton({plain:true,iconCls:'icon-sign'});
			}
		});
	});
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		htmlOperation += '<a class="printCls" onclick="print(' + row.user.id + ',\'' + row.user.realname + '\');" href="javascript:void(0);" style="height:24px;" title="打印"/> ';
		if (!row.signed){
			htmlOperation += '|  <a class="signCls" onclick="sign(' + row.user.id + ');" href="javascript:void(0);" style="height:24px;" title="签字确认"/> ';
		}
		return htmlOperation;
	}
	
	function print(id, realname){
		$('#ttPrint').datagrid({
			url:'${ctx}/yjk/re/voterecord/' + id + '/${currentReviewProcess.id}/query',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			onLoadSuccess:function(row){
				caption='用户：' + realname + ' 在 ${currentReviewProcess.reviewBaseRule.ruleCnName} 中的投票结果明细';
				$(this).datagrid('toExcel','userSubmitted.xls');
			}
		});
	}
	
	function sign(id){
		$.messager.confirm('提示', '确定已签字确认了吗？', function(r){
			if (r){
				$.post('${ctx}/yjk/re/voteresult/' + id + '/sign', {}, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	}
</script>
