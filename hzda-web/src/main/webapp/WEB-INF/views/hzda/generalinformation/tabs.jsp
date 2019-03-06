<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="一般信息 - 标签页"/>
	<div id="tab-generalinformation" class="easyui-tabs" data-options="fit:true,tabPosition:'top',border:true,headerWidth:120">
		<div title="主诉" style="padding:2px;overflow:hidden;">
			<iframe id="editcomplainedifr" name="editcomplainedifr" class="editifr" src="${ctx}/hzda/complained/index/${generalInformationId}"></iframe>
		</div>			
		<div title="风险评估" style="padding:2px;overflow:hidden;">
			<iframe id="editriskevaluationifr"  name="editriskevaluationifr" class="editifr" src=""></iframe>	
		</div>
		<div title="现病史" style="padding:2px;overflow:hidden;">
			<iframe id="editpresentillnessifr"  name="editpresentillnessifr" class="editifr" src=""></iframe>	
		</div>
		<div title="既往史" style="padding:2px;overflow:hidden;">
			<iframe id="editanamnesisifr"  name="editanamnesisifr" class="editifr" src=""></iframe>	
		</div>					
		<div title="骨折史" style="padding:2px;overflow:hidden;">
			<iframe id="editcataclasisifr"  name="editcataclasisifr" class="editifr" src=""></iframe> 
		</div>
		<div title="手术史" style="padding:2px;overflow:hidden;">
			<iframe id="editoperationifr"  name="editoperationifr" class="editifr" src=""></iframe> 
		</div>
		<div title="个人史" style="padding:2px;overflow:hidden;">
			<iframe id="editpersonalifr"  name="editpersonalifr" class="editifr" src=""></iframe> 
		</div>
		<div title="婚育史" style="padding:2px;overflow:hidden;">
			<iframe id="editmarriagebearingifr"  name="editmarriagebearingifr" class="editifr" src=""></iframe> 
		</div>
		<div title="家庭史" style="padding:2px;overflow:hidden;">
			<iframe id="editfamilyifr"  name="editfamilyifr" class="editifr" src=""></iframe> 
		</div>
		<div title="肾移植患者" style="padding:2px;overflow:hidden;">
			<iframe id="editrenaltransplantifr"  name="editrenaltransplantifr" class="editifr" src=""></iframe> 
		</div>
		<div title="查体" style="padding:2px;overflow:hidden;">
			<iframe id="editexaminedifr"  name="editexaminedifr" class="editifr" src=""></iframe> 
		</div>
		<div title="诊断" style="padding:2px;overflow:hidden;">
			<iframe id="editdiagnosisifr"  name="editdiagnosisifr" class="editifr" src=""></iframe> 
		</div>
		<div title="检查及化验结果" style="padding:2px;overflow:hidden;">
			<iframe id="editexaminationlaboratoryresultsifr"  name="editexaminationlaboratoryresultsifr" class="editifr" src=""></iframe> 
		</div>
		<div title="骨密度" style="padding:2px;overflow:hidden;">
			<iframe id="editbonedensityifr"  name="editbonedensityifr" class="editifr" src=""></iframe> 
		</div>
		<div title="影像学检查:骨折" style="padding:2px;overflow:hidden;">
			<iframe id="editfractureifr"  name="editfractureifr" class="editifr" src=""></iframe> 
		</div>
		<div title="其他相关检查" style="padding:2px;overflow:hidden;">
			<iframe id="editfractureotherifr"  name="editfractureotherifr" class="editifr" src=""></iframe> 
		</div>
		<div title="用药记录" style="padding:2px;overflow:hidden;">
			<iframe id="editmedicationrecordifr"  name="editmedicationrecordifr" class="editifr" src=""></iframe> 
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tab-generalinformation').tabs({
			onSelect:function(title){
				if (title == '主诉'){
					$('#editcomplainedifr').attr('src', '${ctx}/hzda/complained/index/${generalInformationId}');
				} else if (title == '风险评估'){
					$('#editriskevaluationifr').attr('src','${ctx}/hzda/riskevaluation/index/${generalInformationId}');
				} else if (title == '现病史'){
					$('#editpresentillnessifr').attr('src','${ctx}/hzda/presentillness/index/${generalInformationId}');
				} else if (title == '既往史'){
					$('#editanamnesisifr').attr('src','${ctx}/hzda/anamnesis/index/${generalInformationId}');
				} else if (title == '骨折史'){
					$('#editcataclasisifr').attr('src','${ctx}/hzda/cataclasis/index/${generalInformationId}');
				} else if (title == '手术史'){
					$('#editoperationifr').attr('src','${ctx}/hzda/operation/index/${generalInformationId}');
				} else if (title == '个人史'){
					$('#editpersonalifr').attr('src','${ctx}/hzda/personal/index/${generalInformationId}');
				} else if (title == '婚育史'){
					$('#editmarriagebearingifr').attr('src','${ctx}/hzda/marriagebearing/index/${generalInformationId}');
				} else if (title == '家庭史'){
					$('#editfamilyifr').attr('src','${ctx}/hzda/family/index/${generalInformationId}');
				} else if (title == '肾移植患者'){
					$('#editrenaltransplantifr').attr('src','${ctx}/hzda/renaltransplant/index/${generalInformationId}');
				} else if (title == '查体'){
					$('#editexaminedifr').attr('src','${ctx}/hzda/examined/index/${generalInformationId}');
				} else if (title == '诊断'){
					$('#editdiagnosisifr').attr('src','${ctx}/hzda/diagnosis/index/${generalInformationId}');
				} else if (title == '检查及化验结果'){
					$('#editexaminationlaboratoryresultsifr').attr('src','${ctx}/hzda/examinationlaboratoryresults/index/${generalInformationId}');
				} else if (title == '骨密度'){
					$('#editbonedensityifr').attr('src','${ctx}/hzda/bonedensity/index/${generalInformationId}');
				} else if (title == '影像学检查:骨折'){
					$('#editfractureifr').attr('src','${ctx}/hzda/fracture/index/${generalInformationId}');
				} else if (title == '其他相关检查'){
					$('#editfractureotherifr').attr('src','${ctx}/hzda/fractureother/index/${generalInformationId}');
				} else if (title == '用药记录'){
					$('#editmedicationrecordifr').attr('src','${ctx}/hzda/medicationrecord/index/${generalInformationId}');
				}
			}
		});
	});
</script>
