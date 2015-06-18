<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="links" required="true" type="java.lang.String[]" %>

<c:set var="links" value="${links}"/>

<div class="vertical-button-container csc-module csc-dashboard-cards">

    <% for (int i = 0;
            i < links.length;
            i++) {%>
    <to:linkButton id="<%=links[i]%>"/>
    <% } %>

</div>
