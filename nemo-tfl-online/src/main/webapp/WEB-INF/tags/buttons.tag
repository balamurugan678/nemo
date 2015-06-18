<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="targetAction" required="false" type="java.lang.String" %>

<div class="form-with-tooltips">
    <div class="button-container clearfix">
        <button type="submit" class="secondary-button button" name="targetAction" value="cancel">
            <spring:message code="<%= ContentCode.CANCEL.buttonLabelCode()%>"/></button>
        <%--<!-- Replaced due to ie 7 issue where the value between the button tags is passed rather than the value, in the value attribute.--> --%>  
        <input type="hidden" name="targetAction" value="${targetAction}" />
        <button type="submit" id="continue" class="primary-button button">
            <spring:message code="<%= ContentCode.CONTINUE.buttonLabelCode()%>"/></button>
    </div>
</div>
