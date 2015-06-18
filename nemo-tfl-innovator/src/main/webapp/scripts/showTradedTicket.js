var previouslyExchangedCheckBoxCssClass = "previouslyExchangedClass";
var tradedTicketTravelCardType = "tradedTicketTravelCardTypeClass";
var PERIOD = ".";
var SLASH_DOT = "\\.";
var SLASH_OPEN_BRACE = "\\[";
var SLASH_CLOSE_BRACE = "\\]";
var HASH = "#";
var EXCHANGE_DATE = TRADED_TICKET + SLASH_DOT + "exchangedDate";
var TRADED_TICKET_START_DATE = TRADED_TICKET + SLASH_DOT + "startDate";
var TRADED_TICKET_END_DATE = TRADED_TICKET + SLASH_DOT + "endDate";
var TRADED_TICKET = "tradedTicket";
var START_ZONE = "startZone";
var END_ZONE = "endZone";
var END_DATE = "endDate";
var LABEL_FOR = 'label[for=';
var INPUT_HASH = 'input#';
var SELECT_HASH = 'select#';
var CARTITEM_LIST_CONTAINER = "cartItemList";
var ID_ATTRIBUTE = 'id';
var CARTITEMLIST_TILL_OPENBRACE = SLASH_DOT+CARTITEM_LIST_CONTAINER+SLASH_OPEN_BRACE;
var CARTITEMLIST_AFTER_CLOSEBRACE = SLASH_CLOSE_BRACE+SLASH_DOT+TRADED_TICKET+SLASH_DOT;
var CARTITEMLIST_TILL_OPENBRACE_WITHOUT_SLASH_DOT = CARTITEM_LIST_CONTAINER+SLASH_OPEN_BRACE;
	
$(document).ready(function () {
	
	if ($("#addTradedTravelCardSection span[id*='errors']").length) {
		$("#travelCardAccordian").accordion({ active: 0 });
	}
	
	setLineNoIfAddTradedTicketCardClicked();
	
	$(PERIOD+previouslyExchangedCheckBoxCssClass).each(function(){
		var id = $(this).attr(ID_ATTRIBUTE);
		var input = id.split(PERIOD);
		var secondInput = input[1];
		
		var index = getIndexFromId(id);
		var closeBraceReplacedSecondInput = replaceStringWithEscapeBackwardSlash(secondInput);
		$(this).change(function(){
			
			$(EXCHANGE_DATE_CONTAINER_ID+index).toggle();
		});
		
		var EXCHANGE_DATE_ID = HASH + pageName  + SLASH_DOT + closeBraceReplacedSecondInput + SLASH_DOT + EXCHANGE_DATE;
		$(EXCHANGE_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettingsWithFourYearsMinAndMaxDateRange());
		
		var TRADED_TICKET_START_DATE_ID = HASH + pageName + SLASH_DOT + closeBraceReplacedSecondInput + SLASH_DOT + TRADED_TICKET_START_DATE;
		$(TRADED_TICKET_START_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettingsWithFourYearsMinAndMaxDateRange());
		
		var TRADED_TICKET_END_DATE_ID = HASH + pageName + SLASH_DOT + closeBraceReplacedSecondInput + SLASH_DOT + TRADED_TICKET_END_DATE;
		$(TRADED_TICKET_END_DATE_ID).datepicker(getCancelAndSurrenderDatePickerSettings()); 
		
		if($(this).is(":checked")){
			$(EXCHANGE_DATE_CONTAINER_ID+index).show();
		}
		else{
			$(EXCHANGE_DATE_CONTAINER_ID+index).hide();
		}
	});
	
	$(PERIOD+tradedTicketTravelCardType).each(function(){
		
		var id = $(this).attr(ID_ATTRIBUTE);
		var length = id.length;
		var index = id.substring(length,length-1);
		$(this).change(function(){
			
			var id = $(this).attr(ID_ATTRIBUTE);
			var length = id.length;
			var index = id.substring(length,length-1);
			
			toggleEnddateField(index,$(this).val());
			toggleZonesForBusPass(index,$(this).val());
		});
		toggleEnddateField(index,$(this).val());
		toggleZonesForBusPass(index,$(this).val());
	});
});

function setLineNoIfAddTradedTicketCardClicked() {
	$(".addTradedTicket").each(function(){
		$(this).click(function(){
			setLineNoFromAddTradedTicketIdValue($(this).attr('id'));
			return true;
		});
	});
}

function setLineNoFromAddTradedTicketIdValue(idValue) {
	console.log("add traded ticket id value"+idValue);
	idValue = idValue.replace("addTradedTicket", "");
	idValue = idValue.replace("-submit", "");
	$("#cartItemId").val(idValue);
}

function replaceStringWithEscapeBackwardSlash(inputString){
	var openBraceReplacedTempInput = inputString.replace("[","\\[");
	var closeBraceReplacedTempInput = openBraceReplacedTempInput.replace("]","\\]");
	return closeBraceReplacedTempInput;
}

function getIndexFromId(id){
	var input = id.split(".");
	var secondInput = input[1];
	var length = secondInput.length;
	var index = secondInput.substring(length-1,length-2);
	return index;
}

function toggleEnddateField(index,travelCardValue){
	console.log(travelCardValue);
	var tradedTicketEndDateId = pageName + CARTITEMLIST_TILL_OPENBRACE+index+CARTITEMLIST_AFTER_CLOSEBRACE+END_DATE;
	var tradedTicketEndDateField = LABEL_FOR + tradedTicketEndDateId + '],' +INPUT_HASH + tradedTicketEndDateId + '';
	var otherTravelCard = "Other Travelcard";
	if( travelCardValue == otherTravelCard){
		$(tradedTicketEndDateField).show();
    } else {
    	$(tradedTicketEndDateField).hide();
    }
}

function toggleZonesForBusPass(index,travelCardValue){
	var tradedTicketStartZoneId =  CARTITEMLIST_TILL_OPENBRACE_WITHOUT_SLASH_DOT+index+CARTITEMLIST_AFTER_CLOSEBRACE+START_ZONE;
	var tradedTicketStartZoneField = LABEL_FOR + tradedTicketStartZoneId + '],'+SELECT_HASH+ tradedTicketStartZoneId + '';
	var tradedTicketEndZoneId = CARTITEMLIST_TILL_OPENBRACE_WITHOUT_SLASH_DOT+index+CARTITEMLIST_AFTER_CLOSEBRACE+END_ZONE;
	var tradedTicketEndZoneField = LABEL_FOR + tradedTicketEndZoneId + '],'+ SELECT_HASH+ tradedTicketEndZoneId + '';
	var busPass = "Bus Pass";
	 if (travelCardValue && travelCardValue.endsWith(busPass)) {
	        $(tradedTicketStartZoneField).hide();
	        $(tradedTicketEndZoneField).hide();
	    } else{
	        $(tradedTicketStartZoneField).show();
	        $(tradedTicketEndZoneField).show();
	    }
}