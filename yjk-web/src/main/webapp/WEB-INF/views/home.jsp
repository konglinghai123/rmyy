<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="网上药品申报评审信息管理系统" index="true"/>
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
<script type="text/javascript" src="${ctx}/static/fcf/js/FusionCharts.js" charset="utf-8"></script>
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
			onChange:onChangeTheme,
			onLoadSuccess:function(){
				var theme = $.cookie('theme');
				if (theme == null) theme = 'default';
				$(this).combobox('setValue', theme);
			}
		});
	    
	    $('#systemParameterId').combobox({
	    	panelHeight:'auto',
	    	editable:false,
	    	onSelect:function(record){
	    		$.ajax({
					 async:false,
					 type:'POST',
					 url:'${ctx}/' + record.value + '/drugFormStatistic',
					 dataType : 'json',
					 success:function(data) {
						 $('#drugForm_nodeclare').empty();
						 $('#drugForm_nodeclare').html(data.drugForm_nodeclare);
						 $('#drugForm_init').empty();
						 $('#drugForm_init').html(data.drugForm_init);
						 $('#drugForm_passed').empty();
						 $('#drugForm_passed').html(data.drugForm_passed);
						 $('#drugForm_unPassed').empty();
						 $('#drugForm_unPassed').html(data.drugForm_unPassed);
						 drugFormCountChart(record.value);
						 $('#ttSystemParameter').datagrid({
							 url:'${ctx}/' + record.value + '/drugFormCountReport'
						 });
						 systemParameterTable();
					 }
				});
	    	},onLoadSuccess:function(){
	    		if ('${systemParameterId}' != ''){
	    			$(this).combobox('setValue','${systemParameterId}');
	    		}
	    	}
		});
	    
	    $('#reviewMainId').combobox({
	    	panelHeight:'auto',
	    	editable:false,
	    	onSelect:function(record){
	    		$.ajax({
					 async:false,
					 type:'POST',
					 url:'${ctx}/' + record.value + '/reviewStatistic',
					 dataType : 'json',
					 success:function(data) {
						 if (data.reviewStatistic){
							 $('#reivewStatistic .t-list').empty();
						     var noticesHtml = '<div class="t-list"><table width="100%">';
						     var pro = [];
						     $.each(data.reviewStatistic, function(idx, item){
							 	pro.push('<tr><td style="font-size:14px;">' + item);
						     });
						     var html = pro.join("");
						     noticesHtml += html + '</table></div>';
						     $(noticesHtml).appendTo('#reivewStatistic');
						 }
						 reviewCountChart(record.value);
					 }
				});
	    	},onLoadSuccess:function(){
	    		if ('${reviewMainId}' != ''){
	    			$(this).combobox('setValue','${reviewMainId}');
	    		}
	    	}
		});
	    
	    $('#ttSystemParameter').datagrid({
	    	height:300,
	    	nowrap:true,
	    	pagination:true,
	    	rownumbers:true,
	    	striped:true,
	    	pageSize:10
	    })
	    
		//systemParameterTable();
	    
	    var poll = new Poll();
	});
	
	function drugFormCountChart(systemParameterId){
		var myChart = new FusionCharts('${ctx}/static/fcf/swf/Pie3D.swf?ChartNoDataText=无数据显示', new Date().getTime(), '400', '170');
   		myChart.setDataURL('${ctx}/' + systemParameterId + '/drugFormCountChart?_=' + new Date().getTime());
   		myChart.render('drugFormCountDiv');
	}
	
	function reviewCountChart(reviewMainId){
		var myChart = new FusionCharts('${ctx}/static/fcf/swf/MSColumn3D.swf?ChartNoDataText=无数据显示', new Date().getTime(), '400', '270');
   		myChart.setDataURL('${ctx}/' + reviewMainId + '/reviewCountChart?_=' + new Date().getTime());
   		myChart.render('reviewCountDiv');
	}
	
	function onChangeTheme(theme){
    	$.cookie('theme', theme);
		var link = $('head').find('link:first');
		link.attr('href', '${ctx}/static/easyui/themes/'+theme+'/easyui.css');
		try{
			var childLink = $('iframe').contents().find('link:first');
			childLink.attr('href', '${ctx}/static/easyui/themes/'+theme+'/easyui.css');
		}catch(err){
		}
	}
	
	function systemParameterTable(){
		<c:if test="${user.admin}">
		var pager = $('#ttSystemParameter').datagrid().datagrid('getPager');
		pager.pagination({
			buttons:[{
				iconCls:'icon-print',
				handler:function(){
					$.ewcms.openTopWindow({src:'${ctx}/buildSystemParameter?systemParameterId=' + $('#systemParameterId').combobox('getValue'),title:'打印各部门填报情况表',isRefresh:false,maximizable:true});
				}
			}]
		});
		</c:if>
	}
</script>