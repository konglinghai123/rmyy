<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="静态资源版本控制"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'text',width:400">文件名</th>
				<th data-options="field:'url',width:700,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.url;
						}">URL</th>
				<th data-options="field:'version',width:80,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.version;
						}">Version</th>
				<th data-options="field:'operation',width:320,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:700,height:360});" href="javascript:void(0);">版本+1</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:700,height:360});" href="javascript:void(0);">清空版本</a>
			<a id="tb-gencss" class="easyui-linkbutton" data-options="iconCls:'icon-gen-css',plain:true" href="javascript:void(0);">压缩js/css</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">切换版本</a>
		</div>
		<div style="padding-left:5px;">
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
$(function(){
	$('#tt').treegrid({
		toolbar:'#tb',
		fit:true,
		url:'${ctx}/system/staticresource/table',
		idField:'id',
		treeField:'text',
		animate:true,
		lines:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:false,
		onLoadSuccess:function(row){
			$('.versionCls').linkbutton({text:'版本号+1',plain:true,iconCls:'icon-edit'});
			$('.previewCls').linkbutton({text:'压缩',plain:true,iconCls:'icon-preview'});
			$('.downloadCls').linkbutton({text:'下载',plain:true,iconCls:'icon-download'});
			$('.historyCls').linkbutton({text:'历史',plain:true,iconCls:'icon-history'});
			$('.verifyCls').linkbutton({text:'校验',plain:true,iconCls:'icon-verify'});
		}
	});
});
function formatOperation(val, row){
	var operation = '';
	operation += '<a class="versionCls" onclick="incVersion(' + row + ');" href="javascript:void(0);" style="height:24px;">版本号+1</a>|';
	operation += '<a class="previewCls" onclick="previewTemplate(' + row.id + ');" href="javascript:void(0);" style="height:24px;">压缩</a>|';
	operation += '<a class="verifyCls" onclick="verifyTemplate(' + row.id + ', \'' + row.attributes.templateTypeInfo + '\');" href="javascript:void(0);" style="height:24px;">校验</a>|'
	operation += '<a class="downloadCls" onclick="window.open(\'${ctx}/stationcluster/template/' + row.id + '/download\');" href="javascript:void(0);" style="height:24px;">下载</a>|';
	operation += '<a class="historyCls" onclick="historyTemplate(' + row.id + ');" href="javascript:void(0);" style="height:24px;">历史</a>';
	return operation;
}
function incVersion(row){
	var fileName = row.id;
	var content = row.content;
	var version = row.version;
	if (!version) {
		version = 1;
	} else {
		version = parseInt(version) + 1;
	}
	$.post(
		'${ctx}/system/staticresource/incVersion',
		{
			fileName : fileName,
			content : content,
			newVersion : version
		}
		,
		function(newContent){
			$('#tt').treegrid('reload');
		}
	);
}
</script>