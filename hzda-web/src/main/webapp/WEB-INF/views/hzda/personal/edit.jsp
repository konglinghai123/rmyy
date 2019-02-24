<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="编辑 - 个人史"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/hzda/personal/save/${generalInformationId}" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
		        		<td width="30%">吸烟</td>
		        		<td width="70%">
		        			<form:checkbox path="noSmok"/>不吸&nbsp;&nbsp;&nbsp;&nbsp;
		        			<form:checkbox path="quitSmok"/>已戒(戒烟年龄<form:input path="quitSmokAge" size="3" cssClass="validate[custom[number]]"/>)&nbsp;&nbsp;&nbsp;&nbsp;
		        			<form:checkbox path="smok"/>目前吸烟(平均每日<form:input path="smokDay" size="3" cssClass="validate[custom[number]]"/>支，吸烟<form:input path="smokYear" size="3" cssClass="validate[custom[number]]"/>年)&nbsp;&nbsp;&nbsp;&nbsp;
		        			<form:checkbox path="smokRefuseAnswer"/>拒绝回答
		        		</td>
					</tr>
					<tr>
						<td>饮酒</td>
						<td>
							<form:checkbox path="noDrink"/>不饮或很少饮酒&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="quitDrink"/>已戒(年龄<form:input path="quitDrinkAge" size="3" cssClass="validate[custom[number]]"/>)&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="oftenDrink"/>经常(开始饮酒年龄<form:input path="oftenDrinkBeginAge" size="3" cssClass="validate[custom[number]]"/>&nbsp;&nbsp;
							<form:checkbox path="beer"/>啤酒，每天<form:input path="beerMl" size="3" cssClass="validate[custom[number]]"/>毫升;
							<form:checkbox path="spirit"/>白酒，每天<form:input path="spiritMl" size="3" cssClass="validate[custom[number]]"/>毫升;
							<form:checkbox path="redWine"/>红酒，每天<form:input path="redWineMl" size="3" cssClass="validate[custom[number]]"/>毫升)&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="drinkRefuseAnswer"/>拒绝回答
						</td>
					</tr>
					<tr>
						<td>膳食</td>
						<td>
							<form:checkbox path="fullDiet"/>普通膳食&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="vegetarianDiet"/>素食或基本素食&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="lowfatDiet"/>低脂肪膳食&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="diabeticDiet"/>糖尿病膳食&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="dietRefuseAnswer"/>拒绝回答
						</td>
					</tr>
					<tr>
						<td>饮料饮用</td>
						<td>
							<form:checkbox path="coffee"/>咖啡&nbsp;&nbsp;&nbsp;&nbsp;(每周<form:input path="coffeeWeeklyDrinks" size="3" cssClass="validate[custom[number]]"/>次，每次<form:input path="coffeeEveryTimeDrinks" size="3" cssClass="validate[custom[number]]"/>毫升)<br/>
							<form:checkbox path="strongTea"/>浓茶&nbsp;&nbsp;&nbsp;&nbsp;(每周<form:input path="strongTeaWeeklyDrinks" size="3" cssClass="validate[custom[number]]"/>次，每次<form:input path="strongTeaEveryTimeDrinks" size="3" cssClass="validate[custom[number]]"/>毫升)<br/>
							<form:checkbox path="sodas"/>碳酸饮料&nbsp;&nbsp;&nbsp;&nbsp;(每周<form:input path="sodasWeeklyDrinks" size="3" cssClass="validate[custom[number]]"/>次，每次<form:input path="sodasEveryTimeDrinks" size="3" cssClass="validate[custom[number]]"/>毫升)<br/>
							<form:checkbox path="milk"/>牛奶&nbsp;&nbsp;&nbsp;&nbsp;(每周<form:input path="milkWeeklyDrinks" size="3" cssClass="validate[custom[number]]"/>次，每次<form:input path="milkEveryTimeDrinks" size="3" cssClass="validate[custom[number]]"/>毫升)<br/>
							<form:checkbox path="otherDrink"/>其他<form:input path="otherDrinkDesc" size="3"/>&nbsp;&nbsp;&nbsp;&nbsp;(每周<form:input path="otherDrinkWeeklyDrinks" size="3" cssClass="validate[custom[number]]"/>次，每次<form:input path="otherDrinkEveryTimeDrinks" size="3" cssClass="validate[custom[number]]"/>毫升)<br/>
							<form:checkbox path="dadra"/>拒绝回答
						</td>
					</tr>
					<tr>
						<td>平均每天睡眠时间</td>
						<td>
							<form:checkbox path="sleepLessThanFourHours"/>小于4小时&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sleepFourToSixHours"/>4-6小时&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sleepSevenToEightHours"/>7-8小时&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sleepNineToTenHours"/>9-10小时&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sleepMoreThanTenHours"/>大于10小时&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="sleepRefuseAnswer"/>拒绝回答
						</td>
					</tr>
					<tr>
						<td>平均每天日晒时间</td>
						<td>
							<form:checkbox path="insolationLittle"/>很少&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="insolationMoreThanTenMin"/>平均每天10分钟以上&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="insolationMoreThanThirtyMin"/>平均每天30分钟以上
						</td>
					</tr>
					<tr>
						<td>平均每周锻炼时间</td>
						<td>
							<form:checkbox path="temperLessThanThree"/>小于3次&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperThreeToFive"/>3-5次&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperMoreThanFive"/>大于5次&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperNever"/>从不&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperRefuseAnswer"/>拒绝回答
						</td>
					</tr>
					<tr>
						<td>平均每次锻炼时间</td>
						<td>
							<form:checkbox path="temperLessThanThirtyMin"/>小于30分&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperThirtyToSixtyMin"/>30-60分&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperMoreThanSixtyMin"/>大于60分
						</td>
					</tr>
					<tr>
						<td>最常用锻炼方式</td>
						<td>
							<form:checkbox path="walk"/>散步&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="skelp"/>快步走&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="jog"/>慢跑&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="dance"/>跳舞&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="shadowboxing"/>太极拳&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="stairs"/>爬山/楼梯&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="fitnessEquipment"/>器材健身&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="playBall"/>打球&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="swim"/>游泳&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="cycling"/>骑车&nbsp;&nbsp;&nbsp;&nbsp;
							<form:checkbox path="temperOther"/>其他<form:input path="temperOtherDesc" size="3"/>
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