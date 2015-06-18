var SLASH_DOT = "\\.";
var HASH = "#";
var CONTAINER = "cartItemCmd";
var TRADED_TICKET = "tradedTicket";
var PREVIOUSLY_EXCHANGED = "previouslyExchanged";
var PREVIOUSLY_EXCHANGED_ID = HASH + pageName + SLASH_DOT + CONTAINER + SLASH_DOT+ PREVIOUSLY_EXCHANGED;
var EXCHANGE_DATE_CONTAINER = "exchangeDateContainer";
var EXCHANGE_DATE_CONTAINER_ID = HASH +  EXCHANGE_DATE_CONTAINER;
var EXCHANGE_DATE = TRADED_TICKET + SLASH_DOT + "exchangedDate";
var EXCHANGE_DATE_ID = HASH + pageName  + SLASH_DOT + CONTAINER + SLASH_DOT + EXCHANGE_DATE;
var TRADED_TICKET_START_DATE = TRADED_TICKET + SLASH_DOT + "startDate";
var TRADED_TICKET_START_DATE_ID = HASH + pageName + SLASH_DOT + CONTAINER + SLASH_DOT + TRADED_TICKET_START_DATE;
var TRADED_TICKET_END_DATE = TRADED_TICKET + SLASH_DOT + "endDate";
var TRADED_TICKET_END_DATE_ID = HASH + pageName + SLASH_DOT + CONTAINER + SLASH_DOT + TRADED_TICKET_END_DATE;

$(document).ready(function () {
    addDatePickerForStartDate(pageName, shortDatePattern);
    addDatePickerForEndDate(pageName, shortDatePattern);
    initialiseTravelCardSection();
    toggleDateField(pageName);
    toggleZones();
    toggleDateFieldAndZonesIfTravelCardTypeChanges();
    activateTravelCardAccordianIfErrors();
    
    $(EXCHANGE_DATE_CONTAINER_ID).toggle($(PREVIOUSLY_EXCHANGED_ID).is(":checked"));
	
	$(TRADED_TICKET_END_DATE_ID).datepicker(getUnlistedProductDatePickerSettings());
	$(TRADED_TICKET_START_DATE_ID).datepicker(getUnlistedProductDatePickerSettingsWithFourYearsMinAndMaxDateRange());
	$(EXCHANGE_DATE_ID).datepicker(getUnlistedProductDatePickerSettingsWithFourYearsMinAndMaxDateRange());
	
	$(PREVIOUSLY_EXCHANGED_ID).change(function() {
		$(EXCHANGE_DATE_CONTAINER_ID).toggle($(PREVIOUSLY_EXCHANGED_ID).is(":checked"));
	});
   
});

function getUnlistedProductDatePickerSettingsWithFourYearsMinAndMaxDateRange() {
	var date = new Date();
    var settings = returnDatePickerSettings();
    settings.changeYear = true;
    settings.changeMonth = true;
    settings.minDate = new Date(date.getFullYear(), date.getMonth()-48, date.getDate());
    settings.maxDate = new Date(date.getFullYear(), date.getMonth()+48, date.getDate()-1);
    return settings;
}

function getUnlistedProductDatePickerSettings() {
	var date = new Date();
    var settings = returnDatePickerSettings();
    settings.changeYear = true;
    settings.changeMonth = true;
    settings.minDate = new Date(date.getFullYear(), date.getMonth()+1, date.getDate());
    settings.maxDate = new Date(date.getFullYear(), date.getMonth()+13, date.getDate()-1);
    return settings;
}
function addDatePickerForStartDate(pageName, shortDatePattern) {
    var startDateId = pageName + "\\." + "cartItemCmd\\.startDate";
    $("#" + startDateId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
        onSelect : function(date) {
        	var endDateId = pageName + "\\.cartItemCmd\\.endDate";
        	setDatepickerMinAndMaxDates(endDateId, $("#" + startDateId));
        }
    });
}

function addDatePickerForEndDate(pageName, shortDatePattern) {
    var endDateId = pageName + "\\.cartItemCmd\\.endDate";
    var startDateId = pageName + "\\." + "cartItemCmd\\.startDate";
    
    $("#" + endDateId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
    });
    setDatepickerMinAndMaxDates(endDateId, $("#" + startDateId));
}

function setDatepickerMinAndMaxDates(fieldId, dateField) {
	var input = dateField.val().split("/");
	var days = parseInt(input[0],10);
	var minDate = new Date(input[2], input[1]-1,days+1 );
	minDate.setMonth(minDate.getMonth()+1);
	$("#" + fieldId).datepicker("option", "minDate", minDate);
	
	var maxDate = new Date(input[2], input[1]-1,days+12);
	maxDate.setMonth(maxDate.getMonth()+10);
	$("#" + fieldId).datepicker("option", "maxDate", maxDate);
}


function toggleDateField(pageName) {
    var otherTravelCard = 'Unknown Travelcard';
    var endDateId = pageName + "\\.cartItemCmd\\.endDate";
    var endDateField = 'label[for=' + endDateId + '], input#' + endDateId + '';
    var tradedTicketEndDateId = pageName + "\\.cartItemCmd\\.tradedTicket\\.endDate";
    var tradedTicketEndDateField = 'label[for=' + tradedTicketEndDateId + '], input#' + tradedTicketEndDateId + '';
    
    if ($("#travelCardType").val() == otherTravelCard) {
        $(endDateField).show();
    } else {
        $(endDateField).hide();
    }
    if ($("#tradedTicketTravelCardType").val() == otherTravelCard) {
    	$(tradedTicketEndDateField).show();
    } else {
    	$(tradedTicketEndDateField).hide();
    }
}

function toggleZones() {
    var busPass = 'Bus Pass';
    var fromZoneIdField = 'label[for=fromZone], #fromZone';
    var endZoneIdField = 'label[for=finishZone], #finishZone';

    var travelCardValue = $("#travelCardType").val();

    if (travelCardValue && travelCardValue.endsWith(busPass)) {
        $(fromZoneIdField).hide();
        $(endZoneIdField).hide();
    } else{
        $(fromZoneIdField).show();
        $(endZoneIdField).show();
    }
}

function initialiseTravelCardSection() {
    $("#addTravelCardsSection").hide();
    $("#travelCardAccordian").accordion({
        collapsible: true,
        active: false,
        heightStyle: "content"
    });

}

function toggleDateFieldAndZonesIfTravelCardTypeChanges() {
	$("#travelCardType").change(function() {
	    toggleDateField(pageName);
	    toggleZones();
	});
	$("#tradedTicketTravelCardType").change(function() {
	    toggleDateField(pageName);
	    toggleTradedTicketZones();
	});
}

function activateTravelCardAccordianIfErrors() {
	if ($("#addTravelCardsSection span[id*='errors']").length) {
		$("#travelCardAccordian").accordion({ active: 0 });
	}
}


function toggleTradedTicketZones() {
    var busPass = 'Bus Pass';
    var fromZoneIdField = 'label[for=cartItemCmd\\.tradedTicketStartZone], #cartItemCmd\\.tradedTicketStartZone';
    var endZoneIdField = 'label[for=cartItemCmd\\.tradedTicketEndZone], #cartItemCmd\\.tradedTicketEndZone';

    var travelCardValue = $("#tradedTicketTravelCardType").val();

    if (travelCardValue && travelCardValue.endsWith(busPass)) {
        $(fromZoneIdField).hide();
        $(endZoneIdField).hide();
    } else{
        $(fromZoneIdField).show();
        $(endZoneIdField).show();
    }
}