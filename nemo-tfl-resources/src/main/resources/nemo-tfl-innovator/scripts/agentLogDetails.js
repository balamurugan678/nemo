var resultsTable;
    var currentData = '';
    $(document).ready(function () {
        setElements();
        setEvents();
    });
    
    function setElements() {
        resultsTable = $("#searchresults").dataTable({
            "bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, "aoColumns": [
                {"mData": "id", "sWidth": "10%"}
                ,{"mData": "log", "sWidth": "80", "sHeight" : "50", "scrollbars" : true}
            ],
        });
    }
    function setEvents() {
    	$("#cancel-button").click(function(){
            history.back();
         });
    }
    
