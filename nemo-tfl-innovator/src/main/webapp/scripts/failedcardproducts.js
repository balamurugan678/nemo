$(document).ready(function () {
	var date = new Date();
	var dateOfRefundId = pageName+"\\.dateOfRefund";
	$("#"+dateOfRefundId).datepicker({
		dateFormat: shortDatePattern, 
		changeYear: true,
		changeMonth: true,
		maxDate: new Date(date.getFullYear(), date.getMonth(), date.getDate())
	});
});