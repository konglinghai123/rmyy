function Poll(){
	var pollingUrl = ctx + '/polling';
	var longPolling = function(url, callback) {
	    $.ajax({
	    	url: url,
	        async: true,
	        cache: false,
	        global: false,
	        timeout: 30 * 1000,
	        dataType : "json",
	        success: function (data, status, request) {
	            callback(data);
	            data = null;
	            status = null;
	            request = null;
	            setTimeout(
	                function () {
	                    longPolling(url, callback);
	                },
	                10 * 1000
	            );
	        },
	        error: function (xmlHR, textStatus, errorThrown) {
	            xmlHR = null;
	            textStatus = null;
	            errorThrown = null;
	
	            setTimeout(
	                function () {
	                    longPolling(url, callback);
	                },
	                30 * 1000
	            );
	        }
	    });
	};
	longPolling(pollingUrl, function(data) {
	    if(data) {
	        if (data.isFlush){
	         	$.post(ctx + "/fcf/test", {}, function(result) {
	        	    var myChart = new FusionCharts(ctx + "/static/fcf/swf/Column3D.swf?ChartNoDataText=无数据显示", "myChartId", "200", "300");
	        	    myChart.setJSONData(result);      
	        		myChart.render("Column2D1");
	        	}); 
	        }
	    }
	});
}	