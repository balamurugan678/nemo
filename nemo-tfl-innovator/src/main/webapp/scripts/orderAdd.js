$(document).ready(function () {
	showAndEnableTicketTypeSection($('#ticketType').val());
     $("#ticketType").change(function(){
         showAndEnableTicketTypeSection($(this).val());
     });
     $("#"+pageName+"\\.endDate").datepicker();
});

function showAndEnableTicketTypeSection(ticketType) {
	hideAndDisableSections();
    if (ticketType == 'payAsYouGo' ) { showAndEnableSection("#payAsYouGoSection"); }
    if (ticketType == 'annualBusTramPass' ) { showAndEnableSection("#busPassSection"); }
    if (ticketType == 'travelcard' ) { showAndEnableSection("#travelCardSection"); }
}

function hideAndDisableSections(){
	hideAndDisableSection("#payAsYouGoSection");
	hideAndDisableSection("#busPassSection");
	hideAndDisableSection("#travelCardSection");
}

function hideAndDisableSection(section) {
	$(section).hide();
    $(section).find('select', 'input').attr('disabled', 'true');
}

function showAndEnableSection(section) {
	$(section).show();
    $(section).find('select', 'input').removeAttr('disabled');
}