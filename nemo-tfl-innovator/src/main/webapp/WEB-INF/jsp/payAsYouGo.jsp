<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
        <div class="box borderless">
            <to:head2 id="payAsYouGoOptions"/>
            <nemo-tfl:selectList id="cartItemCmd.creditBalance" path="cartItemCmd.creditBalance" selectList="${payAsYouGoCreditBalances}"
                                 mandatory="true" selectedValue="${cartItemCmd.creditBalance}"/>
            <form:errors path="cartItemCmd.creditBalance" cssClass="field-validation-error"/>
        </div>
            <br/>

            <div class="box borderless">
                <to:head2 id="autoTopup"/>
                <nemo-tfl:selectList id="autoTopUpAmt" path="cartItemCmd.autoTopUpAmt" selectList="${payAsYouGoAutoTopUpAmounts}"
                                          mandatory="true" selectedValue="${cartItemCmd.autoTopUpAmt}"/>
                <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}notification.text"/>
            </div>
        <div class="form-with-tooltips" style="clear:both">
            <to:secondaryButton id="payAsYouGoContinue" buttonCssClass="leftalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.ADD_PAY_AS_YOU_GO_ITEM_TO_CART %>"/>
        </div>
