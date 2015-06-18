$(document).ready(function () {
    hideSections();
     $("#ticketType").change(function(){
         hideSections();
         if ($(this).val() == 'payAsYouGo' ) { $("#payAsYouGoSection").show();}
         if ($(this).val() == 'annualBusTramPass' ) { $("#busPassSection").show();}
         if ($(this).val() == 'travelcard' ) { $("#travelCardSection").show();}
     });
     $("#"+pageName+"\\.endDate").datepicker();
});

function hideSections(){
    $("#payAsYouGoSection").hide();
    $("#busPassSection").hide();
    $("#travelCardSection").hide();
}