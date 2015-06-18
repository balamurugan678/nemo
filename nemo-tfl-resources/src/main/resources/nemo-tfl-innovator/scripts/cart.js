$(document).ready(function () {
    addDeleteClick();
    $("#continue-submit").click(function(){
        $("#cartCmd").attr("action", "Checkout.htm");
        $("#cartCmd").submit();
    });
});


function deleteShoppingItem(lineNo){
    $.post(sAddress + '/Cart/deleteShoppingItem.htm', { 
        lineNo: lineNo
        
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

function updateCart(){
    $.post(sAddress + '/Cart/updateCart.htm', {
        cardId : $("#OrderAdd\\.cardId").val(),
        startionId : $("#stationId").val(),
        cartType : $("#cartType").val(),
        pageName : $("#pageName").val(),
        webAccountId : $("#OrderAdd\\.webAccountId").val()
    }, function (response) {
        var obj = $.parseJSON(response);
        if ($("#subTotalRow").length == 0 || obj.cartItemList === undefined){
            location.reload();
        } else {
            clearCartItems();
            var x = 0;
            $.each(obj.cartItemList, function(i, current) {
                var row = $("<tr class=\"cartItem\">");
                if ( current.item.indexOf("Auto Top-up") > -1 ) {
                    row.append( $("<td>"));
                    var column = $("<td>");
                    var itemSpan = $("<span>");
                    itemSpan.html("Auto Top-up ("+current.formattedAutoTopUpAmount+")");
                    //column.html("Auto Top-up("+current.formattedAutoTopUpAmount+")");
                    column.append(itemSpan);
                    row.append(column);
                    row.append( $("<td>"));
                    row.append( $("<td>"));
                    row.append( $("<td>"));
                    row.append( $("<td align=\"right\">").html("\u0026pound;0.00"));
                }else {
                    row.append( $("<td>").html($("<button  type=\"button\" class=\"removeItem\">").val(x++).html("Remove")));
                    var column = $("<td>");
                    var itemSpan = $("<span class=\"bold\">").html(current.item);
                    column.append(itemSpan);
                    row.append(column);
                    row.append( $("<td>").html(current.startDate));
                    row.append( $("<td>").html(current.endDate));
                    row.append( $("<td>").html(current.emailReminder));
                    row.append( $("<td align=\"right\">").html(current.formattedPrice));
                }
                row.insertBefore("#subTotalRow");
            });
            addDeleteClick();
            $("#subTotalAmountCell").html(obj.formattedSubTotalAmt);
            $("#refundableDepositlAmountCell").html(obj.formattedRefundableDepositAmount);
            $("#totalAmountCell").html(obj.formattedTotalAmount);
            $("#toPayAmountCell").html(obj.formattedToPayAmount);
            $('form').each(function(current){
                if (current[0] != undefined) {
                    current[0].reset();
                }
            });
        }
    });
}

function addDeleteClick(){
    $(".removeItem").each(function(){
        $(this).click(function(){deleteShoppingItem($(this).val());});
    });
}

function clearCartItems(){
    $(".cartItem").each(function(i,current) {
        current.remove();
    });
}