<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="骨质疏松患者档案系统" index="true"/>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:false" class="head">
			<%@ include file="home/head.jsp" %>
		</div>
		<div data-options="region:'center'" style="overflow:auto;">
			<div id="index" class="easyui-layout" data-options="fit:true,border:false">  
				<%@ include file="home/body.jsp" %>
			</div>
			<div id="center" class="easyui-layout" data-options="fit:true">  
				<div id="west" data-options="region:'west',split:false" title="子菜单项" style="width:163px;">
					<%@ include file="home/menu.jsp" %>
				</div>
				<div data-options="region:'center',border:false" style="overflow:auto;">
					<c:forEach items="${menus}" var="m">
						<div class="easyui-tabs" id="tab-${m.id}" data-options="fit:true,border:false" style="display:none;"></div>
					</c:forEach>
        		</div>
        		<%@ include file="home/tabsMenu.jsp" %>
			</div>
	    </div>
    </div>   
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/js/clock.js"></script>
<script type="text/javascript" src="${ctx}/static/js/polling.js"></script>
<script type="text/javascript" src="${ctx}/static/fcf/js/FusionCharts.js"></script>
<script type="text/javascript">
	$(function(){
		var themes = [
		  	{value:'default',text:'Default',group:'Base'},
		  	{value:'gray',text:'Gray',group:'Base'},
		  	{value:'metro',text:'Metro',group:'Base'},
		  	{value:'material',text:'Material',group:'Base'},
		  	{value:'bootstrap',text:'Bootstrap',group:'Base'},
		  	{value:'black',text:'Black',group:'Base'},
		  	{value:'metro-blue',text:'Metro Blue',group:'Metro'},
		  	{value:'metro-gray',text:'Metro Gray',group:'Metro'},
		  	{value:'metro-green',text:'Metro Green',group:'Metro'},
		  	{value:'metro-orange',text:'Metro Orange',group:'Metro'},
		  	{value:'metro-red',text:'Metro Red',group:'Metro'},
		  	{value:'ui-cupertino',text:'Cupertino',group:'UI'},
		  	{value:'ui-dark-hive',text:'Dark Hive',group:'UI'},
		  	{value:'ui-pepper-grinder',text:'Pepper Grinder',group:'UI'},
		  	{value:'ui-sunny',text:'Sunny',group:'UI'}
		];
		
		var clock = new Clock();
		clock.display(document.getElementById('clock'));
		
	    $('#center').hide();
	    
	    $('#button-main').bind('click',function(){
	        $('#mm').menu('show',{
	            left:$(this).offset().left - 80,
	            top:$(this).offset().top + 18
	        });
	    }).hover(function(){
	        $(this).addClass('l-btn l-btn-plain m-btn-plain-active');
	    },function(){
	        $(this).removeClass('l-btn l-btn-plain m-btn-plain-active');
	    });
	    
	    $('#user-menu').bind('click',function(){
	    	$.ewcms.openWindow({
	    		windowId:'#edit-window',
	    		iframeId:'#editifr',
	    		src:'${ctx}/security/user/loginUser/updateInfo',
	    		width:550,
	    		height:500,
	    		title:'修改用户信息'
	    	});
	    });
	    $('#password-menu').bind('click',function(){
	    	$.ewcms.openWindow({
	    		windowId:'#edit-window',
	    		iframeId:'#editifr',
	    		src:'${ctx}/security/user/loginUser/changePassword',
	    		width:550,
	    		height:240,
	    		title:'修改密码'
	    	});
	    });
	    
	    $('#exit-menu').bind('click',function(){
	        window.location.href = '${ctx}/logout';
	    });
	        
	    $('li[id^="menu-"]').bind('click', function(){
			var id = $(this).get(0).id.substring(5);
			
			$('li[id^="menu-"]').removeClass('tabs-selected');
			$('div[id^="menusub-"]').hide();
			$('div[id^="tab-"]').hide();
			
			$('#menu-' + id).addClass('tabs-selected');
			$('#menusub-' + id).show();
			$('#tab-' + id).show();
			
			if (id == 'index'){
				$('#index').show();
				$('#center').hide();
			}else{
				$('#index').hide();
				$('#center').show();
				$('#west').panel('setTitle', $(this).text());
				$('#tab-' + id).tabs('resize');
			}
			$('.easyui-accordion').accordion('resize');
		});
	    
	    $('#cb-theme').combobox({
			groupField:'group',
			data: themes,
			editable:false,
			panelHeight:'auto',
			onChange:onChangeTheme,
			onLoadSuccess:function(){
				var theme = $.cookie('hzda-theme');
				if (theme == null) theme = 'default';
				$(this).combobox('setValue', theme);
			}
		});
	    
	    $('#ttFollowupTime').datagrid({
	    	url:'${ctx}/followupTime/query',
	    	height:300,
	    	nowrap:true,
	    	pagination:true,
	    	rownumbers:true,
	    	striped:true,
	    	pageSize:10,
	    	onLoadSuccess:function(row){
	    		$('.closeCls').linkbutton({plain:true,iconCls:'icon-exit'});
	    	}
	    })
	    
	    var poll = new Poll();
	});
	
	function onChangeTheme(theme){
    	$.cookie('hzda-theme', theme);
		var link = $('head').find('link:first');
		link.attr('href', '${ctx}/static/easyui/themes/'+theme+'/easyui.css');
		try{
			var childLink = $('iframe').contents().find('link:first');
			childLink.attr('href', '${ctx}/static/easyui/themes/'+theme+'/easyui.css');
		}catch(err){
		}
	}
	
	function formatOperation(val, row) {
		return '<a class="closeCls" onclick="closeFollowup(' + row.id + ')" href="javascript:void(0);" style="height:24px;" title="关闭提醒"/>';
	}
	
	function closeFollowup(followupTimeId){
		$.messager.confirm('提示', '确定要关闭此人员的提醒吗？', function(r) {
			if (r) {
				$.ewcms.addLoading();
				$.post('${ctx}/followupTime/close',{'followupTimeId':followupTimeId}, function(result) {
					if (result.success) {
						$('#ttFollowupTime').datagrid('reload');
					}
					$.messager.alert('提示', result.message, 'info');
					$.ewcms.removeLoading();
				});
			}
		});
	}
</script>