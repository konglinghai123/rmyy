<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 任务设置"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
			<form:form id="editForm" action="${ctx}/system/scheduling/jobinfo/${isClose}/save" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
				<form:hidden path="jobId"/>
				<form:hidden path="jobVersion"/>
				<form:hidden path="triggerId"/>
				<form:hidden path="triggerVersion"/>
				<form:hidden path="channelId"/>
				<form:hidden path="reportId"/>
				<form:hidden path="reportType"/>
				<c:choose>
					<c:when test="${empty m.jobId}">
						<input type="hidden" id="start" name="start" value="2"/>
					</c:when>
					<c:otherwise>
						<form:hidden path="start"/>
					</c:otherwise>
				</c:choose>
				
				<input type="hidden" id="className" name="className" value="${className}"/>
				<input type="hidden" id="objectId" name="objectId" value="${objectId}"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<c:if test="${!isClose}">
					<h1 class="title">任务操作</h1>
					<fieldset>
						<table class="formtable" style="align:center">
							<tr>
								<td width="10%">操作：</td>
								<td width="40%">
									<c:if test="${m.state=='正常'}">
										<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-pause'" onclick="pause(${m.jobId})" href="javascript:void(0);">暂停</a>
									</c:if>
									<c:if test="${m.state=='暂停'}">
										<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-resumed'" onclick="resumed(${m.jobId})" href="javascript:void(0);">恢复</a>
									</c:if>
								</td>
								<td width="10%">状态：</td>
								<td width="40%">${m.state}</td>
							</tr>
					</table>
					</fieldset>
				</c:if>
				<h1 class="title">任务信息</h1>
		 		<fieldset>
		  			<table class="formtable" style="align:center">
						<c:choose>
							<c:when test="${empty className}">
								<tr>
									<td width="10%">调度作业选择：</td>
				  					<td colspan="3">
				      					<form:select path="jobClassId" items="${jobClasses}" itemValue="id" itemLabel="name"/>
				  					</td>
			  					</tr>
							</c:when>
							<c:otherwise>
								<form:hidden path="jobClassId"/>
							</c:otherwise>
						</c:choose>
						<tr>
			  				<td width="10%">名称：</td>
			  				<td width="40%"><form:input path="label" cssClass="validate[required, maxLength[50]]"/></td>
			  				<td width="10%">&nbsp;</td>
			  				<td width="40%">
			    				<c:if test="${m.isJobChannel}">
			      					<form:checkbox path="subChannel" label=" 应用于子栏目" cssStyle="vertical-align: middle;"/>
			    				</c:if>
			  				</td>
						</tr>
						<tr>
			  				<td width="10%">说明：</td>
			  				<td colspan="3"><form:textarea path="description" cols="46" cssStyle="resize:none" placeholder="简要说明"/></td>
						</tr>
					</table>
				</fieldset>
				<h1 class="title">计划信息</h1>
		 		<fieldset>
		  			<table class="formtable" style="align:center">
						<tr>
			  				<td width="10%">开始时间：</td>
			  				<td colspan="3">
			  					<form:input path="startDate" cssClass="easyui-datetimebox" data-options="width:135,showSeconds:false,editable:false"/>
			  				</td>
						</tr>
						<tr>
			  				<td>调度方式：</td>
			  				<td colspan="3">
			    				<form:radiobutton path="mode" value="0" label=" 无" style="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
			    				<form:radiobutton path="mode" value="1" label=" 简单" style="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
			    				<form:radiobutton path="mode" value="2" label=" 复杂" style="vertical-align: middle;"/>
			  				</td>
						</tr>
						<tr id="trSimplicity" style="display:none;">
			  				<td>&nbsp;</td>
			  				<td colspan="3" style="padding: 1px 1px;">
			    				<table class="formtable" style="align:center">
				  					<tr>
										<td width="10%">发生：</td>
										<td colspan="3">
					  						<form:radiobutton path="occur" value="1" label=" 无限制" style="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
					  						<form:radiobutton path="occur" value="2" label=" 结束时间" style="vertical-align: middle;"/>
					  						<label for="occur2"><form:input path="endDateSimple" cssClass="easyui-datetimebox" data-options="width:135,showSeconds:false,editable:false"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
					  						<form:radiobutton path="occur" value="3" label=" " cssStyle="vertical-align: middle;"/>
					  						<form:input path="occurrenceCount" size="4" cssClass="validate[custom[integer]]"/>&nbsp;<form:label path="occurrenceCount">次数</form:label>
				  						</td>
				  					</tr>
				  					<tr>
										<td>每隔：</td>
										<td colspan="3">
					  						<form:input path="recurrenceInterval" cssClass="validate[required,custom[integer]]"/>
					  						<form:select path="recurrenceIntervalUnit">
					    						<form:option value="1" label="分钟"/>
					    						<form:option value="2" label="小时"/>
					    						<form:option value="3" label="天"/>
					    						<form:option value="4" label="星期"/>
					  						</form:select>
					  						执行一次&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>
										</td>
				  					</tr>
								</table>
							</td>
						</tr>
						<tr id="trComplexity">
			  				<td>&nbsp;</td>
			 				<td colspan="3" style="padding: 1px 1px;">
								<table class="formtable" style="align:center">
				  					<tr>
										<td width="10%">结束时间：</td>
										<td colspan="3"><form:input path="endDateCalendar" cssClass="easyui-datetimebox" data-options="width:135,showSeconds:false,editable:false"/></td>
				  					</tr>
				  					<tr>
										<td width="10%">分钟：</td>
										<td width="40%">
											<form:input path="minutes" size="2" maxlength="2" cssClass="validate[required,custom[integer],min[0],max[59]]"/>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>&nbsp;&nbsp;（在0-59之间）
										</td>
										<td width="10%">小时：</td>
										<td width="40%">
											<form:input path="hours" size="2" maxlength="2" cssClass="validate[required,custom[integer],min[0],max[59]]"/>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>&nbsp;&nbsp;（在0-23之间）
										</td>
				  					</tr>
				  					<tr>
										<td>天数：</td>
										<td colspan="3"><form:radiobutton id="days1" path="days" value="1" label=" 每一天" cssClass="validate[groupRequired[days]]" cssStyle="vertical-align: middle;" /></td>
				  					</tr>
				  					<tr>
										<td>&nbsp;</td>
										<td colspan="3"><form:radiobutton id="days2" path="days" value="2" label=" 一周内" cssClass="validate[groupRequired[days]]" cssStyle="vertical-align: middle;"/></td>
				  					</tr>
				  					<tr>
										<td>&nbsp;</td>
										<td colspan="3">
					  						<form:checkbox path="weekDays" value="1" label=" 星期一" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="weekDays" value="2" label=" 星期二" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="weekDays" value="3" label=" 星期三" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="weekDays" value="4" label=" 星期四" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="weekDays" value="5" label=" 星期五" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="weekDays" value="6" label=" 星期六" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="weekDays" value="7" label=" 星期天" cssClass="validate[condRequired[days2]]" cssStyle="vertical-align: middle;"/>
					  						（<input type="checkbox" id="weekDaysAll" name="weekDaysAll" style="vertical-align: middle;"/><label for="weekDaysAll" style="vertical-align: middle;">&nbsp;全选</label>）
				    					</td>
				  					</tr>
				  					<tr>
										<td>&nbsp;</td>
										<td colspan="3">
					  						<form:radiobutton id="days3" path="days" value="3" label=" 一个月内" cssClass="validate[groupRequired[days]]" cssStyle="vertical-align: middle;"/>
					  						<form:input path="monthDays" cssClass="validate[condRequired[days3]]" cssStyle="vertical-align: middle;" size="30" placeholder="多个日期之间用逗号隔开"/>
										</td>
				  					</tr>
				  					<tr>
										<td>月份：</td>
										<td colspan="3">
					  						<form:checkbox path="months" value="1" label=" 一月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="2" label=" 二月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="3" label=" 三月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="4" label=" 四月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="5" label=" 五月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="6" label=" 六月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="7" label=" 七月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="8" label=" 八月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="9" label=" 九月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="10" label=" 十月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					 						<form:checkbox path="months" value="11" label=" 十一月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						<form:checkbox path="months" value="12" label=" 十二月" cssClass="validate[minCheckbox[1]]" cssStyle="vertical-align: middle;"/>
					  						（<input type="checkbox" name="monthsAll" id="monthsAll" style="vertical-align: middle;"/><label for="monthsAll">&nbsp;全选）</label>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>
										</td>
				  					</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				<c:if test="${not empty m.pageShowParams}">
					<h1 class="title">参数信息</h1>
					<fieldset>
		  				<table class="formtable" style="align:center">
							<c:forEach items="${m.pageShowParams}" var="pageShowParam">
								<tr>
			  						<td width="10%">
			    						<c:choose>
			      							<c:when test="${not empty pageShowParam.cnName}">${pageShowParam.cnName}</c:when>
			      							<c:otherwise>${pageShowParam.enName}</c:otherwise>
			    						</c:choose>
			  						</td>
			  						<td colspan="3">
			    						<c:if test="${pageShowParam.type == 'TEXT' || pageShowParam.type == 'BOOLEAN' || pageShowParam.type == 'SQL' || pageShowParam.type == 'SESSION'}">
			  	  							<input type="text" name="${pageShowParam.enName}" value="${pageShowParam.defaultValue}"/>
			  							</c:if>
			  							<c:if test="${pageShowParam.type == 'LIST'}">
			  	  							<form:select cssClass="easyui-combobox" path="${paramShowParam.enName}" items="${pageShowParam.value}" data-options="editable:false"/>
			  							</c:if>
			  							<c:if test="${pageShowParam.type == 'CHECK'}">
			  	  							<form:checkboxes path="${paramShowParam.enName}"  items="${pageShowParam.value}" onclick="checkBoxValue('${paramShowParam.enName}')"/>
			  	  							<input type="hidden" name="${pageShowParam.enName}"/>
			  							</c:if>
			  							<c:if test="${pageShowParam.type == 'DATE'}">
			  	  							<input class="easyui-datebox" type="text" name="${pageShowParam.enName}" value="${pageShowParam.defaultValue}" data-options="editable:false"/>
			  							</c:if>
			  						</td>
								</tr>
							</c:forEach>
						</table>
					</fieldset>
				</c:if>
				<c:if test="${m.reportType == 'text'}">
					<h1 class="title">输出格式</h1>
					<fieldset>
		  				<table class="formtable" style="align:center">
							<tr>
			  					<td width="10%">输出格式：</td>
			  					<td colspan="3">
			    					<form:checkbox path="outputFormats" value="1" label="pdf" cssStyle="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
			    					<form:checkbox path="outputFormats" value="2" label="xls" cssStyle="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
			    					<form:checkbox path="outputFormats" value="3" label="rtf" cssStyle="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
			    					<form:checkbox path="outputFormats" value="4" label="xml" cssStyle="vertical-align: middle;"/>
			  					</td>
							</tr>
						</table>
					</fieldset>
				</c:if>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<c:if test="${isClose}">
	  			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
	  		</c:if>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		<c:if test="${empty className}">$("#jobClassId").combobox({disabled:true,panelHeight:'auto'});</c:if>

		$("#recurrenceIntervalUnit").combobox({
			width:60,
			editable:false,
			panelHeight:'auto'
		});
		
		<c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
		</c:choose>
	});
	
	<c:if test="${isClose}">
		$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
	</c:if>
	
	function pause(id){
		$.post('${ctx}/system/scheduling/jobinfo/' + id + '/pause', {}, function(result) {
			if (result.success){
				location.reload();
			}
		});
	}
	
	function resumed(id){
		$.post('${ctx}/system/scheduling/jobinfo/' + id + '/resumed', {}, function(result) {
			if (result.success){
				location.reload();
			}
		});
	}
	
	var monthsAllChecked = true;
	var weekDaysAllChecked = true;
	$("[name='months']:checkbox").each(function() {
		if (!$(this).is(':checked')) {
			monthsAllChecked = false;
		}
	});
	$('#monthsAll').prop('checked', monthsAllChecked);
	
	$("[name='weekDays']:checkbox").each(function() {
		if (!$(this).is(':checked')) {
			weekDaysAllChecked = false;
		}
	});
	$('#weekDaysAll').prop('checked', weekDaysAllChecked);
	
	$('#monthsAll').bind('click', function() {
		$("[name='months']:checkbox").prop('checked', $(this).is(':checked'));
	});
	
	$('#weekDaysAll').bind('click', function() {
		$("[name='days']").get(1).checked = true;
		$("[name='weekDays']:checkbox").prop('checked', $(this).is(':checked'));
	});
	
	$("[name='weekDays']:checkbox").click(function() {
		$("[name='days']").get(1).checked = true;
		var weekChecked = true;
		$("[name='weekDays']:checkbox").each(function() {
			if (!$(this).prop('checked')) {
				weekChecked = false;
			}
		});
		$('#weekDaysAll').prop('checked', weekChecked);
	});
	
	$("[name='months']:checkbox").bind('click', function() {
		var monthsChecked = true;
		$("[name='months']:checkbox").each(function() {
			if (!$(this).prop('checked')) {
				monthsChecked = false;
			}
		});
		$('#monthsAll').prop('checked', monthsChecked);
	});
	
	$('#monthDays').bind('click', function() {
		$("input[name='days']").get(2).checked = true;
	});
	
	$('#occurrenceCount').bind('click', function() {
		$("input[name='occur']").get(2).checked = true;
	});
	
	$("[name='days']").bind('click', function() {
		if ($("input[name='days']:checked").val() == 3) {
			$('#monthDays').focus();
		}
	});
	
	$("[name='occur']").bind('click', function() {
		var occurId = $("input[name='occur']:checked").val();
		if (occurId == 3) {
			$('#occurrenceCount').focus();
		} else if (occurId == 2) {
			$("input[name='endDateSimple']").focus();
		}
	});
	$("input[name='mode']").bind('click', function() {
		var modeId = $("input[name='mode']:checked").val();
		if (typeof modeId == 'undefined') {
			modeId = 1;
			$("input[name='mode']").get(modeId).checked = true;
			$("input[name='occur']").get(0).checked = true;
		}
		if (modeId == 0) {
			$('#trSimplicity').hide();
			$('#trComplexity').hide();
		} else if (modeId == 1) {
			$('#trSimplicity').show();
			$('#trComplexity').hide();
		} else {
			$('#trSimplicity').hide();
			$('#trComplexity').show();
		}
	});
	var modeId = $("input[name='mode']:checked").val();
	if (typeof modeId == 'undefined' || $('#jobId').val() == "") {
		modeId = 1;
		$("input[name='mode']").get(modeId).checked = true;
		$("input[name='occur']").get(0).checked = true;
	}
	if (modeId == 0) {
		$('#trSimplicity').hide();
		$('#trComplexity').hide();
	} else if (modeId == 1) {
		$('#trSimplicity').show();
		$('#trComplexity').hide();
	} else {
		$('#trSimplicity').hide();
		$('#trComplexity').show();
	}
	var occurId = $("input[name='occur']:checked").val();
	if (typeof occurId == 'undefined' || $('#jobId').val() == "") {
		$("input[name='occur']").get(0).checked = true;
	}
	var daysId = $("input[name='days']:checked").val();
	if (typeof daysId == 'undefined' || $('#jobId').val() == "") {
		$("input[name='days']").get(0).checked = true;
	}
</script>