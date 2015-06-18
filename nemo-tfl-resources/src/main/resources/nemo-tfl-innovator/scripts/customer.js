var customerIdField = "#"+pageName+"\\."+"customerId";
var rowIds = [];
var rowCount = 1;
$(document).ready(function () {
    $("#customersFound").hide();
	$("[id^='refunds']").change(function() {
		var refundId = this.id;
		var cardId = refundId.substring("refunds".length);
		if ($(this).val()) {
			var pageName = $(this).val();
			var idParameter = "id=" + cardId;
			var targetActionParameter = "targetAction=viewCartUsingCubic";
			var url = constructUrl(pageName, idParameter, targetActionParameter);
			
			var imageName = "oyster";
    		var tabName = "failedcardrefund";
    		if (checkRowCardNumberExists(this, "refunds")) {
    		    openLink(url, failedCardRefundCartHeader + ": " + cardId,  failedCardRefundCartHeader + ": " + cardId, imageName, tabName);
    		}
           
		}
	});
	
	$('#cards').dataTable({
        "sPaginationType": "full_numbers", "aoColumns": [
            null,
            null,
            {"bSortable": false},
            {"bSortable": false},
            {"bSortable": false},
            {"bSortable": false}
        ]
    });
    $('#events').dataTable({
        "sPaginationType": "full_numbers", "aoColumns": [
            null,
            {"bSortable": false},
            {"bSortable": false},
            {"bSortable": false}
        ]
    });
    
    if (typeof(window.parent.showTab) != "undefined") {        
    	$("#cancel").hide();
    } else {
    	$("#cancel").click(function(){
            history.back();
         });
    }
    
    //focus on errored area. 
    $(".field-validation-error").each(function(){
    	var fieldId = "#"+pageName+"\\."+$(this).attr("id").split(".")[0];
    	$(fieldId).focus();
    });
    
  
    
    $("#"+pageName+"\\.emailAddress").change(function(){
        checkEmail();
    });
    
    $("#"+pageName+"\\.firstName").change(function(){
        checkCustomer();
    });
    
    $("#"+pageName+"\\.lastName").change(function(){
        checkCustomer();
    });
    
    $("#"+pageName+"\\.postcode").change(function(){
        checkCustomer();
    });
    
    //Webaccount access for agents 
    $("#openwebaccount-button").click(function(){
    	
    	var customerId = $(customerIdField).val();
    	var openWebAccountConfirmMessageCallLoggedPopupText = getMessage(contentCode.OPEN_WEB_ACCOUNT_CONFIRM_MESSAGE_CALL_LOGGED_POPUP_TEXT);
    	var openWebAccountConfirmMessageLoginPopupText = getMessage(contentCode.OPEN_WEB_ACCOUNT_CONFIRM_MESSAGE_LOGIN_POPUP_TEXT);
    	var newLine = '\n';
    	if(confirm(openWebAccountConfirmMessageCallLoggedPopupText + newLine + openWebAccountConfirmMessageLoginPopupText)){
    		$.ajax({
		        type : "GET",
		        url : "Customer/agentLogon.htm?webaccountId="+customerId,
		        success : function(response) {
		        	openOysterWebaccount(response);
		        },
		        error : function(e) {
		            alert(getMessage(contentCode.OPEN_WEB_ACCOUNT_ERROR) + e);
		        }
		    });
    	}
    });
    
    $("#orders-button").click(function(){
        var customerId = $(customerIdField).val();
        openLink(sAddress + "/Order.htm?customerId=" +customerId , "Orders:" + customerId,  "Orders" + customerId + ".", "oyster","oysteredit");
    });
    
    $(".checkFailedCard").each(function(){
        $(this).click(function(e){
            if (checkRowCardNumberExists(this, "checkFailed")) {
                checkFailed(this);
            }
        });
    });
    
    $(".cardAdmin").each(function(){
        var href = $(this).attr("href");
        $(this).attr("href","#");
        var val = href.substr(href.indexOf("=")+1, href.length);
        $(this).click(function(e){
            if (checkRowCardNumberExists(this, "cardAdmin")) {
                openCardAdmin(val,$(customerIdField).val());
            } else {
                e.preventDefault();
            }
        });
    });
    
    $("#save-submit").click(function(){
        return true;
    });

    showCustomerDeactivationAlertIfCustomerIsDeactivated();
    showCustomerDeactivationReasonOtherIfOtherDeactivationReasonIsSelected();
    
    $("#customerDeactivationReason").on('change', function() {
    	if (this.value == 'Other') {
    	  $("#customerDeactivationReasonOther").show();
    	} else {
    	  $("#customerDeactivationReasonOther").hide();
    	}
    });
});

function showCustomerDeactivationAlertIfCustomerIsDeactivated() {
	var customerDeactivatedField = "#"+pageName+"\\."+"customerDeactivated";
	var customerDeactivationReasonField = "#customerDeactivationReason";
	var customerDeactivationReasonOtherField = "#"+pageName+"\\."+"customerDeactivationReasonOther";
	if($(customerDeactivatedField + ":checked").val() && 
	   $(customerDeactivationReasonField).val() && 
	   ($(customerDeactivationReasonField).val() != 'Other' || 
	    ($(customerDeactivationReasonField).val() == 'Other' && $(customerDeactivationReasonOtherField).val()))) {
		alert(getMessage(contentCode.LINKED_WEB_ACCOUNT_IS_DEACTIVATED_ALERT_MESSAGE_POPUP_TEXT));
	}
}

function showCustomerDeactivationReasonOtherIfOtherDeactivationReasonIsSelected() {
	var customerDeactivatedField = "#"+pageName+"\\."+"customerDeactivated";
	var customerDeactivationReasonField = "#customerDeactivationReason";
	var customerDeactivationReasonOtherDiv = "#customerDeactivationReasonOther";
	if ($(customerDeactivatedField + ":checked").val() && $(customerDeactivationReasonField).val() == 'Other')  {
		$(customerDeactivationReasonOtherDiv).show();
	} else {
		$(customerDeactivationReasonOtherDiv).hide();
	}
}

function checkRowCardNumberExists(item, name){
    var rowId = $(item).attr('id').replace(/[^0-9]/g,'');
    var val = $("#cardNumber" + rowId).html();
    if (val === '' || rowId === ''){
        return false;
    }
    return true;
}
 
 function openOysterWebaccount(response){
	 var token = response.substring(response.indexOf("SUCCESS?TOKEN=")+ 14);
	 var customerId = $(customerIdField).val();
	 var webaccountIdField = "#"+pageName+"\\."+"webAccountId";
 	 var webaccountId = $(webaccountIdField).val();
	 window.open(onlineAddress + "/Login.htm?agentId=SK11&customerId="+customerId+"&token="+token+"&webaccountId="+webaccountId, '_blank', config='toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, directories=no, status=no');
 }
 
 function checkEmail(){
     var emailAddress = $("#"+pageName+"\\.emailAddress").val();
     $("input[type='text']").each(function(){$(this).prop("disabled",false);});
     $("#save-submit-button").prop("disabled", false);
     $("#customerLink").remove();
     $.post(sAddress + '/Customer/checkEmailAvailable.htm', { email:emailAddress}, function (response) {
         var customerId =  (response === 'false');
         $("#availableEmail").attr("class", (customerId ? "tick" : "cross"));
         if (!customerId) {
             var customerLink = $("<button />").attr("id", "customerLink")
               .attr("value",sAddress + "/Customer.htm?id=" + response)
               .attr("class","customerLink")
               .attr("type", "button")
               .html("Open")
               .click(function() {
                   openLink($(this).val(), "Edit Customer:" + customerId,  "Edit Customer " + customerId + ".", "oyster","oysteredit");
               });
             $("#availableEmail").after(customerLink);
             $("input[type='text'").each(function(){$(this).prop("disabled",true);});
             $("#"+pageName+"\\.emailAddress").prop("disabled", false);
             $("#save-submit-button").prop("disabled", true);
         }
     });
 };
 
function checkCustomer(){
    if ($("#"+pageName+"\\.firstName").val() != '' && $("#"+pageName+"\\.lastName").val() != '' && $("#"+pageName+"\\.postcode").val() != ''){
        $("#customerSelect").remove();
        $("#checkCustomerLink").remove();
        $.post(sAddress + '/Customer/checkCustomer.htm', $("form").serialize(), function (response) {
            var json = $.parseJSON(response);
            console.info(json);
            console.info(json.length);
            if (json.length > 0) {
                $("#customersFound").show();
                $("#customersFound").html("");
                $.each(json, function(i, opt) {
                    opt.result = opt.firstName + " " + opt.lastName  + " -- [" + opt.address + "]";
                });
                $("#available").attr("class","blank");
                var selectList = $("<select />").attr("id","customerSelect");
                $("#customersFound").append(selectList);
                addOptionsValueAndText(json, $("#customerSelect"), "id", "result");
                var checkCustomerLink = $("<button />").attr("id", "checkCustomerLink")
                .attr("value",sAddress + "/" + customerURL + "?id=")
                .attr("class","customerLink")
                .attr("type", "button")
                .html("Open")
                .click(function() {
                    var customerId = $("#customerSelect").val();
                    openLink($(this).val() + customerId, "Edit Customer:" + customerId,  "Edit Customer " + customerId + ".", "oyster","oysteredit");
                });
                $("#customersFound").append(checkCustomerLink);
            }
        });
    }
}    


function checkFailed(e){
    checkPrePayTicketFailed(e);
};

function checkPrePayTicketFailed(e){
    var prestigeId = $(e).val();
    $("#save-submit-button").prop("disabled", false);
    $.post(sAddress + '/Customer/checkCardStatus.htm', { prestigeId:prestigeId}, function (response) {
        var obj = $.parseJSON(response);
        if (confirm(obj.prePayTicketMessage)) {
            alert(getMessage(contentCode.REISSUE_AS_FAILED_ERROR));
        } else {
            if ( parseInt(obj.prePayValueMessage, 10) > 0) {
                alert(getMessage(contentCode.PAY_AS_YOU_GO_BALANCE_ERROR) + ": " + obj.prePayValueMessage + "\n\n" +getMessage(contentCode.REISSUE_AS_FAILED_ERROR));
            } else {
                alert(obj.prePayValueMessage);
            }
        }

    });
};

function constructUrl(pageName, firstParameter, secondParameter) {
	return pageName + ".htm" + "?" + firstParameter + "&" + secondParameter;
};

function openCardAdmin(cardNumber, customerId){
    openLink( sAddress + '/' + cardAdmin + "?cardNumber="+cardNumber+"&customerId="+customerId , 'Card Admin',  'Card Admin', 'oyster','oysteredit');
}
