<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="匹配的医院药品目录"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/yjk/zd/hospitalcontents/${commonNameContentsId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
		<thead>
			    <th data-options="field:'drugCode',width:120">药品代码</th>
			    <th data-options="field:'genericDrugName',width:120">药品通用名</th>
				<th data-options="field:'extractCommonName',width:120">提取通用名</th>
			    <th data-options="field:'drugRoute',width:120">给药途径</th>
				<th data-options="field:'serialNo',width:120">编号</th>			
				<th data-options="field:'pill',width:100">剂型</th>					
				<th data-options="field:'specNumber',width:70">规格*数量</th>
				<th data-options="field:'manufacturer',width:120">生产企业</th>
				<th data-options="field:'drugMajor',width:120">药品分类大类</th>
				<th data-options="field:'drugCategory',width:80">药品分类</th>
				<th data-options="field:'discom',width:80">配送公司</th>
				<th data-options="field:'deleted',width:100,
						formatter:function(val,row){
							return val ? '<font color=red>已删除</font>' : '';
						}">是否删除</th>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		      			
           		</table>
        	</form>
        </div>
	</div>
<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	

</script>