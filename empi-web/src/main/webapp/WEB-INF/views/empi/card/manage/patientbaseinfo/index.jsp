<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="患者基本信息"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'patientId',width:100">患者ID号</th>
			    <th data-options="field:'name',width:80">姓名</th>
			    <th data-options="field:'sexInfo',width:80">性别</th>
			    <th data-options="field:'birthday',width:80">出生日期</th>
			    <th data-options="field:'sourcePlace',width:80">来源地</th>
			    <th data-options="field:'certificateTypeName',width:80">证件类别</th>
			    <th data-options="field:'certificateNo',width:80">证件号码</th>
			    <th data-options="field:'telephone',width:80">联系电话</th>
			    <th data-options="field:'contactName',width:80">联系人姓名</th>
			    <th data-options="field:'workUnit',width:80">工作单位</th>
			    <th data-options="field:'address',width:80">地址</th>
			    <th data-options="field:'nationName',width:80">民族</th>
			    <th data-options="field:'profession',width:80">职业</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:700,height:350});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:700,height:350});" href="javascript:void(0);">修改</a>
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">患者ID号</td>
              			<td width="23%"><input type="text" name="LIKE_patientId" style="width:140px"/></td>
    					<td width="5%">姓名</td>
    					<td width="23%"><input type="text" name="LIKE_name" style="width:140px"/></td>
    					<td width="5%">证件号码</td>
    					<td width="23%"><input type="text" name="LIKE_certificateNo" style="width:140px"/></td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
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
			url:'${ctx}/empi/card/manage/patientbaseinfo/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/empi/card/manage/practicecard/' + rowData.id + '/detail" frameborder="0" width="100%" height="200px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
	});
</script>