var resultsTable;
    var currentData = '';
    $(document).ready(function () {
        setElements();
        setEvents();
    });
    /* Used to change submit button behaviour*/
    function setElements() {
        
        $("#search").attr("type", "button");
        $("#clearCriteria").attr("type", "button");
        resultsTable = $("#searchresults").dataTable({
            "bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, "aoColumns": [
                {"mData": "id", "sWidth": "10%"}
                ,{"mData": "name", "sWidth": "15%"}
                ,{"mData": "oysternumber", "sWidth": "15%"}
                ,{"mData": "status", "sWidth": "15%"}
                ,{"mData": "address", "sWidth": "40%"}
                ,{"mData": "calls", "sWidth": "10%", "bSortable": false}
            ],
            "fnDrawCallback": function(oSettings) {
                if (oSettings._iDisplayLength > oSettings.fnRecordsDisplay()) {
                    $(oSettings.nTableWrapper).find('.dataTables_paginate').hide();
                } else {
                    $(oSettings.nTableWrapper).find('.dataTables_paginate').show();
                }
            }
        });
        //added to enable the clicks once the resultstable is reloaded
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
        $("#addCustomer-button").click(function () {
            addCustomer();
        });
        
        
        $("input[type=text]").each(function () {
            $(this).bind('keypress', function (e) {
                var code = (e.keyCode ? e.keyCode : e.which);
                if (code == 13) {
                    search();
                }
            });
        });

        $("#openAnonymousGoodwillRefundPage-button").click(function () {
            openAnonymousGoodwillPage();
        });
    }
    function search() {
        if (checkEmptySearch()) {
            alert('Please add a search criteria');
            $("input[type='text']").first().focus();
        } else {
        	toggleLoading(-1);
            $.post(sAddress + '/' + pageName + '/valid.htm', $("form").serialize(), function (response) {
                if (response != '{}') {
                    var message = "";
                    var obj = $.parseJSON(response);
                    $.each(obj, function (i, n) {
                        message = message + n + '\n';
                    });
                    alert(message);
                    toggleLoading(-2);
                } else {
                    $.post(sAddress+'/' + customerSearchURL, $("form").serialize(), function (response) {
                        setNewResult(($.parseJSON(response)));
                    });
                }
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
              openLink( sAddress + '/' + customerURL + '?id=' + aData.id + '&cardNumber=' + aData.oysternumber+ '&reference=' + aData.resultType, 'Edit Customer:' + aData.id,  'Edit Customer ' + aData.id + '.', 'oyster','oysteredit');
        });
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
        $("#searchresults").show();
        $("input[type=text]").each(function () {
        	$(this).val("");
        });
    }
    
    function addCustomer(){
    	var url = sAddress + '/' + customerURL ;
    	openLink(url, addCustomerDescription,  addCustomerHint, addCustomerImage, addCustomerName);
    }
    
    function openAnonymousGoodwillPage(){
    	var url = sAddress + '/' + anonymousGoodwillRefundURL ;
    	openLink(url, openAnonymousGoodwillPageDescription,  openAnonymousGoodwillPageHint, openAnonymousGoodwillPageImage, openAnonymousGoodwillPageName);
    }
    