<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div id="fulfiledOysterCard">
        <to:header id="oysterCardNumber"/>
        <div>
            <to:text id="fulfiledOysterCardNumber" showLabel="true" htmlEscape="false"/>
            <form:errors path="cardNumber" cssClass="field-validation-error"/>
        </div>
</div>
