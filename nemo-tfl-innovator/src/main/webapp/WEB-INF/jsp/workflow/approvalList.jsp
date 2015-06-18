<%@page
	import="com.novacroft.nemo.tfl.common.constant.PageParameterArgument"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page
	import="com.novacroft.nemo.tfl.innovator.controller.ApprovalListController"%>

<form:form modelAttribute="approvalListCmd"
	cssClass="form form-with-tooltips"
	action="${approvalListCmd.formLocation}">
	<table class="display" summary="Display table">
		<tbody>
			<to:textTable id="caseNumber" size="10" row="1" />
			<to:textTable id="dateCreated" size="20" />
			<to:textTable id="amount" row="2" size="10" />
			<to:textTable id="agent" size="20" />
			<to:checkboxTable id="exact" size="40" row="4" colspan="2" val="true" />
			<nemo-tfl:selectList id="paymentMethod" path="paymentMethod" cssClass="approvalListDropDowns" selectList="${PaymentType}" selectedValue="${approvalListCmd.paymentMethod}"  mandatory="true" useManagedContentForMeanings="true" />
			<nemo-tfl:selectList id="status" path="status" cssClass="approvalListDropDowns" selectList="${ApprovalListStatus}" selectedValue="${approvalListCmd.status}" mandatory="true" useManagedContentForMeanings="true"/>
			<nemo-tfl:selectList id="reason" path="reason" cssClass="approvalListDropDowns"  selectList="${RefundApprovalReason}" selectedValue="${approvalListCmd.reason}" mandatory="true" useManagedContentForMeanings="true"/>
			<to:hidden id="formLocation" value="${approvalListCmd.formLocation}" />
			<to:hidden id="targetAction" value="search" />
		</tbody>
	</table>
	<div id="button-area">
		<to:button id="clearCriteria" />
		<to:imageButton id="search" imageCssClass="button-search"
			targetAction="<%=PageParameterValue.SEARCH%>" />
		<to:loadingIcon />
	</div>
</form:form>

<div id="tasks">
	<to:header id="approval" />
	<div class="dataTable-container">
		<form:form id="claimForm" class="form-with-tooltips" commandName="claimCmd" action="${approvalListCmd.formLocation}.htm" method="post">
			<to:hidden id="caseNumber" />
			<to:hidden id="formLocation" />
		</form:form>
		<table class="clickable" cellpadding=0 cellspacing=1 id="approvals">
			<thead>
				<tr class="grid_heading">
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}caseNumber" /></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}dateCreated" /></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}reasons" /></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}amount" /></th>
					<th></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}timeOnQueue" /></th>
					<th></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}agent" /></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}paymentMethod" /></th>
					<th><spring:message
							code="${pageName}${pageName ne ' ' ? '.' : ''}status" /></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="i" value="0" />
				<c:forEach items="${approvalListCmd.approvals}" var="approval">
					<tr class="grid_row">
						<td>${approval.caseNumber}</td>
						<td>${approval.createdTime}</td>
						<td>${approval.approvalReasons}</td>
						<td><nemo-tfl:poundSterlingFormat amount="${approval.amount}" /></td>
						<td>${approval.amount}</td>
						<td>${approval.timeOnQueue}</td>
						<td>${approval.timeOnQueueAsLong}</td>
						<td><c:choose>
								<c:when test="${approval.agent eq null}">
								</c:when>
								<c:otherwise>
									${approval.agent}
								</c:otherwise>
							</c:choose></td>
						<td>${approval.paymentMethod}</td>
						<td>${approval.status}</td>
					</tr>
					<c:set var="i" value="${i + 1}" />
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<script src="scripts/approvalList.js"></script>
<script type="text/javascript">
var sAddress = "${pageContext.request.contextPath}";
var pageName = "${approvalListCmd.formLocation}";
var approvalSearchURL = "<%=PageUrl.APPROVALLIST%>";
var workflowItemURL = "<%=PageUrl.VIEW_WORKFLOW_ITEM%>";
var approvalListCmd = "#" + "<%=PageCommand.APPROVALLIST%>";
</script>