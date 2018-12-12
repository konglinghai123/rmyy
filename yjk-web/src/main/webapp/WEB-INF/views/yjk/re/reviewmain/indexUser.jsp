<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审主表"/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/yjk/re/reviewmain/${reviewMainId}/queryUser',toolbar:'#tb',fit:true,nowrap:true,rownumbers:true,striped:true,pagination:true,border:false">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'realname',width:150,sortable:true">姓名</th>
				<th data-options="field:'sex',width:60,sortable:true,
						formatter:function(val,row){
							return row.sexDescription;
						}">性别</th>
				<th data-options="field:'email',width:200,sortable:true">邮箱</th>
				<th data-options="field:'mobilePhoneNumber',width:100,sortable:true">手机号</th>
				<th data-options="field:'departmentAttribute',width:100,sortable:true,
						formatter:function(val,row){
							return row.departmentAttribute != null ? row.departmentAttribute.name : '';
						}">科室属性</th>
				<th data-options="field:'profession',width:100,sortable:true,
						formatter:function(val,row){
							return row.profession != null ? row.profession.name : '';
						}">执业类别</th>
				<th data-options="field:'technicalTitle',width:100,sortable:true,
						formatter:function(val,row){
							return row.technicalTitle != null ? row.technicalTitle.name : '';
						}">技术职称（资格）</th>
				<th data-options="field:'appointment',width:100,sortable:true,
						formatter:function(val,row){
							return row.appointment != null ? row.appointment.name : '';
						}">是否聘任</th>
				<th data-options="field:'director',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否科主任</th>
				<th data-options="field:'secondDirector',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否科副主任</th>
				<th data-options="field:'pharmacy',width:120,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否药事会成员</th>
				<th data-options="field:'science',width:150,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否院学术委员会成员</th>
				<th data-options="field:'antibiosis',width:170,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '';
						}">是否抗菌药物遴选小组成员</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/yjk/re/reviewmain/${reviewMainId}/saveUser',title:'添加',width:750,height:265,left:200});">添加</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/yjk/re/reviewmain/${reviewMainId}/removeUser',title:'移除',width:750,height:265});">移除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="$.ewcms.openTopWindow({src:'${ctx}/yjk/re/reviewmain/${reviewMainId}/build',title:'申报新药人员打印',isRefresh:false,maximizable:true});">打印</a>
		</div>
        <div>
        	<form id="queryform">
        		<table class="formtable">
              		<tr>
              			<td width="10%">用户名</td>
              			<td width="23%"><input type="text" name="LIKE_username" style="width:140px"/></td>
    					<td width="10%">姓名</td>
    					<td width="23"><input type="text" name="LIKE_realname" style="width:140px"/></td>
						<td width="35%" colspan="2" align="left">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton" onclick="$.ewcms.moreQuery();"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<tr style="display:none;">
           				<td>手机号</td>
              			<td><input type="text" name="LIKE_mobilePhoneNumber" style="width:140px"/></td>
    					<td></td>
    					<td></td>
    					<td></td>
    					<td></td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>