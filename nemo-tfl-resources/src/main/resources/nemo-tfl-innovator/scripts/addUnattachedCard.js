 var allFieldsMatch;
 
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
	//$("#" + pageName +"cardNumber").
	
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
                    var obj = $.parseJSON(response);
                    $.each(obj, function (i, n) {
                        message = n + '\n';
                    });
                    alert(message);
                    toggleLoading(1);
                } else {
                	$.ajax({ 
                        type: 'POST', 
                        url: sAddress + '/' + pageName + '/cardSearch.htm', 
                        data: $("form").serialize() , 
                        dataType:'json',
                        success: function (response) { 
                        setNewResult(response.comparison);
                        $("#hotlistWarning").toggle(response.hotlisted);
                        toggleLoading(1);
                        }
                    });
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


function showHideHotlistWarning(flag){
		$("#hotlistWarning").toggle(flag);
}

  function setNewResult(dataArray) {

    	$("#searchresults").dataTable().fnClearTable();
    	if (dataArray === null || dataArray.length == 0){
    		$("#attach").toggle(false);
  			$("#searchresults").html("No results found");

    	} else {
    		allFieldsMatch = true;
			for (var i=0;i<dataArray.length;i++){
				if(dataArray[i].comparison == false){
					allFieldsMatch= false;
						break;
				}
			}
    		$("#attach").toggle(true);
    		$("#searchresults").dataTable().fnAddData( dataArray);
    	}
}

  function validate(form) {
	  if (!allFieldsMatch){
		  return confirm(messagetext);    
	  }
	  return true;
  	}
