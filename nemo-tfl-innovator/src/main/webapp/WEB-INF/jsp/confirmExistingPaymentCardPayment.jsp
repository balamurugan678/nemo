<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>

<c:set var="toPayDisplayAmount"><nemo-tfl:poundSterlingFormat
        amount="${cartCmd.cartDTO.cyberSourceRequest.totalAmountInPence}" amountInPence="true"/>
</c:set>

<c:set var="webCreditDisplayAmount"><nemo-tfl:poundSterlingFormat
        amount="${cartCmd.webCreditApplyAmount}" amountInPence="true"/>
</c:set>

<div class="r">
    <form:form id="confirmExistingPaymentCardPayment" method="post" action="<%=PageUrl.PAYMENT%>"
               commandName="<%= PageCommand.CART %>" cssClass="form-with-tooltips">

        <p>
            <c:if test="${cartCmd.cartDTO.cyberSourceRequest.totalAmountInPence > 0.0}">
                <spring:message code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_SAVED_CARD.textCode() %>"
                                arguments="${toPayDisplayAmount}, ${paymentCardDisplayName}"/>
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
            <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
            <to:primaryButton id="proceedWithPayment" buttonType="submit"
                              targetAction="<%= PageParameterValue.CONFIRM_SAVED_PAYMENT_CARD_PAYMENT %>"/>
        </div>
    </form:form>
</div>
