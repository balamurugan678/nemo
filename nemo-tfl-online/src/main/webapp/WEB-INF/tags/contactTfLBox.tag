<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<c:set var="houseNameNumber"><spring:message
        code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.address.houseNameNumber"/></c:set>
<c:set var="street"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.address.street"/></c:set>
<c:set var="town"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.address.town"/></c:set>
<c:set var="postcode"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.address.postcode"/></c:set>
<c:set var="country"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.address.country"/></c:set>
<c:set var="phone"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.phone"/></c:set>
<c:set var="fax"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.fax"/></c:set>
<c:set var="email"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.email"/></c:set>

<div class="box csc-module">
    <to:head3 id="contact.tfl"/>
    <div class="csc-contact-group">
        <span class="hide-text icon mail-icon"></span> <span class="heading"><spring:message
            code="${pageName}${pageName ne ' ' ? '.' : ''}contact.tfl.address.heading"/></span>
        <span><nemo-tfl:address houseNameNumber="${houseNameNumber}" street="${street}" town="${town}" postcode="${postcode}"
                                country="${country}"/></span>
    </div>
    <div class="csc-contact-group">
        <to:phone phone="${phone}" fax="${fax}"/>
    </div>
    <div class="csc-contact-group">
        <to:emailLink email="${email}"/>
    </div>
    <to:linkButton id="contact.tfl"/>
</div>
