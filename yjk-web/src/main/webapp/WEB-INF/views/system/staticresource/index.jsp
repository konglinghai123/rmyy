<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="静态资源版本控制"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'text',width:600">文件名</th>
				<th data-options="field:'url',width:800,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.url;
						}">URL</th>
				<th data-options="field:'version',width:80,halign:'left',align:'right',
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.version;
						}">Version</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0);">版本+1</a>
			<a id="tb-clear" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" href="javascript:void(0);">清除版本</a>
			<a id="tb-compress" class="easyui-linkbutton" data-options="iconCls:'icon-compress',plain:true" href="javascript:void(0);">压缩js/css</a>
			<a id="tb-switch" class="easyui-menubutton" data-options="menu:'#menu-switch',iconCls:'icon-status',plain:true" href="javascript:void(0);">切换版本</a>
		</div>
		<div id="menu-switch" style="width:150px">
			<div id="menu-switch-min" data-options="iconCls:'icon-status-show'">切换到压缩版</div>
			<div id="menu-switch-develop" data-options="iconCls:'icon-status-hide'">切换到开发版</div>
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
			singleSelect:false
		});
		
		$('#tb-add').bind('click', function(){
			var rows = $('#tt').treegrid('getSelections');
		    if(rows.length == 0){
		        $.messager.alert('提示','请选择要进行版本+1的记录','info');
		        return;
		    }
		    
		    var fileNames = [], contents = [], newVersions = [];
		    $.each(rows,function(index,row){
		    	var parent = $('#tt').treegrid('getParent', row.id);
		    	if (parent){
			    	fileNames.push(parent.id);
			    	contents.push(row.attributes.content);
			    	var version = row.attributes.version;
					if (!version) {
						version = 1;
					} else {
						version = parseInt(version) + 1;
					}
					newVersions.push(version);
		    	}
		    });
		    
		    $.post('${ctx}/system/staticresource/batchIncVersion', {fileNames : fileNames,contents : contents,newVersions : newVersions}, function(data){
		    	$('#tt').treegrid('unselectAll');
		    	$('#tt').treegrid('reload');
				$.messager.alert('版本+1', data.message, 'info');
			});
		});
		
		$('#tb-clear').bind('click', function(){
			var rows = $('#tt').treegrid('getSelections');
		    if(rows.length == 0){
		        $.messager.alert('提示','请选择要进行清除版本的记录','info');
		        return;
		    }
		    
		    var fileNames = [], contents = [];
		    $.each(rows,function(index,row){
		    	var parent = $('#tt').treegrid('getParent', row.id);
		    	if (parent){
		    		fileNames.push(parent.id);
		    		contents.push(row.attributes.content);
		    	}
		    });
		    
		    $.post('${ctx}/system/staticresource/clearVersion', {fileNames : fileNames, contents : contents}, function(data){
		    	$('#tt').treegrid('unselectAll');
		    	$('#tt').treegrid('reload');
				$.messager.alert('清除版本', data.message, 'info');
			});
		});
		
		$('#tb-compress').bind('click', function(){
			var rows = $('#tt').treegrid('getSelections');
		    if(rows.length == 0){
		        $.messager.alert('提示','请选择要进行压缩的记录','info');
		        return;
		    }
		    
		    var fileNames = [], contents = [];
		    $.each(rows,function(index,row){
		    	var parent = $('#tt').treegrid('getParent', row.id);
		    	if (parent){
			    	fileNames.push(parent.id);
			    	contents.push(row.attributes.content);
		    	}
		    });
		    
		    $.post('${ctx}/system/staticresource/batchCompress', {fileNames : fileNames, contents : contents}, function(data){
		    	$('#tt').treegrid('unselectAll');
		    	$('#tt').treegrid('reload');
				$.messager.alert('压缩', data.message, 'info');
			});
		});
		
		$('#menu-switch-min,#menu-switch-develop').bind('click', function(){
			var min = false;
			if (this.id == 'menu-switch-min'){
				min = true;
			}
			var rows = $('#tt').treegrid('getSelections');
		    if(rows.length == 0){
		        $.messager.alert('提示','请选择要进行切换的记录','info');
		        return;
		    }
		    
		    var fileNames = [], contents = [];
		    $.each(rows,function(index,row){
		    	var parent = $('#tt').treegrid('getParent', row.id);
		    	if (parent){
			    	fileNames.push(parent.id);
			    	contents.push(row.attributes.content);
		    	}
		    });
		    
		    $.post('${ctx}/system/staticresource/batchSwitch', {fileNames : fileNames, contents : contents, min:min}, function(data){
		    	$('#tt').treegrid('unselectAll');
		    	$('#tt').treegrid('reload');
				$.messager.alert('切换', data.message, 'info');
			});
		});
	});
</script>