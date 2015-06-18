var SLASH_DOT = "\\.";
var TICKET_UNUSED = 'ticketUnusedContainer';
var HASH = "#";
var REFUND_DATE = "refundDate";
var TRADED_TICKET = "tradedTicket";
var END_DATE = "endDate";
var EXCHANGE_DATE = TRADED_TICKET + SLASH_DOT + "exchangedDate";
var PREVIOUSLY_EXCHANGED = "previouslyExchanged";
var EXCHANGE_DATE_CONTAINER = "exchangeDateContainer";
var START_DATE = "startDate";
var TRADED_TICKET_START_DATE = TRADED_TICKET + SLASH_DOT + "startDate";
var TRADED_TICKET_END_DATE = TRADED_TICKET + SLASH_DOT + "endDate";
var TICKET_START_ZONE = "refundTicketStartZone";
var TICKET_END_ZONE = "refundTicketEndZone";
var TRADED_TICKET_START_ZONE = "tradedTicketStartZone";
var TRADED_TICKET_END_ZONE = "tradedTicketEndZone";
var PURCHASED_PRODUCT = "productId";
var PURCHASED_PRODUCT_DESCRIPTION =  HASH + "productDescription";
var TRADED_PRODUCT = "tradedProductId";
var TRADED_PRODUCT_DESCRIPTION =  HASH + "tradedProductDescription";
var PURCHASED_PRODUCT_HIDDEN_DESCRIPTION = "purchasedProductDescription";
var TRADED_PRODUCT_HIDDEN_DESCRIPTION = "tradedProductDescription";
var CONTAINER = "cartItemCmd";
var BACKDATEDREASONCONTAINER = "backdatedReasonContainer";
var BACKDATED = "backdated";
var BACKDATEDWARNING = "backdatedWarning";

	
var REFUND_DATE_ID = HASH + pageName + SLASH_DOT + REFUND_DATE;
var START_DATE_ID = HASH + pageName + SLASH_DOT  + CONTAINER + SLASH_DOT + START_DATE;
var END_DATE_ID = HASH + pageName + SLASH_DOT  + CONTAINER + SLASH_DOT + END_DATE;
var TICKET_UNUSED_ID = HASH +  TICKET_UNUSED;
var EXCHANGE_DATE_ID = HASH + pageName  + SLASH_DOT + CONTAINER + SLASH_DOT + EXCHANGE_DATE;
var EXCHANGE_DATE_CONTAINER_ID = HASH +  EXCHANGE_DATE_CONTAINER;
var PREVIOUSLY_EXCHANGED_ID = HASH + pageName + SLASH_DOT + CONTAINER + SLASH_DOT+ PREVIOUSLY_EXCHANGED;
var TRADED_TICKET_START_DATE_ID = HASH + pageName + SLASH_DOT + CONTAINER + SLASH_DOT + TRADED_TICKET_START_DATE;
var TRADED_TICKET_END_DATE_ID = HASH + pageName + SLASH_DOT + CONTAINER + SLASH_DOT + TRADED_TICKET_END_DATE;
var TICKET_START_ZONE_ID = HASH + TICKET_START_ZONE;
var TICKET_END_ZONE_ID = HASH  + TICKET_END_ZONE;
var TRADED_TICKET_START_ZONE_ID = HASH + TRADED_TICKET_START_ZONE;
var TRADED_TICKET_END_ZONE_ID = HASH  + TRADED_TICKET_END_ZONE;
var PURCHASED_PRODUCT_ID = HASH + pageName + SLASH_DOT +  PURCHASED_PRODUCT;
var PURCHASED_PRODUCT_HIDDEN_DESCRIPTION_ID = HASH + pageName + SLASH_DOT +  PURCHASED_PRODUCT_HIDDEN_DESCRIPTION;
var TRADED_PRODUCT_ID = HASH + pageName + SLASH_DOT +  TRADED_PRODUCT;
var TRADED_PRODUCT_HIDDEN_DESCRIPTION_ID = HASH + pageName + SLASH_DOT +  TRADED_PRODUCT_HIDDEN_DESCRIPTION;
var BACKDATEDREASON_ID = HASH + pageName  + SLASH_DOT + CONTAINER + SLASH_DOT + BACKDATED;
var BACKDATEDREASON_CONTAINER_ID = HASH + BACKDATEDREASONCONTAINER;
var BACKDATED_WARNING_ID = HASH + BACKDATEDWARNING;

		
function getCancelAndSurrenderDatePickerSettings() {
	var date = new Date();
    var settings = returnDatePickerSettings();
    settings.changeYear = true;
    settings.changeMonth = true;
    settings.minDate = new Date(date.getFullYear(), date.getMonth()+1, date.getDate());
    settings.maxDate = new Date(date.getFullYear(), date.getMonth()+13, date.getDate()-1);
    return settings;
}

function getCancelAndSurrenderDatePickerSettingsWithFourYearsMinAndMaxDateRange() {
	var date = new Date();
    var settings = returnDatePickerSettings();
    settings.changeYear = true;
    settings.changeMonth = true;
    settings.minDate = new Date(date.getFullYear(), date.getMonth()-48, date.getDate());
    settings.maxDate = new Date(date.getFullYear(), date.getMonth()+48, date.getDate()-1);
    return settings;
}

$(document).ready(function () {

	$(EXCHANGE_DATE_CONTAINER_ID).toggle($(PREVIOUSLY_EXCHANGED_ID).is(":checked"));
	$(BACKDATEDREASON_CONTAINER_ID).toggle($(BACKDATEDREASON_ID).is(":checked"));
	$(BACKDATED_WARNING_ID).toggle($(BACKDATEDREASON_ID).is(":checked"));
	
	$(END_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettings());
	$(TRADED_TICKET_END_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettings());
	$(TRADED_TICKET_START_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettingsWithFourYearsMinAndMaxDateRange());
	$(EXCHANGE_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettingsWithFourYearsMinAndMaxDateRange());
	
	$(PREVIOUSLY_EXCHANGED_ID).change(function() {
		$(EXCHANGE_DATE_CONTAINER_ID).toggle($(PREVIOUSLY_EXCHANGED_ID).is(":checked"));
	});
	
	$(BACKDATEDREASON_ID).change(function() {
		$(BACKDATEDREASON_CONTAINER_ID).toggle($(BACKDATEDREASON_ID).is(":checked"));
		toggleBackdateWarning();
	});
	
	$(TRADED_TICKET_START_DATE_ID).change(function() {
		toggleBackdateWarning();
	});
	
	$(START_DATE_ID).datepicker({
		dateFormat: shortDatePattern, 
		changeYear: true,
		changeMonth: true,
		onSelect: function() {
		    $(this).change();
		    toggleBackdateWarning();
		  },
		onClose: function() {
			$(this).change();
			toggleBackdateWarning();
			}
	
	});
	

		
});

