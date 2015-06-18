<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<form:errors path="travelCardRefundLimit" cssClass="field-validation-error" />
<div>
	
	<to:checkbox id="cartItemCmd.backdated"></to:checkbox>
	<div id="backdatedReasonContainer">
		<div id="backdatedWarning" class="backdatedRefundWarningDiv">
			<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}BackdateCeilingWarning"/>
		</div>
		<nemo-tfl:selectList id="backdatedReasonList" path="cartItemCmd.backdatedRefundReasonId" selectList="${BackdatedRefundTypes}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.backdatedRefundReasonId}" />
		
		<div id="backdatedOtherReasonContainer">
	    	<to:text id="cartItemCmd.backdatedOtherReason" mandatory="true" showLabel="true"/>
		</div>
		
		<form:errors path="cartItemCmd.backdatedRefundReasonId" cssClass="field-validation-error" />
		<br/>
		<div>
		    <div id="dateOfCanceAndSurrenderValue">
		    	<to:text id="cartItemCmd.dateOfCanceAndSurrender" mandatory="true" showLabel="true" cssClass="dateOfRefundValue"/>
		    </div>
		</div>
	</div>
		
	<to:checkbox id="cartItemCmd.deceasedCustomer"></to:checkbox>
	<div id="deceasedCustomerContainer">
	    <to:text id="cartItemCmd.dateOfLastUsage" mandatory="true" showLabel="true" cssClass="dateOfRefundValue"/>
	</div>
	<div class="clear"></div>
	
</div>
<div style="clear: both"></div>
<to:hidden id="cartType"/>
	
<script>
    var pageName = "${pageName}";
</script>
<script src="scripts/addCancelAndSurrenderUnlistedProduct.js"></script>
<script src="scripts/cancelAndSurrender.js"></script>