<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_REFUNDS}%>'/>
<to:headLine/>
<div class="r">
	<div class="main">
		<form:form action="<%= PageUrl.VIEW_REFUNDS%>">
			<spring:message code="viewRefunds.notice.text" />
			<p>
				<to:label id="count" />${fn:length(refunds)}
			</p>
			<div class="dataTable-container mTop">
				<table id="ViewRefundsTransactionTable">
					<thead>
						<tr>
							<th><spring:message code="viewRefunds.refundNumber.heading" /></th>
							<th><spring:message code="viewRefunds.date.heading" /></th>
							<th><spring:message code="viewRefunds.cardNumber.heading" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${refunds}" var="refundItem" varStatus="status">
							<tr>

								<td><a
									href="ViewRefundDetails.htm?refundId=${refundItem.id}">
										${refundItem.orderNumber}</td>
								</a>
								<td><fmt:formatDate
										pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
										value="${refundItem.dateOfRefund}" /></td>
								<td>${refundItem.oysterCardNumber}</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div>
					<br>
					<div class="button-container-width clearfix">
						<to:secondaryButton id="cancel" buttonType="submit"
							targetAction="<%=PageParameterValue.CANCEL%>" />
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#ViewRefundsTransactionTable').dataTable({
            "bSort": false, "bFilter": false, "bInfo": false, "sPaginationType": "full_numbers"
        });
    });
</script>