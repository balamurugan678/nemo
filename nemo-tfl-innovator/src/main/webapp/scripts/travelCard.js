$(document).ready(function () {
    toggleDateField(pageName, shortDatePattern);
    addDatePickerForEndDate(pageName, shortDatePattern);
    toggleZones();
    toggleDateFieldAndZonesIfTravelCardTypeChanges();
    $("#travelCardContinue-button").click(function(){addTravelCardToOrder();});
});

function addTravelCardToOrder() {
    $.post(sAddress + '/TravelCard/addTravelCardToCart.htm', $("#travelCard").serialize(), function(response) {
        console.info(response);
        if (response != "" && response != "Complete") {
            var obj = $.parseJSON(response);
            var message = "";
            $.each(obj, function(i, current) {
                message += getMessage(current.codes[1]);
            });
            alert(message);
        } else {
            updateCart();
        }

    });
}

function toggleDateFieldAndZonesIfTravelCardTypeChanges() {
	$("#travelCardType").change(function() {
	    toggleDateField(pageName);
	    toggleZones();
	});
}

function toggleDateField(pageName, shortDatePattern) {
    var otherTravelCard = 'Unknown';
    var endDateSection = '#endDateSection';

    if ($("#travelCardType").val() == otherTravelCard) {    	
    	$(endDateSection).show();
    } else {
    	$(endDateSection).hide();
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

function addDatePickerForEndDate(pageName, shortDatePattern) {
    var endDateId = pageName + "\\.cartItemCmd\\.endDate";
    $("#" + endDateId).datepicker({
    	minDate: 0,
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true
    });
}
