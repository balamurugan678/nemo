<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
	<to:head4 id="customer" />
	<c:if test="${fulfilmentCmd.shippingMethod == 'First class'}">
		<to:label id="firstClass" />
	</c:if>
	<c:if test="${fulfilmentCmd.shippingMethod == 'Recorded Delivery'}">
		<to:label id="recordedDelivery" />
	</c:if>
	<jsp:include page="manageCustomerAddress.jsp"/>
</div>
	