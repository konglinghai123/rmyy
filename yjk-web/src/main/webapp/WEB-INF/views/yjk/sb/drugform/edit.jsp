<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="申报-新药申报"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">
			<form:form id="editForm" method="post" action="${ctx}/yjk/sb/drugform/drugdeclare" commandName="m"  class="form-horizontal">
        		<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden id="commonNameContentsId" path="commonNameContents.id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
        		<table class="formtable">
              		<c:forEach items="${commonNameRuleList}" var="commonNameRule" varStatus="status">
						<c:choose>
							<c:when test="${commonNameRule.ruleName == 'common.commonName'}">
				        	<tr>
								<td width="30%">${commonNameRule.ruleCnName}(拼音)：</td>
								<td width="70%">							
								<input type="text" id="CNRule${status.index}" name="commonNameContents.${commonNameRule.ruleName}" class="easyui-combobox" data-options="
									valueField:'id',
									textField:'commonName',
									method: 'get',
									width:200,
									required:true,
									panelHeight:200,
									onSelect:function(rec){
										$('#commonNameContentsId').val(rec.id);
										$('#queryCNRule${status.index}').val(rec.commonName);
	    	   							$('#CNRule${status.index+1}').combobox('reload','${ctx}/yjk/zd/commonnamecontents/querydeclare');
	       							},
	       							onBeforeLoad: function(param){
	        	 						param['parameters']=$('#queryform').serializeObject();
	        	 						
	     							},
	     							onChange: function (newValue, oldValue) {
								        	if(newValue==$('#CNRule${status.index}').combobox('getText')){
								        		$('#CNRule${status.index}').combobox('reload','${ctx}/yjk/zd/commonname/findbyspell?spell='+newValue);
								    		}
								         }"/>
     							</td>
							</tr>
							</c:when>
							<c:otherwise>
					        	<tr>
									<td width="20%">${commonNameRule.ruleCnName}：</td>
									<td width="70%">
										<input type="text" id="CNRule${status.index}" name="commonNameContents.${commonNameRule.ruleName}" class="easyui-combobox" data-options="
										valueField:'id',
										textField:'${commonNameRule.ruleName}',
										width:200,
										required:true,
										panelHeight:200,
										onSelect:function(rec){
											$('#commonNameContentsId').val(rec.id);
											$('#queryCNRule${status.index}').val(rec.${commonNameRule.ruleName});
		    	   							$('#CNRule${status.index+1}').combobox('reload','${ctx}/yjk/zd/commonnamecontents/querydeclare');
		       							},
		       							onBeforeLoad: function(param){
		        	 						param['parameters']=$('#queryform').serializeObject();
		     							}"/>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>		
					</c:forEach>
           		</table>
          	</form:form>
		 	<form id="queryform">
				<c:forEach items="${commonNameRuleList}" var="commonNameRule" varStatus="status">
					<input type="hidden" id="queryCNRule${status.index}" name="EQ_${commonNameRule.ruleName}"/>
				</c:forEach>
			</form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
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
	