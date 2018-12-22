<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-大通用名总目录"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'common.commonName',width:200,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.commonName;
						}">通用名</th>
				<th data-options="field:'common.matchNumber',sortable:true,width:80,
						formatter:function(val,row){
							return row.common==null?'':row.common.matchNumber;
						}">匹配编号</th>
				<th data-options="field:'administration.name',sortable:true,width:80,
						formatter:function(val,row){
							return row.administration==null?'':row.administration.name;
						}">给药途径</th>
				<th data-options="field:'drugCategoryInfo',width:80">药品种类</th>												
				<th data-options="field:'commonName',width:150,sortable:true">大目录通用名</th>
				<th data-options="field:'common.bidCommonName',width:150,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.bidCommonName;
						}">省招标通用名</th>							
				<th data-options="field:'projectName',width:150,sortable:true">项目名称</th>
				<th data-options="field:'batch',width:80,sortable:true">批次</th>
				<th data-options="field:'pill',width:100">剂型</th>				
				<th data-options="field:'bidDrugId',width:150,sortable:true">省招标药品ID</th>
				<th data-options="field:'countryId',width:100,sortable:true">国家ID</th>
				<th data-options="field:'specifications',width:150,sortable:true">规格</th>
				<th data-options="field:'amount',width:70">包装数量</th>
				<th data-options="field:'productName',width:100">商品名</th>
				<th data-options="field:'packageUnit',width:80">包装单位</th>
				<th data-options="field:'manufacturer',width:300,sortable:true">生产企业</th>
				<th data-options="field:'importEnterprise',width:300,sortable:true">进口企业</th>
				<th data-options="field:'purchasePrice',width:50,sortable:true">采购价</th>
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
				<th data-options="field:'common.chemicalBigCategory',width:200,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.chemicalBigCategory;
						}">大类</th>	
				<th data-options="field:'common.chemicalSubCategory',width:200,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.chemicalSubCategory;
						}">小类/功效</th>	
				<th data-options="field:'licenseNumber',width:100">批准文号</th>
				<th data-options="field:'bidPill',width:80">招标剂型</th>
				<th data-options="field:'bidSpecifications',width:80">招标规格</th>
				<th data-options="field:'bidUnit',width:80">招标单位</th>
				<th data-options="field:'packageMaterials',width:50">包材</th>
				<th data-options="field:'minimalUnit',width:80">最小制剂单位</th>
				<th data-options="field:'remark1',width:80">备注1</th>
				<th data-options="field:'remark2',width:80">备注2</th>
				<th data-options="field:'remark3',width:80">备注3</th>
				<th data-options="field:'declared',width:100,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否允许申报</th>
				<th data-options="field:'createDate',width:150,sortable:true">创建时间</th>
				<th data-options="field:'updateDate',width:150,sortable:true">修改时间</th>		
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$.ewcms.add({title:'新增',width:850,height:550});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({title:'修改',width:850,height:550});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="$.ewcms.remove({title:'删除'});">删除</a>
 			<a id="tb-declare" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:600,height:400,title:'过滤大目录',src:'${ctx}/yjk/zd/commonnamecontents/filterdeclared'});">过滤</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-import'" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入大目录信息',src:'${ctx}/yjk/zd/commonnamecontents/import'});">导入</a>
 			<a id="tb-export" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导出通用名信息',src:'${ctx}/system/report/show/text/4/paraset'});">导出</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td>通用名</td>
              			<td><input type="text" name="LIKE_common.commonName" style="width:140px;"/></td>
            			<td>匹配编号</td>
              			<td><input type="text" name="Like_common.matchNumber" style="width:120px;"/></td>
              			<td >简拼</td>
              			<td><input type="text" name="LIKE_common.spellSimplify" style="width:80px;"/></td>
              			<td>药品种类</td>
    					<td>
           					<form:select  name="EQ_common.drugCategory" path="drugCategoryList" cssClass="easyui-combobox"  data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="---请选择---"/>
					  			<form:options items="${drugCategoryList}" itemLabel="info"/>
							</form:select>
						</td>              			
              			<td colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton" onclick="$.ewcms.moreQuery();"><span id="showHideLabel">更多</span></a>
           				</td>
           			</tr>
           			<tr style="display: none;">
              			<td>生产企业</td>
              			<td><input type="text" name="LIKE_manufacturer" style="width:140px;"/></td>
              			<td>项目名称</td>
              			<td><input type="text" name="LIKE_projectName" style="width:140px;"/></td>
            			<td>规格</td>
              			<td><input type="text" name="LIKE_specifications" style="width:140px;"/></td>
            			<td>批次</td>
              			<td><input type="text" name="LIKE_batch" style="width:140px;"/></td>   
           				<td>是否允许申报</td>
    					<td>
           					<form:select id="declared" name="EQ_declared" path="booleanList" cssClass="easyui-combobox"   data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="---请选择---"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
						</td>               			   				
           			</tr>  
           			<tr style="display: none;">
              			<td>国家谈判品种</td>
              			<td><input type="text" name="LIKE_negotiationVariety" style="width:140px;"/></td>
              			<td>一致性评价</td>
              			<td><input type="text" name="LIKE_consistencyEvaluation" style="width:140px;"/></td>
            			<td>化药大类</td>
              			<td><input type="text" name="LIKE_common.chemicalBigCategory" style="width:140px;"/></td>
            			<td>化药小类</td>
              			<td><input type="text" name="LIKE_common.chemicalSubCategory" style="width:140px;"/></td>                			   				
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
	});
</script>