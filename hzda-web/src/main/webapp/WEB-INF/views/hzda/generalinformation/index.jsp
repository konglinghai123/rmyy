<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="骨质疏松 - 一般信息"/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/hzda/generalinformation/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,border:false,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true" rowspan="2"/>
			    <th data-options="field:'id',sortable:true,width:100" rowspan="2">编号</th>
				<th data-options="field:'recordingTime',sortable:true,width:140" rowspan="2">记录日期</th>
				<th data-options="field:'realName',sortable:true,width:120" rowspan="2">建档医生</th>
				<th data-options="field:'organizationName',sortable:true,width:120" rowspan="2">建档医院</th>
				<th data-options="field:'transplantNumber',sortable:true,width:100" rowspan="2">移植编号</th>
				<th data-options="field:'name',sortable:true,width:120" rowspan="2">姓名</th>
				<th data-options="field:'hospitalizationNumber',sortable:true,width:100" rowspan="2">住院号</th>
				<th data-options="field:'dxaNumber',sortable:true,width:100" rowspan="2">DXA号</th>
				<th data-options="field:'sex',width:60,sortable:true,
						formatter:function(val,row){
							return row.sexDescription;
						}" rowspan="2">性别</th>
				<th data-options="field:'birthday',sortable:true,width:140" rowspan="2">出生年月</th>
				<th data-options="field:'nation.name',sortable:true,width:100,
						formatter:function(val,row){
							return row.nation==null?'':row.nation.name;
						}" rowspan="2">民族</th>
				<th data-options="field:'degreeEducation',sortable:true,width:120" rowspan="2">文化程度</th>
				<th data-options="field:'occupation',sortable:true,width:120" rowspan="2">职业</th>
				<th data-options="field:'address',sortable:true,width:160" rowspan="2">现住址</th>
				<th data-options="field:'certificateType.name',sortable:true,width:120,
						formatter:function(val,row){
							return row.certificateType==null?'':row.certificateType.name;
						}" rowspan="2">证件类型</th>
				<th data-options="field:'certificateNumber',sortable:true,width:160" rowspan="2">证件号</th>
				<th data-options="field:'medicalInsuranceNumber',sortable:true,width:100" rowspan="2">医保号</th>
				<th data-options="field:'mobilePhoneNumber',sortable:true,width:100" rowspan="2">手机号码</th>
				<th data-options="field:'otherTelephone',sortable:true,width:100" rowspan="2">其他联系电话</th>
				<th data-options="field:'specialTab',sortable:true,width:100,
						formatter:function(val,row){
							if (val != null) {
								return val ? '是':'否';
							}
						}" rowspan="2">特殊患者标记</th>
				<th data-options="field:'specialTabNumber',sortable:true,width:100" rowspan="2">特殊患者编号</th>
				<th data-options="field:'oSTA',sortable:true,width:80" rowspan="2">OSTA评分</th>
				<th data-options="field:'eGFR',sortable:true,width:80" rowspan="2">eGFR</th>
				<th colspan="2">FRAX评估</th>
				<th data-options="field:'remakrs',sortable:true,width:160,formatter:formatTooltip" rowspan="2">备注</th>
			</tr>
			<tr>
				<th data-options="field:'FRAXMain',sortable:true,width:150">主要部位骨折风险</th>
				<th data-options="field:'FRAXHipbone',sortable:true,width:150">髋部骨折风险</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<c:if test="${!user.admin}">
        <div id="toolbar" class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$.ewcms.add({title:'新增 - 一般信息',width:850,height:550});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="$.ewcms.edit({title:'修改 - 一般信息',width:850,height:550});">修改</a>
		</div>
		</c:if>
        <div>
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">姓名</td>
              			<td width="23%"><input type="text" id="likeName" name="LIKE_name" style="width:120px;"/></td>
              			<td width="5%">手机号码</td>
    					<td width="23"><input type="text" name="LIKE_mobilePhoneNumber" style="width:120px"/></td>
           				<td width="5%">证件号</td>
           				<td width="23%"><input type="text" name="LIKE_certificateNumber" style="width:120px;"/></td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton" onclick="$.ewcms.moreQuery();"><span id="showHideLabel">更多</span></a>
           				</td>
           			</tr>
           			<tr style="display: none;">
           				<td>记录日期</td>
           				<td colspan="2"><input type="text" id="recordingTime" name="GTE_recordingTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="recordingTime" name="LTE_recordingTime" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
	$(function(){
		var pathname = window.location.pathname;
		if (pathname != '${ctx}/hzda/generalinformation/index'){
			$('#toolbar').hide();
		} else {
			pathname = "${ctx}/hzda/generalinformation/tabs"
		}
		$('#tt').datagrid({
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:true,
					cache:false,
					content: '<iframe src="' + pathname + '/' + rowData.id + '" frameborder="0" width="60%" height="400px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
		$('#tb-query').bind('click', function(){
			$.cookie('likeName-${user.username}', $('#likeName').val(), {path: '${ctx}/hzda'});
			$.ewcms.query();
		});
		$('#tb-clear').bind('click', function(){
			$.cookie('likeName-${user.username}', null); 
			$('#queryform').form('reset');
		});
		
		try{
			var likeName = $.cookie('likeName-${user.username}');
			if (likeName != null && likeName != '') {
				$('#likeName').val(likeName);
				$.ewcms.query();
			}
		}catch(err){
		}
	});
	
</script>