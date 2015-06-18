<%--
HTML input text element with the name of the element set to the value of the ID without any prefix, eg page name
--%>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="value" required="false" type="java.lang.String" %>
<%@attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@attribute name="hint" required="false" type="java.lang.Boolean" %>
<%@attribute name="size" required="false" type="java.lang.Integer" %>
<%@attribute name="maxlength" required="false" type="java.lang.Integer" %>

<%@attribute name="labelCssClass" required="false" type="java.lang.String" %>
<%@attribute name="inputCssClass" required="false" type="java.lang.String" %>
<%@attribute name="errorCssClass" required="false" type="java.lang.String" %>
<%@attribute name="hintCssClass" required="false" type="java.lang.String" %>
<%@attribute name="additionalAttributes" required="false" type="java.lang.String" %>

<c:set var="placeholder"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${PLACEHOLDER}"/></c:set>
<c:set var="tip"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TIP}"/></c:set>

<div class="form-control-wrap">
    <to:label id="${id}" mandatory="${mandatory}" labelCssClass="${labelCssClass}"/>
    <input id="${pageName}${pageName ne ' ' ? '.' : ''}${id}" name="${id}" placeholder="${placeholder}"
           class="shaded-input ${inputCssClass}" title="${tip}" value="${value}" type="text" size="${size}"
           maxlength="${maxlength}" ${additionalAttributes}/>
    <to:hint id="${id}" hint="${hint}" hintCssClass="${hintCssClass}"/>
</div>
