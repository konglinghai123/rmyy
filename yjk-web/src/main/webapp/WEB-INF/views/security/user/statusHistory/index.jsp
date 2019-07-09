<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/security/user/statusHistory/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,border:false,pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',sortable:true">编号</th>
				<th data-options="field:'user.username',width:150,sortable:true,
						formatter:function(val, row){
							return row.userName;
						}">用户名</th>
				<th data-options="field:'status',width:80,sortable:true,
						formatter:function(val, row){
							return row.statusInfo;
						}">状态</th>
				<th data-options="field:'opUser.username',width:150,sortable:true,
						formatter:function(val, row){
							return row.opUserName;
						}">操作员</th>
				<th data-options="field:'opDate',width:145,sortable:true">操作时间</th>
				<th data-options="field:'reason',width:600">原因</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div>
        	<form id="queryform">
        		<table class="formtable">
              		<tr>
              			<td width="5%">用户名</td>
              			<td width="23%"><input type="text" name="LIKE_user.username" style="width:140px" placeholder="模糊匹配"/></td>
    					<td width="5%">操作员</td>
    					<td width="23%"><input type="text" name="LIKE_opUser.username" style="width:140px" placeholder="模糊匹配"/></td>
    					<td width="28%">&nbsp;</td>
						<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"  onclick="$.ewcms.moreQuery();"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<tr style="display: none;">
    					<td>操作时间</td>
    					<td colspan="2"><input type="text" id="createDate" name="GTE_opDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="createDate" name="LTE_opDate" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
<ewcms:footer/>