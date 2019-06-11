<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 风险评测"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/riskevaluation/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
			  		<tr>
			  			<td colspan="2"><h3>1分钟测试题</h3></td>
			  		</tr>
		        	<tr>
		        		<td width="60%">1.您的父母是否患有骨质疏松症或曾经在轻微跌倒后骨折？</td>
		        		<td width="40%">1.<form:radiobuttons path="omt1" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>2.您的父母一人有驼背？</td>
						<td>2.<form:radiobuttons path="omt2" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>3.您是否40岁或以上？</td>
						<td>3.<form:radiobuttons path="omt3" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>4.您成年后是否曾经在轻微跌倒后骨折？</td>
						<td>4.<form:radiobuttons path="omt4" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>5.您是否经常跌倒(在过去的一年里曾跌倒多过一次)或因身体虚弱而担心会跌倒？</td>
						<td>5.<form:radiobuttons path="omt5" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>6.您40岁后，身高是否降低了3厘米以上？</td>
						<td>6.<form:radiobuttons path="omt6" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>7.您是否体重过轻（BMI值少于19kg/m2）？</td>
						<td>7.<form:radiobuttons path="omt7" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>8.您是否曾经连续服用类固醇类药物(如可的松、强的松等) 超过3个月？</td>
						<td>8.<form:radiobuttons path="omt8" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>9.您是否患有类风湿性关节炎？</td>
						<td>9.<form:radiobuttons path="omt9" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>10.您是否患有甲状腺功能亢进、甲状旁腺功能亢进、1型糖尿病、克罗恩病或乳糜泻等胃肠疾病或营养不良？ </td>
						<td>10.<form:radiobuttons path="omt10" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>11.女士回答：您是否在45岁或以前已停经？</td>
						<td>
							<c:if test="${isShow}">
							11.<form:radiobuttons path="omt11" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td>12.女士回答：除怀孕、停经或切除子宫后，您是否曾经停经超过12个月？</td>
						<td>
							<c:if test="${isShow}">
							12.<form:radiobuttons path="omt12" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>13.女士回答：您是否在50岁前切除卵巢且没有服用激素补充剂？</td>
						<td>
							<c:if test="${isShow}">
							13.<form:radiobuttons path="omt13" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>14.男士回答：您是否曾经因雄激素过低而导致阳痿或性欲减低？</td>
						<td>
							<c:if test="${!isShow}">
							14.<form:radiobuttons path="omt14" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>15. 您是否每天饮酒（每天超过两单位乙醇，相当于啤酒1斤、葡萄糖酒3两或烈性酒1两）？</td>
						<td>15.<form:radiobuttons path="omt15" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>16.您是否抽烟？</td>
						<td>16.<form:radiobuttons path="omt16" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>17.您每天的体力活动(如做家务、园艺、散步等)是否少于30分钟？</td>
						<td>17.<form:radiobuttons path="omt17" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>18.您是否不能进食奶类制品又没有服用钙片？</td>
						<td>18.<form:radiobuttons path="omt18" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
						<td>19. 您每天的户外活动(部分身体暴露于阳光下)是否少于10分钟又没有服用维生素D补充剂？</td>
						<td>19.<form:radiobuttons path="omt19" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
					</tr>
					<tr>
			  			<td colspan="2"><h3>OSTA评分</h3></td>
			  		</tr>
			  		<tr>
			  			<td colspan="2">
							结果：<form:input path="oSTA" readonly="true" placeholder="(体重-年龄)×0.2;结果>-1为低,-1至-4为中,<-4为高"/>&nbsp;&nbsp;体重：<form:input path="weight" size="3" cssClass="validate[custom[integer]]"/>Kg&nbsp;&nbsp;年龄：<form:input path="age" size="3" cssClass="validate[custom[integer]]"/>岁&nbsp;&nbsp;
							<a id="tb-calculate" class="easyui-linkbutton" data-options="iconCls:'icon-sum'" href="javascript:void(0);">计算</a>
			  			</td>
			  		</tr>
			  		<tr>
			  			<td colspan="2"><h3>FRAX风险评估</h3></td>
			  		</tr>
			  		<tr>
			  			<td colspan="2">
				  			<table style="border:0px solid; padding:0px;" class="formtable">
					  			<tr>
									<td width="20%">主要部位骨折风险：</td>
									<td width="30%"><form:input path="fRAXMain"/></td>
									<td width="20%">髋部骨折风险：</td>
									<td width="30%"><form:input path="fRAXHipbone"/></td>
								</tr>
							</table>
						</td>
			  		</tr>
				</table>
			</form:form>
		</div>
		<c:if test="${!user.admin}">
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
		</div>
		</c:if>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tb-calculate').bind('click', function(){
			if ($('#weight').val() != '' && $('#age').val() != ''){
				var osta = ($('#weight').val() - $('#age').val())*0.2
				if (osta > -1){
					$('#oSTA').val('低');
				} else if (osta < -4) {
					$('#oSTA').val('高');
				} else {
					$('#oSTA').val('中');
				}
			}
		})
		
		var validationEngine = $("#editForm").validationEngine({
			promptPosition:'bottomLeft',
			showOneMessage: true
		});
    	<ewcms:showFieldError commandName="m"/>
	});
</script>