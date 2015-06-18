$(document).ready(function () {
	$("#selectTargetCard").change(function() {
		$("#loading-icon").toggle();
		$("#messageArea").html("Loading");
		$.post(contextPath + '/' + pageName + '/getPreferredStation.htm', { cardNumber: $("#selectTargetCard option:selected").val() }, function (response) {
	        $("#selectStation").val(response);
	        $("#loading-icon").toggle();
	        $("#messageArea").html("");
	    });
	});
});

