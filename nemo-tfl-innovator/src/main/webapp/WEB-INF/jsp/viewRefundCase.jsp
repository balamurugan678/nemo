<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<to:header id="RefundDetails" />
<div id="refundCase">
	<table>
		<tbody>
			<tr>
				<td><to:label id="refundCase"></to:label><br>${refundCaseCmd.caseNumber}</td>
				<td><to:label id="totalRefund" /><br /> <nemo-tfl:poundSterlingFormat
						amount="${refundCaseCmd.totalRefund}" /></td>
				<td><to:label id="createdDate" /><br />${refundCaseCmd.createdDate}</td>
				<td><to:label id="agent" /><br />${refundCaseCmd.agent}</td>
			</tr>
			<tr>
				<td><to:label id="effectiveDate" /><br />${refundCaseCmd.effectiveDate}</td>
				<td><to:label id="paidDate" /><br />${refundCaseCmd.paidDate}</td>
				<td><to:label id="refundStatus" /><br />${refundCaseCmd.status}</td>
				<c:choose>
					<c:when test="${refundCaseCmd.held == true}">
						<td><to:label id="heldSince" /><br />${refundCaseCmd.heldSince}</td>
					</c:when>
				</c:choose>
			</tr>
		</tbody>
	</table>
</div>

<to:header id="CustomerDetails" />
<div>
	<table class="default">
		<tbody>
			<tr>
				<td>
					<table class="defaultnoborder">
						<tbody>
							<tr>
								<td class="tag-left-column"><to:label id="sapNumber" /></td>
								<td class="tag-middle-column">${refundCaseCmd.sapNumber}</td>
							</tr>
							<tr>
								<td class="tag-left-column"><to:label id="customerName" /></td>
								<td class="tag-middle-column">${refundCaseCmd.customerName}</td>
							</tr>
							<tr>
								<td class="tag-left-column"><to:label id="customerUsername" /></td>
								<td class="tag-middle-column">${refundCaseCmd.customerUsername}</td>
							</tr>
							<tr>
								<td class="tag-left-column"><to:label id="customerAddress" /></td>
								<td class="tag-middle-column"><c:forEach
										items="${refundCaseCmd.customerAddress}" var="addressLine">
										<c:out value="${addressLine}" />
										<br />
									</c:forEach></td>
							</tr>
							<tr>
								<td class="tag-left-column"><to:label
										id="customerCardNumber" /></td>
								<td class="tag-middle-column">${refundCaseCmd.cardNumber}</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<to:header id="PaymentDetails" />
<div>
	<table class="default">
		<tbody>
			<tr>
				<td>
					<table class="defaultnoborder">
						<tbody>
							<tr>
								<td class="tag-left-column"><to:label id="paymentType" /></td>
								<td class="tag-middle-column">${refundCaseCmd.paymentType}</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${refundCaseCmd.paymentType eq 'Cheque'}">
										<td class="tag-left-column"><to:label
												id="chequeSerialNumber" /></td>
										<td class="tag-middle-column">${refundCaseCmd.chequeNumber}</td>
									</c:when>
									<c:when test="${refundCaseCmd.paymentType eq 'BACS'}">
										<td class="tag-left-column"><to:label id="bacsNumber" /></td>
										<td class="tag-middle-column">${refundCaseCmd.bacsNumber}</td>
									</c:when>
								</c:choose>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<to:header id="CaseNotes" />
<div>
	<table class="grid" cellpadding=0 cellspacing=1 id="caseNoteTable">
		<thead>
			<tr class="grid_heading">
				<th><to:label id="caseHistoryDate" showColon="false" /></th>
				<th><to:label id="caseHistoryActionMessage" showColon="false" /></th>
				<th><to:label id="caseHistoryAgent" showColon="false" /></th>
				<th><to:label id="caseHistoryStatus" showColon="false" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${refundCaseCmd.caseNotes}" var="historyItem">
				<tr style="background-color: #edf1f6">
					<td>${historyItem.date}</td>
					<td>${historyItem.message}</td>
					<td>${historyItem.agent}</td>
					<td>${historyItem.status}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


<script src="scripts/viewRefundCase.js"></script>