<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="评审-评审专家"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',sortable:true">编号</th>
			    <th data-options="field:'weight',hidden:true">排序号</th>
				<th data-options="field:'percent',width:60,
						formatter:function(val,row){
							return val != null ? val + '%' : '';
						}">百分比</th>
				<th data-options="field:'randomNumber',width:80">随机人数</th>
				<th data-options="field:'departmentNumber',width:100">每科室人数</th>
				<th data-options="field:'director',width:100,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							else return val ? '是' : '否';
						}">是否科主任</th>
				<th data-options="field:'secondDirector',width:100,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							else return val ? '是' : '否';
						}">是否科副主任</th>
				<th data-options="field:'pharmacy',width:120,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							return val ? '是' : '否';
						}">是否药事会成员</th>
				<th data-options="field:'science',width:150,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							else return val ? '是' : '否';
						}">是否院学术委员会成员</th>
				<th data-options="field:'antibiosis',width:180,
						formatter:function(val,row){
							if (typeof(val)=='undefined') return '/';
							else return val ? '是' : '否';
						}">是否抗菌药物遴选小组成员</th>
				<th data-options="field:'organizationNames',width:200">科室/病区</th>
				<th data-options="field:'departmentAttributeNames',width:200">科室属性</th>
				<th data-options="field:'professionNames',width:100">执业类别</th>
				<th data-options="field:'technicalTitleNames',width:200">技术职称(资格)</th>
				<th data-options="field:'appointmentNames',width:200">聘任</th>
				<th data-options="field:'enabled',width:60,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否启用</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
        	<c:if test="${isOperation}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/save',title:'新增',width:850,height:450,left:100});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({src:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/save',title:'修改',width:850,height:450,left:100});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="$.ewcms.remove({src:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/delete',title:'删除'});">删除</a>
			<a id="tb-exchange" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-status-hide'">互换</a>
			<a id="tb-status" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-status',iconCls:'icon-status'">状态</a>
			</c:if>
		</div>
		<c:if test="${isOperation}">
		<div id="menu-status" style="width:150px">
			<div id="menu-status-show" data-options="iconCls:'icon-status-show'" onclick="$.ewcms.status({status:'true',info:'启用',prompt:false});">启用</div>
			<div id="menu-status-hide" data-options="iconCls:'icon-status-hide'" onclick="$.ewcms.status({status:'false',info:'封禁',prompt:false});">封禁</div>
		</div>
		</c:if>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/reviewexpert/${reviewMainId}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:true,
	        rowStyler: function(index,row){
	        	if (!row.enabled){
	    			return 'background-color:#FF0000;color:#FFFFFF;';
	        	}
	    	},
			onBeforeLoad:function(param){
				param['parameters']=$('#queryform').serializeObject();
			}
		});
		
		$('#tb-exchange').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    
	    	if(rows.length != 2){
		        $.messager.alert('提示','请选择2条互换的记录','info');
		        return;
		    }
		    $.messager.confirm('提示', '确定要互换所选记录吗?', function(r){
				if (r){
					if(rows[0].weight<rows[1].weight){
						$.post('${ctx}/yjk/re/reviewexpert/' + rows[1].id + '/' + rows[0].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}else{
						$.post('${ctx}/yjk/re/reviewexpert/' + rows[0].id + '/' + rows[1].id + '/down', {}, function(result) {
							if (result.success){
								$('#tt').datagrid('clearSelections');
								$('#tt').datagrid('reload');
							}
							$.messager.alert('提示', result.message, 'info');
						});
					}
				}
			});
		});
	});
</script>