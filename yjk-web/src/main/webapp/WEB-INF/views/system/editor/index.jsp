<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="在线编辑"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'text',width:400,formatter:formatText">文件名</th>
				<th data-options="field:'lastModified',width:148,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.lastModified;
						}">修改时间</th>
				<th data-options="field:'size',width:80,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.size;
						}">大小</th>
				<th data-options="field:'path',width:600,
						formatter:function(val,row){
							if (row.attributes != null) return decodeURIComponent(row.attributes.path);
						}">路径</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-upload" class="easyui-linkbutton" data-options="iconCls:'icon-uploadfile',plain:true" href="javascript:void(0);">上传</a>
			<a id="tb-download" class="easyui-linkbutton" data-options="iconCls:'icon-download',plain:true" href="javascript:void(0);">下载</a>
			<a id="menu-add" class="easyui-menubutton" data-options="iconCls:'icon-add',plain:true,menu:'#menu-addsub'" href="javascript:void(0);">新建</a>
			<a id="tb-rename" class="easyui-linkbutton" data-options="iconCls:'icon-rename',plain:true" href="javascript:void(0);">重命名</a>
			<a id="tb-delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" href="javascript:void(0);">删除</a>
			<a id="tb-move" class="easyui-linkbutton" data-options="iconCls:'icon-move',plain:true" href="javascript:void(0);">移动</a>
			<a id="tb-compress" class="easyui-linkbutton" data-options="iconCls:'icon-compress',plain:true" href="javascript:void(0);">压缩</a>
			<a id="tb-uncompress" class="easyui-linkbutton" data-options="iconCls:'icon-uncompress',plain:true" href="javascript:void(0);">解压缩</a>
		</div>
		<div id="menu-addsub" style="width:80px;">
            <div id="menu-add-directory" data-options="iconCls:'icon-folder'">目录</div>
            <div id="menu-add-file" data-options="iconCls:'icon-file'">文件</div>
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
			url:'${ctx}/system/editor/table',
			idField:'id',
			treeField:'text',
			animate:true,
			lines:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			onBeforeExpand:function(row){
				var url = '${ctx}/system/editor/table?path=' + row.attributes.path; 
		        $("#tt").treegrid("options").url = url;  
		        return true;
			},
			onExpand:function(row){
				var children = $('#tt').treegrid('getChildren',row.id);
				if(children.length <= 0){
					row.leaf = true;
					$('#tt').treegrid('refresh', row.id);
				}
			}
		});
		
		$('#tb-download').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
			
			window.open('${ctx}/system/editor/download?path=' + row.attributes.path,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,scrollbars=yes,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
		});
		
		$('#tb-upload').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
			
			$.ewcms.openWindow({title:'上传',src:'${ctx}/system/editor/upload?parentPath=' + row.attributes.path,width:950,height:450});
		});
		
		$('#tb-rename').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
	
			$.messager.prompt('重命名', '真的需要重命名[' + row.text + ']，请输入新的名称', function(r){
				if (r){
		            $.post('${ctx}/system/editor/rename',{'path': row.attributes.path, 'newName':r},function(data){
			            if(data.success){
			            	var parent = $('#tt').treegrid('getParent', row.id);
			            	$('#tt').treegrid('reload', parent.id);
			            }
			            $.messager.alert('提示', data.message, 'info');
		    	    });						
				}
			});
		});
		
		$('#tb-delete').bind('click', function(){
			//判断是否选择了节点
			var rows = $('#tt').treegrid('getSelections');
			if(!rows){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
			var paths = new Array();
			$.each(rows,function(index,row){
				paths.push(row.attributes.path);
			});
			$.messager.prompt('删除', '<strong>注意：</strong><span>如果选择的是目录将会删除子目录！并且此操作不可恢复，执行请慎重！</span><br/><br/>确定要删除所选记录吗?如果需要删除请输入<strong>ok</strong>', function(r){
				if (r && ('ok' == r)){
					$.post('${ctx}/system/editor/delete', {'paths':paths}, function(data){
						if(data.success){
							$.each(rows,function(index,row){
								$('#tt').treegrid('remove', row.id);
							});
			            }
						$.messager.alert('提示', data.message, 'info');
					});
				}
			});
		});
		
		$('#menu-add-directory,#menu-add-file').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
	
			var url = '${ctx}/system/editor/create/directory';
			var title = '目录';
			var promptContent = '<strong>注意：</strong><span>支持如/a/b/c多级目录</span><br/><br/>请输入目录名称';
			if ('menu-add-file' == this.id){
				url = '${ctx}/system/editor/create/file';
				title = '文件';
				promptContent = '<strong>注意：</strong><span>支持如/a/b.js多级目录</span><br/><br/>请输入文件名称';
			}
			
			$.messager.prompt('新建' + title, promptContent, function(r){
				if (r){
		            $.post(url,{'parentPath': row.attributes.path, 'name':r},function(data){
		            	$('#tt').treegrid('reload', row.id);
			            $.messager.alert('提示', data.message, 'info');
		    	    });						
				}
			});
		});
	
		$('#tb-move').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
			
			
		});
		
		$('#tb-compress').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
			$.messager.prompt('压缩', '<strong>注意：</strong><span>如果选择的文件比较大，速度可能比较慢！</span><br/>请输入 <strong>ok</strong> 进行压缩并下载：', function(r){
				if (r && ('ok' == r)){
					$.post('${ctx}/system/editor/compress', {'paths':paths}, function(data){
						if(data.success){
							$.each(rows,function(index,row){
								$('#tt').treegrid('remove', row.id);
							});
			            }
						$.messager.alert('提示', data.message, 'info');
					});
				}
			});
		});
		
		$('#tb-uncompress').bind('click', function(){
			//判断是否选择了节点
			var row = $('#tt').treegrid('getSelected');
			if(!row){
				$.messager.alert('提示', '请选择需要操作的节点', 'info');
				return;
			}
			
		});
	});
	function formatText(val, row){
		return (row.attributes.canEdit && !row.attributes.isDirectory) ? '<a href="javascript:void(0);" onclick="editFile(\'' + row.attributes.path + '\')">' + val + '</a>' : val;
	}
	
	function editFile(path){
		$.ewcms.openWindow({title:'文件编辑',src:'${ctx}/system/editor/save?path=' + path,width:950,height:450});
	}
</script>