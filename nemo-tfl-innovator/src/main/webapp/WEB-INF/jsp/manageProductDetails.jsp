<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
	<to:head4 id="productDetails" />
	<table  class="receiptFont" border="0" cellspacing="5" width="100%">
		<thead>
			<tr>
			  	<th id="${pageName}${pageName ne ' ' ? '.' : ''}travelCard" class="bold" align="left">
			  		<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}travelCard.tableheader"/>
			  	</th> 
				<to:tableHeader id="startDate" />
				<to:tableHeader id="endDate" />
				<th id="${pageName}${pageName ne ' ' ? '.' : ''}price" class="bold" align="right">
			  		<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}price.tableheader"/>
			  	</th> 
			</tr>
		</thead>
		<c:forEach items="${fulfilmentCmd.order.orderItems}" var="orderItem"
			varStatus="status">
			<c:set var="itemSubclass" value="${orderItem['class'].simpleName}" />
			<c:if test="${itemSubclass == 'ProductItemDTO'}">
				<tr>
					<td>${orderItem.name}</td>
					<td align="center"><fmt:formatDate
							pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
							value="${orderItem.startDate}" /></td>
					<td align="center"><fmt:formatDate
							pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
							value="${orderItem.endDate}" /></td>
					<td align="right"><nemo-tfl:poundSterlingFormat amount="${orderItem.price}" /></td>
				</tr>
			</c:if>
		</c:forEach>
		<c:forEach items="${fulfilmentCmd.order.orderItems}" var="orderItem"
			varStatus="status">
			<c:set var="itemSubclass" value="${orderItem['class'].simpleName}" />
			<c:if test="${itemSubclass == 'PayAsYouGoItemDTO'}">
				<tr>
					<td colspan = "3"><to:label id="payAsYouGoItem" showColon="false"/></td>
					<td align="right"><nemo-tfl:poundSterlingFormat amount="${orderItem.price}" /></td>
				</tr>
			</c:if>
		</c:forEach>
		<c:forEach items="${fulfilmentCmd.order.orderItems}" var="orderItem"
			varStatus="status">
			<c:set var="itemSubclass" value="${orderItem['class'].simpleName}" />
			<c:if test="${itemSubclass == 'AutoTopUpConfigurationItemDTO' }">
				<tr>
					<td colspan = "4"><to:label id="autoTopUp" showColon="false"/>(<nemo-tfl:poundSterlingFormat
							amount="${orderItem.autoTopUpAmount}" />)
					</td>
				</tr>
    		</c:if>
		</c:forEach>
		<tr>
			<td colspan = "3" align="right"><to:label id="subTotal" showColon="false"/></td><td align="right"><nemo-tfl:poundSterlingFormat amount="${fulfilmentCmd.subTotal}" /></td>
		</tr>
		<tr>
			<td colspan = "3" align="right"><to:label id="cardDepositPrice" showColon="false"/></td><td align="right"><nemo-tfl:poundSterlingFormat amount="${fulfilmentCmd.cardDepositPrice}" /></td>
		</tr>
		<tr>
			<td colspan = "3" align="right"><to:label id="delivery" showColon="false"/></td><td align="right"><nemo-tfl:poundSterlingFormat amount="${fulfilmentCmd.shippingCost}" /></td>
		</tr>
		<c:if test="${fulfilmentCmd.webCreditDiscount > 0}">
		<tr>
			<td colspan = "3" align="right"><b><to:label id="webCreditDiscount" showColon="false"/></b></td><td align="right"><b><nemo-tfl:poundSterlingFormat amount="${fulfilmentCmd.webCreditDiscount}" /></b></td>
		</tr>
		</c:if>
		<tr>
			<td colspan = "3" align="right"><b><to:label id="totalCharged" showColon="false"/></td><td align="right"><b><nemo-tfl:poundSterlingFormat amount="${fulfilmentCmd.totalPaid}" /></b></td>
		</tr>
	</table>
</div>