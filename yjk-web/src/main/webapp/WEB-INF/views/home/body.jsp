<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<div data-options="region:'north',border:false" style="height:70px;overflow:no;">
		    <h2 align="center">欢迎使用网上药品申报信息管理系统</h2>
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
									<table class="easyui-datagrid" data-options="height:400,url:'${ctx}/drugFormCountReport',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
										<thead>
											<tr>
											    <th data-options="field:'organizationId',hidden:true">部门编号</th>
											    <th data-options="field:'organizationName',width:160">部门名称</th>
											    <th data-options="field:'noDeclareNumber',width:70">填报</th>
											    <th data-options="field:'initNumber',width:90">提交初审</th>
											    <th data-options="field:'passedNumber',width:90">已通过初审</th>
											    <th data-options="field:'unPassedNumber',width:90">未通过初审</th>
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
		<div data-options="region:'west',border:false" style="width:60%;overflow: auto;">
		    <table style="width:100%;padding:0;">
		    	<tr>
		    		<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">公告栏</div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" style="display:inline;"></a>
		                   			</div>
		                 		</div>
		                 		<div style="height: 190px; padding: 5px;" title="" id="notice" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 			<div class="t-list">
		                 				<table style="width:100%;">
		                 					<tr>	
			                 					<td>
			                 						<span id="sysNotice"></span>
			                 					</td>
		                 					</tr>
		                 				</table>
		                 			</div>
		                 		</div>
		               		</div>
		             	</div>
		           	</td>
		           	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">提示栏</div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" style="display:inline;"></a>
		                   			</div>
		                 		</div>
		                 		<div style="height: 190px; padding: 5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 			<div class="t-list">
		                 				<table style="width:100%;">
		                 					<tr>	
			                 					<td>
			                 						<span style="font-size:14px">1、已提交初审新药数：<span id="initAudit"></span>条</span>
			                 					</td>
		                 					</tr>
		                 					<tr>	
			                 					<td>
			                 						<span style="font-size:14px">2、已通过初审新药数：<span id="passedAudit"></span>条</span>
			                 					</td>
		                 					</tr>
		                 					<tr>	
			                 					<td>
			                 						<span style="font-size:14px">3、未通过初审新药数：<span id="unPassedAudit"></span>条</span>
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
		                   			<div class="panel-title panel-with-icon">图表栏</div>
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
