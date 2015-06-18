<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="nemo-tfl" uri="/WEB-INF/nemo-tfl-taglib.tld" %>

<div class="dataTable-container">
	<table class="clickable" cellpadding=0 cellspacing=1 id="approvals">
		<thead>
			<tr class="grid_heading">
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}caseNumber"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}dateCreated"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}reasons"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}amount"/></th>
				<th></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}timeOnQueue"/></th>
				<th></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}agent"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}paymentMethod"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}status"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${approvalListCmd.approvals}" var="approval">
				<tr class="grid_row">
					<td>${approval.caseNumber}</td>
					<td>${approval.createdTime}</td>
					<td>${approval.approvalReasons}</td>
					<td><nemo-tfl:poundSterlingFormat amount="${approval.amount}" /></td>
					<td>${approval.amount}</td>
					<td>${approval.timeOnQueue}</td>
					<td>${approval.timeOnQueueAsLong}</td>
					<td>${approval.agent}</td>
					<td>${approval.paymentMethod}</td>
					<td>${approval.status}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>