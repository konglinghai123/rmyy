<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 提醒事项"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
			  	<table class="formtable">
		        	<tr>
				  		<td width="20%"><form:label path="title">标题：</form:label></td>
				  		<td width="80%"><form:input path="title" cssClass="validate[required,maxSize[100]]"/></td>
			    	</tr>
			    	<tr>
	             		<td><form:label path="startDate">开始日期：</form:label></td>
			  			<td><form:input path="startDate" cssClass="validate[required]"  data-prompt-position="bottomRight:75,25"/></td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="length">持续天数：</form:label></td>
			    		<td><form:input path="length" cssClass="validate[required]"/>&nbsp;&nbsp;<label><input class="all-day" type="checkbox">全天提醒事项</label></td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="startTime">开始时间：</form:label></td>
			    		<td><form:input path="startTime" cssClass="validate[required]" data-prompt-position="bottomRight:75,85"/></td>
			    	</tr>
			    	<tr>
			        	<td><form:label path="endTime">结束时间：</form:label></td>
			    		<td><form:input path="endTime" cssClass="validate[required]" data-prompt-position="bottomRight:75,105"/></td>
			        </tr>
			        <tr>
			        	<td><form:label path="details">备注：</form:label></td>
			        	<td><form:textarea path="details" cssClass="validate[maxSize[500]]"/></td>
			        </tr>
			        <tr>
			        	<td><form:label path="backgroundColor">背景颜色：</form:label></td>
			        	<td>
			        		<select id="backgroundColor" name="backgroundColor" style="background: ${backgroundColorList[0]}">
                			<c:forEach items="${backgroundColorList}" var="c">
                    			<option style="background: ${c}" value="${c}">&nbsp;</option>
                			</c:forEach>
							</select>
						</td>
			        </tr>
			  	</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a id="tb-submit" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#startDate').datebox({});
		$('#startTime').timespinner({showSeconds: true});
		$('#endTime').timespinner({showSeconds: true});
		
        $(".all-day").change(function() {
            if($(this).is(":checked")) {
                $("#startTime,#endTime").timespinner('disable').removeClass("validate[required]");
                $("#startTime").validationEngine("hide");
                $("#endTime").validationEngine("hide");
            } else {
                $("#startTime,#endTime").timespinner('enable').addClass("validate[required]");
            }
        });
        
        $('#tb-submit').bind('click', function(){
            if(!$('#editForm').validationEngine('validate')) {
                return false;
            }
            var url = "${ctx}/personal/calendar/save";
            $.post(url, $('#editForm').serialize(), function(data) {
            	if (data.success){
            		parent.calendar.fullCalendar("refetchEvents");
            		parent.$('#edit-window').window('close');
            	}
            });
        });
        
		var validationEngine = $("#editForm").validationEngine({
			promptPosition:'bottomRight',
			validateNonVisibleFields:true,
			showOneMessage: true
		});
		<ewcms:showFieldError commandName="m"/>
	});
</script>
