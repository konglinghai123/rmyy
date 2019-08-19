<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ewcms.yjk.YjkConstants" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<c:set var="acn" value="<%=YjkConstants.ACN%>"/>
<c:set var="asap" value="<%=YjkConstants.ASAP%>"/>
<c:choose>
	<c:when test="${isResult}">
<ewcms:head title="最终结果"/>
	<table id="tt">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'hemicalSubCategor',hidden:true">化药小类</th>
				<c:choose>
				<c:when test='${!isClose}'>
				<th data-options="field:'passSum',width:60">通过票</th>
				<th data-options="field:'opposeSum',width:60">反对票</th>
				<th data-options="field:'abstainSum',width:60">弃权票</th>
				<th data-options="field:'selected',width:90,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否拟入围</th>
				<th data-options="field:'adjustedInfo',width:90,formatter:formatOperation">调入/调出</th>
				<th data-options="field:'affirmVoteResulted',width:90,
						formatter:function(val,row){
							return val ? '是' : '';
						}">结果封存</th>
				<th data-options="field:'chosen',width:100,
						formatter:function(val,row){
							return val ? '入围' : '';
						}">本轮最终结果</th>
			    <c:forEach items="${currentReviewProcess.displayColumns}" var="displayColumn" varStatus="status">
 					<c:choose>
	 					<c:when test="${currentReviewProcess.reviewBaseRule.ruleName == acn||currentReviewProcess.reviewBaseRule.ruleName == asap}">
							<th data-options="field:'${displayColumn.ruleName}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return formatTooltip(row.${displayColumn.ruleName}, row);
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:when>
						<c:otherwise>
							<th data-options="field:'${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}',width:${displayColumn.width},
									formatter:function(val,row){
										try{
											if(row.drugForm.commonNameContents==null){
											 	return '';
											}else{
												return formatTooltip(row.${fn:substring(displayColumn.ruleName,9,fn:length(displayColumn.ruleName))}, row);
											}
										}catch(err){
											return '';
										}
									}">${displayColumn.ruleCnName}</th>  						
						</c:otherwise>
					</c:choose>
 				</c:forEach>
 				</c:when>
 				<c:otherwise>
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
 				</c:otherwise>
 				</c:choose>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
        	<c:if test="${!isClose}">
			<a id="tb-transferIn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-forced-open',plain:true">调入</a>
			<a id="tb-callOut" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-forced-closure'">调出</a>
			<a id="tb-affirm" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">结果封存</a>
			<c:choose>
			<c:when test="${isNextEnable}">
			<a id="tb-next" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-next'">进入下一轮</a>
			</c:when>
			<c:otherwise>
			<a id="tb-exit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'">结束投票</a>
			</c:otherwise>
			</c:choose>
			</c:if>
			<a id="tb-print" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">打印</a>
		</div>
		<c:if test="${isClose}">
		<div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">显示情况</td>
              			<td width="23%">
              				<select class="easyui-combobox" name="CUSTOM_show" data-options="editable:false,width:100,panelHeight:'auto'">
              					<option value="all" selected="selected">全部</option>
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
		</c:if>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-groupview.js"></script>
<script type="text/javascript">
	var caption = <c:choose><c:when test='${!isClose}'>"所有用户在 ${currentReviewProcess.reviewBaseRule.ruleCnName} 中投票结果统计"</c:when><c:otherwise>"最终入围结果统计"</c:otherwise></c:choose>;
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voteresult/queryVoteResult',
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
		        return value + ' - ' + rows.length + ' Item(s)';
		    },
			rowStyler: function(index,row){
				if ((row.selected && row.adjustedInfo!='调出') || (!row.selected && row.adjustedInfo=='调入')){
					if( row.drugForm.commonNameContents.common.drugCategory=='Z'){
						return 'background-color:#DDDDFF;color:#000000;';
					}else{
						return 'background-color:#C4E1FF;color:#000000;';
					}
	    			
	        	}
	    	},
			onLoadSuccess:function(row){
				$('.cancelCls').linkbutton({plain:true,iconCls:'icon-cancel'});
			}
		});
	});
	
	$('#tb-print').bind('click', function(){
		$('#tt').datagrid('toExcel','voteResult.xls');
	});
	
	$('#tb-transferIn').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择入围的记录','info');
	        return;
	    }
		$.messager.confirm('提示', '确定要把选中的记录调整到入围里吗？', function(r){
			if (r){
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/transferIn', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	$('#tb-callOut').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择出围的记录','info');
	        return;
	    }
    	
		$.messager.confirm('提示', '确定要把选中的记录调整到出围里吗？', function(r){
			if (r){
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/callOut', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	$('#tb-affirm').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
    	
    	if(rows.length == 0){
	        $.messager.alert('提示','请选择确认结果的记录','info');
	        return;
	    }
    	$.messager.confirm('提示', '确定要把选中的记录变成最终结果吗？如果点确认后选中的记录将不能进行任何调整，要继续操作吗？', function(r){
			if (r){
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + row.id +'&';
			    });
			    
				$.post('${ctx}/yjk/re/voteresult/affirm', parameter, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	});
	
	$('#tb-next').bind('click', function(){
    	$.messager.confirm('提示', '确认要进入下一轮投票流程吗？', function(r){
			if (r){
				$.post('${ctx}/yjk/re/voteresult/next', {reason:'进入下一轮'}, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
						parent.$('#ifrprocess')[0].contentWindow.location.reload();
					}
					alert(result.message);
					window.location.reload();
				});
			}
	 	});
	});
	
	$('#tb-exit').bind('click', function(){
		$.messager.confirm('提示', '确认要结束投票吗？', function(r){
			if (r){
				$.post('${ctx}/yjk/re/voteresult/next', {reason:'结束投票'}, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
						parent.$('#ifrprocess')[0].contentWindow.location.reload();
					}
					alert(result.message);
					window.location.reload();
				});
			}
	 	});
	});
	
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
	
	function formatOperation(val, row) {
		var htmlOperation = '';
		if (row.adjustedInfo != '') {
			htmlOperation = row.adjustedInfo;
			if (!row.affirmVoteResulted){
				htmlOperation += ' | <a class="cancelCls" onclick="cancel(' + row.id + ');" href="javascript:void(0);" style="height:24px;" title="取消"/>';
			}
		}
		return htmlOperation;
	}
	
	function cancel(id){
		$.messager.confirm('提示', '确认要取消调整吗？', function(r){
			if (r){
				$.post('${ctx}/yjk/re/voteresult/cancel', {voteResultId:id}, function(result) {
					if (result.success){
						$('#tt').datagrid('clearSelections');
						$('#tt').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
				});
			}
	 	});
	}
</script>
</c:when>
<c:otherwise>
<ewcms:head title="评审监控 - 投票结果"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',fit:true" style="border:0;">	
			<h1 class="title">本轮投票还未完成，还不能生成投票结果！</h1>
		</div>
	</div>
<ewcms:footer/>
</c:otherwise>
</c:choose>