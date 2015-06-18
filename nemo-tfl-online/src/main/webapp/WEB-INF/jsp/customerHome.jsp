<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<to:breadlvl2 lvl1="Fares &amp; Payments" lvl2="Get Oyster Card"/>
<to:areaheader title="Get Oyster Card"/>
<div class="r">
    <form:form commandName="cartCmd" cssClass="form-with-tooltips" action="customer.htm">
        <form:hidden path="id"/>
        <to:titles mandatory="true"/>
        <to:text id="firstName" mandatory="true"/>
        <to:text id="initials" mandatory="true"/>
        <to:text id="lastName" mandatory="true"/>
        <div class="button-container clearfix">
            <button type="submit" class="button"
            ">Submit</button>
        </div>
    </form:form>
</div>