<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="headingOverride" required="false" type="java.lang.String" %>
<%@attribute name="subtitleText" required="false" type="java.lang.String" %>

<c:set var="heading"><spring:message code="${pageName}.${TITLE}"/></c:set>
<div class="r">
    <div class="page-heading variable-heading-indent with-border with-subtitle">
        <h1>
            <% if (StringUtils.isBlank(headingOverride)) {%>
            <c:out value="${heading}"/>
            <% } else { %>
            <c:out value="${headingOverride}"/>
            <% } %>
        </h1>
        <% if (!StringUtils.isBlank(subtitleText)) {%>
	        <h2 class="styled-heading">
            <c:out value="${subtitleText}"/>
	        </h2>
        <% } %>
    </div>
</div>
