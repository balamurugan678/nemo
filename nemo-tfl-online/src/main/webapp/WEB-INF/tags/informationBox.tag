<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>

<div class="box info-message borderless csc-module">
    <span class="heading significant"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${HEADING}"/></span>
    <span><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${id}.${TEXT}"/></span>
</div>
