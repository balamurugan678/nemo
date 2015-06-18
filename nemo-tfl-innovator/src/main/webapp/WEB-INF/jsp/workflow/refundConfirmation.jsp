<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<c:choose>
<c:when test="${refundCmd.approval eq 'ACCEPTED'}" >


<h1>This Refund has been approved</h1>

</c:when>
<c:when test="${refundCmd.approval eq 'REJECTED'}" >

<h1>This Refund has been rejected</h1>

</c:when>

</c:choose>