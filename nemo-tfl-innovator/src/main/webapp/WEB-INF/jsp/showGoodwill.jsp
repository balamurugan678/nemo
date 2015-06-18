<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<spring:message var="goodwill" code="goodwill.text"/>
<spring:message var="goodwillOther" code="goodwillOther.text"/>

<c:set var="goodwillPaymentItemCount" value="0" />
<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
	<c:if test="${cartItem['class'].simpleName == 'GoodwillPaymentItemDTO'}">
		<c:set var="goodwillPaymentItemCount" value="${goodwillPaymentItemCount + 1}" />
	</c:if>
</c:forEach>

<c:if test="${goodwillPaymentItemCount gt 0}">
	<table id="goodwillTable">
		<thead>
			<tr>
				<th></th>
				<to:tableHeader id="name" />
				<to:tableHeader id="price" />
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
				<c:if test="${cartItem['class'].simpleName == 'GoodwillPaymentItemDTO'}">
					<c:set var="cartIndex" value="cartItems[${status.index}]" />
					<tr>
						<td><to:button id="delete"
								secondaryId="deleteGoodwill${cartItem.id}"
								buttonCssClass="deleteGoodwill buttonastext" buttonType="submit"
								targetAction="<%= PageParameterValue.DELETE_GOODWILL %>" /> <input
							id="cartItemId" name="cartItemId" value="${cartItem.id}"
							type="hidden" /></td>
						<td>${cartItem.name}</td>
						<td><nemo:poundSterlingFormat amount="${cartItem.price}" /></td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</c:if>
<c:if test="${goodwillPaymentItemCount le 0}">
	<to:label id="noGoodwillAdded" showColon="false" />
</c:if>