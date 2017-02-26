<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="追溯查询"/>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:false,border:false" style="height:65px">
			<table style="width:100%;height:90%">
        		<tr>
        			<td width="40%" style="text-align: left"><img src="${ctx}/static/image/top.jpg" height="35px" border="0" style="border:0;padding-left:4px;padding-top:10px;"/> | 查询系统</td>
        			<td width="50%">
        				<h1>追溯查询系统</h1>
        			</td>
        		</tr>
        	</table>
		</div>
		<div data-options="region:'south',split:false,border:false" style="text-align:center;height:50px;padding-top:10px;">
			入库号：<input id="inResult" name="inResult" type="text"/>&nbsp;<a id="tb-yuwei" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">院外追溯</a>
		</div>
		<div data-options="region:'center'" style="overflow:auto;">
			<table id="tt">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',width:230,sortable:true">ID</th>
						<th data-options="field:'cpid',width:230,sortable:true">CPID</th>
						<th data-options="field:'productId',width:150,sortable:true">产品序号</th>
						<th data-options="field:'productNumber',width:150,sortable:true">产品编号</th>
						<th data-options="field:'productName',width:230,sortable:true">产品名称</th>
						<th data-options="field:'productCategory',width:145,sortable:true">产品分类</th>
						<th data-options="field:'productGroup',width:145,sortable:true">产品组别</th>
						<th data-options="field:'unit',width:50,sortable:true">单位</th>
						<th data-options="field:'pinYin',width:100,sortable:true">拼音</th>
						<th data-options="field:'standard',width:100,sortable:true">规格</th>
						<th data-options="field:'model',width:100,sortable:true">型号</th>
						<th data-options="field:'supplier',width:200,sortable:true">供应商</th>
						<th data-options="field:'manufacturer',width:200,sortable:true">生产厂家</th>
						<th data-options="field:'minQuantity',width:70,sortable:true">最小数量</th>
						<th data-options="field:'maxQuantity',width:70,sortable:true">最大数量</th>
						<th data-options="field:'unitPrice',width:70,sortable:true">出货单价</th>
						<th data-options="field:'purchasePrice',width:70,sortable:true">进货单价</th>
						<th data-options="field:'minPrice',width:70,sortable:true">最低单价</th>
						<th data-options="field:'maxPrice',width:70,sortable:true">最高单价</th>
						<th data-options="field:'remark',width:200,sortable:true">备注</th>
						<th data-options="field:'regNumber',width:260,sortable:true">注册证号</th>
						<th data-options="field:'dTime',width:145,sortable:true">时间</th>
						<th data-options="field:'userId',width:230,sortable:true">用户编号</th>
						<th data-options="field:'depId',width:230,sortable:true">部门编号</th>
						<th data-options="field:'hisdm',width:100,sortable:true">HISDM</th>
						<th data-options="field:'state',width:70,sortable:true">状态</th>
					</tr>
				</thead>
			</table>
			<div id="tb" style="padding:5px;height:auto;">
				<div class="toolbar" style="margin-bottom:2px">
				</div>
		        <div style="padding-left:5px;">
		        	<form id="queryform" style="padding:0;margin:0;">
		        		<table class="formtable">
		              		<tr>
		              			<td width="6%">ID</td>
		              			<td width="19%"><input type="text" name="LIKE_id" style="width:140px"/></td>
		    					<td width="6%">CPID</td>
		    					<td width="19%"><input type="text" name="LIKE_cpid" style="width:140px"/></td>
		    					<td width="6%">产品序号</td>
		    					<td width="19%"><input type="text" name="LIKE_productId" style="width:140px"/></td>
								<td width="25%" colspan="2">
		            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
		           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
		           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多...</span></a>
		           				</td>
		           			</tr>
		           			<tr>
		           				<td>产品编号</td>
		           				<td><input type="text" name="LIKE_productNumber" style="width:140px"/></td>
		           				<td>产品名称</td>
		           				<td><input type="text" name="LIKE_productName" style="width:140px"/></td>
		           				<td>产品分类</td>
		           				<td><input type="text" name="LIKE_productCategory" style="width:140px"/></td>
		           				<td>产品组别</td>
		           				<td><input type="text" name="LIKE_productGroup" style="width:140px"/></td>
		           			</tr>
		           			<tr>
		           				<td>单位</td>
		           				<td><input type="text" name="LIKE_unit" style="width:140px"/></td>
		           				<td>拼音</td>
		           				<td><input type="text" name="LIKE_pinYin" style="width:140px"/></td>
		           				<td>规格</td>
		           				<td><input type="text" name="LIKE_standard" style="width:140px"/></td>
		           				<td>型号</td>
		           				<td><input type="text" name="LIKE_model" style="width:140px"/></td>
		           			</tr>
		           			<tr>
		           				<td>供应商</td>
		           				<td><input type="text" name="LIKE_supplier" style="width:140px"/></td>
		           				<td>生产厂家</td>
		           				<td><input type="text" name="LIKE_manufacturer" style="width:140px"/></td>
		           				<td>最小数量</td>
		           				<td><input type="text" id="minQuantity" name="GTE_minQuantity" style="width:100px"/> 至 <input type="text" id="minQuantity" name="LTE_minQuantity" style="width:100px"/></td>
		           				<td>最大数量</td>
		           				<td><input type="text" id="maxQuantity" name="GTE_maxQuantity" style="width:100px"/> 至 <input type="text" id="maxQuantity" name="LTE_maxQuantity" style="width:100px"/></td>
		           			</tr>
		           			<tr>
		           				<td>出货单价</td>
		           				<td><input type="text" id="unitPrice" name="GTE_unitPrice" style="width:100px"/> 至 <input type="text" id="unitPrice" name="LTE_unitPrice" style="width:100px"/></td>
		           				<td>进货单价</td>
		           				<td><input type="text" id="purchasePrice" name="GTE_purchasePrice" style="width:100px"/> 至 <input type="text" id="purchasePrice" name="LTE_purchasePrice" style="width:100px"/></td>
		           				<td>最低单价</td>
		           				<td><input type="text" id="minPrice" name="GTE_minPrice" style="width:100px"/> 至 <input type="text" id="minPrice" name="LTE_minPrice" style="width:100px"/></td>
		           				<td>最高单价</td>
		           				<td><input type="text" id="maxPrice" name="GTE_maxPrice" style="width:100px"/> 至 <input type="text" id="maxPrice" name="LTE_maxPrice" style="width:100px"/></td>
		           			</tr>
		           			<tr>
		           				<td>备注</td>
		           				<td><input type="text" name="LIKE_remark" style="width:140px"/></td>
		           				<td>注册证号</td>
		           				<td><input type="text" name="LIKE_regNumber" style="width:140px"/></td>
		           				<td>状态</td>
		           				<td><input type="text" name="EQ_state" style="width:140px"/></td>
		           				<td>时间</td>
		           				<td><input type="text" id="dTime" name="GTE_dTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="dTime" name="LTE_dTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           			</tr>
		           			<tr>
		           				<td>用户编号</td>
		           				<td><input type="text" name="LIKE_userId" style="width:140px"/></td>
		           				<td>部门编号</td>
		           				<td><input type="text" name="LIKE_depId" style="width:140px"/></td>
		           				<td>HISDM</td>
		           				<td><input type="text" name="LIKE_hisdm" style="width:140px"/></td>
		           				<td>&nbsp;</td>
		           				<td>&nbsp;</td>
		           			</tr>
		           			<tr>
		           				<td>入库时间</td>
		           				<td><input type="text" id="inDateTime" name="GTE_inDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="inDateTime" name="LTE_inDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>入库备注</td>
		           				<td><input type="text" name="LIKE_inRemar" style="width:140px"/></td>
		           				<td>出库时间</td>
		           				<td><input type="text" id="outDateTime" name="GTE_outDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="outDateTime" name="LTE_outDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>出库备注</td>
		           				<td><input type="text" name="LIKE_outRemark" style="width:140px"/></td>
		           			</tr>
		           			<tr>
		           				<td>订单时间</td>
		           				<td><input type="text" id="orderDateTime" name="GTE_orderDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="orderDateTime" name="LTE_orderDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>订单备注</td>
		           				<td><input type="text" name="LIKE_orderRemark" style="width:140px"/></td>
		           				<td>领用时间</td>
		           				<td><input type="text" id="collarDateTime" name="GTE_collarDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="collarDateTime" name="LTE_collarDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>领用备注</td>
		           				<td><input type="text" name="LIKE_collarDateTime" style="width:140px"/></td>
		           			</tr>
		           			<tr>
		           				<td>换货时间</td>
		           				<td><input type="text" id="changeDateTime" name="GTE_changeDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="changeDateTime" name="LTE_changeDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>换货备注</td>
		           				<td><input type="text" name="LIKE_changeRemark" style="width:140px"/></td>
		           				<td>调拔时间</td>
		           				<td><input type="text" id="transferDateTime" name="GTE_transferDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="transferDateTime" name="LTE_transferDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>调拔备注</td>
		           				<td><input type="text" name="LIKE_transferDateTime" style="width:140px"/></td>
		           			</tr>
		           			<tr>
		           				<td>退货时间</td>
		           				<td><input type="text" id="returnDateTime" name="GTE_returnDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="returnDateTime" name="LTE_returnDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>退货备注</td>
		           				<td><input type="text" name="LIKE_returnRemark" style="width:140px"/></td>
		           				<td>计费时间</td>
		           				<td><input type="text" id="tollDateTime" name="GTE_tollDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="tollDateTime" name="LTE_tollDateTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
		           				<td>计费备注</td>
		           				<td><input type="text" name="LIKE_tollDateTime" style="width:140px"/></td>
		           			</tr>
		           		</table>
		          </form>
		        </div>
			</div>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/trace/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
		    view: detailview,
		    detailFormatter: function(rowIndex, rowData){
		        return '<table><tr>' +
		                '<td style="border:0">' + 
		                ((rowData.inDateTim) ? '<p>入库时间：' + rowData.inDateTime + ((rowData.inRemark)? '，备注：' + rowData.inRemark : '') + '</p>' : '') +
		                ((rowData.outDateTim) ? '<p>出库时间：' + rowData.outDateTime + ((rowData.outRemark)? '，备注：' + rowData.outRemark : '') + '</p>' : '') +
		                ((rowData.orderDateTime) ? '<p>订单时间：' + rowData.orderDateTime + ((rowData.orderRemark)? '，备注：' + rowData.orderRemark : '') + '</p>' : '') +
		                ((rowData.collarDateTime) ? '<p>领用时间：' + rowData.collarDateTime + ((rowData.collarRemark)? '，备注：' + rowData.collarRemark : '') + '</p>' : '') +
		                ((rowData.changeDateTime) ? '<p>换货时间：' + rowData.changeDateTime + ((rowData.changeRemark)? '，备注：' + rowData.changeRemark : '') + '</p>' : '') +
		                ((rowData.transferDateTime) ? '<p>调拔时间：' + rowData.transferDateTime + ((rowData.transferRemark)? '，备注：' + rowData.transferRemark : '') + '</p>' : '') +
		                ((rowData.returnDateTime) ? '<p>退货时间：' + rowData.returnDateTime + ((rowData.returnRemark)? '，备注：' + rowData.returnRemark : '') + '</p>' : '') +
		                ((rowData.tollDateTime) ? '<p>计费时间：' + rowData.tollDateTime + ((rowData.tollRemark)? '，备注：' + rowData.tollRemark : '') + '</p>' : '') +
		                ((rowData.d1)? '<p><img src="${ctx}/trace/lookImage?field=d1&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d2)? '<p><img src="${ctx}/trace/lookImage?field=d2&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d3)? '<p><img src="${ctx}/trace/lookImage?field=d3&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d4)? '<p><img src="${ctx}/trace/lookImage?field=d4&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d5)? '<p><img src="${ctx}/trace/lookImage?field=d5&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d6)? '<p><img src="${ctx}/trace/lookImage?field=d6&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d7)? '<p><img src="${ctx}/trace/lookImage?field=d7&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d8)? '<p><img src="${ctx}/trace/lookImage?field=d8&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d9)? '<p><img src="${ctx}/trace/lookImage?field=d9&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                ((rowData.d10)? '<p><img src="${ctx}/trace/lookImage?field=d10&cpid=' + rowData.cpid + '" style="width:600px;float:left"></p>' : '') +
		                '</td>' +
		                '</tr></table>';
		    }
		});
		
		$("form table tr").next("tr").hide();
		$('#tb-more').bind('click', function(){
	       	var showHideLabel_value = $('#showHideLabel').text();
	    	$('form table tr').next('tr').toggle();
	     	if (showHideLabel_value == '收缩'){
	     		$('#showHideLabel').text('更多...');
	    	}else{
	    		$('#showHideLabel').text('收缩');
	    	}
	    	$('#tt').datagrid('resize');
	    });
	    $('#tb-yuwei').bind('click', function(){
		    if ($.trim($('#inResult').val())==''){
		    	alert('入库单不能为空');
			    return;
            } else {
				window.open("/result.html", 'newwindow');
			}
		});
	});	
</script>