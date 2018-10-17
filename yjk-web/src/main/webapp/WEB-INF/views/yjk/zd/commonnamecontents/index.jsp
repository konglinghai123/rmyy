<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-大通用名总目录"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'common',width:120,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.commonName;
						}">通用名</th>		
				<th data-options="field:'extractCommonName',width:120">提取通用名</th>
				<th data-options="field:'projectName',width:100">项目名称</th>
				<th data-options="field:'batch',width:50">批次</th>
				<th data-options="field:'source',width:50">来源</th>
				<th data-options="field:'medicalDirNo',width:80">医保编号</th>
				<th data-options="field:'medicalDirName',width:120">医保目录药品名称</th>
				<th data-options="field:'medicalDirPill',width:80">医保药品剂型</th>
				<th data-options="field:'pill',width:50">剂型</th>
				<th data-options="field:'specNumber',width:70">规格*数量</th>
				<th data-options="field:'productName',width:50">商品名</th>
				<th data-options="field:'packageUnit',width:80">包装单位</th>
				<th data-options="field:'manufacturer',width:120">生产企业</th>
				<th data-options="field:'purchasePrice',width:50">采购价</th>
				<th data-options="field:'packageMaterials',width:50">包材</th>
				<th data-options="field:'minimalUnit',width:50">最小制剂单位</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:750,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:750,height:450});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">通用名</td>
              			<td width="15%"><input type="text" name="LIKE_commonName.commonName" style="width:140px;"/></td>
            			<td width="5%">剂型</td>
              			<td width="15%"><input type="text" name="LIKE_pill" style="width:140px;"/></td>
            			<td width="7%">规格*数量</td>
              			<td width="15%"><input type="text" name="LIKE_specNumber" style="width:140px;"/></td>
              			<td width="5%">生产企业</td>
              			<td width="15%"><input type="text" name="LIKE_manufacturer" style="width:140px;"/></td>
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
			url:'${ctx}/yjk/zd/commonnamecontents/query',
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