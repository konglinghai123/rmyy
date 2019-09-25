<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>

<c:choose>
	<c:when test="${isOpenReview}">
		<c:choose>
			<c:when test="${user.admin}">
				<ewcms:head title="评审 - 药品评审无权限"/>
				<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
					<div data-options="region:'center',fit:true" style="border:0;">	
						<h1 class="title">新药评审已开启，但管理员权限的用户不能进行新药评审，如需评审请切换为有权限评审的普通用户！</h1>
					</div>
				</div>
				<ewcms:footer/>
			</c:when>
			<c:otherwise>
				<c:choose>
				<c:when test="${!isReivewProcess}">
				<ewcms:head title="评审 - 药品评审已结束"/>
				<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
					<div data-options="region:'center',fit:true" style="border:0;">	
						<h1 class="title">新药评审已经结束了！</h1>
					</div>
				</div>
				<ewcms:footer/>
				</c:when>
				<c:otherwise>
				<ewcms:head title="专家评审投票"/>
				<table id="tt">
					<thead data-options="frozen:true">    
						<tr>   
						<c:choose>
						<c:when test="${!isExpertSubmitCurrentReview}">				         
							<th data-options="field:'voteTypeInfo',width:80,
								            editor:{
								                  type:'combobox',
								                  options:{
								                      valueField:'value',
								                      textField:'text',
								                      panelHeight:100,
								                      data:[{ 'value': 'pass', 'text': '通过' }, { 'value': 'oppose', 'text': '反对' }, { 'value': 'abstain', 'text': '弃权' }],
								                      required:true
								                   }
								             }">投票操作</th>
						</c:when>
						<c:otherwise>
							<th data-options="field:'voteTypeInfo',width:80">投票操作</th>				
						</c:otherwise>
						</c:choose>	
						<c:if test="${currentReviewProcess.hospitalData}">	
							<th data-options="field:'drugForm.commonNameContents.id',width:70,formatter:function(val,row){
													if(row.drugForm.commonNameContents==null){
													 	return '';
													}else{
														return formatOperation(row.drugForm.commonNameContents.id, row);
													}
												}">院用查看</th>
						</c:if>
						</tr>    
					</thead> 
					<thead>   		
						<tr>
						    <th data-options="field:'id',hidden:true">编号</th>
			 				<c:forEach items="${currentReviewProcess.displayColumns}" var="displayColumn" varStatus="status">
			 					<c:choose>
				 					<c:when test="${currentReviewProcess.reviewBaseRule.ruleName == acn||currentReviewProcess.reviewBaseRule.ruleName == asap}">
										<th data-options="field:'${displayColumn.ruleName}',width:${displayColumn.width},
												formatter:function(val,row){
													if(row.drugForm.commonNameContents==null){
													 	return '';
													}else{
														return formatTooltip(row.${displayColumn.ruleName}, row);
													}
												}">${displayColumn.ruleCnName}</th>  						
									</c:when>
									<c:otherwise>
										<th data-options="field:'${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}',width:${displayColumn.width},
												formatter:function(val,row){
													if(row.commonNameContents==null){
													 	return '';
													}else{
														return formatTooltip(row.${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}, row);
													}
												}">${displayColumn.ruleCnName}</th>  						
									</c:otherwise>
								</c:choose>
			 				</c:forEach>
						</tr>
					</thead> 
				</table>
				<div id="tb" style="padding:5px;height:auto;">
			        <div class="toolbar" style="margin-bottom:2px">
						投票流程：
						<c:forEach items="${reviewProcessesList}" var="reviewProcess" varStatus="status">
							<c:choose>
								<c:when test="${reviewProcess.reviewBaseRule.ruleName == currentReviewProcess.reviewBaseRule.ruleName}">
								<font color="red" style="font-size:14px;"><b>${reviewProcess.reviewBaseRule.ruleCnName}</b></font>
							</c:when>
							<c:otherwise>
								${reviewProcess.reviewBaseRule.ruleCnName}
							</c:otherwise>
							</c:choose>
							<c:if test="${!status.last}">
				    	   			<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">
				    	   	</c:if>	
						</c:forEach>		 
						&nbsp;&nbsp; 
						<c:choose>
							<c:when test="${!isExpertSubmitCurrentReview}">
								<a id="icon-save" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="javascript:saveVote();">保存</a>
								<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="javascript:submitVote();">提交</a>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${isNextEnable}">
										<font color="red" style="font-size:14px;">当前阶段评审投票你已提交，请等待下一阶段的评审投票！</font>
									</c:when>
									<c:otherwise>
										<font color="red" style="font-size:14px;">当前阶段评审投票你已提交，所有投票已完成！</font>
									</c:otherwise>
								</c:choose>								
								<a id="icon-refresh" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload',toggle:true" onclick="javascript:window.location.reload();;">刷新</a>
							</c:otherwise>
						</c:choose>			
					</div>
					<div>
        				<table class="formtable">
        					<tr>
        						<!-- <td width="10%">分组<input type="checkbox" id="chemicalSub" value="true" checked="checked"></td> -->
        						<td width="90%">统计说明：<span id="statisticalNotes">${statisticalNotes}</span></td>
              				</tr>
        				</table>
          			</div>
				</div>
				<ewcms:editWindow/>
				<ewcms:footer/>
				<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-groupview.js"></script>
				<script type="text/javascript">
					$(function(){
						$('#tt').datagrid({
							url:'${ctx}/yjk/re/voterecord/${reviewProcessId}/query',
							toolbar:'#tb',
							fit:true,
							nowrap:true,
							rownumbers:true,
							striped:true,
							border:false,
							groupField:'chemicalSubCategory',
						    view: groupview,
						    groupFormatter:function(value, rows){
						        return value + ' - ' + rows.length + ' 条';
						    },
				            singleSelect: true,
							onLoadSuccess:function(row){
								$(this).datagrid('fixRownumber');
							}
							<c:if test="${!isExpertSubmitCurrentReview}">		
				            ,onClickCell: onClickCell,
				            onEndEdit: onEndEdit
				            </c:if>
						});
						$('#chemicalSub').bind('click', function(){
							if ($(this).is(':checked')) {
								$('#tt').datagrid({
									groupField:'chemicalSubCategory',
								    view: groupview,
								    groupFormatter:function(value, rows){
								        return value + ' - ' + rows.length + ' 条';
								    }
								})
							} else {
								$('#tt').datagrid({
									groupField:'',
								    view: groupview,
								    groupFormatter:function(value, rows){
								        return  '合计 - ' + rows.length + ' 条';
								    }
								})
							}
						});
					});
					
					function formatOperation(val, row) {

						return '<a style="text-decoration:none;" onclick="viewHospital(' + val + ');" href="javascript:void(0);" style="height:24px;" title="院用情况">查看</a>';
					}
					
					function viewHospital(id){
						$.ewcms.openWindow({src:'${ctx}/yjk/re/voterecord/' + id + '/indexhospital',title:'查看院用情况',height:300,isRefresh:false});
					}
					
					function formatTooltip(val, row){

						val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
						if(val.toString() == 'true'){
							val="是";
						}else if(val.toString() == 'false'){
							val="否";
						}
						return val;
					}
					
				    var editIndex = undefined;
				    function endEditing(){
				        if (editIndex == undefined){return true}
				        if ($('#tt').datagrid('validateRow', editIndex)){
				            $('#tt').datagrid('endEdit', editIndex);
				            editIndex = undefined;
				            return true;
				        } else {
				            return false;
				        }
				    }
				    function onClickCell(index, field){
				            if (endEditing()){
				                $('#tt').datagrid('selectRow', index)
				                        .datagrid('beginEdit', index);
				                var ed = $('#tt').datagrid('getEditor', {index:index,field:field});
				                if (ed){
				                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				                }
				                editIndex = index;
				            } else {
				                setTimeout(function(){
				                    $('#tt').datagrid('selectRow', editIndex);
				                },0);
				            } 
				    }
				
				    
				    function onEndEdit(index, row){
				        var ed = $(this).datagrid('getEditor', {
				            index: index,
				            field: 'voteTypeInfo'
				        });
				        row.voteTypeInfo = $(ed.target).combobox('getText');
				    }
				    
				    function saveVote(){
				    	onClickCell(0, 'voteTypeInfo');
				    	var rows = $('#tt').datagrid('getChanges');
				    	if(rows.length==0){
				    		$.messager.alert('提示', '没有新改变的数据，无需保存!', 'info');
				    		return;
				    	}
					    var parameter='',voteTypeValue='';
					    $.each(rows,function(index,row){
					    	if(row.voteTypeInfo=='通过'){
					    		voteTypeValue='pass';
					    	}else if(row.voteTypeInfo=='反对'){
					    		voteTypeValue='oppose';
					    	}else{
					    		voteTypeValue='abstain';
					    	}
					    	parameter += 'selections=' + row.id +'@'+voteTypeValue+'&';
					    });
				
				        $.post('${ctx}/yjk/re/voterecord/savevote', parameter, function (data) {
				        	if (data.success){
				        		$('#statisticalNotes').text(data.value);
				        	}
				        	$.messager.alert('提示', data.message, 'info');
				        });
				    }
				    
				    function submitVote(){
						    	onClickCell(0, 'voteTypeInfo');
						    	var rows = $('#tt').datagrid('getRows');
							    var parameter='',voteTypeValue='',passTotal=0;
							    $.each(rows,function(index,row){
							    	if(row.voteTypeInfo=='通过'){
							    		voteTypeValue='pass';
							    		passTotal++
							    	}else if(row.voteTypeInfo=='反对'){
							    		voteTypeValue='oppose';
							    	}else{
							    		voteTypeValue='abstain';
							    	}
							    	parameter += 'selections=' + row.id +'@'+voteTypeValue+'&';
							    });
							    
							    //if(passTotal>10){
							    	//$.messager.alert('提示', '投票通过的总数量超过3，不能提交！', 'info');
							    	//return;
							    //}
						    	$.messager.confirm('提示', '确定要提交当前投票结果吗？<br/><font color="red">提交后不能再修改投票结果了！！！</font>', function(r) {
									if (r) {
										$.ewcms.addLoading();
								        $.post('${ctx}/yjk/re/voterecord/submitvote', parameter, function (data) {
								        	if(data.success){
								        		alert(data.message);
								        		window.location.reload();
								        	}else{
								        		$.messager.alert('提示', data.message, 'info');
								        		 $.ewcms.removeLoading();
								        	}
								        });
								       
									}
								});	
				    }
				</script>
				</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<ewcms:head title="申报 - 新药评审未开启"/>
			<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
				<div data-options="region:'center',fit:true" style="border:0;">	
					<h1 class="title">新药评审还未开启，请等待药学部的通知，谢谢！</h1>
				</div>
			</div>
		<ewcms:footer/>
	</c:otherwise>
</c:choose>