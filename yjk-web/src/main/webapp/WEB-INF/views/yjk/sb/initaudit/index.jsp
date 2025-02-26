<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
		<ewcms:head title="申报-新药初审"/>
			<table id="tt">
				<thead>            
					<tr>
					    <th data-options="field:'ck',checkbox:true"/>
					    <th data-options="field:'id',hidden:true">序号</th>
					    <th data-options="field:'formatId',width:80">编号</th>
						<th data-options="field:'auditStatusInfo',width:100">审核状态</th>	
						<th data-options="field:'projectRemark',width:100,sortable:true">申报说明</th>
						<th data-options="field:'realName',width:100,sortable:true">申报医生</th>
						<th data-options="field:'departName',width:150,sortable:true">科室名称</th>
						<th data-options="field:'declareCategory',width:100,sortable:true">申报类型</th>
						<c:forEach items="${commonNameRuleList}" var="commonNameRule">
							<c:choose>
								<c:when test="${commonNameRule.ruleName == 'administration.id'}">
									<th data-options="field:'commonNameContents.administration.name',width:120,sortable:true,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.administration.name;
											}">${commonNameRule.ruleCnName}</th>
								</c:when>	
								<c:when test="${commonNameRule.ruleName == 'common.commonName'}">
									<th data-options="field:'commonNameContents.${commonNameRule.ruleName}',width:120,sortable:true,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.${commonNameRule.ruleName};
											}">${commonNameRule.ruleCnName}</th>
								</c:when>
								<c:when test="${commonNameRule.ruleName == 'common.drugCategory'}">
								</c:when>								
								<c:otherwise>
									<th data-options="field:'${commonNameRule.ruleName}',width:150,sortable:true,
											formatter:function(val,row){
												return row.commonNameContents==null ?'':row.commonNameContents.${commonNameRule.ruleName};
											}">${commonNameRule.ruleCnName}</th>
								</c:otherwise>
							</c:choose>			
						</c:forEach>
						<th data-options="field:'drugCategoryInfo',width:100">药品种类</th>
						<th data-options="field:'dosage',width:150,formatter:formatTooltip">用法用量</th>
						<th data-options="field:'indicationsEffect',width:300,formatter:formatTooltip">适应症及药理作用</th>
						<th data-options="field:'declareReason',width:300,formatter:formatTooltip">申请理由</th>
						<th data-options="field:'remark',width:150,formatter:formatTooltip">初审说明</th>
						<th data-options="field:'constituent',width:150,formatter:formatTooltip">成分</th>
						<th data-options="field:'preparationed',width:100,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">复方制剂</th>
						<th data-options="field:'fillInDate',width:150,sortable:true">填报时间</th>
						<th data-options="field:'declareDate',width:150,sortable:true">申报时间</th>
						<th data-options="field:'auditDate',width:150,sortable:true">初审时间</th>	
					</tr>
				</thead>
			</table>
			<div id="tb" style="padding:5px;height:auto;">
		        <div class="toolbar" style="margin-bottom:2px">
		        	<c:if test="${!isOpenReview}">
		        	<a id="tb-audit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="initAudit();">新药初审</a>
		        	<a id="tb-export" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导出新药申报',src:'${ctx}/system/report/show/text/7/paraset'});">新药申报统计</a>
		        	</c:if>
				</div>
		        <div>
		        	<form id="queryform" style="padding:0;margin:0;" >
		        		<table class="formtable">
		              		<tr>
		           				<td>审核状态</td>
		           				<td>
		           					<form:select  name="EQ_auditStatus" path="stateList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
							  			<form:option value="" label="------请选择------"/>
							  			<form:options items="${stateList}" itemLabel="info"/>
									</form:select>
		           				</td>
			              		<td>医生姓名</td>
			           			<td><input  id="userId" name="EQ_userId" style="width:140px;"/></td>
		           				<td>申报说明</td>
		           				<td>
		           					<form:select  name="EQ_systemParameterId" path="stateList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
							  			<form:option value="" label="------请选择------"/>
							  			<form:options items="${systemParameterList}" itemValue="id" itemLabel="projectRemark"/>
									</form:select>
		           				</td>			           				
              					<td>简拼</td>
              					<td><input type="text" name="LIKE_commonNameContents.common.spellSimplify" style="width:140px;"/></td>
		              			<td width="20%" colspan="2">
		            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
		           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
		           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton" onclick="$.ewcms.moreQuery();"><span id="showHideLabel">更多</span></a>
		           				</td>
		           			</tr>
		           			<tr style="display: none;">
			              		<td>序号</td>
			           			<td><input name="EQ_id" style="width:140px;"/></td>				           			
		              			<td>通用名</td>
		              			<td><input type="text" name="LIKE_commonNameContents.common.commonName" style="width:140px;"/></td>	
		              			<td>申报类型</td>
		              			<td><input type="text" name="LIKE_declareCategory" style="width:140px;"/></td>	           			
								<td >填报时间从</td>
								<td colspan="4">
									<input type="text" id="fillInDate1" name="GTE_fillInDate" class="easyui-datetimebox" style="width: 145px" data-options="editable:false" />
									 至 
									<input type="text" id="fillInDate2" name="LTE_fillInDate" class="easyui-datetimebox" style="width: 145px" data-options="editable:false" />
								</td>    				
		           			</tr>
		           		</table>
		          </form>
		        </div>
			</div>
			<ewcms:editWindow/>
		<ewcms:footer/>
		<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
		<script type="text/javascript">
					$(function(){
						$('#tt').datagrid({
							url:'${ctx}/yjk/sb/initaudit/query',
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
									content: '<iframe src="${ctx}/yjk/sb/initaudit/' + rowData.commonNameContents.id + '/detail" frameborder="0" width="100%" height="450px" scrolling="auto"></iframe>',
									onLoad:function(){
										$('#tt').datagrid('fixDetailRowHeight',rowIndex);
									}
								});
								$('#tt').datagrid('fixDetailRowHeight',rowIndex);
							}
						});
						
						var transTool = $('#userId').combogrid({
							panelWidth: 500,
					        idField: 'id',
					        textField: 'realname',
					        fitColumns: true,
					        multiple:true,
					        url:'${ctx}/security/user/user/findbyrealname',
					        columns: [[
					        	{field:'id',hidden:true},
			                    {field:'username',title:'登录名',width:80},
			                    {field:'realname',title:'姓名',width:130},
			                    {field:'sex',title:'性别',width:60,
			                    	formatter:function(val,row){
										return row.sexDescription;
									}		
			                    },
			                    {field:'mobilePhoneNumber',title:'手机号',width:120},
			                    {field:'organizationNames',title:'科室名称',width:150}
					        ]]
						});
						
						transTool.combogrid('textbox').keyup(function(event) {
							if (event.keyCode == 38){
							} else if (event.keyCode == 40){
							} else {
								$('#userId').combogrid('grid').datagrid('reload', {
									realname : $('#userId').combogrid("getText")
								});
					 		}
					   	});						
					});
			
					function initAudit(){
						var rows = $('#tt').datagrid('getSelections');
					    
					    if(rows.length == 0){
					        $.messager.alert('提示','请选择要初审的记录','info');
					        return;
					    }
					    for(j=0;j<rows.length;j++){
					    	if(rows[j].auditStatusInfo=='未申报'){
						        $.messager.alert('提示','未申报记录不能初审','info');
						        return;
					    	}
					    }
					    $.ewcms.edit({title:'新药初审',width:400,height:265});
					}
					
					function formatTooltip(val, row){
						return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
					}
		</script>