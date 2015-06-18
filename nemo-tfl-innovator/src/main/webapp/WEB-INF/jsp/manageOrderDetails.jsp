<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
	<to:head4 id="orderSection" />
	<table class="receiptFont">
		<tr>
			<td><to:label id="orderNumber" /></td>
			<td>${ fulfilmentCmd.order.orderNumber}</td>
		</tr>
		<tr>
			<td><to:label id="orderDate" /></td>
			<td><fmt:formatDate	pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
							value="${ fulfilmentCmd.order.orderDate}" /></td>
		</tr>
		<tr>
			<td><to:label id="oysterCardNumber" /></td>
			<td>${ fulfilmentCmd.fulfiledOysterCardNumber}</td>
		</tr>
	</table>
</div>