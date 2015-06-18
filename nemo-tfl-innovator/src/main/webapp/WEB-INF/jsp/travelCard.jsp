<%@page import="com.novacroft.nemo.tfl.common.constant.ProductItemType"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<div class="r" style="padding-top: 20px;">
	<input type="hidden" id="travelCardTicketType" name="cartItemCmd.ticketType" value="<%= ProductItemType.TRAVEL_CARD.databaseCode() %>" />
	<div class="box borderless">
		<to:head2 id="travelCardOptions" />
		<form:errors cssClass="field-validation-error" />
		
		<nemo-tfl:selectList id="travelCardType" path="cartItemCmd.travelCardType" selectList="${travelCardTypes}"
			mandatory="true" selectedValue="${cartCmd.cartItemCmd.travelCardType}" />
		<form:errors path="cartItemCmd.travelCardType" cssClass="field-validation-error" />
		
		<nemo-tfl:selectList id="startZone" path="cartItemCmd.startZone" selectList="${travelCardZones}"
			mandatory="true" selectedValue="${cartCmd.cartItemCmd.startZone}" />
		<form:errors path="cartItemCmd.startZone" cssClass="field-validation-error" />
		
		<nemo-tfl:selectList id="endZone" path="cartItemCmd.endZone" selectList="${travelCardZones}" mandatory="true"
			selectedValue="${cartCmd.cartItemCmd.endZone}" />
		<form:errors path="cartItemCmd.endZone" cssClass="field-validation-error" />
		
		<nemo-tfl:selectList id="startDate" path="cartItemCmd.startDate" selectList="${startDates}" mandatory="true"
			selectedValue="${cartCmd.cartItemCmd.startDate}" useManagedContentForMeanings="false" />
		<form:errors path="cartItemCmd.startDate" cssClass="field-validation-error" />
		
		<div id="endDateSection">
			<to:text id="cartItemCmd.endDate" cssClass="date"></to:text>
		</div>
		
		<nemo-tfl:selectList id="emailReminder" path="cartItemCmd.emailReminder" selectList="${basketEmailReminders}"
			mandatory="true" selectedValue="${cartCmd.cartItemCmd.emailReminder}" showPlaceholder="false" />
		<form:errors path="cartItemCmd.emailReminder" cssClass="field-validation-error" />
		
		<div class="form-with-tooltips" style="clear: both;">
			<to:secondaryButton id="travelCardContinue" buttonType="submit"
				targetAction="<%= PageParameterValue.ADD_TRAVEL_CARD_ITEM_TO_CART %>" />
		</div>
	</div>
</div>
<script>
    var pageName = "${pageName}";
    var shortDatePattern = '<%=DateConstant.SHORT_DATE_PATTERN_JQUERY%>';
</script>
<script src="scripts/travelCard.js"></script>