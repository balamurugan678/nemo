var resultsTable;
    var currentData = '';
    $(document).ready(function () {
        setElements();
        setEvents();
        addDatePickerForStartedAt(shortDatePattern);
        addDatePickerForEndedAt(shortDatePattern);
    });
    
    function setElements() {
        
        $("#search").attr("type", "button");
        $("#clearCriteria").attr("type", "button");
        resultsTable = $("#searchresults").dataTable({
            "bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, "aoColumns": [
                {"mData": "id", "sWidth": "10%"}
                ,{"mData": "jobName", "sWidth": "20%"}
                ,{"mData": "fileName", "sWidth": "30%"}
                ,{"mData": "startedAt", "sWidth": "15%"}
                ,{"mData": "endedAt", "sWidth": "15%"}
                ,{"mData": "status", "sWidth": "10%"}
            ],
            "fnDrawCallback": function(oSettings) {
                if (oSettings._iDisplayLength > oSettings.fnRecordsDisplay()) {
                    $(oSettings.nTableWrapper).find('.dataTables_paginate').hide();
                } else {
                    $(oSettings.nTableWrapper).find('.dataTables_paginate').show();
                }
            }
        });
        resultsTable.fnSettings().aoDrawCallback.push({
        	"fn" : function(){enableClickRow();},
        	"sName" : "user"
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
    }
    
    function addDatePickerForStartedAt(shortDatePattern) {
        var date = new Date();
        var elementId = pageName + "\\." +"startedAt"; 
        $("#"+elementId).datepicker({
            dateFormat: shortDatePattern,
            changeYear: true,
            changeMonth: true,
            maxDate: new Date(date.getFullYear(), date.getMonth(), date.getDate()),
        });
    }
    
    function addDatePickerForEndedAt(shortDatePattern) {
        var date = new Date();
        var elementId = pageName + "\\." +"endedAt";  
        $("#"+elementId).datepicker({
            dateFormat: shortDatePattern,
            changeYear: true,
            changeMonth: true,
            maxDate: new Date(date.getFullYear(), date.getMonth(), date.getDate()),
        });
    }

    function search() {
        if (checkEmptySearch()) {
            $("input[type='text']").first().focus();
        } else {
        	toggleLoading(-1);
        	$.post(sAddress+'/' + agentLogSearchURL, $("form").serialize(), function (response) {
                setNewResult(($.parseJSON(response)));
            });
        }
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
    	$("#searchresults tr").unbind("click");
    	$("#searchresults tr").on("click", function (event) {
            var iPos = resultsTable.fnGetPosition(this);
            var aData = resultsTable.fnGetData(iPos);
              openLink( sAddress + '/' + agentLogDetailURL + '?id=' + aData.id);
        });
    }
    
    function checkEmptySearch() {
        var empty = true;
        var elementIdStartedAtVar = pageName + "\\." +"startedAt";  
        var elementIdEndedAtVar = pageName + "\\." +"endedAt";  
        var elementIdStartedAtVal = jQuery.trim($("#"+elementIdStartedAtVar).val());
        var elementIdEndedAtVal = jQuery.trim($("#"+elementIdEndedAtVar).val());
        
        if((elementIdEndedAtVal.length>0) && (elementIdStartedAtVal.length>0)) {
        	empty = false;
        	return empty;
        }
        else {
        	alert("Enter Job Extraction Dates");
        	return empty;
        }
    }

    function clearCriteria() {
        $("#loading-box").hide();
        $("#searchresults").show();
        $("input[type=text]").each(function () {
        	$(this).val("");
        });
    }
