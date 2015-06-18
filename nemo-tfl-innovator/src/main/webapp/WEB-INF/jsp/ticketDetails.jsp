<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<div id="ticketDetails">
	<to:header id="ticketDetails" />
	<to:label id="cardDepositPrice" />
	<nemo-tfl:poundSterlingFormat
		amount="${fulfilmentCmd.cardDepositPrice}" />
	<br/><br/>
	<to:label id="shippingMethod" />${fulfilmentCmd.shippingMethod}
	<br/><br/>
	<to:label id="paymentMethod" />${fulfilmentCmd.paymentMethod}
	<br/><br/><br/>
	<table border="0" width="75%">
		<thead>
			<tr>
			  	<th id="${pageName}${pageName ne ' ' ? '.' : ''}productName" class="bold" align="left">
			  		<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}productName.label"/>
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
					<td colspan = "3"><to:label id="price" showColon="false"/></td>
					<td align="right"><nemo-tfl:poundSterlingFormat amount="${orderItem.price}" /></td>
				</tr>
			</c:if>
		</c:forEach>
		<c:forEach items="${fulfilmentCmd.order.orderItems}" var="orderItem"
			varStatus="status">
			<c:set var="itemSubclass" value="${orderItem['class'].simpleName}" />
			<c:if test="${itemSubclass == 'AutoTopUpConfigurationItemDTO' }">
				<tr>
					<td colspan = "3"><to:label id="autoTopUpAmount" showColon="false"/></td><td align="right">(<nemo-tfl:poundSterlingFormat
							amount="${orderItem.autoTopUpAmount}" />)
					</td>
				</tr>
    		</c:if>
		</c:forEach>
	</table>
</div>

