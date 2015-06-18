<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<to:page name="customerapplication"/>

<form:form commandName="<%= PageCommand.PERSONAL_DETAILS %>" cssClass="form-with-tooltips" action="<%=PageUrl.INV_CUSTOMER %>">
    <to:hidden id="customerId"/>
    <to:hidden id="webAccountId"/>
    <to:hidden id="cardNumber"/>
    <to:hidden id="username"/>
    
    <div id="record">
        <to:header id="record"/>
        <to:formError />
        <to:text id="emailAddress" size="40" mandatory="true" /><div id="availableEmail" class="blank"></div>
    </div>
    <div id="customer">
        <to:header id="customer"/>
        <to:checkbox id="customerDeactivated" />
        <nemo-tfl:selectList id="customerDeactivationReason" path="customerDeactivationReason" selectedValue="${personalDetailsCmd.customerDeactivationReason}" selectList="${CustomerDeactivationReasons}" mandatory="true"/>
        <form:errors path="customerDeactivationReason" cssClass="field-validation-error"/>
        <div id="customerDeactivationReasonOther">
            <to:text id="customerDeactivationReasonOther" />
        </div>
         <div id = "customerDeactivationRules" class= "field-validation-error">  
    	 </div>
    </div>
    <div id="security">
        <to:header id="security"/>
        <to:text id="securityOption"/>
        <to:text id="securityPassword"/>
    </div>
    <div id="applicant">
        <to:header id="applicant"/>
        
        <div id="customersFound">
            <label>Customers Found:</label>
        </div>
        <nemo-tfl:selectList id="title" path="title" selectList="${titles}" mandatory="true"
                             selectedValue="${personalDetailsCmd.title}"/>
        <form:errors path="title" cssClass="field-validation-error"/>
        <to:text id="firstName" mandatory="true" />
        <to:text id="initials"/>
        <to:text id="lastName" mandatory="true" />
        <to:findAddress />
        <to:text id="houseNameNumber" mandatory="true" />
        <to:text id="street" mandatory="true" />
        <to:text id="town" mandatory="true" />
        <to:text id="county"/>
        <nemo-tfl:selectList id="country" path="country" selectList="${countries}" useManagedContentForMeanings="false"  selectedValue="${personalDetailsCmd.country.code}" mandatory="true" />
        <form:errors path="country" cssClass="field-validation-error"/>
        
        <to:text id="homePhone" mandatory="true" />
        <to:hidden id="homePhoneContactId"/>
        <to:text id="mobilePhone"/>
        <to:hidden id="mobilePhoneContactId"/>
    </div>
    
    <div id="oystercards">
        <to:header id="oystercards"/>
        <to:datatableCards />
    </div>
    
    <div id="oysterCardsAdditionalButtons" class="internalToolbar">
        <to:anchor id="addUnattachedCard" cssClass="button" href="?customerId=${personalDetailsCmd.customerId}&webaccountId=${personalDetailsCmd.webAccountId}" />
        <to:anchor id="addStandaloneGoodwill" cssClass="button" href="?id=${personalDetailsCmd.customerId}&cardNumber=${personalDetailsCmd.cardNumber}" /> 
    </div>
    
    <div id="applicationevents">
        <to:header id="applicationevents"/>
        <to:datatableEvents/>
    </div>
    <div id="notes">
        <to:header id="notes"/>
    </div>

    <div id="toolbar">
        <div class="left">
            <to:button buttonType="button" id="openwebaccount" buttonCssClass="openwebaccount"/>
            <to:button buttonType="button" id="orders" />
        </div>
        <div class="right">
            <to:button buttonType="button" id="cancel" buttonCssClass="cancel"/>
            <to:button buttonType="submit" id="save" buttonCssClass="save"/>
        </div>
    </div>
    <spring:message var="failedCardRefundCartHeader" code="${pageName}${pageName ne ' ' ? '.' : ''}FailedCardRefundCart.failedCardRefundCart.header"/>
</form:form>

<script type="text/javascript">
  var pageName = "${pageName}";
  var sAddress = "${pageContext.request.contextPath}";
  var onlineAddress = "${ONLINE_SYSTEM_BASE_URI}";
  var failedCardRefundCartHeader = "${failedCardRefundCartHeader}";
  var customerURL = "<%=PageUrl.INV_CUSTOMER %>";
  var showWebAccountDeactivationEnableFlag = "${personalDetailsCmd.showWebAccountDeactivationEnableFlag}";
  var customerEmailAddress = "${personalDetailsCmd.emailAddress}";
  var customerId = "${personalDetailsCmd.customerId}";
</script>


<script src="scripts/customer.js"></script>