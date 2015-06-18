<%@page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>


<c:set var="toPayDisplayAmount"><nemo-tfl:poundSterlingFormat
        amount="${cartCmd.cartDTO.cyberSourceRequest.totalAmountInPence}" amountInPence="true"/>
</c:set>

<c:set var="webCreditDisplayAmount"><nemo-tfl:poundSterlingFormat
        amount="${cartCmd.webCreditApplyAmount}" amountInPence="true"/>
</c:set>
<c:choose>
	<c:when test="${cartCmd.webCreditApplyAmount > 0.0}">
		<nemo-tfl:breadcrumbs
				pageNames='<%=new String[] { Page.DASHBOARD, Page.PAYMENT_WITH_WEBCREDIT }%>' />
	</c:when>
	<c:otherwise>
	  	<nemo-tfl:breadcrumbs
				pageNames='<%=new String[] { Page.DASHBOARD, Page.PAYMENT_WIH_CARD }%>' />
	</c:otherwise>
</c:choose>			

<c:if test="${cartCmd.cartDTO.cyberSourceRequest.totalAmountInPence > 0.0}">
    <c:set var="heading"><spring:message
            code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_EXISTING_SAVED_CARD.titleCode() %>"/></c:set>
</c:if>

<c:if test="${cartCmd.webCreditApplyAmount > 0.0}">
    <c:set var="heading"><spring:message code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_WEB_CREDIT.titleCode() %>"/></c:set>
</c:if>

<c:set var="esacpedPaymentCardDisplayName"><c:out value="${paymentCardDisplayName}"/></c:set>

<to:headLine headingOverride="${heading}"/>

<div class="r">
    <form:form id="confirmExistingPaymentCardPayment" method="post" action="<%=PageUrl.PAYMENT%>"
               commandName="<%= PageCommand.CART %>" cssClass="form-with-tooltips">

        <p>
            <c:if test="${cartCmd.cartDTO.cyberSourceRequest.totalAmountInPence > 0.0}">
                <spring:message code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_SAVED_CARD.textCode() %>"
                                arguments="${toPayDisplayAmount}, ${esacpedPaymentCardDisplayName}"/>
                <c:out value=" "/>
            </c:if>

            <c:if test="${cartCmd.webCreditApplyAmount > 0.0}">
                <spring:message code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_WEB_CREDIT.textCode() %>"
                                arguments="${webCreditDisplayAmount}"/>
                <c:out value=" "/>
            </c:if>

            <spring:message code="<%= ContentCode.PAYMENT_AMOUNT_USING_SAVED_CARD_INSTRUCTIONS.textCode() %>"/>
        </p>

        <div class="button-container clearfix">
            <c:if test="${cartCmd.webCreditApplyAmount > 0.0}">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.PAYMENT_PAGE_BACK %>"/>
            </c:if>
            <c:if test="${cartCmd.cartDTO.cyberSourceRequest.totalAmountInPence > 0.0}">
                <to:secondaryButton id="cancel" buttonType="submit"
                                    targetAction="<%= PageParameterValue.CHOOSE_PAYMENT_CARD %>"/>
            </c:if>
            <to:primaryButton id="proceedWithPayment" buttonType="submit"
                              targetAction="<%= PageParameterValue.CONFIRM_SAVED_PAYMENT_CARD_PAYMENT %>"/>
        </div>
    </form:form>
</div>
