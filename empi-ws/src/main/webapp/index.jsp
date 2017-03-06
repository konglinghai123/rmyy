<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>JavaScript SOAP Client Test</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>
	</head>

	<body>
		<table>
			<tr>
				<td>卡号:</td>
				<td><input type="text" id="practiceNo" name="practiceNo" /></td>
			</tr>
			<tr>
				<td>HL7版本号：</td>
				<td>
					<select id="version" name="version">
						<option value ="v2.1">v2.1</option>
						<option value ="v2.2">v2.2</option>
						<option value ="v2.3">v2.3</option>
						<option value="v2.3.1">v2.3.1</option>
						<option value="v2.4" selected="selected">v2.4</option>
						<option value="v2.5">v2.5</option>
						<option value="v2.5.1">v2.5.1</option>
						<option value="v2.6">v2.6</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>消息处理ID：</td>
				<td>
					<select id="processingId" name="processingId">
						<option value ="T" selected="selected">测试环境</option>
						<option value ="P">正式环境</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>消息：</td>
				<td>
					XML查询格式：<br/>
					<textarea id="qryMessageXml" cols="100" rows="3"></textarea><br/>
					ER7查询格式：<br/>
					<div id="qryMessageEr7" style="border: 1px solid #808080; font-family: monospace; color: #000000; white-space: nowrap; overflow: auto;"></div><br/>
					<input type="button" id="compositePracticeNo" value="把卡号组合成HL7格式的QRY消息"/>
					<br/><br/><br/>
					XML查询返回格式：<br/>
					<textarea id="adrMessageXml" cols="100" rows="3"></textarea><br/>
					ER7查询返回格式：
					<div id="adrMessageEr7" style="border: 1px solid #808080; font-family: monospace; color: #000000; white-space: nowrap; overflow: auto;"></div><br/>
					<input type="button" id="queryPatient" value="执行查询患者信息"/>
					<br/><br/><br/>
					患者注册格式：&nbsp;&nbsp;&nbsp;&nbsp;样式：<select id="styleSel" name="styleSel">
						<option value ="xml">XML</option>
						<option value ="er7" selected="selected">ER7</option>
					</select><br/>
					注册格式：
					<textarea id="regMessage" cols="100" rows="3"></textarea><br/>
					返回注册格式：
					<div id="regResult" style="border: 1px solid #808080; font-family: monospace; color: #000000; white-space: nowrap; overflow: auto;"></div><br/>
					<input type="button" id="registerPatient" value="执行注册患者信息"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="查看WSDL" onclick="javascript:window.open(ctx + '/webservice');" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="ajaxBack"></div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			var ctx = '<%=request.getScheme()%>' + '://' + '<%=request.getServerName()%>' + ':' + '<%=request.getServerPort()%>' + '<%=request.getContextPath()%>';
			var webServiceUrl = ctx + '/webservice/patient';
			var qryXml, qryEr7;
			$(function(){
				//$('#qrymessage').hide();
				//$('#hl7message').hide();
				
				$('#compositePracticeNo').bind('click', function(){
					var val = $("#practiceNo").val();
					<!--可以通过拦截器获取请求信息-->
					var str = '<?xml version="1.0" encoding="UTF-8"?>'+
							'<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
							'<soap:Body>'+
							'<ns2:compositePracticeNo xmlns:ns2="http://ewcms.com/patient">'+
							'<practiceNo>' + val + '</practiceNo>'+
							'<version>' +  $('#version').val() + '</version>' +
							'<processingId>' + $('#processingId').val() + '</processingId>' +
							'<style>er7</style>' +
							'</ns2:compositePracticeNo>' + 
							 '</soap:Body>'+
							'</soap:Envelope>';
								  
					$.ajax({
						contentType:'application/xml;charset="UTF-8"',
						dataType:'xml',//发送数据格式
						type:'post',
						url:webServiceUrl,		//直接发向这个地址
						data:str,
						success:function(data){
							qryEr7 = $(data).find("hl7Result").first().text();
							document.getElementById('qryMessageEr7').innerHTML = update(qryEr7);
						}
					});
					
					str = '<?xml version="1.0" encoding="UTF-8"?>'+
							'<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
							'<soap:Body>'+
							'<ns2:compositePracticeNo xmlns:ns2="http://ewcms.com/patient">'+
							'<practiceNo>' + val + '</practiceNo>'+
							'<version>' +  $('#version').val() + '</version>' +
							'<processingId>' + $('#processingId').val() + '</processingId>' +
							'<style>xml</style>' +
							'</ns2:compositePracticeNo>' + 
							 '</soap:Body>'+
							'</soap:Envelope>';
						  
					$.ajax({
						contentType:'application/xml;charset="UTF-8"',
						dataType:'xml',//发送数据格式
						type:'post',
						url:webServiceUrl,		//直接发向这个地址
						data:str,
						success:function(data){
							qryXml = $(data).find("hl7Result").first().text();
							document.getElementById('qryMessageXml').innerHTML = qryXml;
						}
					});
					
				})
				
				$("#queryPatient").bind('click', function(){
					if($.trim(qryEr7)=='' || $.trim(qryXml)==''){
						alert('请选执行[把卡号组合成HL7格式的QRY]按钮');
						return;
					}
					
					<!--可以通过拦截器获取请求信息-->
					var str = '<?xml version="1.0" encoding="UTF-8"?>'+
							'<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
							'<soap:Body>'+
							'<ns2:queryPatient xmlns:ns2="http://ewcms.com/patient">'+
							'<qryMessage><![CDATA[' + qryEr7 + ']]></qryMessage>'+
							'<version>' +  $('#version').val() + '</version>' +
							'<processingId>' + $('#processingId').val() + '</processingId>' +
							'<style>er7</style>' +
							'</ns2:queryPatient>' + 
							'</soap:Body>'+
							'</soap:Envelope>';
								  
					$.ajax({
						contentType:'application/xml;charset="UTF-8"',
						dataType:'xml',//发送数据格式
						type:'post',
						url:webServiceUrl,		//直接发向这个地址
						data:str,
						success:function(data){
							document.getElementById('adrMessageEr7').innerHTML = update($(data).find("hl7Result").first().text());
						}
					});
					
					str = '<?xml version="1.0" encoding="UTF-8"?>'+
							'<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
							'<soap:Body>'+
							'<ns2:queryPatient xmlns:ns2="http://ewcms.com/patient">'+
							'<qryMessage><![CDATA[' + qryXml + ']]></qryMessage>'+
							'<version>' +  $('#version').val() + '</version>' +
							'<processingId>' + $('#processingId').val() + '</processingId>' +
							'<style>xml</style>' +
							'</ns2:queryPatient>' + 
							'</soap:Body>'+
							'</soap:Envelope>';
								  
					$.ajax({
						contentType:'application/xml;charset="UTF-8"',
						dataType:'xml',//发送数据格式
						type:'post',
						url:webServiceUrl,		//直接发向这个地址
						data:str,
						success:function(data){
							document.getElementById('adrMessageXml').innerHTML = $(data).find("hl7Result").first().text();
						}
					});
				});
				
				$("#registerPatient").bind('click', function(){
					var val = $("#regMessage").val();
					if($.trim(val)==""){
						alert("请输入患者注册的HL7消息");
						return;
					}
					<!--可以通过拦截器获取请求信息-->
					var str = '<?xml version="1.0" encoding="UTF-8"?>' +
							'<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">' +
							'<soap:Body>' +
							'<ns2:registerPatient xmlns:ns2="http://ewcms.com/patient">' +
							'<adtMessage><![CDATA[' + val + ']]></adtMessage>' +
							'<version>' +  $('#version').val() + '</version>' +
							'<processingId>' + $('#processingId').val() + '</processingId>' +
							'<style>' + $('#styleSel').val() + '</style>' +
							'</ns2:registerPatient>' + 
							'</soap:Body>' +
							'</soap:Envelope>';
								  
					$.ajax({
						contentType:'application/xml;charset="UTF-8"',
						dataType:'xml',//发送数据格式
						type:'post',
						url:webServiceUrl,		//直接发向这个地址
						data:str,
						success:function(data){
							//var ss = "HL7患者信息：<br/>" + $(data).find("hl7Result").first().text();
							//$("#ajaxBack").html(ss).css("border","1px solid blue").css({width:'50%'}).appendTo($("body"));
							document.getElementById('regResult').innerHTML = update($(data).find("hl7Result").first().text());
						}
					});
				});
			});
			function update(value) {
				var lines = value.split("\r");
				var output = "";
				for (var index in lines) {
					var nextLine = lines[index];
					if (nextLine.length < 3) {
						output = output + nextLine + "<br>";
						continue;
					}
					
					var fields = nextLine.split("|");
					for (var fIndex in fields) {
						var nextField = fields[fIndex];
						if (fIndex == 0) {
							output = output + "<span style=\"color: #000080;\"><b>" + nextField + "</b></span>";
						} else {
							
							var reps = nextField.split("~");
							for (var rIndex in reps) {
								var nextRep = reps[rIndex];
								
								if (rIndex > 0) {
									output = output + "<span style=\"color: #808080;\">~</span>";
								}
		
								var comps = nextRep.split("^");
								for (var cIndex in comps) {
									var nextComp = comps[cIndex];
		
									if (cIndex > 0) {
										output = output + "<span style=\"color: #808080;\">^</span>";
									}
		
									var subcomps = nextComp.split("&");
									for (var sIndex in subcomps) {
										var nextSubComp = subcomps[sIndex];
										
										if (sIndex > 0) {
											output = output + "<span style=\"color: #808080;\">&</span>";
										}
										
										if (nextSubComp.match(/^[0-9./]+/)) {
											nextSubComp = "<span style=\"color: #990033;\">" + nextSubComp + "</span>";
										}
										
										output = output + nextSubComp;
										
									}
								}
							}
						}
						output = output + "<span style=\"color: #808080;\"><b>|</b></span>";
					}
					
					output = output + "<br>";
				}
				return output;
			}
		</script>
	</body>
</html>