<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<form:errors path="travelCardRefundLimit" cssClass="field-validation-error" />
<div>
	<nemo-tfl:selectList id="travelCardType" path="cartItemCmd.travelCardType" selectList="${travelCardTypes}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.travelCardType}" />
	<form:errors path="cartItemCmd.travelCardType" cssClass="field-validation-error" />
	
	<nemo-tfl:selectList id="passengerType" path="cartItemCmd.passengerType" selectList="${travelCardRates}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.passengerType}" useManagedContentForMeanings="false"/>
	<form:errors path="cartItemCmd.passengerType" cssClass="field-validation-error" />
	
	<nemo-tfl:selectList id="discountType" path="cartItemCmd.discountType" selectList="${travelCardDiscountTypes}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.discountType}" useManagedContentForMeanings="false"/>
	<form:errors path="cartItemCmd.discountType" cssClass="field-validation-error" />
	
	<to:text id="cartItemCmd.startDate" mandatory="true"/>
	<to:text id="cartItemCmd.endDate" />
	
	<nemo-tfl:selectList id="fromZone" path="cartItemCmd.startZone" selectList="${travelCardZones}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.startZone}" />
	<form:errors path="cartItemCmd.startZone" cssClass="field-validation-error" />
	
	<nemo-tfl:selectList id="finishZone" path="cartItemCmd.endZone" selectList="${travelCardZones}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.endZone}" />
	<form:errors path="cartItemCmd.endZone" cssClass="field-validation-error" />
	
	<to:checkbox id="cartItemCmd.previouslyExchanged"></to:checkbox>
	
	<div id="exchangeDateContainer" style="display:none;">
	
		<to:text id="cartItemCmd.tradedTicket.exchangedDate" mandatory="true"/>
		
		<nemo-tfl:selectList id="tradedTicketTravelCardType" path="cartItemCmd.tradedTicket.travelCardType" selectList="${travelCardTypes}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.tradedTicket.travelCardType}" />
		<form:errors path="cartItemCmd.tradedTicket.travelCardType" cssClass="field-validation-error" />
		
		<nemo-tfl:selectList id="tradedTicketPassengerType" path="cartItemCmd.tradedTicket.passengerType" selectList="${travelCardRates}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.tradedTicket.passengerType}" useManagedContentForMeanings="false"/>
	<form:errors path="cartItemCmd.tradedTicket.passengerType" cssClass="field-validation-error" />
	
	<nemo-tfl:selectList id="tradedTicketDiscountType" path="cartItemCmd.tradedTicket.discountType" selectList="${travelCardDiscountTypes}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.tradedTicket.discountType}" useManagedContentForMeanings="false"/>
	<form:errors path="cartItemCmd.tradedTicket.discountType" cssClass="field-validation-error" />
		
		<to:text id="cartItemCmd.tradedTicket.startDate" mandatory="true"/>
		<to:text id="cartItemCmd.tradedTicket.endDate" />
		
		<nemo-tfl:selectList id="cartItemCmd.tradedTicketStartZone" path="cartItemCmd.tradedTicket.startZone" selectList="${travelCardZones}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.tradedTicket.startZone}" />
		<form:errors path="cartItemCmd.tradedTicket.startZone" cssClass="field-validation-error width100Percent" />
		
		<nemo-tfl:selectList id="cartItemCmd.tradedTicketEndZone" path="cartItemCmd.tradedTicket.endZone" selectList="${travelCardZones}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.tradedTicket.endZone}" />
		<form:errors path="cartItemCmd.tradedTicket.endZone" cssClass="field-validation-error width100Percent" />
	
	
		<div class="clear"></div>
	</div>
	
	<div class="clear"></div>
	<to:secondaryButton id="addTravelCard" buttonCssClass="leftalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.ADD_TRAVEL_CARD %>"/>
</div>
<div style="clear: both"></div>
<to:hidden id="cartType"/>
	
<script>
    var pageName = "${pageName}";
    var shortDatePattern = '<%=DateConstant.SHORT_DATE_PATTERN_JQUERY%>';
</script>
<script src="scripts/addUnlistedProduct.js"></script>