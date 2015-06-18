var anyMisMatchFound;
var searchedAlready;
var proceedToAdd;

$(document).ready(function () {
	
	$("#attach").toggle(false);
	
	$("#searchCard-button").click(function(){
		 search();
    }); 
	
	$("#searchresults").dataTable({
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
			/* Append the grade to the default row class name */
			if ( aData.comparison == null ){
				$('td:eq(3)', nRow).html( '<b>No Data</b>' );
			}
			else if ( aData.comparison == false )
			{
				$('td:eq(3)', nRow).html( '<b>False</b>' );
				$('td:eq(3)', nRow).addClass('different');
			}
			return nRow;
		},
		"bFilter": false, 
		"bProcessing": false,  
		"bDestroy": true, 
		"bPaginate": false,
		"bInfo": false,
	    "aoColumns": [
	        {"mData": "title"}
	        ,{"mData": "cubicValue"}
	        ,{"mData": "oysterValue"}
	        ,{"mData": "comparison"}

	    ]

	});
	
});

function search() {
	$("#searchresults").dataTable().fnClearTable();
	$("#attach").toggle(false);
	$("#hotlistWarning").toggle(false);
	  $("#verified").hide();
        if (checkEmptySearch()) {
            alert('Please add a search criteria');
            $("input[type='text']").first().focus();
        } else {
        	toggleLoading();
            $.post(sAddress + '/' + pageName + '/valid.htm', $("form").serialize(), function (response) {
                if (response != '{}') {
                    var message = "";
                    var hotlistedMessage = "The Oyster Card is hotlisted";
                    var obj = $.parseJSON(response);
                    $.each(obj, function (i, n) {
                        message = n + '\n';
                    });
                    if(message.toLowerCase().trim() == hotlistedMessage.toLowerCase().trim()) { 
                    	alert(message);
                        toggleExecuteCardSearch(true);                        
                    } else {
                    	alert(message);
                    	toggleLoading(1);
                    }
                } else {
                    toggleExecuteCardSearch(false);                        
                }
            });
        }
    }


function checkEmptySearch() {
    var empty = true;
    $("input").each(function () {
    	if (this.type != 'checkbox' && this.type!= 'hidden') {
    		if (this.value != '') empty = false;
    	}
    });
    return empty;
}

function toggleExecuteCardSearch(hotlistedFlag){
	$.ajax({ 
        type: 'POST', 
        url: sAddress + '/' + pageName + '/cardSearch.htm', 
        data: $("form").serialize() , 
        dataType:'json',
        success: function (response) { 
        setNewResult(response.comparison);
        if(hotlistedFlag) {
	       	$("#hotlistWarning").toggle();
	    }
        }
    });
}

function showHideHotlistWarning(flag){
		$("#hotlistWarning").toggle(flag);
}

function setNewResult(dataArray) {

    	$("#searchresults").dataTable().fnClearTable();
    	if (dataArray === null || dataArray.length == 0){
    		$("#attach").toggle(false);
  			$("#searchresults").html("No results found");

    	} else {
    		anyMisMatchFound = false;
    		searchedAlready = true;
			for (var i=0;i<dataArray.length;i++){
				if(dataArray[i].comparison == false){
					anyMisMatchFound= true;
						break;
				}
			}
    		$("#attach").toggle(true);
    		$("#searchresults").dataTable().fnAddData( dataArray);
    	}
}

function validate(form) {
	if (searchedAlready && anyMisMatchFound) {
		proceedToAdd = confirm(messagetext);
		if (proceedToAdd) {
			anyMisMatchFound = false;
		}
		searchedAlready = false;
		return proceedToAdd;
	} else if (searchedAlready && !anyMisMatchFound) {
		return true;
	} else {
		search();
		return false;
	}
}
