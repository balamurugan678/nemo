<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div class="accordian-wrapper">
 <div id="addTradedTravelCardSection">	
	<to:label id="cartItemCmd.previouslyExchanged"/>
	<to:checkbox id="cartItemList[${status.index}].previouslyExchanged" cssClass="previouslyExchangedClass" showLabel="false"></to:checkbox>
 	<div id="exchangeDateContainer${status.index}" style="display:none;">
 	
		<to:label id="tradedTicket.exchangedDate"/>
		<to:text id="cartItemList[${status.index}].tradedTicket.exchangedDate"  showLabel = "false"/>
		
		<to:label id="tradedTicket.travelCardType"/>
		<nemo-tfl:selectList id="tradedTicketTravelCardType${status.index}" showLabel = "false" cssClass= "tradedTicketTravelCardTypeClass" path="cartItemList[${status.index}].tradedTicket.travelCardType" selectList="${travelCardTypes}" mandatory="true" selectedValue="${cartCmd.cartItemList[status.index].tradedTicket.travelCardType}" />
		<form:errors path="cartItemList[${status.index}].tradedTicket.travelCardType" cssClass="field-validation-error" />
		
		<to:label id="tradedTicket.passengerType"/>
		<nemo-tfl:selectList id="tradedTicketPassengerType${status.index}" showLabel = "false" path="cartItemList[${status.index}].tradedTicket.passengerType" selectList="${travelCardRates}" mandatory="true" selectedValue="${cartCmd.cartItemList[status.index].tradedTicket.passengerType}" useManagedContentForMeanings="false"/>
		<form:errors path="cartItemList[${status.index}].tradedTicket.passengerType" cssClass="field-validation-error" />
		
		<to:label id="tradedTicket.discountType"/>
		<nemo-tfl:selectList id="tradedTicketDiscountType${status.index}" showLabel = "false" path="cartItemList[${status.index}].tradedTicket.discountType" selectList="${travelCardDiscountTypes}" mandatory="true" selectedValue="${cartCmd.cartItemList[status.index].tradedTicket.discountType}" useManagedContentForMeanings="false"/>
		<form:errors path="cartItemList[${status.index}].tradedTicket.discountType" cssClass="field-validation-error" />
		
		<to:text id="cartItemList[${status.index}].tradedTicket.startDate"/>
		<to:text id="cartItemList[${status.index}].tradedTicket.endDate" />
		
		<nemo-tfl:selectList id="cartItemList[${status.index}].tradedTicket.startZone"  path="cartItemList[${status.index}].tradedTicket.startZone" selectList="${travelCardZones}" mandatory="true" selectedValue="${cartCmd.cartItemList[status.index].tradedTicket.startZone}" />
		<form:errors path="cartItemList[${status.index}].tradedTicket.startZone" cssClass="field-validation-error width100Percent" />
		
		<nemo-tfl:selectList id="cartItemList[${status.index}].tradedTicket.endZone"  path="cartItemList[${status.index}].tradedTicket.endZone" selectList="${travelCardZones}" mandatory="true" selectedValue="${cartCmd.cartItemList[status.index].tradedTicket.endZone}" />
		<form:errors path="cartItemList[${status.index}].tradedTicket.endZone" cssClass="field-validation-error width100Percent" />
	
		<div class="clear"></div>
		<to:button id="addTradedTicket" secondaryId="addTradedTicket${cartItem.id}" buttonCssClass=" addTradedTicket buttonastext leftalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.ADD_TRADED_TICKET %>"/>
	</div>
	</div>
</div>