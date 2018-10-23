<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="申报-新药申报"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">
			<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<c:forEach items="${commonNameRuleList}" var="commonNameRule">	
						<c:choose>
							<c:when test="${commonNameRule.ruleName == 'commonName'}">
				        	<tr>
								<td width="30%">${commonNameRule.ruleCnName}：</td>
								<td width="70%"><input type="text" id="abc1" name="EQ_${commonNameRule.ruleName}"  class="easyui-combobox" data-options="
								valueField:'id',
								textField:'${commonNameRule.ruleName}',
								panelHeight:200,
								url:'${ctx}/yjk/zd/commonnamecontents/query1',
								onSelect:function(rec){
									$('#def1').val(rec.);
    	   							$('#pill').combobox('reload');
       							},
       							onBeforeLoad: function(param){
        	 						param['parameters']=$('#queryform').serializeObject();
     							}
       							"/></td>
							</tr>
							</c:when>
							<c:otherwise>
					        	<tr>
									<td width="20%">${commonNameRule.ruleCnName}：</td>
									<td width="70%">
										<input id="${commonNameRule.ruleName}" name="EQ_${commonNameRule.ruleName}"  class="easyui-combobox" data-options="
										valueField:'id',
										textField:'${commonNameRule.ruleName}',
										panelHeight:200,
										onSelect:function(rec){
											var url='${ctx}/yjk/zd/commonnamecontents/query1'
		    	   							$('#pill').combobox('reload',url);
		       							}">
									</td>
								</tr>
							</c:otherwise>
						</c:choose>		
					</c:forEach>
           		</table>
          </form>
		 	<form:form id="editForm" method="post" action="${ctx}/yjk/sb/drugform/save1" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<c:forEach items="${commonNameRuleList}" var="commonNameRule">	
						<c:choose>
							<c:when test="${commonNameRule.ruleName == 'commonName'}">
								<input type="hidden" id="def${index}" name="${commonNameRule.ruleName}"/>
							</c:when>
						</c:choose>
				</c:forEach>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$('#commonName').combobox({
		method: 'POST',
		url:'${ctx}/yjk/zd',
        onChange: function (newValue, oldValue) {
        	if(newValue==$("#commonName").combobox("getText")){
        		$('#commonName').combobox('reload','${ctx}/yjk/zd/commonname/findbyspell?spell='+newValue);
    		}
         }
	});
	$(function(){
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
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
	