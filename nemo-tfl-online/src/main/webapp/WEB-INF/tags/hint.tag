<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="hint" required="false" type="java.lang.Boolean" %>
<%@attribute name="hintCssClass" required="false" type="java.lang.String" %>

<c:if test="${hint}">
    <span class="${hintCssClass}" style="color: #2070b6;"><spring:message
            code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${HINT}"/></span>
</c:if>
