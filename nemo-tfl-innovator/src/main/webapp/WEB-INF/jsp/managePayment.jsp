<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil"%>
<div >
    <to:head3 id="payment" headingCssClass="space-before-head"/>

    <nemo-tfl:selectList id="paymentType" path="paymentType" selectList="${PaymentType}" mandatory="true" selectedValue="${cartCmd.paymentType}" useManagedContentForMeanings="true" />
    <form:errors path="paymentType" cssClass="field-validation-error"/>
    <div class="clear"></div>
    
    <to:label id="total"/>
	<c:choose>
		<c:when test="${CartUtil.isDestroyedOrFaildCartType(cartDTO.cartType) == Boolean.TRUE }">
			<nemo-tfl:poundSterlingFormat amount="${cartDTO.cartRefundTotalWithDeposit}" />
		</c:when>
		<c:otherwise>
			<nemo-tfl:poundSterlingFormat amount="${cartDTO.cartRefundTotal}" />
		</c:otherwise>
	</c:choose>
    
	<form:errors path="cartDTO.cartRefundTotal" cssClass="field-validation-error"  htmlEscape="false"/>
	
	<div class="clear"></div>

    <div id="paymentTypeWebAccountCredit">
    	<to:head4 id="paymentTypeWebAccountCredit" headingCssClass="space-before-head"/>
    	<to:label id="webCreditAvailableAmount"/>
    	<nemo-tfl:poundSterlingFormat amount="${-cartCmd.webCreditAvailableAmount}"/>
    </div>
    
    <div id="paymentTypeAdhocLoad">
		<to:head4 id="paymentTypeAdhocLoad" headingCssClass="space-before-head"/>
		<nemo-tfl:selectList id="selectCard" path="targetCardNumber" selectList="${cardSelectList}" mandatory="true"
			useManagedContentForMeanings="false" selectedValue="${cartCmd.targetCardNumber}"/>
		<form:errors path="targetCardNumber" cssClass="field-validation-error"/>
        <to:loadingIcon />
		<nemo-tfl:selectList id="selectStation" path="stationId" selectList="${stationSelectList}" mandatory="true"
			useManagedContentForMeanings="false" selectedValue="${cartCmd.stationId}"/>
		<form:errors path="stationId" cssClass="field-validation-error"/>
	</div>
	   
    
    <div id="paymentTypePaymentCard">
    	<to:head4 id="paymentTypePaymentCard" headingCssClass="space-before-head"/>
    </div>

    <div id="paymentTypeBACS">
    	<to:head4 id="paymentTypeBACS" headingCssClass="space-before-head"/>
    	<to:head4 id="paymentTypeBACSPayeeDetails" headingCssClass="space-before-head"/>
   		<to:text id="payeeSortCode" mandatory="true"/>
    	<to:text id="payeeAccountNumber" mandatory="true"/>
   	</div>
    	
    <div id="paymentTypeCheque">
    	<to:head4 id="paymentTypeCheque" headingCssClass="space-before-head"/>
    	<to:head4 id="paymentTypeChequePayeeDetails" headingCssClass="space-before-head"/>
   	</div>
   	
    <div id="bacsAndChequeAddress">	
    	<to:text id="firstName" mandatory="true"/>
    	<to:text id="initials"/>
    	<to:text id="lastName" mandatory="true"/>
    	<to:checkbox id="overwriteAddress" />
    	<to:text id="payeeAddress.houseNameNumber" value="${cartCmd.payeeAddress.houseNameNumber}" mandatory="true"/>
   		<to:text id="payeeAddress.street" value="${cartCmd.payeeAddress.street}" mandatory="true"/>
    	<to:text id="payeeAddress.town" value="${cartCmd.payeeAddress.town}" mandatory="true"/>
    	<to:text id="payeeAddress.postcode" value="${cartCmd.payeeAddress.postcode}" mandatory="true"/>
    	<to:text id="payeeAddress.county" value="${cartCmd.payeeAddress.county}"/>
    	<nemo-tfl:selectList id="selectCountry" path="payeeAddress.country" selectList="${countries}" showPlaceholder="true" mandatory="true" useManagedContentForMeanings="false" selectedValue="${cartCmd.payeeAddress.country.code}"/>
    	   	
   	</div>
</div>
<div class="clear"></div>
<script src="scripts/payment.js"></script>
<script type="text/javascript">
var contextPath = "${pageContext.request.contextPath}";
</script>