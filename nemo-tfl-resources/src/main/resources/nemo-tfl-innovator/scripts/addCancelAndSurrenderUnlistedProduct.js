var controllerLocation = "CancelAndSurrender.htm";

$(document).ready(function () {
	toggleDateField(pageName);
    initialisetravelcardsection();
    toggleDateFieldAndZonesIfTravelCardTypeChanges();

    if ($("#addTravelCardsSection span[id*='errors']").length) {
		$("#travelCardAccordian").accordion({ active: 0 });
	}

    setLineNoIfDeleteTravelCardClicked();
    updateRefundCalculationBasisIfRefundCalculationBasisChanges();
});


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

function toggleBackdateWarning(){
	
	if($(BACKDATEDREASON_ID).is(":checked")){
	
		var pickedDate = new Date(Date.parse($(START_DATE_ID).datepicker( 'getDate' )));
		var now = new Date();
		var sixWeeksAgo = new Date(new Date().setDate(now.getDate()- 42));
	
		if(sixWeeksAgo.getTime() > pickedDate.getTime()){
			$(BACKDATED_WARNING_ID).toggle(true);
		}else{
			$(BACKDATED_WARNING_ID).toggle(false);
		}
	}else{
		$(BACKDATED_WARNING_ID).toggle(false);
	}
}

function toggleDateField(pageName) {
    var otherTravelCard = 'Other TravelCard';
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

function initialisetravelcardsection() {
    $("#addTravelCardsSection").hide();
    $("#travelCardAccordian").accordion({
        collapsible: true,
        active: false,
        heightStyle: "content"
    });

}

function addTravelCard() {
    var LOADING_TRAVEL_CARDS_SECTION = "#loadingTravelCardsSection";
    var refundCartType = $("[name='cartType']").val();

    $.ajax({
        url: controllerLocation,
        type: 'POST',
        data: $("#travelCardRefundForm").serialize() + "&targetAction=addTravelCard&refundCartType="+refundCartType,
        beforeSend: function() {
            $(LOADING_TRAVEL_CARDS_SECTION).show();
        },
        success: function(html) {
            var $data = $('<div>').html(html);
            $data = $data.find("#travelCardRefundForm");
            $("#travelCardRefundForm").replaceWith($data);
            $(LOADING_TRAVEL_CARDS_SECTION).hide();
            $("#travelCardAccordian").accordion({ active: 0 });
        },
        error: function() {
            $(LOADING_TRAVEL_CARDS_SECTION).hide();
        }
    });
}

function deleteTravelCard(lineNo, pageUrl) {
    var LOADING_FOR_DELETE_TRAVEL_CARD_SECTION = "#loadingForDeleteTravelCardSection";
    var refundCartType = $("[name='cartType']").val();

    $.ajax({
        url: controllerLocation,
        type: 'POST',
        data: $("#travelCardRefundForm").serialize() + "&targetAction=deleteTravelCard&lineNo="+lineNo+"&refundCartType="+refundCartType,
        beforeSend: function() {
            $(LOADING_FOR_DELETE_TRAVEL_CARD_SECTION).show();
        },
        success: function(html) {
            var $data = $('<div>').html(html);
            $data = $data.find("#travelCardRefundForm");
            $("#travelCardRefundForm").replaceWith($data);
            $data = $data.find("#failedCardRefundCartForm");
            $("#failedCardRefundCartForm").replaceWith($data);
            $(LOADING_FOR_DELETE_TRAVEL_CARD_SECTION).hide();
        }
    });
    return false;
}

function updateRefundBasis(value, lineNo,  pageUrl) {
	
	lineNo = lineNo.replace('selectRefundCalculationBasis','');
	var refundCartType = $("[name='cartType']").val();
	
	$.ajax({
		url: controllerLocation,
		type: 'POST',
		data: $("#travelCardRefundForm").serialize() + "&targetAction=updateRefundBasisTravelCard&cartItemId="+lineNo+"&refundCalculationBasis="+ value,
	});
	return false;
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

function updateRefundCalculationBasisIfRefundCalculationBasisChanges() {
    $(".refundCalculationBasis").each(function(){
    	$(this).change(function(){
    		updateRefundBasis($(this).val(),this.id, pageUrl);
    		});
    });
}