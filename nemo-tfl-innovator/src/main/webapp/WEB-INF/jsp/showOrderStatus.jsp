<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<spring:message var="webAccountCreditText" code="webAccountCredit.text"/>

<div >
    <c:choose>
    	<c:when test="${cartCmd.paymentType eq webAccountCreditText}">
    		<to:head3 id="orderStatus" headingCssClass="space-before-head"/>
    		<to:label id="orderId" />${cartCmd.cartDTO.order.orderNumber}
			<div class="clear"></div>
			<to:label id="orderStatus" />${cartCmd.cartDTO.order.status}
    	</c:when>
    	<c:otherwise />
    </c:choose>
	
</div>
<div class="clear"></div>
<script src="scripts/payment.js"></script>