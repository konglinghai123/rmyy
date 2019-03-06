<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/hzda/fracture/query/${generalInformationId}',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'examinationDate',width:120">日期</th>
			    <th data-options="field:'part',width:100">部位</th>
				<th data-options="field:'degree'">检查项目</th>
				<th data-options="field:'reason',width:100">结论</th>
			    <th data-options="field:'remark'">备注</th>	
			   <th data-options="field:'formatName',width:100,formatter:formatLookPicture">上传图片</th>		
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<c:if test="${!user.admin}">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/hzda/fracture/save/${generalInformationId}',title:'新增',width:500,height:260,left:100});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/hzda/fracture/save/${generalInformationId}',title:'修改',width:500,height:260,left:100});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/hzda/fracture/delete/${generalInformationId}',title:'删除'});">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$.ewcms.query();">刷新</a>
		</div>
		</c:if>
	</div>
    <ewcms:editWindow/>
    
		<script type="text/javascript">
			$(function(){
				$('#tt').datagrid({
					onLoadSuccess:function(row){
						$('.printCls').linkbutton({text:'查看图片',plain:true,iconCls:'icon-reload'});
					}
				});
			});			
			function formatLookPicture(val, row){
				if (val == ''){
					return val + '&nbsp;|<a class="printCls" onclick="lookPicture(' + row.id + ')" style="height:24px;" href="javascript:void(0);"></a>'
				}else{
					return '无图片';
				}
			}

			function lookPicture(id){
				$.ewcms.openTopWindow({src:'${ctx}/hzda/fracture/lookpicture?fractureId=' + id,title:'查看图片',isRefresh:false,maximizable:true});
			}

		</script>
    
<ewcms:footer/>