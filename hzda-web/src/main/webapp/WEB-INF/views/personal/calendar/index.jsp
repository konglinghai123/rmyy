<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="个人日历"/>
	<%@include file="/WEB-INF/views/jspf/import-calendar-css.jspf"%>
	<style>
    body {
        margin-top: 40px;
        text-align: center;
        font-size: 14px;
        font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
    }
    .fc-button-add {
        margin-right: 10px!important;
    }
    #loading {
        position: absolute;
        top: 5px;
        right: 5px;
    }
    .ui-dialog {
        overflow: visible!important;
    }
    .ui-dialog-content {
        overflow: hidden!important;
        overflow: visible!important;
    }
    #calendar {
        width: 800px;
        margin: 0 auto;
    }
	</style>
	<div id='calendar'></div>
<ewcms:editWindow/>
<ewcms:footer/>
<%@include file="/WEB-INF/views/jspf/import-calendar-js.jspf"%>
<script type="text/javascript">
	var calendar;
	$(function(){
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
        calendar = $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            events: "${ctx}/personal/calendar/load",
            eventDrop: function(event, delta) {
                moveCalendar(event);
            },
            eventClick: function(event, delta) {
                viewCalendar(event);
            },
            loading: function(bool) {
                if (bool) $('#loading').show();
                else $('#loading').hide();
            },
            editable: true,
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                openNewCalendarForm(start, end);
                calendar.fullCalendar('unselect');
            }
        });
        
        $('span.fc-button-prev').before('<span class="fc-button fc-button-add fc-state-default fc-corner-left fc-corner-right">新增</span>');

        $(".fc-button-add").click(function() {
            openNewCalendarForm();
        });
        
        function openNewCalendarForm(start, end) {
            var url = "${ctx}/personal/calendar/save";
            if(start) {
                start = $.fullCalendar.formatDate(start, "yyyy-MM-dd HH:mm:ss");
                end = $.fullCalendar.formatDate(end, "yyyy-MM-dd HH:mm:ss");
                url = url + "?start=" + start + "&end=" + end;
            }
            $.ewcms.add({src:url,title:'新增提醒事项',width:530,height:480});
        }
        function moveCalendar(event) {
            var url = "${ctx}/personal/calendar/move";
            var id = event.id;
            var start = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd HH:mm:ss");
            var end = $.fullCalendar.formatDate(event.end, "yyyy-MM-dd HH:mm:ss");
            url = url + "?id=" + id;
            url = url + "&start=" + start + "&end=" + end;
            $.post(url, function(data) {
                calendar.fullCalendar("refetchEvents");
            });
        }
        function viewCalendar(event) {
        	calendar.fullCalendar('unselect');
            var url = "${ctx}/personal/calendar/view/" + event.id;
            $.ewcms.openWindow({src:url,title:'查看提醒事项',width:500,height:400});
        }
	});
</script>
