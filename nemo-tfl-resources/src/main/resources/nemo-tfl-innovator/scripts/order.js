var ordersTable; 
var itemsTable;
var dTableOrder = {};
var dTableItems = {};

$(document).ready(function () {
    
    ordersTable = $('#orders').dataTable({
        "sPaginationType": "full_numbers", "aoColumns": [
            {"mData": "orderNumber", "sWidth": "10%"}
            ,{"mData": "orderDate", "sWidth": "10%"}
            ,{"mData": "amount", "sWidth": "10%", "sClass" : "table-right", "sType" : "currency"}
            ,{"mData": "status", "sWidth": "10%", "bSortable": false}
        ]
    });
    dTableOrder = {"id" : "orders", "object" : ordersTable};
     ordersTable.fnSettings().aoDrawCallback.push({
         "fn" : function(){enableClickRow(dTableOrder);},
         "sName" : "user"
     });
     //redraw table to pass data to aData array
     ordersTable.fnDraw();
     
     itemsTable = $('#items').dataTable({
        "sPaginationType" : "full_numbers",
        "aoColumns" : [ {
            "mData" : "name",
            "sWidth" : "80%"
        }, {
            "mData" : "formattedPrice",
            "sWidth" : "20%",
            "sClass" : "table-right",
            "sType" : "currency"
        } ],
        "fnDrawCallback": function(oSettings) {
            if (oSettings._iDisplayLength > oSettings.fnRecordsDisplay()) {
                $(oSettings.nTableWrapper).find('.dataTables_paginate').hide();
                $(oSettings.nTableWrapper).find('.dataTables_filter').hide();
            }
        }
    });
    dTableItems = {"id" : "items", "object" : itemsTable};
    itemsTable.fnSettings().aoDrawCallback.push({
        "fn" : function(){enableClickRow(dTableItems);},
        "sName" : "user"
    });
    //redraw table to pass data to aData array
     itemsTable.fnDraw();
     
     $("#items_wrapper").hide();
    
     
 }); 
 

 function enableClickRow(tableObject){
     $("#" + tableObject.id + " tr").unbind("click");
     $("#" + tableObject.id + " tr").on("click", function (event) {
         var iPos =  tableObject.object.fnGetPosition(this);
         var aData = tableObject.object.fnGetData(iPos);
         if (tableObject.id === "orders") {
             loadOrderItems(aData.orderNumber);
         } else {
             showProductInfo(aData);
         }
     });
 }
 
 function loadOrderItems(orderNumber){
     toggleLoading();
     $.post(sAddress+'/' + pageName + "/LoadItems.htm", { orderNumber: orderNumber },  function (response) {
         console.info(response);
         setNewResult(($.parseJSON(response)), dTableItems);
         $("#items_wrapper").show();
     });
 }
 
 function setNewResult(dataArray, tableObject) {
     tableObject.object.fnClearTable();
     if (dataArray === null || dataArray.length == 0){
         toggleLoading(0);
     } else {
         tableObject.object.fnAddData(dataArray);
         enableClickRow(tableObject);
         toggleLoading(1);
     }
    
 }
 
 function showProductInfo(productInfo){
     var LINE_BREAK = "\n";
     var message = "CardId : " + productInfo.cardId + LINE_BREAK;
     message += "Start Date: " + productInfo.startDate + LINE_BREAK;
     message += "End Date: " + productInfo.endDate + LINE_BREAK;
     message += "Start Zone: " + productInfo.startZone + LINE_BREAK;
     message += "End Zone: " + productInfo.endZone + LINE_BREAK;
     message += "Reminder Date: " + productInfo.reminderDate + LINE_BREAK;
     if ( productInfo.shippingMethodId === undefined) {
         alert(message);
     }
  }
 