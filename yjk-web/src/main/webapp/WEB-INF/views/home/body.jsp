<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<div data-options="region:'north',border:false" style="height:70px;overflow:no;">
		    <h2 align="center">欢迎使用网上药品申报评审信息管理系统V2.0</h2>
		</div>
		<div data-options="region:'center',border:false" style="width:40%;overflow:auto;">
			<table style="width:100%;padding:0;">
		    	<tr>
		    		<td width="1%"></td>
		    		<td class="portal-column-td" width="98%">
		    			<div class="panel" style="margin-bottom:2px;">
		                 	<div class="panel-header">
		                   		<div class="panel-title">各部门填报情况</div>
		                   		<div class="panel-tool">
		                    		<a href="javascript:void(0);" style="display:inline;"></a>
		                   		</div>
		                 	</div>
		                 	<div style="height:410px; padding: 5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 		<div class="t-list">
									<table id="tt" data-options="height:400,url:'${ctx}/drugFormCountReport',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
										<thead>
											<tr>
											    <th data-options="field:'organizationId',hidden:true">部门编号</th>
											    <th data-options="field:'organizationName',width:150">部门名称</th>
											    <th data-options="field:'noDeclareNumber',width:80">未提交初审</th>
											    <th data-options="field:'initNumber',width:80">已提交初审</th>
											    <th data-options="field:'passedNumber',width:90">初审核已通过</th>
											    <th data-options="field:'unPassedNumber',width:90">初审核未通过</th>
											</tr>
										</thead>
									</table>
									<script type="text/javascript">
										$(function(){
											var pager = $('#tt').datagrid().datagrid('getPager');
											<c:if test="${user.admin}">
											pager.pagination({
												buttons:[{
													iconCls:'icon-print',
													handler:function(){
														$.ewcms.openWindow({
															windowId:'#edit-window',
															iframeId : '#editifr', 
															src:'${ctx}/system/report/show/text/8/paraset', 
															width:550,
															height:200,
															title:'参数选择'
														});
													}
												}]
											});
											</c:if>
										})
									</script>
		                 		</div>
		                 	</div>
		               	</div>
		    		</td>
		    		<td width="1%"></td>
		    	</tr>
		    </table>
		</div>
		<div data-options="region:'west',border:false" style="width:60%;overflow: auto;">
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
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">申报统计栏</div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" style="display:inline;"></a>
		                   			</div>
		                 		</div>
		                 		<div style="height: 190px; padding: 5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 			<div class="t-list">
		                 				<table style="width:100%;">
		                 					<tr>	
			                 					<td>
			                 						<span id="drugForm_nodeclare"></span>
			                 					</td>
		                 					</tr>
		                 					<tr>	
			                 					<td>
			                 						<span id="drugForm_init"></span>
			                 					</td>
		                 					</tr>
		                 					<tr>	
			                 					<td>
			                 						<span id="drugForm_passed"></span>
			                 					</td>
		                 					</tr>
		                 					<tr>	
			                 					<td>
			                 						<span id="drugForm_unPassed"></span>
			                 					</td>
		                 					</tr>
		                 				</table>
		                 			</div>
		                 		</div>
		               		</div>
		             	</div>
					</td>
		           	<td width="1%"></td>
		    	</tr>
		    	<tr>
		        	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title panel-with-icon">申报统计图表</div>
		                   			<div class="panel-icon icon-visit-analysis"></div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" class="icon-reload panel-tool-a" onclick="drugFormCountChart();" title="刷新"></a>
		                   			</div>
		                 		</div>
			             		<div style="height: 190px; padding: 5px;" title="" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		        					<div id="drugFormCountDiv" align="center"></div>
				    			</div>
				    		</div>
				    	</div>
		           	</td>
		           	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
					</td>
		           	<td width="1%"></td>
				</tr>
			</table>
		</div>
