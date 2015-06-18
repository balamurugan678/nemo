<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="nemo-tfl" uri="/WEB-INF/nemo-tfl-taglib.tld" %>

<div class="dataTable-container">
	<table class="clickable" cellpadding=0 cellspacing=1 id="refundTable">
		<thead>
			<tr class="grid_heading">
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}caseNumber"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}dateCreated"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}agent"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}customerName"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}cardNumber"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}paymentMethod"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}status"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${refundSearchCmd.refunds}" var="refund">
				<tr class="grid_row">
					<td>${refund.caseNumber}</td>
					<td>${refund.dateCreated}</td>
					<td>${refund.agent}</td>
					<td>${refund.customerName}</td>
					<td>${refund.cardNumber}</td>
					<td>${refund.paymentMethod}</td>
					<td>${refund.status}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>