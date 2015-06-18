<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<to:header id="CaseHistoryNotes" />
<div class="dataTable-container">
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
			<c:forEach items="${workflowItemCmd.workflowItem.caseNotes}"
				var="historyItem">
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

<c:choose>
	<c:when test="${not empty workflowItemCmd.workflowItem.agent}">
		<form:form id="form" class="form-with-tooltips"
			commandName="<%=PageCommand.WORKFLOW_REJECT_COMMAND%>">
			<div class="pad10 margin10">
				<div class="pad10 right">
					<to:primaryButton buttonType="submit" id="approve"
						targetAction="<%=PageParameterValue.APPROVE%>" />
				</div>
				<c:choose>
				<c:when test="${fn:contains(workflowItemCmd.workflowItem.agent, 'Exceptions Stage')}">
					<to:primaryButton buttonType="submit" id="close"
						targetAction="<%=PageParameterValue.CLOSE%>" />
				</c:when>
				<c:otherwise>
					<div class="accordian-wrapper caseHistory right">
						<div id="rejectAccordian">
							<h3>
								<spring:message
									code="${pageName}${pageName ne ' ' ? '.' : ''}reject.label" />
							</h3>
							<div id="rejectSection">
								<nemo-tfl:selectList id="rejectReason" path="rejectReason"
									selectList="${rejectionReasons}" mandatory="true"
									selectedValue="${workflowRejectCmd.rejectReason}" />
									<form:errors path="rejectReason" cssClass="field-validation-error"/>
								<div id="rejectBox">
									<to:textarea id="rejectFreeText" rows="2" cols="60" mandatory="true" />
								</div>
								<to:primaryButton buttonType="submit" id="reject"
									targetAction="<%=PageParameterValue.REJECT%>" />
							</div>
						</div>
					</div>
				</c:otherwise>
				</c:choose>
			</div>		
		</form:form>
	</c:when>
</c:choose>

<div class="clear"></div>
<div class="margin_top5">
	<form:form id="form" class="form-with-tooltips"
		commandName="<%=PageCommand.WORKFLOWITEM_COMMAND%>">
		<to:textarea id="caseNotes" rows="2" cols="90"></to:textarea>
		<div>
			<to:primaryButton buttonType="submit" id="Add Case Note"
				targetAction="<%=PageParameterValue.WORKFLOWITEM_SAVECASENOTE%>" />
		</div>
	</form:form>
</div>