<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="mandatory" required="true" type="java.lang.String" %>

<%
    String[] titles = new String[]{"Please Select", "Miss", "Dr", "Mr", "Mrs", "Ms", "Prof", "Other"};
    request.setAttribute("titles", titles);
%>

<to:label id="title" mandatory="${mandatory}"/>
<to:select items="${titles}" id="title"/>
