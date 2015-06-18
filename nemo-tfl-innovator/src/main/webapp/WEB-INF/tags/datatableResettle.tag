<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="nemo-tfl" uri="/WEB-INF/nemo-tfl-taglib.tld" %>


<div class="dataTable-container">
	<div id="cards_wrapper" class="dataTables_wrapper" role="grid">
		<div id="cards_length" class="dataTables_length">
			<label>Show 
				<select size="1" name="cards_length" aria-controls="cards">
					<option value="5" selected="selected">5</option>
					<option value="10">10</option>
					<option value="25">25</option>
					<option value="50">50</option>
					<option value="100">100</option>
				</select> 
				entries
			</label>
		</div>
		<div class="dataTables_filter" id="cards_filter">
			<label>Search: 
				<input type="text" aria-controls="cards">
			</label>
		</div>
		<table class="grid dataTable" cellpadding="0" cellspacing="1" id="failedAutoTopUpResettle" aria-describedby="cards_info">
		<thead>
			<tr class="grid_heading">
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}caseId"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}caseStatus"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}atuAmount"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}hotlistDate"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundDate"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}resettlementDate"/></th>
				<th><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}actor"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fatuResettleHistoryRecords}" var="fatuResettle">
				<tr class="grid_row">
					<td>${fatuResettle.failedAutoTopUpCaseId}</td>
					<td>${fatuResettle.caseStatus}</td>
					<td>${fatuResettle.autoTopUpAmount}</td>
					<td>${fatuResettle.autoTopUpDate}</td>
					<td>${fatuResettle.hotlistDate}</td>
					<td>${fatuResettle.refundDate}</td>
					<td>${fatuResettle.resettlementAttemptDate}</td>
					<td>${fatuResettle.actor}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="dataTables_info" id="cards_info">Showing 1 to 1 of 1 entries</div>
		<div class="dataTables_paginate paging_full_numbers" id="cards_paginate">
			<a tabindex="0" class="first paginate_button paginate_button_disabled" id="cards_first">First</a>
			<a tabindex="0" class="previous paginate_button paginate_button_disabled" id="cards_previous">Previous</a>
			<span>
				<a tabindex="0" class="paginate_active">1</a>
			</span>
				<a tabindex="0" class="next paginate_button paginate_button_disabled" id="cards_next">Next</a>
				<a tabindex="0" class="last paginate_button paginate_button_disabled" id="cards_last">Last</a>
		</div>
</div>