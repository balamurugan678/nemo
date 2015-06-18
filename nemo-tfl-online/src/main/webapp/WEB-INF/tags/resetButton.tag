<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="buttonCssClass" required="false" type="java.lang.String" %>

<to:button id="${id}" buttonType="reset" buttonCssClass="${buttonCssClass}"/>
