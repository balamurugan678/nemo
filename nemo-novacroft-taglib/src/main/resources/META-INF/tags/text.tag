<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@attribute name="hint" required="false" type="java.lang.Boolean" %>
<%@attribute name="size" required="false" type="java.lang.String" %>

<%@attribute name="labelCssClass" required="false" type="java.lang.String" %>
<%@attribute name="inputCssClass" required="false" type="java.lang.String" %>
<%@attribute name="errorCssClass" required="false" type="java.lang.String" %>
<%@attribute name="hintCssClass" required="false" type="java.lang.String" %>
<%@attribute name="doNotshowLabel" required="false" type="java.lang.Boolean" %>

<c:set var="placeholder"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${PLACEHOLDER}"/></c:set>
<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/></c:set>

<c:set var="showLabel">
    <c:if test="${doNotshowLabel == null}">
        <%= Boolean.TRUE %>
    </c:if>
    <c:if test="${links != null}">
        ${!showLabel}
    </c:if>
</c:set>

<c:if test="${showLabel}">
    <to:label id="${id}" mandatory="${mandatory}" labelCssClass="${labelCssClass}"/>
</c:if>
<form:input path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" placeholder="${placeholder}"
            cssClass="shaded-input ${inputCssClass}" title="${tip}" ${size ne ' ' ? 'size="${size}"' : '' } }}/>
<form:errors path="${id}" cssClass="field-validation-error ${errorCssClass}"/>
<to:hint id="${id}" hint="${hint}" hintCssClass="${hintCssClass}"/>
