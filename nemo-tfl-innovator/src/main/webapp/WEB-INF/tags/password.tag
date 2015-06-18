<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@attribute name="hint" required="false" type="java.lang.Boolean" %>

<%@attribute name="labelCssClass" required="false" type="java.lang.String" %>
<%@attribute name="inputCssClass" required="false" type="java.lang.String" %>
<%@attribute name="errorCssClass" required="false" type="java.lang.String" %>
<%@attribute name="hintCssClass" required="false" type="java.lang.String" %>

<c:set var="placeholder"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${PLACEHOLDER}"/></c:set>
<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/></c:set>

<to:label id="${id}" mandatory="${mandatory}" labelCssClass="${labelCssClass}"/>
<form:password path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" placeholder="${placeholder}" title="${tip}"
               cssClass="shaded-input ${inputCssClass}"/>
<form:errors path="${id}" cssClass="field-validation-error ${errorCssClass}"/>
<to:hint id="${id}" hint="${hint}" hintCssClass="${hintCssClass}"/>
