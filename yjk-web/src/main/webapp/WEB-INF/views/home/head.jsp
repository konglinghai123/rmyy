<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table style="width:100%;height:100%">
	<tr>
		<td width="189px" height="35" style="text-align:left">
			<table style="width:100%;">
				<tr>
					<td><img src="${ctx}/static/image/top_bg_ewcms.gif" height="30px" border="0" style="border:0;padding-left:4px;padding-top:10px;"/></td>
				</tr>
				<tr><td align="center">申报评审系统V2.0</td></tr>
			</table>
		</td>
		<td>
			<div class="tabs-wrap" style="margin-top:37px;padding-left:58px;">
				<ul class="tabs" style="border: none;">
					<li id="menu-index" class="tabs-selected"><a href="javascript:void(0);" class="tabs-inner"><span class="tabs-title">首页</span></a></li>
					<c:forEach items="${menus}" var="m">
						<li id="menu-${m.id}"><a href="javascript:void(0);" class="tabs-inner"><span class="tabs-title">${m.name}</span></a></li>
					</c:forEach>
				</ul>
			</div>
		</td>
		<td>
			<div style="position:absolute;right:10px;text-align:right;top:10px;width:50%;">
				<span id="user-name" style="font-size:13px;font-weight: bold;">
					${user.username}<c:if test="${not empty user.realname}">(${user.realname})</c:if>
					<c:if test="${isRunas}">[上一个身份：${previousUsername}&nbsp;|&nbsp;<a href="${ctx}/switchBack">切换回该身份</a>]</c:if>
				</span>
				&nbsp;欢迎你&nbsp;|&nbsp;
				<span class="badge badge-important" data-toggle="tooltip" data-placement="bottom" title="最近三天，您有${calendarCount}个提醒事项">有<font color="red">${calendarCount}</font>个提醒事项</span>
				&nbsp;|&nbsp;
				<span id="clock"></span>
				<img id="button-main" src="${ctx}/static/image/exit.png" width="17" height="17" style="border:0;cursor:pointer;cursor:hand;" align="top"/>
			</div>
			<div style="position: absolute;right:70px;text-align:left;top:45px;width:15%;">
				<span id="tipMessage" style="color:red;font-size:13px;"></span>
			</div>
			<div class="bs" style="position:absolute;right:10px;text-align:right;top:40px;width:12%;">
				<table style="width:auto;">
					<tr>
						<td><span style="color:#999">主题:</span></td>
						<td>
							<select id="cb-theme" style="width:120px;height:20px"></select>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<div id="mm" class="easyui-menu" style="width:120px;display:none;">
	<div id="user-menu" data-options="iconCls:'icon-edit'">修改用户信息</div>
	<div id="password-menu" data-options="iconCls:'icon-password'">修改密码</div>
	<div class="menu-sep"></div>
	<div id="exit-menu" data-options="iconCls:'icon-exit'">退出</div>
</div>