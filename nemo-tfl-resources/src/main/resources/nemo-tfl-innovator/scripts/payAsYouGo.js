$(document).ready(function () {
    $("#payAsYouGoContinue-button").click(function(){addPayAsYouGoOrder();});
});

function addPayAsYouGoOrder(){
    $.post(sAddress + '/PayAsYouGo/addPayAsYouGoToCart.htm', { 
            creditBalance: $("#creditBalance").val(), 
            autoTopUpAmt : $("#autoTopUpAmt").val(),
            ticketType : $("#ticketType").val(),
            cardId : $("#OrderAdd\\.cardId").val(),
            cartType : $("#cartType").val(),
            webAccountId : $("#OrderAdd\\.webAccountId").val()
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