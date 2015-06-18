<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="headingOverride" required="false" type="java.lang.String" %>

<c:set var="heading"><spring:message code="${pageName}.${TITLE}"/></c:set>

<div class="r">
    <div class="headline-container plain">
        <h1>
            <% if (StringUtils.isBlank(headingOverride)) {%>
            <c:out value="${heading}"/>
            <% } else { %>
            <c:out value="${headingOverride}"/>
            <% } %>
        </h1>
    </div>
</div>
