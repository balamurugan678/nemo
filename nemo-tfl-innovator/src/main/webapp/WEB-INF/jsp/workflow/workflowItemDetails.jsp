<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.PaymentType" %>
<%@ page import="com.novacroft.nemo.tfl.common.constant.RefundConstants" %>
<c:set var="adHocLoad" value="<%=PaymentType.AD_HOC_LOAD%>"/>
<c:set var="anonymousGoodwill" value="<%=RefundConstants.ANONYMOUS_GOODWILL_REFUND_DISPLAY%>"/>

<form:form id="form" class="form-with-tooltips"
	commandName="<%=PageCommand.WORKFLOWITEM_COMMAND%>">
	<to:header id="RefundDetails" />
	<div id="refundCase">
		<table>
			<tbody>
				<tr>
					<td><to:label id="refundCase"></to:label><br>${workflowItemCmd.refundCaseNumber}</td>
					<td><to:label id="totalRefund" /><br /> <nemo-tfl:poundSterlingFormat
							amount="${workflowItemCmd.totalRefund}" /></td>
					<td><to:label id="createdDate" /><br />${workflowItemCmd.createdDate}</td>
					<td><to:label id="refundStatus" /><br />${workflowItemCmd.refundStatus}</td>
				</tr>
				<tr>
					<td><to:label id="reasons"></to:label><br>${workflowItemCmd.reasons}</td>
					<td><to:label id="timeOnQueue" /><br />${workflowItemCmd.timeOnQueue}</td>
					<td><to:label id="agentName" /><br />${workflowItemCmd.agentName}</td>
				</tr>
			</tbody>

		</table>
	</div>
	<c:choose>
		<c:when test="${workflowItemCmd.refundScenarioSubType ne anonymousGoodwill}">
			<to:header id="CustomerDetails" />
			<div>
				<table class="default">
					<tbody>
						<tr>
							<td>
								<table class="defaultnoborder">
									<tbody>
										<tr>
											<td class="tag-left-column"><to:label id="refundName" /></td>
											<td class="tag-middle-column">${workflowItemCmd.refundName}</td>
		
										</tr>
										<tr>
											<td class="tag-left-column"><to:label id="refundUsername" /></td>
											<td class="tag-middle-column">${workflowItemCmd.refundUserName}</td>
		
										</tr>
										<tr>
											<td class="tag-left-column"><to:label id="refundAddress" /></td>
											<td class="tag-middle-column"><c:forEach
													items="${workflowItemCmd.refundAddress}" var="addressLine">
													<c:out value="${addressLine}" />
													<br />
												</c:forEach></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</c:when>
	</c:choose>
	<div>
		<to:header id="RefundScenarioDetails" />
		<table class="default">
			<tbody>
				<tr>
					<td><to:label id="scenarioDetailsForCase" />

						<table class="defaultnoborder">
							<tbody>
								<tr>
									<td class="tag-left-column"><to:label
											id="refundScenarioType" /></td>
									<td class="tag-middle-column">${workflowItemCmd.refundScenarioType}</td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label
											id="refundScenarioSubType" /></td>
									<td class="tag-middle-column">${workflowItemCmd.refundScenarioSubType}</td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label
											id="refundablePeriod" /></td>
									<td class="tag-middle-column">${workflowItemCmd.refundablePeriodStartDate}</td>

								</tr>
							</tbody>
						</table>
						<div class="clear">&nbsp;</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div>
		<to:header id="PaymentItemDetails" />
		<table class="default">
			<tbody>
				<tr>
					<td>
						<table class="defaultnoborder">
							<tbody>
								<tr>
									<td class="tag-left-column"><to:label id="items" /></td>
									<td class="tag-middle-column">
										<table class="default">
											<thead>
												<tr class="grid_heading">
													<th><spring:message
															code="${pageName}${pageName ne ' ' ? '.' : ''}itemName" /></th>
													<th><spring:message
															code="${pageName}${pageName ne ' ' ? '.' : ''}startZone" /></th>
													<th><spring:message
															code="${pageName}${pageName ne ' ' ? '.' : ''}endZone" /></th>
													<th><spring:message
															code="${pageName}${pageName ne ' ' ? '.' : ''}calculationBasis" /></th>
													<th><spring:message
															code="${pageName}${pageName ne ' ' ? '.' : ''}price" /></th>
													<th><spring:message
															code="refundAmount.tableheader" /></th>			
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${workflowItemCmd.refundItems}"
													var="refundItem">
													<tr class="grid_row">
														<td>${refundItem.name}</td>
														<td>${refundItem.startZone}</td>
														<td>${refundItem.endZone}</td>
														<td>${refundItem.calculationBasis}</td>
														<td>${refundItem.price}</td>
														<td>${refundItem.ticketRefundAmount}</td>
													</tr>
												</c:forEach>
											</tbody>

										</table>
									</td>
								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="totalTicketPrice" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowItemCmd.totalTicketAmount}" /></td>
								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="goodwillpaymentitems" /></td>
									<td class="tag-middle-column">
										<table class="default">
											<thead>
												<tr class="grid_heading">													
													<to:tableHeader id="name" />
													<to:tableHeader id="price" />
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${workflowItemCmd.goodwillPaymentItems}"
													var="goodwillItem">
													<tr class="grid_row">
														<td>${goodwillItem.goodwillPaymentReason}</td>
														<td><nemo-tfl:poundSterlingFormat amount= "${goodwillItem.price}"/></td>
													</tr>
												</c:forEach>
											</tbody>

										</table>
									</td>
								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="ticketDeposit" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowItemCmd.ticketDeposit}" /></td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="payAsYouGoCredit" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowItemCmd.payAsYouGoCredit}" /></td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="adminFee" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowItemCmd.ticketAdminFee}" /></td>

								</tr>							
							</tbody>
						</table>

					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div>
		<to:header id="PaymentDetails" />
		<table class="default">
			<tbody>
				<tr>
					<td><to:label id="paymentDetailsForcase" /></td>
				</tr>
				<tr>
					<td>
						<table class="defaultnoborder">
							<tbody>
								<tr>

									<td class="tag-left-column"><to:label id="paymentMethod" /></td>
									<td class="tag-middle-column">${workflowItemCmd.paymentMethod}</td>

								</tr>
								<c:choose>
									<c:when test="${workflowItemCmd.workflowItem.refundDetails.paymentType eq adHocLoad}">
											<tr>
												<td class="tag-left-column"><to:label id="targetCardNumber" /></td>
												<td class="tag-middle-column">${workflowItemCmd.targetCardNumber}</td>
											</tr>
											<tr>
												<td class="tag-left-column"><to:label id="station" /></td>
												<td class="tag-middle-column">${workflowItemCmd.station}</td>
											</tr>
									</c:when>
								</c:choose>
								<tr>

									<td class="tag-left-column"><to:label id="paymentName" /></td>
									<td class="tag-middle-column">${workflowItemCmd.paymentName}</td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="paymentAddress" /></td>
									<td class="tag-middle-column"><c:forEach
											items="${workflowItemCmd.paymentAddress}" var="addressLine">
											<c:out value="${addressLine}" />
											<br />
										</c:forEach></td>
								</tr>

								<tr>
									<td class="tag-left-column"><to:label id="paymentAmount" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowItemCmd.totalRefund}" /></td>

								</tr>

							</tbody>
						</table>

					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<c:choose>
		<c:when test="${not empty workflowItemCmd.workflowItem.agent}">
			<to:primaryButton buttonType="submit" id="edit"
				targetAction="<%=PageParameterValue.EDIT%>" />
		</c:when>
	</c:choose>
</form:form>