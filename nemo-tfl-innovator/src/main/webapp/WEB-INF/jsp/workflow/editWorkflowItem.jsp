<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.PaymentType" %>
<%@ page import="com.novacroft.nemo.tfl.common.constant.RefundConstants" %>
<c:set var="adHocLoad" value="<%=PaymentType.AD_HOC_LOAD%>"/>
<c:set var="anonymousGoodwill" value="<%=RefundConstants.ANONYMOUS_GOODWILL_REFUND_DISPLAY%>"/>

<form:form id="form" class="form-with-tooltips"
	commandName="<%=PageCommand.WORKFLOW_EDIT_COMMAND%>">
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
											<td class="tag-left-column"><to:label id="refundName" mandatory="true" /></td>
											<td class="tag-middle-column">
												<nemo-tfl:selectList id="title" path="title" selectList="${titles}" mandatory="false" selectedValue="${workflowEditCmd.title}" size="1" showLabel="false"/>
												<div class="clear">&nbsp;</div> <to:text id="firstName"
													showLabel="false" />
												<div class="clear">&nbsp;</div> <to:text id="initials"
													showLabel="false" />
												<div class="clear">&nbsp;</div> <to:text id="lastName"
													showLabel="false" /></td>
		
										</tr>
										<tr>
											<td class="tag-left-column"><to:label id="refundUsername" mandatory="true" /></td>
											<td class="tag-middle-column"><to:text id="username"
													showLabel="false" /></td>
		
										</tr>
										<tr>
											<td class="tag-left-column"><to:label id="refundAddress" mandatory="true" /></td>
											<td class="tag-middle-column"><to:text
													id="houseNameNumber" showLabel="false" />
												<div class="clear">&nbsp;</div> <to:text id="street"
													showLabel="false" />
												<div class="clear">&nbsp;</div> <to:text id="town"
													showLabel="false" />
													<div class="clear">&nbsp;</div> <to:text id="postcode"
													showLabel="false" />									
												<div class="clear">&nbsp;</div> 
												<nemo-tfl:selectList id="country" path="country" selectList="${countries}" useManagedContentForMeanings="false"  selectedValue="${workflowEditCmd.country.code}" showLabel="false"/>
												<form:errors path="country" cssClass="field-validation-error"/></td>
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
									<td class="tag-middle-column">
											${workflowItemCmd.refundScenarioType}
									</td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label
											id="refundScenarioSubType" /></td>
									<td class="tag-middle-column">
											${workflowItemCmd.refundScenarioSubType}
									</td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label
											id="refundablePeriod" mandatory="true" /></td>
									<td class="tag-middle-column"><to:text
											id="refundablePeriodStartDate" showLabel="false" /></td>

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
		<to:primaryButton buttonType="submit" id="editRefund"
			targetAction="<%=PageParameterValue.EDIT_REFUND%>" />
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
													<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}itemName"/></th>
													<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}startZone"/></th>
													<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}endZone"/></th>
													<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}calculationBasis"/></th>
													<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}price"/></th>
													<th><spring:message code="refundAmount.tableheader" /></th>	
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${workflowEditCmd.refundItems}"
													var="refundItem">
													<%-- <c:out value="${refundItem}" /> --%>
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
									<tr>
									<td class="tag-left-column"><to:label id="totalTicketPrice" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowEditCmd.totalTicketAmount}" /></td>

								</tr>
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
												<c:forEach items="${workflowEditCmd.goodwillPaymentItems}"
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
											amount="${workflowEditCmd.ticketDeposit}" /></td>

								</tr>
									<tr>
									<td class="tag-left-column"><to:label id="payAsYouGoCredit" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowEditCmd.payAsYouGoCredit}" /></td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="adminFee" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowEditCmd.ticketAdminFee}" /></td>
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
									<td class="tag-middle-column">${workflowEditCmd.paymentMethod}</td>

								</tr>
								<c:choose>
									<c:when test="${workflowEditCmd.paymentType eq adHocLoad}">
											<tr>
												<td class="tag-left-column"><to:label id="targetCardNumber" /></td>
												<td class="tag-middle-column">${workflowEditCmd.targetCardNumber}</td>
											</tr>
											<tr>
												<td class="tag-left-column"><to:label id="station" /></td>
												<td class="tag-middle-column">${workflowEditCmd.station}</td>
											</tr>
									</c:when>
								</c:choose>
								<tr>

									<td class="tag-left-column"><to:label id="paymentName" /></td>
									<td class="tag-middle-column">${workflowEditCmd.paymentName}</td>

								</tr>
								<tr>
									<td class="tag-left-column"><to:label id="paymentAddress" /><td class="tag-middle-column">
									${workflowEditCmd.paymentHouseNameNumber}<br />
									${workflowEditCmd.paymentStreet}<br />
									${workflowEditCmd.paymentTown}<br />
									${workflowEditCmd.paymentCountry.name}</td>
								</tr>

								<tr>
									<td class="tag-left-column"><to:label id="paymentAmount" /></td>
									<td class="tag-middle-column"><nemo-tfl:poundSterlingFormat
											amount="${workflowEditCmd.amount}" /></td>

								</tr>

							</tbody>
						</table>

					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="clear">&nbsp;</div>
	<div>
		<to:textarea id="reason" rows="2" cols="60" mandatory="true"/>
		<div class="clear">&nbsp;</div>
		<to:primaryButton buttonType="submit" id="save"
			targetAction="<%=PageParameterValue.SAVE_CHANGES%>" />
		<to:primaryButton buttonType="submit" id="cancel"
			targetAction="<%=PageParameterValue.CANCEL%>" />
	</div>
</form:form>

<script>
    var pageName = "${pageName}";
    var shortDatePattern = '<%=DateConstant.SHORT_DATE_PATTERN_JQUERY%>';
</script>
<script src="scripts/editWorkflowItem.js"></script>