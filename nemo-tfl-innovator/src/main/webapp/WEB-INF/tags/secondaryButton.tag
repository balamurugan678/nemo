<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="buttonType" required="true" type="java.lang.String" %>
<%@attribute name="buttonCssClass" required="false" type="java.lang.String" %>
<%@attribute name="targetAction" required="false" type="java.lang.String" %>

<to:button id="${id}" buttonType="${buttonType}" targetAction="${targetAction}"
           buttonCssClass="secondary-button ${buttonCssClass}" onclick="this.value='${targetAction}'"/>
