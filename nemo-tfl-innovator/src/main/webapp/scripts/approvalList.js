$(document).ready(function () {
	
	setElements();
	addDateCreated(pageName, shortDatePattern);
    setEvents();
    enableClickRow();
    
});

function addDateCreated(pageName, shortDatePattern) {
    var dateCreatedId = pageName + "\\." + "dateCreated";
    $("#" + dateCreatedId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
    });
}

/* Used to change submit button behaviour*/
function setElements() {
    $("#search").attr("type", "button");
    $("#clearCriteria").attr("type", "button");
    extendDatatable();
    resultsTable = $("#approvals").dataTable({
    	"bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, "aoColumns": [
            {"mData": "caseNumber", "sWidth": "5%", "sClass" : "caseNumber"}
            ,{"mData": "createdTime", "sWidth": "10%", "sType": "date-uk"}
            ,{"mData": "approvalReasons", "sWidth": "35%", "bSortable": false}
            ,{"mData": "amount", "sWidth": "10%", "iDataSort": 4}
            ,{"mData": "amountInPence", "bVisible": false}
            ,{"mData": "timeOnQueue", "sWidth": "10%","iDataSort": 6}
            ,{"mData": "timeOnQueueAsLong", "bVisible": false}
            ,{
            	"mData": "agent", "sWidth": "10%",
            	"mRender": function(data, type, full) {
            		if(data === " " || data === null || data === "") {
            			var caseNumber = full.caseNumber;
            			var button = '<button id="claim-submit" class="claim" type="submit" title="Claim" value=' + caseNumber + '>Claim</button>';
            			return button;
            		} else {
            			return data;
            		}
        		}
            }
            ,{"mData": "paymentMethod", "sWidth": "10%"}
            ,{"mData": "status", "sWidth": "10%"}
        ],
        "fnDrawCallback": function(oSettings) {
        	enableClickRow();
            if (oSettings._iDisplayLength > oSettings.fnRecordsDisplay()) {
                $(oSettings.nTableWrapper).find('.dataTables_paginate').hide();
            } else {
                $(oSettings.nTableWrapper).find('.dataTables_paginate').show();
            }
        },
    	"iDisplayLength": 50,
    	"aLengthMenu": [50, 100, 150, 200],
    	"aaSorting": [[ 5, 'desc' ]]
    });
}

function extendDatatable() {
	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	    "date-uk-pre": function ( date ) {
	        var ukDateArray = date.split('/');
	        return (ukDateArray[2] + ukDateArray[1] + ukDateArray[0]) * 1;
	    },
	 
	    "date-uk-asc": function ( date, nextDate ) {
	        return ((date < nextDate) ? -1 : ((date > nextDate) ? 1 : 0));
	    },
	 
	    "date-uk-desc": function ( date, nextDate ) {
	        return ((date < nextDate) ? 1 : ((date > nextDate) ? -1 : 0));
	    }
	} );
}

function setEvents() {	
    $("#search-button").click(function () {
        search();
    });
    	
    $("#clearCriteria-button").click(function () {
        clearCriteria();
    });
    
    $("input[type=text]").each(function () {
        $(this).bind('keypress', function (e) {
            var code = (e.keyCode ? e.keyCode : e.which);
            if (code == 13) {
                search();
            }
        });
        
    setClaimClickEvents();
    
    });
    
    function search() {
        if (checkEmptySearch()) {
            alert('Please add a search criteria');
            $("input[type='text']").first().focus();
        } else {
        	toggleLoading(-1);
            $.post(sAddress + '/' + pageName + '/approvalValid.htm', $(approvalListCmd).serialize(), function (response) {
                if (response != '{}') {
                    var message = "";
                    var obj = $.parseJSON(response);
                    $.each(obj, function (i, n) {
                        message = message + n + '\n';
                    });
                    alert(message);
                    toggleLoading(-2);
                } else {
                    $.post(sAddress+'/' + approvalSearchURL, $(approvalListCmd).serialize(), function (response) {
                        setNewResult(($.parseJSON(response)));
                    });
                }
            });
        }
    }

    function checkEmptySearch() {
        var empty = true;
        $("input").each(function () {
        	if (this.type != 'checkbox' && this.type!= 'hidden') {
        		if (this.value != '') { empty = false; }
        	}
        });
        
        $("#approvalListCmd select").each(function () {
        	if (this.value != '') { empty = false; }
        });
        return empty;
    }
    
    function clearCriteria() {
        $("#loading-box").hide();
        $("#approvals").show();
        $("input[type=text]").each(function () {
        	$(this).val("");
        });
        
        $("#approvalListCmd select").each(function () {
        	$(this).val("");
        });
        $("input[type=checkbox]").each(function () {
        	$(this).attr('checked', false);
        });
        
        var user = getUrlParameter("user");
        
        if(user == undefined) {
        	getAllByGroup();
        } else {
        	getAllByUser(user);
        }
        
        toggleLoading(-2);
    }
}

function getAllByGroup() {
	$.post(sAddress + '/' + pageName + '/approvalAllByGroup.htm', $(approvalListCmd).serialize(), function (response) {
        setNewResult(($.parseJSON(response)));
    });
}

function getAllByUser(user) {
	$.post(sAddress + '/' + pageName + '/approvalAllByUser.htm?user=' + user, function (response) {
        setNewResult(($.parseJSON(response)));
    });
}

function setNewResult(dataArray) {
	resultsTable.fnClearTable(this);
	if (dataArray === null || dataArray.length == 0){
		   $("#messageArea").html("No results found");
	} else {
		$("#messageArea").html("");
        resultsTable.fnAddData(dataArray);
        enableClickRow();
	}
	toggleLoading(dataArray.length);
}

function enableClickRow(){
	$("#approvals tr").unbind("click");
	$("#approvals tr").on("click", function (event) {
        var iPos = resultsTable.fnGetPosition(this);
        var aData = resultsTable.fnGetData(iPos);
          openLink( sAddress + '/' + workflowItemURL + '?caseNumber=' + aData.caseNumber);
    });
	setClaimClickEvent();
}

function getUrlParameter(param) {
	var pageParams = window.location.search.substring(1);
	var paramVariables = pageParams.split("&");
	for(var i = 0; i < paramVariables.length; i++) {
		var urlParams = paramVariables[i].split("=");
		if(urlParams[0] == param) {
			return urlParams[1];
		}
	}
}

function setClaimClickEvent() {
	$(".claim").click(function () {
		var caseNumber = $(this).val();
		$('input[name=caseNumber]').val(caseNumber);
		$('#claimForm').submit();
		return false;
	});
}