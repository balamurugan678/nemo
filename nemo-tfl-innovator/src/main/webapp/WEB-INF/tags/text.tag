<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>
<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="id" required="true" type="java.lang.String"%>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean"%>
<%@attribute name="hint" required="false" type="java.lang.Boolean"%>
<%@attribute name="size" required="false" type="java.lang.Integer"%>
<%@attribute name="disabled" required="false" type="java.lang.Boolean"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="value" required="false" type="java.lang.String"%>
<%@attribute name="showLabel" required="false" type="java.lang.Boolean" %>
<%@attribute name="showPoundSign" required="false" type="java.lang.Boolean" %>
<%@attribute name="htmlEscape" required="false" type="java.lang.Boolean" %>
<%@attribute name="hintCssClass" required="false" type="java.lang.String" %>
<%@attribute name="maxlength" required="false" type="java.lang.Integer"%>
<%@attribute name="readonly" required="false" type="java.lang.String"%>

<c:set var="placeholder"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${PLACEHOLDER}"/></c:set>
<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/></c:set>
<c:set var="showLabel" value="${(empty showLabel) ? true : showLabel}" />

<c:if test="${showLabel eq true}">
    <to:label id="${id}" mandatory="${mandatory}" labelCssClass="${labelCssClass}"/>
</c:if>
<form:input path="${id}" id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" cssClass="shaded-input ${cssClass}"
            size="${size == null ? 20 : size}" disabled="${disabled}" value="${value}" placeholder="${placeholder}"
            title="${tip}" maxlength="${maxlength == null ? 100 : maxlength}" readonly="${readonly}"/>
<c:if test="${hint}" >
	<span class="hint-color" ><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.hint"/></span>
</c:if>
<form:errors path="${id}" cssClass="field-validation-error " htmlEscape="${(empty htmlEscape) ? true : htmlEscape}"/>
<to:hint id="${id}" hint="${hint}" hintCssClass="${hintCssClass}"/>
