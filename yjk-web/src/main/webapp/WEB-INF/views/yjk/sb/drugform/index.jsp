<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="申报-药品申报"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'stateInfo',width:120">申报状态</th>		
				<th data-options="field:'userName',width:120">申报医生</th>
				<th data-options="field:'commonName',width:120,sortable:true,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.common.commonName;
						}">通用名</th>		
				<th data-options="field:'pill',width:120,sortable:true,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.pill;
						}">剂型</th>		
				<th data-options="field:'specNumber',width:120,sortable:true,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.specNumber;
						}">规格*数量</th>		
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:400,height:450});">新药申报</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;" >
        		<table class="formtable">
              		<tr>
              			<td width="5%">通用名</td>
              			<td width="15%"><input type="text" name="LIKE_commonNameContents.common.commonName" style="width:140px;"/></td>
            			<td width="5%">剂型</td>
              			<td width="15%"><input type="text" name="LIKE_commonNameContents.pill" style="width:140px;"/></td>
            			<td width="7%">规格*数量</td>
              			<td width="15%"><input type="text" name="LIKE_commonNameContents.specNumber" style="width:140px;"/></td>
              			<td width="20%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/sb/drugform/query',
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
</script>