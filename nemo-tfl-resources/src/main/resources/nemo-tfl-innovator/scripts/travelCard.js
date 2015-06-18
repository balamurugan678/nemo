$(document).ready(function () {
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