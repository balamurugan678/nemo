<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.AutoTopUpStatusType" %>
<nemo-tfl:breadcrumbs pageNames='<%= new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.VIEW_AUTO_TOP_UP_HISTORY} %>'/>
<c:set var="autoTopUpStatusfailed">
    <%=AutoTopUpStatusType.AUTO_TOP_UP_FAILED.getCode()%>
</c:set>
<to:headLine/>
<form:form commandName="<%= PageCommand.CART %>" action="<%= PageUrl.VIEW_AUTO_TOP_UP_HISTORY %>" cssClass="oo-responsive-form ">
	<to:hidden id="cardNumber"/>
	<div class="r">
		<div class="main">
			<div class="page-heading with-border">
				<to:oysterCardImageAndCardNumber oysterCardNumber="${cartCmd.cardNumber}"/>
	        </div>
			<div class="dataTable-container mTop csc-module">
				<table id="ViewRefundsTransactionTable">
					<thead>
						<tr>
							<th><spring:message code="viewAutoTopUpHistory.autoTopUpActivity.heading" /></th>
							<th><spring:message code="viewAutoTopUpHistory.location.heading" /></th>
							<th><spring:message code="viewAutoTopUpHistory.dateTimeStamp.heading" /></th>
							<th><spring:message code="viewAutoTopUpHistory.amount.heading" /></th>
							<th><spring:message code="viewAutoTopUpHistory.settlementDateTimeStamp.heading" /></th>
							<th><spring:message code="viewAutoTopUpHistory.status.heading" /></th>
							<th><spring:message code="viewAutoTopUpHistory.action.heading" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${autoTopUpHistory}" var="item" varStatus="status">
							<tr>
								<td>${item.autoTopUpActivity}</td>								
								<td>${item.stationName}</td>								
								<td>${item.activityDateTime}</td>
								<td><nemo-tfl:poundSterlingFormat amount="${item.autoTopUpAmount}" /></td>
								<td>${item.settlementDateTime}</td>
								<td>${item.settlementStatus}</td>
								<c:choose>
									<c:when test="${item.settlementStatus eq autoTopUpStatusfailed}">
										<td><a href="<%=PageUrl.RESETTLE_FAILED_AUTO_TOP_UP%>">Resettle</a></td>
									</c:when>
									<c:otherwise>
										<td>&nbsp;</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="buttonDiv">
				<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%=PageParameterValue.CANCEL%>" />
			</div>
		</div>
		<tiles:insertAttribute name="myAccount"/>
	</div>
</form:form>
<script type="text/javascript">
    $(document).ready(function () {
        $('#ViewRefundsTransactionTable').dataTable({
            "bSort": false, "bFilter": false, "bInfo": false, "sPaginationType": "full_numbers"
        });
    });
</script>