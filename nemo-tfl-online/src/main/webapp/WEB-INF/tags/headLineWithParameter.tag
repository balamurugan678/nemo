<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="headingOverride" required="false" type="java.lang.String" %>
<%@attribute name="isHeadingUnderlined" required="false" type="java.lang.Boolean" %>

<c:set var="heading"><spring:message code="${pageName}.${TITLE}"/></c:set>
<c:set var="headingUnderlined" value="oo-heading"/>
<c:if test="${!empty isHeadingUnderlined && !isHeadingUnderlined}">
	<c:set var="headingUnderlined" value=""/>
</c:if>

<div class="r">
    <div class="page-heading ${headingUnderlined} with-border ">
        <h1>
            <% if (StringUtils.isBlank(headingOverride)) {%>
            	<c:out value="${heading}"/>
            <% } else { %>
            	<c:out value="${heading} ${headingOverride}"/>
            <% } %>
        </h1>
    </div>
</div>
