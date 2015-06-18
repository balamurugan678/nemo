<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="mandatory" required="true" type="java.lang.String" %>
<%
    String[] securityQuestions =
            new String[]{"Please Select", "Your mother's maiden name", "A memorable date (dd/mm/yy)", "A memorable place"};
    request.setAttribute("securityQuestions", securityQuestions);
%>
<label for="securityQuestion"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}securityQuestion"/>${mandatory ? '*' : ''}
    : </label>
<to:select items="${securityQuestions}" id="securityQuestion"/>