<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="用户"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',sortable:true">编号</th>
				<th data-options="field:'username',width:150,sortable:true">登录名</th>
				<th data-options="field:'realname',width:150,sortable:true">姓名</th>
				<th data-options="field:'sex',width:60,sortable:true,
						formatter:function(val,row){
							return row.sexDescription;
						}">性别</th>
				<th data-options="field:'email',width:200,sortable:true">邮箱</th>
				<th data-options="field:'mobilePhoneNumber',width:100,sortable:true">手机号</th>
				<th data-options="field:'organizationNames',width:150">科室名称</th>	
				<th data-options="field:'departmentAttribute',width:100,sortable:true,
						formatter:function(val,row){
							return row.departmentAttribute != null ? row.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,sortable:true,
						formatter:function(val,row){
							return row.profession != null ? row.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:120,sortable:true,
						formatter:function(val,row){
							return row.technicalTitle != null ? row.technicalTitle.name : '';
						}">技术职称(资格)</th>
				<th data-options="field:'appointment',width:100,sortable:true,
						formatter:function(val,row){
							return row.appointment != null ? row.appointment.name : '';
						}">聘任</th>
				<th data-options="field:'director',width:120,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">科主任</th>
				<th data-options="field:'secondDirector',width:120,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">科副主任</th>
				<th data-options="field:'pharmacy',width:140,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">药事会成员</th>
				<th data-options="field:'science',width:170,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">院学术委员会成员</th>
				<th data-options="field:'antibiosis',width:200,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">抗菌药物遴选小组成员</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div>
        	<c:if test="${!istbEnable}">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">评审名称</td>
              			<td width="23%">
		           			<form:select  id="EQ_reviewMainId" name="EQ_reviewMainId" path="reviewMainList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
								<form:option value="-1" label="------请选择------"/>
								<form:options items="${reviewMainList}" itemValue="id" itemLabel="name" cssStyle="width:150"/>
							</form:select>
						</td>
            			<td width="5%"></td>
              			<td width="23%"></td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
          </c:if>
        </div>
	</div>
	<input type="hidden" id="reviewMainId"/>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/uservote/queryUser',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/yjk/re/uservote/' + rowData.id + '/tabs?reviewMainId=' + $('#reviewMainId').val() + '" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
	});
	<c:if test="${!istbEnable}">
	$('#tb-query').bind('click', function(){
		$('#reviewMainId').val($('#EQ_reviewMainId').combobox('getValue'));
		$.ewcms.query();
	});
	$('#tb-clear').bind('click', function(){
		$('#reviewMainId').val(-1);
		$('#queryform').form('reset');
		$('#tt').datagrid('reload');
	});
	</c:if>
</script>
