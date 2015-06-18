<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<nemo-tfl:breadcrumbs
	pageNames='<%=new String[] { Page.DASHBOARD, Page.VIEW_ORDER_HISTORY }%>' />
<to:headLine />
<div class="r">
	<div class="main">
		<div
			class="message-container reminder-container orderHistoryMessageContainer">
			<h4 class="title">
				<spring:message code="pleaseNote.text" />
			</h4>

			<div class="content">
				<spring:message code="${pageName}.instruction.text" />
			</div>
		</div>
		<div class="dataTable-container">
			<table class="journey-table transaction-table orderHistory-table"
				cellpadding=0 cellspacing=1 id="orders" width="100%">
				<thead>
					<tr>
						<th><spring:message code="viewOrderHistory.order.heading" /></th>
						<th><spring:message code="viewOrderHistory.order.heading" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${orderDays}" var="orders" varStatus="z">
						<c:forEach items="${orders.orderItems}" var="orderItems"
							varStatus="i">
							<tr>
								<td></td>
								<td>
									<div>
										<a
											href="ViewOrderDetails.htm?id=${orderItems.order.orderNumber}"
											class="link-button transparent-bgcolor zero-margin-bottom">

											<span class="oo-journey-table-column  first-column emphasis">
												<fmt:formatDate
													pattern="<%= DateConstant.DAY_WEEK_DATE_PATTERN %>"
													value="${orders.orderDate}" />
										</span> <span class="oo-journey-table-column  first-column"> <c:choose>
													<c:when test="${orderItems.items.size() > 1 }">
														<span>${orderItems.items.size()}&nbsp;<spring:message
																code="viewOrderHistory.orderitem.text" /></span>
													</c:when>
													<c:otherwise>
														<span>${orderItems.items[0].getName()}</span>
													</c:otherwise>
												</c:choose> <span class="block csc-subtitle">
													${orderItems.order.orderNumber} </span>
										</span> <span
											class="oo-price oo-journey-table-column second-column spend">
												<span class="csc-payment-price">
													${orderItems.order.formattedTotalAmount} </span>
												<div class="greyText">
													<c:choose>
														<c:when test="${(orderItems.order.totalAmount < 0)}">
															<spring:message code="settlement.refund" />
														</c:when>
														<c:when
															test="${(orderItems.webAccountCreditSettlement != null) && (orderItems.paymentCardSettlement != null && orderItems.paymentCardSettlement.amount!=0)}">
															<spring:message code="settlement.combinedSettlements" />
														</c:when>
														<c:when
															test="${(orderItems.webAccountCreditSettlement != null)}">
															<spring:message code="settlement.webAccountCredit" />
														</c:when>
														<c:when
															test="${(orderItems.paymentCardSettlement != null)}">
															<spring:message code="settlement.paymentCard" />
														</c:when>
														<c:otherwise>
															<spring:message code="settlement.unrecognized" />
														</c:otherwise>
													</c:choose>
												</div>
										</span>
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<tiles:insertAttribute name="myAccount" />
</div>

<script src="scripts/viewOrderHistory.js"></script>