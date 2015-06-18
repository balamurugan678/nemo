<%--
  Note that the TfL pop up window JS/CSS requires the page to be wrapped in a div tag with an id of "full-width-content"
--%>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>
<%@tag language="java" pageEncoding="ISO-8859-1" %>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="id" required="true" type="java.lang.String" %>

<a href="#" class="container link-button" data-popup="#${id}">
    <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${LINK}"/>
    <span class="icon right-arrow vertically-centred"></span>
</a>

<div class="csc-popup-content">
    <div id="${id}">
        <h3><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${POPUP}.${HEADING}"/></h3>
        <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${POPUP}.${TEXT}"/>
    </div>
</div>
