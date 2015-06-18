<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form id="anonymousGoodwillRefundForm" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%= PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL %>">
	<form:errors cssClass="field-validation-error" >	</form:errors>
	<to:header id="AnonymousGoodwillRefund"/>
	<div class="clear"></div>
	<div class="box borderless bold">
		<to:label id="oysterCardNumber" value="${cartCmd.cardNumber}" />
	</div>
	<br/>
	<jsp:include page="manageGoodwill.jsp" />
	<jsp:include page="manageAnonymousGoodWillPayment.jsp"/>

    <div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:primaryButton id="continue" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
        </div>
    </div>
</form:form>