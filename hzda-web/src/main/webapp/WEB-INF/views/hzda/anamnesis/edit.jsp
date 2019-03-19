<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 既往史"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/anamnesis/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
			  		<tr>
			  			<td colspan="2"><h3>疾病史：</h3></td>
			  		</tr>
			  		<tr>
			  			<td colspan="2"><form:checkbox path="pastHealth"/>既往体健</td>
			  		</tr>
		        	<tr>
		        		<td>风温系统疾病：</td>
						<td>
							<form:checkbox path="rheumatoidArthritis"/>类风湿关节炎&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="osteoarthritis"/>骨关节炎&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sle"/>系统性红斑狼疮&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="ankylosingSpondylitis"/>强直性脊柱炎&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="autoimmuneLiverDisease"/>自身免疫性肝病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="vasculitis"/>血管炎&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="primaryDesiccationSyndrome"/>原发性干燥综合征&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="systemicSclerosis"/>系统性硬化症&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="polymyositisDermatomyositis"/>多发性肌炎和皮肌炎&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="rheumatismOther"/>其他<form:input path="rheumatismOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>内分泌及代谢系统疾病：</td>
						<td>
							<form:checkbox path="hyperparathyroidism"/>甲状旁腺功能亢进症&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="hyperthyroidism"/>甲状腺功能亢进症&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="hypogonadism"/>性腺功能减退&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="diabetes1"/>1型糖尿病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="diabetes2"/>2型糖尿病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="cushingSyndrome"/>库欣综合征&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="endocrineOther"/>其他<form:input path="endocrineDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>循环系统疾病：</td>
						<td>
							<form:checkbox path="hypertension"/>高血压&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="cahd"/>冠状动脉粥样硬化性心脏病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="hyperlipidemia"/>高脂血症&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="loopOther"/>其他<form:input path="loopOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>呼吸系统疾病：</td>
						<td>
							<form:checkbox path="copd"/>慢性阻塞性肺疾病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="bronchialAsthma"/>支气管哮喘&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="breathOther"/>其他<form:input path="breathOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>神经及精神系统疾病：</td>
						<td>
							<form:checkbox path="anorexiaNervosa"/>神经性厌食&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="cerebralVascularDisease"/>脑血管病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="myastheniaGravis"/>重症肌无力&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="nerveOther"/>其他<form:input path="nerveOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>消化系统疾病：</td>
						<td>
							<form:checkbox path="cirrhosis"/>肝硬化&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="ibd"/>炎症性肠病(溃疡性结肠炎、克罗恩病)&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="celiacDisease"/>乳糜泻&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="breadbasketResection"/>胃部大切除术后&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="digestionOther"/>其他<form:input path="digestionOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>泌尿系统疾病：</td>
						<td>
							<form:checkbox path="mds"/>骨髓增生异常综合征&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="leukemia"/>白血病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="porphyria"/>肾小管性疾病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="pgn"/>原发性肾小球肾病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="urinationOther"/>其他<form:input path="urinationOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>血液系统疾病：</td>
						<td>
							<form:checkbox path="crif"/>慢性肾功能不全或衰竭&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="renalAmyloidosis"/>肾淀粉样变性&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="rtd"/>肾小管性疾病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="pgn"/>原发性肾小球肾病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="bloodOther"/>其他<form:input path="bloodOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>先天性及遗传性疾病：</td>
						<td>
							<form:checkbox path="msf"/>马凡综合征&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="bronzeDiabetes"/>血色病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="porphyria"/>卟啉病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="brittleBones"/>成骨不全&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="congenitalOther"/>其他<form:input path="congenitalOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
						<td>其他：</td>
						<td>
							<form:checkbox path="pbt"/>原发性骨肿瘤&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sbt"/>继发性骨肿瘤&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="mammaryCancer"/>乳腺癌&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="aids"/>艾滋病&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="otherOther"/>其他<form:input path="otherOtherDesc" size="3"/>
						</td>
					</tr>
					<tr>
			  			<td colspan="2"><h3>用药史：</h3></td>
			  		</tr>
			  		<tr>
			  			<td>是否使用过抗骨质疏松类药物</td>
			  			<td>
			  				<form:checkbox path="refuseAnswer"/>拒绝回答&nbsp;&nbsp;&nbsp;&nbsp;
			  				<form:checkbox path="osteoporosisUnused"/>未使用过&nbsp;&nbsp;&nbsp;&nbsp;
			  			</td>
			  		</tr>
			  		<tr>
			  			<td colspan="2">
			  				<table style="border:0px solid; padding:0px;" class="formtable">
			  					<tr>
			  						<td width="20%"><form:checkbox path="calcium"/>钙剂&nbsp;&nbsp;</td>
			  						<td width="30%">是否规律用药&nbsp;&nbsp;<form:radiobuttons path="calciumLaw" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/>
			  						</td>
			  						<td width="50%">用药总时间<form:input path="calciumDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="vitaminD"/>维生素D及活性维生素D&nbsp;&nbsp;</td>
			  						<td>是否规律用药&nbsp;&nbsp;<form:radiobuttons path="vitaminDLaw" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
			  						<td>用药总时间<form:input path="vitaminDDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="diphosphonate"/>双磷酸盐&nbsp;&nbsp;</td>
			  						<td>是否规律用药&nbsp;&nbsp;<form:radiobuttons path="diphosphonateLaw" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
			  						<td>用药总时间<form:input path="diphosphonateDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="calcitonin"/>降钙素类&nbsp;&nbsp;</td>
			  						<td>是否规律用药&nbsp;&nbsp;<form:radiobuttons path="calcitoninLaw" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
			  						<td>用药总时间<form:input path="calcitoninDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="serm"/>选择性雌激素受体调节剂&nbsp;&nbsp;</td>
			  						<td>是否规律用药&nbsp;&nbsp;<form:radiobuttons path="sermLaw" items="${booleanList}" itemLabel="info" delimiter="&nbsp;"/></td>
			  						<td>用药总时间<form:input path="sermDuration" size="3"/></td>
			  					</tr>
			  				</table>
			  			</td>
			  		</tr>
			  		<tr>
			  			<td>肾上腺糖皮质激素</td>
			  			<td><form:checkbox path="glucocorticoidUnused"/>未使用过</td>
			  		</tr>
			  		<tr>
			  			<td colspan="2">
			  				<table style="border:0px solid; padding:0px;" class="formtable">
			  					<tr>
			  						<td width="10%"><form:checkbox path="prednisone"/>泼尼松&nbsp;&nbsp;</td>
			  						<td width="20%">起始剂量<form:input path="prednisoneInitialDose" size="3"/></td>
			  						<td width="20%">目前剂量<form:input path="prednisoneCurrentDose" size="3"/></td>
			  						<td width="50%">用药总时间<form:input path="prednisoneDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="prednisolone"/>泼尼松龙&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="prednisoloneInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="prednisoloneCurrentDose" size="3"/></td>
			  						<td>用药总时间<form:input path="prednisoloneDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="mp"/>甲强龙&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="mpInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="mpCurrentDose" size="3"/></td>
			  						<td>用药总时间<form:input path="mpDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="dxm"/>地塞米松&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="dxmInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="dxmCurrentDose" size="3"/></td>
			  						<td>用药总时间<form:input path="dxmDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="imuran"/>硫唑嘌呤&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="imuranInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="imuranCurrentDose" size="3"/></td>
			  						<td>用药总时间<form:input path="imuranDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="ciclosporin"/>环孢素&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="ciclosporinInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="ciclosporinCurrentDose" size="3"/></td>
			  						<td>
			  							用药总时间<form:input path="ciclosporinDuration" size="3"/>&nbsp;&nbsp;
			  							血药浓度<form:input path="ciclosporinBC" size="3"/>
			  						</td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="tacrolimus"/>他克莫司&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="tacrolimusInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="tacrolimusCurrentDose" size="3"/></td>
			  						<td>
			  							用药总时间<form:input path="tacrolimusDuration" size="3"/>&nbsp;&nbsp;
			  							血药浓度<form:input path="tacrolimusBC" size="3"/>
			  						</td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="mmf"/>吗替麦考酚酯&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="mmfInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="mmfCurrentDose" size="3"/></td>
			  						<td>用药总时间<form:input path="mmfDuration" size="3"/></td>
			  					</tr>
			  					<tr>
			  						<td><form:checkbox path="glucocorticoidOther"/>其他&nbsp;&nbsp;</td>
			  						<td>起始剂量<form:input path="glucocorticoidOtherInitialDose" size="3"/></td>
			  						<td>目前剂量<form:input path="glucocorticoidOtherCurrentDose" size="3"/></td>
			  						<td>用药总时间<form:input path="glucocorticoidOtherDuration" size="3"/></td>
			  					</tr>
			  				</table>
			  			</td>
			  		</tr>
					<tr>
						<td>其他：</td>
						<td>
							<form:checkbox path="shard"/>性激素及其相关药物&nbsp;&nbsp;&nbsp;&nbsp;用药时长<form:input path="shardDuration" size="3"/><br/>
							<form:checkbox path="aeds"/>抗癫痫药物&nbsp;&nbsp;&nbsp;&nbsp;用药时长<form:input path="aedsDuration" size="3"/><br/>
							<form:checkbox path="aluminumPreparation"/>铝制剂&nbsp;&nbsp;&nbsp;&nbsp;用药时长<form:input path="aluminumPreparationDuration" size="3"/><br/>
							<form:checkbox path="lithiumPreparations"/>锂制剂&nbsp;&nbsp;&nbsp;&nbsp;用药时长<form:input path="lithiumPreparationsDuration" size="3"/><br/>
							<form:checkbox path="heparin"/>肝素&nbsp;&nbsp;&nbsp;&nbsp;用药时长<form:input path="heparinDuration" size="3"/><br/>
							<form:checkbox path="aromataseInhibitor"/>芳重化酶抵制剂&nbsp;&nbsp;&nbsp;&nbsp;用药时长<form:input path="aromataseInhibitorDuration" size="3"/><br/>
							<form:checkbox path="lastOther"/>其他<form:input path="lastOtherDesc" size="3"/>用药时长<form:input path="lastOtherDuration" size="3"/><br/>
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
		var validationEngine = $("#editForm").validationEngine({
			promptPosition:'bottomLeft',
			showOneMessage: true
		});
    	<ewcms:showFieldError commandName="m"/>
	});
</script>