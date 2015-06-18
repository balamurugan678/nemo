<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div class="r">
    <form:form id="choosePaymentCard" method="post" action="<%=PageUrl.PAYMENT%>" commandName="<%= PageCommand.CART %>"
               cssClass="form-with-tooltips">

        <nemo-tfl:radioButtonList id="paymentCardAction" path="paymentCardAction" selectList="${paymentCards}"
                                  mandatory="true" selectedValue="${cartCmd.paymentCardAction}" showHint="false"
                                  useManagedContentForMeanings="false"/>

        <div class="button-container clearfix">
            <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.INV_CANCEL %>"/>
            <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.SELECT_PAYMENT_CARD %>"/>
        </div>
    </form:form>
</div>