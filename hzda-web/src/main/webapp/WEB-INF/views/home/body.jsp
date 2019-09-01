<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<div data-options="region:'north',border:false" style="height:70px;overflow:no;">
		    <h2 align="center">欢迎使用骨质疏松患者档案系统</h2>
		</div>
		<div data-options="region:'center',border:false" style="width:59%;overflow:auto;">
			<table style="width:100%;padding:0;">
		    	<tr>
		        	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		           		<div class="panel">
		                 	<div class="panel-header">
		                   		<div class="panel-title">随访时间提醒栏</div>
		                   		<div class="panel-tool">
		                   		</div>
		                 	</div>
		                 	<div style="height:310px; padding:5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 		<div class="t-list">
									<table id="ttFollowupTime">
										<thead>
											<tr>
											    <th data-options="field:'ck',checkbox:true"/>
											    <th data-options="field:'id',hidden:true">编号</th>
											    <th data-options="field:'operator',width:80,align:'center',formatter:formatOperation">取消提醒</th>
												<th data-options="field:'recordingTime',sortable:true,width:140">记录日期</th>
												<th data-options="field:'realName',sortable:true,width:120">建档医生</th>
												<th data-options="field:'organizationName',sortable:true,width:120">建档医院</th>
												<th data-options="field:'name',sortable:true,width:120">姓名</th>
												<th data-options="field:'sex',width:60,sortable:true,
														formatter:function(val,row){
															return row.sexDescription;
														}">性别</th>
												<th data-options="field:'birthday',sortable:true,width:130">出生年月</th>
												<th data-options="field:'mobilePhoneNumber',sortable:true,width:100">手机号码</th>
												<th data-options="field:'followupTimeId',hidden:true">随防编号</th>
												<th data-options="field:'nextTime',sortable:true,width:130">下一次随访时间</th>
											</tr>
										</thead>
									</table>
		                 		</div>
		                 	</div>
		               	</div>
		           	</td>
		           	<td width="1%"></td>
				</tr>
		    </table>
		</div>
		<div data-options="region:'west',border:false" style="width:40%;overflow: auto;">
			<table style="width:100%;padding:0;">
		    	<tr>
		    		<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
			             <div style="overflow:hidden;padding:0 0 0 0">
			               <div class="panel" style="margin-bottom:2px;">
			                 <div class="panel-header">
			                   <div class="panel-title">公告栏</div>
			                 </div>
			                 <div style="height: 190px; padding: 5px;" title="" id="notice" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
			               </div>
			             </div>
		           	</td>
		           	<td width="1%"></td>
		    	</tr>
			</table>
		</div>
