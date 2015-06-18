<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>
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
					<td>${event.eventDescription}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>