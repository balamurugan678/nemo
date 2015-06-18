$(document).ready(function () {
	
	setElements();
    setEvents();
    enableClickRow();
});

/* Used to change submit button behaviour*/
function setElements() {
    $("#search").attr("type", "button");
    $("#clearCriteria").attr("type", "button");
    resultsTable = $("#approvals").dataTable({
        "bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, "aoColumns": [
            {"mData": "caseNumber", "sWidth": "5%"}
            ,{"mData": "createdTime", "sWidth": "10%"}
            ,{"mData": "approvalReasons", "sWidth": "35%", "bSortable": false}
            ,{"mData": "amount", "sWidth": "10%", "iDataSort": 4}
            ,{"mData": "amountInPence", "bVisible": false}
            ,{"mData": "timeOnQueue", "sWidth": "10%","iDataSort": 6}
            ,{"mData": "timeOnQueueAsLong", "bVisible": false}
            ,{"mData": "agent", "sWidth": "10%"}
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

function setEvents() {
    $("#search-button").click(function () {
        search();
    });
    $("form").submit(function () {
        search();
        return false;
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
    });
    
    function search() {
        if (checkEmptySearch()) {
            alert('Please add a search criteria');
            $("input[type='text']").first().focus();
        } else {
        	toggleLoading(-1);
            $.post(sAddress + '/' + pageName + '/approvalValid.htm', $("form").serialize(), function (response) {
                if (response != '{}') {
                    var message = "";
                    var obj = $.parseJSON(response);
                    $.each(obj, function (i, n) {
                        message = message + n + '\n';
                    });
                    alert(message);
                    toggleLoading(-2);
                } else {
                    $.post(sAddress+'/' + approvalSearchURL, $("form").serialize(), function (response) {
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
        		if (this.value != '') empty = false;
        	}
        });
        return empty;
    }
    
    function clearCriteria() {
        $("#loading-box").hide();
        $("#approvals").show();
        $("input[type=text]").each(function () {
        	$(this).val("");
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
	$.post(sAddress + '/' + pageName + '/approvalAllByGroup.htm', $("form").serialize(), function (response) {
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
          openLink( sAddress + '/' + workflowItemURL + '?workflowItemId=' + aData.caseNumber);
    });
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