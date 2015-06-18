<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.constant.PaymentType" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<spring:message var="webAccountCreditText" code="webAccountCredit.text"/>
<spring:message var="adhocLoadText" code="adhocLoad.text"/>
<spring:message var="chequeText" code="cheque.text"/>
<spring:message var="bacsText" code="bacs.text"/>
<spring:message var="paymentCardText" code="paymentcard.text"/>

<div >
    <to:head3 id="paymentStatus" headingCssClass="space-before-head"/>
    <div class="clear"></div>

    <spring:eval expression="cartCmd.paymentType == T(com.novacroft.nemo.tfl.common.constant.PaymentType).WEB_ACCOUNT_CREDIT.code()" var="isWebAccount" /> 
    <spring:eval expression="cartCmd.paymentType == T(com.novacroft.nemo.tfl.common.constant.PaymentType).AD_HOC_LOAD.code()" var="isAdHocLoad" />
    <spring:eval expression="cartCmd.paymentType == T(com.novacroft.nemo.tfl.common.constant.PaymentType).BACS.code()" var="isBACS" />
    <spring:eval expression="cartCmd.paymentType == T(com.novacroft.nemo.tfl.common.constant.PaymentType).CHEQUE.code()" var="isCheque" />
    <spring:eval expression="cartCmd.paymentType == T(com.novacroft.nemo.tfl.common.constant.PaymentType).PAYMENT_CARD.code()" var="isPaymentCard" />
	
	<c:choose>
		<c:when test="${CartUtil.isDestroyedOrFaildCartType(cartCmd.cartType) == Boolean.TRUE}">
			 <c:set var="totalAmount" value="${cartCmd.cartDTO.cartRefundTotal + cartCmd.cartDTO.cardRefundableDepositAmount}"/>
		</c:when>
		<c:otherwise>
			<c:set var="totalAmount" value="${cartCmd.cartDTO.cartRefundTotal}"/>
		</c:otherwise>
	</c:choose>
    
    <c:choose>
    	<c:when test="${isWebAccount}">
			<to:label id="paymentType"/>${webAccountCreditText}
			<div class="clear"></div>
    		<to:label id="webAccountCreditBeforeRefund" /><nemo-tfl:poundSterlingFormat amount="${-cartCmd.webCreditAvailableAmount}"/>
    		<div class="clear"></div>
    		<to:label id="amountAddedToWebAccountCredit" /><nemo-tfl:poundSterlingFormat amount="${totalAmount}"/>
    		<div class="clear"></div>
    		<to:label id="webAccountCreditAfterRefund" /><nemo-tfl:poundSterlingFormat amount="${-cartCmd.webCreditAvailableAmount + totalAmount}"/>
    		<div class="clear"></div>
    	</c:when>
    	<c:when test="${isPaymentCard}">
			<to:label id="paymentType"/>${paymentCardText}
			<div class="clear"></div>
    		<to:label id="amountPayedViaPaymentCard" /><nemo-tfl:poundSterlingFormat amount="${totalAmount}"/>
    		<div class="clear"></div>
    	</c:when>
    	<c:when test="${isAdHocLoad}"> 
			<to:label id="paymentType"/>${adhocLoadText}
			<div class="clear"></div> 
    		<to:label id="targetOysterCardNumber" />${cartCmd.targetCardNumber}
    		<div class="clear"></div>
    		<to:label id="pickUpStation" />${cartCmd.stationName}
    		<div class="clear"></div>
    		<to:label id="oysterCreditBeforeRefund" /><nemo-tfl:poundSterlingFormat amount="${cartCmd.previousCreditAmountOnCard}"/>
    		<div class="clear"></div>
    		<to:label id="amountAddedToOysterCredit" /><nemo-tfl:poundSterlingFormat amount="${totalAmount}"/>
    		<div class="clear"></div>
    		<to:label id="oysterCreditAfterRefund" /><nemo-tfl:poundSterlingFormat amount="${cartCmd.previousCreditAmountOnCard + totalAmount}"/>
    		<div class="clear"></div>
    	</c:when>
    	<c:when test="${isBACS}">  
    		<to:label id="paymentType"/>${bacsText}
    		<div class="clear"></div>
    		<to:label id="payeeSortCode" />${cartCmd.payeeSortCode}
    		<div class="clear"></div>
    		<to:label id="payeeAccountNumber" />${cartCmd.payeeAccountNumber}
    		<div class="clear"></div>
    		<to:label id="payeeFirstName" />${cartCmd.firstName}
    		<div class="clear"></div>
    		<to:label id="payeeInitials" />${cartCmd.initials}
    		<div class="clear"></div>
    		<to:label id="payeeLastName" />${cartCmd.lastName}
    		<div class="clear"></div>
    		<to:label id="payeeAddressNumber" />${cartCmd.payeeAddress.houseNameNumber}
    		<div class="clear"></div>
    		<to:label id="payeeAddressStreet" />${cartCmd.payeeAddress.street}
    		<div class="clear"></div>
    		<to:label id="payeeAddressTown" />${cartCmd.payeeAddress.town}
    		<div class="clear"></div>
    		<to:label id="payeeAddressPostcode" />${cartCmd.payeeAddress.postcode}
    		<div class="clear"></div>
    		<to:label id="payeeAddressCounty" />${cartCmd.payeeAddress.county}
    		<div class="clear"></div>
    		<to:label id="payeeAddressCountry" />${cartCmd.payeeAddress.country.name}
    		<div class="clear"></div>
    		<c:choose>
	    		<c:when test="${cartCmd.overwriteAddress}">
	    			<to:paragraph id="addressOverwritten" />
	    			<div class="clear"></div>
	    		</c:when>
    		</c:choose>
    		<to:label id="amountPayedViaBACS" /><nemo-tfl:poundSterlingFormat amount="${totalAmount}"/>
    		<div class="clear"></div>
    	</c:when>
    	<c:when test="${isCheque}">  
    		<to:label id="paymentType"/>${chequeText}
    		<div class="clear"></div>
    		<to:label id="payeeFirstName" />${cartCmd.firstName}
    		<div class="clear"></div>
    		<to:label id="payeeInitials" />${cartCmd.initials}
    		<div class="clear"></div>
    		<to:label id="payeeLastName" />${cartCmd.lastName}
    		<div class="clear"></div>
    		<to:label id="payeeAddressNumber" />${cartCmd.payeeAddress.houseNameNumber}
    		<div class="clear"></div>
    		<to:label id="payeeAddressStreet" />${cartCmd.payeeAddress.street}
    		<div class="clear"></div>
    		<to:label id="payeeAddressTown" />${cartCmd.payeeAddress.town}
    		<div class="clear"></div>
    		<to:label id="payeeAddressPostcode" />${cartCmd.payeeAddress.postcode}
    		<div class="clear"></div>
    		<to:label id="payeeAddressCounty" />${cartCmd.payeeAddress.county}
    		<div class="clear"></div>
    		<to:label id="payeeAddressCountry" />${cartCmd.payeeAddress.country.name}
    		<div class="clear"></div>
    		<c:choose>
	    		<c:when test="${cartCmd.overwriteAddress}">
	    			<to:paragraph id="addressOverwritten" />
	    			<div class="clear"></div>
	    		</c:when>
    		</c:choose>
    		<to:label id="amountPayedViaCheque" /><nemo-tfl:poundSterlingFormat amount="${totalAmount}"/>
    		<div class="clear"></div>
    	</c:when>
    	<c:otherwise>
    		<to:paragraph id="${cartCmd.paymentType}" />
    		<to:paragraph id="noPaymentTypeSelected" />
    	</c:otherwise>
    </c:choose>
   
	<div class="clear"></div>
</div>
<div class="clear"></div>
<script src="scripts/payment.js"></script>