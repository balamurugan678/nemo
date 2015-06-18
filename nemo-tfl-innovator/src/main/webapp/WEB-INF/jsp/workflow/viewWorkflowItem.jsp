<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<c:choose>
	<c:when test="${not empty workflowItemCmd}">

		<c:choose>
			<c:when test="${workflowItemCmd.edit == true}">
				<jsp:include page="editWorkflowItem.jsp" />
			</c:when>
			<c:otherwise>
				<jsp:include page="workflowItemDetails.jsp" />
			</c:otherwise>
		</c:choose>

		<jsp:include page="caseHistoryNotes.jsp" />

	</c:when>
	<c:otherwise>
		<div>Unable to find the workflow item.</div>

	</c:otherwise>
</c:choose>

<script type="text/javascript">
	var pageName = "${pageName}";
</script>

<script src="scripts/viewWorkflowItem.js"></script>