<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<!--TODO: Need to implement in generic way  -->

<c:choose>
    <c:when test="${cartCmd.pageName eq 'TopUpTicket'}">
        <nemo-tfl:breadcrumbs pageNames='<%=new String[]{"Dashboard", "TopUpTicket"}%>'/>
    </c:when>
    <c:when test="${cartCmd.pageName eq 'QuickBuy'}">
        <nemo-tfl:breadcrumbs pageNames='<%=new String[]{"Dashboard", "QuickBuy"}%>'/>
    </c:when>
    <c:otherwise>
        <nemo-tfl:breadcrumbs pageNames='<%=new String[]{"getOysterCard"}%>'/>
    </c:otherwise>
</c:choose>