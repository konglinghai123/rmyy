<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="匹配的医院药品目录"/>
	<table id="tt2" class="easyui-datagrid" data-options="url:'${ctx}/yjk/zd/hospitalcontents/${commonNameContentsId}/query',nowrap:true,rownumbers:true,fit:true,striped:true,pageSize:10">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>	
			    <th data-options="field:'common.commonName',width:200,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.commonName;
						}">通用名</th>
			    <th data-options="field:'commonName',width:200">院用目录通用名</th>
			    <th data-options="field:'administration.name',sortable:true,width:80,
						formatter:function(val,row){
							return row.administration==null?'':row.administration.name;
						}">给药途径</th>
				<th data-options="field:'drugCategoryInfo',width:80,
						formatter:function(val,row){
							return row.common==null?'':row.common.drugCategoryInfo;
						}">药品种类</th>							
				<th data-options="field:'bidCommonName',width:200,
						formatter:function(val,row){
							return row.common==null?'':row.common.bidCommonName;
						}">省招标通用名</th>		
				<th data-options="field:'bidDrugId',width:150">省招标药品ID</th>	
				<th data-options="field:'projectName',width:150,sortable:true">项目名称</th>					    
			    <th data-options="field:'drugCode',width:100">药品代码</th>
				<th data-options="field:'pill',width:100">剂型</th>	
				<th data-options="field:'productName',width:100">商品名</th>				
				<th data-options="field:'specifications',width:120">规格</th>
				<th data-options="field:'amount',width:70">包装数量</th>
				<th data-options="field:'manufacturer',width:300">生产企业</th>
				<th data-options="field:'importEnterprise',width:300">进口企业</th>	
				<th data-options="field:'bidPrice',width:80">中标价</th>		
				<th data-options="field:'medical',width:80">医保</th>	
				<th data-options="field:'limitRange',width:100">限制范围</th>
				<th data-options="field:'chemicalBigCategory',width:200,
						formatter:function(val,row){
							return row.common==null?'':row.common.chemicalBigCategory;
						}">大类</th>	
				<th data-options="field:'chemicalSubCategory',width:200,
						formatter:function(val,row){
							return row.common==null?'':row.common.chemicalSubCategory;
						}">小类</th>
			</tr>
		</thead>
	</table>
<ewcms:footer/>