var CUSTOM_PERIOD_OPTION_CODE = "10";
var JourneyHistory = {
	sel : {
		submitLoadingContainer: "#submitLoadingContainer",
		submitButtonContainer : "#submitButtonContainer",  	
		showJourneySubmitButton : "#getDetails-submit-button",  	
		selectDate : "#selectDate",
		selectCard : "#selectCard",
		customDateContainer : "#customDateContainer"
	},

	initialisePage : function(){
	    this.toggleCustomDateVisibility();
		$(this.sel.selectCard).on("change", $.proxy(this.cardChangeHandler, JourneyHistory));
		$(this.sel.selectDate).on("change", $.proxy(this.dateChangeHandler, JourneyHistory));
	},

	
	verifySubmitFormRequired : function(){
		if ($(this.sel.selectCard).val()!=null && $(this.sel.selectCard).val()!="" && $(this.sel.selectDate).val()!=CUSTOM_PERIOD_OPTION_CODE){
			return true;
		}else{
			return false;
		}
	},
	submitForm : function(){
		$(this.sel.submitLoadingContainer).hide().removeAttr("hidden").show();		
		$(this.sel.showJourneySubmitButton).click();		
	},

	cardChangeHandler : function(){
		if (this.verifySubmitFormRequired()){
			this.submitForm();
		}
	},	
	
	dateChangeHandler : function(){
		this.toggleCustomDateVisibility();			
		if (this.verifySubmitFormRequired()){
			this.submitForm();
		}
	},
	
	toggleCustomDateVisibility : function(){
		if ($(this.sel.selectDate).val()===CUSTOM_PERIOD_OPTION_CODE){
			this.toggleVisibility(this.sel.customDateContainer, true);
			this.toggleVisibility(this.sel.submitButtonContainer, true);
		}else{
			this.toggleVisibility(this.sel.customDateContainer, false);
			this.toggleVisibility(this.sel.submitButtonContainer, false);
		}
	},
	
	
	toggleVisibility : function(jQuerySelector, show){
		if (show){
			$(jQuerySelector).removeClass("without-js");
		}else{
			$(jQuerySelector).removeClass("without-js").addClass("without-js");			
		}		
	}
};

