<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.GET_OYSTER_CARD}%>'/>
<div class="r">
	<div>
    <form:form commandName="<%=PageCommand.CART_ITEM%>" action="<%=PageUrl.PAY_AS_YOU_GO%>" cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:head2 id="payAsYouGoOptions"/>
            <nemo-tfl:selectList id="creditBalance" path="creditBalance" selectList="${payAsYouGoCreditBalances}"
                                 mandatory="true" selectedValue="${cartItemCmd.creditBalance}"/>
            <form:errors path="creditBalance" cssClass="field-validation-error"/>
        </div>
          <div class="box borderless">
              <to:head2 id="autoTopup"/>
              <nemo-tfl:radioButtonList id="autoTopUpAmt" path="autoTopUpAmt" selectList="${payAsYouGoAutoTopUpAmounts}"
                                        mandatory="true" selectedValue="${cartItemCmd.autoTopUpAmt}"/>
              <spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}notification.text"/>
          </div>
        <hr/>
        <div class="form-with-tooltips">
	   		<div class="button-container clearfix">
		    	<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
		       	<to:primaryButton id="continue" buttonType="submit" targetAction="<%=PageParameterValue.ADD_TO_CART%>"/>
		    </div>
		 	<to:secondaryButton id="shoppingBasket" buttonType="submit" targetAction="<%= PageParameterValue.SHOPPING_BASKET %>"/>
		</div>
    </form:form>
	</div>
</div>