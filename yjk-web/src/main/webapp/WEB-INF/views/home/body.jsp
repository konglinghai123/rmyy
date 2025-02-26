<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<div data-options="region:'north',border:false" style="height:70px;overflow:no;">
		    <h2 align="center">欢迎使用网上药品申报评审信息管理系统V2.0</h2>
		</div>
		<div data-options="region:'west',border:false" style="width:34%;overflow:auto;padding-top:3px;">
			<fieldset style="height:765px">
			<legend><b style="font-size:16px;color:red">综合栏</b></legend>
			<table style="width:100%;">
				<tr>
					<td class="portal-column-td">
			             <div style="overflow:hidden;padding:0 0 0 0">
			               <div class="panel" style="margin-bottom:2px;">
			                 <div class="panel-header">
			                   <div class="panel-title">公告栏</div>
			                 </div>
			                 <div style="height:300px; padding: 5px;" id="notice" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
			               </div>
			             </div>
		           	</td>
				</tr>
		    </table>
		    </fieldset>
		</div>
		<div data-options="region:'center',border:false" style="width:33%;overflow:auto;">
			<fieldset style="height:767px">
			<legend><b style="font-size:16px;color:red">申报新药统计栏：</b>
			    <form:select id="systemParameterId" path="systemParameterList" cssStyle="width:60%;">
					<form:options items="${systemParameterList}" itemValue="id" itemLabel="projectRemark"/>
				</form:select>
			</legend>
			<table style="width:100%;">
		    	<tr>
		           	<td class="portal-column-td">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel">
		                 		<div class="panel-header">
		                   			<div class="panel-title">申报统计栏</div>
		                   			<div class="panel-tool"></div>
		                 		</div>
		                 		<div style="height: 120px; padding: 5px;" id="drugFormStatistic" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
		               		</div>
		             	</div>
					</td>
		    	</tr>
		    	<tr>
		           	<td class="portal-column-td">
		            	<div class="panel">
		                 	<div class="panel-header">
		                   		<div class="panel-title panel-with-icon">申报统计图表</div>
		                   		<div class="panel-icon icon-visit-analysis"></div>
		                   		<div class="panel-tool"></div>
		                 	</div>
			             	<div style="height: 190px; padding: 5px;" title="" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		        				<div id="drugFormCountDiv" align="center"></div>
				    		</div>
				    	</div>
		           	</td>
				</tr>
				<tr>
		    		<td class="portal-column-td">
		    			<div class="panel">
		                 	<div class="panel-header">
		                   		<div class="panel-title">各部门填报情况</div>
		                   		<div class="panel-tool">
		                   		</div>
		                 	</div>
		                 	<div style="height:310px; padding:5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 		<div class="t-list">
									<table id="ttSystemParameter">
										<thead>
											<tr>
											    <th data-options="field:'organizationId',hidden:true">部门编号</th>
											    <th data-options="field:'organizationName',width:150">部门名称</th>
											    <th data-options="field:'noDeclareNumber',width:80">未提交初审</th>
											    <th data-options="field:'initNumber',width:80">已提交初审</th>
											    <th data-options="field:'passedNumber',width:90">已通过初审</th>
											    <th data-options="field:'unPassedNumber',width:90">未通过初审</th>
											</tr>
										</thead>
									</table>
		                 		</div>
		                 	</div>
		               	</div>
		    		</td>
		    	</tr>
		    </table>
		    </fieldset>
		</div>
		<div data-options="region:'east',border:false" style="width:33%;overflow:auto;">
			<fieldset style="height:767px">
			<legend><b style="font-size:16px;color:red">评审新药统计栏：</b>
				<form:select id="reviewMainId" path="reviewMainList" cssStyle="width:60%;">
					<form:options items="${reviewMainList}" itemValue="id" itemLabel="name"/>
				</form:select>
			</legend>
			评审申报项目：<span id="reviewSystemParameter"></span>
			<table style="width:100%;">
		    	<tr>
		           	<td class="portal-column-td">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">评审统计栏</div>
		                   			<div class="panel-tool">
		                   			</div>
		                 		</div>
		                 		<div style="height: 120px; padding: 5px;" id="reivewStatistic" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
		               		</div>
		             	</div>
					</td>
		    	</tr>
		    	<tr>
		           	<td class="portal-column-td">
		            	<div class="panel" style="margin-bottom:2px;">
		                 	<div class="panel-header">
		                   		<div class="panel-title panel-with-icon">评审统计图表</div>
		                   		<div class="panel-icon icon-visit-analysis"></div>
		                   		<div class="panel-tool"></div>
		                 	</div>
			             	<div style="height: 280px; padding: 5px;" title="" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		        				<div id="reviewCountDiv" align="center"></div>
				    		</div>
				    	</div>
		           	</td>
				</tr>
				<tr>
		           	<td class="portal-column-td">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">评审说明栏</div>
		                   			<div class="panel-tool">
		                   			</div>
		                 		</div>
		                 		<div style="height: 190px; padding: 5px;" id="reivewRemark" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
		               		</div>
		             	</div>
					</td>
		    	</tr>
		    </table>
			</fieldset>
		</div>