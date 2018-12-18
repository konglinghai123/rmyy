<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="匹配的医院药品目录"/>
	<table id="tt2" class="easyui-datagrid" data-options="url:'${ctx}/yjk/zd/hospitalcontents/${commonNameContentsId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>	
			    <th data-options="field:'commonName',width:200">院用目录通用名</th>
				<th data-options="field:'drugCategoryInfo',width:80,
						formatter:function(val,row){
							return row.common==null?'':row.common.drugCategoryInfo;
						}">药品种类</th>							
					
				<th data-options="field:'bidCommonName',width:200,
						formatter:function(val,row){
							return row.common==null?'':row.common.bidCommonName;
						}">省招标通用名</th>		
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

		</thead>
	</table>
	<table id="tt1" class="easyui-datagrid" data-options="url:'${ctx}/yjk/zd/commonnamecontents/${commonNameContentsId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
				<th data-options="field:'commonName',width:150">大目录通用名</th>
				<th data-options="field:'drugCategoryInfo',width:80">药品种类</th>												
				
				<th data-options="field:'bidCommonName',width:150,
						formatter:function(val,row){
							return row.common==null?'':row.common.bidCommonName;
						}">省招标通用名</th>							
				<th data-options="field:'projectName',width:150,sortable:true">项目名称</th>
				<th data-options="field:'batch',width:80">批次</th>
				<th data-options="field:'pill',width:100">剂型</th>				
				<th data-options="field:'bidDrugId',width:150">省招标药品ID</th>
				<th data-options="field:'countryId',width:100">国家ID</th>
				<th data-options="field:'specifications',width:150">规格</th>
				<th data-options="field:'amount',width:70">包装数量</th>
				<th data-options="field:'productName',width:100">商品名</th>
				<th data-options="field:'packageUnit',width:80">包装单位</th>
				<th data-options="field:'manufacturer',width:300">生产企业</th>
				<th data-options="field:'importEnterprise',width:300,sortable:true">进口企业</th>
				<th data-options="field:'purchasePrice',width:50">采购价</th>
				<th data-options="field:'medicalDirNo',width:80">医保目录编号</th>
				<th data-options="field:'medicalCategory',width:120">医保类别</th>
				<th data-options="field:'medicalRemark',width:80">医保备注</th>
				<th data-options="field:'consistencyEvaluation',width:100">一致性评价</th>
				<th data-options="field:'heds',width:80">基药</th>
				<th data-options="field:'gynaecology',width:80">妇科</th>
				<th data-options="field:'pediatric',width:80">儿科</th>
				<th data-options="field:'firstAid',width:80">急救</th>	
				<th data-options="field:'basicInfusion',width:100">基础输液</th>
				<th data-options="field:'cheapShortage',width:80">廉价短缺</th>
				<th data-options="field:'negotiationVariety',width:80">国家谈判品种</th>
				<th data-options="field:'chemicalBigCategory',width:200,
						formatter:function(val,row){
							return row.common==null?'':row.common.chemicalBigCategory;
						}">大类</th>	
				<th data-options="field:'chemicalSubCategory',width:200,
						formatter:function(val,row){
							return row.common==null?'':row.common.chemicalSubCategory;
						}">小类/功效</th>	
				<th data-options="field:'licenseNumber',width:100">批准文号</th>
				<th data-options="field:'bidPill',width:80">招标剂型</th>
				<th data-options="field:'bidSpecifications',width:80">招标规格</th>
				<th data-options="field:'bidUnit',width:80">招标单位</th>
				<th data-options="field:'packageMaterials',width:50">包材</th>
				<th data-options="field:'minimalUnit',width:80">最小制剂单位</th>
		</thead>
	</table>
<ewcms:editWindow/>
<ewcms:footer/>