<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<to:page name="customercall"/>
<form:form commandName="callCmd" cssClass="form-with-tooltips" action="call.htm">
    <to:hidden id="id"/>
    <to:hidden id="customerId"/>
    <to:hidden id="addressId"/>
    <div id="details">
        <to:header id="details"/>

        <to:selectList id="title" path="title" selectList="${titles}" mandatory="false"
                       selectedValue="${callCmd.title}" size="1"/>
        <to:text id="firstName"/>
        <to:text id="lastName"/>
        <to:findAddress/>
        <to:text id="houseNameNumber" size="40"/>
        <to:text id="street" size="40"/>
        <to:text id="town"/>
        <to:text id="county"/>
        <to:text id="country"/>
        <to:text id="homePhone"/>
        <to:text id="mobilePhone"/>
        <to:text id="emailAddress" size="40"/>
        <span class="break-line"></span>

        <to:selectList id="callTypeId" path="callTypeId" selectList="${callTypes}" mandatory="true"
                       selectedValue="${callCmd.callTypeId}" size="1" useManagedContentForMeanings="false"/>

        <to:textarea id="description" cols="60" rows="6"/>
        <to:textarea id="resolution" cols="60" rows="6"/>
        <to:textarea id="notes" cols="60" rows="6"/>
    </div>
    <div class="clear"></div>
    <div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:button buttonType="button" id="cancel" buttonCssClass="cancel"/>
            <to:button buttonType="submit" id="save" buttonCssClass="save"/>
        </div>
    </div>
</form:form>

