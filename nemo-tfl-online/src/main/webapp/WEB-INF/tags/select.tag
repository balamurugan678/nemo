<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="items" required="true" type="java.lang.String[]" %>

<form:select path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" cssClass="combo">
    <c:forEach items="${items}" var="i">
        <c:choose>
            <c:when test="${i != 'Please Select' && i != 'please select'}">
                <form:option value="${i}">${i}</form:option>
            </c:when>
            <c:otherwise>
                <form:option value="">${i}</form:option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</form:select>