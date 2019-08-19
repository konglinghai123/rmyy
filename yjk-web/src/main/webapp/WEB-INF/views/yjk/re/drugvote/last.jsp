<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>
<c:choose>
<c:when test="${isLast}">
<ewcms:head title="最终结果"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'chosen',width:90,
						formatter:function(val,row){
							return val ? '入围' : '';
						}">最终结果</th>
			    <th data-options="field:'commonNameContents.common.commonName',width:200,
						formatter:function(val,row){
							return (row.commonNameContents==null || row.commonNameContents.common==null)?'':row.commonNameContents.common.commonName;
						}">通用名</th>
				<th data-options="field:'commonNameContents.common.matchNumber',width:80,
						formatter:function(val,row){
							return (row.commonNameContents==null || row.commonNameContents.common==null)?'':row.commonNameContents.common.matchNumber;
						}">匹配编号</th>
				<th data-options="field:'commonNameContents.administration.name',width:80,
						formatter:function(val,row){
							return (row.commonNameContents==null || row.commonNameContents.administration==null)?'':row.commonNameContents.administration.name;
						}">给药途径</th>
				<th data-options="field:'commonNameContents.drugCategoryInfo',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.drugCategoryInfo;
						}">药品种类</th>												
				<th data-options="field:'commonNameContents.commonName',width:150,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.commonName;
						}">大目录通用名</th>
				<th data-options="field:'commonNameContents.common.bidCommonName',width:150,
						formatter:function(val,row){
							return (row.commonNameContents==null || row.commonNameContents.common==null)?'':row.commonNameContents.common.bidCommonName;
						}">省招标通用名</th>							
				<th data-options="field:'commonNameContents.projectName',width:150,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.projectName;
						}">项目名称</th>
				<th data-options="field:'commonNameContents.batch',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.batch;
						}">批次</th>
				<th data-options="field:'commonNameContents.pill',width:100,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.pill;
						}">剂型</th>				
				<th data-options="field:'commonNameContents.bidDrugId',width:150,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.bidDrugId;
						}">省招标药品ID</th>
				<th data-options="field:'commonNameContents.countryId',width:100,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.countryId;
						}">国家ID</th>
				<th data-options="field:'commonNameContents.specifications',width:150,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.specifications;
						}">规格</th>
				<th data-options="field:'commonNameContents.amount',width:70,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.amount;
						}">包装数量</th>
				<th data-options="field:'commonNameContents.productName',width:100,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.productName;
						}">商品名</th>
				<th data-options="field:'commonNameContents.packageUnit',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.packageUnit;
						}">包装单位</th>
				<th data-options="field:'commonNameContents.manufacturer',width:300,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.manufacturer;
						}">生产企业</th>
				<th data-options="field:'commonNameContents.importEnterprise',width:300,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.importEnterprise;
						}">进口企业</th>
				<th data-options="field:'commonNameContents.purchasePrice',width:50,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.purchasePrice;
						}">采购价</th>
				<th data-options="field:'commonNameContents.medicalDirNo',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.medicalDirNo;
						}">医保目录编号</th>
				<th data-options="field:'commonNameContents.medicalCategory',width:120,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.medicalCategory;
						}">医保类别</th>
				<th data-options="field:'commonNameContents.medicalRemark',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.medicalRemark;
						}">医保备注</th>
				<th data-options="field:'commonNameContents.consistencyEvaluation',width:100,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.consistencyEvaluation;
						}">一致性评价</th>
				<th data-options="field:'commonNameContents.heds',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.heds;
						}">基药</th>
				<th data-options="field:'commonNameContents.gynaecology',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.gynaecology;
						}">妇科</th>
				<th data-options="field:'commonNameContents.pediatric',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.pediatric;
						}">儿科</th>
				<th data-options="field:'commonNameContents.firstAid',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.firstAid;
						}">急救</th>	
				<th data-options="field:'commonNameContents.basicInfusion',width:100,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.basicInfusion;
						}">基础输液</th>
				<th data-options="field:'commonNameContents.cheapShortage',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.cheapShortage;
						}">廉价短缺</th>
				<th data-options="field:'commonNameContents.negotiationVariety',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.negotiationVariety;
						}">国家谈判品种</th>
				<th data-options="field:'commonNameContents.common.chemicalBigCategory',width:200,
						formatter:function(val,row){
							return (row.commonNameContents==null || row.commonNameContents.common==null)?'':row.commonNameContents.common.chemicalBigCategory;
						}">大类</th>	
				<th data-options="field:'commonNameContents.common.chemicalSubCategory',width:200,
						formatter:function(val,row){
							return (row.commonNameContents==null || row.commonNameContents.common==null)?'':row.commonNameContents.common.chemicalSubCategory;
						}">小类/功效</th>	
				<th data-options="field:'commonNameContents.licenseNumber',width:100,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.licenseNumber;
						}">批准文号</th>
				<th data-options="field:'commonNameContents.bidPill',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.bidPill;
						}">招标剂型</th>
				<th data-options="field:'commonNameContents.bidSpecifications',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.bidSpecifications;
						}">招标规格</th>
				<th data-options="field:'commonNameContents.bidUnit',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.bidUnit;
						}">招标单位</th>
				<th data-options="field:'commonNameContents.packageMaterials',width:50,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.packageMaterials;
						}">包材</th>
				<th data-options="field:'commonNameContents.minimalUnit',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.minimalUnit;
						}">最小制剂单位</th>
				<th data-options="field:'commonNameContents.remark1',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.remark1;
						}">备注1</th>
				<th data-options="field:'commonNameContents.remark2',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.remark2;
						}">备注2</th>
				<th data-options="field:'commonNameContents.remark3',width:80,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.remark3;
						}">备注3</th>
				<th data-options="field:'commonNameContents.createDate',width:150,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.createDate;
						}">创建时间</th>
				<th data-options="field:'commonNameContents.updateDate',width:150,
						formatter:function(val,row){
							return row.commonNameContents==null?'':row.commonNameContents.updateDate;
						}">修改时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-print" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">打印</a>
		</div>
		<div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">显示情况</td>
              			<td width="23%">
              				<select name="CUSTOM_show" class="easyui-combobox" data-options="editable:false,width:100,panelHeight:'auto'">
              					<option value="all">全部</option>
              					<option value="chosen">入围</option>
              				</select>
              			</td>
            			<td width="5%"></td>
              			<td width="23%"></td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          	</form>
    	</div>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-groupview.js"></script>
<script type="text/javascript">
	var caption = '最终入围结果统计';
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/drugvote/queryLastVoteResult?reviewMainId=${reviewMainId}',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			groupField:'chemicalSubCategory',
		    view: groupview,
		    groupFormatter:function(value, rows){
		        return value + ' - ' + rows.length + ' 条';
		    },
			rowStyler: function(index,row){
				if ((row.selected && row.adjustedInfo!='调出') || (!row.selected && row.adjustedInfo=='调入')){
					if( row.drugForm.commonNameContents.common.drugCategory=='Z'){
						return 'background-color:#DDDDFF;color:#000000;';
					}else{
						return 'background-color:#C4E1FF;color:#000000;';
					}
	    			
	        	}
	    	}
		});
	});
	
	$('#tb-print').bind('click', function(){
		$('#tt').datagrid('toExcel','lastVoteResult.xls');
	});
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
</script>
</c:when>
<c:otherwise>
<ewcms:head title="药品投票情况 - 最终结果"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',fit:true" style="border:0;">	
			<h1 class="title">所有流程还未结束，不能查看最终结果！</h1>
		</div>
	</div>
<ewcms:footer/>
</c:otherwise>
</c:choose>