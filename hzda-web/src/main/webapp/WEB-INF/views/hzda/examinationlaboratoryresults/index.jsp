<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/hzda/examinationlaboratoryresults/query/${generalInformationId}',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true" rowspan="2"></th>
			    <th data-options="field:'id',hidden:true" rowspan="2">编号</th>
			    <th data-options="field:'examinationDate',width:120" rowspan="2">日期</th>
			    <th colspan="9">血液</th>
			    <th colspan="3">24h 尿 </th>
			</tr>
			<tr>
				<th data-options="field:'bloodALP',width:100">ALP(U/L)</th>
			    <th data-options="field:'bloodCr',width:100">Cr(umol/L)</th>
			    <th data-options="field:'bloodCa',width:100">Ca(mmol/L)</th>
				<th data-options="field:'bloodP',width:100">P(mmol/L)</th>
				<th data-options="field:'bloodTco2',width:100">Tco2(mmol/L)</th>
				<th data-options="field:'bloodOsteocalcin',width:100">骨钙素(ng/ml)</th>
				<th data-options="field:'bloodPINP',width:100">PINP(ng/ml)</th>
			    <th data-options="field:'bloodCTX',width:100">CTX(ng/ml)</th>
			    <th data-options="field:'bloodVitD',width:100">VitD(ng/ml)</th>
			    <th data-options="field:'urinePTH',width:100">PTH(pg/ml)</th>	
			   	<th data-options="field:'urineCa',width:100">Ca(mmol/L)</th>
				<th data-options="field:'urineP',width:100">P(mmol/L)</th>			
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/hzda/examinationlaboratoryresults/save/${generalInformationId}',title:'新增',width:500,height:260,left:100});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/hzda/examinationlaboratoryresults/save/${generalInformationId}',title:'修改',width:500,height:260,left:100});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/hzda/examinationlaboratoryresults/delete/${generalInformationId}',title:'删除'});">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$.ewcms.query();">刷新</a>
		</div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>