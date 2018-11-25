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
					if (data.drugForm_notice) {
						$('<span style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + data.drugForm_notice + '</span>').appendTo('#sysNotice');
					}
					
					var nodeclare = 0;
					if (data.drugForm_nodeclare) {
						nodeclare = data.drugForm_nodeclare;
					}
					
					var init = 0;
					if (data.drugForm_init) {
						init = data.drugForm_init;
					}
					
					var passed = 0;
					if (data.drugForm_passed) {
						passed = data.drugForm_passed;
					}
					
					var unPassed = 0;
					if (data.drugForm_unPassed) {
						unPassed = data.drugForm_unPassed;
					}
					$('#drugForm_nodeclare').empty();
					$('<span style="font-size:14px">1、未提交初审数：' + nodeclare + '&nbsp;条</span>').appendTo('#drugForm_nodeclare');
					$('#drugForm_init').empty();
					$('<span style="font-size:14px">2、已提交初审数：' + init + '&nbsp;条</span>').appendTo('#drugForm_init');
					$('#drugForm_passed').empty();
					$('<span style="font-size:14px">3、初审核已通过数：' + passed + '&nbsp;条</span>').appendTo('#drugForm_passed');
					$('#drugForm_unPassed').empty();
					$('<span style="font-size:14px">4、初审核未通过数：' + unPassed + '&nbsp;条</span>').appendTo('#drugForm_unPassed');
				}
			});
};
