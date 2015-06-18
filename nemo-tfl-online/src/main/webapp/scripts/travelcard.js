var TravelCard = {
	sel : {
		travelCardType : "#travelCardType",
		customDateContainer : "#customDateContainer",
		startZone           : "#startZone"
	},

	initialisePage : function() {
		this.toggleCustomDateVisibility();
		//this.resetEndZoneOptions();
		$(this.sel.travelCardType).on("change",
				$.proxy(this.dateChangeHandler, TravelCard));
		$(this.sel.travelCardType).on("change",
				$.proxy(this.endZoneListGetter, TravelCard));
		$(this.sel.startZone).on("change",
				$.proxy(this.endZoneListGetter, TravelCard));
	},

	dateChangeHandler : function() {
		this.toggleCustomDateVisibility();
	},
	
	toggleCustomDateVisibility : function() {
		if ($(this.sel.travelCardType).val() === 'Unknown') {
			this.toggleVisibility(this.sel.customDateContainer, true);		} else {
			this.toggleVisibility(this.sel.customDateContainer, false);
		}
	},

	toggleVisibility : function(jQuerySelector, show) {
		if (show) {
			$(jQuerySelector).removeClass("without-js");
		} else {
			$(jQuerySelector).removeClass("without-js").addClass("without-js");
		}
	},
	
	endZoneListGetter : function() {
		if($('#travelCardType').val() !== null && $('#startZone').val() !== null){
			this.doFindMappingZones();
		}
	},
	
	resetEndZoneOptions : function() {
		if($(this.sel.startZone).val() === null){
			$("#endZone").get(0).options.length = 0;
			$("#endZone").prev().text("Please select ...");
		}
	},
	
	doFindMappingZones : function () {
		var formData = {
			'startZone' 		: $('#startZone').val(),
			'travelCardType' 	: $('#travelCardType').val(),	
			'pageListName'   : $('#startZone').attr("pageAndListName")
		};
	    $.ajax({
	        type: "POST",
	        url:  contextPath + "/TopUpTicket.htm",
	        data : formData,
	        dataType : 'json',
	        success: function (response) {
	        	 $("#endZone").get(0).options.length = 0;
	        	 if(response.length == 0) {
	        		 $("#endZone").prev().text("No mapping, Select another start zone");
	        		 $("#endZone").attr("title","No mapping, Select other start zone");
	        		 return;
	        	 } 
	        	 $("#endZone").prev().text("Please select ...");
	        	 var option = new Option("Please select ...", null, true, "selected");
	        	 option.setAttribute("disabled", true);
	        	 $("#endZone").get(0).options[$("#endZone").get(0).options.length] = option;
	        	 
	            $.each(response, function(index, item) {
	                 $("#endZone").get(0).options[$("#endZone").get(0).options.length] = new Option(item.meaning, item.value);
	             });
	        },
	        error: function (request, status, error) {
	            
	        }
	    });
	   
	}

	
};


