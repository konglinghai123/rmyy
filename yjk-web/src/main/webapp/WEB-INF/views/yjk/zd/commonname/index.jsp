<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="字典-通用名"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'commonName',sortable:true,width:200">通用名</th>
				<th data-options="field:'administration',sortable:true,width:100,
						formatter:function(val,row){
							return (row.administration==null) ? '' : row.administration.name;
						}">用药途径</th>
				<th data-options="field:'matchingNumber',sortable:true,width:100">匹配编号</th>
				<th data-options="field:'spell',sortable:true,width:200">全拼</th>
				<th data-options="field:'spellSimplify',sortable:true,width:150">简拼</th>
				<th data-options="field:'enabled',width:100,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否启用</th>		
				<th data-options="field:'deleted',width:100,formatter:formatOperation">是否删除</th>				
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:400,height:265});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:400,height:265});">修改</a>
 			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
 			<a id="tb-import" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-zip-import',toggle:true" onclick="$.ewcms.openWindow({windowId:'#edit-window',width:500,height:350,title:'导入通用名信息',src:'${ctx}/yjk/zd/commonname/import'});">导入</a>
 			<a id="tb-export" href="${ctx}/yjk/zd/commonname/export" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-zip-export',toggle:true">导出</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">通用名</td>
              			<td width="23%"><input type="text" name="LIKE_commonName" style="width:140px;"/></td>
            			<td width="5%">拼音简写</td>
              			<td width="23%"><input type="text" name="LIKE_spellSimplify" style="width:140px;"/></td>
           				<td width="5%">用药途径</td>
    					<td width="23%">
    						<form:select id="administration" name="EQ_administration.id" path="administrationList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="
	    						panelHeight:'auto',
	    						editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${administrationList}" itemLabel="name" itemValue="id"/>
							</form:select>
						</td>              			
              			<td width="25%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多</span></a>
           				</td>
           			</tr>
           			<tr>
           				<td>是否启用</td>
           				<td>
           					<form:select id="enabled" name="EQ_enabled" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
           				</td>
           				<td>是否删除</td>
           				<td>
           					<form:select id="deleted" name="EQ_deleted" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
           				</td>   
           				<td width="5%" ></td>
              			<td width="15%" colspan="3"></td>        				
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
			url:'${ctx}/yjk/zd/commonname/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false
		});
		
		$("form table tr").next("tr").hide();
		$('#tb-more').bind('click', function(){
	       	var showHideLabel_value = $('#showHideLabel').text();
	    	$('form table tr').next('tr').toggle();
	     	if (showHideLabel_value == '收缩'){
	     		$('#showHideLabel').text('更多');
	    	}else{
	    		$('#showHideLabel').text('收缩');
	    	}
	    	$('#tt').datagrid('resize');
	    });		
	});
	
	function formatOperation(val, row){
		return val ? '<font color=red>已删除</font>  <a class="resumedCls" onclick="restore(' + row.id + ')" href="javascript:void(0);">还原</a>' : '';
	}
	
	function restore(id){
		$.post('${ctx}/yjk/zd/commonname/' + id + '/restore', {}, function(result) {
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>