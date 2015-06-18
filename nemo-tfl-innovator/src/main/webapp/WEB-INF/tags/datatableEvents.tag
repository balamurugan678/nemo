<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags"%>
<div class="dataTable-container">
	<table class="grid" cellpadding=0 cellspacing=1 id="events">
		<thead>
			<tr class="grid_heading">
				<th>Date &amp; Time</th>
				<th></th>
				<th>Event</th>
				<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${customerEvents}" var="event">
				<tr class="grid_row">
					<td>${event.createdDateTime}</td>
					<td></td>
					<td>${event.eventName}</td>
					<td>${event.additionalInformation}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>