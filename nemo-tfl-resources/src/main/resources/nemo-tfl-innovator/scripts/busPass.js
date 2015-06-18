$(document).ready(function () {
    $("#busPassContinue-button").click(function(){addBusPassToOrder();});
});

function addBusPassToOrder(){
$.post(sAddress + '/BusPass/addBusPassToCart.htm', { 
    startDate: $("#startDate").val(), 
    emailReminder : $("#emailReminder").val(),
    ticketType : $("#busPassTicketType").val(),
    cardId : $("#OrderAdd\\.cardId").val()
}, function (response) {
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