$(document).ready(function () {
    addDatePickerForStartDate(pageName, shortDatePattern);
    addDatePickerForEndDate(pageName, shortDatePattern);
    toggleDateField(pageName);
    toggleZones();
    initialiseTravelCardSection();
    toggleDateFieldAndZonesIfTravelCardTypeChanges();
    activateAccordianIfErrors();
    setLineNoIfDeleteTravelCardClicked();
});

function addDatePickerForStartDate(pageName, shortDatePattern) {
    var startDateId = pageName + "\\." + "cartItemCmd\\.startDate";
    $("#" + startDateId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
        onSelect : function(date) {
        	var endDateId = pageName + "\\.cartItemCmd\\.endDate";
        	setDatepickerMinimumDate(endDateId, $("#" + startDateId));
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
    setDatepickerMinimumDate(endDateId, $("#" + startDateId));
}

function setDatepickerMinimumDate(fieldId, dateField) {
	var input = dateField.val().split("/");
	var minDate = new Date(input[2], input[1]-1, input[0]);
	$("#" + fieldId).datepicker("option", "minDate", minDate);
}

function toggleDateField(pageName) {
    var otherTravelCard = 'Other TravelCard';
    var endDateId = pageName + "\\.cartItemCmd\\.endDate";
    var endDateField = 'label[for=' + endDateId + '], input#' + endDateId + '';

    if ($("#travelCardType").val() == otherTravelCard) {
        $(endDateField).show();
    } else {
        $(endDateField).hide();
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
}

function activateAccordianIfErrors() {
	if ($("#addTravelCardsSection span[id*='errors']").length) {
		$("#travelCardAccordian").accordion({ active: 0 });
	}
}

function setLineNoIfDeleteTravelCardClicked() {
	$(".deleteTravelCard").each(function(){
		$(this).click(function(){
			setLineNoFromDeleteTravelCardIdValue($(this).attr('id'));
			return true;
		});
	});
}

function setLineNoFromDeleteTravelCardIdValue(idValue) {
	idValue = idValue.replace("deleteTravelCard", "");
	idValue = idValue.replace("-submit", "");
	$("#lineNo").val(idValue);
}
