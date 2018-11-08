<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:choose>
	<c:when test="${isOpenDeclare}">
		<ewcms:head title="申报-药品申报"/>
			<table id="tt">
				<thead>
					<tr>
					    <th data-options="field:'ck',checkbox:true"/>
					    <th data-options="field:'id',hidden:true">编号</th>
						<th data-options="field:'auditStatusInfo',width:100">审核状态</th>	
						<c:if test="${isAdmin}">	
							<th data-options="field:'userName',width:120,sortable:true">申报医生</th>
						</c:if>
						<c:forEach items="${commonNameRuleList}" var="commonNameRule">
							<c:choose>
								<c:when test="${commonNameRule.ruleName == 'common.administration.id'}">
									<th data-options="field:'name',width:120,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.common.administration.name;
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
						<th data-options="field:'declared',width:100,sortable:true,formatter:formatOperation">是否已经申报</th>		
					</tr>
				</thead>
			</table>
			<div id="tb" style="padding:5px;height:auto;">
		        <div class="toolbar" style="margin-bottom:2px">
					<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:600,height:450});">新增</a>
					<a id="tb-declare" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:750,height:450,title:'申报提交',src:'${ctx}/yjk/sb/drugform/declaresubmit'});">申报提交</a>
					<a id="tb-canceldeclare" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:750,height:450,title:'申报撤销',src:'${ctx}/yjk/sb/drugform/declarecancel'});">申报撤销</a>
					<font color=red>${declareRule}</font>
				</div>
		        <div  style="padding-left:5px;">
		        	<form id="queryform" style="padding:0;margin:0;" >
		        		<table class="formtable">
		              		<tr>
		           				<td>审核状态</td>
		           				<td>
		           					<form:select id="enabled" name="EQ_auditStatus" path="stateList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
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
		              			<c:if test="${isAdmin}">
			              			<td>申报医生</td>
			           				<td>
			           					<form:select id="userId" name="EQ_userId" path="userList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
								  			<form:option value="" label="------请选择------"/>
								  			<form:options items="${userList}" itemLabel="username" itemValue="id"/>
										</form:select>
			           				</td>  
		           				</c:if>      				
		           			</tr>            			
		           		</table>
		          </form>
		        </div>
			</div>
			<ewcms:editWindow/>
		<ewcms:footer/>
		<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
		<script type="text/javascript">
			<c:choose>
				<c:when test="${isAdmin}">
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
							view:detailview,
							detailFormatter : function(rowIndex, rowData) {
								return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
							},
							onExpandRow: function(rowIndex, rowData){
								$('#ddv-' + rowIndex).panel({
									border:false,
									cache:false,
									content: '<iframe src="${ctx}/yjk/sb/drugform/' + rowData.commonNameContents.id + '/detail" frameborder="0" width="100%" height="450px" scrolling="auto"></iframe>',
									onLoad:function(){
										$('#tt').datagrid('fixDetailRowHeight',rowIndex);
									}
								});
								$('#tt').datagrid('fixDetailRowHeight',rowIndex);
							}
						});
					});
				</c:when>
				<c:otherwise>
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
							border:false
						});
					});				
				</c:otherwise>
			</c:choose>	
			
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
				return val ?  '是': '否  <a class="resumedCls" onclick="deleteDeclare(' + row.id + ')" href="javascript:void(0);">删除</a>';
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
			
			function declare(){
				
			}
		</script>
	</c:when>
	<c:otherwise>
		药品申报未开启
	</c:otherwise>
</c:choose>