$(document).ready(function () {
	var date = new Date();
	var endDateId = pageName+"\\.endDate";
	
	if ($("#travelCardType").val() == 'Other') {
		$('label[for='+endDateId+'], input#'+endDateId+'').show();
	} else {
		$('label[for='+endDateId+'], input#'+endDateId+'').hide();
	}
	
	$("#"+endDateId).datepicker({
		dateFormat: shortDatePattern, 
		changeYear: true,
		changeMonth: true,
		minDate: new Date(date.getFullYear(), date.getMonth()+1, date.getDate()),
		maxDate: new Date(date.getFullYear(), date.getMonth()+13, date.getDate()-1)
	});
	
	$("#travelCardType").change(function() {
		if ($("#travelCardType").val() == 'Other') {
			$('label[for='+endDateId+'], input#'+endDateId+'').show();
		} else {
			$('label[for='+endDateId+'], input#'+endDateId+'').hide();
		}
	});
});