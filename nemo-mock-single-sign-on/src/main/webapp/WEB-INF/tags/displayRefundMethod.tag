<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="refund" required="true" type="com.novacroft.nemo.tfl.common.domain.Refund" %>
<%@attribute name="ordinal" required="false" type="String" %>
<%@attribute name="tradedTicket" required="true" type="Boolean" %>

<c:set var="reasons" scope="session" value="${refund.refundReasonings}"/>

<a href="#refundMethodTable${ordinal}" id="#refundMethod${ordinal}" class="nav-toggle"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.toggleShowlabel"/></a>
<div class="box csc-module" id="refundMethodTable${ordinal}">
	<table>
		<th colspan=2><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.refundReasonings"/></th>
		<c:if test="${not empty reasons['START_DATE']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.START_DATE"/></td>
				<td>${reasons["START_DATE"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['END_DATE']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.END_DATE"/></td>
				<td>${reasons["END_DATE"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['REFUND_BASIS']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.REFUND_BASIS"/></td>
				<td>	${reasons["REFUND_BASIS"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['USAGE_DURATION']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.USAGE_DURATION"/></td>
				<td>	${reasons["USAGE_DURATION"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['EFFECTIVE_REFUND_DATE']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.EFFECTIVE_REFUND_DATE"/></td>
				<td>	${reasons["EFFECTIVE_REFUND_DATE"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['FORMATTED_ORIGINAL_TICKET_PRICE']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.ORIGINAL_TICKET_PRICE"/></td>
				<td>${reasons["FORMATTED_ORIGINAL_TICKET_PRICE"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['USAGE_PERIOD']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.USAGE_PERIOD"/></td>
				<td>${reasons["USAGE_PERIOD"]}</td>
			</tr>
		</c:if>
		<c:if test="${not empty reasons['CHARGEABLE_DAYS']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.CHARGEABLE_DAYS"/></td>
				<td>${reasons["CHARGEABLE_DAYS"]}</td>
			</tr>
		</c:if>
		<c:if test="${tradedTicket and not empty reasons['FORMATTED_TRADED_TICKET_PRICE']}">
			<tr>
				<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.TRADED_TICKET_PRICE"/></td>
				<td>${reasons["FORMATTED_TRADED_TICKET_PRICE"]}</td>
			</tr>
		</c:if>
	</table>
</div>

<script>
   var toggleShowLabel= '<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.toggleShowLabel"/> ';
   var toggleHideLabel= '<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundReasons.toggleHideLabel"/> ';
</script>