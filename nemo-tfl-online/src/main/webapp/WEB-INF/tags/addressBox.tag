<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="houseNameNumber" required="false" type="java.lang.String" %>
<%@attribute name="street" required="false" type="java.lang.String" %>
<%@attribute name="town" required="false" type="java.lang.String" %>
<%@attribute name="country" required="false" type="java.lang.String" %>
<%@attribute name="postcode" required="false" type="java.lang.String" %>

<%@attribute name="phone" required="false" type="java.lang.String" %>
<%@attribute name="fax" required="false" type="java.lang.String" %>
<%@attribute name="email" required="false" type="java.lang.String" %>

<div class="box csc-module">
    <to:head3 id="personalDetails"/>
    <div class="csc-contact-group">
        <span class="hide-text icon mail-icon"></span> <span class="heading"><spring:message
            code="${pageName}${pageName ne ' ' ? '.' : ''}personalDetails.address.${HEADING}"/></span>
            <span><nemo-tfl:address houseNameNumber="${houseNameNumber}" street="${street}"
                                    town="${town}" postcode="${postcode}"
                                    country="${country}"/></span>
    </div>
    <div class="csc-contact-group">
        <to:phone phone="${phone}" fax="${fax}"/>
    </div>
    <div class="csc-contact-group">
        <to:email email="${email}"/>
    </div>
</div>
