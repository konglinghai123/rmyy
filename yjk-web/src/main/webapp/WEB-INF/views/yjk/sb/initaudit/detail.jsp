<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="匹配的医院药品目录"/>
	<table id="tt1" class="easyui-datagrid" data-options="url:'${ctx}/yjk/zd/commonnamecontents/${commonNameContentsId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
				<th data-options="field:'commonName',width:150">大目录通用名</th>	
				<th data-options="field:'projectName',width:100">项目名称</th>
				<th data-options="field:'batch',width:80">批次</th>
				<th data-options="field:'source',width:100">来源</th>
				<th data-options="field:'pill',width:100">剂型</th>
				<th data-options="field:'specifications',width:150">规格</th>
				<th data-options="field:'amount',width:70">数量</th>
				<th data-options="field:'productName',width:100">商品名</th>
				<th data-options="field:'packageUnit',width:80">包装单位</th>
				<th data-options="field:'purchasePrice',width:50">采购价</th>
				<th data-options="field:'packageMaterials',width:50">包材</th>
				<th data-options="field:'minimalUnit',width:80">最小制剂单位</th>
				<th data-options="field:'medicalDirNo',width:80">医保编号</th>
				<th data-options="field:'medicalDirName',width:120">医保目录药品名称</th>
				<th data-options="field:'medicalDirPill',width:80">医保药品剂型</th>
				<th data-options="field:'manufacturer',width:300">生产企业</th>
		</thead>
	</table>
	<table id="tt2" class="easyui-datagrid" data-options="url:'${ctx}/yjk/zd/hospitalcontents/${commonNameContentsId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>	
				<th data-options="field:'commonName',width:100">院目录通用名</th>			    
			    <th data-options="field:'drugCode',width:120">药品代码</th>
				<th data-options="field:'pill',width:100">剂型</th>					
				<th data-options="field:'specifications',width:120">规格</th>
				<th data-options="field:'amount',width:70">数量</th>
				<th data-options="field:'drugMajor',width:150">药品分类大类</th>
				<th data-options="field:'drugCategory',width:120">药品分类</th>
				<th data-options="field:'discom',width:80">配送公司</th>
				<th data-options="field:'manufacturer',width:300">生产企业</th>
		</thead>
	</table>
<ewcms:editWindow/>
<ewcms:footer/>