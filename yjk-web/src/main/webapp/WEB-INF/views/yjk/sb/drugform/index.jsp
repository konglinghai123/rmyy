<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:choose>
	<c:when test="${isOpenDeclare}">
		<ewcms:head title="申报-新药申报"/>
			<table id="tt">
				<thead>
				    <thead frozen="true">    
				        <tr>    
					    <th data-options="field:'ck',checkbox:true"/>
					    <th data-options="field:'declared',width:100,sortable:true,formatter:formatOperation">是否已经申报</th> 
				        </tr>    
				    </thead>  
				    <thead >  				
					<tr>
					    <th data-options="field:'id',hidden:true">编号</th>
					    <th data-options="field:'formatId',width:100">编号</th>
						<th data-options="field:'auditStatusInfo',width:140,formatter:formatAudit">审核状态</th>	
						<c:forEach items="${commonNameRuleList}" var="commonNameRule">
							<c:choose>
								<c:when test="${commonNameRule.ruleName == 'common.administration.id'}">
									<th data-options="field:'name',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.common.administration.name;
											}">${commonNameRule.ruleCnName}</th>
								</c:when>
								<c:when test="${commonNameRule.ruleName == 'common.drugCategory'}">
									<th data-options="field:'drugCategory',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.common.drugCategoryInfo;
											}">${commonNameRule.ruleCnName}</th>
								</c:when>
								<c:otherwise>
									<th data-options="field:'${commonNameRule.ruleName}',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.${commonNameRule.ruleName};
											}">${commonNameRule.ruleCnName}</th>
								</c:otherwise>
							</c:choose>			
						</c:forEach>
						<th data-options="field:'dosage',width:150">用法用量</th>
						<th data-options="field:'indicationsEffect',width:300">适应症及药理作用</th>
						<th data-options="field:'declareReason',width:300">申请理由</th>
						<th data-options="field:'fillInDate',width:150">填报时间</th>
						<th data-options="field:'declareDate',width:150">申报时间</th>
						<th data-options="field:'remark',width:150">初审说明</th>
							
					</tr>
					 </thead> 
				</thead>
			</table>
			<div id="tb" style="padding:5px;height:auto;">
		        <div class="toolbar" style="margin-bottom:2px">
					<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:600,height:450});">填写新药</a>
					<a id="tb-declare" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:750,height:450,title:'申报提交',src:'${ctx}/yjk/sb/drugform/declaresubmit'});">申报提交</a>
					<a id="tb-canceldeclare" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:750,height:450,title:'申报撤销',src:'${ctx}/yjk/sb/drugform/declarecancel'});">申报撤销</a>
					<font color=red>${declareRule}</font>
				</div>
		        <div  style="padding-left:5px;">
		        	<form id="queryform" style="padding:0;margin:0;" >
		        		<table class="formtable">
		              		<tr>
		           				<td>审核状态</td>
		           				<td>
		           					<form:select name="EQ_auditStatus" path="stateList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
							  			<form:option value="" label="------请选择------"/>
							  			<form:options items="${stateList}" itemLabel="info"/>
									</form:select>
		           				</td>
		              		   
              					<td width="7%">通用名拼音</td>
              					<td width="23%"><input type="text" name="LIKE_commonNameContents.common.spellSimplify" style="width:140px;"/></td>
		              			<td width="5%">通用名</td>
		              			<td width="15%"><input type="text" name="LIKE_commonNameContents.common.commonName" style="width:140px;"/></td>
		              			<td width="20%" colspan="2">
		            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
		           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
		           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多</span></a>
		           				</td>
		           			</tr>
		           			<tr>
		            			<td width="5%">剂型</td>
		              			<td width="15%"><input type="text" name="LIKE_commonNameContents.pill" style="width:140px;"/></td>
		            			<td width="7%">规格</td>
		              			<td width="15%"><input type="text" name="LIKE_commonNameContents.specifications" style="width:140px;"/></td>     				
		           			</tr>            			
		           		</table>
		          </form>
		        </div>
			</div>
			<div>
				<form:form id="printForm" method="post" action="${ctx}/system/report/show/text/1/build" commandName="m"  class="form-horizontal" target="_blank">
					<input type="hidden" id="printDrugFormId" name="paramMap['drugFormId']"/>
					<input type="hidden" name="textType" vlaue="PDF"/>
				</form:form>
			</div>
			<ewcms:editWindow/>
		<ewcms:footer/>
		<script type="text/javascript">
			$(function(){
				$('#tt').datagrid({
					url:'${ctx}/yjk/sb/drugform/querybyuser',
					toolbar:'#tb',
					fit:true,
					nowrap:true,
					pagination:true,
					rownumbers:true,
					striped:true,
					pageSize:20,
					border:false,
					onLoadSuccess:function(row){
						$('.removeCls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
						$('.printCls').linkbutton({text:'打印',plain:true,iconCls:'icon-print'});
					}
				});
			});				
			
			$("form table tr").next("tr").hide();
			$('#tb-more').bind('click', function(){
		       	var showHideLabel_value = $('#showHideLabel').text();
		    	$('form table tr').next('tr').toggle();
		     	if (showHideLabel_value == '收缩'){
		     		$('#showHideLabel').text('更多');
		    	}else{
		    		$('#showHideLabel').text('收缩');
		    	}
		    	$('#tt').datagrid('resize');
		    });	
			
			
			function formatOperation(val, row){
				return val ?  '是' : '否&nbsp;|&nbsp;<a class="removeCls" onclick="deleteDeclare(' + row.id + ')" style="height:24px;" href="javascript:void(0);">删除</a>';
			}
			
			function formatAudit(val, row){
				if (row.auditStatus == 'passed'){
					return val + '&nbsp;|<a class="printCls" onclick="print(' + row.id + ')" style="height:24px;" href="javascript:void(0);"></a>'
				}else{
					return val;
				}
			}
			
			function deleteDeclare(id){
				 $.messager.confirm('提示', '确定要删除该记录吗', function(r){
				 	if (r){
						$.post('${ctx}/yjk/sb/drugform/' + id + '/deletedeclare', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
			        }
				});
			}
			
			function print(id){
				$.ewcms.openTopWindow({src:'${ctx}/yjk/sb/drugform/build?drugFormId=' + id,title:'打印新药申报表',isRefresh:false,maximizable:true});
			}
		</script>
	</c:when>
	<c:otherwise>
		新药申报未开启
	</c:otherwise>
</c:choose>