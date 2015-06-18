<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="headingCssClass" required="false" type="java.lang.String" %>
<%@attribute name="showLink" required="true" type="java.lang.Boolean" %>
<%@attribute name="linkArguments" required="false" type="java.lang.String[]" %>
<%@attribute name="links" required="false" type="java.lang.String[]" %>

<div>
    <to:head2 headingCssClass="${headingCssClass}" id="${id}"/>
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TEXT}"/>
    <c:if test="${showLink}">
        <c:if test="${links == null}">
            <to:linkButton id="${id}" linkArguments="${linkArguments}"/>
        </c:if>
        <c:if test="${links != null}">
            <to:linkButtonBlock links="${links}"/>
        </c:if>
    </c:if>
</div>
