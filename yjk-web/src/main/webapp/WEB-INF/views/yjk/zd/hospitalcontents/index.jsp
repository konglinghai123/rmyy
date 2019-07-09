<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-院在用药品总目录"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',sortable:true">编号</th>	
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
				<th data-options="field:'common.drugCategoryInfo',width:80,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.drugCategoryInfo;
						}">药品种类</th>							
				<th data-options="field:'commonName',width:200">院用目录通用名</th>	
				<th data-options="field:'common.bidCommonName',width:200,sortable:true,
						formatter:function(val,row){
							return row.common==null?'':row.common.bidCommonName;
						}">省招标通用名</th>		
				<th data-options="field:'bidDrugId',width:150,sortable:true">省招标药品ID</th>
				<th data-options="field:'projectName',width:150,sortable:true">项目名称</th>					    
			    <th data-options="field:'drugCode',width:100,sortable:true">药品代码</th>
				<th data-options="field:'pill',width:100,sortable:true">剂型</th>	
				<th data-options="field:'productName',width:100">商品名</th>				
				<th data-options="field:'specifications',width:120,sortable:true">规格</th>
				<th data-options="field:'amount',width:70,sortable:true">包装数量</th>
				<th data-options="field:'manufacturer',width:300,sortable:true">生产企业</th>
				<th data-options="field:'importEnterprise',width:300">进口企业</th>	
				<th data-options="field:'bidPrice',width:80,sortable:true">中标价</th>		
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
				<th data-options="field:'remark',width:100">备注</th>
				<th data-options="field:'createDate',width:150,sortable:true">创建时间</th>
				<th data-options="field:'updateDate',width:150,sortable:true">修改时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$.ewcms.add({title:'新增',width:800,height:450});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({title:'修改',width:850,height:450});">修改</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-import'" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入医院在用目录信息',src:'${ctx}/yjk/zd/hospitalcontents/import'});">导入</a>
 			<a id="tb-export" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导出通用名信息',src:'${ctx}/system/report/show/text/3/paraset'});">导出</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="$.ewcms.remove({title:'删除'});">删除</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform">
        		<table class="formtable">
              		<tr>
              			<td>通用名</td>
              			<td><input type="text" name="LIKE_common.commonName" style="width:120px;"/></td>
            			<td>匹配编号</td>
              			<td><input type="text" name="Like_common.matchNumber" style="width:120px;"/></td>
              			<td>简拼</td>
              			<td><input type="text" name="LIKE_common.spellSimplify" style="width:120px;"/></td>
              			<td>药品种类</td>
    					<td>
           					<form:select  name="EQ_common.drugCategory" path="drugCategoryList" cssClass="easyui-combobox"  cssStyle="width:120px;" data-options="panelHeight:'auto',editable:false">
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
           				<td>省招标药品ID</td>
              			<td><input type="text" name="LIKE_bidDrugId" style="width:120px;"/></td>
              			<td>生产企业</td>
              			<td><input type="text" name="LIKE_manufacturer" style="width:120px;"/></td>
              			<td>项目名称</td>
              			<td><input type="text" name="LIKE_projectName" style="width:120px;"/></td>
            			<td>规格</td>
              			<td><input type="text" name="LIKE_specifications" style="width:120px;"/></td>
            			<td>批次</td>
              			<td><input type="text" name="LIKE_batch" style="width:120px;"/></td>                  			   				
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
			url:'${ctx}/yjk/zd/hospitalcontents/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
	        rowStyler: function(index,row){
	        	if (row.deleted){
	    			return 'background-color:#FF0000;color:#FFFFFF;';
	        	}
	    	},
			onLoadSuccess : function () {
		        $(this).datagrid('fixRownumber');
		    }
		});
	});
</script>