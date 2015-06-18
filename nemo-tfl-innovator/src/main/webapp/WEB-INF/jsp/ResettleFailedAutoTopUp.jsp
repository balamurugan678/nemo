<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<to:page name="resettleFailedAutoTopUpView"/>

<form:form commandName="<%= PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT %>" cssClass="form-with-tooltips" action="<%=PageUrl.RESETTLE_FAILED_AUTO_TOP_UP %>">
    <div id="customer">
        <to:header id="customer"/>
        <to:label id="customerId"/>
        <to:label id="oysterCardNumber"/>
        <to:label id="failedAutoTopUpAmount"/>
        <to:checkbox id="customerNotLiableFlag" />
    </div>
    <div id="failedAutoTopUpResettle">
        <to:header id="failedAutoTopUpResettle"/>
        <to:datatableResettle/>
    </div>
    <div id="notes">
        <to:header id="caseNote"/>
        <to:textarea rows="10" cols="70" mandatory="true" id="caseNote"></to:textarea>
    </div>
    <div id="resettlementData">
        <to:header id="Resettlement"/>
        <to:text id="resettlementPeriod" mandatory="true"/>
        <to:text id="resettlementEndDate" mandatory="true"/>
        <to:text id="resettlementAmount" mandatory="true"/>
	</div>
	<div id="payment">
      	<to:header id="payment"/>
      	<to:label id="paymentAttempt"/>
	    <nemo-tfl:selectList id="paymentType" path="paymentType" selectList="${PaymentType}" mandatory="true" selectedValue="${FailedAutoTopUpCaseCmd.paymentType}" useManagedContentForMeanings="true" />
	    <form:errors path="paymentType" cssClass="field-validation-error"/>
	    <div class="clear"></div>
    	<to:label id="total"/>
    </div>
    <div id="toolbar">
        <div class="right">
            <to:primaryButton id="cancel" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%=PageParameterValue.CANCEL%>" />
			<to:primaryButton id="update" buttonCssClass="rightalignbutton"	buttonType="submit" targetAction="<%=PageParameterValue.UPDATE_RESETTLE%>" />
            <to:primaryButton id="resettle" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%=PageParameterValue.RESETTLE%>" />
        </div>
    </div>
    <input type="hidden" value="${FailedAutoTopUpCaseCmd.customerId }" name="customerId">
    <input type="hidden" value="${FailedAutoTopUpCaseCmd.oysterCardNumber }" name="oysterCardNumber">
</form:form>

<script type="text/javascript">
  var pageName = "${pageName}";
  var sAddress = "${pageContext.request.contextPath}";
  var onlineAddress = "${ONLINE_SYSTEM_BASE_URI}";
  var customerId = "${FailedAutoTopUpCaseCmd.customerId}";
</script>
<script src="scripts/resettleFailedAutoTopUp.js"></script>