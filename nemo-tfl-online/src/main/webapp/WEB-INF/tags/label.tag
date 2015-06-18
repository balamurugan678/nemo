<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@attribute name="labelCssClass" required="false" type="java.lang.String" %>
<%@attribute name="showColon" required="false" type="java.lang.Boolean" %>
<%@attribute name="value" required="false" type="java.lang.String" %>

<span class="label">
    <label for="${pageName}${pageName ne ' ' ? '.' : ''}${id}" class="${labelCssClass}">
        <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${LABEL}"/>
        ${mandatory ? '*' : ''} ${showColon eq false ? '' : ':'}
        <c:if test="${not empty value}">
            <span>${value}</span>
        </c:if>
    </label>
</span>
