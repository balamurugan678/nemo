<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_ORDER_HISTORY, Page.VIEW_ORDER_DETAILS}%>'/>
<to:headLine/>
<div class="r">
	<div class="blue">
		<c:forEach items="${orders}" var="orderItems" varStatus="i">
			<c:if test="${param.id eq orderItems.order.orderNumber}">
				<spring:message code="viewOrderDetails.orderNumber.heading"/> ${orderItems.order.orderNumber}<br/>
				<spring:message code="viewOrderDetails.dateColon.heading"/> <fmt:formatDate value="${orderItems.order.orderDate}" pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" /><br/>
				<spring:message code="viewOrderDetails.totalAmount.heading"/> ${orderItems.order.formattedTotalAmount}<br/>
				<c:choose>
					<c:when test="${(orderItems.webAccountCreditSettlement != null) && (orderItems.paymentCardSettlement != null && orderItems.paymentCardSettlement.amount!=0)}">
						<spring:message code="viewOrderDetails.paymentType.heading"/> <spring:message code="settlement.combinedSettlements"/> <br/>
						<spring:message code="viewOrderDetails.paymentWeb.heading"/> <nemo-tfl:poundSterlingFormat amount="${orderItems.webAccountCreditSettlement.amount}" /> <br/>
						<spring:message code="viewOrderDetails.paymentCard.heading"/> <nemo-tfl:poundSterlingFormat amount="${orderItems.paymentCardSettlement.amount}" /> <br/>
					</c:when>
					<c:when test="${orderItems.webAccountCreditSettlement != null}">
						<spring:message code="viewOrderDetails.paymentType.heading"/><spring:message code="settlement.webAccountCredit"/><br/>
					</c:when>
					<c:when test="${orderItems.paymentCardSettlement != null}">
						<spring:message code="viewOrderDetails.paymentType.heading"/><spring:message code="settlement.paymentCard"/><br/>
					</c:when>
					<c:otherwise>
						<spring:message code="viewOrderDetails.paymentType.heading"/><spring:message code="settlement.unrecognized"/><br/>
					</c:otherwise>
				</c:choose>
				<spring:message code="viewOrderDetails.statusColon.heading"/> ${orderItems.order.status}<br/>
			</c:if>
		</c:forEach>
		<br/>
	</div>
	<ul style="margin-top: 10px; list-style: none; float: right;">
		<c:forEach items="${orders}" var="orderItems" varStatus="i">
			<c:if test="${param.id eq orderItems.order.orderNumber}">
				<c:forEach items="${orderItems.items}" var="item" varStatus="x">
					<li>
						<td> 
							<div class="blue">
								<span class="column1 bold">
								<spring:message code="viewOrderDetails.autoTopUp" var="autoTopUp"/>
									<c:choose>
										<c:when test="${item.name == autoTopUp}">
											<!-- The amount on AutoTopUp is not charged straight away,
											so it is confusing to have it on the receipt -->
											${item.name} - <nemo-tfl:poundSterlingFormat amount="${item.autoTopUpAmount}"/>
										</c:when>
										<c:otherwise>
											${item.name}
										</c:otherwise>
									</c:choose>
								</span>
								<span class="column2">
								<c:if test="${item.name == autoTopUp && item.autoTopUpAmount==0}">
									<spring:message code="viewOrderDetails.zeroAmount" var="zeroAmount"/>
									<nemo-tfl:poundSterlingFormat amount="${zeroAmount}" />
								</c:if>
								<c:if test="${item.name != autoTopUp}">
									<nemo-tfl:poundSterlingFormat amount="${item.price}"/>							
								</c:if>
								</span>
							</div>
							<div class="white">
								<span class="column1">
									Item details here
									<!-- TODO Add item details -->
								</span>
							</div>
						</td>
					</li>
				</c:forEach>
			</c:if>
		</c:forEach>
	</ul>
</div>
		
<script src="scripts/viewOrderDetails.js"></script>