<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-医院在用药品总目录"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>	
				<th data-options="field:'common',width:120,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.commonName;
						}">通用名</th>
				<th data-options="field:'commonName',width:100">院目录通用名</th>			    
			    <th data-options="field:'drugCode',width:120">药品代码</th>
				<th data-options="field:'pill',width:100">剂型</th>					
				<th data-options="field:'specifications',width:70">规格</th>
				<th data-options="field:'amount',width:70">数量</th>
				<th data-options="field:'manufacturer',width:120">生产企业</th>
				<th data-options="field:'drugMajor',width:120">药品分类大类</th>
				<th data-options="field:'drugCategory',width:80">药品分类</th>
				<th data-options="field:'discom',width:80">配送公司</th>
				<!-- <th data-options="field:'deleted',width:100,
						formatter:function(val,row){
							return val ? '<font color=red>已删除</font>' : '';
						}">是否删除</th>-->
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:750,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:750,height:450});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-import',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入医院在用目录信息',src:'${ctx}/yjk/zd/hospitalcontents/import'});">导入</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform">
        		<table class="formtable">
              		<tr>
              			<td width="5%">药品代码</td>
              			<td width="15%"><input type="text" name="LIKE_drugCode" style="width:140px;"/></td>              		
              			<td width="5%">药品通用名</td>
              			<td width="15%"><input type="text" name="LIKE_genericDrugName" style="width:140px;"/></td>
            			<td width="5%">剂型</td>
              			<td width="15%"><input type="text" name="LIKE_pill" style="width:140px;"/></td>
						<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多</span></a>
           				</td>
           			</tr>
           			<tr>
            			<td width="7%">规格*数量</td>
              			<td width="15%"><input type="text" name="LIKE_specNumber" style="width:140px;"/></td>
              			<td width="5%">生产企业</td>
              			<td width="15%"><input type="text" name="LIKE_manufacturer" style="width:140px;"/></td>
              			<td width="15%" colspan="2"></td>
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
			url:'${ctx}/yjk/zd/hospitalcontents/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false
		});
		
		
		$("form table tr").next("tr").hide();
		$('#tb-more').bind('click', function(){
	       	var showHideLabel_value = $('#showHideLabel').text();
	    	$('form table tr').next('tr').toggle();
	     	if (showHideLabel_value == '收缩'){
	     		$('#showHideLabel').text('更多');
	    	}else{
	    		$('#showHideLabel').text('收缩');
	    	}
	    	$('#tt').datagrid('resize');
	    });
	});

</script>