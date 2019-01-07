<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典 - 通用名"/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/yjk/zd/commonname/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,border:false,pageSize:20,
	        rowStyler: function(index,row){
	        	if (!row.enabled){
	    			return 'background-color:#FF0000;color:#FFFFFF;';
	        	}
	    	}">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'commonName',sortable:true,width:200">通用名</th>
				<th data-options="field:'bidCommonName',width:200,sortable:true">省招标通用名</th>
				<th data-options="field:'matchNumber',width:80,sortable:true">匹配编号</th>
				<th data-options="field:'drugCategoryInfo',width:80">药品种类</th>
				<th data-options="field:'chemicalBigCategory',width:200">化药大类</th>
				<th data-options="field:'chemicalSubCategory',width:200">化药小类</th>
				<th data-options="field:'spell',sortable:true,width:200">全拼</th>
				<th data-options="field:'spellSimplify',sortable:true,width:100">简拼</th>
				<th data-options="field:'enabled',width:80,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否启用</th>
				<!--  <th data-options="field:'deleted',width:100,formatter:formatOperation">是否删除</th>-->				
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:500,height:265});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:500,height:330});">修改</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-import',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入通用名信息',src:'${ctx}/yjk/zd/commonname/import'});">导入</a>
 			<a id="tb-export" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导出通用名信息',src:'${ctx}/system/report/show/text/2/paraset'});">导出</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
		</div>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td >通用名</td>
              			<td ><input type="text" name="LIKE_commonName" style="width:140px;"/></td>
            			<td>匹配编号</td>
              			<td><input type="text" name="LIKE_matchNumber" style="width:120px;"/></td>              			
            			<td>简拼</td>
              			<td><input type="text" name="LIKE_spellSimplify" style="width:80px;"/></td>
              			<td>药品种类</td>
    					<td>
           					<form:select id="drugCategory" name="EQ_drugCategory" path="drugCategoryList" cssClass="easyui-combobox"  data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="---请选择---"/>
					  			<form:options items="${drugCategoryList}" itemLabel="info"/>
							</form:select>
						</td> 
           				<td>是否启用</td>
    					<td>
           					<form:select id="enabled" name="EQ_enabled" path="booleanList" cssClass="easyui-combobox"   data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="---请选择---"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
						</td>              			
              			<td>
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>         			
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>