<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="title" required="true" type="java.lang.String" %>

<div class="r">
    <div class="headline-container plain">
        <h1><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}${title}"/></h1>
    </div>
</div>
