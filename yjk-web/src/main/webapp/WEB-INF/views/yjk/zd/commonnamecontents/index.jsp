<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-大通用名总目录"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'extractCommonName',width:150,sortable:true">通用名</th>
				<th data-options="field:'number',width:50,
						formatter:function(val,row){
							return row.common==null?'':row.common.number;
						}">编号</th>
				<th data-options="field:'administrationName',width:80">给药途径</th>
				<th data-options="field:'drugCategoryInfo',width:80">药品种类</th>												
				<th data-options="field:'commonName',width:150">大目录通用名</th>	
				<th data-options="field:'projectName',width:100">项目名称</th>
				<th data-options="field:'batch',width:80">批次</th>
				<th data-options="field:'source',width:100">来源</th>
				<th data-options="field:'pill',width:100">剂型</th>
				<th data-options="field:'specifications',width:150">规格</th>
				<th data-options="field:'amount',width:70">包装数量</th>
				<th data-options="field:'productName',width:100">商品名</th>
				<th data-options="field:'packageUnit',width:80">包装单位</th>
				<th data-options="field:'purchasePrice',width:50">采购价</th>
				<th data-options="field:'packageMaterials',width:50">包材</th>
				<th data-options="field:'minimalUnit',width:80">最小制剂单位</th>
				<th data-options="field:'medicalDirNo',width:80">医保编号</th>
				<th data-options="field:'medicalDirName',width:120">医保目录药品名称</th>
				<th data-options="field:'medicalDirPill',width:80">医保药品剂型</th>
				<th data-options="field:'manufacturer',width:300">生产企业</th>
				<th data-options="field:'remark1',width:80">备注1</th>
				<th data-options="field:'remark2',width:80">备注2</th>
				<th data-options="field:'remark3',width:80">备注3</th>
				<th data-options="field:'declared',width:100,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否允许申报</th>
				<th data-options="field:'createDate',width:100">创建时间</th>
				<th data-options="field:'updateDate',width:100">修改时间</th>
				<!--  <th data-options="field:'deleted',width:100,
						formatter:function(val,row){
							return val ? '<font color=red>已删除</font>' : '';
						}">是否删除</th>	-->			
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:750,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:750,height:450});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-import',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入大目录信息',src:'${ctx}/yjk/zd/commonnamecontents/import'});">导入</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">通用名</td>
              			<td width="15%"><input type="text" name="LIKE_common.commonName" style="width:140px;"/></td>
            			<td width="5%">剂型</td>
              			<td width="15%"><input type="text" name="LIKE_pill" style="width:140px;"/></td>
              			<td width="7%">通用名拼音</td>
              			<td width="23%"><input type="text" name="LIKE_spellSimplify" style="width:140px;"/></td>
              			<td width="25%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多</span></a>
           				</td>
           			</tr>
           			<tr>
              			<td width="5%">生产企业</td>
              			<td width="15%"><input type="text" name="LIKE_manufacturer" style="width:140px;"/></td>
              			<td width="5%">商品名</td>
              			<td width="15%"><input type="text" name="LIKE_productName" style="width:140px;"/></td>
            			<td width="7%">规格</td>
              			<td width="15%"><input type="text" name="LIKE_specifications" style="width:140px;"/></td>
            			<td width="7%">数量</td>
              			<td width="15%"><input type="text" name="LIKE_amount" style="width:140px;"/></td>      				
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
			url:'${ctx}/yjk/zd/commonnamecontents/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
	        rowStyler: function(index,row){
	        	if (!row.declared){
	    			return 'background-color:#FF0000;color:#FFFFFF;';
	        	}
	    	}
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