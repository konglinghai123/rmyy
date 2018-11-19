function Poll() {
	var pollingUrl = ctx + '/polling';
	var longPolling = function(url, callback) {
		$.ajax({
			url : url,
			async : true,
			cache : false,
			global : false,
			timeout : 30 * 1000,
			dataType : "json",
			success : function(data, status, request) {
				callback(data);
				data = null;
				status = null;
				request = null;
				setTimeout(function() {
					longPolling(url, callback);
				}, 10);
			},
			error : function(xmlHR, textStatus, errorThrown) {
				xmlHR = null;
				textStatus = null;
				errorThrown = null;

				setTimeout(function() {
					longPolling(url, callback);
				}, 30 * 1000);
			}
		});
	};
	longPolling(
			pollingUrl,
			function(data) {
				if (data) {
					$('#sysNotice').empty();
					if (data.notice) {
						var html = '<span id="sysNotice" style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
								+ data.notice + '</span>';
						$(html).appendTo('#sysNotice');
					}
					
					$('#initAudit').empty();
					var initAudit = 0;
					if (data.initAudit) {
						initAudit = data.initAudit;
					}
					$('<span id="initAudit" style="font-size:14px">' + initAudit + '</span>').appendTo('#initAudit');
					
					$('#passedAudit').empty();
					var passedAudit = 0;
					if (data.passedAudit) {
						passedAudit = data.passedAudit;
					}
					$('<span id="passedAudit" style="font-size:14px">' + passedAudit + '</span>').appendTo('#passedAudit');
					
					$('#unPassedAudit').empty();
					var unPassedAudit = 0;
					if (data.unPassedAudit) {
						unPassedAudit = data.unPassedAudit;
					}
					$('<span id="unPassedAudit" style="font-size:14px">' + unPassedAudit + '</span>').appendTo('#unPassedAudit');
				}
			});
}
